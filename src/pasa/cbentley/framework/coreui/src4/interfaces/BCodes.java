package pasa.cbentley.framework.coreui.src4.interfaces;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.framework.coreui.src4.tech.ITechCodes;
import pasa.cbentley.framework.coreui.src4.tech.ITechGestures;

public class BCodes implements ITechCodes, ITechGestures {

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
      char c = getChar(keyCode);
      if(c == 'Z') {
         return "UnkownKeyName" + keyCode;
      } else {
         return String.valueOf(c);
      }
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
