package pasa.cbentley.framework.core.ui.src4.event;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.framework.core.ui.src4.ctx.CoreUiCtx;
import pasa.cbentley.framework.core.ui.src4.tech.IInput;

/**
 * Mouth Gesture.. coming from a microphone
 * @author Charles Bentley
 *
 */
public class VoiceEvent extends BEvent {

   private String[] ar;

   private String   msg;

   public VoiceEvent(CoreUiCtx fc) {
      super(fc);
      this.type = IInput.TYPE_0_OTHER;
   }

   public String[] getMatches() {
      return ar;
   }

   public String getMessage() {
      return msg;
   }

   public String getUserLineString() {
      return "VoiceEvent " + fc.getUC().getStrU().getString(ar, ",");
   }

   public void setMatches(String[] ar) {
      this.ar = ar;
   }

   public void setMessage(String msg) {
      this.msg = msg;
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, "VoiceEvent");
      dc.appendVarWithSpace("Msg", msg);
      dc.nl();
      dc.append(ar, 0, ar.length, ",");
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "VoiceEvent");
      dc.append(ar, 0, ar.length, ",");
   }
   //#enddebug
}
