package pasa.cbentley.framework.coreui.src4.event;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.framework.coreui.src4.ctx.CoreUiCtx;
import pasa.cbentley.framework.coreui.src4.interfaces.BCodes;
import pasa.cbentley.framework.coreui.src4.interfaces.ICanvasHost;
import pasa.cbentley.framework.coreui.src4.interfaces.IHostEvents;
import pasa.cbentley.framework.coreui.src4.tech.IInput;

/**
 * Event such as
 * 
 * {@link IInput#TYPE_6_APPLI}
 * <br>
 * Event has the function of activityResult.
 * 
 * Appli events can be used as command triggers.
 * <br>
 * 
 * @author Charles Bentley
 *
 */
public class AppliEvent extends BEvent {

   private int mode;

   public AppliEvent(CoreUiCtx fc, int mode) {
      super(fc);
      this.type = TYPE_6_APPLI;
      this.mode = mode;
   }
   /**
    * Explicit actions made by the user
    * <li> {@link IHostEvents#ACTION_1_CLOSE} when they use the host UI to close a {@link ICanvasHost}
    * <li> {@link IHostEvents#ACTION_2_EXIT} when a pointer exits the {@link ICanvasHost}
    * <li> {@link IHostEvents#ACTION_3_ENTER} when a pointer exits the {@link ICanvasHost}
    * <li> {@link IHostEvents#ACTION_4_FOCUS_GAIN} when Host UI gives the focus to the {@link ICanvasHost}
    * <li> {@link IHostEvents#ACTION_5_FOCUS_LOSS} when Host UI take away the focus from the {@link ICanvasHost}
    * <li> {@link IHostEvents#ACTION_3_RESIZED} when the {@link ICanvasHost} is (user,implicitly) resized, moved, fullscreened
    * 
    */
   public int getAction() {
      return mode;
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, "AppliEvent");
      dc.appendVarWithSpace("Sub", BCodes.getStringAppliAction(mode));
   }

   public String getUserLineString() {
      return "Appli " + BCodes.getStringAppliAction(getAction());
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "AppliEvent");
      dc.appendVarWithSpace("Sub", BCodes.getStringAppliAction(mode));
   }
   //#enddebug
}
