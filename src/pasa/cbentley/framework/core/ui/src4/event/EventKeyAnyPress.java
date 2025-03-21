package pasa.cbentley.framework.core.ui.src4.event;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.framework.core.ui.src4.ctx.CoreUiCtx;
import pasa.cbentley.framework.core.ui.src4.ctx.ToStringStaticCoreUi;
import pasa.cbentley.framework.core.ui.src4.tech.ITechInput;

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
      if (be.getType() == ITechInput.TYPE_1_DEVICE) {
         DeviceEvent de = (DeviceEvent) be;
         if (de.getDeviceMode() == ITechInput.MOD_0_PRESSED) {
            return true;
         }
      }
      return false;
   }

   public String getUserLineString() {
      return "AnyPress " + ToStringStaticCoreUi.toStringKeyEventUserLine(patternAction);
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
