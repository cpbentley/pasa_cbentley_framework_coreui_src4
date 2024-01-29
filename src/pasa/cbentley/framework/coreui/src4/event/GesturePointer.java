package pasa.cbentley.framework.coreui.src4.event;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.interfaces.C;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.core.src4.structs.IntBuffer;
import pasa.cbentley.core.src4.structs.listdoublelink.LinkedListDouble;
import pasa.cbentley.core.src4.structs.listdoublelink.ListElement;
import pasa.cbentley.core.src4.utils.BitUtils;
import pasa.cbentley.core.src4.utils.Geo2dUtils;
import pasa.cbentley.core.src4.utils.MathUtils;
import pasa.cbentley.core.src4.utils.StringUtils;
import pasa.cbentley.framework.coreui.src4.ctx.CoreUiCtx;
import pasa.cbentley.framework.coreui.src4.ctx.ToStringStaticCoreUi;
import pasa.cbentley.framework.coreui.src4.interfaces.IBentleyFwSerial;
import pasa.cbentley.framework.coreui.src4.tech.ITechGestures;
import pasa.cbentley.framework.coreui.src4.utils.ViewState;

/**
 * Collects the data required to generate {@link GestureEvent}. Describes a Gesture. Pointer Press. Dragged and Released.
 * <br>
 * <li> Pointer and its data points of x and y
 * <li> {@link EventKey}s to know when analysis should be done or canceled.
 * <li> {@link GestureIdentity} what to look for once fired by {@link EventKey}.
 * <br>
 * <br>
 * 
 * Gesture of one key pressed and released.
 * <br>
 * {@link GestureIdentity} :
 * <li> when none.. 
 * <li> when one
 * <li> when several. Each {@link GestureIdentity} is checked on {@link GesturePointer} activation.
 * <br>
 * <br>
 * 
 * It is serializable
 * 
 * @author Charles Bentley
 *
 */
public class GesturePointer extends ListElement implements ITechGestures, IBentleyFwSerial {

   private static final int  COSA             = 1 << 1;

   private static final int  DEGREES          = 1 << 3;

   private static final int  DISTANCE         = 1 << 2;

   /**
    * Used when point is outside the {@link GestureArea} box.
    * <br>
    * <br>
    */
   public static final int   PATH_OUT         = -1;

   private static final int  SINA             = 1 << 0;

   private EventKey          activatingKey;

   /**
    * Flag when values are computed... not to compute them again.
    */
   private int               computedFlags    = 0;

   private double            cosA;

   private int               dataX;

   private int               dataY;

   private String            debugName;

   private int               degrees;

   private double            distance;

   private EventKey[]        eventKeys;

   protected final CoreUiCtx fc;

   /**
    * Ratio relative to {@link GestureArea} of the Gesture.
    * Range of 0 to 1000.
    */
   private int               gestureAmplitude = 0;

   private GestureEvent      gestureEvent;

   /**
    * Defines 
    */
   private GestureIdentity   gestureIdentity;

   /**
    * Time in milliseconds from start to end of the gesture
    */
   private int               gestureTiming    = 0;

   private boolean           isIntentionalDrag;

   private boolean           isMoving;

   private boolean           isPressed;

   private boolean           isReleased;

   private int               pointerID;

   private int               pressedX;

   private int               pressedY;

   private double    sinA;

   /**
    * <li> {@link ITechGestures#GESTURE_DIR_03_TOP}
    */
   private int       slideSubType = ITechGestures.GESTURE_UNKNOWN;

   /**
    * <li> {@link ITechGestures#GESTURE_TYPE_1_DRAG_SLIDE}
    * <li> {@link ITechGestures#GESTURE_TYPE_2_SWIPE}
    * <li> {@link ITechGestures#GESTURE_TYPE_3_FLING}
    * 
    */
   private int       slideType    = ITechGestures.GESTURE_UNKNOWN;

   private long      timeReference;

   /**
    * Milli seconds since the pressed Time. (Start of time)
    */
   private IntBuffer times;

   private IntBuffer xs;

   private IntBuffer ys;

   public GesturePointer(LinkedListDouble list, CoreUiCtx kernel, int pointerID) {
      super(list);
      this.fc = kernel;
      this.pointerID = pointerID;
      gestureIdentity = new GestureIdentity(fc);
      UCtx uc = fc.getUCtx();
      xs = new IntBuffer(uc);
      ys = new IntBuffer(uc);
      times = new IntBuffer(uc);
   }

   public void addKey(EventKey ek) {
      eventKeys = EventKey.increaseCap(eventKeys);
      eventKeys[eventKeys.length - 1] = ek;
   }

   /**
    * When moved before the pressed?
    * @param x
    * @param y
    * @param time
    */
   public void addMove(int x, int y, long time) {
      computedFlags = 0;
      isMoving = true;
      xs.addInt(x);
      ys.addInt(y);
      //when before the press
      long delta = time - timeReference;
      times.addInt((int) delta);
      //calculer une moyenne sur plusieurs point de donnée?
      //TODO
      //meanXChange = floor((0.5 * meanXChange) + (0.5 * xChange));
   }

