package pasa.cbentley.framework.core.ui.src4.tech;

import pasa.cbentley.byteobjects.src4.ctx.IBOTypesBOC;
import pasa.cbentley.core.src4.interfaces.IHostData;
import pasa.cbentley.framework.core.ui.src4.event.GestureArea;
import pasa.cbentley.framework.coredraw.src4.interfaces.ITechHostDataDraw;
import pasa.cbentley.framework.coredraw.src4.interfaces.ITechHostFeatureDraw;

public interface ITechHostFeatureDrawUI extends ITechHostFeatureDraw {



   public static final int FEAT_01_DRAG_CONTROLLER               = 1;

 
   public static final int SUP_ID_01_KEYBOARD                    = 1;

   public static final int SUP_ID_02_POINTERS                    = 2;

   public static final int SUP_ID_05_SCREEN_ROTATIONS            = 5;

   /**
    * 
    */
   public static final int SUP_ID_15_BLUETOOTH                   = 15;



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
    * Same as {@link ITechFeaturesCanvas#SUP_ID_27_FULLSCREEN}
    */
   public static final int SUP_ID_27_FULLSCREEN                  = 27;

   /**
    * Should the canvas behave like a window always on top of other windows.
    * <br>
    * Android Phone / MIDP won't support that
    * 
    * Puts all active canvas as Always on Top.
    * 
    * {@link ITechFeaturesCanvas#}
    */
   public static final int SUP_ID_28_ALWAYS_ON_TOP               = 28;

   /**
    * Host Canvas support
    * 
    */
   public static final int SUP_ID_29_UNDECORATED                 = 29;

   /**
    * When true, frame is minimize. when false, normal
    */
   public static final int SUP_ID_30_MINIMIZE                    = 30;

   public static final int SUP_ID_31_ACTIVATE_FRONT              = 31;


   public static final int SUP_ID_50_SENSOR_ACCELEROMETER        = 50;

   public static final int SUP_ID_51_SENSOR_LIGHT                = 51;

   public static final int SUP_ID_52_SENSOR_GPS                  = 52;

   public static final int SUP_04_WHEEL_EVENTS                   = 1 << 5;




   public static final int SUP_ID_50_SENSOR_ACC                  = 50;
}
