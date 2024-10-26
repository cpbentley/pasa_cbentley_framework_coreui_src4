package pasa.cbentley.framework.core.ui.src4.ctx;

import pasa.cbentley.byteobjects.src4.core.ByteObject;
import pasa.cbentley.byteobjects.src4.ctx.ABOCtx;
import pasa.cbentley.byteobjects.src4.ctx.BOCtx;
import pasa.cbentley.byteobjects.src4.ctx.IConfigBO;
import pasa.cbentley.byteobjects.src4.ctx.IStaticIDsBO;
import pasa.cbentley.core.src4.api.ApiManager;
import pasa.cbentley.core.src4.ctx.CtxManager;
import pasa.cbentley.core.src4.ctx.ICtx;
import pasa.cbentley.core.src4.ctx.IStaticIDs;
import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.event.EventBusArray;
import pasa.cbentley.core.src4.event.IEventBus;
import pasa.cbentley.core.src4.interfaces.IExecutor;
import pasa.cbentley.core.src4.interfaces.IHost;
import pasa.cbentley.core.src4.interfaces.IHostData;
import pasa.cbentley.core.src4.interfaces.IHostFeature;
import pasa.cbentley.core.src4.interfaces.IHostService;
import pasa.cbentley.core.src4.interfaces.ITimeCtrl;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.structs.IntToObjects;
import pasa.cbentley.framework.core.ui.src4.engine.CanvasAppliAbstract;
import pasa.cbentley.framework.core.ui.src4.engine.CanvasHostAbstract;
import pasa.cbentley.framework.core.ui.src4.engine.KeyMapAbstract;
import pasa.cbentley.framework.core.ui.src4.event.EventAppli;
import pasa.cbentley.framework.core.ui.src4.event.BEvent;
import pasa.cbentley.framework.core.ui.src4.event.GestureArea;
import pasa.cbentley.framework.core.ui.src4.interfaces.ICanvasAppli;
import pasa.cbentley.framework.core.ui.src4.interfaces.ICanvasHost;
import pasa.cbentley.framework.core.ui.src4.interfaces.IHostGestures;
import pasa.cbentley.framework.core.ui.src4.interfaces.ITechEventApp;
import pasa.cbentley.framework.core.ui.src4.interfaces.IWrapperManager;
import pasa.cbentley.framework.core.ui.src4.tech.IBOCanvasHost;
import pasa.cbentley.framework.core.ui.src4.tech.IBOFramePos;
import pasa.cbentley.framework.core.ui.src4.tech.ITechWrapper;
import pasa.cbentley.framework.core.ui.src4.utils.CoreUiSettings;
import pasa.cbentley.framework.core.ui.src4.wrapper.WrapperAbstract;
import pasa.cbentley.framework.coredraw.src4.ctx.CoreDrawCtx;
import pasa.cbentley.framework.coredraw.src4.interfaces.IFontFactory;
import pasa.cbentley.framework.coredraw.src4.interfaces.IImageFactory;

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
public abstract class CoreUiCtx extends ABOCtx implements IEventsCoreUi, IBOCtxSettingsCoreUi {

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

   private CoreUiSettings      inputSettings;

   protected int               pressedKeyCounter;

   protected int[]             pressedKeys = new int[20];

   private IWrapperManager     wrapperManager;

   public CoreUiCtx(IConfigCoreUi configUI, CoreDrawCtx cdc) {
      super(configUI, cdc.getBOC());
      this.cdc = cdc;
      canvases = new IntToObjects(cdc.getUC(), 2);

      boModule = new BOModuleCoreUi(this);

      CtxManager cm = uc.getCtxManager();
      cm.registerStaticRange(this, IStaticIDsBO.SID_BYTEOBJECT_TYPES, IBOTypesCoreUi.AZ_BOTYPE_FW_A, IBOTypesCoreUi.AZ_BOTYPE_FW_Z);

      cm.registerStaticRange(this, IStaticIDs.SID_EVENTS, IEventsCoreUi.A_SID_COREUI_EVENT_A, IEventsCoreUi.A_SID_COREUI_EVENT_Z);
   }

   protected void applySettings(ByteObject settingsNew, ByteObject settingsOld) {

   }

   public void clearCanvases() {
      canvasRoot = null;
      canvases.clear();
   }

