package pasa.cbentley.framework.core.ui.src4.event;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.framework.core.ui.src4.ctx.CoreUiCtx;

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
public class DeviceEventFloat extends DeviceEvent {

   private float value;

   /**
    * 
    * @param deviceType Could be mouse, Wheel rotation, 
    * @param deviceID
    * @param mode
    * @param deviceButton
    * @param x
    * @param y
    */
   public DeviceEventFloat(CoreUiCtx fc, int deviceType, int deviceID, int deviceButton, float value) {
      super(fc, deviceType, deviceID, MOD_6_FLOAT, deviceButton);
      this.value = value;
   }

   public float getData() {
      return value;
   }

   public String getUserLineString() {
      String str = super.getUserLineString();
      str = str + " value=" + uc.getStrU().prettyFloat(value, 4);
      return str;
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, DeviceEventFloat.class, 51);
      toStringPrivate(dc);
      super.toString(dc.sup());
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, DeviceEventFloat.class, 51);
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   private void toStringPrivate(Dctx dc) {
      dc.appendVarPretty(" data", value, 4);
   }
   //#enddebug

}
