package pasa.cbentley.framework.coreui.src4.ctx;

import pasa.cbentley.core.src4.logging.ToStringStaticBase;
import pasa.cbentley.framework.coreui.src4.event.ITechEventKey;
import pasa.cbentley.framework.coreui.src4.interfaces.ITechEventHost;
import pasa.cbentley.framework.coreui.src4.interfaces.ITechSenses;
import pasa.cbentley.framework.coreui.src4.tech.IInput;
import pasa.cbentley.framework.coreui.src4.tech.ITechCodes;
import pasa.cbentley.framework.coreui.src4.tech.ITechGestures;
import pasa.cbentley.framework.coreui.src4.tech.ITechHostUI;

public class ToStringStaticCoreUi extends ToStringStaticBase implements ITechCodes {

   public static String toStringEventKeyModeType(int modeType) {
      switch (modeType) {
         case ITechEventKey.EVENT_KEY_DEVICE_MODE_TYPE_0_INVERSE:
            return "Inverse";
         case ITechEventKey.EVENT_KEY_DEVICE_MODE_TYPE_1_SAME:
            return "Same";
         case ITechEventKey.EVENT_KEY_DEVICE_MODE_TYPE_2_ANY:
            return "Any";
         default:
            return "UnknownModeType" + modeType;
      }
   }

   public static String toStringGestureType(int key) {
      switch (key) {
         case ITechGestures.GESTURE_TYPE_0_RAW:
            return "Raw";
         case ITechGestures.GESTURE_TYPE_1_DRAG_SLIDE:
            return "Slide";
         case ITechGestures.GESTURE_TYPE_2_SWIPE:
            return "Swipe";
         case ITechGestures.GESTURE_TYPE_3_FLING:
            return "Fling";
         case ITechGestures.GESTURE_TYPE_4_ENTER:
            return "Enter";
         case ITechGestures.GESTURE_TYPE_5_EXIT:
            return "Exit";
         case ITechGestures.GESTURE_TYPE_6_PATH:
            return "Path";
         case ITechGestures.GESTURE_TYPE_7_ALLER_RETOUR:
            return "AllerRetour";
         case ITechGestures.GESTURE_TYPE_8_SWINGS:
            return "Swings";
         default:
            break;
      }
      return "UnknownGesture" + key;
   }

   public static String toStringGridArea(int gridFlags) {
      switch (gridFlags) {
         case ITechGestures.GRID_X1_Y1:
            return "X1_Y1";
         case ITechGestures.GRID_X1_Y2:
            return "X1_Y2";
         case ITechGestures.GRID_X1_Y3:
            return "X1_Y3";
         case ITechGestures.GRID_X2_Y1:
            return "X2_Y1";
         case ITechGestures.GRID_X2_Y2:
            return "X2_Y2";
         case ITechGestures.GRID_X2_Y3:
            return "X2_Y3";
         case ITechGestures.GRID_X3_Y1:
            return "X3_Y1";
         case ITechGestures.GRID_X3_Y2:
            return "X3_Y2";
         case ITechGestures.GRID_X3_Y3:
            return "X3_Y3";
         default:
            return "Unknown Grid " + gridFlags;
      }
   }

   public static String toStringGridType(int type) {
      switch (type) {
         case ITechGestures.GRID_TYPE_11_1x1:
            return "1x1";
         case ITechGestures.GRID_TYPE_12_1x2:
            return "1x2";
         case ITechGestures.GRID_TYPE_21_2x1:
            return "2x1";
         case ITechGestures.GRID_TYPE_22_2x2:
            return "2x2";
         case ITechGestures.GRID_TYPE_23_2x3:
            return "2x3";
         case ITechGestures.GRID_TYPE_33_3x3:
            return "3x3";
         case ITechGestures.GRID_TYPE_32_3x2:
            return "3x2";
         case ITechGestures.GRID_TYPE_31_3x1:
            return "3x1";
         case ITechGestures.GRID_TYPE_13_1x3:
            return "1x3";
         default:
            return "Unknown Grid " + type;
      }
   }

