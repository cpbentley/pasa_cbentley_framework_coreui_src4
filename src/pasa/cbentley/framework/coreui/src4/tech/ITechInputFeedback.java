package pasa.cbentley.framework.coreui.src4.tech;

public interface ITechInputFeedback {

   /**
    * Flags that stops the processing of the event.
    */
   int FLAG_01_ACTION_DONE       = 1 << 0;
   /**
    * 
    */
   int FLAG_02_FULL_REPAINT      = 1 << 1;
   /**
    * 
    */
   int FLAG_03_MENU_REPAINT      = 1 << 2;
   /**
    * Force the GUI to do a full layout before the repaint call.
    * <br>
    * Renew Layout includes a {@link ITechInputFeedback#FLAG_02_FULL_REPAINT} 
    */
   int FLAG_04_RENEW_LAYOUT      = 1 << 3;
   /**
    * Cmd generated one or several user messages.
    */
   int FLAG_05_USER_MESSAGE    = 1 << 4;
   /**
    * Cmd requests that app wide data has changed. Display language was changed.
    * <br>
    * 
    */
   int FLAG_06_DATA_REFRESH      = 1 << 5;
   int FLAG_07_OBJECT_REPAINT    = 1 << 6;
   /**
    * Cmd requests that app wide data has changed. Display language was changed.
    * <br>
    * 
    */
   int FLAG_08_CMD_LABEL_REFRESH = 1 << 7;
   /**
    * When an lock is used and should be notified when painting is finished.
    * <br>
    * In a business thread, stop the thread until the screen result has been painted.
    * <br>
    */
   int FLAG_15_SCREEN_LOCK       = 1 << 14;

}
