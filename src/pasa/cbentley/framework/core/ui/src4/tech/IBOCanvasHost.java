package pasa.cbentley.framework.core.ui.src4.tech;

import pasa.cbentley.byteobjects.src4.core.interfaces.IByteObject;
import pasa.cbentley.framework.core.ui.src4.interfaces.ICanvasHost;

/**
 * Describes the options of the {@link ICanvasHost}.
 * 
 * <br>
 * Type is 
 * 
 * {@link IBOFramePos} defines the position
 * @author Charles Bentley
 *
 */
public interface IBOCanvasHost extends IByteObject {

   public static final int CANVAS_HOST_BASIC_SIZE              = A_OBJECT_BASIC_SIZE + 26;

   /**
    * Enable Gesture Detector
    */
   public static final int CANVAS_HOST_FLAG_1_GESTURE          = 1 << 0;

   /**
    * If the device host supports it, puts the Canvas into another Window/Frame
    */
   public static final int CANVAS_HOST_FLAG_2_WINDOW           = 1 << 1;

   /**
    * 
    */
   public static final int CANVAS_HOST_FLAG_3_OPEN_GL          = 1 << 2;

   /**
    * Size cannot be changed. And will espouse the root canvas size.
    * <br>
    * TODO Frame can be expanded.. but JComponent must scale to fit it.
    */
   public static final int CANVAS_HOST_FLAG_4_SIZE_LINKED_ROOT = 1 << 3;

   public static final int CANVAS_HOST_FLAG_5_CLONED           = 1 << 4;

   public static final int CANVAS_HOST_FLAG_6_AWT_CANVAS       = 1 << 5;

   /**
    * Dynamic Title
    */
   public static final int CANVAS_HOST_FLAG_7_DYNAMIC_TITLE    = 1 << 6;

   /**
    * When set, the Canvas font and content scale to the size of dimension of the canvas
    * <br>
    */
   public static final int CANVAS_HOST_FLAG_8_SCALED           = 1 << 7;

   /**
    * When set, use {@link IBOFramePos} if defined. Otherwise as if flag is not set.
    * When this flag is not set, position of frame will be 
    * 
    */
   public static final int CANVAS_HOST_FLAGX_1_CUSTOM_POS      = 1 << 0;

   public static final int CANVAS_HOST_FLAGX_2_                = 1 << 1;

   public static final int CANVAS_HOST_FLAGX_3_                = 1 << 2;

   public static final int CANVAS_HOST_FLAGX_4_                = 1 << 3;

   public static final int CANVAS_HOST_FLAGX_5_                = 1 << 4;

   public static final int CANVAS_HOST_FLAGX_6_                = 1 << 5;

   public static final int CANVAS_HOST_FLAGX_7_                = 1 << 6;

   public static final int CANVAS_HOST_FLAGX_8_                = 1 << 7;

   public static final int CANVAS_HOST_OFFSET_01_FLAG          = A_OBJECT_BASIC_SIZE;

   public static final int CANVAS_HOST_OFFSET_02_FLAX1         = A_OBJECT_BASIC_SIZE + 1;

   /**
    * When many similar canvas are used, use this ID to differentiate them.
    * 
    * ID for fecthing saved settings like position and size.
    * <br>
    * 1 based ID, set by the creator. Should be unique
    * <br>
    * When 0 {@link ITechWrapper#TCANVAS_ID_0_DEFAULT} , it is undefined
    * 
    */
   public static final int CANVAS_HOST_OFFSET_03_ID2           = A_OBJECT_BASIC_SIZE + 2;

   /**
    * ID of the icon image to use
    */
   public static final int CANVAS_HOST_OFFSET_07_ICON_ID2      = A_OBJECT_BASIC_SIZE + 11;

   /**
    * ID of the localized title
    */
   public static final int CANVAS_HOST_OFFSET_08_TITLE_ID_ID2  = A_OBJECT_BASIC_SIZE + 13;

   /**
    * This is a settings for host configuration
    */
   public static final int CANVAS_HOST_OFFSET_09_NUM_BUFFERS1  = A_OBJECT_BASIC_SIZE + 15;

   /**
    * Type of wrapper around the canvas.
    * <li> {@link ITechWrapper#WRAPPER_TYPE_1_FRAME}
    * <li> {@link ITechWrapper#WRAPPER_TYPE_2_CONTROLLED}
    * 
    */
   public static final int CANVAS_HOST_OFFSET_10_WRAPPER_TYPE1 = A_OBJECT_BASIC_SIZE + 16;

   /**
    * Pointer to the {@link IBOFramePos}.
    * 
    * When zero, none has been defined
    */
   public static final int CANVAS_HOST_OFFSET_14_FRAMEPOS1     = A_OBJECT_BASIC_SIZE + 24;
}
