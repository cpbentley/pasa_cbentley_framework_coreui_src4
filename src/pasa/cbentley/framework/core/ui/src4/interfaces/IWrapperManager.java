package pasa.cbentley.framework.core.ui.src4.interfaces;

import pasa.cbentley.byteobjects.src4.core.ByteObject;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.framework.core.ui.src4.ctx.CoreUiCtx;
import pasa.cbentley.framework.core.ui.src4.engine.CanvasHostAbstract;
import pasa.cbentley.framework.core.ui.src4.tech.IBOCanvasHost;
import pasa.cbentley.framework.core.ui.src4.wrapper.WrapperAbstract;

/**
 * Class that knows how to handle the canvas wrappers
 * 
 * Could be the Frame that controls 3 cells wrappers
 * <p>
 * Create {@link WrapperAbstract} implementations from {@link IBOCanvasHost}
 * <li> {@link IWrapperManager#createNewWrapper(ByteObject)}
 * <li> {@link IWrapperManager#createCanvasHost(WrapperAbstract, ByteObject)}
 * 
 * </p>
 * 
 * <p>
 * Setters. Those methods are called
 * 
 * <li> {@link IWrapperManager#setTitle(WrapperAbstract, String)}
 * <li> {@link IWrapperManager#setPosition(WrapperAbstract, int, int)}
 * <li> {@link IWrapperManager#setSize(WrapperAbstract, int, int)}
 * <br>
 * A Swing Panel does not know how it is layouted. Can it be resized or positioned ? Only the {@link IWrapperManager} knows.
 * In a cardlayout, Manager may agree to resize or not, depending on number of cards.
   In a Tab Pane Layout, the title is not on the frame, but on the card title.
 * </p>
 * 
 * 
 * @see WrapperAbstract
 * 
 * @author Charles Bentley
 *
 */
public interface IWrapperManager extends IStringable {

   /**
    * When the wrapper is not able to set its title, the wrapper delegates the job to its manager.
    * 
    * @param wrapper
    * @param title
    */
   public void setTitle(WrapperAbstract wrapper, String title);

   /**
    * Called by the wrapper when it does not know how to set its size because of its nature.
    * <p>
    * <b>Explanation</b>: <br>
    * A Swing Panel does not know how it is layouted. Can it be resized ? Only its {@link IWrapperManager} knows.
    * </p>
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
    * Create wrapper according to {@link IBOCanvasHost} and its {@link IBOCanvasHost#CANVAS_HOST_OFFSET_10_WRAPPER_TYPE1} .
    * 
    * <p>
    * There is not specific IBOWrapper in this current version.
    * Why not ? Because too little use.
    * </p>
    * 
    * <p>
    * Depending on the platform interpretation of {@link IBOCanvasHost},
    * it will create a Canvas within its own window, a panel, a new tab inside tabbed pane.
    * etc etc. 
    * </p>
    * 
    * <p>
    * Some managers may even return an already created instance. A TabbedPane where each tab is a container for an Appli.
    * </p>
    * @param boCanvasHost {@link IBOCanvasHost}
    * @return
    */
   public WrapperAbstract createNewWrapper(ByteObject boCanvasHost);

   /**
    * Gives the opportuinity to the {@link IWrapperManager} to create the {@link CanvasHostAbstract}.
    * 
    * <p>
    * Otherwise, the platform {@link CoreUiCtx} creates the default canvas implementation.
    * </p>
    * @param wrapper
    * @param boCanvasHost
    * @return
    */
   public CanvasHostAbstract createCanvasHost(WrapperAbstract wrapper, ByteObject boCanvasHost);

}
