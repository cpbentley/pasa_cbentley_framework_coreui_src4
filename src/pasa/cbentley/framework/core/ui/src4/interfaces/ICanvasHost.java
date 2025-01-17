package pasa.cbentley.framework.core.ui.src4.interfaces;

import pasa.cbentley.byteobjects.src4.core.ByteObject;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.core.src4.stator.IStatorable;
import pasa.cbentley.framework.core.ui.src4.tech.IBOCanvasHost;
import pasa.cbentley.framework.core.ui.src4.tech.ITechFeaturesCanvas;
import pasa.cbentley.framework.coredraw.src4.interfaces.IGraphics;

/**
 * This interface defines Outbound methods used by the <b>Bentley</b> framework to communicate with the <b>Outside World</b>
 * <br>
 * <br>
 * See {@link ICanvasAppli} for inbound methods for Outside World->Bentley
 * <br>
 * Defines the Canvas container for a Modlet.
 * Interface used by Bridge.base and which has to be provided by each specific platform.
 * <li>Swing
 * <li>SWT
 * <li>JPython
 * <br>
 * <br>
 * Wierd method names are used to prevent clashes with.
 * If names are colliding, instead of IsA relationship, use UseA.
 * <br>
 * Design decision, to use a name that reminds the reader that it is from the ICanvas interface.
 * <br>
 * <br>
 * 
 * @author Charles-Philip Bentley
 *
 */
public interface ICanvasHost extends IStringable, IStatorable {
   /**
    * Ask the Canvas or its wrapper to hide.
    * <br><br>
    * Q:Does this remove any reference in Framework Managers ?<br>
    * A: TODO<br>
    * 
    */
   public void canvasHide();

   /**
    * Ask the Canvas and its wrapper to show it
    */
   public void canvasShow();

   /**
    * 
    */
   public void flushGraphics();

   /**
    * {@link IBOCanvasHost} 
    * @return not null
    */
   public ByteObject getBOCanvasHost();

   /**
    * 
    * @return
    */
   public ICanvasAppli getCanvasAppli();

   /**
    * 
    * @return
    */
   public IGraphics getGraphics();

   /**
    * The height of the Host Canvas.
    * @return
    */
   public int getICHeight();

   /**
    * The width of the Host Canvas
    * @return
    */
   public int getICWidth();

   /**
    * The X position of the Host Canvas in the host referential.
    * @return
    */
   public int getICX();

   /**
    * The Y position of the Host Canvas in the host referential.
    * @return
    */
   public int getICY();

   public int getScreenX(int x);

   /**
    * Maps the y into the screen coordinate
    * @param y
    * @return
    */
   public int getScreenY(int y);

   public void icRepaint();

   public void icRepaint(int x, int y, int w, int h);

   /**
    * Tries to change the size. Only possible when Canvas is not contained
    * @param w
    * @param h
    */
   public void icSetSize(int w, int h);

   /**
    * Positions the host canvas at x,y.
    * <br>
    * Relative to host axis.
    * @param x
    * @param y
    */
   public void icSetXY(int x, int y);

   /**
    * Query the state of a canvas feature such as
    * <li> {@link ITechHost#SUP_ID_04_ALIAS}
    * <li> {@link ITechHost#SUP_ID_27_FULLSCREEN}
    * <li> {@link ITechHost#SUP_ID_28_ALWAYS_ON_TOP}
    * 
    * @param feature
    * @return
    */
   public boolean isCanvasFeatureEnabled(int feature);

   /**
    * True when Canvas supports the flag.
    * by default returns false if the Framework cannot act
    * <br>
    * <li> {@link ITechHost#SUP_ID_26_CANVAS_RESIZE_MOVE} true when the host allows this canvas to resize itself and move
    * <li> {@link ITechHost#SUP_ID_25_SCREEN_ORIENTATION} true when the host allows this canvas to rotate
    * 
    * @param feature
    * @return
    */
   public boolean isCanvasFeatureSupported(int feature);

   /**
    * True when CanvasHost is currently visible to the user.
    * <br>
    * @return
    */
   public boolean isShown();

   /**
    * 
    * @param canvasAppli
    */
   public void setCanvasAppli(ICanvasAppli canvasAppli);

   /**
    * Activate/Deactivate the canvas
    * @param b
    */
   public void setCanvasAppliActive(boolean b);

   /**
    * 
    * Activates a canvas feature. a System flag of the {@link ICanvasHost}.
    * <li> {@link ITechFeaturesCanvas#SUP_ID_16_CUSTOM_CURSORS}
    * 
    * The feature is set locally on the Canvas, there are no implications for other Canvases or future Canvases.
    * 
    * @param feature
    * @param mode
    * @return TODO
    */
   public boolean setCanvasFeature(int feature, boolean mode);

   /**
    * Communicates object to canvas feature
    * @param feature
    * @param mode
    * @return
    */
   public boolean setCanvasFeature(int feature, Object mode);

   /**
    * Sets/Resets the canvas to its default position on the screen.
    * 
    * Values are taken from the hard coded configuration.
    * 
    * <p>
    * The result of this call depends on the {@link ICanvasHost} nature.
    * Some Canvas cannot change their size and position from the inside
    * </p>
    */
   public void setStartPositionAndSize();

   /**
    * Sets the Icon for the Canvas.
    * <br>
    * The display of the icon will depend on the wrapper hosting the {@link ICanvasHost}.
    * <br>
    * <li>A Frame/Window will display it
    * <li>On a Tabbed pane, the icon will be displayed with the Title in the tab.
    * <li>Embedded Canvas might not show the icon
    * @param string Path to icon in application package
    */
   public void setIcon(String string);

   /**
    * Title of panel. If constrained in a Tab Panned, Title can be used as tab text.
    * When inside a Frame/Window, Title is the name of the frame.
    * @param string
    */
   public void setTitle(String string);

   /**
    * 
    */
   public void titleIconComesticUpdate();

}
