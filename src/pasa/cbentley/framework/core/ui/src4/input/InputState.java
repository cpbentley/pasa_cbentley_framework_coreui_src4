package pasa.cbentley.framework.core.ui.src4.input;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.helpers.StringBBuilder;
import pasa.cbentley.core.src4.interfaces.C;
import pasa.cbentley.core.src4.io.BADataOS;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.core.src4.structs.CircularObjects;
import pasa.cbentley.core.src4.structs.IntBuffer;
import pasa.cbentley.core.src4.structs.IntToObjects;
import pasa.cbentley.core.src4.structs.listdoublelink.LinkedListDouble;
import pasa.cbentley.core.src4.structs.listdoublelink.ListElement;
import pasa.cbentley.core.src4.utils.BitUtils;
import pasa.cbentley.core.src4.utils.Geo2dUtils;
import pasa.cbentley.framework.core.ui.src4.ctx.CoreUiCtx;
import pasa.cbentley.framework.core.ui.src4.ctx.ObjectCUC;
import pasa.cbentley.framework.core.ui.src4.ctx.ToStringStaticCoreUi;
import pasa.cbentley.framework.core.ui.src4.engine.CanvasAppliAbstract;
import pasa.cbentley.framework.core.ui.src4.event.AppliEvent;
import pasa.cbentley.framework.core.ui.src4.event.BEvent;
import pasa.cbentley.framework.core.ui.src4.event.CanvasHostEvent;
import pasa.cbentley.framework.core.ui.src4.event.DeviceEvent;
import pasa.cbentley.framework.core.ui.src4.event.DeviceEventGroup;
import pasa.cbentley.framework.core.ui.src4.event.DeviceEventXY;
import pasa.cbentley.framework.core.ui.src4.event.DeviceEventXYTouch;
import pasa.cbentley.framework.core.ui.src4.event.EventKey;
import pasa.cbentley.framework.core.ui.src4.event.EventKeyDevice;
import pasa.cbentley.framework.core.ui.src4.event.EventKeyGridCrossing;
import pasa.cbentley.framework.core.ui.src4.event.EventKeyPosition;
import pasa.cbentley.framework.core.ui.src4.event.GestureArea;
import pasa.cbentley.framework.core.ui.src4.event.GestureEvent;
import pasa.cbentley.framework.core.ui.src4.event.GestureIdentity;
import pasa.cbentley.framework.core.ui.src4.event.GesturePath;
import pasa.cbentley.framework.core.ui.src4.event.GesturePointer;
import pasa.cbentley.framework.core.ui.src4.event.GestureUtils;
import pasa.cbentley.framework.core.ui.src4.event.ITechEventKey;
import pasa.cbentley.framework.core.ui.src4.event.RepeatEvent;
import pasa.cbentley.framework.core.ui.src4.event.SenseEvent;
import pasa.cbentley.framework.core.ui.src4.event.VoiceEvent;
import pasa.cbentley.framework.core.ui.src4.interfaces.ICanvasAppli;
import pasa.cbentley.framework.core.ui.src4.interfaces.ITechEventHost;
import pasa.cbentley.framework.core.ui.src4.tech.IInput;
import pasa.cbentley.framework.core.ui.src4.tech.ITechCodes;
import pasa.cbentley.framework.core.ui.src4.tech.ITechEvent;
import pasa.cbentley.framework.core.ui.src4.tech.ITechGestures;
import pasa.cbentley.framework.core.ui.src4.utils.CoreUiSettings;

/**
 * Encapsulates the state of input devices (touchscreen, keyboard etc).
 * <br>
 * User groups is an application level concern. At the level of {@link InputState}, there are no users.
 * <br>
 * Virtual devices creations: Application can create virtual devices which generates events (Virtual Keyboard ...)
 * Who decides of the ID? A mapping must be done to marry hardware level devices and application level virtual devices
 * to avoid ID collision at the level of the {@link InputState} and {@link DeviceEvent}. The Java Devices is usually zero.
 * <br>
 * Each device is given a dynamic device ID.
 * Now how can the Application statically refer to a device? A user wants 4 keyboards to create complex musical commands. 
 * In his mind the blue , red, green and black keyboard. Another user plugs a gamepad to play new musical commands
 * a serie of key presses identifies the device to another user. how are events colored for the command controller?
 * Color dimension belongs to one user. A user will want to use laptop mouse as AuxMouse in the command definition.
 * How is this linked? M1 is the default mouse (device). Ctrl+B is with the default/root keyboard.
 * M1[aux] Ctrl[aux]+B where aux is mapped to ID 1 for pointers. It could be Ctrl[red]+B[green]. red mapped to 1 and green mapped to 2. 0 being
 * given to the root.
 * AuxMouse moves on the X and Y axis may act as a wheel as well.
 * <br>
 * [virtual input device: letters in 3 groups. voyelles, consonnes du bas, consonnes du haut]
 * InputState only records states.
 * <br>
 * Class managing input of the User Input Thread and {@link Repeater2} thread. Managed by the {@link CanvasAppliInput}.
 * <br>
 * One input is appli bound.. And may be forwarded to an inside sub appli.
 * When several Canvas make the Appli where to forward
 * {@link CanvasAppliInput#processInputState(InputState)}. What is the central nervous system?
 * When one event occurs on one {@link CanvasAppliInput}, may it be forwarded on others?
 * In the paradigm of a Command controller, there is one controller that may require repaints of one or several Canvas
 * The {@link CanvasAppliInput} who forwarded the event does not matter.
 * The {@link CanvasResult} therefore is linked to the {@link CanvasAppliInput}. but when drawables from another
 * Canvas are requested to be drawn? A Drawable must have a Canvas ID along with its x and y
 * <br>
 * Application difference between local User and Network User.  Managed by the command processor
 * <br>
 * Therefore each {@link CanvasAppliInput} has 1 {@link InputState}...... but we have one Appli.
 * We shall have 1 Input State 
 * <br>
 * <br>
 * TODO 1 input state for many canvas because the Input state is a snapshot of the whole application not just one of its windows.
 * <br>
 * Yet, there should be specific settings per canvas.
 * {@link InputState#getFastKeyTypeValue()} is not inputstate
 * <br>
 * {@link InputState} is not a singleton though. 
 * {@link EventControllerQueued} creates several state copies in a multi thread GUI set up.
 * <br>
 * But each copies encapsulates the whole user input state 
 * <br>
 * In J2SE, each event is localized to a Frame. The Input controller decides what happens and if other frame components have
 * to be repainted. J2SE is single user. Bentley provides a multi user framework. Where 2 user may work on 2 different 
 * Canvas at the same time. One keyboard focus on the first frame, Second keyboard focus on the 2nd frame.
 * In fullscreen, the Windows Tool bar focus feedback won't be visible. however all events of both keyboards will be managed
 * by one InputState and the application's command controller will link keyboard IDs to user commands.
 * 
 * <br>
 * It gives View classes a snapshot of the current input configuration. It is a flat command trigger.
 * <br>
 * Models the state of keys and pointers. It remembers the past key events.
 * <br>
 * Check field isUserInputThread to know whether context is UserInputThread. This is used as a Lock
 * <br>
 * When programmatically doing moves on a {@link IDrawable}, only the {@link CanvasResult} is used
 * When moving because of an animation hook,
 * <br>
 * <br>
 * This allow the controlling code to query for complex triggers of keys and mouse.
 * <br>
 * <b>Repeats</b> : <br>
 * <br>
 * <br>
 * <b>Key Locks</b> <br>
 * Any Key can become LOCKED. It is advisable for usability to provide clear and bright visual feedback
 * You can lock a few keys states<br>
 * <li>##
 * <li>#*
 * <li>**
 * <li>*#
 * <br>
 * For locking, the second key has to be pressed rapidly after the first. If any other key is pressed, the lock does not hold.<br>
 * These locks let the user access different key mappings. <br>
 * So instead of **3 **4 **6, user will do ** LOCK 3,4,6 UNLOCK. <br>
 * <br>
 * Tool is similar to the CAPS LOCK mechanism on PC keyboards
 * <br>
 * <br>On a phone to lock ## you do ##_ SOFTLEFT
 * <br>
 * To unlock Do it again.**_ SOFTLEFT is going to lock a new state
 * <br>
 * <b>Pressed Lock vs Full Lock</b><br>
 * <li>A pressed lock is active until the key trigger is released<br>
 * <li>A Full lock is active until explicitely canceled
 *  <br>
 *  <b>Lock State Machine</b> <br>
 * <li> Key * is typed once. 
 * <li>* pressed fast again.
 * <li> Mode to pressedLock.
 * <li> if * key is released fast InputConfig is locked to ** and lockPress is set to false.
 * <li>
 *  <br>

 * <br>
 * <b>J2SE Driver</b>. 
 * <br>
 * The framework is able to live on J2SE. Mouse information is one of the major differences.
 * <li>Mouse button presses.
 * <li>Mouse button drag.
 * <li>Mouse button release.
 * <li>New Mouse location. The Drawable that has the mouse in its area, delegate down the Drawable hierarchy until
 * a Drawable act on it. Mostly Drawables change their {@link IDrawable#STYLE_07_FOCUSED_POINTER} state. 
 * <br>
 * <br>
 * Uses phone drivers to map to code recieved by the J2ME keyPressed method.
 * <br>
 * Since those applications are ported to the laptop, code may handle many different key codes
 * <br>
 * Kodes of the Framework
 * <br>
 * 
 * <br>
 * <br>
 * 
 * <b>Inversions</b> : <br>
 * <br>
 * In the {@link IDrawable}, the method is reserved for mouse mouvement. It will be used
 * for application specifically developped for J2SE.
 * An event is created when the mouse mouse.
 * <br>
 * <br>
 * <br>
 * <br>
 * @author Charles-Philip Bentley
 *
 */
public abstract class InputState extends ObjectCUC implements IInput, ITechGestures, IStringable {

   private static final int      MAX_HISTORY            = 100;

   private DeviceKeys            activeDevice;

   private CircularObjects       beEventsHistory;

   /**
    * When an event cancel others.. we create a hierarchy.
    * a root event, generates sub events to be process before parent event is process.
    * when root event is processed, it may generate post event at his level
    * or post event parent or post event root..
    * a Pointer on a virtual keyboard queue an event in the local queue
    */
   private BEvent                bEventActive;

   /**
    * Track the last 100 press and releases with their orders
    */
   private LinkedListDouble      deviceEventHistoryPressRelease;

   DeviceKeys                    deviceKeyMainKeyboard;

   DeviceKeys                    deviceKeyMainPointer;

   DeviceKeys                    deviceKeyMainTouchScreen;

   /**
    * 
    */
   DeviceKeys[]                  deviceKeysGamePads;

   /**
    * Each device has a {@link DeviceKeys} tracking which keys are pressed.
    * <br>
    * This array tracks all instances of keyboards, pads etcs.
    * <br>
    * Non null values inside.
    */
   DeviceKeys[]                  deviceKeysKeyboard;

   /**
    * Tracks {@link DeviceKeys} object for pointers.
    * <br>
    * Null at first. Created on demand.
    * <br>
    * starts at the second pointer
    */
   DeviceKeys[]                  deviceKeysPointers;

   DeviceKeys[]                  deviceKeysScreens;

   /**
    * {@link ICanvasAppli} on which the last event occurred
    */
   protected CanvasAppliAbstract eventCanvas;

   /**
    * TODO use Tri
    * TriggerUnit
    * This ease the work of command controller the keys units are already constructed
    * 
    * Match of a trigger with InputState is nice to have anyways.
    * How do Context? Some kind of Gesture Event?
    */
   private BEvent                eventCurrent;

   private int                   eventID;

   private BEvent                eventPrevious;

   /**
    * History of last events. 10 history
    */
   private BEvent[]              eventTimeLine          = new BEvent[10];

   /**
    * State change counter.
    */
   private int                   eventUniID;

   /**
    * This flag is never resetted 
    */
   private int                   flags;

   /**
    * milliseconds for which to ignore repeat events. probably because of faulty Host like Linux
    * stupid enough to send fake events 
    */
   int                           ignoreTooFastKeyEvents = 20;

   private boolean               isAutoMouseGesture     = false;

   private boolean               isInnerEvents          = false;

   /**
    * Records every single events and stores it.
    */
   private boolean               isRecording;

   private LinkedListDouble      keysEmptyList;

   private AppliEvent            lastActionEvent;

   private CanvasHostEvent       lastCanvasEvent;

   private DeviceEvent           lastDeviceEvent;

   /**
    * Never null. init with a empty object
    */
   private DeviceEventXY         lastDeviceEventXY;

   private DeviceEvent           lastDeviceKeyPress;

   private DeviceEvent           lastDeviceKeyRelease;

   private GestureEvent          lastGestureEvent;

   /**
    * never null. initialized with empty event
    */
   private KeyEventListed        lastKeyEvent;

   private Pointer               lastPointer;

   private DeviceEventXY[]       lastPointerEvents;

   private RepeatEvent           lastRepeatEvent;

   private SenseEvent            lastSensorEvent;

   private long                  loopDelta;

   private int                   modSequence;

   /**
    * Performance tracker fo
    */
   private int                   numTotalActiveGestures;

   private BADataOS              output;

   /**
    * non null values of available pointers
    */
   private Pointer[]             pointers;

   /**
    * List of {@link GesturePointer} that can be reused
    */
   private LinkedListDouble      pointersEmpty;

   /**
    * 
    */
   private LinkedListDouble      pointersHistory;

   private double                simulDelta;

   private double                simulTime;

   private long                  timeCurrent;

   /**
    * Delta since the previous event
    */
   private long                  timeCurrentDelta;

   private long                  timePrevious;

   protected final UCtx          uc;

