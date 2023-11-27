package pasa.cbentley.framework.coreui.src4.event;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.core.src4.structs.listdoublelink.LinkedListDouble;
import pasa.cbentley.core.src4.structs.listdoublelink.ListElementHolder;
import pasa.cbentley.framework.coreui.src4.ctx.CoreUiCtx;
import pasa.cbentley.framework.coreui.src4.ctx.ToStringStaticCoreUi;
import pasa.cbentley.framework.coreui.src4.tech.IInput;

/**
 * The event object travelling through the event pipes.
 * Repeats a pattern until a cancel event occurs.
 * <br> 
 * <li> one key pressed is canceled by the same key being released. {@link EventKeyDevice}
 * <li> one mouse pointer long pressed is also canceled if its moves. {@link EventKeyPosition}
 * Could be several keys in pressed mode.
 * <br>
 * <br>
 * Use Case: Arrow Keys.
 * <br>
 * As long as one arrow key is pressed, a Navigation event is repeated.
 * <br>
 * When requesting a repetition, code creates {@link RepeatEvent}. It plugs the repeat
 * with siblings class if any.
 * <br>
 * When siblings are repeated, a single repeat event is sent. Code knows that this event is about those siblings.
 * Event group.
 * 
 * When a pointer pressed is repeated, it creates a new class of repetition which will
 * be processed independently.
 * <br>
 * <br>
 * <br>
 * Repetition happens until
 * <li> any event occurs
 * <li> all ids contrarians in the chain occur
 * <li> one id contrarian occur
 * <br>
 * <br>
 * Repeat of a CmdTrigger
 * 
 * Can also be used to repeat a command, as long as
 * an event is not recieved
 * 
 * @author Charles Bentley
 *
 */
public class RepeatEvent extends BEvent implements IStringable {

   public static final int    FLAG_1_CANCEL_ON_RELEASE     = 1 << 5;

   public static final int    FLAG_15_HAS_STARTING_TIMEOUT = 1 << 14;

   public static final int    FLAG_16_IS_PINGING           = 1 << 15;

   public static final int    FLAG_2_CANCEL_ON_POINTER     = 1 << 6;

   //todo use an eventkey. any press from same user/same device...
   public static final int    FLAG_3_CANCEL_ON_ANY_PRESS   = 1 << 7;

   public static final int    FLAG_4_IS_CANCEL_EVENT       = 1 << 8;

   public static final int    FLAG_5_IS_NUPLE              = 1 << 8;

   public static final int    REPEAT_STATE_0_NORMAL        = 0;

   public static final int    REPEAT_STATE_1_STARTED       = 1;

   public static final int    REPEAT_STATE_2_PINGING       = 2;

   public static final int    REPEAT_STATE_3_CANCELED      = 3;

   /**
    * Event stops the repeat because it has run its course. Infinite events are 
    * not finalized
    */
   public static final int    REPEAT_STATE_4_FINALIZED     = 4;

   /**
    * {@link EventKey} that will cancels
    * List of Even  .
    * <br>
    * When null, only canceled by timeout
    */
   protected LinkedListDouble cancelers;

   private EventKey           cancelingKey;

   protected LinkedListDouble fireConditions;

   private boolean            isSendCancel;

   /**
    * Starts at zero
    */
   protected int              multCount;

   /**
    * Number of repeitions to make.
    * <br>
    * 0 means infinite.
    * 1 one repeat
    */
   protected int              multTarget                   = Integer.MAX_VALUE;

   /**
    * The pattern of repeats
    * <li> One wait, X periods
    * <li> X periods until X long
    * <br>
    * <br>
    *  
    */
   private int[]              pattern;

   /**
    * milliseconds for repetition
    */
   private int                periodMillis;

   private int                pingAccumulation;

   /**
    * is a ping event done between event occurences
    */
   private int                pingMillis;

   protected int              repeatState;

   /**
    * <li> {@link IInput#REPEAT_0_INFINITE}
    * <li> {@link IInput#REPEAT_1_FINITE}
    * <li> {@link IInput#REPEAT_2_LONG}
    * <li> {@link IInput#REPEAT_3_TRAIL_FUNCTION}
    * <li> {@link IInput#REPEAT_4_PATTERN}
    * 
    */
   protected int              repeatType;

   private Object             source;

   private int                startMillis;

   public void setPeriodMillis(int period) {
      this.periodMillis = period;
   }

