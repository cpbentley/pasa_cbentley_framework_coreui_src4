package pasa.cbentley.framework.coreui.src4.engine;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.framework.coreui.src4.tech.ITechCodes;

public abstract class KeyMapAbstract implements IStringable, ITechCodes {

   protected int        pressedKeyCounter;

   protected int[]      pressedKeys = new int[20];

   protected final UCtx uc;

   public KeyMapAbstract(UCtx uc) {
      this.uc = uc;
   }

   private boolean isNumPadInvert;

   public boolean isNumPadInvert() {
      return isNumPadInvert;
   }

   public void setNumPadInvert(boolean b) {
      isNumPadInvert = b;
   }

   /**
    * Decided by the config, the settings
    * @return
    */
   public boolean isInverseNumPad28() {
      return false;
   }

   public void setMajOn(boolean b) {

   }

   /**
    * Prevent the repeating nature of keys at the framework level.
    * <br>
    * Some frameworks like SWT will not send a key release if the key is
    * released after the release of another key.
    * <br>
    * TODO In those cases, when such a mix occurs, we have to release all keys
    * @param j2seKeyCode
    */
   public synchronized void keyReleaseRepeat(int j2seKeyCode) {
      boolean shift = false;
      for (int i = 0; i < pressedKeyCounter; i++) {
         if (pressedKeys[i] == j2seKeyCode) {
            shift = true;
         } else if (shift) {
            pressedKeys[i - 1] = pressedKeys[i];
         }
      }
      if (pressedKeyCounter > 0) {
         pressedKeyCounter--;
      }
   }

   /**
    * Maps the key id to {@link ITechCodes}
    * @param key
    * @return
    */
   public abstract int getKeyMappedToFramework(int key);

   //#mdebug
   public IDLog toDLog() {
      return toStringGetUCtx().toDLog();
   }

   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "KeyMapAbstract");
      toStringPrivate(dc);
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   private void toStringPrivate(Dctx dc) {

   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "KeyMapAbstract");
      toStringPrivate(dc);
   }

   public UCtx toStringGetUCtx() {
      return uc;
   }

   //#enddebug

}
