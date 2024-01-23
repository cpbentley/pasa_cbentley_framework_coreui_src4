package pasa.cbentley.framework.coreui.src4.event;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.framework.coreui.src4.ctx.CoreUiCtx;
import pasa.cbentley.framework.coreui.src4.ctx.ToStringStaticCoreUi;
import pasa.cbentley.framework.coreui.src4.tech.ITechCodes;
import pasa.cbentley.framework.coreui.src4.tech.IInput;

/**
 * Device event with 2 axis values.
 * <br>
 * {@link DeviceEvent#getDeviceButton()} identifies the .
 * A device may have 2 wheels or 2 pointers.
 * <br>
 * Mode allows a wheel to be pressed and released, 
 * <br>
 * @author Charles Bentley
 *
 */
public class DeviceEventXY extends DeviceEvent {

   private int x;

   private int y;

   /**
    * 
    * @param deviceType Could be mouse, Wheel rotation, 
    * @param deviceID
    * @param mode
    * @param deviceButton
    * @param x
    * @param y
    */
   public DeviceEventXY(CoreUiCtx fc, int deviceType, int deviceID, int mode, int deviceButton, int x, int y) {
      super(fc, deviceType, deviceID, mode, deviceButton);
      this.x = x;
      this.y = y;
      setFlag(DEVICE_FLAG_10_POINTER, true);
   }

   public int getX() {
      return x;
   }

   public int getY() {
      return y;
   }

   public void updateY(int y) {
      this.y = y;
   }

   public void updateX(int x) {
      this.x = x;
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, "DeviceEventXY");
      dc.append(" " + x + "," + y);
      super.toString(dc.sup());
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "DeviceEventXY");
      dc.append(" " + x + "," + y);
      super.toString1Line(dc.sup1Line());
   }
   //#enddebug

   public String getUserLineString() {
      String str = "";
      if (deviceType == IInput.DEVICE_1_MOUSE) {
         //depends on the mouse
         if (mode == IInput.MOD_3_MOVED) {
            str = "Mouse " + deviceID + " Moved";
         } else if (mode == IInput.MOD_5_WHEELED) {
            if(y == ITechCodes.PBUTTON_3_WHEEL_UP) {
               str = "Mouse wheeled up "+ x;
            } else {
               str = "Mouse wheeled down "+ x;
            }
         } else {
            str = "Mouse " + ToStringStaticCoreUi.getStringButtonShort(deviceButton) + " " + ToStringStaticCoreUi.getStringMod(mode);
         }
      } else if (deviceType == IInput.DEVICE_3_FINGER) {
         str = "Finger#" + +deviceButton + " " + ToStringStaticCoreUi.getStringMod(mode);
      }
      str += " " + getX() + "," + getY();
      return str;
   }
}
