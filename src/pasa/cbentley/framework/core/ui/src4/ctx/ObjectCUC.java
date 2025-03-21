package pasa.cbentley.framework.core.ui.src4.ctx;

import pasa.cbentley.core.src4.ctx.ICtx;
import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.core.src4.logging.LogParameters;

public class ObjectCUC implements IStringable {

   protected final CoreUiCtx cuc;

   public ObjectCUC(CoreUiCtx cuc) {
      this.cuc = cuc;
   }

   public ICtx getCtxOwner() {
      return cuc;
   }

   public CoreUiCtx getCUC() {
      return cuc;
   }

   //#mdebug
   public IDLog toDLog() {
      return toStringGetUCtx().toDLog();
   }

   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, ObjectCUC.class, toStringGetLine(30));
      toStringPrivate(dc);
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, ObjectCUC.class);
      toStringPrivate(dc);
   }

   public LogParameters toStringGetLine(Class cl, String method, int value) {
      return toStringGetUCtx().toStringGetLine(cl, method, value);
   }

   public String toStringGetLine(int value) {
      return toStringGetUCtx().toStringGetLine(value);
   }

   public UCtx toStringGetUCtx() {
      return cuc.getUC();
   }

   private void toStringPrivate(Dctx dc) {

   }

   //#enddebug

}
