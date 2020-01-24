package pasa.cbentley.framework.coreui.src4.event;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.framework.coreui.src4.ctx.CoreUiCtx;

public abstract class EventKey implements IStringable {

   /**
    * Fire continuously
    */
   public static final int KEY_TYPE_0_FIRE            = 0;

   /**
    * Key cancels monitoring. Event is fired
    */
   public static final int KEY_TYPE_1_CANCEL          = 1;

   /**
    * Fire and cancel the {@link GesturePointer}.
    */
   public static final int KEY_TYPE_2_FIRE_AND_CANCEL = 2;

   /**
    * Keep the {@link GesturePointer} active. the fire will be muted
    * <br>
    * Flag
    */
   public static final int KEY_TYPE_3_MUTE_FIRE       = 3;

   public static final int KEY_TYPE_4_ACTIVATE_FIRE   = 4;

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

   public static final String toStringType(int type) {
      switch (type) {
         case KEY_TYPE_0_FIRE:
            return "Fire";
         case KEY_TYPE_1_CANCEL:
            return "Cancel";
         case KEY_TYPE_2_FIRE_AND_CANCEL:
            return "FireCancel";
         default:
            return "Unknown " + type;
      }
   }

   public static final String toStringUserLine(int type) {
      switch (type) {
         case KEY_TYPE_0_FIRE:
            return "fire";
         case KEY_TYPE_1_CANCEL:
            return "cancel";
         case KEY_TYPE_2_FIRE_AND_CANCEL:
            return "fire&cancel";
         default:
            return "Unknown " + type;
      }
   }

   protected final int       keyType;

   protected final CoreUiCtx cac;

   /**
    * <li> {@link EventKey#KEY_TYPE_0_FIRE}
    * <li> {@link EventKey#KEY_TYPE_1_CANCEL}
    * <li> {@link EventKey#KEY_TYPE_2_FIRE_AND_CANCEL}
    * <li> {@link EventKey#KEY_TYPE_3_MUTE_FIRE}
    * <li> {@link EventKey#KEY_TYPE_4_ACTIVATE_FIRE}
    * 
    * @param keyType
    */
   public EventKey(CoreUiCtx cac, int keyType) {
      this.cac = cac;
      this.keyType = keyType;

   }

   /**
    * <li> {@link EventKey#KEY_TYPE_0_FIRE}
    * <li> {@link EventKey#KEY_TYPE_1_CANCEL}
    * <li> {@link EventKey#KEY_TYPE_2_FIRE_AND_CANCEL}
    * <li> {@link EventKey#KEY_TYPE_3_MUTE_FIRE}
    * <li> {@link EventKey#KEY_TYPE_4_ACTIVATE_FIRE}
    * 
    * @return
    */
   public int getKeyType() {
      return keyType;
   }

   public abstract String getUserLineString();

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
      sb.appendVarWithSpace("Type", toStringType(keyType));
   }

   public UCtx toStringGetUCtx() {
      return cac.getUCtx();
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "EventKey");
      dc.appendVarWithSpace("Type", toStringType(keyType));
   }
   //#enddebug
}
