package pasa.cbentley.framework.core.ui.src4.input;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.interfaces.C;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.core.src4.structs.listdoublelink.LinkedListDouble;
import pasa.cbentley.framework.core.ui.src4.ctx.CoreUiCtx;
import pasa.cbentley.framework.core.ui.src4.ctx.ObjectCUC;
import pasa.cbentley.framework.core.ui.src4.event.DeviceEventXY;
import pasa.cbentley.framework.core.ui.src4.event.GestureArea;
import pasa.cbentley.framework.core.ui.src4.event.GesturePointer;
import pasa.cbentley.framework.core.ui.src4.tech.ITechInput;

/**
 * 
 * @author Charles Bentley
 *
 */
public class Pointer extends ObjectCUC implements IStringable {
   private LinkedListDouble gestureList;


   /**
    * Last event for this Pointer
    */
   private DeviceEventXY    lastPointerEvent;

   /**
    * Internal PointerID. Each Pointing device is assigned a pointerID.
    * <br>
    * The hosts creates the events but requests pointerIDs for new devices.
    * <br>
    * When a new finger is pressed, Host requests a new free pointerID
    * from the framework input module.
    * <br>
    * 
    */
   private int              pointerID;

   public Pointer(CoreUiCtx cuc, int pointerID) {
      super(cuc);
      gestureList = new LinkedListDouble(cuc.getUC());
      this.pointerID = pointerID;
      lastPointerEvent = new DeviceEventXY(cuc, 0, 0, 0, 0, 0, 0);
   }

   /**
    * <li> {@link C#ANC_0_TOP_LEFT}
    * <li> {@link C#ANC_2_TOP_RIGHT}
    * <li> {@link C#ANC_6_BOT_LEFT}
    * <li> {@link C#ANC_8_BOT_RIGHT}
    * <li> {@link C#ANC_1_TOP_CENTER}
    * <li> {@link C#ANC_3_CENTER_LEFT}
    * <li> {@link C#ANC_5_CENTER_RIGHT}
    * <li> {@link C#ANC_7_BOT_CENTER}
    * 
    * <br>
    * {@link C#ANC_MINUS1_OUTSIDE}
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
      if (isInside(x, y, wu, hu)) {
         pos = C.ANC_0_TOP_LEFT;
      } else if (isInside(x + wu, y, wu, hu)) {
         pos = C.ANC_1_TOP_CENTER;
      } else if (isInside(x + wu2, y, wu, hu)) {
         pos = C.ANC_2_TOP_RIGHT;
      } else if (isInside(x, y + hu, wu, hu)) {
         pos = C.ANC_3_CENTER_LEFT;
      } else if (isInside(x + wu, y + hu, wu, hu)) {
         pos = C.ANC_4_CENTER_CENTER;
      } else if (isInside(x + wu2, y + hu, wu, hu)) {
         pos = C.ANC_5_CENTER_RIGHT;
      } else if (isInside(x, y + hu2, wu, hu)) {
         pos = C.ANC_6_BOT_LEFT;
      } else if (isInside(x + wu, y + hu2, wu, hu)) {
         pos = C.ANC_7_BOT_CENTER;
      } else if (isInside(x + wu2, y + hu2, wu, hu)) {
         pos = C.ANC_8_BOT_RIGHT;
      }
      if (pos == C.ANC_MINUS1_OUTSIDE) {
         //#debug
         toDLog().pEvent("Pointer  " + getX() + "," + getY() + " is outside for x,y=" + x + "," + y + ":" + w + "," + h + " wu=" + wu + " hu=" + hu, null, GesturePointer.class, "computeXYGrid3x3Position");
      }
      return pos;
   }

   /**
    * Button value of last pointer event of this pointer.
    * @return
    */
   public int getButton() {
      return lastPointerEvent.getDeviceButton();
   }

   /**
    * The List of {@link GesturePointer} recording the moves of this Pointing device.
    * <br>
    * @return
    */
   public LinkedListDouble getGestureList() {
      return gestureList;
   }

   /**
    * The number of {@link GesturePointer} currently behaving.
    * 
    * Usually, it will be one.
    * @return
    */
   public GesturePointer[] getGestures() {
      int num = gestureList.getNumElements();
      GesturePointer[] gps = new GesturePointer[num];
      int count = 0;
      GesturePointer le = (GesturePointer) gestureList.getHead();
      while (le != null) {
         gps[count] = le;
         count++;
         le = (GesturePointer) le.getNext();
      }
      return gps;
   }

   /**
    * Return the last recorded {@link DeviceEventXY} for this {@link Pointer}.
    * @return {@link DeviceEventXY}. Never null.
    */
   public DeviceEventXY getLastPointerEvent() {
      return lastPointerEvent;
   }

   /**
    * Null if {@link GesturePointer}.
    * @return {@link GesturePointer}
    */
   public GesturePointer getOldest() {
      return (GesturePointer) gestureList.getHead();
   }

   /**
    * The internal pointer id of the pointing device.
    * <br>
    * Domain is shared by all pointer classes. (mouses, fingers).
    * <br>
    * 
    * @return
    */
   public int getPointerID() {
      return pointerID;
   }

   public int getX() {
      return lastPointerEvent.getX();
   }

   public int getY() {
      return lastPointerEvent.getY();
   }

   public boolean isFinger() {
      return lastPointerEvent.getDeviceType() == ITechInput.DEVICE_4_SCREEN;
   }

   public boolean isInside(GestureArea ga) {
      return isInside(ga.x, ga.y, ga.w, ga.h);
   }

   public boolean isInside(int x, int y, int w, int h) {
      if (this.getX() >= x && this.getY() >= y) {
         if (this.getX() < x + w && this.getY() < y + h) {
            return true;
         }
      }
      return false;
   }

   public boolean isMouse() {
      return lastPointerEvent.getDeviceType() == ITechInput.DEVICE_1_MOUSE;
   }

   void setLastPointerEvent(DeviceEventXY dex) {
      lastPointerEvent = dex;
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, Pointer.class, 199);
      toStringPrivate(dc);
      super.toString(dc.sup());
      dc.appendVarWithSpace("PointerID", pointerID);
      dc.nlLvl(lastPointerEvent, "LastPointerEvent");
      dc.nlLvl(gestureList, "Gestures");
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, Pointer.class, 199);
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   private void toStringPrivate(Dctx dc) {
      
   }
   //#enddebug
   
}