   /**
    * Compute value based.
    * Returns 0 when if it is not a released.
    * <br>
    * Slide must be done in a straight line.
    * <br>
    * Every single drag point must be the same value.
    * <br>
    * TODO training mode... User messages explaining the Gesture construction
    * TODO Path and Slide Gesture together in the same 
    * @return
    */
   private void computeSlide() {
      slideType = GESTURE_NOT_DETECTED;
      if (getSize() == 0) {
         //return early
         return;
      }
      int slideMarginSlop = fc.getInputSettings().getXYAxisSlop();
      int xReleaseVectorAbs = Math.abs(getVectorX());
      int yReleaseVectorAbs = Math.abs(getVectorY());
      int allerRetourMinAmp = fc.getInputSettings().getAllerRetourMinAmplitude();
      int allerRetourSlop = fc.getInputSettings().getAllerRetourMinAmplitude();
      int degrees = getAngleDegreeCos();
      //#debug
      toDLog().pEvent("yReleaseVector=" + yReleaseVectorAbs + " xReleaseVector=" + xReleaseVectorAbs + " allerRetourMinAmp=" + allerRetourMinAmp + "  allerRetourSlop=" + allerRetourSlop, null, GesturePointer.class, "computeSlide");
      if (yReleaseVectorAbs < allerRetourSlop && xReleaseVectorAbs < allerRetourSlop) {
         //read the middle value
         int halfIndex = times.getSize() / 2;
         int xHalf = xs.get(halfIndex);
         int yHalf = ys.get(halfIndex);
         int halfDist = (int) Geo2dUtils.getDistance(pressedX, pressedY, xHalf, yHalf);
         if (halfDist > allerRetourMinAmp) {
            int halfXVector = pressedX - xHalf;
            int halfYVector = pressedY - yHalf;
            //#debug
            toDLog().pEvent("halfDist=" + halfDist + " halfXVector=" + halfXVector + " halfYVector=" + halfYVector, null, GesturePointer.class, "computeSlide");
            int halfDegrees = getAngleDegreeCos(halfIndex);
            //we have an aller retour.. check direction
            int dir = getDirVector(halfXVector, halfYVector, slideMarginSlop, allerRetourMinAmp);
            slideType = ITechGestures.GESTURE_TYPE_7_ALLER_RETOUR;
            slideSubType = getDirFromDegrees(halfDegrees);
            //speed of it?
         }
      }
      int slideMinAmplitude = fc.getInputSettings().getSlideMinAmplitude();
      //int pointerSlideDir = getDirVector(getVectorX(), getVectorY(), slideMarginSlop, slideMinAmplitude);
      int pointerSlideDir = getDirFromDegrees(degrees);
      int dist = (int) getDistance();
      if (dist < slideMinAmplitude) {
         return;
      }
      //#debug
      toDLog().pEvent("vector=" + xReleaseVectorAbs + "," + yReleaseVectorAbs + " slideMinAmplitude=" + slideMinAmplitude + " slideMarginSlop=" + slideMarginSlop + "v=" + ToStringStaticCoreUi.toStringGestureDir(pointerSlideDir), null, GesturePointer.class, "computeSlide");
      if (slideType == GESTURE_NOT_DETECTED) {

         //timing of gesture is for 
         //base gesture on just time difference?
         int timeDiff = getTimeTotal();
         if (timeDiff < 300) {
            gestureTiming = ITechGestures.GESTURE_TIME_1_FAST;
         } else if (timeDiff > 1300) {
            gestureTiming = ITechGestures.GESTURE_TIME_2_SLOW;
         }

         int speed = getVelocityAverageOverDistance(100);
         //#debug
         toDLog().pEvent("Speed average over 100 pixels=" + speed, null, GesturePointer.class, "computeSlide");

         if (speed > 400) {
            //update to fling? look at acceleration
            slideType = ITechGestures.GESTURE_TYPE_2_SWIPE;
            slideSubType = pointerSlideDir;

            //#debug
            toDLog().pEvent("SubType " + ToStringStaticCoreUi.toStringGestureDir(slideSubType), null, GesturePointer.class, "computeSlide");

            int acc = getAccOverall();

            if (acc > 2500) {
               slideType = ITechGestures.GESTURE_TYPE_3_FLING;
            }
            //#debug
            toDLog().pEvent("Acceleration Overall=" + acc, null, GesturePointer.class, "computeSlide");
         } else {
            slideType = ITechGestures.GESTURE_TYPE_1_DRAG_SLIDE;
            slideSubType = pointerSlideDir;
         }
      }
   }

