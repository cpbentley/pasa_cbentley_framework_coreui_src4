package pasa.cbentley.framework.core.ui.src4.exec;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.framework.core.ui.src4.ctx.CoreUiCtx;
import pasa.cbentley.framework.core.ui.src4.ctx.ObjectCUC;
import pasa.cbentley.framework.core.ui.src4.event.BEvent;
import pasa.cbentley.framework.core.ui.src4.event.RepeatEvent;
import pasa.cbentley.framework.core.ui.src4.tech.ITechInput;

public abstract class OutputState extends ObjectCUC implements ITechInput {

   public OutputState(CoreUiCtx cuc) {
      super(cuc);
   }

   /**
    * Review all states and process it
    */
   public void processOutputState() {

   }

   public void queuePost(final BEvent de) {

   }

   public void requestRepetition(RepeatEvent eventRepeat) {

   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, OutputState.class, 400);
      toStringPrivate(dc);
      super.toString(dc.sup());
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, OutputState.class, 400);
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   private void toStringPrivate(Dctx dc) {

   }
   //#enddebug

}
