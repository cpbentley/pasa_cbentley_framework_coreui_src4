package pasa.cbentley.framework.core.ui.src4.event;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.framework.core.ui.src4.ctx.CoreUiCtx;
import pasa.cbentley.framework.core.ui.src4.ctx.ToStringStaticCoreUi;
import pasa.cbentley.framework.core.ui.src4.tech.ITechInput;

/**
 * Describes a range of events based on a {@link DeviceEvent}
 *  
 * Event that activated when Press/Release opposite events.
 * 
 * TODO change with flags for any combination of checks
 * @author Charles Bentley
 *
 */
public class EventKeyDevice extends EventKey {

   private DeviceEvent myDeviceEvent;

   private int         modeType;

   /**
    * Type
    * <li> {@link ITechEventKey#KEY_TYPE_0_FIRE}
    * <li> {@link ITechEventKey#KEY_TYPE_1_CANCEL}
    * <li> {@link ITechEventKey#KEY_TYPE_2_FIRE_AND_CANCEL}
    * <li> {@link ITechEventKey#KEY_TYPE_3_MUTE_FIRE}
    * <li> {@link ITechEventKey#KEY_TYPE_4_ACTIVATE_FIRE}
    * <br>
    * <br>
    * By default uses the {@link ITechEventKey#EVENT_KEY_DEVICE_MODE_TYPE_0_INVERSE}
    * @param type 
    * @param de
    */
   public EventKeyDevice(CoreUiCtx cac, int type, DeviceEvent de) {
      this(cac, type, de, ITechEventKey.EVENT_KEY_DEVICE_MODE_TYPE_0_INVERSE);
   }

   /**
    * Type
    * <li> {@link ITechEventKey#KEY_TYPE_0_FIRE}
    * <li> {@link ITechEventKey#KEY_TYPE_1_CANCEL}
    * <li> {@link ITechEventKey#KEY_TYPE_2_FIRE_AND_CANCEL}
    * <li> {@link ITechEventKey#KEY_TYPE_3_MUTE_FIRE}
    * <li> {@link ITechEventKey#KEY_TYPE_4_ACTIVATE_FIRE}
    * <br>
    * <br>
    * Mode Type
    * <li> {@link ITechEventKey#EVENT_KEY_DEVICE_MODE_TYPE_0_INVERSE} if de is pressed, matches with release
    * <li> {@link ITechEventKey#EVENT_KEY_DEVICE_MODE_TYPE_1_SAME} if de is press, matches with press
    * <li> {@link ITechEventKey#EVENT_KEY_DEVICE_MODE_TYPE_2_ANY} any mode will match. press or release
    * 
    * @param type
    * @param de
    * @param modeType activation pattern mode the for mod
    */
   public EventKeyDevice(CoreUiCtx cac, int type, DeviceEvent de, int modeType) {
      super(cac, type);
      this.myDeviceEvent = de;
      this.modeType = modeType;
   }

   public DeviceEvent getDeviceEvent() {
      return myDeviceEvent;
   }

   public String getUserLineString() {
      return myDeviceEvent.getUserLineString();
   }

   /**
    * 
    */
   public boolean isEquals(EventKey ek) {
      if (ek instanceof EventKeyDevice) {
         EventKeyDevice ekd = (EventKeyDevice) ek;
         return myDeviceEvent.isMatchTypeIDButtonMode(ekd.myDeviceEvent);
      }
      return false;
   }

   /**
    * True when matched Released/Pressed.
    * <br>
    */
   public boolean isKeyActivated(BEvent be) {
      if (be.getType() == ITechInput.TYPE_1_DEVICE) {
         DeviceEvent deviceEventInput = (DeviceEvent) be;
         if (deviceEventInput.getDeviceType() == myDeviceEvent.getDeviceType() && deviceEventInput.getDeviceID() == myDeviceEvent.getDeviceID() && deviceEventInput.getDeviceButton() == myDeviceEvent.getDeviceButton()) {
            if (modeType == ITechEventKey.EVENT_KEY_DEVICE_MODE_TYPE_0_INVERSE) {
               if (myDeviceEvent.getDeviceMode() == ITechInput.MOD_0_PRESSED) {
                  return deviceEventInput.getDeviceMode() == ITechInput.MOD_1_RELEASED;
               } else if (myDeviceEvent.getDeviceMode() == ITechInput.MOD_1_RELEASED) {
                  return deviceEventInput.getDeviceMode() == ITechInput.MOD_0_PRESSED;
               }
            } else if (modeType == ITechEventKey.EVENT_KEY_DEVICE_MODE_TYPE_1_SAME) {
               return deviceEventInput.getDeviceMode() == myDeviceEvent.getDeviceMode();
            } else {
               //mode type is alway true
               return true;
            }
         }
      }
      return false;
   }

   public boolean isKeyMatched(BEvent be) {
      if (be.getType() == ITechInput.TYPE_1_DEVICE) {
         DeviceEvent d = (DeviceEvent) be;
         if (d.getDeviceType() == myDeviceEvent.getDeviceType() && d.getDeviceID() == myDeviceEvent.getDeviceID() && d.getDeviceButton() == myDeviceEvent.getDeviceButton()) {
            return myDeviceEvent.getDeviceMode() == d.getDeviceMode();
         }
      }
      return false;
   }

   
   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, EventKeyDevice.class, "@line5");
      toStringPrivate(dc);
      super.toString(dc.sup());
      dc.nlLvl(myDeviceEvent);
   }

   private void toStringPrivate(Dctx dc) {
      dc.appendVarWithSpace("Mode", ToStringStaticCoreUi.toStringEventKeyModeType(modeType));
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, EventKeyDevice.class);
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
      dc.oneLine(myDeviceEvent);
   }

   //#enddebug
   

}
