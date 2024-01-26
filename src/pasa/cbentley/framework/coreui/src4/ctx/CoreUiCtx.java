package pasa.cbentley.framework.coreui.src4.ctx;

import pasa.cbentley.byteobjects.src4.core.ByteObject;
import pasa.cbentley.byteobjects.src4.ctx.ABOCtx;
import pasa.cbentley.byteobjects.src4.ctx.BOCtx;
import pasa.cbentley.byteobjects.src4.ctx.IConfigBO;
import pasa.cbentley.byteobjects.src4.ctx.IStaticIDsBO;
import pasa.cbentley.byteobjects.src4.stator.StatorReaderBO;
import pasa.cbentley.byteobjects.src4.stator.StatorWriterBO;
import pasa.cbentley.core.src4.api.ApiManager;
import pasa.cbentley.core.src4.ctx.CtxManager;
import pasa.cbentley.core.src4.ctx.ICtx;
import pasa.cbentley.core.src4.event.EventBusArray;
import pasa.cbentley.core.src4.event.IEventBus;
import pasa.cbentley.core.src4.interfaces.IExecutor;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.structs.IntToObjects;
import pasa.cbentley.framework.coredraw.src4.ctx.CoreDrawCtx;
import pasa.cbentley.framework.coredraw.src4.interfaces.IFontFactory;
import pasa.cbentley.framework.coredraw.src4.interfaces.IImageFactory;
import pasa.cbentley.framework.coreui.src4.engine.CanvasAppliAbstract;
import pasa.cbentley.framework.coreui.src4.engine.CanvasHostAbstract;
import pasa.cbentley.framework.coreui.src4.engine.KeyMapAbstract;
import pasa.cbentley.framework.coreui.src4.engine.WrapperAbstract;
import pasa.cbentley.framework.coreui.src4.event.BEvent;
import pasa.cbentley.framework.coreui.src4.event.GestureArea;
import pasa.cbentley.framework.coreui.src4.interfaces.ICanvasAppli;
import pasa.cbentley.framework.coreui.src4.interfaces.ICanvasHost;
import pasa.cbentley.framework.coreui.src4.interfaces.IWrapperManager;
import pasa.cbentley.framework.coreui.src4.interfaces.IHostGestures;
import pasa.cbentley.framework.coreui.src4.interfaces.IHostUI;
import pasa.cbentley.framework.coreui.src4.tech.IBOCanvasHost;
import pasa.cbentley.framework.coreui.src4.tech.IBOFramePos;
import pasa.cbentley.framework.coreui.src4.tech.ITechHostUI;
import pasa.cbentley.framework.coreui.src4.utils.InputSettings;

/**
 * Intermediate CoreUi context for J2Se, J2me, Android, IOS, etc.
 * 
 * <p>
 * Introduces Canvas, Gestures on top of {@link CoreDrawCtx} capabilities.
 * </p>
 * 
 * <p>
 * Same reasoning as for {@link CoreDrawCtx}. {@link CoreUiCtx} is independant from CoreFramework.
 * </p>
 * 
 * @author Charles Bentley
 *
 */
public abstract class CoreUiCtx extends ABOCtx implements IEventsCoreUI, ITechHostUI, ITechCtxSettingsCoreUI {

   private BOModuleCoreUi      boModule;

   /**
    * Some Host configurations prevent outside windows.
    * In those cases, when several canvases are created,
    * a canvas is shown instead of another. A command
    * must be used to change the focus. each canvas is replaced
    * in the container.
    * <br>
    * <br>
    * contains {@link CanvasBridgeX} 
    * <br>
    * @see {@link CoreUiCtx#createCanvasHost(ByteObject)}
    */
   protected IntToObjects      canvases;

   /** 
    * Reference to the root Canvas of the Host
    * <br>
    * {@link ICanvasHost}
    */
   private CanvasHostAbstract  canvasRoot;

   protected final CoreDrawCtx cdc;

   private EventBusArray       eventBus;

   private InputSettings       inputSettings;

   protected int               pressedKeyCounter;

   protected int[]             pressedKeys = new int[20];

   private IWrapperManager     wrapperManager;

