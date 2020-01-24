package pasa.cbentley.framework.coreui.src4.tech;

import pasa.cbentley.byteobjects.src4.tech.ITechByteObject;
import pasa.cbentley.framework.coreui.src4.event.GestureArea;

public interface ITechUI extends ITechByteObject {
   public static final int DATA_ID_00                            = 0;

   /**
    * ms time out before long event pointer
    */
   public static final int DATA_ID_01_POINTER_LONG_TIMEOUT       = 1;

   /**
    * pixels difference for a double pointer
    */
   public static final int DATA_ID_02_POINTER_NUPLE_SLOP         = 2;

   /**
    * ms time out for a double click
    */
   public static final int DATA_ID_03_POINTER_NUPLE_TIMEOUT      = 3;

   public static final int DATA_ID_04_POINTER_DRAG_SLOP          = 4;

   public static final int DATA_ID_06_POINTER_FAST_TYPE_TIMEOUT  = 6;

   /**
    * Usual maximum number of pointers used. Desktop is 1.
    * Android will be 3.
    */
   public static final int DATA_ID_07_NUM_START_POINTERS         = 7;

   public static final int DATA_ID_09_SLIDE_MIN_AMPLITUDE        = 9;

   public static final int DATA_ID_10_SIMULTANEOUS_TIMEOUT       = 10;

   public static final int DATA_ID_11_FLING_SPEED_MAX            = 11;

   public static final int DATA_ID_12_FLING_SPEED_MIN            = 12;

   public static final int DATA_ID_14_ALLER_RETOUR_SLOP          = 14;

   public static final int DATA_ID_15_ALLER_RETOUR_MIN_AMPLITUDE = 15;

   public static final int DATA_ID_17_NUMBER_OF_SCREENS          = 17;

   /**
    * <li>{@link ITechCanvas#SCREEN_0_TOP_NORMAL}
    * <li>{@link ITechCanvas#SCREEN_1_BOT_UPSIDEDOWN}
    * <li>{@link ITechCanvas#SCREEN_2_LEFT_ROTATED} 
    * <li>{@link ITechCanvas#SCREEN_3_RIGHT_ROTATED}
    * 
    */
   public static final int DATA_ID_18_SCREEN_ORIENTATION         = 18;

   /**
    * Tells whether the host can deal with long paint times and drag events 
    */
   public static final int DATA_ID_19_DRAG_CONTROLLED            = 20;

   public static final int DATA_ID_20_DPI                        = 20;

   public static final int DATA_ID_23_KEYBOARD_TYPE              = 23;

   public static final int DATA_ID_24_KEY_REPEAT_TIMEOUT         = 24;

   public static final int DATA_ID_25_KEY_REPEAT_DELAY           = 25;

   public static final int DATA_ID_26_KEY_FAST_TYPE_TIMEOUT      = 26;

   public static final int DATA_ID_27_KEY_NUPLE_TIMEOUT          = 27;

   /**
    * returns an array of {@link GestureArea} defining the available screens.
    * <li> x = -
    */
   public static final int DATA_ID_OBJ_01_SCREENS                = 1;

   /**
    * Returns a ByteObject of type literal that defines.
    * <br>
    * Return null if irrevelant because only 1 screen is assumed (J2ME).
    */
   public static final int DATA_ID_OBJ_02_SCREENCONFIG           = 2;

   public static final int DATA_ID_STR_01_MANUFACTURER           = 1;

   public static final int DATA_ID_STR_02_IMEI                   = 2;

   public static final int DATA_ID_STR_03_DEVICE_MODEL           = 3;

   public static final int DATA_ID_STR_04_PLATFORM               = 4;

   public static final int DATA_ID_STR_05_HOSTNAME               = 5;

   public static final int DATA_ID_STR_06_ENCODING               = 6;

   public static final int DATA_ID_STR_07_FILE_SEPARATOR         = 7;

   public static final int FEAT_01_DRAG_CONTROLLER               = 1;

   public static final int FEAT_02_ANTI_ALIAS                    = 2;

   public static final int HOST_BASIC_SIZE                       = A_OBJECT_BASIC_SIZE + 30;

   /**
    * Does the framework support drag and drop of files?
    */
   public static final int HOST_FLAG_1_FILEDROP                  = 1;

   public static final int HOST_FLAG_2                           = 1 << 1;

