package pasa.cbentley.framework.coreui.src4.engine;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.interfaces.IExecutor;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.framework.coreui.src4.ctx.CoreUiCtx;

public abstract class AbstractExecutor implements IExecutor {

   protected final CoreUiCtx cuc;

   public AbstractExecutor(CoreUiCtx cuc) {
      this.cuc = cuc;
      
   }
   
   //#mdebug
   public IDLog toDLog() {
      return toStringGetUCtx().toDLog();
   }

   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "AbstractExecutor");
      toStringPrivate(dc);
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   private void toStringPrivate(Dctx dc) {

   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "AbstractExecutor");
      toStringPrivate(dc);
   }

   public UCtx toStringGetUCtx() {
      return cuc.getUCtx();
   }

   //#enddebug
   

}