   /**
    * Computes the current position of {@link GesturePointer}
    * <li> {@link C#ANC_0_TOP_LEFT}
    * <li> {@link C#ANC_2_TOP_RIGHT}
    * <li> {@link C#ANC_6_BOT_LEFT}
    * <li> {@link C#ANC_8_BOT_RIGHT}
    * <li> {@link C#ANC_1_TOP_CENTER}
    * <li> {@link C#ANC_3_CENTER_LEFT}
    * <li> {@link C#ANC_5_CENTER_RIGHT}
    * <li> {@link C#ANC_7_BOT_CENTER}
    * @param x
    * @param y
    * @param w
    * @param h
    * @return
    */
   public int computeXYGrid3x3Position(int x, int y, int w, int h) {
      int hu = h / 3;
      int wu = w / 3;
      int hu2 = 2 * hu;
      int wu2 = 2 * wu;
      int pos = C.ANC_MINUS1_OUTSIDE;
      if (GestureUtils.isInside(this, x, y, hu, wu)) {
         pos = C.ANC_0_TOP_LEFT;
      } else if (GestureUtils.isInside(this, x + wu, y, wu, hu)) {
         pos = C.ANC_1_TOP_CENTER;
      } else if (GestureUtils.isInside(this, x + wu2, y, wu, hu)) {
         pos = C.ANC_2_TOP_RIGHT;
      } else if (GestureUtils.isInside(this, x, y + hu, wu, hu)) {
         pos = C.ANC_3_CENTER_LEFT;
      } else if (GestureUtils.isInside(this, x + wu, y + hu, wu, hu)) {
         pos = C.ANC_4_CENTER_CENTER;
      } else if (GestureUtils.isInside(this, x + wu2, y + hu, wu, hu)) {
         pos = C.ANC_5_CENTER_RIGHT;
      } else if (GestureUtils.isInside(this, x, y + hu2, wu, hu)) {
         pos = C.ANC_6_BOT_LEFT;
      } else if (GestureUtils.isInside(this, x + wu, y + hu2, wu, hu)) {
         pos = C.ANC_7_BOT_CENTER;
      } else if (GestureUtils.isInside(this, x + wu2, y + hu2, wu, hu)) {
         pos = C.ANC_8_BOT_RIGHT;
      }
      if (pos == C.ANC_MINUS1_OUTSIDE) {
         //#debug
         toDLog().pEvent("Pointer  " + getX() + "," + getY() + " is outside for x,y=" + x + "," + y + ":" + w + "," + h, null, GesturePointer.class, "computeXYGrid3x3Position");
      }
      return pos;
   }

   /**
    * Computes the position of the current x,y in the box..
    * -1 if outside.
    * <li> {@link C#ANC_0_TOP_LEFT}
    * <li> {@link C#ANC_2_TOP_RIGHT}
    * <li> {@link C#ANC_6_BOT_LEFT}
    * <li> {@link C#ANC_8_BOT_RIGHT}
    * 
    * @param x
    * @param y
    * @param w
    * @param h
    * @return
    */
   public int computeXYGrid4Position(int x, int y, int w, int h) {
      int hu = h / 2;
      int wu = w / 2;
      int pos = -1;
      if (GestureUtils.isInside(this, x, y, hu, wu)) {
         pos = C.ANC_0_TOP_LEFT;
      } else if (GestureUtils.isInside(this, x + wu, y, wu, hu)) {
         pos = C.ANC_2_TOP_RIGHT;
      } else if (GestureUtils.isInside(this, x, y + hu, wu, hu)) {
         pos = C.ANC_6_BOT_LEFT;
      } else if (GestureUtils.isInside(this, x + wu, y + hu, wu, hu)) {
         pos = C.ANC_8_BOT_RIGHT;
      }
      return pos;
   }

   private int get1For1000(int dist, int etalon) {
      return dist * 1000 / etalon;
   }

   /**
    * 
    * @param i
    * @param distance
    * @return
    */
   public int getAcceleration(int i, int distance) {
      int num = times.getSize();
      if (i <= 0 || i >= num) {
         return 0;
      }
      int dist = 0;
      int iCount = i;
      float accAcu = 0;
      int counter = 0;
      while (dist < distance && iCount > 0) {
         int v1 = getVelocity(iCount);
         int v2 = getVelocity(iCount - 1);
         int iDist = getDistance(iCount);
         int time = getTimeDiff(iCount); //millis
         float timeSeconds = (float) time;
         float acc = ((float) (v1 - v2) / timeSeconds);
         acc = acc * 1000;
         accAcu += acc;
         dist += iDist;
         iCount--;
         counter++;
      }
      return (int) (accAcu / (float) counter);
   }

   public int getAccelerationAverageOverDistance(int distance) {
      int num = times.getSize();
      return getAcceleration(num - 1, distance);
   }

   /**
    * Compute acceleration with a maximum of x.
    * <br>
    * <br>
    * 
    * @return
    */
   public int getAccelerationLast() {
      int num = times.getSize();
      if (num >= 2) {
         int v1 = getVelocity(num - 2);
         int v2 = getVelocity(num - 1);
         int time = times.getLast();
         float timeSeconds = (float) time / 1000;
         int acc = (int) ((float) (v1 - v2) / timeSeconds);
         return acc;
      } else {
         return 0;
      }
   }

   public int getAccOverall() {
      float v1 = getSpeed1stHalf();
      float v2 = getSpeed2ndhalf();
      int numPoints = times.getSize();
      int half = numPoints / 2;
      int timeFirstHalf = times.get(half);
      int timeLast = times.get(numPoints - 1);
      float timeSecondHalf = (timeLast - timeFirstHalf);
      //#debug
      //toDLog().pEvent("timeSecondHalf=" + timeSecondHalf + " v1=" + v1 + " v2=" + v2 + " timeLast=" + timeLast + " timeFirstHalf=" + timeFirstHalf, null, GesturePointer.class, "getAccOverall");

      if (timeSecondHalf == 0) {
         return 0;
      }
      float acc = (v2 - v1) / timeSecondHalf;
      acc = acc * 1000;
      return (int) acc;
   }

