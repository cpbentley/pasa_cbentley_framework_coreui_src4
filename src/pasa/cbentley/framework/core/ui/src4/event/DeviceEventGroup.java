package pasa.cbentley.framework.core.ui.src4.event;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.framework.core.ui.src4.ctx.CoreUiCtx;
import pasa.cbentley.framework.core.ui.src4.tech.ITechInput;

/**
 * Event generated when a group of {@link DeviceEvent} match a pattern.
 * <br>
 * <li> Keys simultaneously pressed
 * <li> Keys simultaneously released
 * <li> Keys pressed in serie
 * <li> Mixed pressed and released.. Two keys pressed and one released together
 * <br>
 * <br>
 * Low level version of a trigger. Alls keys have the same importance.
 * <br>
 * What is the difference between Ctrl+E& and (Ctrl+E+)&
 * <li>Ctrl+E& is E repeated with Ctrl press or released. Similar to Up&Down& 
 * <li>(ctrl+e+)& repeat stops as soon as one key is released 
 * <li>(ctrl+=e+)& 
 * <br>
 * @author Charles Bentley
 *
 */
public class DeviceEventGroup extends BEvent implements IStringable {

   public static final int  FLAG_10_SIMUL        = 1 << 9;

   /**
    * Mix of release and press
    */
   public static final int  FLAG_11_MIXED        = 1 << 9;

   /**
    * Disabled when any press or release is activated
    * from any device the group
    */
   private static final int FLAG_12_STRICT_GROUP = 0;

   /**
    * The DeviceEvent that started the repeat.
    * <br>
    * Might be a {@link DeviceEventXY} when a pointer button is pressed
    */
   private DeviceEvent[]    deviceEvents;

   public DeviceEventGroup(CoreUiCtx fc, DeviceEvent[] de) {
      super(fc);
      deviceEvents = de;
      type = TYPE_7_GROUP;
   }

   public String getUserLineString() {
      String str = "";
      if (isSimulGroup()) {
         str += "Simul ";
      }
      DeviceEvent[] ar = getEvents();
      for (int i = 0; i < ar.length; i++) {
         str += ar[i].getUserStringButton();
         if (ar[i].getDeviceMode() == MOD_0_PRESSED) {
            str += "+";
         } else {
            str += "-";
         }
      }
      return str;
   }

   public int getNumEvents() {
      return deviceEvents.length;
   }

   public DeviceEvent[] getEvents() {
      return deviceEvents;
   }

   /**
    * Method used to check if {@link DeviceEvent} invalidates the {@link DeviceEventGroup}.
    * <br>
    * @param de
    * @return
    */
   public boolean hasOppositeModeMatch(DeviceEvent de) {
      for (int i = 0; i < deviceEvents.length; i++) {
         DeviceEvent deviceEvent = deviceEvents[i];
         if (de.getDeviceButton() == deviceEvent.getDeviceButton() && de.getDeviceType() == de.getDeviceType()) {
            if (de.getDeviceID() == deviceEvent.getDeviceID()) {
               if (de.getDeviceMode() == ITechInput.MOD_0_PRESSED) {
                  if (deviceEvent.getDeviceMode() == ITechInput.MOD_1_RELEASED) {
                     return true;
                  }
               } else if (de.getDeviceMode() == ITechInput.MOD_1_RELEASED) {
                  if (deviceEvent.getDeviceMode() == ITechInput.MOD_0_PRESSED) {
                     return true;
                  }
               }
            }
         }
      }
      return false;
   }

   /**
    * At least one press in the group
    * @return
    */
   public boolean hasPressed() {
      for (int i = 0; i < deviceEvents.length; i++) {
         if (deviceEvents[i].getDeviceMode() == MOD_0_PRESSED) {
            return true;
         }
      }
      return false;
   }

   public boolean hasPressedOnly() {
      for (int i = 0; i < deviceEvents.length; i++) {
         if (deviceEvents[i].getDeviceMode() != MOD_0_PRESSED) {
            return false;
         }
      }
      return true;
   }

   public boolean isCancelling(DeviceEvent de) {
      if (isStrict()) {
         for (int i = 0; i < deviceEvents.length; i++) {
            DeviceEvent deviceEvent = deviceEvents[i];
            if (deviceEvent.getDeviceType() == de.getDeviceType()) {
               if (deviceEvent.getDeviceID() == de.getDeviceID()) {
                  return true;
               }
            }
         }
         return false;
      } else {
         return hasOppositeModeMatch(de);
      }
   }

   /**
    * At least one {@link DeviceEvent} in the group matches {@link DeviceEvent#isMatchTypeIDButtonMode(DeviceEvent)}
    * @param de
    * @return
    */
   public boolean isMatch(DeviceEvent de) {
      for (int i = 0; i < deviceEvents.length; i++) {
         if (deviceEvents[i].isMatchTypeIDButtonMode(de)) {
            return true;
         }
      }
      return false;
   }

   /**
    * True when all {@link DeviceEvent} true as {@link DeviceEvent#isMatchTypeIDButtonMode(DeviceEvent)}
    * <br>
    * @param de
    * @return
    */
   public boolean isMatch(DeviceEventGroup de) {
      if (deviceEvents.length == de.deviceEvents.length) {
         for (int i = 0; i < deviceEvents.length; i++) {
            DeviceEvent dev = deviceEvents[i];
            //find it in group
            boolean f = de.isMatch(dev);
            if (!f) {
               return false;
            }
         }
         return true;
      }
      return false;
   }

   public boolean isMatchNoMode(DeviceEvent de) {
      for (int i = 0; i < deviceEvents.length; i++) {
         if (deviceEvents[i].isMatchTypeIDButton(de)) {
            return true;
         }
      }
      return false;
   }

   /**
    * 
    * @return
    */
   public boolean isSimulGroup() {
      return hasFlag(FLAG_10_SIMUL);
   }

   /**
    * 
    * @return
    */
   public boolean isStrict() {
      return hasFlag(FLAG_12_STRICT_GROUP);
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, "DeviceEventGroup");
      dc.appendWithSpace(getUserLineString());
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "DeviceEventGroup");
      dc.appendWithSpace(getUserLineString());
   }
   //#enddebug

}
