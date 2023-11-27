package pasa.cbentley.framework.coreui.src4.engine;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.core.src4.stator.IStatorable;
import pasa.cbentley.core.src4.stator.StatorReader;
import pasa.cbentley.core.src4.stator.StatorWriter;
import pasa.cbentley.framework.coreui.src4.ctx.CoreUiCtx;
import pasa.cbentley.framework.coreui.src4.interfaces.ICanvasOwner;
import pasa.cbentley.framework.coreui.src4.tech.ITechFeaturesUI;

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
public abstract class WrapperAbstract extends AbstractUITemplate implements IStringable, ITechFeaturesUI, IStatorable {

   /**
    * The create
    */
   private ICanvasOwner manager;

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

   /**
    * {@link ICanvasOwner} of this wrapper. Basically the object that created it and knows
    *  its true nature.
    * @return
    */
   public ICanvasOwner getWrapperManager() {
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
    * 
    * @return
    */
   public abstract boolean isContained();

   public boolean isFeatureEnabled(int feature) {
      return false;
   }

   /** 
    * Initialize the wrapper with the Canvas.
    * <br><br>
    * The {@link SwingManager} when requested to create a new canvas does the following
    * <li> Asks its {@link ICanvasOwner} which wrapper to create.
    * <li> Creates a {@link CanvasAbstractSwing} with requested capabilities (OpenGL, Active Rendering etc)
    * <li> Link the Wrapper with the {@link CanvasAbstractSwing}
    * <li> Link the {@link CanvasAbstractSwing} with the wrapper.
    * <br>
    * The method is responsible to "add" the Canvas to the wrapper structure.
    * @param canvas the host implementation of a canvas. 
    */
   public abstract void setCanvasHost(CanvasHostAbstract canvas);

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
    * Sets the size of the Wrapper containing the {@link CanvasHostAbstract} so that
    * the {@link CanvasHostAbstract} is 
    * @param x
    * @param y
    */
   public abstract void setPosition(int x, int y);

   /**
    * 
    * @param w
    * @param h
    */
   public abstract void setSize(int w, int h);

   /**
    * 
    * @param str
    */
   public abstract void setTitle(String str);

   /**
    * 
    * @return
    */
   public String getTitle() {
      throw new RuntimeException();
   }

   public void stateReadFrom(StatorReader state) {

   }

   public void stateWriteTo(StatorWriter state) {

   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, AbstractUITemplate.class);
      toStringPrivate(dc);
      super.toString(dc.sup());

      dc.nlLvl(manager, ICanvasOwner.class);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, AbstractUITemplate.class);
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   private void toStringPrivate(Dctx dc) {

   }

   //#enddebug

}
