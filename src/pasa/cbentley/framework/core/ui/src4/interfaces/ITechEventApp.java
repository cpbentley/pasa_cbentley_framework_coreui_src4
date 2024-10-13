package pasa.cbentley.framework.core.ui.src4.interfaces;

import pasa.cbentley.core.src4.interfaces.ITech;

public interface ITechEventApp extends ITech {

   /**
    * Is this ever used ? When the appli is paused, it is resumed, not started.
    */
   public static final int ACTION_01_APPLI_STARTED = 1;

   /**
    * 
    */
   public static final int ACTION_02_APPLI_PAUSED  = 2;

   /**
    * 
    */
   public static final int ACTION_03_APPLI_RESUMED = 3;

   /**
    * Simple String message in param
    */
   public static final int ACTION_11_MESSAGE       = 11;

}