   public CoreUiCtx(IConfigCoreUI configUI, CoreDrawCtx cdc) {
      super(configUI, cdc.getBOC());
      this.cdc = cdc;
      canvases = new IntToObjects(cdc.getUCtx(), 2);

      boModule = new BOModuleCoreUi(this);

      CtxManager cm = uc.getCtxManager();
      cm.registerStaticRange(this, IStaticIDsBO.SID_BYTEOBJECT_TYPES, IBOTypesCoreUI.AZ_BOTYPE_FW_A, IBOTypesCoreUI.AZ_BOTYPE_FW_Z);
   }

   protected void applySettings(ByteObject settingsNew, ByteObject settingsOld) {

   }

   /**
    * {@link IBOTypesCoreUI#TYPE_5_TECH_CANVAS_HOST}
    * @return
    */
   public ByteObject createBOCanvasHostDefault() {
      int type = IBOTypesCoreUI.TYPE_5_TECH_CANVAS_HOST;
      int size = IBOCanvasHost.TCANVAS_BASIC_SIZE;
      ByteObject tech = cdc.getBOC().getByteObjectFactory().createByteObject(type, size);
      tech.set1(IBOCanvasHost.TCANVAS_OFFSET_02_WRAPPER_TYPE1, IBOCanvasHost.TCANVAS_TYPE_1_FRAME);
      tech.set2(IBOCanvasHost.TCANVAS_OFFSET_14_FRAMEPOS2, 0);
      return tech;
   }
   
   public void clearCanvases() {
      canvasRoot = null;
      canvases.clear();
   }

   public ApiManager getApiManager() {
      return getUCtx().getApiManager();
   }

   /**
    * Empty with
    * <li> {@link IBOFramePos#FPOS_OFFSET_02_X2} = 0
    * <li> {@link IBOFramePos#FPOS_OFFSET_03_Y2} = 0
    * <li> {@link IBOFramePos#FPOS_OFFSET_04_W2} = 0
    * <li> {@link IBOFramePos#FPOS_OFFSET_05_H2} = 0
    * @return
    */
   public ByteObject createBOFrameDefault() {
      int type = IBOTypesCoreUI.TYPE_8_FRAME_POS;
      int size = IBOFramePos.FPOS_BASIC_SIZE;
      ByteObject tech = cdc.getBOC().getByteObjectFactory().createByteObject(type, size);
      return tech;
   }

   /**
    * Creates a Canvas.. 
    * <li> Wraps the Canvas in its host owner.
    * <li> Add the canvas to this {@link CoreUiCtx} list of managed {@link CanvasHostAbstract}
    * <li> no linking to a {@link ICanvasAppli}.
    * @param boCanvasHost
    * @return
    */
   public final ICanvasHost createCanvasHost(ByteObject boCanvasHost) {
      if (boCanvasHost == null) {
         boCanvasHost = createBOCanvasHostDefault();
      }

      IWrapperManager wrapperMan = getWrapperManager();
      WrapperAbstract wrapper = wrapperMan.createNewWrapper(boCanvasHost);
      if (wrapper == null) {
         throw new NullPointerException();
      }

      CanvasHostAbstract canvasHost = createCanvasHost(wrapper, boCanvasHost);
      if (canvasHost.getWrapper() == null) {
         canvasHost.setWrapper(wrapper);
      }
      wrapper.setCanvasHost(canvasHost);

      //now that the wrapper and canvas are packed together. 
      canvasHost.setDefaultStartPosition();

      //where to host the canvas, in a new Frame?
      if (canvasRoot == null) {
         canvasRoot = canvasHost;
      }
      canvases.add(canvasHost);

      return canvasHost;
   }

   /**
    * Called by Appli to create the {@link ICanvasHost} on which it will draw the {@link ICanvasAppli}.
    * 
    * 
    * Calling this method means Host supports multiple windows.
    * 
    * <p>
    * {@link ICanvasHost} is created according to the {@link IBOCanvasHost} byteobject settings.
    * </p>
    * 
    * The constructor of {@link CanvasAppliAbstract} get the tech param.
    * 
    * The {@link ICanvasHost} belongs to the {@link IAppli}.
    * 
    * <li> Root Level Frame/Windows Canvas
    * <li> Slave Window Canvas
    * <li> 
    * 
    * 
    * This method does not call {@link ICanvasHost#canvasShow()}. It is the job of the caller.
    * 
    * 
    * When creating an embedded video canvas host ... TODO
    * 
    * When application is setting up from previous state, that state decides which {@link ICanvasAppli} is hosted
    * 
    * @param canvasAppli is not null. the canvas from the appli in need of a sleeve in the target host.
    * @param boCanvasHost {@link IBOCanvasHost} could be null. then we use a default tech provided by the ctx
    */
   public ICanvasHost createCanvas(ICanvasAppli canvasAppli, ByteObject boCanvasHost) {
      ICanvasHost canvasHost = createCanvasHost(boCanvasHost);
      linkCanvasAppliToHost(canvasAppli, canvasHost);
      return canvasHost;
   }

