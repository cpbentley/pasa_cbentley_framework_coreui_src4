package pasa.cbentley.framework.coreui.src4.event;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.framework.coreui.src4.ctx.CoreUiCtx;
import pasa.cbentley.framework.coreui.src4.ctx.ToStringStaticCoreUi;

/**
 * Base class for creating EventKey.
 * 
 * An EventKey is pattern matching
 * 
 * @author Charles Bentley
 *
 */
public abstract class EventKey implements IStringable {

   public static final EventKey[] increaseCap(EventKey[] ek) {
      if (ek == null) {
         return new EventKey[1];
      } else {
         EventKey[] old = ek;
         EventKey[] news = new EventKey[old.length + 1];
         for (int i = 0; i < old.length; i++) {
            news[i] = old[i];
         }
         return news;
      }
   }

   /**
    * Pattern action to be taken when activated.
    * 
    * <li> {@link ITechEventKey#KEY_TYPE_0_FIRE}
    * <li> {@link ITechEventKey#KEY_TYPE_1_CANCEL}
    * <li> {@link ITechEventKey#KEY_TYPE_2_FIRE_AND_CANCEL}
    * <li> {@link ITechEventKey#KEY_TYPE_3_MUTE_FIRE}
    * <li> {@link ITechEventKey#KEY_TYPE_4_ACTIVATE_FIRE}
    */
   protected final int       patternAction;

   protected final CoreUiCtx cac;

   /**
    * <li> {@link ITechEventKey#KEY_TYPE_0_FIRE}
    * <li> {@link ITechEventKey#KEY_TYPE_1_CANCEL}
    * <li> {@link ITechEventKey#KEY_TYPE_2_FIRE_AND_CANCEL}
    * <li> {@link ITechEventKey#KEY_TYPE_3_MUTE_FIRE}
    * <li> {@link ITechEventKey#KEY_TYPE_4_ACTIVATE_FIRE}
    * 
    * @param keyType
    */
   public EventKey(CoreUiCtx cac, int keyType) {
      this.cac = cac;
      this.patternAction = keyType;

   }

   /**
    * <li> {@link ITechEventKey#KEY_TYPE_0_FIRE}
    * <li> {@link ITechEventKey#KEY_TYPE_1_CANCEL}
    * <li> {@link ITechEventKey#KEY_TYPE_2_FIRE_AND_CANCEL}
    * <li> {@link ITechEventKey#KEY_TYPE_3_MUTE_FIRE}
    * <li> {@link ITechEventKey#KEY_TYPE_4_ACTIVATE_FIRE}
    * 
    * @return
    */
   public int getKeyType() {
      return patternAction;
   }

   public abstract String getUserLineString();

   /**
    * 
    * @param ek
    * @return
    */
   public abstract boolean isEquals(EventKey ek);

   /**
    * Returns true when the event activates the key. (cancel/fire)
    * <br>
    * <li>For example, a LeftMousePress key is activated by a LeftMouseRelease
    * <li> A=B press is activated by ? any key releases
    * @param be
    * @return
    */
   public abstract boolean isKeyActivated(BEvent be);

   //#mdebug
   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx sb) {
      sb.root(this, "EventKey");
      sb.appendVarWithSpace("Type", ToStringStaticCoreUi.toStringKeyEventType(patternAction));
   }

   public UCtx toStringGetUCtx() {
      return cac.getUC();
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "EventKey");
      dc.appendVarWithSpace("Type", ToStringStaticCoreUi.toStringKeyEventType(patternAction));
   }
   //#enddebug
}
