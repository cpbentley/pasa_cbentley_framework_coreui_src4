package pasa.cbentley.framework.coreui.src4.ctx;

import pasa.cbentley.byteobjects.src4.core.ByteObject;
import pasa.cbentley.byteobjects.src4.ctx.ABOCtx;
import pasa.cbentley.byteobjects.src4.ctx.BOCtx;
import pasa.cbentley.core.src4.ctx.ACtx;
import pasa.cbentley.core.src4.event.EventBusArray;
import pasa.cbentley.core.src4.event.IEventBus;
import pasa.cbentley.core.src4.interfaces.IExecutor;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.structs.IntToObjects;
import pasa.cbentley.framework.coredraw.src4.ctx.CoreDrawCtx;
import pasa.cbentley.framework.coredraw.src4.interfaces.IFontFactory;
import pasa.cbentley.framework.coredraw.src4.interfaces.IImageFactory;
import pasa.cbentley.framework.coreui.src4.engine.AbstractCanvasAppli;
import pasa.cbentley.framework.coreui.src4.engine.AbstractCanvasHost;
import pasa.cbentley.framework.coreui.src4.engine.WrapperAbstract;
import pasa.cbentley.framework.coreui.src4.event.BEvent;
import pasa.cbentley.framework.coreui.src4.event.GestureArea;
import pasa.cbentley.framework.coreui.src4.interfaces.ICanvasAppli;
import pasa.cbentley.framework.coreui.src4.interfaces.ICanvasHost;
import pasa.cbentley.framework.coreui.src4.interfaces.ICanvasWrapperManager;
import pasa.cbentley.framework.coreui.src4.interfaces.IConfigUI;
import pasa.cbentley.framework.coreui.src4.interfaces.IHostGestures;
import pasa.cbentley.framework.coreui.src4.tech.IFramePos;
import pasa.cbentley.framework.coreui.src4.tech.ITechCanvas;
import pasa.cbentley.framework.coreui.src4.tech.ITechUI;
import pasa.cbentley.framework.coreui.src4.utils.InputSettings;

/**
 * 
 * @author Charles Bentley
 *
 */
