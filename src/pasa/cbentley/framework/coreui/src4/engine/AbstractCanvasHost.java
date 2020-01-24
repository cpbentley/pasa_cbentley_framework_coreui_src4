package pasa.cbentley.framework.coreui.src4.engine;

import pasa.cbentley.byteobjects.src4.core.ByteObject;
import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.event.BusEvent;
import pasa.cbentley.core.src4.event.IEventConsumer;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.framework.coredraw.src4.interfaces.IGraphics;
import pasa.cbentley.framework.coreui.src4.ctx.CoreUiCtx;
import pasa.cbentley.framework.coreui.src4.ctx.IEventsCoreUI;
import pasa.cbentley.framework.coreui.src4.event.AppliEvent;
import pasa.cbentley.framework.coreui.src4.event.BEvent;
import pasa.cbentley.framework.coreui.src4.event.CanvasHostEvent;
import pasa.cbentley.framework.coreui.src4.event.DeviceEvent;
import pasa.cbentley.framework.coreui.src4.event.DeviceEventXY;
import pasa.cbentley.framework.coreui.src4.event.DeviceEventXYTouch;
import pasa.cbentley.framework.coreui.src4.interfaces.ICanvasAppli;
import pasa.cbentley.framework.coreui.src4.interfaces.ICanvasHost;
import pasa.cbentley.framework.coreui.src4.interfaces.IHostEvents;
import pasa.cbentley.framework.coreui.src4.tech.IBCodes;
import pasa.cbentley.framework.coreui.src4.tech.IInput;
import pasa.cbentley.framework.coreui.src4.tech.ITechCanvas;
import pasa.cbentley.framework.coreui.src4.tech.ITechUI;

/**
 * Host implementation will use composition to draw to its host.
 * Event canvas.
 * 
 * @author Charles Bentley
 *
 */
public abstract class AbstractCanvasHost extends AbstractUITemplate implements ICanvasHost, IEventConsumer, ITechUI, IEventsCoreUI {

   /** 
    * The Bentley Framework {@link ICanvasAppli}
    */
   protected ICanvasAppli    canvasAppli;

   /**
    * {@link ITechCanvas} definition
    */
   protected ByteObject      tech;

   protected WrapperAbstract wrapper;

   public AbstractCanvasHost(CoreUiCtx cac) {
      super(cac);
      //register for AppModule events
      cac.getEventBus().addConsumer(this, IEventsCoreUI.PID_1_DEVICE, IEventsCoreUI.EVENT_ID_01_DEVICE_UPDATE);
      cuc.getEventBus().addConsumer(this, PID_02_CANVAS, PID_02_CANVAS_0_ANY);
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
         CanvasHostEvent de = new CanvasHostEvent(cuc, IHostEvents.ACTION_2_MOVED, this);
         de.setX(x);
         de.setY(y);
         canvasAppli.event(de);
      }
   }

   /**
    * @param id screen id where the origin of the canvas is located
    * @param w
    * @param h
    */
   public void canvasSizeChangedBridge(int id, int w, int h) {
      if (canvasAppli != null) {
         CanvasHostEvent de = new CanvasHostEvent(cuc, IHostEvents.ACTION_3_RESIZED, this);
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
      toLog().pBridge("", null, AbstractCanvasHost.class, "focusGainedBridge");
      if (canvasAppli != null) {
         AppliEvent ge = new AppliEvent(cuc, IHostEvents.ACTION_4_FOCUS_GAIN);
         canvasAppli.event(ge);
      }
   }

   /**
    * full appli focus lost.. not just a window
    */
   public void focusLostBridge() {
      //#debug
      toLog().pBridge("", null, AbstractCanvasHost.class, "focusLostBridge");
      if (canvasAppli != null) {
         AppliEvent ge = new AppliEvent(cuc, IHostEvents.ACTION_5_FOCUS_LOSS);
         canvasAppli.event(ge);
      }

   }

   public ICanvasAppli getCurrentDisplayable() {
      return canvasAppli;
   }

   public ByteObject getTech() {
      return tech;
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
         toLog().pBridge("x=" + x + " y=" + y, null, AbstractCanvasHost.class, "mouseEnteredBridge");
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
         toLog().pBridge("x=" + x + " y=" + y, null, AbstractCanvasHost.class, "mouseExitedBridge");
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
            code = IBCodes.PBUTTON_3_WHEEL_UP;
         } else {
            //down
            code = IBCodes.PBUTTON_4_WHEEL_DOWN;
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

   public void setWrapper(WrapperAbstract wrapper) {
      this.wrapper = wrapper;
   }

   /**
    * {@link IEventsCoreUI#PID_02_CANVAS_1_TITLE}
    */
   public void titleIconComesticUpdate() {
      //#debug
      toDLog().pFlow("", this, AbstractCanvasHost.class, "titleIconComesticUpdate", LVL_05_FINE, true);
      
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
   }

   /**
    * Toggle the Alias in Canvas Settings in
    */
   public void toggleAlias() {
      //TODO
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, "AbstractCanvasHost");
      toStringPrivate(dc);
      super.toString(dc.sup());

      dc.nlLvl(canvasAppli, "ICanvasAppli");
      dc.nlLvl(wrapper, "Wrapper");
   }

   private void toStringPrivate(Dctx dc) {

   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "AbstractCanvasHost");
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   //#enddebug

}