   /**
    * Returns the Ratio relative to {@link GestureArea} of the Gesture.
    * Range of 0 to 1000.
    * <br>
    * 1000 means Gesture was over whole distance
    * @return
    */
   public int getAmplitude() {
      if (hasFlagCompute(DISTANCE)) {

      } else {
         int num = times.getSize(); //number of points
         if (num > 0) {
            int dist = getDistance(num - 1);
            //compare the distance with the gesture area. this depends on the direction
            GestureArea ga = getIdentity().getGestureArea(); //TODO ga cannot be null. init with screen GA
            if (ga == null) {
               throw new NullPointerException();
            }
            int dir = getDirection();
            if (dir == ITechGestures.GESTURE_DIR_05_LEFT || dir == ITechGestures.GESTURE_DIR_06_RIGHT) {
               gestureAmplitude = get1For1000(dist, ga.w);
            } else if (dir == ITechGestures.GESTURE_DIR_03_TOP || dir == ITechGestures.GESTURE_DIR_04_BOT) {
               gestureAmplitude = get1For1000(dist, ga.h);
            } else {
               //use diagonal or max? max
               int val = Math.max(ga.h, ga.w);
               gestureAmplitude = get1For1000(dist, val);
            }
         }
         setFlagCompute(DISTANCE);
      }
      return gestureAmplitude;
   }

   /**
    * Angle degree relative to X axis.
    * <br>
    * @return
    */
   public int getAngleDegreeCos() {
      if (hasFlagCompute(DEGREES)) {
         return degrees;
      }
      int vy = getVectorY();
      double cosx = getCosAlpha();
      double angle = MathUtils.acos(cosx);
      int degree = (int) Math.toDegrees(angle);
      if (vy < 0) {
         degree = -degree + 360;
      }
      degrees = degree;
      setFlagCompute(DEGREES);
      return degree;
   }

   public int getAngleDegreeCos(int i) {
      int vy = getVectorY(i);
      double cosx = getCosAlpha(i);
      double angle = MathUtils.acos(cosx);
      int degree = (int) Math.toDegrees(angle);
      if (vy < 0) {
         degree = -degree + 360;
      }
      degrees = degree;
      setFlagCompute(DEGREES);
      return degree;
   }

   public int getAngleDegreeTan() {
      int vx = getVectorX();
      int vy = getVectorY(); //positive when going up
      if (vx != 0) {
         if (vy == 0) {
            if (vx < 0) {
               return 180;
            } else {
               return 0;
            }
         } else {
            double angleRad = MathUtils.aTan(vy, vx);
            int degree = (int) Math.toDegrees(angleRad);
            return degree;
         }
      } else {
         if (vy > 0) {
            return 90;
         } else {
            return 370;
         }
      }
   }

   /**
    * Return 0 if outside.
    * <br>
    * Return 1 if dead center.
    * <br>
    * @param grid
    * @return
    */
   public float getCloseNess(int grid, GestureArea ga) {
      int x = getX();
      int y = getY();

      float val = 0;

      return val;
   }

   /**
    * 
    * Will be negative when X vector is pointing towards the east
    * @return
    */
   public double getCosAlpha() {
      int vx = getVectorX();
      double c = getDistance();
      double cosx = (double) vx / c;
      return cosx;
   }

   public double getCosAlpha(int i) {
      int vx = getVectorX(i);
      double c = getDistanceV(i);
      double cosx = (double) vx / c;
      return cosx;
   }

   /**
    * <li> {@link ITechGestures#GESTURE_DIR_03_TOP}
    * <li> {@link ITechGestures#GESTURE_DIR_04_BOTT}
    * <li> {@link ITechGestures#GESTURE_DIR_05_LEFT}
    * <li> {@link ITechGestures#GESTURE_DIR_06_RIGHT}
    * <li> {@link ITechGestures#GESTURE_DIR_07_TOP_LEFT}
    * <li> {@link ITechGestures#GESTURE_DIR_08_TOP_RIGHT}
    * <li> {@link ITechGestures#GESTURE_DIR_09_BOT_LEFT}
    * <li> {@link ITechGestures#GESTURE_DIR_10_BOT_RIGHT}
    * @return
    */
   public int getCurDir() {
      int marginSlop = fc.getInputSettings().getXYAxisSlop();
      return getDirVector(getVectorX(), getVectorY(), marginSlop, 0);
   }

   public int getDataX() {
      return dataX;
   }

   public int getDataY() {
      return dataY;
   }

   public String getDebugName() {
      return debugName;
   }

   /**
    * Sub type based on drag gesture type
    * <li> {@link ITechGestures#GESTURE_DIR_03_TOP}
    * <li> {@link ITechGestures#GESTURE_DIR_04_BOT}
    * <li> {@link ITechGestures#GESTURE_DIR_05_LEFT}
    * <li> {@link ITechGestures#GESTURE_DIR_06_RIGHT}
    * <li> {@link ITechGestures#GESTURE_DIR_07_TOP_LEFT}
    * 
    * @return
    */
   public int getDirection() {
      computeSlide();
      return slideSubType;
   }

   public int getDirFromDegrees(int degrees) {
      if (degrees < 20) {
         return GESTURE_DIR_06_RIGHT;
      } else if (degrees < 70) {
         return GESTURE_DIR_10_BOT_RIGHT;
      } else if (degrees < 110) {
         return GESTURE_DIR_04_BOT;
      } else if (degrees < 160) {
         return GESTURE_DIR_09_BOT_LEFT;
      } else if (degrees < 200) {
         return GESTURE_DIR_05_LEFT;
      } else if (degrees < 250) {
         return GESTURE_DIR_07_TOP_LEFT;
      } else if (degrees < 290) {
         return GESTURE_DIR_03_TOP;
      } else if (degrees < 340) {
         return GESTURE_DIR_08_TOP_RIGHT;
      } else {
         return GESTURE_DIR_06_RIGHT;
      }
   }

