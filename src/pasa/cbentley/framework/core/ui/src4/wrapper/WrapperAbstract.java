package pasa.cbentley.framework.core.ui.src4.wrapper;

import pasa.cbentley.core.src4.ctx.ICtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.core.src4.stator.IStatorable;
import pasa.cbentley.core.src4.stator.StatorReader;
import pasa.cbentley.core.src4.stator.StatorWriter;
import pasa.cbentley.framework.core.ui.src4.ctx.CoreUiCtx;
import pasa.cbentley.framework.core.ui.src4.ctx.IConfigCoreUi;
import pasa.cbentley.framework.core.ui.src4.ctx.ObjectCUC;
import pasa.cbentley.framework.core.ui.src4.engine.CanvasHostAbstract;
import pasa.cbentley.framework.core.ui.src4.interfaces.ICanvasHost;
import pasa.cbentley.framework.core.ui.src4.interfaces.IWrapperManager;
import pasa.cbentley.framework.core.ui.src4.tech.ITechFeaturesCanvas;

/**
 * A Wrapper controls only 1 {@link CanvasHostAbstract}
 * <br>
 * Appli is unaware of this class. It wraps the {@link CanvasHostAbstract} in a host specific GUI
 * Host gui may uses several wrappers in the same layout.
 * <br>
 * In which case the {@link WrapperAbstract#isContained()} return true.
 * <br>
 * When SizeDecidedByHost is set, the Canvas cannot set size from AppliCalls. Only Host actions will be able
 * to resize.
 * <br>
 * For example, if Canvas is inside a scrollpane managed by the Host, the Host canvas wrapper will tell
 * that it is able to set its size.
 * <br>
 * In J2ME, Android canvas wrapper? Well you could use an application inside
 * a CustomView.
 * 
 * A Wrapper takes
 * @author Charles Bentley
 *
 */
public abstract class WrapperAbstract extends ObjectCUC implements IStringable, IStatorable, ITechFeaturesCanvas {

   protected IWrapperManager manager;

   protected WrapperAbstract(CoreUiCtx cac) {
      super(cac);
   }

   /**
    * Hide the wrapped canvas to the user.
    * 
    * <li>If wrapper is a frame, hide the frame
    * <li>If wrapper is a tab, hide the tab
    * <li>etc.
    */
   public abstract void canvasHide();

   /**
    * Show the wrapped canvas to the user
    */
   public abstract void canvasShow();

   public ICtx getCtxOwner() {
      return cuc;
   }

   public int getStatorableClassID() {
      throw new RuntimeException("Must be implemented by subclass" + getClass().getName());
   }

   /**
    * 
    * @return
    */
   public String getTitle() {
      throw new RuntimeException();
   }

   public IWrapperManager getWrapperManager() {
      return manager;
   }

   /**
    * 
    * @param feature
    * @return
    */
   public boolean hasFeatureSupport(int feature) {
      return false;
   }

   /**
    * Returns true if the user cannot move the Wrapper independantly.
    * 
    * Basically all returns true unless a free floating frame wrapper.
    * @return
    */
   public abstract boolean isContained();

   /**
    * Called when the {@link CanvasHostAbstract} implementation is using a host wrapper and
    * its the wrapper that can answer the question to
    * {@link CanvasHostAbstract#isCanvasFeatureEnabled(int)}.
    * 
    * <p>
    * The wrapper may also in turn delegate the answer to its {@link IWrapperManager}.
    * </p>
    * @param feature
    * @return
    */
   public boolean isFeatureEnabled(int feature) {
      return false;
   }

   public void onExit() {

   }

   /** 
    * Initialize the wrapper with the {@link CanvasHostAbstract}.
    * 
    * 
    * The {@link SwingManager} when requested to create a new canvas does the following
    * <li> Asks its {@link IWrapperManager} which wrapper to create.
    * <li> Creates a {@link CanvasAbstractSwing} with requested capabilities (OpenGL, Active Rendering etc)
    * <li> Link the Wrapper with the {@link CanvasAbstractSwing}
    * <li> Link the {@link CanvasAbstractSwing} with the wrapper.
    * <br>
    * The method is responsible to "add" the Canvas to the wrapper structure.
    * @param canvas the host implementation of a canvas. 
    */
   public abstract void setCanvasHost(CanvasHostAbstract canvas);

   /**
    * Asks the wrapper to position its in the default position as defined by {@link IConfigCoreUi}
    * 
    * Host Implementation of configuration provides the value x,y.
    */
   public abstract void setDefaultStartPosition();

   public boolean setFeature(int feature, boolean mode) {
      return false;
   }

   /**
    * 
    * @param mode
    */
   public abstract void setFullScreenMode(boolean mode);

   /**
    * Sets the tab based on the application manifest
    * @param str
    */
   public abstract void setIcon(String str);

   /**
    * Sets the position of the Wrapper containing the {@link CanvasHostAbstract}.
    * 
    * <p>
    * Called by  {@link CanvasHostAbstract} on its wrapper when application call {@link ICanvasHost#icSetXY(int, int)}
    * </p>
    * @param x
    * @param y
    */
   public abstract void setPosition(int x, int y);

   /**
    * Sets the size of the Wrapper so that the containing the {@link CanvasHostAbstract} is exactly w,h in size.
    * 
    * <p>
    * Called by  {@link CanvasHostAbstract} on its wrapper when application call {@link ICanvasHost#icSetSize(int, int)}
    * </p>
    * @param x
    * @param y
    */
   public abstract void setSize(int w, int h);

   /**
    * 
    * @param str
    */
   public abstract void setTitle(String str);

   public void setWrapperManager(IWrapperManager manager) {
      this.manager = manager;
   }

   public void stateReadFrom(StatorReader state) {
   }

   public void stateWriteTo(StatorWriter state) {

   }

   public void stateWriteToParamSub(StatorWriter state) {

   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, WrapperAbstract.class, 156);
      toStringPrivate(dc);
      super.toString(dc.sup());
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, WrapperAbstract.class);
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   private void toStringPrivate(Dctx dc) {

   }

   //#enddebug

}