   /**
    * {@link IBOTypesCoreUi#TYPE_5_CANVAS_HOST}
    * @return
    */
   public ByteObject createBOCanvasHostDefault() {
      int type = IBOTypesCoreUi.TYPE_5_CANVAS_HOST;
      int size = IBOCanvasHost.CANVAS_HOST_BASIC_SIZE;
      ByteObject tech = cdc.getBOC().getByteObjectFactory().createByteObject(type, size);
      tech.set1(IBOCanvasHost.CANVAS_HOST_OFFSET_10_WRAPPER_TYPE1, ITechWrapper.WRAPPER_TYPE_1_FRAME);
      tech.set2(IBOCanvasHost.CANVAS_HOST_OFFSET_14_FRAMEPOS1, 0);
      return tech;
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
      int type = IBOTypesCoreUi.TYPE_8_FRAME_POS;
      int size = IBOFramePos.FPOS_BASIC_SIZE;
      ByteObject tech = cdc.getBOC().getByteObjectFactory().createByteObject(type, size);
      return tech;
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
    * Creates a Canvas.. 
    * <li> Wraps the Canvas in its host owner.
    * <li> Add the canvas to this {@link CoreUiCtx} list of managed {@link CanvasHostAbstract}
    * <li> no linking to a {@link ICanvasAppli}.
    * 
    * <p>
    * This method is final. It contains immutable logic
    * </p>
    * 
    * KEYMETHOD for create a CanvasHost.
    * 
    * @param boCanvasHost
    * @return
    */
   public final ICanvasHost createCanvasHost(ByteObject boCanvasHost) {
      if (boCanvasHost == null) {
         boCanvasHost = createBOCanvasHostDefault();
      }
      //#debug
      boCanvasHost.checkType(IBOTypesCoreUi.TYPE_5_CANVAS_HOST);

      IWrapperManager wrapperManager = getWrapperManager();
      WrapperAbstract wrapper = wrapperManager.createNewWrapper(boCanvasHost);
      wrapper.setWrapperManager(wrapperManager);
      if (wrapper == null) {
         throw new NullPointerException();
      }

      CanvasHostAbstract canvasHost = createCanvasHost(wrapper, boCanvasHost);
      //if method forgot to set the wrapper
      if (canvasHost.getWrapper() == null) {
         canvasHost.setWrapper(wrapper);
      }
      wrapper.setCanvasHost(canvasHost);

      //now that the wrapper and canvas are packed together. 
      canvasHost.setStartPositionAndSize();

      //where to host the canvas, in a new Frame?
      if (canvasRoot == null) {
         canvasRoot = canvasHost;
      }
      canvases.add(canvasHost);

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
    * {@link ITechWrapper#WRAPPER_TYPE_0_DEFAULT}
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

   /**
    * Shortcut to {@link UCtx#getApiManager()}
    * @return
    */
   public ApiManager getApiManager() {
      return getUC().getApiManager();
   }

   public BOCtx getBOC() {
      return cdc.getBOC();
   }

   public IExecutor getExecutor() {
      return getHost().getExecutor();
   }

   public int getBOCtxSettingSize() {
      return CTX_COREUI_BASIC_SIZE;
   }

   /**
    * Return an array of {@link ICanvasHost} located at that coordinate.
    * <br>
    * The first being the topmost canvas
    * what about the Z coordinate ? Impossible to get from Swing.
    * 
    * @param x
    * @param y
    * @return empty array if none
    */
   /**
    */
   public CanvasHostAbstract[] getCanvasAt(int x, int y) {
      //TODO keep track of top most canvas
      //problem is that the z cannot be determ
      IntToObjects itos = new IntToObjects(getUC(), canvases.nextempty);
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

   /**
    * Iterates over known {@link CanvasHostAbstract} and returns the first canvas with ID.
    * <p>
    * <li>It is an ID given consciously by the creator of the Canvas.
    * <li>This is NOT the array/order of creation kind of id.
    * <li>Stored in {@link IBOCanvasHost#CANVAS_HOST_OFFSET_03_ID2} 
    * </p>
    * @param id matched against {@link IBOCanvasHost#CANVAS_HOST_OFFSET_03_ID2} 
    * @return
    */
   public CanvasHostAbstract getCanvasFromID(int id) {
      for (int i = 0; i < canvases.nextempty; i++) {
         CanvasHostAbstract c = (CanvasHostAbstract) canvases.getObjectAtIndex(i);
         int cid = c.getBOCanvasHost().get2(IBOCanvasHost.CANVAS_HOST_OFFSET_03_ID2);
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

   public IConfigCoreUi getConfigUI() {
      if (config == null) {
         throw new IllegalStateException("IConfigUI not initialized");
      }
      return (IConfigCoreUi) config;
   }

   public ICtx[] getCtxSub() {
      return new ICtx[] { cdc };
   }

   /**
    * Returns the number of events
    * @return
    */
   private int[] getEventBaseTopology() {
      int[] events = new int[IEventsCoreUi.COREUI_NUM_EVENTS];
      events[IEventsCoreUi.PID_00] = IEventsCoreUi.PID_00_XX;
      events[IEventsCoreUi.PID_01] = IEventsCoreUi.PID_01_XX;
      events[IEventsCoreUi.PID_02] = IEventsCoreUi.PID_02_XX;
      return events;
   }

   public IEventBus getEventBus() {
      if (eventBus == null) {
         eventBus = new EventBusArray(getUC(), this, getEventBaseTopology(), IEventsCoreUi.A_SID_COREUI_EVENT_A);
      }
      return eventBus;
   }

   public IFontFactory getFontFactory() {
      return cdc.getFontFactory();
   }

   public IHost getHost() {
      return cdc.getHost();
   }

   public IHostData getHostData() {
      return getHost().getHostData();
   }

   public int getHostDataInt(int dataID) {
      return getHostData().getHostDataInt(dataID);
   }

   public IHostFeature getHostFeature() {
      return getHost().getHostFeature();
   }

   /**
    * 
    * @return
    */
   public abstract IHostGestures getHostGestures();

   public IHostService getHostService() {
      return getHost().getHostService();
   }

   /**
    * 
    * @return
    */
   public IImageFactory getImageFactory() {
      return cdc.getImageFactory();
   }

   public CoreUiSettings getInputSettings() {
      if (inputSettings == null) {
         inputSettings = new CoreUiSettings(this);
      }
      return inputSettings;
   }

   public abstract KeyMapAbstract getKeyMap();

   /**
    * Time controller for this framework
    * @return
    */
   public ITimeCtrl getTimeCtrl() {
      return getHost().getTimeCtrl();
   }

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
      //activate
      canvasHost.setCanvasAppliActive(true);
   }

   /**
    * Will be called when we need to apply incoming config to {@link IBOCtxSettingsCoreUi} ByteObject.
    * @param config
    * @param settings
    */
   protected void matchConfig(IConfigBO config, ByteObject settings) {
      IConfigCoreUi configUI = (IConfigCoreUi) config;
      settings.setFlag(CTX_COREUI_OFFSET_01_FLAG1, CTX_COREUI_FLAG_1_FULLSCREEN, configUI.isFullscreen());
   }

   public void onExit() {
      for (int i = 0; i < canvases.nextempty; i++) {
         CanvasHostAbstract c = (CanvasHostAbstract) canvases.getObjectAtIndex(i);
         c.onExit();
      }
   }

   /**
    * Publish the Event on all knows Canvas.
    * 
    * <li> Gamepads events use this method, as they cannot know which Canvas has its focus
    * <li> Most {@link EventAppli} will use this entry point since we want pauses to be forwarded to all Canvases.
    * @param se
    */
   public void publishEventOnAllCanvas(BEvent se) {
      for (int i = 0; i < canvases.nextempty; i++) {
         CanvasHostAbstract c = (CanvasHostAbstract) canvases.getObjectAtIndex(i);
         ICanvasAppli canvasAppli = c.getCanvasAppli();
         canvasAppli.event(se);
      }
   }

   public void publishEventOnRoot(BEvent be) {
      canvasRoot.getCanvasAppli().event(be);
   }

   public void publishMessageOnRoot(String msg) {
      EventAppli ae = new EventAppli(this, ITechEventApp.ACTION_11_MESSAGE);
      ae.setParamO1(msg);
      this.publishEventOnRoot(ae);
   }

   public void setMajOn(boolean b) {

   }

   public void setWrapperManager(IWrapperManager wrapperManager) {
      this.wrapperManager = wrapperManager;
   }

   public void showAllCanvases() {
      ICanvasAppli canvasAppli = canvasRoot.getCanvasAppli();
      canvasAppli.showNotify();
      canvasRoot.canvasShow();
      for (int i = 0; i < canvases.nextempty; i++) {
         if (canvases.objects[i] != canvasRoot) {
            CanvasHostAbstract canvas = (CanvasHostAbstract) canvases.objects[i];
            ICanvasAppli canvasAppli2 = canvas.getCanvasAppli();
            canvasAppli2.showNotify();
            canvas.canvasShow();
         }
      }
   }

   public void showCanvasAppli(ICanvasAppli canvas) {
      canvas.showNotify();
      canvas.getCanvasHost().canvasShow();
   }

   public void showCanvasHost(ICanvasHost canvas) {
      canvas.canvasShow();
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

      dc.nlLvl(cdc, CoreDrawCtx.class);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, CoreUiCtx.class);
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   /**
    * {@link IBOCtxSettingsCoreUi}
    */
   public void toStringCtxSettings(Dctx dc, ByteObject bo) {
      super.toStringCtxSettings(dc, bo);
      dc.nl();
      dc.append("#IBOCtxSettingsCoreUI");
      dc.nl();
      boolean isFullScreen = bo.hasFlag(CTX_COREUI_OFFSET_01_FLAG1, CTX_COREUI_FLAG_1_FULLSCREEN);
      dc.appendVarWithNewLine("isFullScreen", isFullScreen);

   }
   //#enddebug

   private void toStringPrivate(Dctx dc) {

   }

}
