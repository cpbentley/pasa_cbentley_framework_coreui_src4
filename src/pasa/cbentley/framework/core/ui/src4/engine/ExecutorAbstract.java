package pasa.cbentley.framework.core.ui.src4.engine;

import pasa.cbentley.core.src4.interfaces.IExecutor;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.framework.core.ui.src4.ctx.CoreUiCtx;
import pasa.cbentley.framework.core.ui.src4.ctx.ObjectCUC;

public abstract class ExecutorAbstract extends ObjectCUC implements IExecutor {

   public ExecutorAbstract(CoreUiCtx cuc) {
      super(cuc);
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, ExecutorAbstract.class, 21);
      toStringPrivate(dc);
      super.toString(dc.sup());
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, ExecutorAbstract.class, 21);
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   private void toStringPrivate(Dctx dc) {

   }
   //#enddebug

}
