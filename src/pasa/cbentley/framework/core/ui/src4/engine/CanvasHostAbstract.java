package pasa.cbentley.framework.core.ui.src4.engine;

import pasa.cbentley.byteobjects.src4.core.ByteObject;
import pasa.cbentley.byteobjects.src4.stator.StatorReaderBO;
import pasa.cbentley.byteobjects.src4.stator.StatorWriterBO;
import pasa.cbentley.core.src4.ctx.ICtx;
import pasa.cbentley.core.src4.event.BusEvent;
import pasa.cbentley.core.src4.event.IEventConsumer;
import pasa.cbentley.core.src4.interfaces.IExecutor;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.stator.ITechStator;
import pasa.cbentley.core.src4.stator.StatorReader;
import pasa.cbentley.core.src4.stator.StatorWriter;
import pasa.cbentley.framework.core.ui.src4.ctx.CoreUiCtx;
import pasa.cbentley.framework.core.ui.src4.ctx.IBOTypesCoreUi;
import pasa.cbentley.framework.core.ui.src4.ctx.IEventsCoreUi;
import pasa.cbentley.framework.core.ui.src4.ctx.ObjectCUC;
import pasa.cbentley.framework.core.ui.src4.event.EventAppli;
import pasa.cbentley.framework.core.ui.src4.event.BEvent;
import pasa.cbentley.framework.core.ui.src4.event.EventCanvasHost;
import pasa.cbentley.framework.core.ui.src4.event.DeviceEvent;
import pasa.cbentley.framework.core.ui.src4.event.DeviceEventXY;
import pasa.cbentley.framework.core.ui.src4.event.DeviceEventXYTouch;
import pasa.cbentley.framework.core.ui.src4.interfaces.ICanvasAppli;
import pasa.cbentley.framework.core.ui.src4.interfaces.ICanvasHost;
import pasa.cbentley.framework.core.ui.src4.interfaces.ITechEventHost;
import pasa.cbentley.framework.core.ui.src4.tech.IBOCanvasHost;
import pasa.cbentley.framework.core.ui.src4.tech.IBOFramePos;
import pasa.cbentley.framework.core.ui.src4.tech.ITechInput;
import pasa.cbentley.framework.core.ui.src4.tech.ITechCodes;
import pasa.cbentley.framework.core.ui.src4.tech.ITechFeaturesCanvas;
import pasa.cbentley.framework.core.ui.src4.tech.ITechHostUI;
import pasa.cbentley.framework.core.ui.src4.wrapper.WrapperAbstract;
import pasa.cbentley.framework.coredraw.src4.interfaces.IGraphics;

/**
 * Host implementation will use composition to draw to its host.
 * Event canvas.
 * 
 * @author Charles Bentley
 *
 */
public abstract class CanvasHostAbstract extends ObjectCUC implements ICanvasHost, IEventConsumer, IEventsCoreUi, ITechFeaturesCanvas {

   /**
    * {@link IBOCanvasHost} definition
    */
   protected ByteObject      boCanvasHost;

   /** 
    * The Bentley Framework {@link ICanvasAppli}
    */
   protected ICanvasAppli    canvasAppli;

   /**
    * Set to null when in Paused State
    */
   protected ICanvasAppli    canvasAppliActive;

   protected WrapperAbstract wrapper;

   public CanvasHostAbstract(CoreUiCtx cac, ByteObject boCanvasHost) {
      super(cac);
      if (boCanvasHost == null) {
         throw new NullPointerException();
      }
      this.boCanvasHost = boCanvasHost;
      //register for AppModule events
      cac.getEventBus().addConsumer(this, IEventsCoreUi.PID_01_DEVICE, IEventsCoreUi.PID_01_DEVICE_05_UPDATE);
      cuc.getEventBus().addConsumer(this, PID_02_CANVAS, PID_02_CANVAS_00_ANY);

      //#debug
      toDLog().pCreate("", this, CanvasHostAbstract.class, "Created@65", LVL_04_FINER, true);

   }

