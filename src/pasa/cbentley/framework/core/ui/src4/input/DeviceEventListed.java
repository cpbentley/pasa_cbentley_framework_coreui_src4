package pasa.cbentley.framework.core.ui.src4.input;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.structs.listdoublelink.LinkedListDouble;
import pasa.cbentley.core.src4.structs.listdoublelink.ListElement;
import pasa.cbentley.framework.core.ui.src4.event.DeviceEvent;

/**
 * Encapsulate the event state for a key.
 * <br>
 * Both press and release.
 * 
 * <br>
 * Listed in the {@link DeviceKeys} list history.
 * <br>
 * @author Charles Bentley
 *
 */
public class DeviceEventListed extends ListElement {

   /**
    * The {@link DeviceEvent} for the press
    */
   private DeviceEvent deviceEvent;

   private InputState  is;

   /**
    * 
    * @param list
    * @param is
    * @param de
    */
   public DeviceEventListed(LinkedListDouble list, InputState is) {
      super(list);
      this.is = is;
   }

   public void resetTo(DeviceEvent de) {
      deviceEvent = de;
   }

   /**
    * Event that could be null
    * @return
    */
   public DeviceEvent getEvent() {
      return deviceEvent;
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, "DeviceEventListed");
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "DeviceEventListed");
   }
   //#enddebug
}
