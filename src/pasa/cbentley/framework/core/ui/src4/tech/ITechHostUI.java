package pasa.cbentley.framework.core.ui.src4.tech;

import pasa.cbentley.byteobjects.src4.ctx.IBOTypesBOC;
import pasa.cbentley.core.src4.interfaces.ITech;
import pasa.cbentley.framework.core.ui.src4.event.GestureArea;
import pasa.cbentley.framework.coredraw.src4.interfaces.ITechHostDataDraw;

/**
 * 
 * @author Charles Bentley
 *
 */
public interface ITechHostUI extends ITech {


   public static final int FEAT_01_DRAG_CONTROLLER               = 1;

   public static final int FEAT_02_ANTI_ALIAS                    = 2;

   public static final int KB_TYPE_0_PHONE                       = 0;

   public static final int KB_TYPE_1_FULL                        = 1;

   public static final int KB_TYPE_2_FULL_VIRTUAL                = 2;

   /**
    * Host supports OpenGL.
    * 
    */
   public static final int SUP_ID_03_OPEN_GL                     = 3;

   public static final int SUP_ID_01_KEYBOARD                    = 1;

   public static final int SUP_ID_02_POINTERS                    = 2;

   public static final int SUP_ID_05_SCREEN_ROTATIONS            = 5;

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

  
   public static final int SUP_ID_37_JINPUT                      = 37;

   public static final int SUP_ID_38_GAMEPADS                    = 38;

   public static final int SUP_ID_50_SENSOR_ACCELEROMETER        = 50;

   public static final int SUP_ID_51_SENSOR_LIGHT                = 51;

   public static final int SUP_ID_52_SENSOR_GPS                  = 52;

   public static final int SUP_04_WHEEL_EVENTS                   = 1 << 5;

   public static final int SUP_ID_04_ALIAS                       = 4;

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

   public static final int SUP_ID_50_SENSOR_ACC                  = 50;

   int                     SCREEN_0_TOP_NORMAL                   = 0;

   int                     SCREEN_1_BOT_UPSIDEDOWN               = 1;

   int                     SCREEN_2_LEFT_ROTATED                 = 2;

   int                     SCREEN_3_RIGHT_ROTATED                = 3;

}
