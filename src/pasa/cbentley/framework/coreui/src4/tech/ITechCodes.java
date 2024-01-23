package pasa.cbentley.framework.coreui.src4.tech;

/**
 * Defines the codes for keys
 * <br>
 * <br>
 * This class must be customized for every phone since phones
 * may have different codes for exotic keys (camera,volume,walkman)
 * When application is launched, Phone App Driver does it.
 * <br>
 * <br>
 * 
 * On some devices, when the phone is opened or closed, keyCodes are generated and can be detected by the Canvas.Keypressed() event method.
 * <br>
 * For SE Phones <br>
 * <li>keyCode -30 is returned when the phone is opened
 * <li>keyCode -31 is returned when the phone is closed
 * <br>
 * <br>
 * All Trig codes are positive values.
 * <br>
 * So they can fit into 16 bits or less
 * 
 * TODO: how do we get Function Key codes for debuggin in J2SE?
 * the J2SEDriver translates those codes.
 * @author Charles-Philip Bentley
 *
 */
public interface ITechCodes {

   public static int        AXIS_X                         = 555;

   public static int        AXIS_Y                         = 556;

   public static int        AXIS_Z                         = 557;

   /**
    * Some throttle have a end button
    */
   public static int        THROTTLE_A                     = 319;

   public static int        THROTTLE_B                     = 320;

   public static int        THROTTLE_C                     = 321;

   public static int        THROTTLE_D                     = 322;

   /**
    * Pads have buttons.
    * <br>
    * TODO pad profiles. Sega has a start button, select
    * 
    * Nintendo. has a select
    * <br>
    */
   public static final int  PAD_UP                         = 100;

   public static final int  PAD_LEFT                       = 102;

   public static final int  PAD_DOWN                       = 101;

   public static final int  PAD_RIGHT                      = 103;

   public static final int  PAD_UPZ                        = 104;

   public static final int  PAD_DOWNZ                      = 105;

   public static final int  PAD_BUTTON_0                   = 1000;

   public static final int  PAD_BUTTON_1                   = 1;

   public static final int  PAD_BUTTON_2                   = 2;

   public static final int  PAD_BUTTON_3                   = 3;

   public static final int  PAD_BUTTON_4                   = 4;

   public static final int  PAD_BUTTON_5                   = 5;

   public static final int  PAD_BUTTON_6                   = 6;

   public static final int  PAD_BUTTON_7                   = 7;

   public static final int  PAD_BUTTON_8                   = 8;

   public static final int  PAD_BUTTON_9                   = 9;

   public static final int  PAD_BUTTON_10                  = 10;

   public static final int  PAD_BUTTON_11                  = 11;

   public static final int  PAD_BUTTON_12                  = 12;

   public static final int  PAD_BUTTON_13                  = 13;

   public static final int  PAD_BUTTON_14                  = 14;

   public static final int  PAD_BUTTON_15                  = 15;

   public static final int  PAD_START                      = 98;

   public static final int  PAD_SELECT                     = 99;

   public static final int  PAD_INDEX_LEFT                 = 50;

   public static final int  PAD_INDEX_RIGHT                = 51;

   public static final char CHAR_BACK                      = '↱';

   public static final char CHAR_CANCEL                    = 'ȼ';

   public static final char CHAR_DOWN                      = '▼';

   public static final char CHAR_FIRE                      = '↲';

   public static final int  CHAR_FOLLOWS                   = ',';

   public static final char CHAR_LEFT                      = '◀';

   public static final char CHAR_MENU_LEFT                 = '«';

   public static final char CHAR_MENU_RIGHT                = '»';

   public static final char CHAR_PHOTO                     = 'X';

   public static final int  CHAR_PLUS                      = '+';

   public static final char CHAR_POUND                     = '#';

   public static final int  CHAR_PRESSED                   = '_';

   public static final int  CHAR_RELEASED                  = '^';

   public static final char CHAR_RIGHT                     = '►';

   public static final char CHAR_STAR                      = '*';

   public static final char CHAR_UP                        = '▲';

   /**
    * Identify the finger for touch screen.
    */
   public static final int  DEFAULT_POINTER_ID             = 0;

