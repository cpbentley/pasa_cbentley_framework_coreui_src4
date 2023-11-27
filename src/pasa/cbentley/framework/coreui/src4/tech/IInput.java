package pasa.cbentley.framework.coreui.src4.tech;

import pasa.cbentley.core.src4.interfaces.C;
import pasa.cbentley.framework.coreui.src4.event.DeviceEvent;
import pasa.cbentley.framework.coreui.src4.event.GestureEvent;
import pasa.cbentley.framework.coreui.src4.event.RepeatEvent;
import pasa.cbentley.framework.coreui.src4.interfaces.ITechEventHost;

/**
 * defines static constants valid across the whole Bentley's Framework.
 * <br>
 * <br>
 * 
 * @author Charles-Philip
 *
 */
public interface IInput {

   /**
    * 4 bits for 8 different devices
    * TODO what about multiple keyboard?
    * 
    * Accept the following mod
    * <li> {@link IInput#MOD_0_PRESSED}
    * <li> {@link IInput#MOD_1_RELEASED}
    */
   public static final int DEVICE_0_KEYBOARD       = 0;

   /**
    * Accept the following mod
    * <li> {@link IInput#MOD_0_PRESSED}
    * <li> {@link IInput#MOD_1_RELEASED}
    * <li> {@link IInput#MOD_3_MOVED}
    * <li> {@link IInput#MOD_5_WHEELED}
    * <br>
    * Each pointer has button options
    * <li> {@link IBCodes#PBUTTON_0_DEFAULT}
    * <li> {@link IBCodes#PBUTTON_1_RIGHT}
    * <li> {@link IBCodes#PBUTTON_2_MIDDLE}
    * <li> {@link IBCodes#PBUTTON_3_WHEEL_UP}
    * <li> {@link IBCodes#PBUTTON_4_WHEEL_DOWN}
    * <br>
    * <br>
    * CmdTrigger can model, Pressed Center Ctx, Dragged to Ctx
    */
   public static final int DEVICE_1_MOUSE          = 1;

   public static final int DEVICE_2_GAMEPAD        = 2;

   /**
    * 
    * A finger is a special pointer with one button.
    * <br>
    * While mouse pointers are always on, finger devices 
    * are one button/key devices with a size and pressure.
    * <br>
    * 
    */
   public static final int DEVICE_3_FINGER         = 3;

   public static final int DEVICE_4_SCREEN         = 4;

   public static final int DEVICE_5_OTHER          = 5;

   /**
    * <li> real mouse
    * <li> real finger
    * <li> virtual mouse moved with fingers
    */
   public static final int DEVICE_6_POINTER        = 6;

   /**
    * The Mod value depends on each sensor
    * <li> {@link DeviceEvent#getDeviceButton()}
    * <li> {@link DeviceEvent#getDeviceID()} identifies the sensors if several
    */
   public static final int DEVICE_7_SENSOR         = 7;

   /**
    * MOD type for
    * <li> {@link IInput#TYPE_0_KEYBOARD}
    * <li> {@link IInput#TYPE_6_POINTER}
    */
   public static final int MOD_0_PRESSED           = 0;

   /**
    * MOD type for
    * <li> {@link IInput#TYPE_0_KEYBOARD}
    * <li> {@link IInput#TYPE_6_POINTER} Pointer Button
    */
   public static final int MOD_1_RELEASED          = 1;

   /**
    * Mod type for {@link IInput#TYPE_6_POINTER}.
    * For axis, throttle up down with a middle (released non active state)
    * When released, the axis active button is {@link IInput#MOD_1_RELEASED}
    */
   public static final int MOD_3_MOVED             = 3;

   /**
    * 
    */
   public static final int MOD_4_SENSED            = 4;

   /**
    * Wheel events are events that are generated once.
    * <br>
    * There is no pressed/release state associated.
    * They just happen.
    * Wheel is of type {@link IInput#TYPE_6_POINTER}.
    * <br>
    * It could be another hardware. They have up/down left/right
    * and an amplitude.
    * <br>
    * A device may have several wheels.
    */
   public static final int MOD_5_WHEELED           = 5;

   public static final int MOD_6_FLOAT             = 6;