   protected InputState(CoreUiCtx cuc) {
      super(cuc);
      this.uc = cuc.getUC();
      pointers = new Pointer[] { new Pointer(cuc, 0) };
      lastPointer = pointers[0];
      lastPointerEvents = new DeviceEventXY[10];
      deviceKeyMainKeyboard = new DeviceKeys(cuc, this, IInput.DEVICE_0_KEYBOARD, 0);
      //make it null but check when first created
      deviceKeyMainPointer = new DeviceKeys(cuc, this, IInput.DEVICE_1_MOUSE, 0);
      deviceKeyMainTouchScreen = new DeviceKeys(cuc, this, IInput.DEVICE_4_SCREEN, 0);

      beEventsHistory = new CircularObjects(uc, 10);
      ignoreTooFastKeyEvents = 10;
      //SET dummy mode
      lastDeviceEvent = new DeviceEvent(cuc, 0, 0, 0, 0);
      lastDeviceEventXY = new DeviceEventXY(cuc, 0, 0, 0, 0, 0, 0);
      eventCurrent = lastDeviceEvent;
      eventPrevious = lastDeviceEventXY;

      keysEmptyList = new LinkedListDouble(uc);
      DeviceEvent emptyEvent = new DeviceEvent(cuc, 0, 0, 0, 0);
      for (int i = 0; i < 10; i++) {
         KeyEventListed ke = new KeyEventListed(this, keysEmptyList, emptyEvent);
         ke.addToList();
      }
      deviceEventHistoryPressRelease = new LinkedListDouble(uc);
      for (int i = 0; i < 50; i++) {
         DeviceEventListed del = new DeviceEventListed(deviceEventHistoryPressRelease, this);
         del.addToList();
      }
      pointersHistory = new LinkedListDouble(uc);
      pointersEmpty = new LinkedListDouble(uc);
      for (int i = 0; i < 10; i++) {
         GesturePointer gp = new GesturePointer(pointersHistory, cuc, -1);
         gp.addToList();
      }
      //usual max pointers
      lastKeyEvent = new KeyEventListed(this, keysEmptyList, emptyEvent);
   }

   /**
    * There are different wheels.
    * <li>Wheel with always 1 as increment.
    * <li>Wheel with a variable increment. A driving wheel will send
    * several events 
    * 
    * 
    * @param DeviceEventXY X as rotation and Y axis as increment. 
    * The wheel has an ID with {@link DeviceEventXY#getDeviceButton()}
    * Device type depends.
    * 
    * When several mouses with wheels are connected.
    * <br>
    * @param rotation
    */
   private boolean addDeviceWheeled(DeviceEventXY dex) {
      lastDeviceEventXY = dex;
      setEventID(ITechEvent.EVID_20_WHEEL);
      return true;
   }

   /**
    * InputState is interested in 
    * <li>{@link DeviceEvent} 
    * <li>{@link GestureEvent}
    * <li>{@link SenseEvent}
    * <br>
    * <br>
    * Trail events are Gesture Events, they are forwarded to the command but there are not a trigger per se.
    * <br>
    * Sensors may also be forwarded to a command that will take action
    * @param tu
    * @return true if event was accepted, false is event was dropped because not valid state change.
    */
   public boolean addEvent(BEvent be, CanvasAppliAbstract canvas) {
      //#debug
      toDLog().pFlow("", be, InputState.class, "addEvent@434", LVL_03_FINEST, DEV_0_1LINE_THREAD);
      //time the event here
      timePointerEvent();
      long time = cuc.getTimeCtrl().getNowClock();
      be.setTime(time);
      eventCanvas = canvas;
      boolean res = true;
      int eventType = be.getType();
      if (eventType == IInput.TYPE_2_GESTURE) {
         GestureEvent tu = (GestureEvent) be;
         res = addEventGesture(tu);
      } else if (eventType == TYPE_1_DEVICE) {
         DeviceEvent tu = (DeviceEvent) be;
         res = addEventDevice(tu);
      } else if (eventType == TYPE_4_REPEAT) {
         RepeatEvent tu = (RepeatEvent) be;
         res = addEventRepeat(tu);
      } else if (eventType == TYPE_6_APPLI) {
         AppliEvent ae = (AppliEvent) be;
         res = addEventAction(ae);
      } else if (eventType == TYPE_7_GROUP) {
         DeviceEventGroup ae = (DeviceEventGroup) be;
         res = addEventGroup(ae);
      } else if (eventType == TYPE_3_CANVAS) {
         CanvasHostEvent ae = (CanvasHostEvent) be;
         res = addEventCanvas(ae);
      } else {
         res = false;
         //#debug
         toDLog().pEvent("Unknown Event Type " + eventType, be, InputState.class, "addEvent");
      }
      if (res) {
         eventPrevious = eventCurrent;
         eventCurrent = be;
         eventUniID++;
         eventCurrent.setID(eventUniID);
         beEventsHistory.addObject(be);
         //must be done last so eventCurrent is correctly used
         newEventCanceler(be);
      }
      return res;
   }

   private boolean addEventAction(AppliEvent ae) {
      int mode = ae.getAction();
      if (mode == ITechEventHost.ACTION_5_FOCUS_LOSS) {
         resetPresses();
      }
      lastActionEvent = ae;
      return true;
   }

   private boolean addEventCanvas(CanvasHostEvent ae) {
      lastCanvasEvent = ae;
      setEventID(ITechEvent.EVID_40_CANVAS);
      return true;
   }

   private boolean addEventDevice(DeviceEvent tu) {
      int mode = tu.getDeviceMode();
      int deviceType = tu.getDeviceType();
      boolean res = false;
      if (deviceType == IInput.DEVICE_0_KEYBOARD) {
         eventCanvas.fixRotationKey(tu); //update up/down left/right keys relative to rotated screen
         if (mode == MOD_0_PRESSED) {
            res = addPressedKeyboard(tu);
         } else if (mode == MOD_1_RELEASED) {
            res = addKeyReleased(tu);
         }
      } else if (deviceType == IInput.DEVICE_1_MOUSE) {
         //check for first time event
         DeviceEventXY dex = (DeviceEventXY) tu;
         eventCanvas.fixRotation(dex);
         if (mode == MOD_0_PRESSED) {
            res = addMouseKeyPressed(dex);
         } else if (mode == MOD_1_RELEASED) {
            res = addMouseKeyReleased(dex);
         } else if (mode == MOD_5_WHEELED) {
            res = addDeviceWheeled(dex);
         } else if (mode == MOD_3_MOVED) {
            res = addMouseMoved(dex);
         }
      } else if (deviceType == IInput.DEVICE_2_GAMEPAD) {
         if (mode == MOD_0_PRESSED) {
            res = addPadPressed(tu);
         } else if (mode == MOD_1_RELEASED) {
            res = addPadReleased(tu);
         }
      } else if (deviceType == IInput.DEVICE_4_SCREEN) {
         DeviceEventXYTouch dex = (DeviceEventXYTouch) tu;
         if (mode == MOD_0_PRESSED) {
            res = addFingerPressed(dex);
         } else if (mode == MOD_1_RELEASED) {
            res = addFingerReleased(dex);
         } else if (mode == MOD_3_MOVED) {
            res = addFingerMoved(dex);
         }
      } else if (deviceType == IInput.DEVICE_7_SENSOR) {
         SenseEvent se = (SenseEvent) tu;
         res = addSenseEvent(se);
      } else {
         res = false;
         //#debug
         toDLog().pEvent("Unknown Device Type " + deviceType, tu, InputState.class, "addEventDevice");
      }
      if (res) {
         lastDeviceEvent = tu;
         commonEventEnd();
         if (isRecording) {
            recordEventDevice(tu);
         }
      }
      return res;
   }

   /**
    * Adds {@link GestureInput} to the {@link InputState}.
    * <br>
    * Code checks for {@link InputState#isGestured()}
    * Then
    * <br>
    * Called when a Host Gesture is sent down the pipe.
    * <br>
    * <br>
    * <br>
    * 
    * @param g
    */
   public boolean addEventGesture(GestureEvent g) {
      //#debug
      toDLog().pFlow("", g, InputState.class, "addGesture@683", LVL_03_FINEST, true);
      lastGestureEvent = g;
      commonEventEnd();
      setEventID(ITechEvent.EVID_15_GESTURE);
      return true;
   }

   private boolean addEventGroup(DeviceEventGroup ae) {
      return true;
   }

   private boolean addEventRepeat(RepeatEvent re) {
      //#debug
      toDLog().pEvent1(" Thread=" + Thread.currentThread(), re, InputState.class, "addEventRepeat@line594");

      //TODO special case when only one key? do we use the Mod
      lastRepeatEvent = re;
      return true;
   }

   /**
    * Called when pointerID exited from the Canvas.
    * <br>
    * This resets the move {@link GesturePointer}.
    * <br>
    * @param x
    * @param y
    * @param pointerID
    */
   public void addExit(int x, int y, int pointerID) {
      //#debug
      toDLog().pEvent("pointerID " + pointerID, null, InputState.class, "addExit");
      GesturePointer gp = getGesturePointer0(pointerID);

   }

   /**
    * Finger is a pointer device.
    * <br>
    *  don't have but
    * @param dex
    * @return
    */
   private boolean addFingerMoved(DeviceEventXYTouch dex) {
      int pointerID = getFingerPointerID(dex);
      return addPointerMoved(dex, pointerID);
   }

   /**
    * Finger press events may have extra info such as size and pressure
    * @param dex
    * @return
    */
   private boolean addFingerPressed(DeviceEventXYTouch dex) {
      //pointer id for fingers? don't we want a screen as a device and the input point
      //as buttons? and pressure points ?
      int screenID = dex.getDeviceID();
      DeviceKeys dk = getDeviceKeysTouchScreen(screenID);
      boolean res = addKeyEventListedPress(dex, dk);
      if (res) {
         int pointerID = getFingerPointerID(dex);
         addPointerPressed(dex, pointerID);
      }
      return res;
   }

   private boolean addFingerReleased(DeviceEventXYTouch dex) {
      int screenID = dex.getDeviceID();
      DeviceKeys dk = getDeviceKeysPointer(screenID);
      boolean res = addKeyEventListedRelease(dex, dk);
      if (res) {
         int pointerID = getFingerPointerID(dex);
         addPointerReleased(dex, pointerID);
      }
      return res;
   }

   private boolean addKeyEventListedPress(DeviceEvent de, DeviceKeys dk) {
      int key = de.getDeviceButton();
      //anti linux bugs but could also work against buggy devices TODO test in emulator
      boolean isBug = dk.antiBugPress(key, de.getTime());
      if (isBug) {
         return false;
      }
      //checkk if pressed already
      KeyEventListed ke = dk.getKeyEventPressed(key);
      if (ke == null) {
         ke = createNewKeyEvent(dk, de);
      } else {
         //key is already pressed return false
         return false;
      }
      lastDeviceKeyPress = de;
      lastKeyEvent = ke;
      addKeyHistory(de);
      return true;
   }

   private boolean addKeyEventListedRelease(DeviceEvent de, DeviceKeys dk) {
      int key = de.getDeviceButton();
      //code remove if we know for sure there is no bug on the system
      boolean isBug = dk.antiBugRelease(key, de.getTime());
      if (isBug) {
         return false;
      }
      KeyEventListed ke = getKeyEventPressed(de);
      if (ke == null) {
         //this is possible because released events might be sent after
         //the application resets
         //we just have to ignore the release event
         return false;
      }
      //different type. within its device up to all press/release events
      //for it to be 
      //when not type. create event and link it
      ke.setRelease(de);
      lastDeviceKeyRelease = de;
      lastKeyEvent = ke;
      addKeyHistory(de);
      return true;
   }

   private void addKeyHistory(DeviceEvent de) {
      //#debug
      //canvas.toLog().ptEvent("", deviceEventHistoryPressRelease, InputState.class, "addKeyHistory");

      DeviceEventListed del = (DeviceEventListed) deviceEventHistoryPressRelease.getHead();
      del.resetTo(de);
      deviceEventHistoryPressRelease.moveHeadToTail();
   }

   /**
    * Add release key event to the InputConfig. <br>
    * Update the trigger.
    * <br>
    * TODO How to deal with the Linux Bug releasing
    * Upon release store timestamp and key
    * Upon press. if release store is younger than 10 millis ignore
    * idea is to ignore too close events
    * @param key
    */
   public boolean addKeyReleased(DeviceEvent de) {
      int boardID = de.getDeviceID();
      DeviceKeys dk = getDeviceKeysKeyboard(boardID);
      boolean res = addKeyEventListedRelease(de, dk);
      if (res) {
         setEventID(ITechEvent.EVID_02_KEYBOARD_RELEASE);
      }
      return res;
   }

   /**
    * Register Pointer event.
    * For each button type, there is a {@link GestureDetector}
    * <br>
    * For double taps
    * <li> P0 P0 P1 P1
    * <li> P0 P1 P0 P1
    * <br>
    * <br>
    * Both are double tap, but the latter is mixed
    * <br>
    * Assume release event might be lost or that the pointer is already pressed.
    * <br>
    * 
    * A Pointer button might already be pressed when 2 mouses act as 1.
    * <br>
    * <br>
    * 
    * @param x
    * @param y
    */
   private boolean addMouseKeyPressed(DeviceEventXY dex) {
      int pointerID = dex.getDeviceID();
      DeviceKeys dk = getDeviceKeysPointer(pointerID);
      boolean res = addKeyEventListedPress(dex, dk);
      if (res) {
         addPointerPressed(dex, pointerID);
         if (isAutoMouseGesture) {
            GesturePointer gp1 = gesturePointerAdd(pointerID);
            gp1.setDebugName("AutoMouse"); //looks up for slide flings
            EventKeyDevice edkFire = new EventKeyDevice(cuc, ITechEventKey.KEY_TYPE_4_ACTIVATE_FIRE, dex, ITechEventKey.EVENT_KEY_DEVICE_MODE_TYPE_1_SAME);
            gp1.setEventKeyFire(edkFire); //set the event key that started the gesture 
            EventKeyDevice edkCancelReleaseFire = new EventKeyDevice(cuc, ITechEventKey.KEY_TYPE_2_FIRE_AND_CANCEL, dex);
            gp1.addKey(edkCancelReleaseFire); //one time.. gesture disappears
         }
      }

      return res;

   }

