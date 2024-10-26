package pasa.cbentley.framework.core.ui.src4.tech;

import pasa.cbentley.core.src4.interfaces.IHostFeature;
import pasa.cbentley.framework.core.ui.src4.interfaces.ICanvasHost;
import pasa.cbentley.framework.coredraw.src4.interfaces.ITechHostFeatureDraw;

/**
 * General availability of those features are checked in {@link IHostFeature}
 * 
 * Using
 * {@link IHostFeature#setHostFeatureEnabledFactory(int, boolean)} change factory settings of new Canvas.
 * 
 * {@link IHostFeature#isHostFeatureEnabled(int)} works on main Canvas
 * 
 * @author Charles Bentley
 *
 */
public interface ITechFeaturesCanvas {

   /**
    * Same as {@link ITechHostFeatureDraw#FEAT_02_ANTI_ALIAS}
    */
   public static final int SUP_ID_04_ALIAS              = 4;

   public static final int SUP_ID_15_ACTIVE_RENDERING   = 15;

   public static final int SUP_ID_16_CUSTOM_CURSORS     = 16;

   /**
    * Host allows the Appli to resize and move its container using
    * 
    * <li>{@link ICanvasHost#icSetSize(int, int)}
    * <li>{@link ICanvasHost#icSetXY(int, int)}
    */
   public static final int SUP_ID_26_CANVAS_RESIZE_MOVE = 26;

   public static final int SUP_ID_27_FULLSCREEN         = 27;

   /**
    * Should the canvas behave like a window always on top of other windows.
    * <br>
    * Android Phone / MIDP won't support that
    * 
    * Puts all active canvas as Always on Top.
    * 
    * {@link ITechFeaturesCanvas#}
    */
   public static final int SUP_ID_28_ALWAYS_ON_TOP      = 28;

   public static final int SUP_ID_29_UNDECORATED        = 29;

   /**
    * When true, frame is minimize. when false, normal
    */
   public static final int SUP_ID_30_MINIMIZE           = 30;

   public static final int SUP_ID_31_ACTIVATE_FRONT     = 31;

   /**
    * Goes into full single screen
    */
   public static final int SUP_ID_40_FULLSCREEN         = 40;

   public static final int SUP_ID_41_FULL_WIDTH         = 41;

   public static final int SUP_ID_42_FULL_HEIGHT        = 42;

}
