package pasa.cbentley.framework.coreui.src4.interfaces;

import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.framework.coredraw.src4.interfaces.IGraphics;
import pasa.cbentley.framework.coreui.src4.event.BEvent;
import pasa.cbentley.framework.coreui.src4.event.CanvasHostEvent;
import pasa.cbentley.framework.coreui.src4.event.DeviceEvent;
import pasa.cbentley.framework.coreui.src4.event.GestureEvent;
import pasa.cbentley.framework.coreui.src4.event.SenseEvent;

/**
 * Implementation of this interface belongs to the Bentley framework.
 * 
 * This interface defines Inbound methods.  <b>Host</b> uses those methods to communicate with -> <b>Bentley</b>
 * <br>
 * <li> Outside events are send with {@link ICanvasAppli#event(BEvent)}
 * <ol>
 * <li> {@link DeviceEvent} from keyboards, pointers, gamepads
 * <li> {@link SenseEvent} continuous sensing values from sensors
 * <li> {@link GestureEvent} Gesture detected by the Host.
 * </ol>
 * <br>
 * See {@link ICanvasHost} for outbound methods for Bentley->Outside World
 * <br>
 * Object to be displayed in the {@link ICanvasHost}
 * @author Charles Bentley
 *
 */
public interface ICanvasAppli extends IStringable {

   /**
    * The implementation calls showNotify() immediately prior to this Canvas being made visible on the display.
    */
   public void showNotify();

   /**
    * The implementation calls hideNotify() shortly after the Canvas has been removed from the display.
    */
   public void hideNotify();

   /**
    * See {@link Displayable#getWidth()}
    * @return
    */
   public int getHeight();

   /**
    * 
    * @return
    */
   public String getTitle();
   
   /**
    * Path to the icon, null if no icon
    * @return
    */
   public String getIcon();

   /**
    * Return the width of the CanvasAppli.
    * <br>
    * <li>host will decide it
    * <li>the state of the appli will.
    * <br>
    * The Host generates {@link CanvasHostEvent} for notify to size changes.
    * <br>
    * @return
    */
   public int getWidth();

   /**
    * Posts an event to this Canvas.
    * <br>
    * This will be called by the Host when it detects key, mouse events etc.
    * @param g
    */
   public void event(BEvent g);

   /**
    * False if the Framework is sure that the {@link ICanvasAppli} is not being shown.
    * <br>
    * true otherwise
    */
   public boolean isShown();

   /**
    * Paints the canvas on {@link IGraphics}
    * @param g
    */
   public void paint(IGraphics g);

   /**
    * The Host implementation of the {@link ICanvasAppli}.
    * @return possibly null
    */
   public ICanvasHost getICanvas();

}