   public static final int  KB_016_MAJ                     = 16;

   public static final int  KB_020_MAJ_LOCK                = 20;

   public static final int  KB_065_A                       = 65;

   public static final int  KB_066_B                       = 66;

   public static final int  KB_067_C                       = 67;

   public static final int  KB_068_D                       = 68;

   public static final int  KB_069_E                       = 69;

   public static final int  KB_070_F                       = 70;

   public static final int  KB_071_G                       = 71;

   public static final int  KB_072_H                       = 72;

   public static final int  KB_073_I                       = 73;

   public static final int  KB_074_J                       = 74;

   public static final int  KB_075_K                       = 75;

   public static final int  KB_076_L                       = 76;

   public static final int  KB_077_M                       = 77;

   public static final int  KB_078_N                       = 78;

   public static final int  KB_079_O                       = 79;

   public static final int  KB_080_P                       = 80;

   public static final int  KB_081_Q                       = 81;

   public static final int  KB_082_R                       = 82;

   public static final int  KB_083_S                       = 83;

   public static final int  KB_084_T                       = 84;

   public static final int  KB_085_U                       = 85;

   public static final int  KB_086_V                       = 86;

   public static final int  KB_087_W                       = 87;

   public static final int  KB_088_X                       = 88;

   public static final int  KB_089_Y                       = 89;

   public static final int  KB_090_Z                       = 90;

   public static final int  KB_221_PAGE_UP                 = 221;

   public static final int  KB_222_PAGE_DOWN               = 222;

   public static final int  KB_223_PAGE_HOME               = 223;

   public static final int  KB_224_PAGE_END                = 224;

   public static final int  KEY_BACK                       = 1005;

   /**
    * Suppr
    */
   public static final int  KEY_CANCEL                     = 127;

   public static final int  KEY_CTRL                       = 17;

   public static final int  KEY_ALT                        = 18;

   public static final int  KEY_TAB                        = 9;

   public static final int  KEY_INSERT                     = 155;

   public static final int  KEY_CAPS_LOCK                  = 20;

   /**
    * Backspace
    */
   public static final int  KEY_DELETE_CHAR                = 8;

   public static final int  KEY_DOWN                       = 40;

   public static final int  KEY_ESCAPE                     = 27;

   public static final int  KEY_F1                         = 112;

   public static final int  KEY_F10                        = 121;

   public static final int  KEY_F11                        = 122;

   public static final int  KEY_F12                        = 123;

   public static final int  KEY_F2                         = 113;

   public static final int  KEY_F3                         = 114;

   public static final int  KEY_F4                         = 115;

   public static final int  KEY_F5                         = 116;

   public static final int  KEY_F6                         = 117;

   public static final int  KEY_F7                         = 118;

   public static final int  KEY_F8                         = 119;

   public static final int  KEY_F9                         = 120;

   /**
    * Fire is Enter
    */
   public static final int  KEY_FIRE                       = 10;

   public static final int  KEY_LEFT                       = 37;

   public static final int  KEY_MENU_LEFT                  = 1007;

   public static final int  KEY_MENU_RIGHT                 = 1006;

   public static final int  KEY_VOLUME_UP                  = 1008;

   public static final int  KEY_VOLUME_DOWN                = 1009;

   public static final int  KEY_PLAY_MUSIC                 = 1010;

   public static final int  KEY_MINUS                      = 109;

   public static final int  KEY_NUM0                       = 48;

   public static final int  KEY_NUM1                       = 49;

   public static final int  KEY_NUM2                       = 50;

   public static final int  KEY_NUM3                       = 51;

   public static final int  KEY_NUM4                       = 52;

   public static final int  KEY_NUM5                       = 53;

   public static final int  KEY_NUM6                       = 54;

   public static final int  KEY_NUM7                       = 55;

   public static final int  KEY_NUM8                       = 56;

   public static final int  KEY_NUM9                       = 57;

   public static final int  KEY_PHONE_CLOSED               = 1031;

   /**
    * Does not work on P990i.
    * So to check open or closed mode, one must check canvas size.
    */
   public static final int  KEY_PHONE_OPENED               = 1030;

