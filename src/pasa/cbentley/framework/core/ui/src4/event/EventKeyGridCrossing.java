package pasa.cbentley.framework.core.ui.src4.event;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.framework.core.ui.src4.ctx.CoreUiCtx;
import pasa.cbentley.framework.core.ui.src4.ctx.ToStringStaticCoreUi;

/**
 * Activates when pointer changes grid area
 * <br>
 * There is no specific position. 
 * <br>
 * 
 * @author Charles Bentley
 *
 */
public class EventKeyGridCrossing extends EventKey {

   private int         grid;

   private int         pointerID;

   private int         previous;

   private GestureArea ga;

   public EventKeyGridCrossing(CoreUiCtx cuc, int keyType, int grid, int pointerID, GestureArea ga) {
      super(cuc, keyType);
      this.grid = grid;
      this.pointerID = pointerID;
      this.ga = ga;
   }

   public boolean isKeyActivated(BEvent be) {
      if (be instanceof DeviceEventXY) {
         DeviceEventXY de = (DeviceEventXY) be;
         if (de.getDeviceID() == pointerID) {
            int x = de.getX();
            int y = de.getY();
            int currentGrid = GestureUtils.computeXYGridPosition(x, y, ga, grid);
            if (currentGrid != previous) {
               previous = currentGrid;
               return true;
            }
         }
      }
      return false;
   }

   public String getUserLineString() {
      return "Grid " + " " + ToStringStaticCoreUi.toStringGridType(grid) + " " + ToStringStaticCoreUi.toStringKeyEventUserLine(patternAction);
   }

   public int getCurrentGrid() {
      return previous;
   }

   public boolean isEquals(EventKey ek) {
      // TODO Auto-generated method stub
      return false;
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, "EventKeyGrid");
      super.toString(dc.sup());
      dc.appendVarWithSpace("PointerID", pointerID);
      dc.appendVarWithSpace("Grid", ToStringStaticCoreUi.toStringGridType(grid));
      dc.nlLvl(ga);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "EventKeyGrid");
      dc.appendVarWithSpace("PointerID", pointerID);
      dc.appendVarWithSpace("Grid", ToStringStaticCoreUi.toStringGridType(grid));
      dc.nlLvl(ga);
      super.toString1Line(dc.sup1Line());
   }
   //#enddebug
}
