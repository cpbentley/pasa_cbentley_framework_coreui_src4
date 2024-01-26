package pasa.cbentley.framework.coreui.src4.engine;

import pasa.cbentley.byteobjects.src4.core.ByteObject;
import pasa.cbentley.byteobjects.src4.stator.StatorReaderBO;
import pasa.cbentley.core.src4.ctx.ICtx;
import pasa.cbentley.core.src4.event.BusEvent;
import pasa.cbentley.core.src4.event.IEventConsumer;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.stator.StatorReader;
import pasa.cbentley.core.src4.stator.StatorWriter;
import pasa.cbentley.framework.coredraw.src4.interfaces.IGraphics;
import pasa.cbentley.framework.coreui.src4.ctx.CoreUiCtx;
import pasa.cbentley.framework.coreui.src4.ctx.IBOTypesCoreUI;
import pasa.cbentley.framework.coreui.src4.ctx.IEventsCoreUI;
import pasa.cbentley.framework.coreui.src4.ctx.ObjectCUC;
import pasa.cbentley.framework.coreui.src4.event.AppliEvent;
import pasa.cbentley.framework.coreui.src4.event.BEvent;
import pasa.cbentley.framework.coreui.src4.event.CanvasHostEvent;
import pasa.cbentley.framework.coreui.src4.event.DeviceEvent;
import pasa.cbentley.framework.coreui.src4.event.DeviceEventXY;
import pasa.cbentley.framework.coreui.src4.event.DeviceEventXYTouch;
import pasa.cbentley.framework.coreui.src4.interfaces.ICanvasAppli;
import pasa.cbentley.framework.coreui.src4.interfaces.ICanvasHost;
import pasa.cbentley.framework.coreui.src4.interfaces.ITechEventHost;
import pasa.cbentley.framework.coreui.src4.tech.IBOCanvasHost;
import pasa.cbentley.framework.coreui.src4.tech.IBOFramePos;
import pasa.cbentley.framework.coreui.src4.tech.IInput;
import pasa.cbentley.framework.coreui.src4.tech.ITechCodes;
import pasa.cbentley.framework.coreui.src4.tech.ITechHostUI;

/**
 * Host implementation will use composition to draw to its host.
 * Event canvas.
 * 
 * @author Charles Bentley
 *
 */
public abstract class CanvasHostAbstract extends ObjectCUC implements ICanvasHost, IEventConsumer, ITechHostUI, IEventsCoreUI {

   /** 
    * The Bentley Framework {@link ICanvasAppli}
    */
   protected ICanvasAppli    canvasAppli;

   /**
    * {@link IBOCanvasHost} definition
    */
   protected ByteObject      boCanvasHost;

   protected WrapperAbstract wrapper;

   public CanvasHostAbstract(CoreUiCtx cac, ByteObject boCanvasHost) {
      super(cac);
      if(boCanvasHost == null) {
         throw new NullPointerException();
      }
      this.boCanvasHost = boCanvasHost;
      //register for AppModule events
      cac.getEventBus().addConsumer(this, IEventsCoreUI.PID_1_DEVICE, IEventsCoreUI.EVENT_ID_01_DEVICE_UPDATE);
      cuc.getEventBus().addConsumer(this, PID_02_CANVAS, PID_02_CANVAS_0_ANY);
   }

   public int getStatorableClassID() {
      throw new RuntimeException("Must be implemented by subclass " + this.getClass().getName());
   }

   public ICtx getCtxOwner() {
      return cuc;
   }