   /**
    * Event generated when the screen window is moved.
    * 
    * @param id
    * @param x
    * @param y
    */
   public void canvasPositionChangedBridge(int id, int x, int y) {
      if (canvasAppli != null) {
         EventCanvasHost de = new EventCanvasHost(cuc, ITechEventHost.ACTION_02_MOVED, this);
         de.setX(x);
         de.setY(y);
         canvasAppli.event(de);
      }
   }

   /**
    * The method creates a system wide event  {@link ITechEventHost#ACTION_03_RESIZED}. 
    * 
    * <p>
    * Called by implementing class when it detects a change in size of the canvas.
    * </p>
    * @param w
    * @param h
    */
   public void canvasSizeChangedBridge(int w, int h) {
      //#debug
      toDLog().pBridge("", this, CanvasHostAbstract.class, "canvasSizeChangedBridge@140", LVL_05_FINE, true);

      if (canvasAppli != null) {
         EventCanvasHost de = new EventCanvasHost(cuc, ITechEventHost.ACTION_03_RESIZED, this);
         de.setW(w);
         de.setH(h);
         canvasAppli.event(de);
      }
   }

   public void consumeEvent(BusEvent e) {
      if (e.getPID() == PID_02_CANVAS) {
         if (e.getProducer() == canvasAppli) {
            if (e.getEventID() == PID_02_CANVAS_01_TITLE) {
               titleIconComesticUpdate();
            }
         }
      }
   }

   public void eventBridgeGuiLater(final BEvent e) {

      //#debug
      toDLog().pFlow("Runnable", e, toStringGetLine(CanvasHostAbstract.class, "eventBridgeGuiLater", 127), LVL_05_FINE, true);

      IExecutor exec = cuc.getExecutor();
      exec.executeMainLater(new Runnable() {

         public void run() {
            eventBridge(e);

         }
      });
   }

   public void eventBridge(BEvent e) {
      if (canvasAppli != null) {
         canvasAppli.event(e);
      }
   }

   public void fingerMovedBridge(int x, int y, int screenID, int fingerID) {
      this.fingerMovedBridge(x, y, screenID, fingerID, 0, 0);
   }

   /**
    * A Finger is a touch point on a touchscreen.
    * <br>
    * @param x
    * @param y
    * @param pointerID 0 based
    * @param fingerID 0 based
    */
   public void fingerMovedBridge(int x, int y, int screenID, int fingerID, float size, float pressure) {
      if (canvasAppli != null) {
         DeviceEvent de = new DeviceEventXYTouch(cuc, screenID, ITechInput.MOD_3_MOVED, fingerID, x, y, size, pressure);
         canvasAppli.event(de);
      }
   }

   public void fingerPressedBridge(int x, int y, int screenID, int fingerID) {
      this.fingerPressedBridge(x, y, screenID, fingerID, 0, 0);
   }

   public void fingerPressedBridge(int x, int y, int screenID, int fingerID, float size, float pressure) {
      if (canvasAppli != null) {
         DeviceEvent de = new DeviceEventXYTouch(cuc, screenID, ITechInput.MOD_0_PRESSED, fingerID, x, y, size, pressure);
         canvasAppli.event(de);
      }
   }

   public void fingerReleasedBridge(int x, int y, int screenID, int fingerID) {
      this.fingerReleasedBridge(x, y, screenID, fingerID, 0, 0);
   }

   public void fingerReleasedBridge(int x, int y, int screenID, int fingerID, float size, float pressure) {
      if (canvasAppli != null) {
         DeviceEvent de = new DeviceEventXYTouch(cuc, screenID, ITechInput.MOD_1_RELEASED, fingerID, x, y, size, pressure);
         canvasAppli.event(de);
      }
   }

   /**
    * Called when
    */
   public void focusGainedBridge() {
      //#debug
      toDLog().pBridge("", null, CanvasHostAbstract.class, "focusGainedBridge@"+toStringGetLine(190));
      if (canvasAppli != null) {
         EventCanvasHost ge = new EventCanvasHost(cuc, ITechEventHost.ACTION_04_FOCUS_GAIN, this);
         canvasAppli.event(ge);
      }
   }