   public int getPeriodMillis() {
      return periodMillis;
   }

   /**
    * Set the number of milliseconds before the first repetition is fired
    * @param startMillis
    */
   public void setStartMillis(int startMillis) {
      this.startMillis = startMillis;
   }

   public RepeatEvent(CoreUiCtx cac) {
      super(cac);
      type = IInput.TYPE_4_REPEAT;

   }

   public RepeatEvent(CoreUiCtx k, int repeatType) {
      super(k);
      type = IInput.TYPE_4_REPEAT;
      this.repeatType = repeatType;
      //target is unknown
      //set starting state

   }

   public void addCanceler(EventKey ek) {
      if (cancelers == null) {
         cancelers = new LinkedListDouble(fc.getUCtx());
      }
      cancelers.addFreeHolder(ek);
   }

   /**
    * The repeat event may be repeated by other means of a timer ?
    * @param ek
    */
   public void addFireCondition(EventKey ek) {
      if (fireConditions == null) {
         fireConditions = new LinkedListDouble(fc.getUCtx());
      }
      fireConditions.addFreeHolder(ek);
   }

   /**
    * Returns true if event cancels the {@link RepeatEvent}.
    * <br>
    * TODO does focus out stops the repeats??
    * @param e
    * @return
    */
   public boolean checkCancelers(BEvent e) {
      if (cancelers != null) {
         if (e instanceof RepeatEvent) {
            return false;
         }
         //optimize with a cue on which events are used by the EventKeys
         LinkedListDouble list = cancelers;
         ListElementHolder bj = (ListElementHolder) list.getHead();
         while (bj != null) {
            EventKey ek = (EventKey) bj.getObject();
            boolean isCancel = ek.isKeyActivated(e);
            if (isCancel) {
               cancelingKey = ek;
               return true;
            }
            bj = (ListElementHolder) bj.getNext();
         }
      }
      return false;
   }

   /**
    * True when all fire conditions are met.
    * <br>
    * @param e
    * @return
    */
   public boolean checkFireConditions(BEvent e) {
      if (fireConditions != null) {
         LinkedListDouble list = fireConditions;
         ListElementHolder bj = (ListElementHolder) list.getHead();
         while (bj != null) {
            EventKey ek = (EventKey) bj.getObject();
            boolean isCancel = ek.isKeyActivated(e);
            if (!isCancel) {
               return false;
            }
            bj = (ListElementHolder) bj.getNext();
         }
      }
      return true;
   }

   /**
    * The {@link EventKey} that actually canceled the {@link RepeatEvent}.
    * @return
    */
   public EventKey getCancelingKey() {
      return cancelingKey;
   }

   public int getCount() {
      return multCount;
   }

   public int getPingAccumulationTime() {
      return pingAccumulation;
   }

   public int getPingMillis() {
      return pingMillis;
   }

   public int getRepeatState() {
      return repeatState;
   }

   /**
    * 
    * <li> {@link IInput#REPEAT_0_INFINITE}
    * <li> {@link IInput#REPEAT_1_FINITE}
    * <li> {@link IInput#REPEAT_2_LONG}
    * <li> {@link IInput#REPEAT_3_TRAIL_FUNCTION}
    * 
    * @return
    */
   public int getRepeatType() {
      return repeatType;
   }

   /**
    * The object being repeated.
    * <li> most often a single key
    * <li> a mouse button in a long press
    * <li> 2 keys pressed simultaneously.
    * <br>
    * <br>
    * @return
    */
   public Object getSource() {
      return source;
   }

   public synchronized int getSyncCount() {
      //#debug
      //toLog().ptEvent1("", null, RepeatEvent.class, "getSyncCount");
      return multCount;
   }

   public int getTarget() {
      return multTarget;
   }

   public int getTimeout() {
      if (pattern != null) {
         int index = multCount % pattern.length;
         return pattern[index];
      } else {
         if (multCount == 0 && isUseStartingTimeOut()) {
            return startMillis;
         } else {
            return periodMillis;
         }
      }
   }

