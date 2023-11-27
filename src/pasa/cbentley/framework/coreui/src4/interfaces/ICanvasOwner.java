package pasa.cbentley.framework.coreui.src4.interfaces;

import pasa.cbentley.byteobjects.src4.core.ByteObject;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.framework.coreui.src4.engine.WrapperAbstract;
import pasa.cbentley.framework.coreui.src4.tech.ITechCanvasHost;

/**
 * Class that knows how to handle the canvas wrappers
 * 
 * Could be the Frame that controls 3 cells wrappers
 * 
 * 
 * 
 * @author Charles Bentley
 *
 */
public interface ICanvasOwner extends IStringable {

   /**
    * When the wrapper is not able to set its title, the wrapper delegates the job to its manager.
    * @param wrapper
    * @param title
    */
   public void setTitle(WrapperAbstract wrapper, String title);

   /**
    * Tries to set the given size to the wrapper
    * @param wrapper
    * @param w
    * @param h
    * @return
    */
   public boolean setSize(WrapperAbstract wrapper, int w, int h);

   /**
    * 
    * @param wrapper
    * @param x
    * @param y
    * @return
    */
   public boolean setPosition(WrapperAbstract wrapper, int x, int y);

   /**
    * Create wrapper according to tech.
    * 
    * Depending on the implementation interpretation of {@link ITechCanvasHost},
    * it will create a Canvas within its own window, a panel, a new tab inside tabbed pane.
    * etc etc. 
    * <li> 
    * @param tech {@link ITechCanvasHost}
    * @return
    */
   public WrapperAbstract createNewWrapper(ByteObject tech);

}