   /**
    * full appli focus lost.. not just a window
    */
   public void focusLostBridge() {
      //#debug
      toDLog().pBridge("", null, CanvasHostAbstract.class, "focusLostBridge@"+toStringGetLine(200));
      if (canvasAppli != null) {
         EventCanvasHost ge = new EventCanvasHost(cuc, ITechEventHost.ACTION_05_FOCUS_LOSS, this);
         canvasAppli.event(ge);
      }

   }

   public ByteObject getBOCanvasHost() {
      return boCanvasHost;
   }

   public ICanvasAppli getCanvasAppli() {
      return canvasAppli;
   }

   /**
    * Wrapper around {@link IBOCanvasHost#CANVAS_HOST_OFFSET_03_ID2} 
    * @return
    */
   public int getCanvasID() {
      return boCanvasHost.get2(IBOCanvasHost.CANVAS_HOST_OFFSET_03_ID2);
   }

   public ICtx getCtxOwner() {
      return cuc;
   }

   /**
    * {@link IBOTypesCoreUi#TYPE_8_FRAME_POS} of the {@link IBOCanvasHost}
    * 
    * When and where was it set in the first place ?
    * @return
    */
   public ByteObject getFramePOS() {
      return boCanvasHost.getSubFirst(IBOTypesCoreUi.TYPE_8_FRAME_POS);
   }

   public ByteObject getFramePosNewWithXYWH() {
      ByteObject framePos = cuc.createBOFrameDefault();
      setXYWHToFramePos(framePos);
      return framePos;
   }

   public int getScreenY(int y) {
      return y;
   }

   public int getScreenX(int x) {
      return x;
   }

   public int getStatorableClassID() {
      throw new RuntimeException("Must be implemented by subclass " + this.getClass().getName());
   }

   public String getTitle() {
      return wrapper.getTitle();
   }

   /**
    * Null if none was set using {@link CanvasHostAbstract#setWrapper(WrapperAbstract)}
    * 
    * @return {@link WrapperAbstract} 
    */
   public WrapperAbstract getWrapper() {
      return wrapper;
   }

   /**
    * Method from {@link ICanvasHost}
    */
   public void icRepaint() {
      icRepaint(0, 0, getICWidth(), getICHeight());
   }

   public void keyPressedBridge(int keyCode) {
      if (canvasAppliActive != null) {
         DeviceEvent de = new DeviceEvent(cuc, ITechInput.DEVICE_0_KEYBOARD, 0, ITechInput.MOD_0_PRESSED, keyCode);
         canvasAppliActive.event(de);
      }
   }

   /**
    * Caller must make sure there is no repeat
    * @param finalCode
    */
   public void keyReleasedBridge(int keyCode) {
      if (canvasAppliActive != null) {
         DeviceEvent de = new DeviceEvent(cuc, ITechInput.DEVICE_0_KEYBOARD, 0, ITechInput.MOD_1_RELEASED, keyCode);
         canvasAppliActive.event(de);
      }
   }

   public void mouseDraggedBridge(int x, int y, int pointerID) {
      //drags are moves. input module decides if it is a really drag or not.
      this.mouseMovedBridge(x, y, pointerID);
   }

   /**
    * TODO How to listen to keys released outside the frame?
    * <br>
    * Historically, we sent fake release events for every buttons pressed from this class. This is now
    * managed by the Framework input module on event Focus Out. The framework cannot trust consistency of
    * outside events anyways.
    * <br>
    * JInput gets all events even when outside of the frame.
    * <br>
    * @param x
    * @param y
    */
   public void mouseEnteredBridge(int x, int y) {
      if (canvasAppliActive != null) {
         //#debug
         toDLog().pBridge("x=" + x + " y=" + y, null, CanvasHostAbstract.class, "mouseEnteredBridge@290");
         DeviceEventXY dex = new DeviceEventXY(cuc, ITechInput.DEVICE_1_MOUSE, 0, ITechInput.MOD_3_MOVED, ITechInput.MOVE_1_ENTER, x, y);
         dex.setSource(canvasAppliActive);
         canvasAppliActive.event(dex);
      }
   }