   /**
    * Every vector has an angle.
    * <br>
    * <br>
    * When breaking the angle into the 8.
    * <br>
    * <br>
    * When below a threshold we have horizontal and vertical directions.
    * <br>
    * Otherwise the vector is a mix of both
    * <li> {@link ITechGestures#GESTURE_DIR_07_TOP_LEFT}
    * <li> {@link ITechGestures#GESTURE_DIR_08_TOP_RIGHT}
    * <li> {@link ITechGestures#GESTURE_DIR_09_BOT_LEFT}
    * <li> {@link ITechGestures#GESTURE_DIR_10_BOT_RIGHT}
    * <br>
    * <br>
    * <br>
    * <br>
    * TODO can also be computed based on alpha
    * @param xVector {@link GesturePointer#getVectorX()}
    * @param yVector
    * @param marginSlop number of pixels of deviation before Horiz/Verti becomes diagonal
    * @return
    */
   public int getDirVector(int xVector, int yVector, int marginSlop, int minAmplitude) {
      int absX = Math.abs(xVector);
      int absY = Math.abs(yVector);
      int pointerSlideValue = GESTURE_NOT_DETECTED;
      if (absX < marginSlop) {
         //we may have a vertical Vector
         if (yVector > minAmplitude) {
            pointerSlideValue = GESTURE_DIR_03_TOP;
         } else if (yVector < -minAmplitude) {
            pointerSlideValue = GESTURE_DIR_04_BOT;
         }
      } else if (absY < marginSlop) {
         if (xVector > minAmplitude) {
            pointerSlideValue = GESTURE_DIR_05_LEFT;
         } else if (xVector < -minAmplitude) {
            pointerSlideValue = GESTURE_DIR_06_RIGHT;
         }
      } else {
         //diags
         if (xVector > 0) {
            if (yVector > 0) {
               pointerSlideValue = GESTURE_DIR_07_TOP_LEFT;
            } else {
               pointerSlideValue = GESTURE_DIR_09_BOT_LEFT;
            }
         } else {
            if (yVector > 0) {
               pointerSlideValue = GESTURE_DIR_08_TOP_RIGHT;
            } else {
               pointerSlideValue = GESTURE_DIR_10_BOT_RIGHT;
            }
         }
      }
      return pointerSlideValue;
   }

   /**
    * Used to check minimum amplitude for some gestures.
    * <br>
    * @return
    */
   public double getDistance() {
      int vx = getVectorX();
      int vy = getVectorY();
      double c = Math.sqrt(vx * vx + vy * vy);
      return c;
   }

   /**
    * Returns the distance on the.
    * <br>
    * This is a measure of the amplitude of the movement
    * @param i 1 >= 1
    * @return
    */
   public int getDistance(int i) {
      int cx0 = xs.get(i);
      int cy0 = ys.get(i);

      int cx1 = xs.get(i - 1);
      int cy1 = ys.get(i - 1);

      //StrictFP...TODO
      float curDistance = Geo2dUtils.getDistance(cx0, cy0, cx1, cy1);

      return (int) curDistance;
   }

   public float getDistanceFloat(int i) {
      int cx0 = xs.get(i);
      int cy0 = ys.get(i);

      int cx1 = xs.get(i + 1);
      int cy1 = ys.get(i + 1);

      //StrictFP...TODO
      float curDistance = Geo2dUtils.getDistance(cx0, cy0, cx1, cy1);

      return curDistance;
   }

   public double getDistanceV(int i) {
      int vx = getVectorX(i);
      int vy = getVectorY(i);
      double c = Math.sqrt(vx * vx + vy * vy);
      return c;
   }

   /**
    *  <li> {@link ITechGestures#GESTURE_TYPE_1_DRAG_SLIDE}
    *  <li> {@link ITechGestures#GESTURE_TYPE_2_SWIPE}
    *  <li> {@link ITechGestures#GESTURE_TYPE_3_FLING}
    *  
    * @return
    */
   public int getDragGestureType() {
      computeSlide();
      return slideType;
   }

   /**
    * The {@link GestureEvent} 
    * null if no event was created by this {@link GesturePointer}
    * @return
    */
   public GestureEvent getEvent() {
      return gestureEvent;
   }

   public EventKey getEventKeyFire() {
      return activatingKey;
   }

   /**
    * Compute pixel distance from the center of the grid area defined by grid.
    * <br>
    * From this value one can compute a float ratio of closeness.
    * Ratio of distance and Half
    * @param gridPos
    * @return
    */
   public int getGridCenterDistance(int grid) {
      throw new RuntimeException();
   }

   /**
    * A {@link GestureIdentity}. It is never null. But initialized with type
    * {@link ITechGestures#GESTURE_TYPE_0_RAW} and {@link GestureArea}.
    * <br>
    * 
    * @return
    */
   public GestureIdentity getIdentity() {
      return gestureIdentity;
   }

   /**
    * might be null if no keys
    * @return
    */
   public EventKey[] getKeys() {
      return eventKeys;
   }

   public long getLastEventTime() {
      return times.getLast() + timeReference;
   }

   public int getNumKeys() {
      if (eventKeys == null)
         return 0;
      else {
         return eventKeys.length;
      }
   }

   public int getNumPoints() {
      return times.getSize();
   }

