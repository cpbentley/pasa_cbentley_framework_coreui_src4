package pasa.cbentley.framework.core.ui.src4.engine;

import pasa.cbentley.byteobjects.src4.core.ByteObject;
import pasa.cbentley.byteobjects.src4.stator.StatorReaderBO;
import pasa.cbentley.byteobjects.src4.stator.StatorWriterBO;
import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.core.src4.stator.ITechStator;
import pasa.cbentley.core.src4.stator.StatorReader;
import pasa.cbentley.core.src4.stator.StatorWriter;
import pasa.cbentley.core.src4.structs.IntToObjects;
import pasa.cbentley.framework.core.ui.src4.ctx.CoreUiCtx;
import pasa.cbentley.framework.core.ui.src4.ctx.ObjectCUC;
import pasa.cbentley.framework.core.ui.src4.event.BEvent;
import pasa.cbentley.framework.core.ui.src4.event.DeviceEvent;
import pasa.cbentley.framework.core.ui.src4.event.DeviceEventXY;
import pasa.cbentley.framework.core.ui.src4.event.GestureArea;
import pasa.cbentley.framework.core.ui.src4.exec.ExecutionContext;
import pasa.cbentley.framework.core.ui.src4.exec.OutputState;
import pasa.cbentley.framework.core.ui.src4.input.InputState;
import pasa.cbentley.framework.core.ui.src4.input.ScreenOrientationCtrl;
import pasa.cbentley.framework.core.ui.src4.interfaces.IBEventListener;
import pasa.cbentley.framework.core.ui.src4.interfaces.ICanvasAppli;
import pasa.cbentley.framework.core.ui.src4.interfaces.ICanvasHost;
import pasa.cbentley.framework.core.ui.src4.tech.IBOCanvasHost;
import pasa.cbentley.framework.core.ui.src4.tech.IInput;
import pasa.cbentley.framework.core.ui.src4.tech.ITechHostUI;
import pasa.cbentley.framework.core.ui.src4.utils.CoreUiSettings;
import pasa.cbentley.framework.coredraw.src4.interfaces.IGraphics;

/**
 * Base implementation of {@link ICanvasAppli}. UI on the side of the Bentley framework.
 * <br>
 * 
 * Can a {@link CanvasAppliAbstract} be used without a {@link ICanvasHost} ?
 * Some kind of null host ? Why not. But it won't recieve any events from the host.
 * It will have to be managed by the Application
 * Some kind of CanvasHostAppli class ?
 * @author Charles-Philip Bentley
 *
 */
public abstract class CanvasAppliAbstract extends ObjectCUC implements ICanvasAppli, IStringable {

   /**
   * Link to the Framework implementation of a Canvas.
   * <br>
   * Used to request repaints,  query the width/height
   */
   protected ICanvasHost         canvasHost;

   /**
    * Freely settable by subclass
    * 
    * {@link CanvasHostAbstract#titleIconComesticUpdate()} must be call for update on Host canvas.
    */
   protected String              canvasIconPath;

   /**
    * Freely settable by subclass
    * 
    * {@link CanvasHostAbstract#titleIconComesticUpdate()} must be call for update on Host canvas.
    */
   protected String              canvasTitle;

   /**
    * 
    */
   protected IntToObjects        duplicates;

   private GestureArea           gaCanvas;

   private IBEventListener       listener;

   private ScreenOrientationCtrl screenCtrl;

   protected final UCtx          uc;

   /**
    * When the Canvas is created, a reference to the Swing/SWT/J2ME Canvas is fetched
    * @param cac
    */
   public CanvasAppliAbstract(CoreUiCtx cac) {
      this(cac, null);
   }

   /**
    * Creates a {@link ICanvasHost} based on the given tech.
    * 
    * At this stage, we don't know about the technicalities of the implementation
    * @param cuc
    * @param boCanvasHost {@link IBOCanvasHost}
    */
   public CanvasAppliAbstract(CoreUiCtx cuc, ByteObject boCanvasHost) {
      super(cuc);
      if (boCanvasHost == null) {
         boCanvasHost = cuc.createBOCanvasHostDefault();
      }
      this.uc = cuc.getUC();
      canvasHost = cuc.createCanvasHost(boCanvasHost);
      if (canvasHost == null) {
         throw new NullPointerException();
      }
      cuc.linkCanvasAppliToHost(this, canvasHost);
   }

