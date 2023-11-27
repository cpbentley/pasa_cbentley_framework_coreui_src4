package pasa.cbentley.framework.coreui.src4.event;

import pasa.cbentley.core.src4.interfaces.ITech;

public interface ITechEventKey extends ITech {

   /**
    * Fire continuously
    */
   public static final int KEY_TYPE_0_FIRE            = 0;

   /**
    * Key cancels monitoring. a Cancel Event is fired
    */
   public static final int KEY_TYPE_1_CANCEL          = 1;

   /**
    * Fire and cancel the {@link GesturePointer}.
    */
   public static final int KEY_TYPE_2_FIRE_AND_CANCEL = 2;

   /**
    * Keep the {@link GesturePointer} active. the fire will be muted
    * <br>
    * Flag
    */
   public static final int KEY_TYPE_3_MUTE_FIRE       = 3;

   /**
    * Describes this pattern to enable matching the event that started a {@link GesturePointer}.
    */
   public static final int KEY_TYPE_4_ACTIVATE_FIRE   = 4;

   /**
    * Flag telling button must be the same
    * <br>
    * Group of buttons
    */
   int BUTTON_FLAG_1_SAME  = 1;

   int BUTTON_FLAG_2_ANY   = 1;

   int DEVICE_FLAG_1_SAME  = 1;

   int DEVICE_FLAG_2_ANY   = 1;

   /**
    * Usually Type and ID goes together. How to create an EventKey for a group
    * of device IDs?
    */
   int ID_TYPE_1_SAME      = 2;

   int ID_TYPE_2_ANY       = 2;

   /**
    * Activates when same button but inverse mode. Press <> Release
    */
   int EVENT_KEY_DEVICE_MODE_TYPE_0_INVERSE = 0;

   /**
    * Produces a match when same button and same mode as the {@link DeviceEvent}
    */
   int EVENT_KEY_DEVICE_MODE_TYPE_1_SAME    = 1;

   /**
    * Any mode for same device/id/user/button
    */
   int EVENT_KEY_DEVICE_MODE_TYPE_2_ANY     = 2;

}