   private boolean addMouseKeyReleased(DeviceEventXY dex) {
      int pointerID = dex.getDeviceID();
      DeviceKeys dk = getDeviceKeysPointer(pointerID);
      boolean res = addKeyEventListedRelease(dex, dk);
      if (res) {
         addPointerReleased(dex, pointerID);
      }
      return res;
   }

   private boolean addMouseMoved(DeviceEventXY dex) {
      int pointerID = dex.getDeviceID();
      return addPointerMoved(dex, pointerID);
   }

   private int addNumKeysPress(DeviceKeys[] ar, int v) {
      if (ar != null) {
         for (int i = 0; i < ar.length; i++) {
            v += ar[i].getNumKeysPressed();
         }
      }
      return v;
   }

   private boolean addPadAxis(DeviceEvent de, int code1, int cod2) {
      KeyEventListed ke = getKeyEventPressed(code1, de.getDeviceID(), de.getDeviceType());
      if (ke == null) {
         ke = getKeyEventPressed(cod2, de.getDeviceID(), de.getDeviceType());
         if (ke != null) {
            de.updateButton(cod2);
         }
      } else {
         de.updateButton(code1);
      }
      if (ke != null) {
         ke.setRelease(de);
         lastDeviceKeyRelease = de;
         lastKeyEvent = ke;
         addKeyHistory(de);
         return true;
      } else {
         return false;
      }
   }

   private boolean addPadPressed(DeviceEvent de) {
      int boardID = de.getDeviceID();
      DeviceKeys dk = getDeviceKeyGamePads(boardID);
      boolean res = addKeyEventListedPress(de, dk);
      if (res) {
         setEventID(ITechEvent.EVID_15_PAD_PRESS);
         commonEventEnd();
      }
      return res;
   }

   /**
    * There is a trick here with AXIS
    * @param de
    * @return
    */
   private boolean addPadReleased(DeviceEvent de) {
      int boardID = de.getDeviceID();
      DeviceKeys dk = getDeviceKeyGamePads(boardID);
      boolean res = false;
      if (de.getDeviceButton() == ITechCodes.AXIS_X) {
         res = addPadAxis(de, ITechCodes.PAD_LEFT, ITechCodes.PAD_RIGHT);
      } else if (de.getDeviceButton() == ITechCodes.AXIS_Y) {
         res = addPadAxis(de, ITechCodes.PAD_UP, ITechCodes.PAD_DOWN);
      } else {
         res = addKeyEventListedRelease(de, dk);
      }
      if (res) {
         setEventID(ITechEvent.EVID_16_PAD_RELEASE);
         commonEventEnd();
      }
      return res;
   }

   /**
    * When a pointer is moving, it updates the {@link GesturePointer} of registered pressed
    * keys and the freekey {@link GesturePointer}. 
    * You cannot really trust outside values.
    * <br>
    * Most application logic will always register
    * gesture for a pointer.
    * Application logic will register free pointer movement gesture as well. 
    * <br>
    * Host might behave buggy. So state consistency must be check.
    * <br>
    * For a move to occur, we need a release event.. if none was recorded
    * we throw an exception?
    * Move occurs on all recorded {@link GesturePointer} objects
    * @param x
    * @param y
    * @param pointerID
    */
   private boolean addPointerMoved(DeviceEventXY dex, int pointerID) {
      int x = dex.getX();
      int y = dex.getY();
      timePointerEvent();
      lastDeviceEventXY = dex;
      Pointer p = getPointerLazy(pointerID);
      p.setLastPointerEvent(dex);
      lastPointer = p;
      //just do add move to all
      LinkedListDouble lld = getPointerGestureList(pointerID);
      GesturePointer head = (GesturePointer) lld.getHead();
      GesturePointer gp = head;
      while (gp != null) {
         gp.addMove(x, y, dex.getTime());
         gp = (GesturePointer) gp.getNext();
      }
      commonEventEnd();
      setEventID(ITechEvent.EVID_13_POINTER_MOVE);
      return true;
   }

   private void addPointerPressed(DeviceEventXY dex, int pointerID) {
      lastDeviceEventXY = dex;
      Pointer p = getPointerLazy(pointerID);
      p.setLastPointerEvent(dex);
      lastPointer = p;
      setEventID(ITechEvent.EVID_11_POINTER_PRESS);
   }

   private void addPointerReleased(DeviceEventXY dex, int pointerID) {
      lastDeviceEventXY = dex;
      Pointer p = getPointerLazy(pointerID);
      p.setLastPointerEvent(dex);
      lastPointer = p;
      setEventID(ITechEvent.EVID_12_POINTER_RELEASE);
   }

   /**
    * All presses 
    * Buggy hosts might send key presses repetit.
    * How to deal with the Linux Bug releasing
    * <li>Upon release store timestamp and key
    * <li>Upon press. if release store is younger than 10 millis ignore
    * idea is to ignore too close events
    * @param key
    * @return false when key is already pressed and event should not be processed
    */
   private boolean addPressedKeyboard(DeviceEvent de) {
      int boardID = de.getDeviceID();
      DeviceKeys dk = getDeviceKeysKeyboard(boardID);
      boolean res = addKeyEventListedPress(de, dk);
      if (res) {
         setEventID(ITechEvent.EVID_01_KEYBOARD_PRESS);
      }
      return res;
   }

   /**
    * Collect {@link KeyEventListed} from {@link DeviceKeys} and add them to {@link IntToObjects}.
    * <br>
    * @param dk
    * @param ito
    */
   private void addPressKeys(DeviceKeys dk, IntToObjects ito) {
      LinkedListDouble keysPressedList = dk.getPressedKeysList();
      KeyEventListed ke = (KeyEventListed) keysPressedList.getHead();
      while (ke != null) {
         ito.add(ke);
         ke = (KeyEventListed) ke.getNext();
      }
   }

   private void addPressKeys(DeviceKeys[] dk, IntToObjects ito) {
      if (dk != null) {
         for (int i = 0; i < dk.length; i++) {
            addPressKeys(dk[i], ito);
         }
      }
   }

   private boolean addSenseEvent(SenseEvent se) {
      lastSensorEvent = se;
      //no state to keep here? what if several
      return true;
   }

   /**
    * 
    * @param deviceKeys
    * @param id
    * @param deviceType
    * @return
    */
   private DeviceKeys[] appDeviceKeys(DeviceKeys[] deviceKeys, int id, int deviceType) {
      int index = id - 1;
      if (deviceKeys == null) {
         deviceKeys = new DeviceKeys[index + 1];
         for (int i = 0; i < deviceKeys.length; i++) {
            deviceKeys[i] = new DeviceKeys(cuc, this, deviceType, i + 1);
         }
      }
      if (deviceKeys.length <= id) {
         DeviceKeys[] old = deviceKeys;
         DeviceKeys[] newd = new DeviceKeys[index + 1];
         for (int i = 0; i < old.length; i++) {
            newd[i] = old[i];
         }
         for (int i = old.length; i < newd.length; i++) {
            newd[i] = new DeviceKeys(cuc, this, deviceType, i + 1);
         }
         deviceKeys = newd;
      }
      return deviceKeys;
   }

   private void checkType(BEvent be) {
      int eventType = be.getType();
      if (eventType == IInput.TYPE_2_GESTURE) {
         GestureEvent tu = (GestureEvent) be;
      } else if (eventType == TYPE_1_DEVICE) {
         DeviceEvent tu = (DeviceEvent) be;
         int deviceType = tu.getDeviceType();
         if (deviceType == IInput.DEVICE_0_KEYBOARD) {

         } else if (deviceType == IInput.DEVICE_1_MOUSE) {
            DeviceEventXY dex = (DeviceEventXY) tu;
         } else if (deviceType == IInput.DEVICE_2_GAMEPAD) {
         } else if (deviceType == IInput.DEVICE_3_FINGER) {
            DeviceEventXY dex = (DeviceEventXY) tu;
         } else if (deviceType == IInput.DEVICE_4_SCREEN) {
            DeviceEventXYTouch dex = (DeviceEventXYTouch) tu;
         } else {
            throw new IllegalStateException("Unknown deviceType " + deviceType);
         }
      } else if (eventType == TYPE_4_REPEAT) {
         RepeatEvent tu = (RepeatEvent) be;
      } else if (eventType == TYPE_5_CTX_CHANGE) {
      } else if (eventType == TYPE_6_APPLI) {
         AppliEvent ae = (AppliEvent) be;
      } else if (eventType == TYPE_7_GROUP) {
         DeviceEventGroup ae = (DeviceEventGroup) be;
      } else {
         throw new IllegalStateException("Unknown eventType " + eventType);
      }
   }

   /**
    * Make InputConfig keys virgin. No more triggering
    */
   public void clearKeys() {
   }

   /**
    * Copy data from parameters to object so that
    * when {@link InputState} is modified, there is zero memory overlap between the two.
    * @param ic
    */
   public void cloneFrom(InputState ic) {
      this.modSequence = ic.modSequence;
      cloneFromSub(ic);
      this.eventID = ic.eventID;
      this.beEventsHistory = ic.beEventsHistory;
      this.activeDevice = ic.activeDevice;
      this.eventCurrent = ic.eventCurrent;
      this.eventPrevious = ic.eventPrevious;

      this.lastActionEvent = ic.lastActionEvent;
      this.lastCanvasEvent = ic.lastCanvasEvent;
      this.lastDeviceEvent = ic.lastDeviceEvent;
      this.lastDeviceEventXY = ic.lastDeviceEventXY;
      this.lastDeviceKeyPress = ic.lastDeviceKeyPress;
      this.lastDeviceKeyRelease = ic.lastDeviceKeyRelease;
      this.lastGestureEvent = ic.lastGestureEvent;
      this.lastKeyEvent = ic.lastKeyEvent;
      this.lastPointer = ic.lastPointer;
      this.lastPointerEvents = ic.lastPointerEvents;
      this.lastRepeatEvent = ic.lastRepeatEvent;
      this.lastSensorEvent = ic.lastSensorEvent;
   }

   /**
    * Override the clone
    * @param ic
    */
   public void cloneFromSub(InputState ic) {

   }

   /**
    * All events,including repeating event, call this method at the end of their addEvent method
    */
   private void commonEventEnd() {

   }

   /**
    * Computes the position of the pointer in the box..
    * -1 if outside.
    * <li> {@link C#ANC_0_TOP_LEFT}
    * <li> {@link C#ANC_2_TOP_RIGHT}
    * <li> {@link C#ANC_6_BOT_LEFT}
    * <li> {@link C#ANC_8_BOT_RIGHT}
    * 
    * @param x
    * @param y
    * @param w
    * @param h
    * @return
    */
   public int computeXYGrid4Position(int x, int y, int w, int h) {
      GesturePointer gp = getGesturePointer0(0);
      return gp.computeXYGrid4Position(x, y, w, h);
   }

   public int computeXYGrid9Position(int x, int y, int w, int h) {
      GesturePointer gp = getGesturePointer0(0);
      return gp.computeXYGrid3x3Position(x, y, w, h);
   }

   private GestureEvent createDragGesture(GesturePointer gp, EventKey fireKey, GestureIdentity gi, int typeMatch) {
      int type = gp.getDragGestureType();
      GestureEvent ge = null;
      if (type == typeMatch || typeMatch == ITechGestures.GESTURE_TYPE_8_SWINGS) {
         //is dir matching
         int dir = gi.getDir();
         boolean isDirectionMatch = false;
         int gestureDir = gp.getDirection();
         if (dir == ITechGestures.GESTURE_DIR_00_ANY) {
            isDirectionMatch = true;
         } else if (dir == ITechGestures.GESTURE_DIR_01_VERTICAL) {
            isDirectionMatch = dir == ITechGestures.GESTURE_DIR_03_TOP || dir == ITechGestures.GESTURE_DIR_04_BOT;
         } else if (dir == ITechGestures.GESTURE_DIR_02_HORIZONTAL) {
            isDirectionMatch = dir == ITechGestures.GESTURE_DIR_05_LEFT || dir == ITechGestures.GESTURE_DIR_06_RIGHT;
         } else {
            isDirectionMatch = dir == gestureDir;
         }
         if (isDirectionMatch) {
            ge = new GestureEvent(cuc, type, gp);
         }
      }
      return ge;
   }

   /**
    * The pointer has entered the {@link GestureArea}. Based on the {@link GestureIdentity} create a {@link GestureEvent}.
    * <br>
    * The {@link EventKey} is a {@link EventKeyPosition}.
    * <br>
    * When Gesture request a specific entry angle/position... Gesture
    * @param gp not null
    * @param fireKey not null
    * @param garea 
    * @param gi null null
    * @return
    */
   private GestureEvent createGestureEnter(GesturePointer gp, EventKey fireKey, GestureArea garea, GestureIdentity gi) {
      int grid = gi.grid;
      GestureEvent ge = null;
      //Enter may only have one point.. go is to detect which area
      //
      if (fireKey instanceof EventKeyPosition) {

      }
      //0 is any position
      if (gi.pos != 0) {
         int x = gp.getReleasedX();
         int y = gp.getReleasedY();
         int pos = GestureUtils.computeXYGridPosition(x, y, garea, grid);
         //generic position flag top
         if (GestureUtils.isMatchPosition(pos, gi.pos)) {
            ge = new GestureEvent(cuc, ITechGestures.GESTURE_TYPE_4_ENTER, gp);
         }
      } else {
         ge = new GestureEvent(cuc, ITechGestures.GESTURE_TYPE_4_ENTER, gp);
      }
      return ge;
   }

