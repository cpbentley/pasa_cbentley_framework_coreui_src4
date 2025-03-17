package pasa.cbentley.framework.core.ui.src4.input;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.framework.core.ui.src4.ctx.CoreUiCtx;
import pasa.cbentley.framework.core.ui.src4.event.BEvent;
import pasa.cbentley.framework.core.ui.src4.event.EventKey;
import pasa.cbentley.framework.core.ui.src4.event.RepeatEvent;
import pasa.cbentley.framework.core.ui.src4.tech.ITechInput;

/**
 * {@link NUpleEvent} is an active repetition of a event pattern by the user.
 * <br>
 * A contrario, repeat event is a passive repetition.
 * <br>
 * Is a NUple
 * When looking for a double click for example?
 * <br>
 * Should the NUple be valid for pointers ? 
 * It generates a nuple event when activated.
 * <br>
 * How are {@link NUpleEvent} used in a command framework?
 * <li>It is used to augment/modify an existing command
 * <li>Execute commands such as Exit with 5 fast escape
 * @author Charles Bentley
 *
 */
public class NUpleEvent extends RepeatEvent {

   /**
    * Root {@link KeyEventListed} that request a nuple count
    */
   private EventKey   kef;

   private InputState is;

   private int        maxTime;

   /**
    * 
    */
   private long       lastTickTime;

   private boolean isTimed;

   private boolean isLast;

   /**
    * 
    * @param kef
    */
   public NUpleEvent(CoreUiCtx cuc, EventKey evKey, InputState is) {
      super(cuc, ITechInput.REPEAT_4_PATTERN);

      this.kef = evKey;
      this.is = is;
      lastTickTime = is.getTimeCurrent();
      //depends on the device.. nuple time out
      maxTime = is.getInputSettings().getKeyNupleTimeout();
      multCount = 1;
      multTarget = Integer.MAX_VALUE;
   }

   public void setPointerTiming() {
      maxTime = is.getInputSettings().getPointerNupleTimeout();
   }
   /**
    * The {@link EventKey} that will fire the Nuple
    * @return
    */
   public EventKey getKeyEventFire() {
      return kef;
   }


   /**
    * Process new event and fire an event if a nuple occurs.
    * <br>
    * When only looking for double click. cancel the nupl
    * @param e 
    */
   public boolean isNewEventCanceling(InputState is, BEvent e) {
      //#debug
      toDLog().pEvent1("", e, NUpleEvent.class, "isNewEventCanceling");
      if (is.isTypeDevice()) {
         long diffTime = is.getTimeCurrent() - lastTickTime;
         //#debug
         toDLog().pEvent1("diffTime=" + diffTime, e, NUpleEvent.class, "isNewEventCanceling");
         if (diffTime > maxTime) {
            //#debug
            //toLog().ptEvent1("diffTime=" + diffTime + " maxTime=" + maxTime, e, NUpleEvent.class, "isNewEventCanceling");
            return true;//cancel because of timeout
         }
         boolean isCanceling = checkCancelers(e);
         if (isCanceling) {
            return true;
         } else {
            //when a group of keys is pressed
            boolean b = kef.isKeyActivated(e);
            //#debug
            toDLog().pEvent1("Activated=" + b, e, NUpleEvent.class, "isNewEventCanceling");
            if (b) {
               lastTickTime = is.getTimeCurrent();
               int count = incrementSyncCount();
               //generate nuple event.. is it a gesture? could be. it is an active repetition of a pattern by the user
               is.queuePost(this);
               if (count >= multTarget) {
                  return true;
               }
            }
         }
         return false;
      } else {
         return checkCancelers(e);
      }
   }

   /**
    * When timed, sends an event after timeout without an increase in nple.
    * <br>
    * When event is sent
    */
   public void setLastTimed() {
      isTimed = true;
   }
   
   /**
    * When {@link RepeatEvent} is timed, it will send itself again after a timeout
    * that invalidates an increase in the nuple count.
    * It will not be sent if canceled by another canceler. Only the timeout
    * @return
    */
   public boolean isLastRepeat() {
      return isLast;
   }
   
   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, "NUpleEvent");
      dc.appendVarWithSpace("#", getSyncCount());
      dc.nlLvl(kef);
   }

   public String getUserLineString() {
      return "NUple " + getSyncCount();
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "NUpleEvent");
      dc.appendVarWithSpace("#", getSyncCount());
   }
   //#enddebug

   

}
