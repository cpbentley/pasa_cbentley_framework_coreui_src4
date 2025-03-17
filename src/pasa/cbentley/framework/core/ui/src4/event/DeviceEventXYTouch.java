package pasa.cbentley.framework.core.ui.src4.event;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.framework.core.ui.src4.ctx.CoreUiCtx;
import pasa.cbentley.framework.core.ui.src4.ctx.ToStringStaticCoreUi;
import pasa.cbentley.framework.core.ui.src4.tech.ITechInput;

/**
 * Events for touches on a screen.
 * <br>
 * <li>DeviceType will be {@link ITechInput#DEVICE_4_SCREEN}
 * <li> DeviceID is the screen #
 * <li> Mode is press/moved/released
 * <li> deviceButton is the point ID
 * <br>
 * <br>
 * A stylus device with Buttons.... how do you differentiate from a finger that moves?
 * <br>
 * A physical button generates a {@link DeviceEvent}.
 * The screen registers a Keyboard device as well.
 * <br>
 * 2 screens may create touch points on the same drawing surface.
 * <br>
 * A wacom tablet could translate the touch event to the main screen coordinates.
 * <br>
 * TODO how does the user decides if a device should work in paralell. ie use the same
 * deviceID.
 * @author Charles Bentley
 *
 */
public class DeviceEventXYTouch extends DeviceEventXY {

   private float size;

   /**
    * As a trigger, pressure is low or high.
    * <br>
    * For continuous pressure for drawing application
    */
   private float pressure;

   public float getSize() {
      return size;
   }

   public float getPressure() {
      return pressure;
   }

   public DeviceEventXYTouch(CoreUiCtx fc, int deviceID, int mode, int deviceButton, int x, int y) {
      this(fc, deviceID, mode, deviceButton, x, y, 0, 0);
   }

   public DeviceEventXYTouch(CoreUiCtx fc, int deviceID, int mode, int deviceButton, int x, int y, float size, float pressure) {
      super(fc, ITechInput.DEVICE_4_SCREEN, deviceID, mode, deviceButton, x, y);
      this.size = size;
      this.pressure = pressure;
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, "DeviceEventXYTouch");
      dc.appendVarWithSpace("size", size);
      dc.appendVarWithSpace("pressure", pressure);
      super.toString(dc.sup());
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "DeviceEventXYTouch");
      dc.appendVarWithSpace("size", size);
      dc.appendVarWithSpace("pressure", pressure);
      super.toString1Line(dc.sup1Line());
   }
   //#enddebug

   public String getUserLineString() {
      String str = "";
      if (deviceType == ITechInput.DEVICE_4_SCREEN) {
         str = "Finger#" + +deviceButton + " " + ToStringStaticCoreUi.toStringMod(mode);
      } else {
         return super.getUserLineString();
      }
      str += " " + getX() + "," + getY();
      return str;
   }
}