   /**
    * Device event of type pointer. Exit and Entering Events.
    * Provides raw x, y and area
    * @param x
    * @param y
    */
   protected void mouseExitedBridge(int x, int y) {
      if (canvasAppliActive != null) {
         //#debug
         toDLog().pBridge("x=" + x + " y=" + y, null, CanvasHostAbstract.class, "mouseExitedBridge@306");
         DeviceEventXY dex = new DeviceEventXY(cuc, ITechInput.DEVICE_1_MOUSE, 0, ITechInput.MOD_3_MOVED, ITechInput.MOVE_2_EXIT, x, y);
         dex.setSource(canvasAppliActive);
         canvasAppliActive.event(dex);
      }
   }

   /**
    * TODO NativeController
    * When controlled by JInput disable if we have a NativeDeviceController. send position to Controller for 
    * NativeController sends BEvents to which Canvas?
    * @param x
    * @param y
    * @param id
    */
   public void mouseMovedBridge(int x, int y, int id) {
      if (canvasAppliActive != null) {
         DeviceEvent de = new DeviceEventXY(cuc, ITechInput.DEVICE_1_MOUSE, id, ITechInput.MOD_3_MOVED, 0, x, y);
         canvasAppliActive.event(de);
      }
   }

   public void mousePressedBridge(int x, int y, int pointerID, int button) {
      if (canvasAppliActive != null) {
         DeviceEvent de = new DeviceEventXY(cuc, ITechInput.DEVICE_1_MOUSE, pointerID, ITechInput.MOD_0_PRESSED, button, x, y);
         canvasAppliActive.event(de);
      }
   }

   public void mouseReleasedBridge(int x, int y, int pointerID, int button) {
      if (canvasAppliActive != null) {
         DeviceEvent de = new DeviceEventXY(cuc, ITechInput.DEVICE_1_MOUSE, pointerID, ITechInput.MOD_1_RELEASED, button, x, y);
         canvasAppliActive.event(de);
      }
   }

   /**
    * Wheel events.
    * @param scrollAmount
    * @param rot
    */
   public void mouseWheeledBridge(int scrollAmount, int rot) {
      if (canvasAppliActive != null) {
         int code;
         if (rot == -1) {
            //up
            code = ITechCodes.PBUTTON_3_WHEEL_UP;
         } else {
            //down
            code = ITechCodes.PBUTTON_4_WHEEL_DOWN;
         }
         DeviceEvent de = new DeviceEventXY(cuc, ITechInput.DEVICE_1_MOUSE, 0, ITechInput.MOD_5_WHEELED, 0, scrollAmount, code);
         canvasAppliActive.event(de);
      }
   }

   public void onExit() {
      if (wrapper != null) {
         wrapper.onExit();
      }
   }

   public void paintBridge(IGraphics g) {
      if (canvasAppli != null) {
         canvasAppli.paint(g);
      }
      //TODO generate an event for paint being finished?
      //driver.paintDone();
   }

   public void setCanvasAppli(ICanvasAppli dis) {
      canvasAppli = dis;
   }

   public boolean isCanvasAppliActive() {
      return canvasAppliActive != null;
   }

   public void setCanvasAppliActiveFalse() {
      setCanvasAppliActive(false);
   }

   public void setCanvasAppliActiveTrue() {
      setCanvasAppliActive(true);
   }

   public void setCanvasAppliActive(boolean b) {
      if (b) {
         canvasAppliActive = canvasAppli;
      } else {
         canvasAppliActive = null;
      }
   }

   /**
    * TODO difference between Host Full screen and 
    * How to set full screen mode in Swing?
    * That's the job of the {@link DeviceDriver}. Similarly for vibration and light uses.
    * <br>
    * When Canvas is the sole owner of the JFrame.
    * <br>
    * In the case of a Mosaic, the fullscreen call does nothing
    * @param mode
    */
   public void setFullScreenMode(boolean mode) {
      wrapper.setFullScreenMode(mode);
   }

   /**
    * Path to icon in application package
    * @param string
    */
   public void setIcon(String string) {
      wrapper.setIcon(string);
   }