   /**
    * <li>{@link ITechGestures#GRID_TYPE_11_1x1}
    * <li>{@link ITechGestures#GRID_TYPE_12_1x2}
    * <li>{@link ITechGestures#GESTURE_PATH_03_2x3}
    * <li>{@link ITechGestures#GRID_TYPE_21_2x1}
    * <li>{@link ITechGestures#GRID_TYPE_11_1x1}
    * @param grid
    * @param ga
    * @return
    */
   public int[] getPath(int grid, GestureArea ga) {
      if (grid == ITechGestures.GRID_TYPE_23_2x3) {
         return getPath2x3(ga);
      } else if (grid == ITechGestures.GRID_TYPE_33_3x3) {
         return getPath3x3(ga);
      } else if (grid == ITechGestures.GRID_TYPE_22_2x2) {
         return getPath2x2(ga);
      } else {
         //#debug
         toDLog().pEvent1("" + grid, this, GesturePointer.class, "getGesturePath");
         throw new IllegalArgumentException("" + grid);
      }
   }

   public int[] getPath2x2(GestureArea area) {
      IntBuffer ib = new IntBuffer(fc.getUCtx());
      int size = getSize();
      int oldV = -1;
      for (int i = 0; i < size; i++) {
         int xi = xs.get(i);
         int yi = ys.get(i);
         //zero base computation
         int val = GestureUtils.computeXYGrid2x2Position(xi, yi, area);
         if (val != oldV) {
            ib.addInt(val);
            oldV = val;
         }
      }
      return ib.getIntsClonedTrimmed();
   }

   public int[] getPath2x3(GestureArea area) {
      IntBuffer ib = new IntBuffer(fc.getUCtx());
      int size = getSize();
      int oldV = -1;
      for (int i = 0; i < size; i++) {
         int xi = xs.get(i);
         int yi = ys.get(i);
         //zero base computation
         int val = GestureUtils.computeXYGrid2x3Position(xi, yi, area);
         if (val != oldV) {
            ib.addInt(val);
            oldV = val;
         }
      }
      return ib.getIntsClonedTrimmed();
   }

   public int[] getPath3x3(GestureArea area) {
      IntBuffer ib = new IntBuffer(fc.getUCtx());
      int size = getSize();
      int oldV = -1;
      for (int i = 0; i < size; i++) {
         int xi = xs.get(i);
         int yi = ys.get(i);
         //zero base computation
         int val = GestureUtils.computeXYGrid3x3Position(xi, yi, area);
         if (val != oldV) {
            ib.addInt(val);
            oldV = val;
         }
      }
      return ib.getIntsClonedTrimmed();
   }

   public int getPointerID() {
      return pointerID;
   }

   /**
    * The absolute millis at which gesture was pressed
    * @return
    */
   public long getPressedTime() {
      return timeReference;
   }

   /**
    * Starting X Position
    * @return
    */
   public int getPressedX() {
      return pressedX;
   }

   public int getPressedY() {
      return pressedY;
   }

   /**
    * Ending X Position of the Gesture.
    * @return
    */
   public int getReleasedX() {
      return xs.getLast();
   }

   public int getReleasedY() {
      return ys.getLast();
   }

   /**
    * Will be negative when Y is pointing towards up north
    * @return
    */
   public double getSinAlpha() {
      if (hasFlagCompute(SINA)) {
         return sinA;
      }
      int vx = getVectorX();
      int vy = getVectorY();
      double c = Math.sqrt(vx * vx + vy * vy);
      double sinx = (double) vy / c;
      sinA = sinx;
      setFlagCompute(SINA);
      return sinx;
   }

   /**
    * Number of x,y,time points
    * @return
    */
   public int getSize() {
      return times.getSize();
   }

   public float getSpeed1stHalf() {
      int numPoints = times.getSize();
      int half = numPoints / 2;
      float timeZero = times.get(0);
      float timeFirstHalf = times.get(half);
      int x1 = xs.get(half);
      int y1 = ys.get(half);
      int x0 = xs.get(0);
      int y0 = ys.get(0);
      float dist = Geo2dUtils.getDistance(x0, y0, x1, y1);

      float timeFirstHalfDiff = (timeFirstHalf - timeZero) / 1000;
      if (timeFirstHalfDiff == 0) {
         return 0;
      }
      float firstHalfSpeed = dist / timeFirstHalfDiff;
      return firstHalfSpeed;
   }

   public float getSpeed2ndhalf() {
      int numPoints = times.getSize();
      int half = numPoints / 2;
      float timeFirstHalf = times.get(half);
      float timeLast = times.get(numPoints - 1);
      int x1 = xs.get(half);
      int y1 = ys.get(half);
      int x2 = xs.get(numPoints - 1);
      int y2 = ys.get(numPoints - 1);

      float dist = Geo2dUtils.getDistance(x1, y1, x2, y2);
      float timeSecondHalf = (timeLast - timeFirstHalf) / 1000;
      if (timeSecondHalf == 0) {
         return 0;
      }
      float secondHalfSpeed = dist / timeSecondHalf;
      return secondHalfSpeed;
   }

   /**
    * Returns the last time of the event
    * @return
    */
   public int getTime() {
      return times.getLast();
   }

   /**
    * An array of ints with milliseconds time deltas, x and y.
    * <br>
    * Velocities are pixel distance divided by the time be
    * @return
    */
   public IntBuffer getTimeDeltas() {
      return times;
   }

   public int getTimeDiff(int i) {
      int time0 = times.get(i);
      int time1 = times.get(i - 1);
      return time0 - time1;
   }

