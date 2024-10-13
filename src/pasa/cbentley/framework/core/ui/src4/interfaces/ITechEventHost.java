package pasa.cbentley.framework.core.ui.src4.interfaces;

import pasa.cbentley.core.src4.interfaces.ITech;
import pasa.cbentley.framework.core.ui.src4.ctx.ToStringStaticCoreUi;

/**
 * {@link ToStringStaticCoreUi#toStringEventCanvas(int)}
 * @author Charles Bentley
 *
 */
public interface ITechEventHost extends ITech {

   /**
    * When a Canvas is closed. 
    * 
    * <p>
    * Closing while a key modifier is pressed may activate a special command
    * </p>
    */
   public static final int ACTION_01_CLOSE         = 1;

   /**
    * 
    */
   public static final int ACTION_02_MOVED         = 2;

   /**
    * Gesture that occurs when a resize is made, a move or a fullscreen.
    * <li> Contains X,Y and the difference
    * <li> Width and Height and the diff
    * <li> the Screen
    * <li> Fullscreen On/off
    * 
    * Event sent after the resize has been executed on the canvas
    */
   public static final int ACTION_03_RESIZED       = 3;

   /**
    * Application event when Host Canvas gains platform focus.
    */
   public static final int ACTION_04_FOCUS_GAIN    = 4;

   /**
    * Application event when Host Canvas loses platform focus.
    */
   public static final int ACTION_05_FOCUS_LOSS    = 5;

   /**
    * Application event
    * {@link ICanvasAppli#showNotify()} TODO remove
    */
   public static final int ACTION_06_NOTIFY_SHOW   = 6;

   /**
    * When appli's canvases are all hidden
    */
   public static final int ACTION_07_NOTIFY_HIDE   = 7;

   /**
    * 
    */
   public static final int ACTION_10_DRAG_DROP     = 10;


}