   /**
    * Depending on the type create a {@link GestureEvent} from the {@link GesturePointer}
    * data.
    * <br>
    * When a {@link GesturePointer} is activated by a key press, the caller knows which gesture it is looking for.
    * So for each type of gestures, one {@link GesturePointer} is created.
    * <br>
    * When the {@link GestureIdentity} is null, a {@link ITechGestures#GESTURE_TYPE_0_RAW} is fired. The gesture is identified
    * by handler catching {@link GestureEvent}s.
    * <br>
    * The {@link GestureIdentity} managed the creation of the {@link GestureEvent}.
    * <br>
    * ONe can create new custom types of gestures by sub class {@link GestureIdentity}
    * <br>
    * @param gp {@link GesturePointer}
    * @param fireKey the {@link EventKey} requesting gesture analysis (key release, pointer move over area)
    */
   private GestureEvent createGestureEvent(GesturePointer gp, EventKey fireKey) {
      GestureEvent ge = null;
      GestureIdentity gi = gp.getIdentity();
      //gi is never null
      int gtype = gi.getType();
      GestureArea garea = gi.getGestureArea();
      if (garea == null) {
         garea = eventCanvas.getGACanvas();
      }
      if (gtype == ITechGestures.GESTURE_TYPE_0_RAW) {
         //
         ge = new GestureEvent(cuc, ITechGestures.GESTURE_TYPE_0_RAW, gp);
         //when gesture identify is not known the event code identifies itself what it is looking 
         //for. The Bentley framework provides some gesture code
      } else if (gtype == ITechGestures.GESTURE_TYPE_1_DRAG_SLIDE) {
         ge = createGesturePath(gp, fireKey, garea, gi);
      } else if (gtype == ITechGestures.GESTURE_TYPE_6_PATH) {
         ge = createGesturePath(gp, fireKey, garea, gi);
      } else if (gtype == ITechGestures.GESTURE_TYPE_4_ENTER) {
         ge = createGestureEnter(gp, fireKey, garea, gi);
      } else if (gtype == ITechGestures.GESTURE_TYPE_5_EXIT) {
         ge = createGestureExit(gp, fireKey, garea, gi);
      } else if (gtype == ITechGestures.GESTURE_TYPE_2_SWIPE) {
         ge = createDragGesture(gp, fireKey, gi, ITechGestures.GESTURE_TYPE_2_SWIPE);
      } else if (gtype == ITechGestures.GESTURE_TYPE_3_FLING) {
         ge = createDragGesture(gp, fireKey, gi, ITechGestures.GESTURE_TYPE_3_FLING);
      } else if (gtype == ITechGestures.GESTURE_TYPE_7_ALLER_RETOUR) {
         ge = createDragGesture(gp, fireKey, gi, ITechGestures.GESTURE_TYPE_7_ALLER_RETOUR);
      } else if (gtype == ITechGestures.GESTURE_TYPE_8_SWINGS) {
         ge = createDragGesture(gp, fireKey, gi, ITechGestures.GESTURE_TYPE_8_SWINGS);
      }
      if (ge == null) {
         //#debug
         toDLog().pEvent1("" + fireKey, gp, InputState.class, "createGestureEvent");
      } else {
         gp.setGestureEvent(ge);
      }
      return ge;

   }

   private GestureEvent createGestureExit(GesturePointer gp, EventKey fireKey, GestureArea garea, GestureIdentity gi) {
      int grid = gi.grid;
      int dir = gi.dir;

      GestureEvent ge = null;
      //Enter may only have one point.. go is to detect which area
      //
      if (fireKey instanceof EventKeyPosition) {

      }
      //TODO in exit, we may have a vector indicating the exit
      //on a circle
      //0 is any position
      if (gi.pos != 0) {
         int x = gp.getReleasedX();
         int y = gp.getReleasedY();
         int pos = GestureUtils.computeXYGridPosition(x, y, garea, grid);
         boolean isMatch = GestureUtils.isMatchPath(pos, dir);
         if (isMatch) {
            ge = new GestureEvent(cuc, ITechGestures.GESTURE_TYPE_5_EXIT, gp);
         }
      } else {
         ge = new GestureEvent(cuc, ITechGestures.GESTURE_TYPE_5_EXIT, gp);
      }
      return ge;
   }

   /**
    * The {@link EventKey} type defines if the path event is continuous or
    * @param gp
    * @param fireKey
    * @param garea
    * @param gi
    * @return
    */
   private GestureEvent createGesturePath(GesturePointer gp, EventKey fireKey, GestureArea garea, GestureIdentity gi) {
      int grid = gi.grid;
      GestureEvent ge = gp.getEvent();
      //gesture is looking for a path
      int keyType = fireKey.getKeyType();
      //difference between continuous event that sends a single value and one time event when canceled to send all
      if (keyType == ITechEventKey.KEY_TYPE_0_FIRE) {
         //continuous fires 
         EventKeyGridCrossing ekg = (EventKeyGridCrossing) fireKey;
         //updates the gesture path?
         int newGrid = ekg.getCurrentGrid();
         if (ge == null) {
            ge = new GesturePath(cuc, grid, new int[] { newGrid }, gp, garea);
         } else {
            ((GesturePath) ge).addPath(newGrid);
         }
      } else if (keyType == ITechEventKey.KEY_TYPE_2_FIRE_AND_CANCEL) {
         //build it anyways.. 
         int[] da = gp.getPath(grid, garea);
         ge = new GesturePath(cuc, grid, da, gp, garea);
      } else {
         throw new IllegalStateException("" + keyType);
      }
      return ge;
   }

   public EventKeyDevice createInverseDeviceEvent() {
      DeviceEvent de = getLastDeviceEvent();
      EventKeyDevice edkDevice = new EventKeyDevice(cuc, ITechEventKey.KEY_TYPE_1_CANCEL, de);
      return edkDevice;
   }

   /**
    * Called when a press event is generated.
    * @param dk
    * @param de
    * @return
    */
   private KeyEventListed createNewKeyEvent(DeviceKeys dk, DeviceEvent de) {
      LinkedListDouble keysPressedList = dk.getPressedKeysList();
      KeyEventListed ke = (KeyEventListed) keysEmptyList.getHead();
      if (ke == null) {
         ke = new KeyEventListed(this, keysPressedList, de);
         ke.addToList();
      } else {
         ke.setListAndAdd(keysPressedList);
      }
      ke.setPressReset(de, dk);
      return ke;
   }

   /**
    * Create a {@link RepeatEvent} with source being the last key event {@link KeyEventListed}.
    * <br>
    * No cancelers.
    * @return
    */
   public RepeatEvent createRepeatEventLastDevicePress() {
      RepeatEvent er = new RepeatEvent(cuc);
      KeyEventListed ke = lastKeyEvent;
      ke.setRepeat(er);
      er.setSource(ke);
      return er;
   }

   public RepeatEvent createRepeatEventVirgin() {
      RepeatEvent er = new RepeatEvent(cuc);
      return er;
   }

   public void doSimulMix() {
      DeviceEventGroup degMix = this.getSimultaneousMixed();
      if (degMix != null) {
         this.queuePost(degMix);
      }
   }

   public boolean doSimulPress() {
      DeviceEventGroup deg = this.getSimultaneousPressed();
      if (deg != null) {
         //we want the simul device group to be fired when sure, it is not upgraded with another event
         //cancel previous simulgroup? isn't a timer suppposed to play the event?
         //
         this.queuePost(deg);
         return true;
      }
      return false;
   }

   public boolean doSimulRelease() {
      DeviceEventGroup deg = this.getSimultaneousReleased();
      if (deg != null) {
         //we want the simul device group to be fired when sure, it is not upgraded with another event
         //cancel previous simulgroup? isn't a timer suppposed to play the event?
         this.queuePost(deg);
         return true;
      }
      return false;
   }

   void dotrimListKey(LinkedListDouble keysHistoryList) {
      if (keysHistoryList.getNumElements() > MAX_HISTORY + 50) {
         for (int i = 0; i < 50; i++) {
            ListElement head = keysHistoryList.getHead();
            head.removeFromList();
            head.setListAndAdd(keysEmptyList);
         }
      }
   }

   private void generateRelease(DeviceKeys dk) {
      if (dk != null) {
         KeyEventListed[] kes = dk.getPressedKeys();
         for (int i = 0; i < kes.length; i++) {
            //queue a release event
            DeviceEvent press = kes[i].deviceEventPressed;
            int type = press.getDeviceType();
            int id = press.getDeviceID();
            int button = press.getDeviceButton();
            if (press instanceof DeviceEventXY) {
               DeviceEventXY de = new DeviceEventXY(cuc, type, id, IInput.MOD_1_RELEASED, button, 0, 0);
               queuePost(de);
            } else {
               DeviceEvent de = new DeviceEvent(cuc, type, id, IInput.MOD_1_RELEASED, button);
               queuePost(de);
            }
         }
      }
   }

   private void generateRelease(DeviceKeys[] ar) {
      if (ar != null) {
         for (int i = 0; i < ar.length; i++) {
            generateRelease(ar[i]);
         }
      }
   }

   /**
    * Create a {@link GesturePointer} for the pointer ID.
    * 
    * You can then create gestures for other 
    * With null key
    * @param pointerID
    * @return
    */
   public GesturePointer gesturePointerAdd(int pointerID) {
      //#debug
      toDLog().pFlow("pointerID=" + pointerID, this, InputState.class, "gesturePointerAdd", LVL_03_FINEST, true);
      Pointer p = getPointerLazy(pointerID);
      LinkedListDouble pointerGestureList = getPointerGestureList(pointerID);
      GesturePointer gp = new GesturePointer(pointerGestureList, cuc, pointerID);
      gp.addToList();
      long pressTime = this.getTimeCurrent();
      gp.setPressed(p.getX(), p.getY(), pressTime, 0);
      numTotalActiveGestures++;
      return gp;
   }

   private void gesturePointerRemove(GesturePointer gp) {
      //#debug
      toDLog().pEvent1("", gp, InputState.class, "gesturePointerRemove");
      gp.removeFromList();
      numTotalActiveGestures--;
      //TODO if we use a factory pooling for gesture pointer
   }

   //   public GesturePointer gesturePointerAddKey(int pointerID, EventKey ke) {
   //      return gesturePointerAddKey(pointerID, ke, null);
   //   }
   //
   //   /**
   //    * Create a gesture pointer at current pointer position.
   //    * One applicatin may decide to register the gesture of a pointer when
   //    * a pointer enter inside an area. And Gesture stops when it exits.
   //    * Gesture is finalized.
   //    * <br>
   //    * The Gesture will automatically unregisters when the key is released.
   //    * <br>
   //    * When a pointerID from a finger is released. All GesturePointer for that pointer
   //    * should be removed.
   //    * <br>
   //    * There is a one to one relationship between GesturePointer and its key.
   //    * For a given pointer, one gesture for a {@link KeyEventListed}.
   //    * @param pointerID
   //    * @param ke
   //    */
   //   public GesturePointer gesturePointerAddKey(int pointerID, EventKey ke, GestureIdentity gi) {
   //      //check if key is already registered
   //      if (gesturePointerGet(pointerID, ke) != null) {
   //         throw new IllegalStateException("");
   //      }
   //      Pointer p = getCreatePointer(pointerID);
   //      LinkedListDouble pointerGestureList = getPointerGestureList(pointerID);
   //      GesturePointer gp = new GesturePointer(pointerGestureList, dd, pointerID);
   //      gp.addToList();
   //      gp.addKey(ke);
   //      gp.setGesture(gi);
   //      long pressTime = this.getTimeCurrent();
   //      gp.setPressed(p.getX(), p.getX(), pressTime, 0);
   //      numTotalActiveGestures++;
   //      return gp;
   //   }
   //
   //   /**
   //    * 
   //    * @param pointerID
   //    * @param fireKey
   //    * @return
   //    */
   //   public GesturePointer gesturePointerGet(int pointerID, EventKey fireKey) {
   //      Pointer p = getCreatePointer(pointerID);
   //      LinkedListDouble lld = p.getGestureList();
   //      GesturePointer head = (GesturePointer) lld.getHead();
   //      GesturePointer gp = head;
   //      while (gp != null) {
   //         GesturePointer gpNext = (GesturePointer) gp.getNext();
   //         if (gp.getKeyFire() == fireKey) {
   //            return gp;
   //         }
   //         gp = gpNext;
   //      }
   //      return null;
   //   }

   public int getDeviceID() {
      return lastDeviceEvent.getDeviceID();
   }

   // remove because new event test all gesture keys
   //   /**
   //    * When a release key event is generated .
   //    * Sets the {@link GesturePointer} for Gesture analysis.
   //    * <br>
   //    * stop recording pointer movement for a gesture
   //    * @param pointerID
   //    * @param ke
   //    */
   //   public GesturePointer gesturePointerRemoveKey(int pointerID, Object ke) {
   //      Pointer p = getCreatePointer(pointerID);
   //      LinkedListDouble lld = p.getGestureList();
   //      GesturePointer head = (GesturePointer) lld.getHead();
   //      GesturePointer gp = head;
   //      while (gp != null) {
   //         GesturePointer gpNext = (GesturePointer) gp.getNext();
   //         if (gp.getKeyCancel() == ke) {
   //            long releaseTime = this.getTimeCurrent();
   //            //it is ours. finalize it
   //            gp.setRelease(p.getX(), p.getY(), releaseTime, 0);
   //            //send gp for gesture analysis
   //            //remove from list?
   //            gesturePointerRemove(gpNext);
   //            return gp;
   //         }
   //         gp = gpNext;
   //      }
   //      return null;
   //   }

   /**
    * 
    * @param deviceID 0 based ID
    * @return
    */
   private DeviceKeys getDeviceKeyGamePads(int deviceID) {
      //make sure the array is ok
      DeviceKeys[] dks = appDeviceKeys(deviceKeysGamePads, deviceID, IInput.DEVICE_2_GAMEPAD);
      if (deviceKeysGamePads != dks) {
         deviceKeysGamePads = dks;
      }
      return deviceKeysGamePads[deviceID - 1];
   }

   private DeviceKeys getDeviceKeysKeyboard(int deviceID) {
      if (deviceID == 0) { //fast check
         return deviceKeyMainKeyboard;
      } else {
         DeviceKeys[] dks = appDeviceKeys(deviceKeysKeyboard, deviceID, IInput.DEVICE_0_KEYBOARD);
         if (deviceKeysKeyboard != dks) {
            deviceKeysKeyboard = dks;
         }
         return deviceKeysKeyboard[deviceID - 1];
      }
   }

