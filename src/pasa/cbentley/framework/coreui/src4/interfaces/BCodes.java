package pasa.cbentley.framework.coreui.src4.interfaces;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.framework.coreui.src4.tech.IBCodes;
import pasa.cbentley.framework.coreui.src4.tech.ITechGestures;
import pasa.cbentley.framework.coreui.src4.tech.IInput;

public class BCodes implements IBCodes, ITechGestures {

   public static final int createLock(int key1, int key2) {
      return (key1 << 8) + key2;
   }

   public static int createLock(int key1, int key2, int key3) {
      return (key1 << 16) + (key2 << 8) + key3;
   }

   public static char getChar(int keyCode) {
      switch (keyCode) {
         case KEY_NUM0:
            return '0';
         case KEY_NUM1:
            return '1';
         case KEY_NUM2:
            return '2';
         case KEY_NUM3:
            return '3';
         case KEY_NUM4:
            return '4';
         case KEY_NUM5:
            return '5';
         case KEY_NUM6:
            return '6';
         case KEY_NUM7:
            return '7';
         case KEY_NUM8:
            return '8';
         case KEY_NUM9:
            return '9';
         case KEY_STAR:
            return '*';
         case KEY_POUND:
            return '#';
         case KEY_BACK:
            return CHAR_BACK;
         case KEY_CANCEL:
            return CHAR_CANCEL;
         case KEY_DOWN:
            return CHAR_DOWN;
         case KEY_FIRE:
            return CHAR_FIRE;
         case KEY_LEFT:
            return CHAR_LEFT;
         case KEY_RIGHT:
            return CHAR_RIGHT;
         case KEY_MENU_LEFT:
            return CHAR_MENU_LEFT;
         case KEY_MENU_RIGHT:
            return CHAR_MENU_RIGHT;
         case KEY_PHOTO:
            return CHAR_PHOTO;
         case KB_065_A:
            return 'a';
         case KB_066_B:
            return 'b';
         case KB_067_C:
            return 'c';
         case KB_068_D:
            return 'd';
         case KB_069_E:
            return 'e';
         case KB_070_F:
            return 'f';
         case KB_071_G:
            return 'g';
         case KB_072_H:
            return 'h';
         case KB_073_I:
            return 'i';
         case KB_074_J:
            return 'j';
         case KB_075_K:
            return 'k';
         case KB_076_L:
            return 'l';
         case KB_077_M:
            return 'm';
         case KB_078_N:
            return 'n';
         case KB_079_O:
            return 'o';
         case KB_080_P:
            return 'p';
         case KB_081_Q:
            return 'q';
         case KB_082_R:
            return 'r';
         case KB_083_S:
            return 's';
         case KB_084_T:
            return 't';
         case KB_085_U:
            return 'u';
         case KB_086_V:
            return 'v';
         case KB_087_W:
            return 'w';
         case KB_088_X:
            return 'x';
         case KB_089_Y:
            return 'y';
         case KB_090_Z:
            return 'z';
         default:
            return 'Z';
      }
   }

   public static String getGestureState(int state) {
      switch (state) {
         case GESTURE_STATE_0_RUNNING:
            return "Running";
         case GESTURE_STATE_1_FINISHED:
            return "Finished";
         case GESTURE_STATE_2_CANCELED:
            return "Canceled";
         default:
            return "UnknownState" + state;
      }
   }

   public static String getKeyName(int keyCode) {
      return "UnkownKeyName" + keyCode;
   }

   public static int getLockSize(int lock) {
      int count = 0;
      int key1 = (lock >> 24) & 0xFF;
      int key2 = (lock >> 16) & 0xFF;
      int key3 = (lock >> 8) & 0xFF;
      int key4 = (lock >> 0) & 0xFF;
      if (key4 != 0)
         count++;
      if (key3 != 0)
         count++;
      if (key2 != 0)
         count++;
      if (key1 != 0)
         count++;
      return count;
   }

