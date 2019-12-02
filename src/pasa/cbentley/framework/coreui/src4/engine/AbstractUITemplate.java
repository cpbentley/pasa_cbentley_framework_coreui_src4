package pasa.cbentley.framework.coreui.src4.engine;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.framework.coreui.src4.ctx.CoreUiCtx;

public class AbstractUITemplate implements IStringable {
   
   protected final CoreUiCtx fc;

   protected AbstractUITemplate(CoreUiCtx fc) {
      this.fc = fc;
   }
   
   public CoreUiCtx getFC() {
      return fc;
   }

   //#mdebug
   public String toString() {
      return Dctx.toString(this);
   }

 
   public IDLog toLog() {
      return fc.toDLog();
   }

   public void toString(Dctx dc) {
      dc.root(this, "AbstractUITemplate");
   }

   public UCtx toStringGetUCtx() {
      return fc.getUCtx();
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "AbstractUITemplate");
   }
   //#enddebug

}
