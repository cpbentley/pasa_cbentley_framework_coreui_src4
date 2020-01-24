package pasa.cbentley.framework.coreui.src4.event;

import pasa.cbentley.core.src4.structs.listdoublelink.LinkedListDouble;
import pasa.cbentley.framework.coreui.src4.ctx.CoreUiCtx;

public class EventCreator {

   private CoreUiCtx        dd;

   private LinkedListDouble pointersEmpty;

   private LinkedListDouble pointersActive;

   private int              poolingFlags;

   public EventCreator(CoreUiCtx dd) {
      this.dd = dd;
      pointersEmpty = new LinkedListDouble(dd.getUCtx());
      for (int i = 0; i < 10; i++) {
         GesturePointer gp = new GesturePointer(pointersEmpty, dd, -1);
         gp.addToList();
      }
   }

   /**
    * Set the {@link GesturePointer} in the active category list
    * @param pointerID
    * @return
    */
   public GesturePointer createGesturePointer(int pointerID) {
      GesturePointer gp = (GesturePointer) pointersEmpty.getHead();
      gp.reset(pointerID);
      gp.setListAndAdd(pointersActive);
      return gp;
   }

   /**
    * The Gesture object is released
    * @param gp
    */
   public void deleteGesture(GesturePointer gp) {
      gp.setListAndAdd(pointersEmpty);
   }

   /**
    * When kicked out of the history list, it is freed to be reused.
    * Event are flagged when fully processed.
    * <br>
    * The command processor will keep events used in active triggers. when trigger
    * is no more potential, it is dropped and events associated with are released.
    * <br>
    * @param gp
    */
   public void releaseDeviceEvent(DeviceEvent gp) {
   }
}
