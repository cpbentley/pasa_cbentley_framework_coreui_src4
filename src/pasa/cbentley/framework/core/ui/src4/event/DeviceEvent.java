package pasa.cbentley.framework.core.ui.src4.event;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.framework.core.ui.src4.ctx.CoreUiCtx;
import pasa.cbentley.framework.core.ui.src4.ctx.ToStringStaticCoreUi;
import pasa.cbentley.framework.core.ui.src4.interfaces.IExternalDevice;
import pasa.cbentley.framework.core.ui.src4.tech.IInput;

/**
 * Input Event from an Input Device.
 * <br>
 * One event from a device with explicit mode of pressed/release.
 * <br>
 * The turned mode is for wheels. They include a direction and an amplitude value. Value is relative
 * Trigger event unit.
 * <br>
 * <li> code (key,button identifier)
 * <li> mode (Pressed,Released,Type)
 * <li> type (device)
 * @author Charles-Philip Bentley
 *
 */
public class DeviceEvent extends BEvent {

   public static final int DEVICE_FLAG_10_POINTER = 1 << 9;

   /**
    * Some devices have many buttons, others like fingers only have one.
    * <br>
    */
   protected int           deviceButton;

   /**
    * Several devices of the same type will differentiate with a deviceID
    * ID from a.
    * <br>
    * This is set inside a Host class. Fingers, Mice, Gamepads, Keyboards.
    */
   protected int           deviceID;

   /**
    * Any device type may have wheels
    * {@link IInput#DEVICE_0_KEYBOARD}
    * {@link IInput#DEVICE_1_MOUSE}
    * {@link IInput#DEVICE_2_GAMEPAD}
    * {@link IInput#DEVICE_3_FINGER}
    * {@link IInput#DEVICE_4_OTHER}
    */
   protected int           deviceType;

   /**
    * The mode 
    * pressed or released, wheel
    * <li> {@link IInput#MOD_0_PRESSED} Device Button pressed
    * <li> {@link IInput#MOD_1_RELEASED} Device Button
    * <li> {@link IInput#MOD_5_WHEELED} Device Wheel Turned
    */
   protected int           mode;

   public DeviceEvent(CoreUiCtx fc, int deviceType, int deviceID, int mode, int deviceButton) {
      super(fc);
      this.type = IInput.TYPE_1_DEVICE;
      this.deviceType = deviceType;
      this.mode = mode;
      this.deviceID = deviceID;
      this.deviceButton = deviceButton;
   }

   public int getDeviceButton() {
      return deviceButton;
   }

   /**
    * Differentiate devices within a device class type and user space.
    * <li> {@link IInput#DEVICE_0_KEYBOARD}. Each keyboard will be given an ID
    * <li> {@link IInput#DEVICE_1_MOUSE} Each mouse within the User Space
    * <li> {@link IInput#DEVICE_2_GAMEPAD}. Each gamepad will be given an ID
    * <li> {@link IInput#DEVICE_4_SCREEN} what if 2 screens give different
    * fingers? Don't we want different IDs?
    * @return
    */
   public int getDeviceID() {
      return deviceID;
   }

   /**
    * <li> {@link IInput#MOD_0_PRESSED} Device Button pressed
    * <li> {@link IInput#MOD_1_RELEASED} Device Button released
    * <li> {@link IInput#MOD_3_MOVED} Device Moved
    * <li> {@link IInput#MOD_4_SENSED} Device Sensed something
    * <li> {@link IInput#MOD_5_WHEELED} Device Wheel Turned
    * @return
    */
   public int getDeviceMode() {
      return mode;
   }

   public int getDeviceType() {
      return deviceType;
   }

   /**
    * Return a canonical Int representation of the Trigger unit for fast pattern matching.
    * <br>
    * When a match is true, further matching can be done if necessary with other trigger unit
    * non canonical values.
    * @return
    */
   public int getIntHeader() {
      return 0;
   }

   public String getUserLineString() {
      String str = "";
      if (deviceType == IInput.DEVICE_0_KEYBOARD) {
         str = "Key";
         if (deviceID != 0) {
            str += " " + deviceID;
         }
         str += " " + ToStringStaticCoreUi.toStringKey(deviceButton);
      } else if (deviceType == IInput.DEVICE_1_MOUSE) {
         //depends on the mouse
      } else if (deviceType == IInput.DEVICE_3_FINGER) {
         return "Finger " + deviceButton;
      } else if (deviceType == IInput.DEVICE_2_GAMEPAD) {
         //gamepad is always in def
         if (getParamO1() != null) {
            IExternalDevice ed = (IExternalDevice) this.getParamO1();
            String padName = ed.getName() + " #" + deviceID;
            String buttonName = ed.getNameButton(deviceButton);
            return padName + " " + buttonName + " " + ToStringStaticCoreUi.toStringMod(mode);
         }
         return "GamePad #" + deviceID + " " + deviceButton + " " + ToStringStaticCoreUi.toStringMod(mode);
      }
      return str = str + " " + ToStringStaticCoreUi.toStringMod(mode);
   }

   /**
    * The user readable string representing this {@link DeviceEvent}.
    * <br>
    * TODO localize it
    * @return
    */
   public String getUserStringButton() {
      if (deviceType == IInput.DEVICE_0_KEYBOARD) {
         return ToStringStaticCoreUi.toStringKey(deviceButton);
      } else if (deviceType == IInput.DEVICE_1_MOUSE) {
         //depends on the mouse
         return ToStringStaticCoreUi.toStringButtonShort(deviceButton);
      } else if (deviceType == IInput.DEVICE_3_FINGER) {
         return "Finger#" + deviceButton;
      } else {
         return "" + deviceButton;
      }
   }

   /**
    * No matching on mode.
    * @param de
    * @return
    */
   public boolean isMatchTypeIDButton(DeviceEvent de) {
      return deviceButton == de.deviceButton && deviceID == de.deviceID && deviceType == de.deviceType;
   }

   /**
    * Check match on button,deviceid, type and mode
    * @param de
    * @return
    */
   public boolean isMatchTypeIDButtonMode(DeviceEvent de) {
      return deviceButton == de.deviceButton && deviceID == de.deviceID && deviceType == de.deviceType && mode == de.mode;
   }

   public boolean isModeEqual(DeviceEvent de) {
      return mode == de.mode;
   }

   public boolean isPointer() {
      return hasFlag(DEVICE_FLAG_10_POINTER);
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, "DeviceEvent");
      dc.appendVarWithSpace("UserLine", getUserLineString());
      dc.nl();
      dc.appendVar("DeviceType", ToStringStaticCoreUi.toStringDeviceType(deviceType));
      dc.appendVarWithSpace("DeviceID", deviceID);
      dc.appendVarWithSpace("Mode", ToStringStaticCoreUi.toStringMod(mode));
      dc.appendVarWithSpace("Button", deviceButton);
      super.toString(dc.sup());
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "DeviceEvent");
      dc.appendVarWithSpace("UserLine", getUserLineString());
      super.toString1Line(dc.sup1Line());
   }
   //#enddebug

   /**
    * Called when the Button was not able to 
    * @param v
    */
   public void updateButton(int v) {
      this.deviceButton = v;
   }
}