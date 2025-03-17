package pasa.cbentley.framework.core.ui.src4.event;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.framework.core.ui.src4.ctx.CoreUiCtx;
import pasa.cbentley.framework.core.ui.src4.ctx.ToStringStaticCoreUi;
import pasa.cbentley.framework.core.ui.src4.tech.ITechInput;
import pasa.cbentley.framework.core.ui.src4.utils.CircleArea;

/**
 * Cancels/Fires when pointerID reaches a position relative to {@link GestureArea}.
 * <br>
 * @author Charles Bentley
 *
 */
public class EventKeyPosition extends EventKey {

   /**
    * Use system slop defined by host. define the {@link GestureArea} for this key
    * {@link CircleArea}
    */
   public static final int SLOP_0_SYSTEM   = 0;

   public static final int SLOP_1_NONE     = 1;

   public static final int SLOP_2_INFINITE = 2;

   public static final int SLOP_3_BIG      = 3;

   public static final int SLOP_4_SMALL    = 4;

   private GestureArea     ga;

   private int             grid;

   private boolean         isInside;

   private int             pointerID;

   private int             position;

   /**
    * Activates for both enter and exit
    * @param keyType
    * @param ga
    * @param pointerID
    */
   public EventKeyPosition(CoreUiCtx ic,int keyType, GestureArea ga, int pointerID) {
      this(ic,keyType, ga, 0, true, 0, pointerID);
   }

   public EventKeyPosition(CoreUiCtx ic,int keyType, GestureArea ga, int pointerID, boolean isInside) {
      this(ic,keyType, ga, 0, isInside, 0, pointerID);
   }

   public EventKeyPosition(CoreUiCtx ic,int keyType, GestureArea ga, int pos, boolean isInside, int grid, int pointerID) {
      super(ic,keyType);
      if (ga == null)
         throw new NullPointerException();
      this.ga = ga;
      this.pointerID = pointerID;
      this.isInside = isInside;
      this.position = pos;
      this.grid = grid;
   }

   public EventKeyPosition(CoreUiCtx ic,int keyType) {
      super(ic,keyType);
   }

   public boolean isEquals(EventKey ek) {
      if (ek instanceof EventKeyPosition) {
         EventKeyPosition ekp = (EventKeyPosition) ek;
         if (pointerID == ekp.pointerID) {
            if (grid == ekp.grid) {
               if (position == ekp.position) {
                  if (isInside == ekp.isInside) {
                     if (ga.equals(ekp.ga)) {
                        return true;
                     }
                  }
               }
            }
         }
      }
      return false;
   }

   /**
    * When it activates, 
    */
   public boolean isKeyActivated(BEvent be) {
      if (be instanceof DeviceEventXY) {
         DeviceEventXY de = (DeviceEventXY) be;
         if (de.getDeviceMode() == ITechInput.MOD_3_MOVED) {
            boolean is = ga.isInside(de.getX(), de.getY());
            if (isInside) {
               if (is) {
                  return true;
               }
            } else {
               if (!is) {
                  return true;
               }
            }
         }
      }
      return false;
   }

   public String getUserLineString() {
      return "Position " + " " + GestureUtils.toStringFlag(position) + " " + ToStringStaticCoreUi.toStringKeyEventUserLine(patternAction);
   }

   /**
    * Key activates when pointer is inside gesture area gird position
    */
   public void setInsideActivated() {
      isInside = true;
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, "EventKeyPosition");
      super.toString(dc.sup());
      dc.appendVarWithSpace("PointerID", pointerID);
      dc.appendVarWithSpace("Grid", grid);
      dc.nlLvl(ga);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "EventKeyPosition");
      super.toString1Line(dc.sup1Line());
      dc.appendVarWithSpace("PointerID", pointerID);
      dc.appendVarWithSpace("Grid", grid);
      dc.oneLine(ga);
   }
   //#enddebug
}
