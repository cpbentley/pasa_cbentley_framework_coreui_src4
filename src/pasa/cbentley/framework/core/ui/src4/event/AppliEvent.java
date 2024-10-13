package pasa.cbentley.framework.core.ui.src4.event;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.framework.core.ui.src4.ctx.CoreUiCtx;
import pasa.cbentley.framework.core.ui.src4.ctx.ToStringStaticCoreUi;
import pasa.cbentley.framework.core.ui.src4.interfaces.ICanvasHost;
import pasa.cbentley.framework.core.ui.src4.interfaces.ITechEventHost;
import pasa.cbentley.framework.core.ui.src4.tech.IInput;

/**
 * Class for {@link BEvent} at the application manager level. 
 * 
 * <p>
 * 
 * </p>
 * Type is {@link IInput#TYPE_6_APPLI}
 * 
 * 
 * Event has the function of activityResult principles used in Android.
 * 
 * <p>
 * Appli events can be used as command triggers.
 * </p>
 * 
 * @author Charles Bentley
 *
 */
public class AppliEvent extends BEvent {

   private int action;

   /**
    * Creates a new {@link AppliEvent}.
    * 
    * @param cuc
    * @param action
    * <li> {@link ITechEventHost#ACTION_01_CLOSE}
    * <li> {@link ITechEventHost#ACTION_2_EXIT} 
    * <li> {@link ITechEventHost#ACTION_3_ENTER} 
    * <li> {@link ITechEventHost#ACTION_04_FOCUS_GAIN} 
    * <li> {@link ITechEventHost#ACTION_05_FOCUS_LOSS}
    * <li> {@link ITechEventHost#ACTION_03_RESIZED}
    * 
    */
   public AppliEvent(CoreUiCtx cuc, int action) {
      super(cuc);
      this.type = TYPE_6_APPLI;
      this.action = action;
   }

   /**
    * Explicit actions made by the user
    * <li> {@link ITechEventHost#ACTION_01_CLOSE} when they use the host UI to close a {@link ICanvasHost}
    * <li> {@link ITechEventHost#ACTION_2_EXIT} when a pointer exits the {@link ICanvasHost}
    * <li> {@link ITechEventHost#ACTION_3_ENTER} when a pointer exits the {@link ICanvasHost}
    * <li> {@link ITechEventHost#ACTION_04_FOCUS_GAIN} when Host UI gives the focus to the {@link ICanvasHost}
    * <li> {@link ITechEventHost#ACTION_05_FOCUS_LOSS} when Host UI take away the focus from the {@link ICanvasHost}
    * <li> {@link ITechEventHost#ACTION_03_RESIZED} when the {@link ICanvasHost} is (user,implicitly) resized, moved, fullscreened
    * 
    */
   public int getAction() {
      return action;
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, AppliEvent.class, 48);
      dc.appendVarWithSpace("Sub", ToStringStaticCoreUi.toStringEventAppli(action));
   }

   public String getUserLineString() {
      return "Appli " + ToStringStaticCoreUi.toStringEventAppli(getAction());
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, AppliEvent.class, 57);
      dc.appendVarWithSpace("Sub", ToStringStaticCoreUi.toStringEventAppli(action));
   }
   //#enddebug
}
