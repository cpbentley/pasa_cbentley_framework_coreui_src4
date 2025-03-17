package pasa.cbentley.framework.core.ui.src4.event;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.framework.core.ui.src4.ctx.CoreUiCtx;
import pasa.cbentley.framework.core.ui.src4.ctx.ToStringStaticCoreUi;
import pasa.cbentley.framework.core.ui.src4.tech.ITechInput;

/**
 * Actives when {@link DeviceEvent} is part of incoming group
 * @author Charles Bentley
 *
 */
public class EventKeyInGroup extends EventKey implements IStringable {

   private DeviceEvent de;

   private boolean     isSimul;

   public EventKeyInGroup(CoreUiCtx cac, int keyType, DeviceEvent de) {
      super(cac, keyType);
      this.de = de;
   }

   public void setSimul(boolean b) {
      isSimul = b;
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
      if (be.getType() == ITechInput.TYPE_7_GROUP) {
         DeviceEventGroup deg = (DeviceEventGroup) be;
         if (isSimul && !deg.isSimulGroup()) {
            //we only accept simul groups
            return false;
         }
         if (deg.isMatchNoMode(de)) {
            return true;
         }
      }
      return false;
   }

   public String getUserLineString() {
      String simul = "";
      if (isSimul) {
         simul = " simul";
      }
      return de.getUserLineString() + " AnyMode in " + simul + " Group" + ToStringStaticCoreUi.toStringKeyEventUserLine(patternAction);
   }

   public boolean isEquals(EventKey ek) {
      if (ek instanceof EventKeyInGroup) {
         return true;
      }
      return false;
   }

   //#mdebug
   public void toString(Dctx sb) {
      sb.root(this, "EventKeyInGroup");
      sb.appendVarWithSpace("isSimul", isSimul);
      super.toString(sb.sup1Line());
      sb.nlLvl(de);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "EventKeyInGroup");
      dc.oneLine(de);
   }
   //#enddebug
}