   /**
    * Canvas implementation provides a subclass of {@link InputState}
    * @return
    */
   public abstract InputState createInputState();

   public abstract OutputState createOutputState();

   public abstract ExecutionContext createExecutionContext();

   /**
    * A Duplicate is {@link ICanvasHost} that is linked to this Canvas.
    * @param c
    */
   public void addDuplicate(ICanvasHost c) {
      if (duplicates == null) {
         duplicates = new IntToObjects(cuc.getUC(), 1);
      }
      duplicates.add(c);
   }

   public void canvasHostUpdate() {
      //force title after the link. afterwards when changed inside app, event will call it
      canvasHost.titleIconComesticUpdate();
   }

   public void event(BEvent g) {
      eventToCanvas(g);
      if (listener != null) {
         listener.newEvent(g);
      }
   }

   /**
    * All input event from devices.
    * All events relative external business logic events.
    * <li> Buys results
    * <li> Activity Results
    */
   protected abstract void eventToCanvas(BEvent g);

   public void fixRotation(DeviceEventXY dex) {
      if (screenCtrl != null) {
         if (dex.getDeviceMode() != IInput.MOD_5_WHEELED) {
            int x = dex.getX();
            int y = dex.getY();
            dex.updateX(screenCtrl.rotationXChange(x, y));
            dex.updateY(screenCtrl.rotationYChange(x, y));
         }
      }
   }

   public void fixRotationKey(DeviceEvent dex) {
      if (screenCtrl != null) {
         int newButton = screenCtrl.rotationKeyChange(dex.getDeviceButton());
         dex.updateButton(newButton);
      }
   }

   public ScreenOrientationCtrl getScreenCtrl() {
      if (screenCtrl == null) {
         screenCtrl = new ScreenOrientationCtrl(cuc, this);
      }
      return screenCtrl;
   }

   /**
    * When painting is done using {@link CanvasAppliAbstract#getGraphics()}, you need to call
    * this method to make the graphics seen on screen
    * <br>
    * Wrapper around {@link ICanvasHost#flushGraphics()}
    */
   public void flushGraphics() {
      //#debug
      toDLog().pFlow("", this, CanvasAppliAbstract.class, "flushGraphics@150", LVL_03_FINEST, true);

      canvasHost.flushGraphics();
   }

   /**
    * Returns the {@link ICanvasHost} rendering this {@link CanvasAppliAbstract}
    * <br>
    * <br>
    * @return
    */
   public ICanvasHost getCanvasHost() {
      return canvasHost;
   }

   /**
    * {@link GestureArea}. 
    * Updates {@link GestureArea} with current size.
    * 
    * This object is automatically updated when canvas is resized
    * @return
    */
   public GestureArea getGACanvas() {
      gaCanvas = new GestureArea();
      gaCanvas.w = this.getWidth();
      gaCanvas.h = this.getHeight();
      return gaCanvas;
   }

   /**
    * TODO method for active rendering.
    * 
    * Gets a graphics with the given size of the {@link ActiveCanvas}.
    * @return
    */
   public IGraphics getGraphics() {
      return canvasHost.getGraphics();
   }

   /**
    * 0 when called inside the constructor
    */
   public int getHeight() {
      return canvasHost.getICHeight();
   }

   public String getIcon() {
      if (canvasIconPath == null) {
         canvasIconPath = cuc.getConfigUI().getIconPathDefault();
      }
      return canvasIconPath;
   }

   public CoreUiSettings getInputSettings() {
      return cuc.getInputSettings();
   }

   public int getScreenX(int x) {
      return canvasHost.getScreenX(x);
   }

   public int getScreenY(int y) {
      return canvasHost.getScreenY(y);
   }

   public int getStatorableClassID() {
      throw new RuntimeException("Must be implemented by subclass" + this.getClass().getName());
   }

   /**
    * by default returns the title from the config
    * @return
    */
   public String getTitle() {
      if (canvasTitle == null) {

      }
      return canvasTitle;
   }

   /**
    * 0 when called inside the constructor
    */
   public int getWidth() {
      return canvasHost.getICWidth();
   }

