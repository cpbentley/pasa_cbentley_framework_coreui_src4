package pasa.cbentley.framework.coreui.src4.interfaces;

import pasa.cbentley.byteobjects.src4.core.ByteObject;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.framework.coreui.src4.engine.WrapperAbstract;
import pasa.cbentley.framework.coreui.src4.tech.IBOCanvasHost;

/**
 * Class that knows how to handle the canvas wrappers
 * 
 * Could be the Frame that controls 3 cells wrappers
 * <p>
 * 
 * Create {@link WrapperAbstract} implementations from {@link IBOCanvasHost}
 * 
 * <li> {@link IWrapperManager#createNewWrapper(ByteObject)}
 * 
 * </p>
 * @author Charles Bentley
 *
 */
public interface IWrapperManager extends IStringable {

   /**
    * When the wrapper is not able to set its title, the wrapper delegates the job to its manager.
    * @param wrapper
    * @param title
    */
   public void setTitle(WrapperAbstract wrapper, String title);

   /**
    * Called by the wrapper when it does not know how to set its size because of its nature.
    * @param wrapper
    * @param w
    * @param h
    * @return true when size was set, false if wrapper cannot modify its size due to its nature
    */
   public boolean setSize(WrapperAbstract wrapper, int w, int h);

   /**
    * Called by the Wrapper when it does not know how to set its position because of its nature.
    * <p>
    * For example, a Panel wrapper inside a Tabbed Pane.
    * </p>
    * @param wrapper
    * @param x
    * @param y
    * @return true when position was set, false if wrapper cannot modify its position due to its nature
    */
   public boolean setPosition(WrapperAbstract wrapper, int x, int y);

   /**
    * Create wrapper according to tech.
    * 
    * Depending on the implementation interpretation of {@link IBOCanvasHost},
    * it will create a Canvas within its own window, a panel, a new tab inside tabbed pane.
    * etc etc. 
    * <li> 
    * @param tech {@link IBOCanvasHost}
    * @return
    */
   public WrapperAbstract createNewWrapper(ByteObject tech);

}