   public int getTimeDiffLast() {
      int numPoints = times.getSize();
      return getTimeDiff(numPoints - 1);
   }

   /**
    * Time in milliseconds between the first and the current last.
    * <br>
    * <br>
    * This is an important metrics. Tells how fast the user pressed dragged and
    * released
    * @return
    */
   public int getTimeTotal() {
      return times.getLast();
   }

   /**
    * getX - pressedX.
    * <br>
    * This means 
    * <li> positive if going East/right
    * <li> negative when going West/left
    * <br>
    * @return
    */
   public int getVectorX() {
      return getX() - pressedX;
   }

   public int getVectorX(int i) {
      return xs.get(i) - pressedX;
   }

   /**
    * getY - pressedY.
    * <br>
    * This means 
    * <li>positive if going south/down
    * <li>negative when going north/up
    * <br>
    * Yea but what is y are negative
    * @return
    */
   public int getVectorY() {
      return getY() - pressedY;
   }

   public int getVectorY(int i) {
      return ys.get(i) - pressedY;
   }

   /**
    * Velocity in float for both axis.
    * <br>
    * Compute Velocity from current state, averaging over the number of measure points.
    * <br.
    * The speed from first to last event.
    * <br>
    * <br>
    * <br>
    * <br>
    * @return
    */
   public int getVelocity(int i) {
      int num = times.getSize();
      if (i > 0 && i < num) {
         int time = getTimeDiff(i); //millis
         int dist = getDistance(i);
         float timeSeconds = (float) time / 1000;
         float speed = ((float) dist / timeSeconds);
         //#debug
         //toDLog().pEvent("" + i + " time=" + time + " dist=" + dist + " timeSeconds=" + timeSeconds + " speed=" + speed, null, GesturePointer.class, "getVelocity");
         return (int) speed;
      } else {
         return 0;
      }
   }

   /**
    * Velocity using x samples... nope. we need over a certain distance.
    * 
    * @param i
    * @param size
    * @return
    */
   public int getVelocity(int i, int distance) {
      int num = times.getSize();
      if (i <= 0 || i >= num) {
         return 0;
      }
      int distAcc = 0;
      int iCount = i;
      float speedAcu = 0;
      int counter = 0;
      while (distAcc < distance && iCount > 0) {
         int time = getTimeDiff(iCount); //millis
         int iDist = getDistance(iCount);
         float timeSeconds = (float) time;
         //when too low
         if (timeSeconds != 0) {
            float speed = ((float) iDist / timeSeconds);
            //#debug
            //toDLog().pEvent("" + i + " time=" + time + " iDist=" + iDist + " timeSeconds=" + timeSeconds + " speed=" + speed, null, GesturePointer.class, "getVelocity");
            speedAcu += speed;
         }
         distAcc += iDist;
         iCount--;
         counter++;
      }
      speedAcu = speedAcu * 1000; //set it in seconds
      float speedAverage = (speedAcu / (float) counter);
      //#debug
      //toDLog().pEvent(i + " Speed Average " + speedAverage, null, GesturePointer.class, "getVelocity");

      return (int) speedAverage;

   }

   public int getVelocityAverageOverDistance(int distance) {
      int num = times.getSize();
      return getVelocity(num - 1, distance);
   }

   /**
    * Pixels per seconds.
    * <br>
    * At the first return 0
    * @return
    */
   public int getVelocityLast() {
      int num = times.getSize();
      return getVelocity(num - 1, 20);
   }

   /**
    * Pointer velocity from the pressed to current
    * @return
    */
   public int getVelocityOverall() {
      int cx1 = xs.getLast();
      int cy1 = ys.getLast();
      int cx0 = xs.get(0);
      int cy0 = ys.get(0);
      float curDistance = Geo2dUtils.getDistance(cx0, cy0, cx1, cy1);
      int timeSincePressed = times.getLast();
      int timeSince0 = times.get(0);
      float timeDiff = (timeSincePressed - timeSince0);
      float speedMillis = ((curDistance / (float) timeDiff));
      float speedSecs = speedMillis * 1000;
      //#debug
      //toDLog().pEvent(" timeDiff=" + timeDiff + " curDistance=" + curDistance + " speedSecs=" + speedSecs, null, GesturePointer.class, "getVelocityOverall");

      return (int) speedSecs;
   }

   /**
    * Return current pointer X coordinate
    * @return
    */
   public int getX() {
      return xs.getLast();
   }

   public IntBuffer getXs() {
      return xs;
   }

   public int getY() {
      return ys.getLast();
   }

   public IntBuffer getYs() {
      return ys;
   }

   private boolean hasFlagCompute(int flag) {
      return BitUtils.hasFlag(computedFlags, flag);
   }

   public boolean isDragIntentional() {
      return isIntentionalDrag;
   }

   public boolean isMoving() {
      return isMoving;
   }

   /**
    * True when pressed or dragging
    * @return
    */
   public boolean isPressed() {
      return isPressed;
   }

   public boolean isReleased() {
      return isReleased;
   }

   public boolean isReleasedDragged() {
      int xReleaseVector = getVectorX();
      int yReleaseVector = getVectorY();
      int val = fc.getInputSettings().getSlideMinAmplitude();
      if (Math.abs(xReleaseVector) > val) {
         return true;
      }
      if (Math.abs(yReleaseVector) > val) {
         return true;
      }
      return false;
   }

