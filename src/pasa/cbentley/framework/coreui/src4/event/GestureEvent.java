package pasa.cbentley.framework.coreui.src4.event;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.framework.coreui.src4.ctx.CoreUiCtx;
import pasa.cbentley.framework.coreui.src4.ctx.ToStringStaticCoreUi;
import pasa.cbentley.framework.coreui.src4.tech.ITechGestures;
import pasa.cbentley.framework.coreui.src4.tech.IInput;

/**
 * {@link GestureEvent} is published after analysis of a {@link GesturePointer}.
 * <br>
 * 
 * @author Charles Bentley
 *
 */
public class GestureEvent extends BEvent implements IStringable, ITechGestures {

   public static final int FLAG_10_PONCTUAL = 1 << 9;

   public static final int FLAG_11_PONCTUAL = 1 << 10;

   /**
    * 
    */
   private int             gestureType;

   private GesturePointer  gp;

   /**
    * <li> {@link ITechGestures#GESTURE_TYPE_1_DRAG_SLIDE}
    * <li> {@link ITechGestures#GESTURE_TYPE_2_SWIPE}
    * <li> {@link ITechGestures#GESTURE_TYPE_3_FLING}
    * <li> {@link ITechGestures#GESTURE_TYPE_7_ALLER_RETOUR}
    * @param type
    */
   public GestureEvent(CoreUiCtx fc, int gestureType, GesturePointer gp) {
      super(fc);
      if (gp == null) {
         throw new NullPointerException();
      }
      this.gp = gp;
      this.type = IInput.TYPE_2_GESTURE;
      this.gestureType = gestureType;
   }

   public GestureEvent(CoreUiCtx fc, GesturePointer gp) {
      this(fc, ITechGestures.GESTURE_TYPE_0_RAW, gp);
   }

   public GestureArea getGA() {
      return gp.getIdentity().getGestureArea();
   }

   public GestureIdentity getGI() {
      return gp.getIdentity();
   }

   public int getGestureType() {
      return gestureType;
   }

   /**
    * True when {@link EventKey} responsible for the fire is {@link ITechEventKey#KEY_TYPE_0_FIRE}
    * @return
    */
   public boolean isContinuous() {
      return hasFlag(FLAG_11_PONCTUAL);
   }

   public boolean isPonctual() {
      return hasFlag(FLAG_10_PONCTUAL);
   }

   public GesturePointer getGP() {
      return gp;
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, "GestureEvent");
      dc.appendVarWithSpace("Type", ToStringStaticCoreUi.toStringGestureType(gestureType));
      dc.nlLvl(gp);
      super.toString(dc.sup());
   }

   public String getUserLineString() {
      String str = "Gesture" + ToStringStaticCoreUi.toStringGestureType(gestureType);
      int gestureDir = getGI().getDir();
      if (gestureDir != 0) {
         str += ToStringStaticCoreUi.toStringGestureDir(gestureDir);
      }
      return str + " [" + gp.getIdentity().getGestureArea().toStringCompact() + "]";
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "GestureEvent");
      dc.appendVarWithSpace("", ToStringStaticCoreUi.toStringGestureType(gestureType));
      super.toString(dc.sup1Line());
   }
   //#enddebug

}
