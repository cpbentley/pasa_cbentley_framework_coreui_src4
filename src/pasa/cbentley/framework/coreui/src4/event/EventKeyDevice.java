package pasa.cbentley.framework.coreui.src4.event;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.framework.coreui.src4.ctx.CoreUiCtx;
import pasa.cbentley.framework.coreui.src4.tech.IInput;

/**
 * Activated when Press/Release opposite events.
 * 
 * TODO change with flags for any combination of checks
 * @author Charles Bentley
 *
 */
public class EventKeyDevice extends EventKey {

   private DeviceEvent de;

   private int         modeType;

   public static String getStringModeType(int modeType) {
      switch (modeType) {
         case MODE_TYPE_0_INVERSE:
            return "Inverse";
         case MODE_TYPE_1_SAME:
            return "Same";
         case MODE_TYPE_2_ANY:
            return "Any";
         default:
            return "UnknownModeType" + modeType;
      }
   }

   public static final int DEVICE_FLAG_1_SAME  = 1;

   public static final int DEVICE_FLAG_2_ANY   = 1;

   /**
    * Flag telling button must be the same
    * <br>
    * Group of buttons
    */
   public static final int BUTTON_FLAG_1_SAME  = 1;

   public static final int BUTTON_FLAG_2_ANY   = 1;

   /**
    * Activates when same button and same mode
    */
   public static final int MODE_TYPE_1_SAME    = 1;

   /**
    * Activates when same button but inverse mode. Press <> Release
    */
   public static final int MODE_TYPE_0_INVERSE = 0;

   /**
    * Any mode for same device/id/user/button
    */
   public static final int MODE_TYPE_2_ANY     = 2;

   /**
    * Usually Type and ID goes together. How to create an EventKey for a group
    * of device IDs?
    */
   public static final int ID_TYPE_1_SAME      = 2;

   public static final int ID_TYPE_2_ANY       = 2;

   /**
    * Type
    * <li> {@link EventKey#KEY_TYPE_0_FIRE}
    * <li> {@link EventKey#KEY_TYPE_1_CANCEL}
    * <li> {@link EventKey#KEY_TYPE_2_FIRE_AND_CANCEL}
    * <li> {@link EventKey#KEY_TYPE_3_MUTE_FIRE}
    * <li> {@link EventKey#KEY_TYPE_4_ACTIVATE_FIRE}
    * <br>
    * <br>
    * By default uses the {@link EventKeyDevice#MODE_TYPE_0_INVERSE}
    * @param type 
    * @param de
    */
   public EventKeyDevice(CoreUiCtx cac, int type, DeviceEvent de) {
      this(cac, type, de, MODE_TYPE_0_INVERSE);
   }

   /**
    * Type
    * <li> {@link EventKey#KEY_TYPE_0_FIRE}
    * <li> {@link EventKey#KEY_TYPE_1_CANCEL}
    * <li> {@link EventKey#KEY_TYPE_2_FIRE_AND_CANCEL}
    * <li> {@link EventKey#KEY_TYPE_3_MUTE_FIRE}
    * <li> {@link EventKey#KEY_TYPE_4_ACTIVATE_FIRE}
    * <br>
    * <br>
    * Mode Type
    * <li> {@link EventKeyDevice#MODE_TYPE_0_INVERSE} if de is pressed, matches with release
    * <li> {@link EventKeyDevice#MODE_TYPE_1_SAME} if de is press, matches with press
    * <li> {@link EventKeyDevice#MODE_TYPE_2_ANY} any mode will match. press or release
    * 
    * @param type
    * @param de
    * @param modeType
    */
   public EventKeyDevice(CoreUiCtx cac, int type, DeviceEvent de, int modeType) {
      super(cac, type);
      this.de = de;
      this.modeType = modeType;
   }

   public DeviceEvent getDeviceEvent() {
      return de;
   }

   public boolean isEquals(EventKey ek) {
      if (ek instanceof EventKeyDevice) {
         EventKeyDevice ekd = (EventKeyDevice) ek;
         return de.isMatchTypeIDButtonMode(ekd.de);
      }
      return false;
   }

   public String getUserLineString() {
      return de.getUserLineString();
   }

   /**
    * True when matched Released/Pressed.
    * <br>
    */
   public boolean isKeyActivated(BEvent be) {
      if (be.getType() == IInput.TYPE_1_DEVICE) {
         DeviceEvent d = (DeviceEvent) be;
         if (d.getDeviceType() == de.getDeviceType() && d.getDeviceID() == de.getDeviceID() && d.getDeviceButton() == de.getDeviceButton()) {
            if (modeType == MODE_TYPE_0_INVERSE) {
               if (de.getDeviceMode() == IInput.MOD_0_PRESSED) {
                  return d.getDeviceMode() == IInput.MOD_1_RELEASED;
               } else if (de.getDeviceMode() == IInput.MOD_1_RELEASED) {
                  return d.getDeviceMode() == IInput.MOD_0_PRESSED;
               }
            } else if (modeType == MODE_TYPE_1_SAME) {
               return d.getDeviceMode() == de.getDeviceMode();
            } else {
               //mode type is alway true
               return true;
            }
         }
      }
      return false;
   }

   public boolean isKeyMatched(BEvent be) {
      if (be.getType() == IInput.TYPE_1_DEVICE) {
         DeviceEvent d = (DeviceEvent) be;
         if (d.getDeviceType() == de.getDeviceType() && d.getDeviceID() == de.getDeviceID() && d.getDeviceButton() == de.getDeviceButton()) {
            return de.getDeviceMode() == d.getDeviceMode();
         }
      }
      return false;
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, "EventKeyDevice");
      dc.appendVarWithSpace(" ", toStringType(keyType));
      dc.appendVarWithSpace("Mode", getStringModeType(modeType));
      dc.nlLvl(de);
      super.toString(dc.sup1Line());
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "EventKeyDevice");
      dc.appendVarWithSpace(" ", toStringType(keyType));
      dc.appendVarWithSpace("Mode", getStringModeType(modeType));
      dc.oneLine(de);
   }
   //#enddebug
}
