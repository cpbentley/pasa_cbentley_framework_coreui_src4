package pasa.cbentley.framework.coreui.src4.event;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.framework.coreui.src4.ctx.CoreUiCtx;
import pasa.cbentley.framework.coreui.src4.tech.ITechGestures;

/**
 * This class will have to fit Command Trigger format
 * @author Charles Bentley
 *
 */
public class GestureIdentity implements IStringable {

   /**
    * Vector based direction. Computed from gesture start to gesture computation time.
    * <br>
    */
   public int         dir;

   /**
    * A Gesture Area. what is the meaning of null {@link GestureArea} ?
    * it takes the default accessible area. of which windows?
    */
   public GestureArea ga = new GestureArea(-5000, -5000, 10000, 10000);

   public int         grid;

   public boolean     isCancelEvent;

   public boolean     isContinuous;

   public boolean     isPonctual;

   /**
    * Position within the grid
    */
   public int         pos;

   public int[]       positionPath;

   public int         type;

   protected final CoreUiCtx cac;

   public GestureIdentity(CoreUiCtx cac) {
      this.cac = cac;

   }

   /**
    * The direction the Gesture 
    * 
    * <li> {@link ITechGestures#GESTURE_DIR_00_ANY}
    * <li> {@link ITechGestures#GESTURE_DIR_01_VERTICAL}
    * <li> {@link ITechGestures#GESTURE_DIR_03_TOP}
    * 
    * @return
    */
   public int getDir() {
      return dir;
   }

   /**
    * Maybe null
    * @return
    */
   public GestureArea getGestureArea() {
      return ga;
   }

   /**
    * <li>{@link ITechGestures#GESTURE_TYPE_1_DRAG_SLIDE}  
    * <li>{@link ITechGestures#GESTURE_TYPE_2_SWIPE}  
    * <li>{@link ITechGestures#GESTURE_TYPE_3_FLING}  
    * <li>{@link ITechGestures#GESTURE_TYPE_4_ENTER}  
    * 
    * @return
    */
   public int getType() {
      return type;
   }

   /**
    * 
    * @param gtype
    * @param area
    */
   public void init(int gtype, GestureArea area) {
      type = gtype;
      this.ga = area;
   }

   /**
    * 
    */
   public void reset() {
      grid = 0;
      ga = null;
      dir = 0;
      type = 0;
      pos = 0;
      positionPath = null;
   }

   //#mdebug
   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx sb) {
      sb.root(this, "GestureIdentity");
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "GestureIdentity");
   }
   //#enddebug

   public UCtx toStringGetUCtx() {
      return cac.getUCtx();
   }
}