   public static final int MOD_8_RESIZED           = 8;

   public static final int MOVE_0_MOVE             = 0;

   public static final int MOVE_1_ENTER            = 1;

   public static final int MOVE_2_EXIT             = 2;

   public static final int POINTER_0_DEFAULT       = 0;

   public static final int POINTER_1               = 1;

   public static final int POINTER_2               = 2;

   public static final int POINTER_3               = 3;

   public static final int POINTER_4               = 4;

   /**
    * Infinite until the cancelling event occurs
    */
   public static final int REPEAT_0_INFINITE       = 0;

   /**
    * Repeat a number of times. No user actoin to change state.
    */
   public static final int REPEAT_1_FINITE         = 1;

   /**
    * Repeat once to simulate a long press.
    * <br>
    * Long press are event repeats with a special long timeouts
    * When a long press is called, key contains the milliseconds.
    * Except for the special timing values
    * <li> {@link IBCodes#TIMING_0_NONE}
    * <li> {@link IBCodes#TIMING_1_SLOW}
    * <li> {@link IBCodes#TIMING_3_FAST}
    * <br>
    * <br>
    * Long press event may generate pre event and post events.
    * <br>
    * Event is generated as long as it is pressed
    * <br>
    * <li> {@link GestureEvent#timingFinal} total timing since start
    * <li> {@link GestureEvent#timing} current timing (reset to 0 when timeOut is reached)
    * <li> {@link RepeatEvent#count} the number of long presses
    * 
    */
   public static final int REPEAT_2_LONG           = 2;

   /**
    * A Repeat function decides when to stop the repeat.
    * 
    * Repeat after a gesture is finished based on specific conditions
    * <br>
    * Type of repeat
    */
   public static final int REPEAT_3_TRAIL_FUNCTION = 3;

   /**
    * User activated pattern
    */
   public static final int REPEAT_4_PATTERN        = 4;

   /**
    * 3 bits range
    */
   public static final int TYPE_0_OTHER            = 0;

   /**
    * 
    */
   public static final int TYPE_1_DEVICE           = 1;

   /**
    * Gestures are combinations of events/sensors that match a set of
    * constraints. 
    * <br>
    * <br>
    * Fling is a pointer movement within the press and a release of a button.
    * <br>
    * Continuous gestures such as Pinch/Drag/Slide(drag with straight line constraint).
    * <br>
    * <br>
    * 
    * <li> {@link ITechGestures#GESTURE_TYPE_1_DRAG_SLIDE}
    * <li> {@link ITechGestures#GESTURE_TYPE_3_FLING}
    * <li> {@link ITechGestures#GESTURE_DOUBLE_TYPE_1_PINCH}
    * 
    * <br>
    * <br>
    * Key id is
    * <li> {@link C#DIR_0_TOP}
    * <li> {@link C#DIR_1_BOTTOM}
    * <li> {@link C#DIR_2_LEFT}
    * <li> {@link C#DIR_3_RIGHT}
    * <br>
    * <br>
    * Long Press starts, Key is Press, Long Press Preset Event, Key is Released.
    * <br>
    * Does that invalidate a Key Type sequence?
    */
   public static final int TYPE_2_GESTURE          = 2;

   public static final int TYPE_3_CANVAS           = 3;

   public static final int TYPE_4_REPEAT           = 4;

   /**
    * When a context change occurs, it can breaks down 
    * trigger matches, depending 
    * <br>
    * Each Trigger unit has a flag. if ctx dependant,
    * the context id registered during the trigger
    * must be the same.
    * <br>
    * <br>
    * When registering for a specific context, it must IDs ?
    * Dynamic or static? We can use a static context id.
    * <br>
    * This input event is generated by the Framework.
    * <li> An integer ID represents the context.
    * <li> the context type. Key Context, Pointer Context
    * <li> In multi keyboard context
    */
   public static final int TYPE_5_CTX_CHANGE       = 5;

   /**
    * Mods for those appli actions.
    * {@link ITechEventHost#ACTION_1_CLOSE}
    */
   public static final int TYPE_6_APPLI            = 6;

   public static final int TYPE_7_GROUP            = 7;

}