   /**
    * Title of panel. If constrained in a Tab Panned, Title can be used as tab text.
    * When inside a Frame/Window, Title is the name of the frame.
    * @param string
    */
   public void setTitle(String string) {
      wrapper.setTitle(string);
   }

   /**
    * Called by {@link CoreUiCtx} when creating a new {@link ICanvasHost}.
    * 
    * It needs to have a wrapper to be shown on the host platform.
    * @param wrapper
    */
   public void setWrapper(WrapperAbstract wrapper) {
      this.wrapper = wrapper;
   }

   public void setXYWHToFramePos(ByteObject framePos) {
      //sets current pos. This is the absolute position
      int cx = this.getICX(); // what is this? the wrapper position or the component position which is 0
      int cy = this.getICY();
      int cw = this.getICWidth();
      int ch = this.getICHeight();
      framePos.set2(IBOFramePos.FPOS_OFFSET_02_X2, cx);
      framePos.set2(IBOFramePos.FPOS_OFFSET_03_Y2, cy);
      framePos.set2(IBOFramePos.FPOS_OFFSET_04_W2, cw);
      framePos.set2(IBOFramePos.FPOS_OFFSET_05_H2, ch);

   }

   public void stateReadFrom(StatorReader state) {
      //#debug
      toDLog().pStator("Reading...", state, CanvasHostAbstract.class, "stateReadFrom@436", LVL_04_FINER, true);
      if (state == null) {
         //#debug
         toDLog().pData("StatorReader is null for CanvasHostAbstract", this, CanvasHostAbstract.class, "stateReadFrom", LVL_05_FINE, true);
         return;
      }
      //boCanvasHost was read before creating the object.
      StatorReaderBO statorBo = (StatorReaderBO) state;
      wrapper = (WrapperAbstract) statorBo.dataReadObject(wrapper);
      canvasAppli = (ICanvasAppli) statorBo.dataReadObject(canvasAppli);

   }

   /**
    * See {@link CanvasAppliAbstract#stateWriteTo(StatorWriter)}
    */
   public void stateWriteTo(StatorWriter state) {
      //very important to check if boCanvasHost has been written
      //canvas host is written inside the canvasAppli call
      //why ? because canvasHost belongs to canvasAppli 
      //canvasHost is created by canvasAppli constructor.
      state.dataWriterToStatorable(wrapper);
      state.dataWriterToStatorable(canvasAppli);
   }

   public void stateWriteToParamSub(StatorWriter state) {
      state.dataWriteInt(ITechStator.MAGIC_WORD_OBJECT_PARAM);
      StatorWriterBO swbo = (StatorWriterBO) state;
      ByteObject boCanvasHost = this.getBOCanvasHost();
      //add a framepos ? Or is it computed whenever there is a change ?
      swbo.dataWriteByteObject(boCanvasHost);
   }

   /**
    * {@link IEventsCoreUi#PID_02_CANVAS_01_TITLE}
    */
   public void titleIconComesticUpdate() {

      String title = canvasAppli.getTitle();
      this.setTitle(title);
      String icon = canvasAppli.getIcon();
      if (icon != null) {
         this.setIcon(icon);
      } else {
         //try the appconfig default icon of the context
         String iconPathDefault = cuc.getConfigUI().getIconPathDefault();
         if (iconPathDefault != null) {
            this.setIcon(iconPathDefault);
         }
      }
      //#debug
      toDLog().pFlow("title=" + title + " icon=" + icon, this, CanvasHostAbstract.class, "titleIconComesticUpdate@line415", LVL_05_FINE, true);

   }

   /**
    * Toggle the Alias in Canvas Settings in
    */
   public void toggleAlias() {
      //TODO
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, CanvasHostAbstract.class, 456);
      toStringPrivate(dc);
      super.toString(dc.sup());

      dc.nlLvl(canvasAppli, "ICanvasAppli");
      dc.nlLvl(wrapper, "Wrapper");
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, CanvasHostAbstract.class, 470);
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   private void toStringPrivate(Dctx dc) {

   }

   //#enddebug

}