   public static String getStringRepeatType(int type) {
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

   /**
    * Simply gets a Number of the Key, if number key
    * @param keyCode
    * @return
    */
   public static int getNum(int keyCode) {
      switch (keyCode) {
         case KEY_NUM0:
            return 0;
         case KEY_NUM1:
            return 1;
         case KEY_NUM2:
            return 2;
         case KEY_NUM3:
            return 3;
         case KEY_NUM4:
            return 4;
         case KEY_NUM5:
            return 5;
         case KEY_NUM6:
            return 6;
         case KEY_NUM7:
            return 7;
         case KEY_NUM8:
            return 8;
         case KEY_NUM9:
            return 9;
         default:
            return -1;
      }
   }

   public static String getStringButtonShort(int pointerButID) {
      switch (pointerButID) {
         case PBUTTON_4_WHEEL_DOWN:
            return "WheelDown";
         case PBUTTON_3_WHEEL_UP:
            return "WheelUp";
         case PBUTTON_0_DEFAULT:
            return "Left";
         case PBUTTON_1_RIGHT:
            return "Right";
         case PBUTTON_2_MIDDLE:
            return "Middle";
         default:
            return "Unknown Button " + pointerButID;
      }
   }

   public static String getStringEventType(int t) {
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

   public static String getStringAppliAction(int subtype) {
      switch (subtype) {
         case IHostEvents.ACTION_1_CLOSE:
            return "Closed";
         case IHostEvents.ACTION_2_MOVED:
            return "Moved";
         case IHostEvents.ACTION_3_RESIZED:
            return "Resized";
         case IHostEvents.ACTION_4_FOCUS_GAIN:
            return "FocusGain";
         case IHostEvents.ACTION_5_FOCUS_LOSS:
            return "FocusLoss";
         default:
            return "UnknownAction" + subtype;
      }
   }

   /**
    * 
    * @param key
    * @return
    */
   public static String getStringGestureDir(int key) {
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

   public static String getStringDeviceType(int devType) {
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

   public static String getStringSense(int type) {
      switch (type) {
         case ISenses.GESTURE_TYPE_05_SHAKE:
            return "Shake";
         case ISenses.GESTURE_TYPE_07_MOVE:
            return "Move";
         case ISenses.GESTURE_TYPE_08_LIGHT:
            return "Light";
         case ISenses.SENSOR_TYPE_04_AXIS:
            return "Axis";
         case ISenses.GESTURE_TYPE_10_LOCATION:
            return "Location";
         case ISenses.GESTURE_TYPE_11_COMPASS:
            return "Compass";
         case ISenses.GESTURE_TYPE_12_DEVICE:
            return "Device";
         default:
            return "UnknownSenseType" + type;
      }
   }

   public static String getStringGestureType(int key) {
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

   /**
    * TODO When Keyboard is in Russian mode.... We are the framework level. this can only be known
    * by the Driver. Some Kind of Locale
    * @param key
    * @return
    */
   public static String getStringKey(final int key) {
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

   public static String getStringMod(int mod) {
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
   public static String getStringMouseButton(int pointerButID) {
      switch (pointerButID) {
         case PBUTTON_0_DEFAULT:
            return "MouseLeft";
         case PBUTTON_1_RIGHT:
            return "MouseRight";
         case PBUTTON_2_MIDDLE:
            return "MouseMiddle";
         case PBUTTON_4_WHEEL_DOWN:
            return "WheelDown";
         case PBUTTON_3_WHEEL_UP:
            return "WheelUp";
         default:
            return "Unknown Button " + pointerButID;
      }
   }

   public static String getStringPointer(int pointer) {
      switch (pointer) {
         case POINTER_ID_0:
            return "1stPointer";
         case POINTER_ID_1:
            return "2ndPointer";
         case POINTER_ID_2:
            return "3rdPointer";
         default:
            return "UnknownPointer" + pointer;
      }
   }

   public static String getStringGridType(int type) {
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

   public static String getStringGridArea(int gridFlags) {
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

   public static String getStringTiming(int time) {
      switch (time) {
         case TIMING_0_NONE:
            return "Normal";
         case TIMING_1_SLOW:
            return "Slow";
         case TIMING_3_FAST:
            return "Fast";
         default:
            return "UnknownTiming" + time;
      }
   }

   /**
    * Maps the char of a trigger to its integer code
    * @param c
    * @return
    */
   public static int map(char c) {
      int keyCode = -5;
      switch (c) {
         case '0':
            return KEY_NUM0;
         case '1':
            return KEY_NUM1;
         case '2':
            return KEY_NUM2;
         case '3':
            return KEY_NUM3;
         case '4':
            return KEY_NUM4;
         case '5':
            return KEY_NUM5;
         case '6':
            return KEY_NUM6;
         case '7':
            return KEY_NUM7;
         case '8':
            return KEY_NUM8;
         case '9':
            return KEY_NUM8;
         case CHAR_STAR:
            return KEY_STAR;
         case CHAR_POUND:
            return KEY_POUND;
         case CHAR_CANCEL:
            //cancel
            return KEY_CANCEL;
         case CHAR_UP:
            //up
            return KEY_UP;
         case CHAR_RIGHT:
            // right direction
            return KEY_RIGHT;
         case CHAR_LEFT:
            // left direction
            return KEY_LEFT;
         case CHAR_MENU_LEFT:
            //select left
            return KEY_MENU_LEFT;
         case CHAR_DOWN:
            //down
            return KEY_DOWN;
         case CHAR_MENU_RIGHT:
            //select right
            return KEY_MENU_RIGHT;
         case CHAR_FIRE:
            return KEY_FIRE;
         case CHAR_BACK:
            //back
            return KEY_BACK;
         case CHAR_PHOTO:
            //photo
            return KEY_PHOTO;
      }
      return keyCode;
   }

   /**
    * Map Phone key coded on 5 bits
    * @param keyCode
    * @return
    */
   public static int map(int keyCode) {
      int k = keyCode - 48;
      if (k < 0) {
         if (keyCode == 42)
            return 12;
         if (keyCode == 35)
            return 11;
         if (keyCode == -25)
            return 13;
         //keycode -1 => 21 -2 => 22
         return k + 70;
      } else {
         return k;
      }
   }

   /**
    * 0-9 mapping to code
    * @param chiffre
    * @return
    */
   public static int mapKey(int chiffre) {
      return chiffre + 48;
   }

   /**
    * inverse function of map
    * @param trigid
    * @return
    */
   public static int unmap(int trigid) {
      if (trigid < 35) {
         return 0 - trigid;
      }
      return trigid;
   }

   public String toString1Line() {
      // TODO Auto-generated method stub
      return null;
   }

   public void toString(Dctx dc) {
      // TODO Auto-generated method stub
      
   }

   public void toString1Line(Dctx dc) {
      // TODO Auto-generated method stub
      
   }

   public UCtx toStringGetUCtx() {
      // TODO Auto-generated method stub
      return null;
   }

}