   /**
    * Returns the key controller {@link DeviceKeys} for the given pointer.
    * <br>
    * Fingers are different devices than mouses? They are linked to a Screen/Monitor
    * <br>
    * They are pointers but don't have keys. Therefore they don't have a {@link DeviceKeys}.
    * <br>
    * <br>
    * @param pointerID
    * @return
    */
   private DeviceKeys getDeviceKeysPointer(int pointerID) {
      if (pointerID == 0) {
         return deviceKeyMainPointer;
      } else {
         DeviceKeys[] dks = appDeviceKeys(deviceKeysPointers, pointerID, IInput.DEVICE_1_MOUSE);
         if (deviceKeysPointers != dks) {
            deviceKeysPointers = dks;
         }
         return deviceKeysPointers[pointerID - 1];
      }
   }

   /**
    * The {@link DeviceKeys} for the given screenID.
    * <br>
    * @param screenID
    * @return
    */
   private DeviceKeys getDeviceKeysTouchScreen(int screenID) {
      if (screenID == 0) {
         return deviceKeyMainTouchScreen;
      } else {
         DeviceKeys[] dks = appDeviceKeys(deviceKeysScreens, screenID, IInput.DEVICE_4_SCREEN);
         if (deviceKeysScreens != dks) {
            deviceKeysScreens = dks;
         }
         return deviceKeysScreens[screenID - 1];
      }
   }

   private DeviceKeys getDKeys(int deviceID, int deviceType) {
      if (deviceType == IInput.DEVICE_0_KEYBOARD) {
         return getDeviceKeysKeyboard(deviceID);
      } else if (deviceType == DEVICE_1_MOUSE) {
         return getDeviceKeysPointer(deviceID);
      } else if (deviceType == DEVICE_2_GAMEPAD) {
         return getDeviceKeyGamePads(deviceID);
      } else if (deviceType == DEVICE_4_SCREEN) {
         return getDeviceKeysTouchScreen(deviceID);
      } else {
         throw new IllegalArgumentException();
      }
   }

   /**
    * Legacy method for left mouse button press drags
    * <br>
    * Compute the x distance travelled since the reference event occurred.
    * <br>
    * Only valid when Auto Gesture for LeftMouse is created.
    * @return
    */
   public int getDraggedDiffX() {
      return getGesturePointer0(0).getPressedX() + getDraggedVectorX();
   }

   public int getDraggedDiffY() {
      return getGesturePointer0(0).getPressedY() + getDraggedVectorY();
   }

   /**
    * The amount of X pixels dragged since the last press event.
    * <br>
    * <br>
    * @return value is positive or negative depending on direction
    */
   public int getDraggedVectorX() {
      return getGesturePointer0(0).getVectorX();
   }

   /**
    * The amount of Y pixels dragged since the last press event.
    * <br> value is positive or negative depending on direction
    * @return
    */
   public int getDraggedVectorY() {
      return getGesturePointer0(0).getVectorY();
   }

   /**
    * TODO In case of a repetition, uses the original pressed event ?
    * @return
    */
   public BEvent getEventCurrent() {
      return eventCurrent;
   }

   public int getEventID() {
      return eventID;
   }

   /**
    * Used by the game loop. Every time
    * Set to true when a new event is added.
    * Set to false at the end of the event processing
    * @return
    */
   public int getEventUniID() {
      return eventUniID;
   }

   /**
    * When Rendering takes too much time, fast types will never register,
    * unless all events are queued and timed in a thread.
    * <br>
    * 
    * @return
    */
   public int getFastKeyTypeValue() {
      return eventCanvas.getInputSettings().getKeyFastTypeTimeout();
   }

   private int getFingerPointerID(DeviceEventXYTouch dt) {
      return dt.getDeviceButton();
   }

   /**
    * Array of unique PointersIDs that have at least one active {@link GesturePointer}.
    * <br>
    * @return
    */
   public int[] getGesturedPointersActive() {
      IntBuffer ib = new IntBuffer(uc);
      for (int i = 0; i < pointers.length; i++) {
         if (pointers[i] != null) {
            if (pointers[i].getGestureList().getNumElements() != 0) {
               ib.addInt(i);
            }
         }
      }
      return ib.getIntsClonedTrimmed();
   }

   /**
    * Returns the oldest {@link GesturePointer} for the pointerID. 
    * <br>
    * Remember that you have to create a Gesture Pointer when a key (mouse or keyboard) is pressed
    * down.
    * @param pointerID
    * @return cannot be null. if none is found, an exception is thrown.
    * @throws IllegalStateException 
    */
   public GesturePointer getGesturePointer0(int pointerID) {
      if (pointerID < 0) {
         throw new IllegalArgumentException();
      }
      Pointer p = getPointerLazy(pointerID);
      GesturePointer gp = (GesturePointer) p.getGestureList().getHead();
      if (gp == null) {
         throw new IllegalStateException("No Gesture for pointer " + pointerID);
      }
      return gp;
   }

   /**
    * Returns active {@link GesturePointer} for the pointer.
    * <br>
    * @param pointerID
    * @return
    */
   public GesturePointer[] getGesturePointers(int pointerID) {
      LinkedListDouble lld = getPointerGestureList(pointerID);
      GesturePointer[] gps = new GesturePointer[lld.getNumElements()];
      GesturePointer ke = (GesturePointer) lld.getHead();
      int count = 0;
      while (ke != null) {
         gps[count] = ke;
         ke = (GesturePointer) ke.getNext();
         count++;
      }
      return gps;
   }

   /**
    * Array of the {@link GesturePointer} from the last pointer.
    * <br>
    * There is a {@link GesturePointer} for each key that registered with 
    * <br>
    * @return
    */
   public GesturePointer[] getGesturesLastPointer() {
      LinkedListDouble lld = getPointerGestureList(lastPointer.getPointerID());
      GesturePointer head = (GesturePointer) lld.getHead();
      GesturePointer[] size = new GesturePointer[lld.getNumElements()];
      GesturePointer gp = head;
      int index = 0;
      while (gp != null) {
         size[index] = gp;
         index++;
         gp = (GesturePointer) gp.getNext();
      }
      return size;
   }

   public CoreUiSettings getInputSettings() {
      return eventCanvas.getInputSettings();
   }

   /**
    * For pointer events, sets the pointer button. 
    * @return
    */
   public int getKeyCode() {
      return lastDeviceEvent.getDeviceButton();
   }

   private KeyEventListed getKeyEventPressed(DeviceEvent de) {
      return getKeyEventPressed(de.getDeviceButton(), de.getDeviceID(), de.getDeviceType());
   }

   /**
    * 
    * @param key
    * @param deviceID
    * @param deviceType
    * @return null if not pressed
    */
   private KeyEventListed getKeyEventPressed(int key, int deviceID, int deviceType) {
      //start with tail cuz most of the time last key pressed is released first
      DeviceKeys dk = getDKeys(deviceID, deviceType);
      LinkedListDouble keysPressedList = dk.getPressedKeysList();
      KeyEventListed ke = (KeyEventListed) keysPressedList.getTail();
      while (ke != null) {
         if (ke.deviceEventPressed.getDeviceButton() == key) {
            if (ke.deviceEventPressed.getDeviceID() == deviceID) {
               if (ke.deviceEventPressed.getDeviceType() == deviceType) {
                  return ke;
               }
            }
         }
         ke = (KeyEventListed) ke.getPrev();
      }
      return null;
   }

   /**
    * 
    * @param last
    * @return
    */
   public int getKeyModSequence(int last) {
      return modSequence & BitUtils.getMask(last);
   }

   /**
    * -1 if no number is involved in the current event.
    * <br>
    * <br>
    * Returned values:
    * <li> 0 for 0 key
    * <li> 1 for 1 key
    * 
    * @return
    */
   public int getKeyNum() {
      if (getKeyCode() >= ITechCodes.KEY_NUM0 && getKeyCode() <= ITechCodes.KEY_NUM9) {
         return getKeyCode() - 48;
      }
      return -1;
   }

   public AppliEvent getLastActionEvent() {
      return lastActionEvent;
   }

   /**
    * Returns the last {@link DeviceEvent}.. but it may not be the current event!
    * @return
    */
   public DeviceEvent getLastDeviceEvent() {
      return lastDeviceEvent;
   }

   public int getLastDeviceType() {
      return lastDeviceEvent.getDeviceType();
   }

   /**
    * 0 is last
    * 
    * @param i
    * @return
    */
   public BEvent getLastEvent(int i) {
      return (BEvent) beEventsHistory.getLast(i);
   }

   /**
    * Returns the {@link GestureInput} of the input.
    * <br>
    * Null if no Gestures.
    * @return
    */
   public GestureEvent getLastGesture() {
      return lastGestureEvent;
   }

   public KeyEventListed getLastKeyEvent() {
      return lastKeyEvent;
   }

   /**
    * 
    * @return
    */
   public Pointer getLastPointer() {
      return lastPointer;
   }

   public DeviceEventXY getLastPointerEvent() {
      return lastDeviceEventXY;
   }

   public DeviceEventXY getLastPointerEvent(int pointerID) {
      return lastPointerEvents[pointerID];
   }

   public SenseEvent getLastSenseEvent() {
      return lastSensorEvent;
   }

   /**
    * THe detla to update the simulation
    * @return
    */
   public long getLoopDelta() {
      return loopDelta;
   }

   /**
    * Returns the last device mode
    * 
    * 
    * @return
    */
   public int getMode() {
      return lastDeviceEvent.getDeviceMode();
   }

   /**
    * This is no localized...
    * TODO how to localized to russian keyboard for example?
    * @param b
    * @return
    */
   public String getNameEvent(BEvent b) {
      if (b instanceof DeviceEvent) {
         DeviceEvent de = (DeviceEvent) b;
         int type = de.getDeviceType();
         if (type == DEVICE_0_KEYBOARD) {
            int key = de.getDeviceButton();
            return ToStringStaticCoreUi.toStringKey(key);
         }
      }
      return b.toString();
   }

   public String getNameEventLong(BEvent b) {
      if (b instanceof DeviceEvent) {
         DeviceEvent de = (DeviceEvent) b;
         int type = de.getDeviceType();
         if (type == DEVICE_0_KEYBOARD) {
            int key = de.getDeviceButton();
            int id = de.getDeviceID();
            int mode = de.getDeviceMode();
            return ToStringStaticCoreUi.toStringMod(mode) + ToStringStaticCoreUi.toStringKey(key) + "Kb[" + id + "]";
         } else if (type == DEVICE_1_MOUSE) {
            DeviceEventXY dex = (DeviceEventXY) b;

         }
      }
      return b.toString();
   }

   /**
    * All pressed elements in all devices
    * @return
    */
   public int getNumKeysPressed() {
      int v = 0;
      v += deviceKeyMainKeyboard.getNumKeysPressed();
      v += deviceKeyMainPointer.getNumKeysPressed();
      v = addNumKeysPress(deviceKeysKeyboard, v);
      v = addNumKeysPress(deviceKeysGamePads, v);
      v = addNumKeysPress(deviceKeysPointers, v);
      return v;
   }

   public int getNumKeysPressed(int deviceID, int deviceType) {
      DeviceKeys dk = getDKeys(deviceID, deviceType);
      return dk.getNumKeysPressed();
   }

   /**
    * Returns nuple press of the last key event
    * <br>
    * Times the key is press and released and press without other key event.
    * <br>
    * Count the number of times the key is has been pressed consecutively
    * KEY => 1
    * KEY KEY => 2
    * KEY KEY KEY => 3
    * Canceled when a press event invalidates it with the timer
    * A continuous event receives this value
    * 
    * <br>
    * Which context? Whole history/UserDevices/Device/Button
    * Button context means double click is not canceled by another button clicks.
    * <br>
    * Is this a Gesture? Sort of..        
    * <li> Mouse + Space + Mouse : Double click with a space in between. release events not important.        
    * Double click with a Mouse Mouse
    * @return 1 if first time a
    */
   public int getNUplePressed() {
      return lastKeyEvent.getNUpleDevice();
   }

   public float getPinchAngle(GesturePointer gp0, GesturePointer gp1) {
      int px0 = gp0.getPressedX();
      int py0 = gp0.getPressedY();
      int px1 = gp1.getPressedX();
      int py1 = gp1.getPressedY();

      int cx0 = gp0.getX();
      int cy0 = gp0.getY();
      int cx1 = gp1.getX();
      int cy1 = gp1.getY();

      float angle = uc.getGeo2dUtils().angleBetweenLines(px0, py0, px1, py1, cx0, cy0, cx1, cy1);

      return angle;
   }

   public float getPinchAngle(int pid, int pid2) {
      GesturePointer gp0 = getGesturePointer0(pid);
      GesturePointer gp1 = getGesturePointer0(pid2);
      return getPinchAngle(gp0, gp1);
   }

   public float getPinchRatio(GesturePointer gp0, GesturePointer gp1) {
      if (gp1.isPressed() && gp0.isPressed()) {
         int px0 = gp0.getPressedX();
         int py0 = gp0.getPressedY();
         int px1 = gp1.getPressedX();
         int py1 = gp1.getPressedY();

         float pressedDistance = Geo2dUtils.getDistance(px0, py0, px1, py1);

         int cx0 = gp0.getX();
         int cy0 = gp0.getY();
         int cx1 = gp1.getX();
         int cy1 = gp1.getY();

         float curDistance = Geo2dUtils.getDistance(cx0, cy0, cx1, cy1);

         return curDistance / pressedDistance;
      }

      return 1;
   }

   public float getPinchRatio(int pid, int pid2) {
      GesturePointer gp0 = getGesturePointer0(pid);
      GesturePointer gp1 = getGesturePointer0(pid2);
      return getPinchRatio(gp0, gp1);
   }

   /**
    * Returns the {@link Pointer}.
    * {@link ArrayIndexOutOfBoundsException} if pointer is not
    * @param pointerID
    * @return
    */
   public Pointer getPointer(int pointerID) {
      return pointers[pointerID];
   }

   public int getPointerButton() {
      return getKeyCode();
   }

   /**
    * Tracks all the button events during a {@link GesturePointer}.
    * <br>
    * Button 1 is pressed, then button 2 is press and released, then pressed again
    * @param pointerID internal id for the pointer.
    * @return
    */
   private LinkedListDouble getPointerGestureList(int pointerID) {
      Pointer p = getPointerLazy(pointerID);
      LinkedListDouble pointerGestureList = p.getGestureList();
      return pointerGestureList;
   }