public abstract class CoreUiCtx extends ABOCtx implements IEventsCoreUI, ITechUI, ITechCtxSettingsCoreUI {

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
    * @see {@link AbstractAppli#createCanvas(ICanvasAppli, ByteObject)}
    */
   protected IntToObjects        canvases;

   /** 
    * Reference to the root Canvas of the Host
    * <br>
    * {@link ICanvasHost}
    */
   private AbstractCanvasHost    canvasRoot;

   protected final CoreDrawCtx   cdc;

   private IConfigUI             configUI;

   private EventBusArray         eventBus;

   private InputSettings         inputSettings;

   protected int                 pressedKeyCounter;

   protected int[]               pressedKeys = new int[20];

   private ICanvasWrapperManager wrapperManager;

   public CoreUiCtx(CoreDrawCtx cdc, IConfigUI configUI) {
      super(cdc.getBOC());
      this.cdc = cdc;
      canvases = new IntToObjects(cdc.getUCtx(), 2);
      this.configUI = configUI;

      //when settings don't exist yet. print the config to it
      if (hasCtxData()) {

      } else {
         //create settings based on config
         ByteObject sets = getSettingsBO();
      }

   }

   public int getBOSettingsCtxSize() {
      return CTX_COREUI_BASIC_SIZE;
   }

   /**
    * Called by Appli to create the {@link ICanvasHost} on which it will draw the {@link ICanvasAppli} .
    * <br>
    * Canvas is created according to the settings.
    * <br>
    * The constructor of {@link AbstractCanvasAppli} get the tech param.
    * <br>
    * The {@link ICanvasHost} belongs to the {@link IAppli}.
    * <br>
    * <li> Root Level Frame/Windows Canvas
    * <li> Slave Window Canvas
    * <li> 
    * <br>
    * This method does not call {@link ICanvasHost#canvasShow()}. It is the job of the caller.
    * <br>
    * When creating an embedded video canvas host ... TODO
    * <br>
    * <br>
    * When application is setting up from previous state, that state decides which {@link ICanvasAppli} is hosted
    * 
    * @param canvasAppli is not null. the canvas from the appli in need of a sleeve in the target host.
    * @param canvasTech could be null. then we use a default tech provided by the ctx
    */
   public ICanvasHost createCanvas(ICanvasAppli canvasAppli, ByteObject canvasTech) {
      if (canvasTech == null) {
         canvasTech = createDefaultCanvasTech();
      }

      AbstractCanvasHost canvasHost = createCanvasClass(canvasAppli, canvasTech);
      WrapperAbstract wrapper = getWrapperManager().createNewWrapper(canvasTech);
      if (wrapper == null) {
         throw new NullPointerException();
      }
      wrapper.setCanvasHost(canvasHost);
      canvasHost.setWrapper(wrapper);

      //registers the relevant events/listeners/observers
      canvasHost.setCanvasAppli(canvasAppli);
      //force title after the link. afterwards when changed inside app, event will call it
      canvasHost.titleIconComesticUpdate();

      //where to host the canvas, in a new Frame?
      if (canvasRoot == null) {
         canvasRoot = canvasHost;
      }
      canvases.add(canvasHost);

      return canvasHost;
   }

   /**
    * Create a Canvas within {@link CoreUiCtx}. 
    * 
    * If we are in Swing Context, it will create a Swing Canvas.
    * 
    * Cross boundary? Swing app wants to use JX Canvas for Browser.
    * 
    * The {@link ITechCanvas} provides some hints on what kind of canvas we want.
    * 
    * But the application cannot always have what it wants.
    * 
    * In the case of the launcher, the launcher decides which wrapper to use
    * 
    * {@link ITechCanvas#TCANVAS_TYPE_0_DEFAULT}
    * 
    * @param canvasAppli
    * @param canvasTech
    * @return
    */
   public abstract AbstractCanvasHost createCanvasClass(ICanvasAppli canvasAppli, ByteObject canvasTech);

   /**
    * {@link IBOTypesFrameworkUI#TYPE_5_CANVAS_TECH}
    * @return
    */
   public ByteObject createDefaultCanvasTech() {
      int type = IBOTypesFrameworkUI.TYPE_5_CANVAS_TECH;
      int size = ITechCanvas.TCANVAS_BASIC_SIZE;
      ByteObject tech = cdc.getBOC().getByteObjectFactory().createByteObject(type, size);
      tech.set1(ITechCanvas.TCANVAS_OFFSET_02_TYPE1, ITechCanvas.TCANVAS_TYPE_1_FRAME);
      tech.set1(ITechCanvas.TCANVAS_OFFSET_03_SCREEN_MODE1, ITechCanvas.SCREEN_0_TOP_NORMAL);
      tech.set1(ITechCanvas.TCANVAS_OFFSET_04_DEBUG_FLAGS1, 0);
      tech.set4(ITechCanvas.TCANVAS_OFFSET_05_BG_COLOR4, 0);
      tech.set1(ITechCanvas.TCANVAS_OFFSET_06_THREADING_MODE1, 0); //TODO
      tech.set2(ITechCanvas.TCANVAS_OFFSET_07_ICON_ID2, 0);
      tech.set2(ITechCanvas.TCANVAS_OFFSET_08_TITLE_ID_ID2, 0);
      tech.set2(ITechCanvas.TCANVAS_OFFSET_14_FRAMEPOS2, 0);
      return tech;
   }

   public ByteObject createDefaultFrameTech() {
      int type = IBOTypesFrameworkUI.FTYPE_8_FRAME_POS;
      int size = IFramePos.FPOS_BASIC_SIZE;
      ByteObject tech = cdc.getBOC().getByteObjectFactory().createByteObject(type, size);
      return tech;
   }

   /**
    * Creates
    * @return
    */
   public abstract ICanvasWrapperManager createWrapperManagerDefault();

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
   public AbstractCanvasHost[] getCanvasAt(int x, int y) {
      //TODO keep track of top most canvas
      //problem is that the z cannot be determ
      IntToObjects itos = new IntToObjects(getUCtx(), canvases.nextempty);
      for (int i = 0; i < canvases.nextempty; i++) {
         AbstractCanvasHost ch = (AbstractCanvasHost) canvases.objects[i];
         int rx = ch.getICX();
         int ry = ch.getICY();
         int rw = ch.getICWidth();
         int rh = ch.getICHeight();
         boolean isInside = GestureArea.isInside(x, y, rx, ry, rw, rh);
         if (isInside) {
            itos.add(ch);
         }
      }
      AbstractCanvasHost[] ar = new AbstractCanvasHost[itos.nextempty];
      itos.copyToArray(ar);
      return ar;
   }

   /**
    * Returns all canvases
    * @return
    */
   public AbstractCanvasHost[] getCanvases() {
      AbstractCanvasHost[] hosts = new AbstractCanvasHost[canvases.getLength()];
      canvases.copyToArray(hosts);
      return hosts;
   }

   public CoreDrawCtx getCDC() {
      return cdc;
   }

   public IConfigUI getConfigUI() {
      if (configUI == null) {
         throw new IllegalStateException("IConfigUI not initialized");
      }
      return configUI;
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

   /**
    * 
    * @param id
    * @return
    */
   public abstract int getHostInt(int id);

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

   /**
    * 
    * @return
    */
   public AbstractCanvasHost getRootCanvas() {
      return canvasRoot;
   }

   /**
    * Creates default if not set
    * @return
    */
   public ICanvasWrapperManager getWrapperManager() {
      if (wrapperManager == null) {
         wrapperManager = createWrapperManagerDefault();
      }
      return wrapperManager;
   }

   /**
    * Does this draw context support the given draw feature id.
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

   /**
    * Decided by the config, the settings
    * @return
    */
   public boolean isInverseNumPad28() {
      return false;
   }

   public void setMajOn(boolean b) {
      
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

   public void publishEvent(BEvent se, ICanvasAppli canvas) {
      if (canvas == null) {
         //send event with the root canvas as parameter
         canvas = canvasRoot.getCurrentDisplayable();
      }
      canvas.event(se);
   }

   /**
    * Asks the Canvas to run this in its GUI thread.
    * <br>
    * @param run
    */
   public abstract void runGUI(Runnable run);

   public void setWrapperManager(ICanvasWrapperManager wrapperManager) {
      this.wrapperManager = wrapperManager;
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, "CoreUiCtx");
      toStringPrivate(dc);
      super.toString(dc.sup());

      dc.nlLvl(wrapperManager, "wrapperManager");
      dc.nlLvl(canvasRoot, "canvasRoot");

      for (int i = 0; i < canvases.getLength(); i++) {
         AbstractCanvasHost canvas = (AbstractCanvasHost) canvases.getObjectAtIndex(i);
         dc.nlLvl(canvas, "AbstractCanvasHost");
      }
      dc.nlLvl(eventBus);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "CoreUiCtx");
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   private void toStringPrivate(Dctx dc) {

   }


   //#enddebug

}