   public String getUserLineString() {
      if (repeatState == REPEAT_STATE_3_CANCELED) {
         if (debugName != null) {
            return debugName + " Repeat canceled";
         } else {
            return "Repeat Canceled";
         }
      } else if (repeatState == REPEAT_STATE_2_PINGING) {
         if (debugName != null) {
            return debugName + " Repeat ping";
         } else {
            return "Repeat ping";
         }
      } else if (repeatState == REPEAT_STATE_1_STARTED) {
         return "Repeat Started";
      } else if (repeatState == REPEAT_STATE_4_FINALIZED) {
         return "Repeat Finalized" + ToStringStaticCoreUi.getStringRepeatType(repeatType) + " " + multCount;
      } else {
         return "Repeat " + ToStringStaticCoreUi.getStringRepeatType(repeatType) + " " + multCount;
      }
   }

   public synchronized int incrementSyncCount() {
      //#debug
      //toLog().ptEvent1("", null, RepeatEvent.class, "incrementSyncCount");
      multCount++;
      return multCount;
   }

   /**
    * The Repeat Event was canceled
    * @return
    */
   public boolean isCanceled() {
      return repeatState == REPEAT_STATE_3_CANCELED;
   }

   public boolean isPinging() {
      return repeatState == REPEAT_STATE_2_PINGING;
   }

   public boolean isSendCancelEvent() {
      return isSendCancel;
   }

   public boolean isUsePinging() {
      return hasFlag(FLAG_16_IS_PINGING);
   }

   public boolean isUseStartingTimeOut() {
      return hasFlag(FLAG_15_HAS_STARTING_TIMEOUT);
   }

   /**
    * Any press from the same device/user/any
    */
   public void setCancelAnyPress() {
      EventKeyAnyPress ekp = new EventKeyAnyPress(fc, ITechEventKey.KEY_TYPE_1_CANCEL);
      addCanceler(ekp);
   }

   public void setCanceled() {
      setRepeatState(REPEAT_STATE_3_CANCELED);
   }

   /**
    * S
    */
   public void setPatternTiming(int[] timing, boolean isLooping) {
      if (timing != null && timing.length == 0)
         timing = null;
      pattern = timing;
   }

   public void setPingAccu(int time) {
      pingAccumulation = time;
   }

   public void setPingingTrue() {
      setFlag(FLAG_16_IS_PINGING, true);
   }

   public void setPingingFalse() {
      setFlag(FLAG_16_IS_PINGING, false);
   }

   public void setPingTime(int pingTime) {
      pingMillis = pingTime;
   }

   public void setRepeatState(int repeatState) {
      this.repeatState = repeatState;
   }

   /**
    * This value is ignored when type is {@link IInput#REPEAT_0_INFINITE}
    * @param value
    */
   public void setRepeatTarget(int value) {
      multTarget = value;
   }

   /**
    * <li> {@link IInput#REPEAT_0_INFINITE}
    * <li> {@link IInput#REPEAT_1_FINITE}
    * <li> {@link IInput#REPEAT_2_LONG}
    * <li> {@link IInput#REPEAT_3_TRAIL_FUNCTION}
    * @param type
    */
   public void setRepeatType(int type) {
      this.repeatType = type;
   }

   /**
    * Send an event when the repeatjob is canceled because of timing and released
    */
   public void setSendCancel() {
      isSendCancel = true;
   }

   /**
    * 
    */
   public void setSource(Object src) {
      source = src;
   }

   public void setStartTimeOut() {
      setFlag(FLAG_15_HAS_STARTING_TIMEOUT, true);
   }

   public void setTimingKeyboard() {
      startMillis = fc.getInputSettings().getKeyRepeatTimeout();
      periodMillis = fc.getInputSettings().getKeyRepeatDelay();
      pingMillis = 20;
   }

   public void setTimingLongPressMouse() {
      startMillis = 5000;
      periodMillis = fc.getInputSettings().getPointerLongTimeout();
      pingMillis = 20;
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, RepeatEvent.class, 437);
      dc.appendVarWithSpace("TypeRepeat", ToStringStaticCoreUi.getStringRepeatType(repeatType));
      dc.appendVarWithSpace("multCount", multCount);
      dc.appendVarWithSpace("multTarget", multTarget);
      dc.nl();
      dc.appendVar("StartMillis", startMillis);
      dc.appendVarWithSpace("PeriodMillis", periodMillis);
      dc.nlLvl(cancelers, "Cancelers");
      dc.nlLvl(fireConditions, "FireConditions");
      dc.nlLvl(cancelingKey, "CancelingEventKey");
      dc.nlLvlO(source, "RepeatEventSoure");
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, RepeatEvent.class);
      super.toString1Line(dc.sup1Line());
      dc.appendVarWithSpace("count", multCount);
   }
   //#enddebug

}
