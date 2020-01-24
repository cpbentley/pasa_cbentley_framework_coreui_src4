package pasa.cbentley.framework.coreui.src4.event;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.event.BusEvent;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.core.src4.utils.BitUtils;
import pasa.cbentley.core.src4.utils.DateUtils;
import pasa.cbentley.framework.coreui.src4.ctx.CoreUiCtx;
import pasa.cbentley.framework.coreui.src4.interfaces.BCodes;
import pasa.cbentley.framework.coreui.src4.interfaces.ICanvasAppli;
import pasa.cbentley.framework.coreui.src4.tech.IBCodes;
import pasa.cbentley.framework.coreui.src4.tech.ITechGestures;
import pasa.cbentley.framework.coreui.src4.tech.IInput;

/**
 * Used by {@link ICanvasAppli#event(BEvent)}
 * <br>
 * Those event objects cannot be stored because their state will be used again.
 * <br>are valid 
 * <br>
 * The historical flags is set when locally store.
 * <br>
 * <br>
 * The outside world can rely on the event only during its official life.
 * <br>
 * Event Creator has the option to turn off/on the pooling
 * <br>
 * @author Charles Bentley
 *
 */
public class BEvent extends BusEvent implements IStringable, IInput, IBCodes {

   public static final int   EVENT_FLAG_1_VIRTUAL    = 1;

   public static final int   EVENT_FLAG_2_POOLED     = 1 << 1;

   public static final int   EVENT_FLAG_3_HISTORICAL = 1 << 3;

   protected final CoreUiCtx fc;

   /**
    * Is it a Host Event / Framework Event, Trigger Event, Action Event
    */
   private int               flags;

   private int               id;

   protected Object          source;

   private long              timeStamp;

   /**
    * type of event. Some type are hardware. 
    * <li> {@link IInput#TYPE_1_DEVICE} Device
    * <li> {@link IInput#TYPE_2_GESTURE} Compute
    * <li> {@link IInput#TYPE_5_CTX_CHANGE} Framework.
    * 
    * {@link ITechGestures#GESTURE_DOUBLE_TYPE_1_PINCH}
    * 
    * Triggers are units of ...
    * Trigger Luminosity reaching 90%. Trigger will active again only once Light goes below and up again.
    * However the sensor keeps sensing sending events. a mapper must map lights events
    * to possible triggers. How?
    * <br>
    * Well like the Shake event
    * <br>
    * <br>
    * The slide that moves current view and starts showing next view is a continuous command. upon release
    * depending on position of release, a page slide occurs or current page slides back to position.
    * A registered fling registered may go to move to yet another page further. 
    */
   protected int             type;

   public BEvent(CoreUiCtx fc, IEventCreator ec) {
      super(fc.getUCtx(), fc.getEventBus(), 0, 0);
      this.fc = fc;
   }

   public BEvent(CoreUiCtx fc) {
      super(fc.getUCtx(), fc.getEventBus(), 0, 0);
      if (fc == null)
         throw new NullPointerException();
      this.fc = fc;
   }

   public int getID() {
      return id;
   }

   public Object getSource() {
      return source;
   }

   /**
    * Used for ordering.. but what if 2 events are using the same timestamp?
    * You won't be able to truly order them
    * @return
    */
   public long getTime() {
      return timeStamp;
   }

   public int getType() {
      return type;
   }

   public String getUserLineString() {
      return "BEvent " + id;
   }

   public boolean hasFlag(int flag) {
      return BitUtils.hasFlag(flags, flag);
   }

   public void setFlag(int flag, boolean v) {
      flags = BitUtils.setFlag(flags, flag, v);
   }

   public void setID(int eventUniID) {
      this.id = eventUniID;
   }

   /**
    * Object that semantically sourced the event.
    * <br>
    * Allows to differentiate the event from different sources.
    * <br.
    * @param source
    */
   public void setSource(Object source) {
      this.source = source;
   }

   public void setTime(long time) {
      timeStamp = time;
   }

   //#mdebug
   public IDLog toLog() {
      return fc.toDLog();
   }

   public IDLog toDLog() {
      return fc.toDLog();
   }

   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "BEvent");
      dc.appendVarWithSpace("#", id);
      dc.appendVarWithSpace("Type", BCodes.getStringEventType(type));
      dc.appendVarWithSpace("Flags", flags);
      dc.appendVarWithSpace("Time", DateUtils.getDslashMslashYslashHourslashMin(timeStamp));
      super.toString(dc.sup());
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "BEvent");
      dc.append("#" + id);
      dc.appendVarWithSpace("Type", BCodes.getStringEventType(type));
      if (flags != 0) {
         dc.appendVarWithSpace("Flags", flags);
      }
      dc.appendVarWithSpace("Time", DateUtils.getDslashMslashYslashHourslashMin(timeStamp));
   }
   //#enddebug

   public UCtx toStringGetUCtx() {
      return fc.getUCtx();
   }
}
