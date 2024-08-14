package pasa.cbentley.framework.core.ui.src4.input;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.core.src4.structs.listdoublelink.LinkedListDouble;
import pasa.cbentley.framework.core.ui.src4.ctx.CoreUiCtx;
import pasa.cbentley.framework.core.ui.src4.ctx.ObjectCUC;

/**
 * Tracks Pressed Keys for a Device.
 * <br>
 * <br>
 * ..
 * But could be also do a gesture for a keyboard key? Why not?
 * 
 * Fingers that belong to a screen
 * @author Charles Bentley
 *
 */
class DeviceKeys extends ObjectCUC implements IStringable {

   private int              deviceID;

   private int              deviceType;

   private InputState       is;

   private LinkedListDouble keysHistoryList;

   /**
    * List of {@link KeyEventListed}.
    */
   private LinkedListDouble keysPressedList;

   /**
    * Last key event for this group of keys
    */
   KeyEventListed           lastKeyboardEvent;

   private int              lastKeyPress   = Integer.MIN_VALUE;

   private long             lastKeyPressTime;

   //anti linux key bug
   private int              lastKeyRelease = Integer.MIN_VALUE;

   //anti linux key bug or any bug that make a release event just after a press event
   private long             lastKeyReleaseTime;


   public DeviceKeys(CoreUiCtx cuc, InputState is, int deviceType, int deviceID) {
      super(cuc);
      this.is = is;
      this.deviceType = deviceType;
      this.deviceID = deviceID;
      keysPressedList = new LinkedListDouble(cuc.getUC());
      keysHistoryList = new LinkedListDouble(cuc.getUC());
   }

   /**
    * In stupid Linux.. when a key is pressed, it is quickly followed by a release and a press and a release
    * until you eventually release the physical key. And they said Linux dev are bright.
    * <br>
    * <br>
    * 
    * @param key
    * @param timeCurrent
    * @return
    */
   public boolean antiBugPress(int key, long timeCurrent) {
      if (key == lastKeyRelease) {
         long diff = timeCurrent - lastKeyReleaseTime;
         if (diff < is.ignoreTooFastKeyEvents) {
            return true;
         }

      }
      lastKeyPress = key;
      lastKeyPressTime = timeCurrent;
      return false;
   }

   /**
    * 
    * @param key
    * @param timeCurrent
    */
   public boolean antiBugRelease(int key, long timeCurrent) {
      if (key == lastKeyPress) {
         long diff = timeCurrent - lastKeyPressTime;
         if (diff < is.ignoreTooFastKeyEvents) {
            //but what if it is the true release event?
            //TODO set up a timer. if timer is not invalidated by a press during the time period.. fire release again
            return true;
         }
      }
      lastKeyReleaseTime = timeCurrent;
      lastKeyRelease = key;
      return false;
   }

   public LinkedListDouble getHistoryList() {
      return keysHistoryList;
   }

   /**
    * Null if key is not pressed
    * @param key
    * @return
    */
   public KeyEventListed getKeyEventPressed(int key) {
      //start with tail cuz most of the time last key pressed is released first
      KeyEventListed ke = (KeyEventListed) keysPressedList.getTail();
      while (ke != null) {
         if (ke.getKey() == key) {
            return ke;
         }
         ke = (KeyEventListed) ke.getPrev();
      }
      return null;
   }

   public int getNumKeysPressed() {
      return keysPressedList.getNumElements();
   }

   public KeyEventListed[] getPressedKeys() {
      KeyEventListed ke = (KeyEventListed) keysPressedList.getHead();
      int size = keysPressedList.getNumElements();

      KeyEventListed[] ar = new KeyEventListed[size];
      int count = 0;
      while (ke != null) {
         ar[count] = ke;
         ke = (KeyEventListed) ke.getNext();
         count++;
      }
      return ar;
   }

   public LinkedListDouble getPressedKeysList() {
      return keysPressedList;
   }

   public boolean isNupled(int n) {
      if (lastKeyboardEvent != null) {
         int val = lastKeyboardEvent.getNUpleDevice();
         return n <= val;
      }
      return false;
   }

   /**
    * 
    * @param ke
    */
   public void setRelease(KeyEventListed ke) {
      lastKeyboardEvent = ke;
      ke.removeFromList();
      //pop the head of history// write it to long term storage
      //attach list to press
      ke.setListAndAdd(keysHistoryList);
      is.dotrimListKey(keysHistoryList);
   }
   
   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, DeviceKeys.class,178);
      toStringPrivate(dc);
      
      dc.appendVarWithSpace("lastKeyPress", lastKeyPress);
      dc.appendVarWithSpace("lastKeyPressTime", lastKeyPressTime);
      dc.appendVarWithSpace("lastKeyRelease", lastKeyRelease);
      dc.appendVarWithSpace("lastKeyReleaseTime", lastKeyReleaseTime);
      
      dc.nlLvl(lastKeyboardEvent, "lastKeyboardEvent");
      
      dc.nlLvl(keysPressedList, "keysPressedList");
      dc.nlLvl(keysHistoryList, "keysHistoryList");
   }


   private void toStringPrivate(Dctx dc) {
      dc.appendVarWithSpace("deviceID", deviceID);
      dc.appendVarWithSpace("deviceType", deviceType);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, DeviceKeys.class);
      toStringPrivate(dc);
   }


   //#enddebug
   

}
