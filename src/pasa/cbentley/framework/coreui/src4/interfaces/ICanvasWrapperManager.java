package pasa.cbentley.framework.coreui.src4.interfaces;

import pasa.cbentley.byteobjects.src4.core.ByteObject;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.framework.coreui.src4.engine.WrapperAbstract;

/**
 * Represents the Wrapper Manager.
 * 
 * Could be the Frame that controls 3 cells wrappers
 * @author Charles Bentley
 *
 */
public interface ICanvasWrapperManager extends IStringable {

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
    * <li> 
    * @param tech
    * @return
    */
   public WrapperAbstract createNewWrapper(ByteObject tech);

}