   /**
    * Return when released is done inside the rectangle defined by pressedX-value,pressedY-value,2*value,2*value
    * 
    * @param value pixel value
    * @return
    */
   public boolean isReleaseInsidePressedRetangle(int value) {
      if (isReleased) {
         if (Math.abs(getVectorX()) < value && Math.abs(getVectorY()) < value) {
            return true;
         }
      }
      return false;
   }

   public void reset(int id) {
      pointerID = id;
      isPressed = false;
      isReleased = false;
      xs.clear();
      ys.clear();
      times.clear();
      timeReference = 0;
      slideSubType = ITechGestures.GESTURE_UNKNOWN;
      slideType = ITechGestures.GESTURE_UNKNOWN;
      pressedX = 0;
      pressedY = 0;
      if (gestureIdentity != null) {
         gestureIdentity.reset();
      }
   }

   public void setDataXY(int dataX, int dataY) {
      this.dataX = dataX;
      this.dataY = dataY;
   }

   public void setDebugName(String str) {
      this.debugName = str;
   }

   /**
    * Set the {@link EventKey} that activated the {@link GesturePointer}.
    * 
    * @param edkFire
    */
   public void setEventKeyFire(EventKey edkFire) {
      this.activatingKey = edkFire;
   }

   private void setFlagCompute(int flag) {
      computedFlags = BitUtils.setFlag(computedFlags, flag, true);
   }

   public void setGesture(GestureIdentity gestureIdentity) {
      this.gestureIdentity = gestureIdentity;
   }

   public void setGestureEvent(GestureEvent ge) {
      this.gestureEvent = ge;
   }

   public void setKeys(EventKey[] ar) {
      this.eventKeys = ar;
   }

   /**
    * Set pressed with reference time as zero.
    * @param x
    * @param y
    */
   public void setPressed(int x, int y, long time, int button) {
      isPressed = true;
      pressedX = x;
      pressedY = y;
      xs.addInt(x);
      ys.addInt(y);
      timeReference = time;
      times.addInt(0);
   }

   //#mdebug

   public void setStateFrom(ViewState vs) {

   }

   public void setStateTo(ViewState vs) {

   }

   public IDLog toDLog() {
      return fc.getUCtx().toDLog();
   }

   public void toString(Dctx dc) {
      dc.root(this, GesturePointer.class, 1310);
      dc.appendVarWithSpaceNotNull("name", debugName);
      dc.appendVarWithSpace("pointerID", pointerID);
      dc.appendVarWithSpace("x", getX());
      dc.appendVarWithSpace("y", getY());
      computeSlide();
      dc.appendVarWithSpace("Slide", ToStringStaticCoreUi.toStringGestureDir(slideType));
      dc.appendVarWithSpace("SubType", ToStringStaticCoreUi.toStringGestureType(slideSubType));
      dc.nl();
      dc.appendVar("PressedXY", pressedX + "," + pressedY);
      dc.appendVarWithSpace("releasedXY", getReleasedX() + "," + getReleasedY());
      dc.appendVarWithSpace("vectorXY", getVectorX() + "," + getVectorY());
      dc.appendVarWithSpace("sizeXY", xs.getSize() + "," + ys.getSize());
      dc.appendVarWithSpace("TimesSize", times.getSize());
      dc.nl();
      dc.appendVar("dataX", dataX);
      dc.appendVarWithSpace("dataY", dataY);
      dc.appendVarWithSpace("DegreeCos", getAngleDegreeCos());
      dc.appendVarWithSpace("DegreesTan", getAngleDegreeTan());
      dc.nl();
      dc.appendVar("VelocityOverall", getVelocityOverall());
      dc.appendVarWithSpace("VelocityLast", getVelocityLast());
      dc.appendVarWithSpace("VelocityAvgDistance10", getVelocityAverageOverDistance(10));
      dc.appendVarWithSpace("VelocityAvgDistance100", getVelocityAverageOverDistance(100));
      dc.nl();
      dc.appendVar("isPressed", isPressed);
      dc.appendVarWithSpace("isIntentionalDrag", isIntentionalDrag);
      dc.appendVarWithSpace("isMoving", isMoving);
      dc.appendVarWithSpace("isReleased", isReleased);

      dc.nlLvlArray("EventKeys", eventKeys);

      dc.nlLvl(gestureEvent, "gestureEvent");
      dc.nlLvl(activatingKey, "activatingKey");

      //flag this data as potentially very big. only prints a few lines
      int num = xs.getSize();
      StringUtils su = fc.getUCtx().getStrU();
      for (int i = 0; i < num; i++) {
         if (i % 10 == 0) {
            dc.nl();
         }
         int x = xs.get(i);
         int y = ys.get(i);
         int time = times.get(i);
         String strx = su.prettyIntPaddStr(x, 3, " ");
         String stry = su.prettyIntPaddStr(y, 3, " ");
         String strxt = su.prettyIntPaddStr(time, 3, " ");
         dc.append(strx + "," + stry + " " + strxt + " \t");

      }

   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "GesturePointer");
      dc.appendVarWithSpaceNotNull("Name", debugName);
      dc.appendVarWithSpace("ID", pointerID);
      dc.appendVarWithSpace("x", getX());
      dc.appendVarWithSpace("y", getY());
      dc.appendVarWithSpace("NumKeys", getNumKeys());
      dc.appendVarWithSpace("NumPoints", getNumPoints());
   }

   public String toStringUserLine() {
      return debugName;
   }
   //#enddebug

}
