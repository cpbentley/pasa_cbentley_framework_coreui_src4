package pasa.cbentley.framework.core.ui.src4.interfaces;

public interface ITechSenses {
   
   /**
    * 
    */
   public static final int SENSOR_TYPE_04_AXIS      = 4;

   /**
    * Shake of the phone
    */
   public static final int GESTURE_TYPE_05_SHAKE    = 5;

   /**
    * The device is moved and accelerometer detects it
    */
   public static final int GESTURE_TYPE_07_MOVE     = 7;

   public static final int GESTURE_TYPE_08_LIGHT    = 8;

   /**
    * Gesture made with the tongue
    */
   public static final int GESTURE_TYPE_09_VOICE    = 9;

   /**
    * Longitude/Latitude location services.
    */
   public static final int GESTURE_TYPE_10_LOCATION = 10;

   public static final int GESTURE_TYPE_11_COMPASS  = 11;

   public static final int GESTURE_TYPE_12_DEVICE   = 12;

   public static final int DEVICE_CONNECTED         = 1;

   public static final int DEVICE_DISCONNECTED      = 2;

   /**
    * Event subtype raw send 
    */
   public static final int LIGHT_0_RAW              = 0;

   /**
    * Gesture sends this event when lumen is near 0.
    */
   public static final int LIGHT_1_OBSCURITY        = 1;

   /**
    * Sensor sends an event when darkness is detected relative to the 
    */
   public static final int LIGHT_2_DARK             = 2;

}