   public static final int HOST_FLAG_3_                          = 1 << 2;

   public static final int HOST_FLAG_4_                          = 1 << 3;

   public static final int HOST_FLAGK_1_LONGPRESS                = 1 << 0;

   public static final int HOST_FLAGK_2_                         = 1 << 1;

   public static final int HOST_FLAGK_3_                         = 1 << 2;

   public static final int HOST_FLAGK_4_                         = 1 << 3;

   public static final int HOST_FLAGK_5_                         = 1 << 4;

   public static final int HOST_FLAGK_6_                         = 1 << 5;

   public static final int HOST_FLAGK_7_                         = 1 << 6;

   public static final int HOST_FLAGK_8_                         = 1 << 7;

   /**
    * Set by host framework as read only.
    * When true, Host is able to handle anti alias
    * <br>
    * Only J2ME is not able.
    */
   public static final int HOST_FLAGX_1_ANTI_ALIAS               = 1 << 0;

   /**
    * Set by the user. Initially set to same as above
    */
   public static final int HOST_FLAGX_2_ANTI_ALIAS_USER          = 1 << 1;

   /**
    * Flag set when Host is capable is generating long press event. The duration is not configurable.
    * 
    */
   public static final int HOST_FLAGY_1_LONGPRESS                = 1 << 0;

   public static final int HOST_FLAGY_2_                         = 1 << 1;

   public static final int HOST_FLAGY_3_                         = 1 << 2;

   public static final int HOST_FLAGY_4_                         = 1 << 3;

   public static final int HOST_FLAGY_5_                         = 1 << 4;

   public static final int HOST_FLAGY_6_                         = 1 << 5;

   public static final int HOST_FLAGY_7_                         = 1 << 6;

   public static final int HOST_FLAGY_8_                         = 1 << 7;

   public static final int HOST_FLAGZ_1_POINTER_EVENTS           = 1;

   public static final int HOST_FLAGZ_2_WHEEL_EVENTS             = 1 << 1;

   public static final int HOST_FLAGZ_3_REPEAT_EVENTS            = 1 << 2;

   public static final int HOST_FLAGZ_4_DRAG_CONTROLLED          = 1 << 3;

   public static final int HOST_FLAGZ_6_OPEN_GL                  = 1 << 5;

   public static final int HOST_OFFSET_01_FLAG                   = A_OBJECT_BASIC_SIZE;

   public static final int HOST_OFFSET_02_FLAGX                  = A_OBJECT_BASIC_SIZE + 1;

   public static final int HOST_OFFSET_03_FLAGZ                  = A_OBJECT_BASIC_SIZE + 2;

   /**
    * Genetics of the Host.
    */
   public static final int HOST_OFFSET_04_FLAGY                  = A_OBJECT_BASIC_SIZE + 3;

   public static final int HOST_OFFSET_05_FLAGK                  = A_OBJECT_BASIC_SIZE + 4;

   /**
    * Config to choosing different canvas types (opengl or ...)
    */
   public static final int HOST_OFFSET_06_CANVAS_TYPE1           = A_OBJECT_BASIC_SIZE + 5;

   public static final int HOST_OFFSET_07_KEYBOARD_TYPE1         = A_OBJECT_BASIC_SIZE + 6;

   public static final int HOST_OFFSET_08_Y_SCREEN_OFFSET2       = A_OBJECT_BASIC_SIZE + 7;

   public static final int HOST_OFFSET_09_DRAG_THRESHOLD2        = A_OBJECT_BASIC_SIZE + 9;

   /**
    * Though Android doesn't use a direct pixel mapping, it uses a handful of quantized Density Independent Pixel values then scales to the actual screen size.
    * 
    *  So the metrics.densityDpi property will be one of the DENSITY_??? constants (120, 160, 213, 240, 320, 480 or 640 dpi)
    */
   public static final int HOST_OFFSET_10_DPI1                   = A_OBJECT_BASIC_SIZE + 11;

   public static final int HOST_OFFSET_11_PINCH_KEY2             = A_OBJECT_BASIC_SIZE + 12;

   /**
    * Maximum number of pointers.
    * Android will maybe allow 5 fingers.
    * PC will be 3 to 4 buttons.
    * This is a safeguard against system errors.
    */
   public static final int HOST_OFFSET_12_MAX_NUM_POINTERS1      = A_OBJECT_BASIC_SIZE + 14;