   /**
    * Create a {@link Pointer} for the given pointerID. 
    * 
    * Returns existing one if already created.
    * @param pointerID
    * @return
    */
   public Pointer getPointerLazy(int pointerID) {
      if (pointers.length <= pointerID) {
         Pointer[] old = pointers;
         pointers = new Pointer[pointerID + 1];
         for (int i = 0; i < old.length; i++) {
            pointers[i] = old[i];
         }
      }
      if (pointers[pointerID] == null) {
         pointers[pointerID] = new Pointer(cuc, pointerID);
      }
      return pointers[pointerID];
   }

   /**
    * Active or inactive pointers? Fingers when released become inactive
    * @return
    */
   public Pointer[] getPointers() {
      return pointers;
   }

   public Pointer[] getPressedFingers() {
      IntToObjects ito = new IntToObjects(uc);
      for (int i = 0; i < pointers.length; i++) {
         if (pointers[i].isFinger()) {
            if (pointers[i].getLastPointerEvent().getDeviceMode() == IInput.MOD_0_PRESSED) {
               ito.add(pointers[i]);
            }
         }
      }
      Pointer[] ar = new Pointer[ito.getLength()];
      ito.copy(ar, 0);
      return ar;
   }

   /**
    * 
    * @return null if less than 2 keys pressed
    */
   public DeviceEventGroup getPressedKeyGroup() {
      KeyEventListed[] ls = getPressedKeys();
      if (ls.length > 1) {
         DeviceEvent[] ar = new DeviceEvent[ls.length];
         for (int i = 0; i < ls.length; i++) {
            ar[i] = ls[i].getEventPressed();
         }
         DeviceEventGroup deg = new DeviceEventGroup(cuc, ar);
         return deg;
      } else {
         return null;
      }
   }

   /**
    * Number of currently pressed keys.
    * @return
    */
   public int getPressedKeyNum() {
      return getNumKeysPressed();
   }

   /**
    * Array copy of pressed keys of the all devices. Order is unknonwn.
    * Array is not null. but size 0 if none
    * @return
    */
   public KeyEventListed[] getPressedKeys() {
      IntToObjects ito = new IntToObjects(uc, 20);
      addPressKeys(deviceKeyMainKeyboard, ito); //main key
      addPressKeys(deviceKeysKeyboard, ito); //other keyspoints
      addPressKeys(deviceKeyMainPointer, ito); //maint pointer
      addPressKeys(deviceKeysPointers, ito);
      addPressKeys(deviceKeysGamePads, ito);
      int size = ito.getLength();
      KeyEventListed[] ar = new KeyEventListed[size];
      ito.copy(ar, 0);
      return ar;
   }

   public KeyEventListed[] getPressedKeys(int deviceID, int deviceType) {
      DeviceKeys dk = getDKeys(deviceID, deviceType);
      return dk.getPressedKeys();
   }

   /**
    * Null if not pressed.
    * <br>
    * @param screenID
    * @param fingerID
    * @return
    */
   public KeyEventListed getPressedPoint(int screenID, int fingerID) {
      DeviceKeys dk = getDeviceKeysTouchScreen(screenID);
      return dk.getKeyEventPressed(fingerID);
   }

   /**
    * The last {@link RepeatEvent}
    * @return
    */
   public RepeatEvent getRepeatEvent() {
      return lastRepeatEvent;
   }

   public SenseEvent getSensor() {
      return lastSensorEvent;
   }

   public double getSimulationTime() {
      return simulTime;
   }

   public double getSimulDelta() {
      return simulDelta;
   }

   /**
    * 
    * @return
    */
   public DeviceEventGroup getSimultaneousMixed() {
      IntToObjects ib = null;
      int simulTimeOut = eventCanvas.getInputSettings().getSimultaneousTimeOut();
      DeviceEvent root = lastKeyEvent.deviceEventReleased;
      if (root == null) {
         root = lastKeyEvent.deviceEventPressed;
      }
      long refTime = root.getTime();
      //read history until we have the first match
      DeviceEventListed ar = (DeviceEventListed) deviceEventHistoryPressRelease.getTail();
      ar = (DeviceEventListed) ar.getNext();
      while (ar != null) {
         DeviceEvent d = ar.getEvent();
         if (d == null) {
            break;
         }
         int diff = (int) (refTime - d.getTime());
         if (diff <= simulTimeOut) {
            if (d.getDeviceMode() == IInput.MOD_1_RELEASED || d.getDeviceMode() == IInput.MOD_0_PRESSED) {
               if (ib == null) {
                  ib = new IntToObjects(uc);
                  ib.add(root);
               }
               ib.add(d);
            }
            ar = (DeviceEventListed) ar.getNext();
         } else {
            break;
         }
      }
      if (ib != null) {
         DeviceEvent[] dear = new DeviceEvent[ib.nextempty];
         ib.copy(dear, 0);
         DeviceEventGroup deg = new DeviceEventGroup(cuc, dear);
         deg.setFlag(DeviceEventGroup.FLAG_10_SIMUL, true);
         return deg;
      } else {
         return null;
      }
   }

   /**
    * Builds Array of events simul pressed from this current event.
    * <br>
    * null if
    * @return {@link DeviceEventGroup} 
    * @throws IllegalStateException when called InputState without device press event
    */
   public DeviceEventGroup getSimultaneousPressed() {
      //#mdebug
      if (!(isTypeDevice() && isModPressed())) {
         //#debug
         toDLog().pEvent("Wrong State", this, InputState.class, "getSimultaneousPressed");
         throw new IllegalStateException("Must be called with a press device event state");
      }
      //#enddebug
      int simulTimeOut = eventCanvas.getInputSettings().getSimultaneousTimeOut();
      //go back in time
      KeyEventListed[] ls = getPressedKeys();
      IntBuffer ib = null;
      long refTime = lastDeviceKeyPress.getTime();
      for (int i = 0; i < ls.length; i++) {
         DeviceEvent dePress = ls[i].getEventPressed();
         if (dePress != lastDeviceKeyPress) {
            int diff = (int) (refTime - dePress.getTime());
            if (diff <= simulTimeOut) {
               if (ib == null) {
                  ib = new IntBuffer(uc);
               }
               ib.addInt(i);
            }
         }
      }
      if (ib != null) {
         DeviceEvent[] ar = new DeviceEvent[ib.getSize() + 1];
         ar[0] = lastDeviceKeyPress;
         for (int i = 1; i < ar.length; i++) {
            int id = ib.get(i - 1);
            ar[i] = ls[id].getEventPressed();
         }
         DeviceEventGroup deg = new DeviceEventGroup(cuc, ar);
         deg.setFlag(DeviceEventGroup.FLAG_10_SIMUL, true);
         return deg;
      } else {
         return null;
      }
   }

   public DeviceEventGroup getSimultaneousReleased() {
      if (!(isTypeDevice() && isModReleased())) {
         throw new IllegalStateException();
      }
      IntToObjects ib = null;
      int simulTimeOut = eventCanvas.getInputSettings().getSimultaneousTimeOut();
      long refTime = lastDeviceKeyRelease.getTime();
      DeviceEventListed ar = (DeviceEventListed) deviceEventHistoryPressRelease.getTail();
      ar = (DeviceEventListed) ar.getNext();
      while (ar != null) {
         DeviceEvent d = ar.getEvent();
         int diff = (int) (refTime - d.getTime());
         if (diff <= simulTimeOut) {
            if (d.getDeviceMode() == IInput.MOD_1_RELEASED) {
               if (ib == null) {
                  ib = new IntToObjects(uc);
                  ib.add(lastDeviceKeyRelease);
               }
               ib.add(d);
            }
            ar = (DeviceEventListed) ar.getNext();
         } else {
            break;
         }
      }
      if (ib != null) {
         DeviceEvent[] dear = new DeviceEvent[ib.nextempty];
         ib.copy(dear, 0);
         DeviceEventGroup deg = new DeviceEventGroup(cuc, dear);
         deg.setFlag(DeviceEventGroup.FLAG_10_SIMUL, true);
         return deg;
      } else {
         return null;
      }
   }

   public long getTimeCurrent() {
      return eventCurrent.getTime();
   }

   /**
    * Computes the time delta in milliseconds between this event and the previous one.
    * <br>
    * @return
    */
   public int getTimeDelta() {
      return (int) (eventCurrent.getTime() - eventPrevious.getTime());
   }

   /**
    * X position of last {@link DeviceEventXY} event.
    * <br>
    * @return
    */
   public int getX() {
      return lastDeviceEventXY.getX();
   }

   public int getY() {
      return lastDeviceEventXY.getY();
   }

   public boolean hasFlag(int flag) {
      return BitUtils.hasFlag(flags, flag);
   }

   public boolean is0() {
      return getKeyCode() == ITechCodes.KEY_NUM0;
   }

   public boolean is0P() {
      return isPressedKeyboard0(ITechCodes.KEY_NUM0);
   }

   public boolean is1() {
      return getKeyCode() == ITechCodes.KEY_NUM1;
   }

   public boolean is1P() {
      return isPressedKeyboard0(ITechCodes.KEY_NUM1);
   }

   public boolean is2() {
      return getKeyCode() == ITechCodes.KEY_NUM2;
   }

   public boolean is2P() {
      return isPressedKeyboard0(ITechCodes.KEY_NUM2);
   }

   public boolean is3() {
      return getKeyCode() == ITechCodes.KEY_NUM3;
   }

   public boolean is3P() {
      return isPressedKeyboard0(ITechCodes.KEY_NUM3);
   }

   public boolean is4() {
      return getKeyCode() == ITechCodes.KEY_NUM4;
   }

   public boolean is4P() {
      return isPressedKeyboard0(ITechCodes.KEY_NUM4);
   }

   public boolean is5() {
      return getKeyCode() == ITechCodes.KEY_NUM5;
   }

   public boolean is5P() {
      return isPressedKeyboard0(ITechCodes.KEY_NUM5);
   }

   public boolean is6() {
      return getKeyCode() == ITechCodes.KEY_NUM6;
   }

   public boolean is6P() {
      return isPressedKeyboard0(ITechCodes.KEY_NUM6);
   }

   public boolean is7() {
      return getKeyCode() == ITechCodes.KEY_NUM7;
   }

   public boolean is7P() {
      return isPressedKeyboard0(ITechCodes.KEY_NUM7);
   }

   public boolean is8() {
      return getKeyCode() == ITechCodes.KEY_NUM8;
   }

   public boolean is8P() {
      return isPressedKeyboard0(ITechCodes.KEY_NUM8);
   }

   public boolean is9() {
      return getKeyCode() == ITechCodes.KEY_NUM9;
   }

   public boolean is9P() {
      return isPressedKeyboard0(ITechCodes.KEY_NUM9);
   }

   public boolean isActive(int key) {
      return isPressedKeyboard0(key);
   }

   public boolean isCancel() {
      return getKeyCode() == ITechCodes.KEY_CANCEL;
   }

   public boolean isCancelP() {
      return isPressedKeyboard0(ITechCodes.KEY_CANCEL);
   }

   public boolean isCtxChange() {
      return getLastDeviceType() == IInput.TYPE_5_CTX_CHANGE;
   }

   public boolean isDeviceTypeGamePad() {
      return getLastDeviceType() == IInput.DEVICE_2_GAMEPAD;
   }

   /**
    * Double Tap is true when pointer x,y hasn't moved
    * or moved to a 3x3 Grid
    * Double Tap 1,5. means first press in 1 and second in 5 area.
    * <br>
    * Triple Tap 1,5,1 (time is around 750ms
    * Triple Fast Tap (time is around 200ms).
    * <br>
    * Those x,y constraints.
    * <br>
    *  
    * @param deviceID
    * @param deviceType
    * @return
    */
   public boolean isDoubleTap(int deviceID, int deviceType) {
      DeviceKeys dk = getDKeys(deviceID, deviceType);
      return dk.isNupled(2);
   }

   /**
    * Compute if we have a double tap for pointer 0
    * @return
    */
   public boolean isDoubleTapPointer0Button0() {
      return isDoubleTap(0, DEVICE_1_MOUSE);
   }

   public boolean isDown() {
      return getKeyCode() == ITechCodes.KEY_DOWN;
   }

   public boolean isDownActive() {
      return isActive(ITechCodes.KEY_DOWN);
   }

   public boolean isDownP() {
      return isPressedKeyboard0(ITechCodes.KEY_DOWN);
   }

   /**
    * Is Pointer 0 gestured by at least one key/button.
    * <br>
    * @return
    */
   public boolean isDragged() {
      return isPointerGestured(0);
   }

   public boolean isDraggedDragged() {
      return isDraggedDragged(0);
   }

   public boolean isDraggedDragged(int pointerID) {
      return isPointerGesturedIntentionally(pointerID);
   }

   public boolean isFingerPressed(int finger1) {
      for (int i = 0; i < pointers.length; i++) {
         if (pointers[i].isFinger()) {
            if (pointers[i].getLastPointerEvent().getDeviceMode() == IInput.MOD_0_PRESSED) {
               if (pointers[i].getLastPointerEvent().getDeviceButton() == finger1) {
                  return true;
               }
            }
         }
      }
      return false;
   }

   public boolean isFire() {
      return getKeyCode() == ITechCodes.KEY_FIRE;
   }

   public boolean isFireP() {
      return isPressedKeyboard0(ITechCodes.KEY_FIRE);
   }

   /**
    */
   public boolean isGestured() {
      return isTypeGesture();
   }

   public boolean isInside(GestureArea ga) {
      int x = lastDeviceEventXY.getX();
      int y = lastDeviceEventXY.getY();
      return ga.isInside(x, y);
   }

   public boolean isKeyPressed(int key) {
      return isPressedKeyboard0(key);
   }

   /**
    * True if current state of inputconfig has a KeyType event. KeyType is actually a case of
    * the release event
    * @return
    */
   public boolean isKeyTyped() {
      return lastKeyEvent.isTyped();
   }

