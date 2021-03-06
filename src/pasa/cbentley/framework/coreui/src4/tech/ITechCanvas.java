package pasa.cbentley.framework.coreui.src4.tech;

import pasa.cbentley.byteobjects.src4.tech.ITechByteObject;
import pasa.cbentley.framework.coreui.src4.ctx.IBOTypesFrameworkUI;
import pasa.cbentley.framework.coreui.src4.interfaces.ICanvasHost;

/**
 * Describes the options of the {@link ICanvasHost}.
 * 
 * <br>
 * Type is 
 * 
 * {@link IFramePos} defines the position
 * @author Charles Bentley
 *
 */
public interface ITechCanvas extends ITechByteObject {

   public static final int SCREEN_0_TOP_NORMAL               = 0;

   public static final int SCREEN_1_BOT_UPSIDEDOWN           = 1;

   public static final int SCREEN_2_LEFT_ROTATED             = 2;

   public static final int SCREEN_3_RIGHT_ROTATED            = 3;

   public static final int TCANVAS_BASIC_SIZE                = A_OBJECT_BASIC_SIZE + 26;

   /**
    * Enable Gesture Detector
    */
   public static final int TCANVAS_FLAG_1_GESTURE            = 1 << 0;

   /**
    * If the device host supports it, puts the Canvas into another Window/Frame
    */
   public static final int TCANVAS_FLAG_2_WINDOW             = 1 << 1;

   /**
    * 
    */
   public static final int TCANVAS_FLAG_3_OPEN_GL            = 1 << 2;

   /**
    * Size cannot be changed. And will espouse the root canvas size.
    * <br>
    * TODO Frame can be expanded.. but JComponent must scale to fit it.
    */
   public static final int TCANVAS_FLAG_4_SIZE_LINKED_ROOT   = 1 << 3;

   public static final int TCANVAS_FLAG_5_CLONED             = 1 << 4;

   public static final int TCANVAS_FLAG_6_AWT_CANVAS         = 1 << 5;

   /**
    * When set, the Canvas font and content scale to the size of dimension of the canvas
    * <br>
    */
   public static final int TCANVAS_FLAG_6_SCALED             = 1 << 5;

   /**
    * Dynamic Title
    */
   public static final int TCANVAS_FLAG_7_DYNAMIC_TITLE      = 1 << 6;

   public static final int TCANVAS_FLAG_7_SCALED             = 1 << 6;

   public static final int TCANVAS_ID_0_DEFAULT              = 0;

   public static final int TCANVAS_ID_1_ROOT                 = 1;

   public static final int TCANVAS_OFFSET_01_FLAG            = A_OBJECT_BASIC_SIZE;

   /**
    * Type of wrapper around the canvas.
    * <li> {@link ITechCanvas#TCANVAS_TYPE_1_FRAME}
    * <li> {@link ITechCanvas#TCANVAS_TYPE_2_CONTROLLED}
    * 
    */
   public static final int TCANVAS_OFFSET_02_TYPE1           = A_OBJECT_BASIC_SIZE + 1;

   /**
    * ID for fecthing saved settings like position and size.
    * <br>
    * <br>
    * When 0, it is undefined
    */
   public static final int TCANVAS_OFFSET_03_ID2             = A_OBJECT_BASIC_SIZE + 2;

   /**
    * <li>{@link ITechCanvas#SCREEN_0_TOP_NORMAL}
    * <li>{@link ITechCanvas#SCREEN_1_BOT_UPSIDEDOWN}
    * <li>{@link ITechCanvas#SCREEN_2_LEFT_ROTATED}
    * <li>{@link ITechCanvas#SCREEN_3_RIGHT_ROTATED}
    * 
    */
   public static final int TCANVAS_OFFSET_03_SCREEN_MODE1    = A_OBJECT_BASIC_SIZE + 4;

   public static final int TCANVAS_OFFSET_04_DEBUG_FLAGS1    = A_OBJECT_BASIC_SIZE + 5;

   public static final int TCANVAS_OFFSET_05_BG_COLOR4       = A_OBJECT_BASIC_SIZE + 6;

   /**
    * Threading mode for the controlling canvas events 
    * 
    * <br>
    * <li>{@link ICtrl#THREADING_0_ONE_TO_RULE_ALL}
    * <li>{@link ICtrl#THREADING_1_UI_UPDATERENDERING}
    * <li>{@link ICtrl#THREADING_2_UIUPDATE_RENDERING}
    * <li>{@link ICtrl#THREADING_3_THREE_SEPARATE}
    * 
    */
   public static final int TCANVAS_OFFSET_06_THREADING_MODE1 = A_OBJECT_BASIC_SIZE + 10;

   /**
    * ID of the icon image to use
    */
   public static final int TCANVAS_OFFSET_07_ICON_ID2        = A_OBJECT_BASIC_SIZE + 11;

   /**
    * ID of the localized title
    */
   public static final int TCANVAS_OFFSET_08_TITLE_ID_ID2    = A_OBJECT_BASIC_SIZE + 13;

   /**
    * 
    */
   public static final int TCANVAS_OFFSET_09_NUM_BUFFERS1    = A_OBJECT_BASIC_SIZE + 15;

   /**
    * Pointer to the {@link IFramePos}
    */
   public static final int TCANVAS_OFFSET_14_FRAMEPOS2       = A_OBJECT_BASIC_SIZE + 24;

   public static final int TCANVAS_TYPE                      = IBOTypesFrameworkUI.TYPE_5_CANVAS_TECH;

   /**
    * The wrapper decided by the launcher
    */
   public static final int TCANVAS_TYPE_0_DEFAULT            = 0;

   public static final int TCANVAS_TYPE_1_FRAME              = 0;

   public static final int TCANVAS_TYPE_2_CONTROLLED         = 1;
}