   public static final int HOST_OFFSET_13_FAST_TYPE_KEY2         = A_OBJECT_BASIC_SIZE + 16;

   public static final int HOST_OFFSET_14_DOUBLE_KEY2            = A_OBJECT_BASIC_SIZE + 18;

   public static final int HOST_OFFSET_15_DOUBLE_POINTER2        = A_OBJECT_BASIC_SIZE + 20;

   public static final int HOST_OFFSET_16_LONG_PRESS_TIMEOUT2    = A_OBJECT_BASIC_SIZE + 22;

   public static final int HOST_OFFSET_17_TOUCH_SLOP2            = A_OBJECT_BASIC_SIZE + 24;

   int                     KB_TYPE_0_PHONE                       = 0;

   int                     KB_TYPE_1_FULL                        = 1;

   int                     KB_TYPE_2_FULL_VIRTUAL                = 2;

   public static final int SUP_03_POINTER_MOTION                 = 1 << 4;

   public static final int SUP_04_WHEEL_EVENTS                   = 1 << 5;

   public static final int SUP_ID_01_KEYBOARD                    = 1;

   /**
    * Host has pointers
    */
   public static final int SUP_ID_02_POINTERS                    = 2;

   /**
    * Host supports OpenGL.
    * 
    */
   public static final int SUP_ID_03_OPEN_GL                     = 3;

   public static final int SUP_ID_04_ALIAS                       = 4;

   public static final int SUP_ID_05_SCREEN_ROTATIONS            = 5;

   /**
    * All but J2ME
    */
   public static final int SUP_ID_10_TRANSPARENT_BACKGROUND      = 10;

   /**
    * Fonts can be changed by name
    */
   public static final int SUP_ID_06_CUSTOM_FONTS                = 6;

   public static final int SUP_ID_07_IMAGE_SCALING               = 7;

   public static final int SUP_ID_09_FONT_CANVAS_SCALE           = 9;

   /**
    * 
    */
   public static final int SUP_ID_15_BLUETOOTH                   = 15;

   public static final int SUP_ID_16_CUSTOM_CURSORS              = 16;

   /**
    * Host has a clipboard service.
    * <br>
    * Convenience Service.
    * <br>
    * <br>
    * Applications must provide a way to enter if clipboard is not there.
    */
   public static final int SUP_ID_20_CLIPBOARD                   = 20;

   /**
    * Host provides multiple screens for Canvas.
    * TODO what if support exists.. but only 1 screen is currently available?
    * <br>
    * <li>{@link IHost#hasFeatureSupport(int)}
    * <li> {@link IHost#getHostInt(int)} returns the number of screens
    * <br>
    */
   public static final int SUP_ID_23_MULTIPLE_SCREENS            = 23;

   public static final int SUP_ID_24_MULTIPLE_WINDOWS            = 24;

   /**
    * Host has ability to modify the screen orientation on its own.
    * <br>
    * Bentley Framework has its own orientation framework when the Host does
    * not provide that functionnality built-in.
    * <br>
    * TODO Also the Host may have disabled this feature. and could possibly
    * be changed by application
    */
   public static final int SUP_ID_25_SCREEN_ORIENTATION          = 25;

   /**
    * Host allows the Appli to resize and move its container
    */
   public static final int SUP_ID_26_CANVAS_RESIZE_MOVE          = 26;

   /**
    * Provides support for fullscreen toggle
    */
   public static final int SUP_ID_27_FULLSCREEN                  = 27;

   /**
    * Should the canvas behave like a window always on top of other windows.
    * <br>
    * Android Phone / MIDP won't support that
    */
   public static final int SUP_ID_28_ALWAYS_ON_TOP               = 28;

   public static final int SUP_ID_29_UNDECORATED                 = 29;

   /**
    * When true, frame is minimize. when false, normal
    */
   public static final int SUP_ID_30_MINIMIZE                    = 30;

   public static final int SUP_ID_31_ACTIVATE_FRONT              = 31;

   public static final int SUP_ID_37_JINPUT                      = 37;

   public static final int SUP_ID_38_GAMEPADS                    = 38;

   public static final int SUP_ID_50_SENSOR_ACC                  = 50;

   public static final int SUP_ID_51_SENSOR_LIGHT                = 51;

   public static final int SUP_ID_52_SENSOR_GPS                  = 52;

}