   /**
    * Create a Canvas within {@link CoreUiCtx}.  Wrapper may influence the Canvas type/parameters.
    * 
    * <p>
    * 
    * </p>
    * The parent for example.
    * 
    * <p>
    * If we are in Swing Context, it will create a Swing Canvas.
    * Cross boundary? Swing app that wants to use JX Canvas for Browser.
    * 
    * </p>
    * 
    * The {@link IBOCanvasHost} provides some hints on what kind of canvas we want.
    * 
    * But the application cannot always have what it wants.
    * 
    * In the case of the launcher, the launcher decides which wrapper to use
    * 
    * {@link IBOCanvasHost#TCANVAS_TYPE_0_DEFAULT}
    * {@link IBOCanvasHost}
    * 
    * Note: Canvas Icon is decided by the wrapper. (tabbed, frame)
    * <p>
    * 
    * </p>
    * 
    * @param wrapper when not null, overrides {@link IBOCanvasHost} normal definition of wrappers.
    * @param boCanvasHost {@link IBOCanvasHost}
    * 
    * @return linked {@link CanvasHostAbstract} with its wrapper
    */
   public abstract CanvasHostAbstract createCanvasHost(WrapperAbstract wrapper, ByteObject boCanvasHost);

   /**
    * Creates the {@link IWrapperManager} if none is defined.
    * 
    * This API provides a way for applications to directly interact with Wrappers.
    * Should this be necessary in case of Desktop applications managing several screens.
    * @return
    */
   public abstract IWrapperManager createCanvasOwnerDefault();

   /**
    * 
    */
   public void disposeCanvas(ICanvasHost c) {
      if (canvases != null) {
         canvases.removeRef(c);
      }

      //TODO write canvas settings based on screen configs.. ask the host to compute it
      //or should it be done at the close? and written to tech
   }

   public BOCtx getBOC() {
      return cdc.getBOC();
   }

   public void onExit() {
      for (int i = 0; i < canvases.nextempty; i++) {
         CanvasHostAbstract c = (CanvasHostAbstract) canvases.getObjectAtIndex(i);
         c.onExit();
      }      
   }
   
   public int getBOCtxSettingSize() {
      return CTX_COREUI_BASIC_SIZE;
   }

   /**
    * Return an array of {@link ICanvasHost} located at that coordinate.
    * <br>
    * The first being the topmost canvas
    * @param x
    * @param y
    * @return empty array if none
    */
   /**
    */
   public CanvasHostAbstract[] getCanvasAt(int x, int y) {
      //TODO keep track of top most canvas
      //problem is that the z cannot be determ
      IntToObjects itos = new IntToObjects(getUCtx(), canvases.nextempty);
      for (int i = 0; i < canvases.nextempty; i++) {
         CanvasHostAbstract ch = (CanvasHostAbstract) canvases.objects[i];
         int rx = ch.getICX();
         int ry = ch.getICY();
         int rw = ch.getICWidth();
         int rh = ch.getICHeight();
         boolean isInside = GestureArea.isInside(x, y, rx, ry, rw, rh);
         if (isInside) {
            itos.add(ch);
         }
      }
      CanvasHostAbstract[] ar = new CanvasHostAbstract[itos.nextempty];
      itos.copyToArray(ar);
      return ar;
   }

   /**
    * Returns all canvases
    * @return
    */
   public CanvasHostAbstract[] getCanvases() {
      CanvasHostAbstract[] hosts = new CanvasHostAbstract[canvases.getLength()];
      canvases.copyToArray(hosts);
      return hosts;
   }

