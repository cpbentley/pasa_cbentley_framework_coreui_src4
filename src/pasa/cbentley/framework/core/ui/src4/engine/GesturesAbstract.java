package pasa.cbentley.framework.core.ui.src4.engine;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.framework.core.ui.src4.ctx.CoreUiCtx;
import pasa.cbentley.framework.core.ui.src4.ctx.ObjectCUC;
import pasa.cbentley.framework.core.ui.src4.interfaces.IHostGestures;
import pasa.cbentley.framework.core.ui.src4.tech.ITechGestures;

public abstract class GesturesAbstract extends ObjectCUC implements IHostGestures, ITechGestures {

   public GesturesAbstract(CoreUiCtx cuc) {
      super(cuc);
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, GesturesAbstract.class, 20);
      toStringPrivate(dc);
      super.toString(dc.sup());
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, GesturesAbstract.class, 20);
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   private void toStringPrivate(Dctx dc) {

   }
   //#enddebug

}
