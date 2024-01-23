package pasa.cbentley.framework.coreui.src4.engine;

import pasa.cbentley.byteobjects.src4.core.ByteObject;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.framework.coreui.src4.ctx.CoreUiCtx;
import pasa.cbentley.framework.coreui.src4.ctx.IBOTypesCoreUI;
import pasa.cbentley.framework.coreui.src4.ctx.ObjectCUC;
import pasa.cbentley.framework.coreui.src4.event.GestureArea;
import pasa.cbentley.framework.coreui.src4.interfaces.IBOHostUI;
import pasa.cbentley.framework.coreui.src4.interfaces.IHostUI;
import pasa.cbentley.framework.coreui.src4.tech.ITechHostUI;

public abstract class HostUIAbstract extends ObjectCUC implements IHostUI, IBOHostUI, IStringable, ITechHostUI {

   /**
    * {@link IBOHostUI}.
    * Fields filled by the specific framework driver
    */
   protected ByteObject techHost;

   public HostUIAbstract(CoreUiCtx cuc) {
      super(cuc);
      techHost = cuc.getBOC().getByteObjectFactory().createByteObject(IBOTypesCoreUI.TYPE_4_HOST_UI, IBOHostUI.HOST_BASIC_SIZE);
   }

   public ByteObject getScreenConfig() {
      return getScreenConfigLive();
   }
   /**
    * Returns actual live screen config creates a new object.
    * <br>
    * Type is {@link ITypesCore#TYPE_007_LIT_ARRAY_INT}.
    * The first value being number of screens.
    * and then x,y,w,h tuple for each screens.
    * <br>
    * Thus. This config object does not represent screen positioning relative to each other.
    * @return
    */
   public ByteObject getScreenConfigLive() {
      GestureArea[] val = (GestureArea[]) this.getHostObject(DATA_ID_OBJ_01_SCREENS);

      int[] ar = new int[1 + (val.length * 2)];
      ar[0] = val.length;
      int index = 1;
      for (int i = 0; i < val.length; i++) {
         ar[index++] = val[i].x;
         ar[index++] = val[i].y;
         ar[index++] = val[i].w;
         ar[index++] = val[i].h;
      }
      ByteObject bo = cuc.getBOC().getLitteralIntFactory().getIntArrayBO(ar);
      return bo;
   }

   public ByteObject getBOHostUI() {
      return techHost;
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, HostUIAbstract.class, 70);
      toStringPrivate(dc);
      super.toString(dc.sup());
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, HostUIAbstract.class);
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   private void toStringPrivate(Dctx dc) {

   }

   //#enddebug

}
