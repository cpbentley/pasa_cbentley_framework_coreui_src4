package pasa.cbentley.framework.coreui.src4.engine;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.framework.coreui.src4.ctx.CoreUiCtx;
import pasa.cbentley.framework.coreui.src4.interfaces.ICanvasWrapperManager;

/**
 * A Wrapper controls only 1 {@link AbstractCanvasHost}
 * <br>
 * Appli is unaware of this class. It wraps the {@link AbstractCanvasHost} in a host specific GUI
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
public abstract class WrapperAbstract extends AbstractUITemplate implements IStringable {

   /**
    * The create
    */
   private ICanvasWrapperManager manager;

   protected WrapperAbstract(CoreUiCtx cac) {
      super(cac);
   }

   public abstract boolean isContained();

   /** 
    * Initialize the wrapper with the Canvas.
    * <br><br>
    * The {@link SwingManager} when requested to create a new canvas does the following
    * <li> Asks its {@link ICanvasWrapperManager} which wrapper to create.
    * <li> Creates a {@link CanvasAbstractSwing} with requested capabilities (OpenGL, Active Rendering etc)
    * <li> Link the Wrapper with the {@link CanvasAbstractSwing}
    * <li> Link the {@link CanvasAbstractSwing} with the wrapper.
    * <br>
    * The method is responsible to "add" the Canvas to the wrapper structure.
    * @param can
    */
   public abstract void setCanvasHost(AbstractCanvasHost can);

   public ICanvasWrapperManager getWrapperManager() {
      return manager;
   }

   public abstract void setDefaultStartPosition();

   /**
    * Sets the tab based on the application manifest
    * @param str
    */
   public abstract void setIcon(String str);

   public abstract void setTitle(String str);

   public abstract void setFullScreenMode(boolean mode);

   public abstract void setSize(int w, int h);

   public abstract void setPosition(int x, int y);

   /**
    * Show the wrapped canvas to the user
    */
   public abstract void canvasShow();

   public abstract void canvasHide();

   public boolean hasFeatureSupport(int feature) {
      return false;
   }

   public boolean isFeatureEnabled(int feature) {
      return false;
   }

   public boolean setFeature(int feature, boolean mode) {
      return false;
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, "WrapperAbstract");
      toStringPrivate(dc);
      super.toString(dc.sup());

      dc.nlLvl(manager, ICanvasWrapperManager.class);
   }

   private void toStringPrivate(Dctx dc) {

   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "WrapperAbstract");
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   //#enddebug

}
