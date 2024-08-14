package pasa.cbentley.framework.core.ui.src4.tech;

import pasa.cbentley.core.src4.interfaces.ITech;

public interface ITechEvent extends ITech {

   /**
    * 
    */
   public static final int EVID_00_UNDEFINED        = 0;

   /**
    * Event ID given, 
    */
   public static final int EVID_01_KEYBOARD_PRESS   = 1;

   public static final int EVID_02_KEYBOARD_RELEASE = 2;

   public static final int EVID_11_POINTER_PRESS    = 11;

   public static final int EVID_12_POINTER_RELEASE  = 12;

   public static final int EVID_13_POINTER_MOVE     = 13;

   public static final int EVID_14_POINTER_DRAG     = 14;

   public static final int EVID_15_PAD_PRESS        = 15;

   public static final int EVID_16_PAD_RELEASE      = 16;

   public static final int EVID_20_WHEEL            = 20;

   public static final int EVID_40_CANVAS           = 40;

   public static final int EVID_15_GESTURE          = 15;

}