   public boolean isKeyTyped(int key) {
      return isKeyTyped() && lastKeyEvent.getKey() == key;
   }

   public boolean isKeyTypedAlone(int key) {
      return isKeyTyped(key) && getNumKeysPressed() == 0;
   }

   /**
    * Is last event the key fast typed.
    * <br>
    * Problem is about the long repaints. the fast is not registered.
    * @param key
    * @return
    */
   public boolean isLastKeyFastTyped(int key) {
      if (lastKeyEvent.getKey() == key) {
         return lastKeyEvent.isFastTyped();
      }
      return false;
   }

   public boolean isLeft() {
      return getKeyCode() == ITechCodes.KEY_LEFT;
   }

   public boolean isLeftActive() {
      return isActive(ITechCodes.KEY_LEFT);
   }

   public boolean isLeftP() {
      return isPressedKeyboard0(ITechCodes.KEY_LEFT);
   }

   public boolean isLongPress() {
      if (lastRepeatEvent.getRepeatType() == IInput.REPEAT_2_LONG) {
         return true;
      }
      return false;
   }

   public boolean isMinusP() {
      return isPressedKeyboard0(ITechCodes.KEY_MINUS);
   }

   public boolean isModMoved() {
      return getMode() == IInput.MOD_3_MOVED;
   }

   /**
    * Is Pressed the last Modifier in the chain of events. For keys and pointer events.
    * @return
    */
   public boolean isModPressed() {
      return getMode() == IInput.MOD_0_PRESSED;
   }

   /**
    * Does not check if the release is inside
    * <br>
    * 
    * @return
    */
   public boolean isModReleased() {
      return getMode() == IInput.MOD_1_RELEASED;
   }

   /**
    * A {@link SenseEvent} was last received.
    * @return
    */
   public boolean isModSensed() {
      return getMode() == IInput.MOD_4_SENSED;
   }

   public boolean isNavKey() {
      return getKeyCode() == ITechCodes.KEY_UP || getKeyCode() == ITechCodes.KEY_DOWN || getKeyCode() == ITechCodes.KEY_LEFT || getKeyCode() == ITechCodes.KEY_RIGHT;
   }

   public boolean isPhotoP() {
      return isPressedKeyboard0(ITechCodes.KEY_PHOTO);
   }

   /**
    * The Pinch is a distance between 2 different pointers. The root pointer is used as a base
    * for computing the pinch distance. Good android read here http://developer.android.com/training/gestures/scale.html.
    * <br>
    * Frameworks that don't support touch may emulate pinch with a key. The key is pressed, pinch is started,
    * mouse is moved and distance is double the distance since the pinch key press.
    * 
    * @return
    */
   public boolean isPinched() {
      return false;
   }

   public boolean isPlusP() {
      return isPressedKeyboard0(ITechCodes.KEY_PLUS);
   }

   /**
    * Is Button 0 currently pressed.
    * <br>
    * Method is not about the current event.
    * @param pointerID
    * @return
    */
   public boolean isPointerButton0Pressed(int pointerID) {
      return isPointerButtonPressed(pointerID, ITechCodes.PBUTTON_0_DEFAULT);
   }

   /**
    * 
    * @param button
    * @return
    */
   public boolean isPointerButtonPressed(int pointerID, int button) {
      DeviceKeys dk = getDeviceKeysPointer(pointerID);
      KeyEventListed ke = dk.getKeyEventPressed(button);
      return ke != null;
   }

   /**
    * Return true if pointer 0 is dragging
    * @return
    */
   public boolean isPointerDrag() {
      if (isPointerButton0Pressed(0) && isModMoved()) {
         return true;
      }
      return false;
   }

   public boolean isPointerGestured(int pointerID) {
      Pointer p = getPointerLazy(pointerID);
      return p.getGestureList().getNumElements() != 0;
   }

   /**
    * False if none or all {@link GesturePointer} return false to {@link GesturePointer#isDragIntentional()}
    * @param pointerID
    * @return
    */
   public boolean isPointerGesturedIntentionally(int pointerID) {
      Pointer p = getPointerLazy(pointerID);
      if (p.getGestureList().getNumElements() != 0) {
         LinkedListDouble ld = p.getGestureList();
         GesturePointer gp = (GesturePointer) ld.getHead();
         while (gp != null) {
            if (gp.isDragIntentional()) {
               return true;
            }
            gp = (GesturePointer) gp.getNext();
         }
         return false;
      } else {
         return false;
      }
   }

   public boolean isPointerPPrimary() {
      if (lastDeviceEvent == lastDeviceEventXY) {
         if (lastDeviceEventXY.getDeviceMode() == IInput.MOD_0_PRESSED) {
            if (lastDeviceEventXY.getDeviceType() == IInput.DEVICE_1_MOUSE) {
               return lastDeviceEventXY.getDeviceButton() == ITechCodes.PBUTTON_0_DEFAULT;
            } else if (lastDeviceEventXY.getDeviceType() == IInput.DEVICE_4_SCREEN) {
               return lastDeviceEventXY.getDeviceButton() == ITechCodes.FINGER_1;
            }
         }
      }
      return false;
   }

   public boolean isPointerPSecondary() {
      if (lastDeviceEvent == lastDeviceEventXY) {
         if (lastDeviceEventXY.getDeviceMode() == IInput.MOD_0_PRESSED) {
            if (lastDeviceEventXY.getDeviceType() == IInput.DEVICE_1_MOUSE) {
               return lastDeviceEventXY.getDeviceButton() == ITechCodes.PBUTTON_1_RIGHT;
            } else if (lastDeviceEventXY.getDeviceType() == IInput.DEVICE_4_SCREEN) {
               return lastDeviceEventXY.getDeviceButton() == ITechCodes.FINGER_2;
            }
         }
      }
      return false;
   }

   /**
    * Returns true if the release event is done on the same Drawable on which the press was made.
    * Use {@link GestureDetector}.
    * @return
    */
   public boolean isPointerReleasedInsidePressed() {
      throw new RuntimeException("");
   }

   /**
    * Is the Default Pointer sliding
    * @param value
    * @return
    */
   public boolean isPointerSlide(int value) {
      return isPointerSlide(value, ITechCodes.POINTER_ID_0);
   }

   /**
    * Looks up the {@link GesturePointer} and compute.
    * <br>
    * On Demand compute.
    * <br>
    * <li> {@link ITechGestures#GESTURE_DIR_00_ANY}
    * <li> {@link ITechGestures#GESTURE_DIR_01_VERTICAL}
    * <li> {@link ITechGestures#GESTURE_DIR_01_VERTICAL}
    * <li> {@link ITechGestures#GESTURE_DIR_03_TOP}
    * <li> {@link ITechGestures#GESTURE_DIR_04_BOT}
    * <li> {@link ITechGestures#GESTURE_DIR_05_LEFT}
    * <li> {@link ITechGestures#GESTURE_DIR_06_RIGHT}
    * <br>
    * <br>
    * 
    * @param value
    * @param pointerID
    * @return
    */
   public boolean isPointerSlide(int value, int pointerID) {
      GesturePointer gp = getGesturePointer0(pointerID);
      int val = gp.getDragGestureType();
      int sub = gp.getDirection();
      if (val == ITechGestures.GESTURE_TYPE_1_DRAG_SLIDE) {
         return val == sub;
      }
      return false;
   }

   public boolean isPointerSlideDown() {
      return isPointerSlide(GESTURE_DIR_04_BOT);
   }

   public boolean isPointerSlideLeft() {
      return isPointerSlide(GESTURE_DIR_05_LEFT);
   }

   public boolean isPointerSlideRight() {
      return isPointerSlide(GESTURE_DIR_06_RIGHT);
   }

   public boolean isPointerSlideUp() {
      return isPointerSlide(GESTURE_DIR_03_TOP);
   }

   /**
    * Is the # key pressed
    * @return
    */
   public boolean isPoundP() {
      return isPressedKeyboard0(ITechCodes.KEY_POUND);
   }

   /**
    * A double press
    * @return
    */
   public boolean isPressedDouble() {
      return getMode() == IInput.MOD_0_PRESSED && isDoubleTapPointer0Button0();
   }

   /**
    * Is the key pressed, among all the keys currently pressed
    * <br>
    * <br>
    * @param key value from {@link ITechCodes#KEY_BACK}, {@link ITechCodes#KEY_NUM3}
    * @return
    */
   public boolean isPressedKeyboard0(int key) {
      KeyEventListed ke = getKeyEventPressed(key, 0, IInput.DEVICE_0_KEYBOARD);
      return ke != null;
   }

   /**
    * A double click
    * @return
    */
   public boolean isPressedReleasedDouble() {
      return getMode() == IInput.MOD_1_RELEASED && isDoubleTapPointer0Button0();
   }

   /**
    * Check if the release event is not occuring further than the given distance for both x and y
    * @param value
    * @return
    */
   public boolean isReleaseAround(int value) {
      return getGesturePointer0(0).isReleaseInsidePressedRetangle(value);
   }

   /**
    * Is last device event {@link ITechCodes#KEY_RIGHT}
    * @return
    */
   public boolean isRight() {
      return getKeyCode() == ITechCodes.KEY_RIGHT;
   }

   public boolean isRightActive() {
      return isActive(ITechCodes.KEY_RIGHT);
   }

   /**
    * Is Right key currently pressed.
    * @return
    */
   public boolean isRightP() {
      return isPressedKeyboard0(ITechCodes.KEY_RIGHT);
   }

   /**
    * True when the press keys of the device are key1+key2 and nothing else.
    * <br>
    * @param key1
    * @param key2
    * @param deviceID
    * @param deviceType
    * @return
    */
   public boolean isSequencedPressed(int key1, int key2, int deviceID, int deviceType) {
      DeviceKeys dk = getDKeys(deviceID, deviceType);
      LinkedListDouble keysPressedList = dk.getPressedKeysList();
      if (keysPressedList.getNumElements() == 2) {
         KeyEventListed ke = (KeyEventListed) keysPressedList.getHead();
         if (ke != null) {
            if (ke.getKey() == key1) {
               ke = (KeyEventListed) ke.getNext();
               if (ke != null && ke.getKey() == key2) {
                  return true;
               }
            }
         }
      }
      return false;
   }

   public boolean isSequencedPressedKB0(int key1, int key2, int key3) {
      return isSequencedPressed(key1, key2, key3, 0, DEVICE_0_KEYBOARD);
   }

   public boolean isSequencedPressedKB0(int key1, int key2, int key3, int key4) {
      return isSequencedPressed(key1, key2, key3, key4, 0, DEVICE_0_KEYBOARD);
   }

   public boolean isSequencedPressed(int key1, int key2, int key3, int deviceID, int deviceType) {
      DeviceKeys dk = getDKeys(deviceID, deviceType);
      LinkedListDouble keysPressedList = dk.getPressedKeysList();
      if (keysPressedList.getNumElements() == 3) {
         KeyEventListed ke = (KeyEventListed) keysPressedList.getHead();
         if (ke != null) {
            if (ke.getKey() == key1) {
               ke = (KeyEventListed) ke.getNext();
               if (ke != null && ke.getKey() == key2) {
                  ke = (KeyEventListed) ke.getNext();
                  if (ke != null && ke.getKey() == key3) {
                     return true;
                  }
               }
            }
         }
      }
      return false;
   }

   public boolean isSequencedPressed(int key1, int key2, int key3, int key4, int deviceID, int deviceType) {
      DeviceKeys dk = getDKeys(deviceID, deviceType);
      LinkedListDouble keysPressedList = dk.getPressedKeysList();
      if (keysPressedList.getNumElements() == 4) {
         KeyEventListed ke = (KeyEventListed) keysPressedList.getHead();
         if (ke != null) {
            if (ke.getKey() == key1) {
               ke = (KeyEventListed) ke.getNext();
               if (ke != null && ke.getKey() == key2) {
                  ke = (KeyEventListed) ke.getNext();
                  if (ke != null && ke.getKey() == key3) {
                     ke = (KeyEventListed) ke.getNext();
                     if (ke != null && ke.getKey() == key4) {
                        return true;
                     }
                  }
               }
            }
         }
      }
      return false;
   }

   /**
    * True when exactly those keys are pressed in that order
    * @param key1 oldest key
    * @param key2
    * @return
    */
   public boolean isSequencedPressedKeyboard0(int key1, int key2) {
      return isSequencedPressed(key1, key2, 0, DEVICE_0_KEYBOARD);
   }

   /**
    * Are the 2 keys typed
    * @param key1
    * @param key2
    * @param deviceID
    * @param deviceType
    * @return
    */
   public boolean isSequencedTyped(int key1, int key2, int deviceID, int deviceType) {
      DeviceKeys dk = getDKeys(deviceID, deviceType);
      LinkedListDouble keysHistoryList = dk.getHistoryList();
      KeyEventListed ke = (KeyEventListed) keysHistoryList.getTail();
      //start in reverse with key2
      if (ke != null && ke.getKey() == key2 && ke.isTyped()) {
         ke = (KeyEventListed) ke.getPrev();
         if (ke != null && ke.getKey() == key1 && ke.isTyped()) {
            return true;
         }
      }
      return false;
   }

   /**
    * Are keys typed in sequence within which history context?
    * Device? Whole ? User Devices.
    * <br>
    * In the whole history
    * @param key1 
    * @param key2 last key to be typed
    * @return
    */
   public boolean isSequencedTypedKeyboard0(int key1, int key2) {
      return isSequencedTyped(key1, key2, 0, DEVICE_0_KEYBOARD);
   }