   public int getCanvasesNum() {
      return canvases.getLength();
   }

   public int getCanvasesNumShown() {
      int count = 0;
      for (int i = 0; i < canvases.nextempty; i++) {
         CanvasHostAbstract c = (CanvasHostAbstract) canvases.getObjectAtIndex(i);
         if (c.isShown()) {
            count++;
         }
      }
      return count;
   }

   public CanvasHostAbstract[] getCanvasesShown() {
      int numShown = getCanvasesNumShown();
      CanvasHostAbstract[] hosts = new CanvasHostAbstract[numShown];
      int count = 0;
      for (int i = 0; i < canvases.nextempty; i++) {
         CanvasHostAbstract c = (CanvasHostAbstract) canvases.getObjectAtIndex(i);
         if (c.isShown()) {
            hosts[count] = c;
            count++;
         }
      }
      return hosts;
   }
   


   public CanvasHostAbstract getCanvasFromID(int id) {
      for (int i = 0; i < canvases.nextempty; i++) {
         CanvasHostAbstract c = (CanvasHostAbstract) canvases.getObjectAtIndex(i);
         int cid = c.getBOCanvasHost().get2(IBOCanvasHost.TCANVAS_OFFSET_03_ID2);
         if (cid == id) {
            return c;
         }
      }
      return null;
   }

   /**
    * Simple get. No object creation. Null if no Canvas were initialized at all.
    * @return
    */
   public CanvasHostAbstract getCanvasRootHost() {
      return canvasRoot;
   }

   public CoreDrawCtx getCDC() {
      return cdc;
   }

   public IConfigCoreUI getConfigUI() {
      if (config == null) {
         throw new IllegalStateException("IConfigUI not initialized");
      }
      return (IConfigCoreUI) config;
   }

   public ICtx[] getCtxSub() {
      return new ICtx[] { cdc };
   }

   public int[] getEventBaseTopology() {
      int[] events = new int[IEventsCoreUI.BASE_EVENTS];
      events[IEventsCoreUI.PID_0_ANY] = IEventsCoreUI.PID_0_ANY;
      events[IEventsCoreUI.PID_02_CANVAS] = IEventsCoreUI.PID_02_CANVAS_X_NUM;
      return events;
   }

   public IEventBus getEventBus() {
      if (eventBus == null) {
         eventBus = new EventBusArray(uc, this, getEventBaseTopology());
      }
      return eventBus;
   }

   public abstract IExecutor getExecutor();

   public IFontFactory getFontFactory() {
      return cdc.getFontFactory();
   }

   /**
    * 
    * @return
    */
   public abstract IHostGestures getHostGestures();

   public abstract IHostUI getHostUI();

   /**
    * 
    * @param id
    * @return
    */
   public int getHostInt(int id) {
      return getHostUI().getHostInt(id);
   }

   /**
    * 
    * @return
    */
   public IImageFactory getImageFactory() {
      return cdc.getImageFactory();
   }

   public InputSettings getInputSettings() {
      if (inputSettings == null) {
         inputSettings = new InputSettings(this);
      }
      return inputSettings;
   }

   public abstract KeyMapAbstract getKeyMap();

   /**
    * Creates default if not set
    * @return
    */
   public IWrapperManager getWrapperManager() {
      if (wrapperManager == null) {
         wrapperManager = createCanvasOwnerDefault();
      }
      return wrapperManager;
   }

   /**
    * Does this ui context support the given ui feature id.
    * 
    * @param supportID
    * @return
    */
   public boolean hasFeatureSupport(int supportID) {
      switch (supportID) {
         case SUP_ID_01_KEYBOARD:
            return true;
         case SUP_ID_02_POINTERS:
            return true;
         case SUP_ID_03_OPEN_GL:
            return true;
         case SUP_ID_05_SCREEN_ROTATIONS:
            return false;
         case SUP_ID_24_MULTIPLE_WINDOWS:
            return true;
         default:
            break;
      }
      return false;
   }

   /**
    * Decided by the config, the settings.
    * 
    * {@link CoreUiCtx} assume by default that the keypad is designed like on a phone
    * 
    * 1 2 3 
    * 4 5 6 
    * 7 8 9
    *   0  
    *   
    * Other devices can have a different setup like PC keyboard
    * 7 8 9
    * 4 5 6 
    * 1 2 3
    *   0  
    *   
    *   
    * @return
    */
   public boolean isInverseNumPad28() {
      return false;
   }