   /**
    * TODO When Keyboard is in Russian mode.... We are the framework level. this can only be known
    * by the Driver. Some Kind of Locale
    * @param key
    * @return
    */
   public static String toStringKey(final int key) {
      switch (key) {
         case KEY_NUM0:
            return "0";
         case KEY_NUM1:
            return "1";
         case KEY_NUM2:
            return "2";
         case KEY_NUM3:
            return "3";
         case KEY_NUM4:
            return "4";
         case KEY_NUM5:
            return "5";
         case KEY_NUM6:
            return "6";
         case KEY_NUM7:
            return "7";
         case KEY_NUM8:
            return "8";
         case KEY_NUM9:
            return "9";
         case KEY_STAR:
            return "*";
         case KEY_POUND:
            return "#";
         case KEY_DELETE_CHAR:
            return "Backspace";
         case KEY_BACK:
            return "Back";
         case KEY_CANCEL:
            return "Cancel";
         case KEY_UP:
            return "Up";
         case KEY_DOWN:
            return "Down";
         case KEY_FIRE:
            return "Enter";
         case KEY_LEFT:
            return "Left";
         case KEY_RIGHT:
            return "Right";
         case KEY_SHIFT:
            return "Shift";
         case KEY_CTRL:
            return "Ctrl";
         case KEY_MENU_LEFT:
            return "MenuLeft";
         case KEY_MENU_RIGHT:
            return "MenuRight";
         case KEY_VOLUME_UP:
            return "VolumeUp";
         case KEY_VOLUME_DOWN:
            return "VolumeDown";
         case KEY_PLAY_MUSIC:
            return "Play";
         case KEY_PHOTO:
            return "Photo";
         case KEY_MINUS:
            return "Minus-";
         case KEY_PLUS:
            return "Plus+";
         case KEY_ESCAPE:
            return "Escape";
         case KEY_F1:
            return "F1";
         case KEY_F2:
            return "F2";
         case KEY_F3:
            return "F3";
         case KEY_F4:
            return "F4";
         case KEY_F5:
            return "F5";
         case KEY_F6:
            return "F6";
         case KEY_F7:
            return "F7";
         case KEY_F8:
            return "F8";
         case KEY_F9:
            return "F9";
         case KEY_F10:
            return "F10";
         case KEY_F11:
            return "F11";
         case KEY_F12:
            return "F12";
         case WILDCARD_001_NUMBERS:
            return "Numeric";
         case KEY_CAPS_LOCK:
            return "CapsLock";
         case KEY_TAB:
            return "Tab";
         case KEY_ALT:
            return "Alt";
         case KEY_INSERT:
            return "Insert";
         case KEY_WINDOWS:
            return "Windows";

         default:
            if (key > 51 && key < 128) {
               return String.valueOf((char) key);
            }
            if (key == ' ') {
               return "Space";
            }
            return "Unknown" + key;
      }
   }

   public static String toStringMod(int mod) {
      switch (mod) {
         case IInput.MOD_0_PRESSED:
            return "Pressed";
         case IInput.MOD_1_RELEASED:
            return "Released";
         case IInput.MOD_3_MOVED:
            return "Moved";
         case IInput.MOD_4_SENSED:
            return "Sensed";
         case IInput.MOD_5_WHEELED:
            return "Wheeled";
         default:
            return "MOD_UNKNOWN" + mod;
      }
   }

   /**
    * What if a mouse has 10 buttons and 2 wheels?
    * <br>
    * We
    * @param pointerButID
    * @return
    */
   public static String toStringMouseButton(int pointerButID) {
      switch (pointerButID) {
         case ITechCodes.PBUTTON_0_DEFAULT:
            return "MouseLeft";
         case ITechCodes.PBUTTON_1_RIGHT:
            return "MouseRight";
         case ITechCodes.PBUTTON_2_MIDDLE:
            return "MouseMiddle";
         case ITechCodes.PBUTTON_4_WHEEL_DOWN:
            return "WheelDown";
         case ITechCodes.PBUTTON_3_WHEEL_UP:
            return "WheelUp";
         default:
            return "Unknown Button " + pointerButID;
      }
   }

   public static String toStringPointer(int pointer) {
      switch (pointer) {
         case ITechCodes.POINTER_ID_0:
            return "1stPointer";
         case ITechCodes.POINTER_ID_1:
            return "2ndPointer";
         case ITechCodes.POINTER_ID_2:
            return "3rdPointer";
         default:
            return "UnknownPointer" + pointer;
      }
   }