   public static final int  KEY_PHOTO                      = 1025;

   /**
    * Same as Java
    */
   public static final int  KEY_PLUS                       = 107;

   public static final int  KEY_POUND                      = 35;

   public static final int  KEY_RIGHT                      = 39;

   public static final int  KEY_SHIFT                      = 16;

   public static final int  KEY_STAR                       = 42;

   public static final int  KEY_UP                         = 38;

   public static final int  FINGER_1                       = 0;

   public static final int  FINGER_2                       = 1;

   public static final int  FINGER_3                       = 2;

   public static final int  FINGER_4                       = 3;

   public static final int  FINGER_5                       = 4;

   /**
    * Pointers goes in multiples of 10. 10 human fingers. 2 hands.
    */
   public static final int  POINTER_ID_0                   = 0;

   public static final int  POINTER_ID_1                   = 1;

   public static final int  POINTER_ID_2                   = 2;

   public static final int  POINTER_ID_3                   = 3;

   public static final int  POINTER_ID_4                   = 4;

   public static final int  POINTER_ID_5                   = 5;

   public static final int  POINTER_ID_6                   = 6;

   public static final int  POINTER_ID_7                   = 7;

   public static final int  POINTER_ID_8                   = 8;

   public static final int  POINTER_ID_9                   = 9;

   public static final int  POINTER_ID_Z10                 = 10;

   /**
    * How to distinguish between Pointer Right in PC and Pointers in Android ?
    * <br>
    * Only the first pointer is identical in semantics.
    * In Android each pointer has only one MOD
    * <br>
    * On the PC, one single pointer has 3 different buttons, and the wheel.
    * <br>
    * They are semantically different.
    * Pointer modders
    */
   public static final int  PBUTTON_0_DEFAULT              = 0;

   public static final int  PBUTTON_1_RIGHT                = 1;

   public static final int  PBUTTON_2_MIDDLE               = 2;

   /**
    * Left Mouse/ 1st finger
    */
   public static final int  POINTER_0_PRIMARY              = 0;

   /**
    * Right mouse, 2nd finger
    */
   public static final int  POINTER_1_SECONDARY            = 1;

   /**
    * Middle mouse, 3rd finger
    */
   public static final int  POINTER_2_TERTIARY             = 2;

   /**
    * -100?
    */
   public static final int  PBUTTON_3_WHEEL_UP             = 10;

   /**
    * -101?
    */
   public static final int  PBUTTON_4_WHEEL_DOWN           = 11;

   /**
    * Any timing will be accepted
    */
   public static final int  TIMING_0_NONE                  = 0;

   public static final int  TIMING_1_SLOW                  = 1;

   public static final int  TIMING_2_NORMAL                = 2;

   public static final int  TIMING_3_FAST                  = 3;

   /**
    * Wildcards are key functions referencing a set of keys.
    * <br>
    * Incoming keys will.
    * This list defines usual wildcards
    */
   public static final int  WILDCARD_                      = 0;

   /**
    * 
    */
   public static final int  WILDCARD_255_ANY_POINTER       = 255;

   /**
    * 
    */
   public static final int  WILDCARD_000_ANY               = 60000;

   public static final int  WILDCARD_001_NUMBERS           = 60001;

   /**
    * Any letter key. This will depend on the keyboard layout! lol
    * Russian/Latin
    */
   public static final int  WILDCARD_002_LETTERS           = 60002;

   /**
    * The previous wildcard refers to the key same device ? or same user?
    * Do moves invalidate?
    *     * Key value that refers to the previous key in the serie of event relative to device context.
    * <li> When the previous key is a wildcard.
    * 
    * trigger valid when previous trigger
    * fulfill a condition such as all key released
    */
   public static final int  WILDCARD_003_PREVIOUS          = 60003;

   public static final int  WILDCARD_004_PRESSED           = 60004;

   public static final int  WILDCARD_005_PRESSED_CURRENTLY = 60005;

   /**
    * TODO are you sure?
    */
   public static final int  UNKNOWN_NO_KEY                 = -1;

   /**
    * Is it working?
    */
   public static final int  KEY_WINDOWS                    = 524;

}
