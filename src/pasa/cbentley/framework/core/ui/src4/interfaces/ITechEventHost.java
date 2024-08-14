package pasa.cbentley.framework.core.ui.src4.interfaces;

import pasa.cbentley.core.src4.interfaces.ITech;
import pasa.cbentley.framework.core.ui.src4.ctx.ToStringStaticCoreUi;

/**
 * {@link ToStringStaticCoreUi#toStringAppliAction(int)}
 * @author Charles Bentley
 *
 */
public interface ITechEventHost extends ITech {

   /**
    * When a Canvas is closed. Closing while a key modifier is
    * press when activate a special command
    */
   public static final int ACTION_1_CLOSE         = 1;

   /**
    * 
    */
   public static final int ACTION_2_MOVED         = 2;

   /**
    * Gesture that occurs when a resize is made, a move or a fullscreen.
    * <li> Contains X,Y and the difference
    * <li> Width and Height and the diff
    * <li> the Screen
    * <li> Fullscreen On/off
    * 
    * Event sent after the resize has been executed on the canvas
    */
   public static final int ACTION_3_RESIZED       = 3;

   /**
    * Application event
    */
   public static final int ACTION_4_FOCUS_GAIN    = 4;

   public static final int ACTION_5_FOCUS_LOSS    = 5;

   /**
    * Application event
    * {@link ICanvasAppli#showNotify()} TODO remove
    */
   public static final int ACTION_6_NOTIFY_SHOW   = 6;

   /**
    * When appli's canvases are all hidden
    */
   public static final int ACTION_7_NOTIFY_HIDE   = 7;

   public static final int ACTION_10_DRAG_DROP    = 10;

   public static final int ACTION_8_APPLI_PAUSED  = 8;

   public static final int ACTION_9_APPLI_STARTED = 9;

}
