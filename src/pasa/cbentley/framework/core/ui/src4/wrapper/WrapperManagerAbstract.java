package pasa.cbentley.framework.core.ui.src4.wrapper;

import pasa.cbentley.byteobjects.src4.core.ByteObject;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.framework.core.ui.src4.ctx.CoreUiCtx;
import pasa.cbentley.framework.core.ui.src4.ctx.ObjectCUC;
import pasa.cbentley.framework.core.ui.src4.engine.CanvasHostAbstract;
import pasa.cbentley.framework.core.ui.src4.interfaces.IWrapperManager;

public abstract class WrapperManagerAbstract extends ObjectCUC implements IWrapperManager {

   public WrapperManagerAbstract(CoreUiCtx cuc) {
      super(cuc);
   }

   public CanvasHostAbstract createCanvasHost(WrapperAbstract wrapper, ByteObject boCanvasHost) {
      return null;
   }

   public boolean setPosition(WrapperAbstract wrapper, int x, int y) {
      return false;
   }

   public boolean setSize(WrapperAbstract wrapper, int w, int h) {
      return false;
   }

   public void setTitle(WrapperAbstract wrapper, String title) {
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, WrapperManagerAbstract.class, 50);
      toStringPrivate(dc);
      super.toString(dc.sup());
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, WrapperManagerAbstract.class, 50);
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   private void toStringPrivate(Dctx dc) {

   }
   //#enddebug

}