   public static String toStringRepeatType(int type) {
      switch (type) {
         case IInput.REPEAT_0_INFINITE:
            return "Infinite";
         case IInput.REPEAT_1_FINITE:
            return "Finite";
         case IInput.REPEAT_2_LONG:
            return "Long";
         case IInput.REPEAT_3_TRAIL_FUNCTION:
            return "Trail";
         case IInput.REPEAT_4_PATTERN:
            return "Pattern";
         default:
            return "UnknownType " + type;
      }
   }

   public static String toStringScreenMode(int mode) {
      switch (mode) {
         case ITechHostUI.SCREEN_0_TOP_NORMAL:
            return "Normal";
         case ITechHostUI.SCREEN_1_BOT_UPSIDEDOWN:
            return "UpSideDown";
         case ITechHostUI.SCREEN_2_LEFT_ROTATED:
            return "RotatedLeft";
         case ITechHostUI.SCREEN_3_RIGHT_ROTATED:
            return "RotatedRight";
         default:
            return "Unknown ScreenMode " + mode;
      }
   }

   /**
    * {@link ITechEventHost}
    * @param subtype
    * @return
    */
   public static String toStringAppliAction(int subtype) {
      switch (subtype) {
         case ITechEventHost.ACTION_1_CLOSE:
            return "Closed";
         case ITechEventHost.ACTION_2_MOVED:
            return "Moved";
         case ITechEventHost.ACTION_3_RESIZED:
            return "Resized";
         case ITechEventHost.ACTION_4_FOCUS_GAIN:
            return "FocusGain";
         case ITechEventHost.ACTION_5_FOCUS_LOSS:
            return "FocusLoss";
         case ITechEventHost.ACTION_6_NOTIFY_SHOW:
            return "NotifyShow";
         case ITechEventHost.ACTION_7_NOTIFY_HIDE:
            return "NotifyHide";
         case ITechEventHost.ACTION_8_APPLI_PAUSED:
            return "AppliPaused";
         case ITechEventHost.ACTION_9_APPLI_STARTED:
            return "AppliStarted";
         default:
            return "UnknownAction" + subtype;
      }
   }

   public static String toStringButtonShort(int pointerButID) {
      switch (pointerButID) {
         case ITechCodes.PBUTTON_4_WHEEL_DOWN:
            return "WheelDown";
         case ITechCodes.PBUTTON_3_WHEEL_UP:
            return "WheelUp";
         case ITechCodes.PBUTTON_0_DEFAULT:
            return "Left";
         case ITechCodes.PBUTTON_1_RIGHT:
            return "Right";
         case ITechCodes.PBUTTON_2_MIDDLE:
            return "Middle";
         default:
            return "Unknown Button " + pointerButID;
      }
   }

   public static String toStringDeviceType(int devType) {
      switch (devType) {
         case IInput.DEVICE_0_KEYBOARD:
            return "Keyboard";
         case IInput.DEVICE_1_MOUSE:
            return "Mouse";
         case IInput.DEVICE_2_GAMEPAD:
            return "Gamepad";
         case IInput.DEVICE_3_FINGER:
            return "Finger";
         case IInput.DEVICE_4_SCREEN:
            return "Screen";
         case IInput.DEVICE_5_OTHER:
            return "Other";
         case IInput.DEVICE_6_POINTER:
            return "Pointer";
         case IInput.DEVICE_7_SENSOR:
            return "Sensor";
         default:
            return "UnknownDeviceType" + devType;
      }
   }

   public static String toStringEventType(int t) {
      switch (t) {
         case IInput.TYPE_0_OTHER:
            return "Other";
         case IInput.TYPE_1_DEVICE:
            return "Device";
         case IInput.TYPE_2_GESTURE:
            return "Gesture";
         case IInput.TYPE_3_CANVAS:
            return "Canvas";
         case IInput.TYPE_4_REPEAT:
            return "Repeat";
         case IInput.TYPE_5_CTX_CHANGE:
            return "CtxChange";
         case IInput.TYPE_6_APPLI:
            return "Appli";
         default:
            return "Unknown" + t;
      }
   }

