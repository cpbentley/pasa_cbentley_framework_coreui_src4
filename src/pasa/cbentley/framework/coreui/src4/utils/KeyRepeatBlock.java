package pasa.cbentley.framework.coreui.src4.utils;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.framework.coreui.src4.ctx.CoreUiCtx;

public class KeyRepeatBlock implements IStringable {

   protected int             pressedKeyCounter;

   protected int[]           pressedKeys = new int[20];

   protected final CoreUiCtx cuc;

   public KeyRepeatBlock(CoreUiCtx cuc) {
      this.cuc = cuc;

   }

   /**
    * Avoid repeats. What if different devices? must be the same device
    * @param j2seKeyCode
    * @return
    */
   public synchronized boolean isKeyRepeat(int j2seKeyCode) {
      for (int i = 0; i < pressedKeyCounter; i++) {
         if (pressedKeys[i] == j2seKeyCode) {
            return true;
         }
      }
      pressedKeys[pressedKeyCounter] = j2seKeyCode;
      pressedKeyCounter++;
      return false;
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

   //#mdebug
   public IDLog toDLog() {
      return toStringGetUCtx().toDLog();
   }

   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, KeyRepeatBlock.class, 71);
      toStringPrivate(dc);

      for (int i = 0; i < pressedKeyCounter; i++) {
         dc.append(pressedKeys[i]);
         dc.append(" ");
      }
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   private void toStringPrivate(Dctx dc) {
      dc.appendVarWithSpace("pressedKeyCounter", pressedKeyCounter);

   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "KeyReapeatBlock");
      toStringPrivate(dc);
   }

   public UCtx toStringGetUCtx() {
      return cuc.getUCtx();
   }

   //#enddebug

}
