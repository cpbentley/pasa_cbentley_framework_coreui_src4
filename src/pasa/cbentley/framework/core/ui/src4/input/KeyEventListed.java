package pasa.cbentley.framework.core.ui.src4.input;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.structs.listdoublelink.LinkedListDouble;
import pasa.cbentley.core.src4.structs.listdoublelink.ListElement;
import pasa.cbentley.framework.core.ui.src4.event.DeviceEvent;
import pasa.cbentley.framework.core.ui.src4.event.RepeatEvent;
import pasa.cbentley.framework.core.ui.src4.tech.ITechCodes;

/**
 * Encapsulate the event state for a key.
 * 
 * <p>
 * Both press and release. Then it is gone
 * <li> {@link KeyEventListed#isFastTyped()} 
 * <li> {@link KeyEventListed#isTyped()} 
 * </p>
 * 
 * Listed in the {@link DeviceKeys} list history.
 * @author Charles Bentley
 *
 */
public class KeyEventListed extends ListElement {

   /**
    * The {@link DeviceEvent} for the press
    */
   DeviceEvent         deviceEventPressed;

   /**
    * The {@link DeviceEvent} for the release
    */
   DeviceEvent         deviceEventReleased;

   /**
    * The device to which this key belongs to.
    */
   DeviceKeys          deviceKeys;

   /**
    * The repetition of the press alone.
    * <br>
    * null if none.
    * <br>
    * It is used to record long press.
    */
   private RepeatEvent eventRepeat;

   private InputState  is;

   /**
    * Pressed and release consecutively on the device.
    */
   private boolean     isDeviceStrictTyped = false;

   private boolean     isGestured          = false;

   private NUpleEvent  nuple;

   /**
    * 
    * @param is
    * @param list
    * @param de
    */
   public KeyEventListed(InputState is, LinkedListDouble list, DeviceEvent de) {
      super(list);
      this.is = is;
      this.deviceEventPressed = de;
   }

   /**
    * Event that was given in the constructo
    * @return
    */
   public DeviceEvent getEventPressed() {
      return deviceEventPressed;
   }

   public int getKey() {
      return deviceEventPressed.getDeviceButton();
   }

   /**
    * Is the current event, a nuple inside its device class.
    * <br>
    * Can interference with other keys cancel it? Yes when strict mode.
    * <br>
    * Nuple is looked up on the time constraint.
    * <br>
    * Nuple job
    * Has the pointer double tap event.
    * <br>
    * Button ids must match.
    * <br>
    * And 
    * Constraints on a double/nuple tap may vary from devices
    * <li>
    * <li>
    * 
    * @param pointerID
    * @return
    */
   private int getNUpleStrict(boolean isStrict, int timing) {
      LinkedListDouble keysHistoryList = deviceKeys.getHistoryList();
      KeyEventListed ke = (KeyEventListed) keysHistoryList.getTail();
      int nUplePressed = 1;
      int myKey = getKey();
      long timePressed = getTimePressed();
      while (ke != null) {
         long timePressedNext = ke.getTimePressed();
         if (timePressed - timePressedNext < timing) {
            if (myKey == ke.getKey()) {
               nUplePressed++;
               timePressed = timePressedNext;
               ke = (KeyEventListed) ke.getPrev();
            } else {
               if (isStrict) {
                  ke = null; //exit loop  
               } else {
                  ke = (KeyEventListed) ke.getPrev(); //continue look up
               }
            }
         } else {
            ke = null;
         }
      }
      return nUplePressed;
   }

   /**
    * {@link ITechCodes#TIMING_1_SLOW} constraint. which is a long press event time out
    * @return
    */
   public int getNUpleStrictSlow() {
      return getNUpleStrict(false, is.getInputSettings().getPointerLongTimeout());
   }

   /**
    * 
    * @return
    */
   public int getNUpleStrictFast() {
      return getNUpleStrict(false, is.getInputSettings().getKeyFastTypeTimeout());
   }

   /**
    * Allows interference of other device keys.
    * <br>
    * <br>
    * @return
    */
   public int getNUpleDevice() {
      return getNUpleStrict(false, is.getInputSettings().getKeyNupleTimeout());
   }

   /**
    * Time interval from current {@link InputState} time and key pressed time.
    * <br>
    * <br>
    * @return
    */
   public long getPressedTimeInterval() {
      return is.getTimeCurrent() - deviceEventPressed.getTime();
   }

   public NUpleEvent getNUple() {
      return nuple;
   }

   public RepeatEvent getRepeat() {
      return eventRepeat;
   }

   /**
    * The number of times the key is repeated.
    * <br>
    * It will be 0 when the Rep
    * @return
    */
   public int getRepeatCount() {
      if (eventRepeat != null) {
         return eventRepeat.getSyncCount();
      }
      return 0;
   }

   /**
    * The time at which that key was pressed.
    * <br>
    * @return
    */
   public long getTimePressed() {
      return deviceEventPressed.getTime();
   }

   public long getTimeReleased() {
      if (deviceEventReleased == null) {
         throw new IllegalStateException("Key not released yet");
      }
      return deviceEventReleased.getTime();
   }

   /**
    * Nuple with x y constraints on the pointer associated with it
    * @return
    */
   public boolean isDoublePress() {
      return false;
   }

   public boolean isFastTyped() {
      if (isDeviceStrictTyped) {
         int fastKeyTyped = is.getFastKeyTypeValue();
         long timeReleased = deviceEventReleased.getTime();
         long timePressed = deviceEventPressed.getTime();
         if (timeReleased - timePressed < fastKeyTyped) {
            return true;
         }
      }
      return false;
   }

   public boolean isGestured() {
      return isGestured;
   }

   /**
    * True when the event was pressed and released without any other event from the same device.
    * @return
    */
   public boolean isTyped() {
      return isDeviceStrictTyped;
   }

   /**
    * 
    * @param de
    * @param dk
    */
   public void setPressReset(DeviceEvent de, DeviceKeys dk) {
      this.deviceKeys = dk;
      dk.lastKeyboardEvent = this;
      this.deviceEventPressed = de;
      isDeviceStrictTyped = false;
      eventRepeat = null;
      nuple = null;
   }

   public void setGestured(boolean b) {
      isGestured = b;
   }

   public void setRelease(DeviceEvent de) {
      if (deviceKeys.lastKeyboardEvent == this) {
         isDeviceStrictTyped = true;
      }
      deviceKeys.setRelease(this);

      deviceEventReleased = de;

   }

   public void setRepeat(RepeatEvent er) {
      if (er == null) {
         throw new NullPointerException("RepeatEvent");
      }
      this.eventRepeat = er;
   }

   public void setNuple(NUpleEvent np) {
      this.nuple = np;
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, KeyEventListed.class, "@line282");
      toStringPrivate(dc);
      super.toString(dc.sup());

      dc.nlLvl(eventRepeat, "eventRepeat");
      dc.nlLvl(deviceKeys, "deviceKeys");
      dc.nlLvl(deviceEventPressed, "deviceEventPressed");
      dc.nlLvl(deviceEventReleased, "deviceEventReleased");
      dc.nlLvl(nuple, "nuple");
         
   }

   private void toStringPrivate(Dctx dc) {
      dc.appendVarWithSpace("Key", getKey());
      dc.appendVarWithSpace("IsTyped", isDeviceStrictTyped);
      dc.appendVarWithSpace("isGestured", isGestured);

   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, KeyEventListed.class);
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   //#enddebug

}
