package pasa.cbentley.framework.core.ui.src4.interfaces;

import pasa.cbentley.framework.core.ui.src4.tech.ITechInputFeedback;

/**
 * 
 * Generic way to give a feedback on a Action.
 * <br>
 * We loosely define an Action here as an event impacting the state of the {@link IAppManager} or {@link IAppli} 
 * <br>
 * An event is processed.
 * If action is taken, the flag {@link ITechInputFeedback#FLAG_01_ACTION_DONE} 
 * is set to true
 * 
 */
public interface IActionFeedback extends ITechInputFeedback {
   /**
    * an Action has worked on object.
    * <br>
    * <br>
    * The {@link IActionFeedback} user will take relevant action based on type and class of object.
    * <br>
    * <br>
    * When object is null, type has special flag meanings.
    * <br>
    * <br>
    * Flags in the type are
    * <li> {@link ITechInputFeedback#FLAG_02_FULL_REPAINT}
    * <li> {@link ITechInputFeedback#FLAG_03_MENU_REPAINT}
    * <li> {@link ITechInputFeedback#FLAG_04_RENEW_LAYOUT}
    * <li> {@link ITechInputFeedback#FLAG_05_USER_MESSAGE}
    * <li> {@link ITechInputFeedback#FLAG_06_DATA_REFRESH}
    * 
    * @param o
    * @param type Flags which must be actionned
    */
   public void actionDone(Object o, int type);

   public void actionDone();
}
