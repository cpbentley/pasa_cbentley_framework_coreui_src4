package pasa.cbentley.framework.coreui.src4.event;

import pasa.cbentley.framework.coreui.src4.ctx.CoreUiCtx;

public class CtxChangeEvent extends BEvent {

   private Object newCtx;

   private int    deviceType;

   private int    deviceID;

   private Object oldCtx;

   public CtxChangeEvent(CoreUiCtx fc, Object newCtx, int deviceType, int deviceID, Object oldCtx) {
      super(fc);
      this.newCtx = newCtx;
      this.deviceType = deviceType;
      this.deviceID = deviceID;
      this.oldCtx = oldCtx;

   }

   public Object getNewCtx() {
      return newCtx;
   }

   public int getDeviceType() {
      return deviceType;
   }

   public int getDeviceID() {
      return deviceID;
   }

   public Object getOldCtx() {
      return oldCtx;
   }
}