   /**
    * Avoid repeats. What if different devices? must be the same device
    * @param j2seKeyCode
    * @return
    */
   public synchronized boolean isKeyRepeat(int j2seKeyCode) {
      for (int i = 0; i < pressedKeyCounter; i++) {
         if (pressedKeys[i] == j2seKeyCode) {
            return true;
         }
      }
      pressedKeys[pressedKeyCounter] = j2seKeyCode;
      pressedKeyCounter++;
      return false;
   }

   public boolean isMajOn() {
      return false;
   }

   /**
    * Prevent the repeating nature of keys at the framework level.
    * <br>
    * Some frameworks like SWT will not send a key release if the key is
    * released after the release of another key.
    * <br>
    * TODO In those cases, when such a mix occurs, we have to release all keys
    * @param j2seKeyCode
    */
   public synchronized void keyReleaseRepeat(int j2seKeyCode) {
      boolean shift = false;
      for (int i = 0; i < pressedKeyCounter; i++) {
         if (pressedKeys[i] == j2seKeyCode) {
            shift = true;
         } else if (shift) {
            pressedKeys[i - 1] = pressedKeys[i];
         }
      }
      if (pressedKeyCounter > 0) {
         pressedKeyCounter--;
      }
   }

   /**
    * Called when {@link CanvasAppliAbstract} is created. Its links both canvas together.
    * 
    * @param canvasAppli
    * @param canvasHost
    */
   public void linkCanvasAppliToHost(ICanvasAppli canvasAppli, ICanvasHost canvasHost) {
      //registers the relevant events/listeners/observers
      canvasHost.setCanvasAppli(canvasAppli);
   }

   /**
    * Will be called when we need to apply incoming config to {@link ITechCtxSettingsCoreUI} ByteObject.
    * @param config
    * @param settings
    */
   protected void matchConfig(IConfigBO config, ByteObject settings) {
      IConfigCoreUI configUI = (IConfigCoreUI) config;
      settings.setFlag(CTX_COREUI_OFFSET_01_FLAG1, CTX_COREUI_FLAG_1_FULLSCREEN, configUI.isFullscreen());
   }

   public void publishEventOnRoot(BEvent be) {
      canvasRoot.getCanvasAppli().event(be);
   }

   /**
    * Publish the Event on all knows Canvas
    * @param se
    */
   public void publishEvent(BEvent se) {
      for (int i = 0; i < canvases.nextempty; i++) {
         CanvasHostAbstract c = (CanvasHostAbstract) canvases.getObjectAtIndex(i);
         c.getCanvasAppli().event(se);
      }
   }

   /**
    * Asks the Canvas to run this in its GUI thread.
    * <br>
    * @param run
    */
   public abstract void runGUI(Runnable run);

   public void setMajOn(boolean b) {

   }

   public void setWrapperManager(IWrapperManager wrapperManager) {
      this.wrapperManager = wrapperManager;
   }

   public void showAllCanvases() {
      canvasRoot.getCanvasAppli().showNotify();
      canvasRoot.canvasShow();
      for (int i = 0; i < canvases.nextempty; i++) {
         if (canvases.objects[i] != canvasRoot) {
            CanvasHostAbstract canvas = (CanvasHostAbstract) canvases.objects[i];
            canvas.getCanvasAppli().showNotify();
            canvas.canvasShow();
         }
      }
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, CoreUiCtx.class);
      toStringPrivate(dc);
      super.toString(dc.sup());

      dc.nlLvl(wrapperManager, "wrapperManager");
      dc.nlLvl(canvasRoot, "canvasRoot");

      for (int i = 0; i < canvases.getLength(); i++) {
         CanvasHostAbstract canvas = (CanvasHostAbstract) canvases.getObjectAtIndex(i);
         dc.nlLvl(canvas, "AbstractCanvasHost");
      }
      dc.nlLvl(eventBus, "eventBus");

      dc.nlLvl(cdc, CoreDrawCtx.class);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, CoreUiCtx.class);
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   private void toStringPrivate(Dctx dc) {

   }

  

   //#enddebug

}