   /**
    * True when event is similar
    * <li> Repeat of the same key
    * <li> Move of the same pointer
    * @return
    */
   public boolean isSimilar() {
      if (eventCurrent.getType() == eventPrevious.getType()) {
         if (eventCurrent.getType() == IInput.TYPE_1_DEVICE) {
            DeviceEvent de = (DeviceEvent) eventCurrent;
            DeviceEvent dEventPrevious = (DeviceEvent) eventPrevious;
            if (de.getDeviceMode() == IInput.MOD_3_MOVED && de.isModeEqual(dEventPrevious)) {
               if (de.getDeviceID() == dEventPrevious.getDeviceID()) {
                  return true;
               }
            }
         } else if (eventCurrent.getType() == IInput.TYPE_4_REPEAT) {
            RepeatEvent re = (RepeatEvent) eventCurrent;
            RepeatEvent dEventPrevious = (RepeatEvent) eventPrevious;
            if (re.getSource() == dEventPrevious.getSource()) {
               return true;
            }
         } else if (eventCurrent.getType() == IInput.TYPE_3_CANVAS) {
            CanvasHostEvent che = (CanvasHostEvent) eventCurrent;
            CanvasHostEvent cheP = (CanvasHostEvent) eventPrevious;
            if (che.getActionType() == cheP.getActionType()) {
               return true;
            }
         }
      }
      return false;
   }

   /**
    * Is this event simultaneous with the num previous events;
    * @param num
    * @return
    */
   public boolean isSimultaneous(int num) {
      //look numth event and timesta

      throw new RuntimeException();
   }

   public boolean isSimultaneous2() {
      return isSimultaneous(1);
   }

   public boolean isSimultaneous3() {
      return isSimultaneous(2);
   }

   /**
    * 
    * @param deviceID
    * @param num
    * @return
    */
   public boolean isSimultaneousDevice(int deviceID, int num) {
      //look numth event and timestamp
      throw new RuntimeException();
   }

   /**
    * Is Soft key pressed 
    * @return
    */
   public boolean isSoftLeftP() {
      return isPressedKeyboard0(ITechCodes.KEY_MENU_LEFT);
   }

   public boolean isSoftRightP() {
      return isPressedKeyboard0(ITechCodes.KEY_MENU_RIGHT);
   }

   /**
    * Is the key * currently pressed
    * @return
    */
   public boolean isStarP() {
      return isPressedKeyboard0(ITechCodes.KEY_STAR);
   }

   /**
    * True when * is press along side with that key
    * @param key
    * @return
    */
   public boolean isTriggerStar(int key) {
      return isStarP() && this.getKeyCode() == key;
   }

   public boolean isTypeAction() {
      return eventCurrent.getType() == TYPE_6_APPLI;
   }

   /**
    * True when current event is of type {@link IInput#TYPE_1_DEVICE}
    * <br>
    * @return
    */
   public boolean isTypeDevice() {
      return eventCurrent.getType() == TYPE_1_DEVICE;
   }

   public boolean isTypeDeviceFinger() {
      return getLastDeviceType() == DEVICE_4_SCREEN;
   }

   public boolean isTypeDeviceGamePad() {
      return getLastDeviceType() == DEVICE_2_GAMEPAD;
   }

   /**
    * Is the last device event of type keyboard.
    * <br>
    * @return
    */
   public boolean isTypeDeviceKeyboard() {
      return getLastDeviceType() == DEVICE_0_KEYBOARD;
   }

   public boolean isTypeDeviceMouse() {
      return getLastDeviceType() == DEVICE_1_MOUSE;
   }

   /**
    * When mouse or finger or any other 'pointing' device that can press/move/release combo
    * @return
    */
   public boolean isTypeDevicePointer() {
      return getLastDeviceType() == DEVICE_1_MOUSE || getLastDeviceType() == DEVICE_4_SCREEN;
   }

   public boolean isTypeDeviceSensor() {
      return getLastDeviceType() == DEVICE_7_SENSOR;
   }

   public boolean isTypeGesture() {
      return eventCurrent.getType() == TYPE_2_GESTURE;
   }

   /**
    * Is the event a group.
    * <br>
    * i.e.  a simul group of 
    * @return
    */
   public boolean isTypeGroup() {
      return eventCurrent.getType() == TYPE_7_GROUP;
   }

   /**
    * True when we have a {@link IInput#TYPE_4_REPEAT} event
    * @return
    */
   public boolean isTypeRepeat() {
      return eventCurrent.getType() == TYPE_4_REPEAT;
   }

   public boolean isUp() {
      return getKeyCode() == ITechCodes.KEY_UP;
   }

   public boolean isUpActive() {
      return isActive(ITechCodes.KEY_UP);
   }

   public boolean isUpP() {
      return isPressedKeyboard0(ITechCodes.KEY_UP);
   }

   public boolean isVoice() {
      return eventCurrent instanceof VoiceEvent;
   }

   public boolean isWheelDown() {
      if (isWheeled()) {
         return lastDeviceEventXY.getY() == ITechCodes.PBUTTON_4_WHEEL_DOWN;
      }
      return false;
   }

   public boolean isWheeled() {
      return getMode() == IInput.MOD_5_WHEELED;
   }

   public boolean isWheelUp() {
      if (isWheeled()) {
         return lastDeviceEventXY.getY() == ITechCodes.PBUTTON_3_WHEEL_UP;
      }
      return false;
   }

   public boolean isZeroP() {
      return isPressedKeyboard0(ITechCodes.KEY_NUM0);
   }

   /**
    * Called after the event is registered? Single events always processed first.
    * <br>
    * Should be done after. The {@link GesturePointer} are thus updated with moves.
    * 
    * Also called if event is validated (no linux bug)
    * @param e
    */
   private void newEventActivateGesture(BEvent e) {
      //#
      if (numTotalActiveGestures != 0) {
         for (int i = 0; i < pointers.length; i++) {
            Pointer p = pointers[i];
            if (p == null) {
               //#debug
               toDLog().pEvent("Null Pointer", this, InputState.class, "newEventCancelGesture");
               continue;
            }
            int pointerID = p.getPointerID();
            LinkedListDouble lld = getPointerGestureList(pointerID);
            GesturePointer head = (GesturePointer) lld.getHead();
            GesturePointer gp = head;
            while (gp != null) {
               GesturePointer next = (GesturePointer) gp.getNext();
               //read next before possibly removing it
               //look up cancel key
               EventKey[] ek = gp.getKeys();
               if (ek != null) {
                  for (int j = 0; j < ek.length; j++) {
                     boolean isActivated = ek[j].isKeyActivated(e);
                     if (isActivated) {
                        int typeKey = ek[j].getKeyType();
                        //#debug
                        String msg = "ACTIVATION OF [" + ek[j].toString1Line() + "] BY " + e.toString1Line();
                        //#debug
                        toDLog().pEvent(msg, gp, InputState.class, "newEventActivateGesture@line3161");
                        if (typeKey == ITechEventKey.KEY_TYPE_1_CANCEL) {
                           gesturePointerRemove(gp);
                        } else if (typeKey == ITechEventKey.KEY_TYPE_0_FIRE) {
                           GestureEvent ge = createGestureEvent(gp, ek[j]);
                           if (ge != null) {
                              this.queuePost(ge);
                           }
                        } else if (typeKey == ITechEventKey.KEY_TYPE_2_FIRE_AND_CANCEL) {
                           //removing it prevents get next
                           gesturePointerRemove(gp);
                           GestureEvent ge = createGestureEvent(gp, ek[j]);
                           if (ge != null) {
                              this.queuePost(ge);
                           }
                        } else {
                           throw new IllegalStateException("EventKey type unknown " + typeKey);
                        }
                     }
                  }
               }
               gp = next;
            }
         }
      }
   }

   private void newEventCanceler(BEvent be) {
      //if event is accepted, event updates active GesturePointers
      newEventActivateGesture(be);

      newEventCancelerSub(be);
   }

   protected abstract void newEventCancelerSub(BEvent be);

   /**
    * Event is written as a Sync Check to because it is a sub event and should be generated by the replay
    * <br>
    * Local queue of events to execute before a repaint is called.
    * <br>
    * After a release, if a gesure is detected, it will be queuePost.
    * <br>
    * Event queue here are virtual events, they can cancel running {@link BaseJob}? Yes. Virtual Keyboard
    * events queued here will cancel jobs.
    * <br>
    * @param de
    */
   public void queuePost(final BEvent de) {
      //append event in list
      if (isInnerEvents) {

      } else {
         checkType(de);
         sendEvent(de);
      }
   }

   /**
    * Before an event is processed by the application with .
    * Queue Post current event and continue with {@link BEvent}.
    * <br>
    * If QueuePre is disabled, queuePost the given Event
    * @param cancelEvent
    */
   public void queuePre(BEvent de) {
      if (isInnerEvents) {

      } else {
         sendEvent(de);
      }
   }

   private void recordEventDevice(DeviceEvent de) {

      output.writeByte(de.getDeviceType());
      output.writeByte(de.getDeviceID());
      output.writeShort(de.getDeviceMode());
      output.writeShort(de.getDeviceButton());
      output.writeInt(getTimeDelta());
      //each 100 events write a reference
   }

   public abstract void requestRepeat(RepeatEvent er);

   /**
    * The issue is lost releases events when application loses focus.
    * Those releases events are never registered by this {@link InputState} and it will think forever that
    * the key is pressed. outside.
    * On the other hand, it is not able to know if keys are pressed when focus returns.
    * <br>
    * The strategy is to ignore thoses. It will simply generate lose release events
    */
   public void resetPresses() {
      //System.out.println("#InputConfig ResetPresses");
      //send releases to all pressed
      generateRelease(deviceKeyMainKeyboard);
      generateRelease(deviceKeyMainPointer);
      generateRelease(deviceKeysGamePads);
      generateRelease(deviceKeysKeyboard);
      generateRelease(deviceKeysPointers);
   }

   private void sendEvent(final BEvent de) {
      if (de == null) {
         throw new NullPointerException();
      }
      cuc.getExecutor().executeMainLater(new Runnable() {

         public void run() {
            eventCanvas.event(de);
         }
      });
   }

   void setEventID(int eid) {
      eventID = eid;
   }

   public void setFlag(int flag, boolean v) {
      flags = BitUtils.setFlag(flags, flag, v);
   }

   public void setSimulationTime(double time, double dt) {
      simulTime = time;
      simulDelta = dt;
   }

   private void timeEvent() {
      timeCurrent = cuc.getTimeCtrl().getTickMillis();
   }

   private void timeKeyEvent() {
      timeEvent();
   }

   /**
    * Timed
    */
   private void timePointerEvent() {
      timeEvent();
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, InputState.class, 3370);
      toStringPrivate(dc);
      super.toString(dc.sup());

      dc.appendVarWithSpace("getKeyCode", getKeyCode());
      dc.appendVarWithSpace("Mode", ToStringStaticCoreUi.toStringMod(getMode()));
      dc.nl();
      dc.appendVarWithSpace("isKeyTyped", isKeyTyped());
      dc.appendVarWithSpace("isFastKeyTyped", isLastKeyFastTyped(getKeyCode()));
      dc.appendVarWithSpace("nUplePressed", getNUplePressed());
      dc.appendVarWithSpace("isDoubleTap", isDoubleTapPointer0Button0());
      dc.nl();
      dc.appendVarWithSpace("getNumKeysPressed", getNumKeysPressed());

      KeyEventListed[] pressedKeys = getPressedKeys();

      int simulTimeOut = eventCanvas.getInputSettings().getSimultaneousTimeOut();

      for (int i = 0; i < pressedKeys.length; i++) {
         dc.nl();
         KeyEventListed ke = pressedKeys[i];
         long timePressed = ke.getTimePressed();
         for (int j = 0; j < pressedKeys.length; j++) {
            if (j != i) {
               KeyEventListed kej = pressedKeys[j];
               long timePressedJ = kej.getTimePressed();
               int diff = Math.abs((int) (timePressed - timePressedJ));
               if (diff <= simulTimeOut) {
                  dc.append("{simul}");
               }
            }
         }
         pressedKeys[i].toString1Line(dc);
      }

      dc.nlLvl(eventCurrent, "eventCurrent");
      dc.appendVarWithSpace("TimeDelta Between", getTimeDelta());
      dc.nlLvl(eventPrevious, "eventPrevious");

      dc.nl();
      dc.appendVar("numTotalActiveGestures", numTotalActiveGestures);

      dc.nlLvlArray("Pointers", pointers);

      dc.appendVarWithSpace("deviceKeyMainKeyboard", deviceKeyMainKeyboard);
      dc.appendVarWithSpace("deviceKeyMainPointer", deviceKeyMainPointer);
      dc.appendVarWithSpace("deviceKeyMainTouchScreen", deviceKeyMainTouchScreen);

      dc.nlLvlArray("deviceKeysGamePads", deviceKeysGamePads);

      dc.nlLvl(lastKeyEvent, "lastKeyEvent");
      dc.nlLvl(lastCanvasEvent, "lastCanvasEvent");
      dc.nlLvlArray("lastPointerEvents", lastPointerEvents);

      dc.nlLvl(lastRepeatEvent, "lastRepeatEvent");
      dc.nlLvl(lastSensorEvent, "lastSensorEvent");
      dc.nlLvl(lastGestureEvent, "lastGestureEvent");

      dc.nlLvl("bEventActive", bEventActive);
      dc.nlLvl("activeDevice", activeDevice);

      dc.nlLvl(eventCanvas.getInputSettings());
      dc.nlLvl1Line(eventCanvas, "CanvasLast");

   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, InputState.class);
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());

      dc.appendVarWithSpace("", eventCurrent.toString1Line());
      KeyEventListed[] pressedKeys = getPressedKeys();
      if (pressedKeys.length > 0) {
         dc.appendWithSpace("PressedKeys=");
         for (int i = 0; i < pressedKeys.length; i++) {
            if (i != 0) {
               dc.append(",");
            }
            dc.append(pressedKeys[i].getEventPressed().getUserStringButton());
         }
      }
   }

   public String toString1LinePressed() {
      StringBBuilder sb = new StringBBuilder(uc);
      sb.append("#InpuConfig ");
      sb.append(getKeyCode());
      sb.append('\t');
      sb.append(ToStringStaticCoreUi.toStringMod(getMode()));
      sb.append('\t');
      sb.append(getX() + "," + getY());
      return sb.toString();
   }

   public String toStringCurrent1line() {
      BEvent lastE = eventCurrent;
      return lastE.getUserLineString();
   }

   private void toStringPrivate(Dctx dc) {

   }

   //#enddebug
}