   /**
    * Event generated when the screen window is moved.
    * <br>
    * @param id
    * @param x
    * @param y
    */
   public void canvasPositionChangedBridge(int id, int x, int y) {
      if (canvasAppli != null) {
         CanvasHostEvent de = new CanvasHostEvent(cuc, ITechEventHost.ACTION_2_MOVED, this);
         de.setX(x);
         de.setY(y);
         canvasAppli.event(de);
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
    * Wrapper around {@link IBOCanvasHost#TCANVAS_OFFSET_03_ID2} 
    * @return
    */
   public int getCanvasID() {
      return boCanvasHost.get2(IBOCanvasHost.TCANVAS_OFFSET_03_ID2);
   }

   /**
    * {@link IBOTypesCoreUI#TYPE_8_FRAME_POS} of the {@link IBOCanvasHost}
    * 
    * When and where was it set in the first place ?
    * @return
    */
   public ByteObject getFramePOS() {
      return boCanvasHost.getSubFirst(IBOTypesCoreUI.TYPE_8_FRAME_POS);
   }


   public void onExit() {
      if(wrapper != null) {
         wrapper.onExit();
      }
   }
   
   public ByteObject getFramePosNewWithXYWH() {
      ByteObject framePos = cuc.createBOFrameDefault();
      setXYWHToFramePos(framePos);
      return framePos;
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

   public void stateWriteTo(StatorWriter state) {
      state.writerToStatorable(wrapper);
      state.writerToStatorable(canvasAppli);
   }

   public void stateReadFrom(StatorReader state) {
      if(state == null) {
         //#debug
         toDLog().pData("StatorReader is null for CanvasHostAbstract", this, CanvasHostAbstract.class, "stateReadFrom", LVL_05_FINE, true);
         return;
      }
      StatorReaderBO statorBo = (StatorReaderBO) state;
      wrapper = (WrapperAbstract) statorBo.readObject(wrapper);
      canvasAppli = (ICanvasAppli) statorBo.readObject(canvasAppli);

   }

   /**
    * We create a system wide event. So that anyone can listen to this resize.
    * @param w
    * @param h
    */
   public void canvasSizeChangedBridge(int w, int h) {
      if (canvasAppli != null) {
         canvasAppli.eventCanvasSize(w, h);
         CanvasHostEvent de = new CanvasHostEvent(cuc, ITechEventHost.ACTION_3_RESIZED, this);
         de.setW(w);
         de.setH(h);
         canvasAppli.event(de);
      }
   }

   public void consumeEvent(BusEvent e) {
      if (e.getPID() == PID_02_CANVAS) {
         if (e.getProducer() == canvasAppli) {
            if (e.getEventID() == PID_02_CANVAS_1_TITLE) {
               titleIconComesticUpdate();
            }
         }
      }
   }

   public void eventBridge(BEvent g) {
      if (canvasAppli != null) {
         canvasAppli.event(g);
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
         DeviceEvent de = new DeviceEventXYTouch(cuc, screenID, IInput.MOD_3_MOVED, fingerID, x, y, size, pressure);
         canvasAppli.event(de);
      }
   }

   public void fingerPressedBridge(int x, int y, int screenID, int fingerID) {
      this.fingerPressedBridge(x, y, screenID, fingerID, 0, 0);
   }

   public void fingerPressedBridge(int x, int y, int screenID, int fingerID, float size, float pressure) {
      if (canvasAppli != null) {
         DeviceEvent de = new DeviceEventXYTouch(cuc, screenID, IInput.MOD_0_PRESSED, fingerID, x, y, size, pressure);
         canvasAppli.event(de);
      }
   }

   public void fingerReleasedBridge(int x, int y, int screenID, int fingerID) {
      this.fingerReleasedBridge(x, y, screenID, fingerID, 0, 0);
   }

   public void fingerReleasedBridge(int x, int y, int screenID, int fingerID, float size, float pressure) {
      if (canvasAppli != null) {
         DeviceEvent de = new DeviceEventXYTouch(cuc, screenID, IInput.MOD_1_RELEASED, fingerID, x, y, size, pressure);
         canvasAppli.event(de);
      }
   }

   public void focusGainedBridge() {
      //#debug
      toDLog().pBridge("", null, CanvasHostAbstract.class, "focusGainedBridge");
      if (canvasAppli != null) {
         AppliEvent ge = new AppliEvent(cuc, ITechEventHost.ACTION_4_FOCUS_GAIN);
         canvasAppli.event(ge);
      }
   }

   /**
    * full appli focus lost.. not just a window
    */
   public void focusLostBridge() {
      //#debug
      toDLog().pBridge("", null, CanvasHostAbstract.class, "focusLostBridge");
      if (canvasAppli != null) {
         AppliEvent ge = new AppliEvent(cuc, ITechEventHost.ACTION_5_FOCUS_LOSS);
         canvasAppli.event(ge);
      }

   }

   public ICanvasAppli getCanvasAppli() {
      return canvasAppli;
   }

   public ByteObject getBOCanvasHost() {
      return boCanvasHost;
   }

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
      if (canvasAppli != null) {
         DeviceEvent de = new DeviceEvent(cuc, IInput.DEVICE_0_KEYBOARD, 0, IInput.MOD_0_PRESSED, keyCode);
         canvasAppli.event(de);
      }
   }

   /**
    * Caller must make sure there is no repeat
    * @param finalCode
    */
   public void keyReleasedBridge(int keyCode) {
      if (canvasAppli != null) {
         DeviceEvent de = new DeviceEvent(cuc, IInput.DEVICE_0_KEYBOARD, 0, IInput.MOD_1_RELEASED, keyCode);
         canvasAppli.event(de);
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
      if (canvasAppli != null) {
         //#debug
         toDLog().pBridge("x=" + x + " y=" + y, null, CanvasHostAbstract.class, "mouseEnteredBridge");
         DeviceEventXY dex = new DeviceEventXY(cuc, IInput.DEVICE_1_MOUSE, 0, IInput.MOD_3_MOVED, IInput.MOVE_1_ENTER, x, y);
         dex.setSource(canvasAppli);
         canvasAppli.event(dex);
      }
   }

   /**
    * Device event of type pointer. Exit and Entering Events.
    * Provides raw x, y and area
    * @param x
    * @param y
    */
   protected void mouseExitedBridge(int x, int y) {
      if (canvasAppli != null) {
         //#debug
         toDLog().pBridge("x=" + x + " y=" + y, null, CanvasHostAbstract.class, "mouseExitedBridge");
         DeviceEventXY dex = new DeviceEventXY(cuc, IInput.DEVICE_1_MOUSE, 0, IInput.MOD_3_MOVED, IInput.MOVE_2_EXIT, x, y);
         dex.setSource(canvasAppli);
         canvasAppli.event(dex);
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
      if (canvasAppli != null) {
         DeviceEvent de = new DeviceEventXY(cuc, IInput.DEVICE_1_MOUSE, id, IInput.MOD_3_MOVED, 0, x, y);
         canvasAppli.event(de);
      }
   }

   public void mousePressedBridge(int x, int y, int pointerID, int button) {
      if (canvasAppli != null) {
         DeviceEvent de = new DeviceEventXY(cuc, IInput.DEVICE_1_MOUSE, pointerID, IInput.MOD_0_PRESSED, button, x, y);
         canvasAppli.event(de);
      }
   }

   public void mouseReleasedBridge(int x, int y, int pointerID, int button) {
      if (canvasAppli != null) {
         DeviceEvent de = new DeviceEventXY(cuc, IInput.DEVICE_1_MOUSE, pointerID, IInput.MOD_1_RELEASED, button, x, y);
         canvasAppli.event(de);
      }
   }

   /**
    * Wheel events.
    * @param scrollAmount
    * @param rot
    */
   public void mouseWheeledBridge(int scrollAmount, int rot) {
      if (canvasAppli != null) {
         int code;
         if (rot == -1) {
            //up
            code = ITechCodes.PBUTTON_3_WHEEL_UP;
         } else {
            //down
            code = ITechCodes.PBUTTON_4_WHEEL_DOWN;
         }
         DeviceEvent de = new DeviceEventXY(cuc, IInput.DEVICE_1_MOUSE, 0, IInput.MOD_5_WHEELED, 0, scrollAmount, code);
         canvasAppli.event(de);
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

   /**
    * Called by {@link CoreUiCtx} when creating a new {@link ICanvasHost}.
    * 
    * It needs to have a wrapper to be shown on the host platform.
    * @param wrapper
    */
   public void setWrapper(WrapperAbstract wrapper) {
      this.wrapper = wrapper;
   }

   /**
    * {@link IEventsCoreUI#PID_02_CANVAS_1_TITLE}
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
      dc.root(this, CanvasHostAbstract.class, 428);
      toStringPrivate(dc);
      super.toString(dc.sup());

      dc.nlLvl(canvasAppli, "ICanvasAppli");
      dc.nlLvl(wrapper, "Wrapper");
   }

   private void toStringPrivate(Dctx dc) {

   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "CanvasHostAbstract");
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   //#enddebug

}
