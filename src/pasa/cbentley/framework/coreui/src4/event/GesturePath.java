package pasa.cbentley.framework.coreui.src4.event;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.ToStringStaticC;
import pasa.cbentley.framework.coreui.src4.ctx.CoreUiCtx;
import pasa.cbentley.framework.coreui.src4.ctx.ToStringStaticCoreUi;
import pasa.cbentley.framework.coreui.src4.tech.ITechGestures;

/**
 * 
 * @author Charles Bentley
 *
 */
public class GesturePath extends GestureEvent {

   private int[] arrayInt;

   private int   gridType;

   public GesturePath(CoreUiCtx fc, int gridType, int[] array, GesturePointer gp, GestureArea ga) {
      super(fc, ITechGestures.GESTURE_TYPE_6_PATH, gp);
      this.gridType = gridType;
      this.arrayInt = array;
   }

   public int getGridType() {
      return gridType;
   }

   public void addPath(int v) {
      arrayInt = fc.getUCtx().getIU().addIntToEndOf(arrayInt, v);
   }

   /**
    * 
    * @return
    */
   public int[] getArray() {
      return arrayInt;
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, "GesturePath");
      if (arrayInt != null) {
         for (int i = 0; i < arrayInt.length; i++) {
            dc.appendWithSpace(ToStringStaticC.toStringAnc(arrayInt[i]));
         }
      }
   }

   public String getUserLineString() {
      return "Path " + ToStringStaticCoreUi.toStringGridType(gridType) + " " + getGA().toStringCompact();
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "GesturePath");
   }
   //#enddebug
}