   public static String toStringFeature(int feat) {
      switch (feat) {
         case ITechHostUI.SUP_ID_38_GAMEPADS:
            return "GamePads";
         default:
            return "UnknownFeature" + feat;
      }
   }

   /**
    * 
    * @param key
    * @return
    */
   public static String toStringGestureDir(int key) {
      switch (key) {
         case ITechGestures.GESTURE_DIR_00_ANY:
            return "Any";
         case ITechGestures.GESTURE_DIR_01_VERTICAL:
            return "Vertical";
         case ITechGestures.GESTURE_DIR_02_HORIZONTAL:
            return "Horizontal";
         case ITechGestures.GESTURE_DIR_03_TOP:
            return "Top";
         case ITechGestures.GESTURE_DIR_04_BOT:
            return "Bot";
         case ITechGestures.GESTURE_DIR_05_LEFT:
            return "Left";
         case ITechGestures.GESTURE_DIR_06_RIGHT:
            return "Right";
         case ITechGestures.GESTURE_DIR_07_TOP_LEFT:
            return "TopLeft";
         case ITechGestures.GESTURE_DIR_08_TOP_RIGHT:
            return "TopRight";
         case ITechGestures.GESTURE_DIR_09_BOT_LEFT:
            return "BotLeft";
         case ITechGestures.GESTURE_DIR_10_BOT_RIGHT:
            return "BotRight";
         case ITechGestures.GESTURE_NOT_DETECTED:
            return "NotDetected";
         case ITechGestures.GESTURE_UNKNOWN:
            return "NotComputed";
         default:
            return "UnknownGestureDir" + key;
      }
   }

   public static final String toStringKeyEventType(int type) {
      switch (type) {
         case ITechEventKey.KEY_TYPE_0_FIRE:
            return "Fire";
         case ITechEventKey.KEY_TYPE_1_CANCEL:
            return "Cancel";
         case ITechEventKey.KEY_TYPE_2_FIRE_AND_CANCEL:
            return "FireCancel";
         default:
            return "Unknown " + type;
      }
   }

   public static final String toStringKeyEventUserLine(int type) {
      switch (type) {
         case ITechEventKey.KEY_TYPE_0_FIRE:
            return "fire";
         case ITechEventKey.KEY_TYPE_1_CANCEL:
            return "cancel";
         case ITechEventKey.KEY_TYPE_2_FIRE_AND_CANCEL:
            return "fire&cancel";
         default:
            return "Unknown " + type;
      }
   }

   public static String toStringScreenConfig(int c) {
      switch (c) {
         case ITechHostUI.SCREEN_0_TOP_NORMAL:
            return "Normal";
         case ITechHostUI.SCREEN_1_BOT_UPSIDEDOWN:
            return "UpsideDown";
         case ITechHostUI.SCREEN_2_LEFT_ROTATED:
            return "Left";
         case ITechHostUI.SCREEN_3_RIGHT_ROTATED:
            return "Right";
         default:
            return "UnknownConfig" + c;
      }
   }

   public static String toStringSense(int type) {
      switch (type) {
         case ITechSenses.GESTURE_TYPE_05_SHAKE:
            return "Shake";
         case ITechSenses.GESTURE_TYPE_07_MOVE:
            return "Move";
         case ITechSenses.GESTURE_TYPE_08_LIGHT:
            return "Light";
         case ITechSenses.SENSOR_TYPE_04_AXIS:
            return "Axis";
         case ITechSenses.GESTURE_TYPE_10_LOCATION:
            return "Location";
         case ITechSenses.GESTURE_TYPE_11_COMPASS:
            return "Compass";
         case ITechSenses.GESTURE_TYPE_12_DEVICE:
            return "Device";
         default:
            return "UnknownSenseType" + type;
      }
   }

   public static String toStringTiming(int time) {
      switch (time) {
         case ITechCodes.TIMING_0_NONE:
            return "Normal";
         case ITechCodes.TIMING_1_SLOW:
            return "Slow";
         case ITechCodes.TIMING_3_FAST:
            return "Fast";
         default:
            return "UnknownTiming" + time;
      }
   }

}
