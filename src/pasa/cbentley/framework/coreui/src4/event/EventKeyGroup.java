package pasa.cbentley.framework.coreui.src4.event;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.framework.coreui.src4.ctx.CoreUiCtx;
import pasa.cbentley.framework.coreui.src4.ctx.ToStringStaticCoreUi;

/**
 * Pattern activates when incoming {@link BEvent} cancels a {@link DeviceEventGroup}.
 * <br>
 * In practice when  {@link DeviceEventGroup#isCancelling(DeviceEvent)} returns true.
 * 
 * @author Charles Bentley
 *
 */
public class EventKeyGroup extends EventKey {

   private DeviceEventGroup deg;

   /**
    * Activated 
    */
   private boolean          isCancel;

   public EventKeyGroup(CoreUiCtx cac,int type, DeviceEventGroup deg) {
      super(cac,type);
      this.deg = deg;
   }

   public DeviceEventGroup getDeg() {
      return deg;
   }

   public boolean isEquals(EventKey ek) {
      if (ek instanceof EventKeyGroup) {
         EventKeyGroup ekd = (EventKeyGroup) ek;
         return deg.isMatch(ekd.deg);
      }
      return false;
   }

   public String getUserLineString() {
      return deg.getUserLineString() + " AnyMode Group" + ToStringStaticCoreUi.toStringKeyEventUserLine(patternAction);
   }

   public boolean isKeyActivated(BEvent be) {
      if (be instanceof DeviceEvent) {
         return deg.isCancelling((DeviceEvent) be);
      }
      return false;
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, "EventKeyGroup");
      super.toString(dc.sup());
      dc.nlLvl(deg);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "EventKeyGroup");
      super.toString1Line(dc.sup1Line());
      dc.appendVarWithSpace("", deg.toString1Line());
   }
   //#enddebug

}