   public boolean hasCanvasFeatureSupport(int feature) {
      return canvasHost.isCanvasFeatureEnabled(feature);
   }

   public void hideNotify() {
   }

   public boolean isShown() {
      return canvasHost.isShown();
   }

   /**
    * Abstract method that needs to be implemented by concrete midlet supplied subclasses.
    */
   public abstract void paint(IGraphics g);

   public void removeDuplicate(ICanvasHost c) {
      if (duplicates != null) {
         duplicates.removeRef(c);
      }
   }

   /**
    * Repaints the whole application. That includes all the duplicated canvas.
    * 
    * <p>
    * Called when an action changed the content of the application.
    * </p>
    * @see ICanvasHost#icRepaint()
    */
   public void repaint() {
      canvasHost.icRepaint();
      if (duplicates != null) {
         for (int i = 0; i < duplicates.nextempty; i++) {
            ((ICanvasHost) duplicates.getObjectAtIndex(i)).icRepaint();
         }
      }
   }

   public void repaint(int x, int y, int w, int h) {
      canvasHost.icRepaint(x, y, w, h);
      if (duplicates != null) {
         for (int i = 0; i < duplicates.nextempty; i++) {
            ((ICanvasHost) duplicates.getObjectAtIndex(i)).icRepaint(x, y, w, h);
         }
      }
   }

   /**
    */
   public void setFullScreenMode(boolean mode) {
      canvasHost.setCanvasFeature(ITechHostUI.SUP_ID_27_FULLSCREEN, mode);
   }

   public void setIcon(String icon) {
      this.canvasIconPath = icon;
   }

   public void setListener(IBEventListener lis) {
      this.listener = lis;
   }

   /**
    * Sets the title to be used
    */
   public void setTitle(String title) {
      this.canvasTitle = title;
   }

   public void showNotify() {
   }

   /**
    */
   public void stateReadFrom(StatorReader state) {

      //#debug
      toDLog().pStator("StatorReader", this, CanvasAppliAbstract.class, "stateReadFrom", LVL_05_FINE, true);
      //parameters were already read

      StatorReaderBO stator = (StatorReaderBO) state;

      state.checkInt(98765);
      canvasTitle = stator.getReader().readString();
      canvasIconPath = stator.getReader().readString();

      canvasHost = (ICanvasHost) stator.readObject(cuc, canvasHost);
      listener = (IBEventListener) stator.readObject(listener);
   }

   /**
    */
   public void stateWriteTo(StatorWriter state) {

      //#debug
      toDLog().pStator("StatorWriter", this, CanvasAppliAbstract.class, "stateWriteTo", LVL_05_FINE, true);

      stateWriteToParamSub(state);

      //it might already be written... who knows
      state.writeInt(98765);
      state.getWriter().writeString(canvasTitle);
      state.getWriter().writeString(canvasIconPath);

      state.writerToStatorable(canvasHost);
      state.writerToStatorable(listener);
   }

   protected void stateWriteToParamSub(StatorWriter state) {
      //Do the parameters for the constructor
      state.writeInt(ITechStator.MAGIC_WORD_OBJECT_PARAM);
      StatorWriterBO swbo = (StatorWriterBO) state;
      ByteObject boCanvasHost = this.getCanvasHost().getBOCanvasHost();
      swbo.writeByteObject(boCanvasHost);
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, CanvasAppliAbstract.class, 300);
      toStringPrivate(dc);
      super.toString(dc.sup());

      dc.appendVarWithSpace("canvasTitle", canvasTitle);
      dc.appendVarWithSpace("canvasIconPath", canvasIconPath);
      dc.nlLvl(listener, "IBEventListener");
      dc.nlLvl("Duplicates", duplicates);
      dc.nlLvl(canvasHost, "ICanvasHost");
      dc.nlLvl(gaCanvas, "GestureArea");
      dc.nlLvl(screenCtrl, "ScreenOrientation");

   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, CanvasAppliAbstract.class);
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   private void toStringPrivate(Dctx dc) {
      dc.appendVarWithSpace("getWidth", getWidth());
      dc.appendVarWithSpace("getHeight", getHeight());
      dc.appendVarWithSpace("isShown", isShown());
   }

   //#enddebug

}
