package pasa.cbentley.framework.coreui.src4.event;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.framework.coreui.src4.ctx.CoreUiCtx;
import pasa.cbentley.framework.coreui.src4.tech.IInput;

/**
 * Actives when any press
 * @author Charles Bentley
 *
 */
public class EventKeyAnyPress extends EventKey implements IStringable {

   public EventKeyAnyPress(CoreUiCtx cac, int keyType) {
      super(cac, keyType);
   }

   /**
    * Any press from the {@link DeviceEvent} type, id and user.
    * @param keyType
    * @param de
    */
   public EventKeyAnyPress(CoreUiCtx cac, int keyType, DeviceEvent de) {
      super(cac, keyType);
   }

   /**
    * Returns true when the event activates the key. (cancel/fire)
    * <br>
    * <li>For example, a LeftMousePress key is activated by a LeftMouseRelease
    * <li> A=B press is activated by ? any key releases
    * @param be
    * @return
    */
   public boolean isKeyActivated(BEvent be) {
      if (be.getType() == IInput.TYPE_1_DEVICE) {
         DeviceEvent de = (DeviceEvent) be;
         if (de.getDeviceMode() == IInput.MOD_0_PRESSED) {
            return true;
         }
      }
      return false;
   }

   public String getUserLineString() {
      // TODO Auto-generated method stub
      return "AnyPress " + toStringUserLine(keyType);
   }

   public boolean isEquals(EventKey ek) {
      if (ek instanceof EventKeyAnyPress) {
         return true;
      }
      return false;
   }

   //#mdebug
   public void toString(Dctx sb) {
      sb.root(this, "EventKeyPress");
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "EventKeyPress");
   }
   //#enddebug


}
