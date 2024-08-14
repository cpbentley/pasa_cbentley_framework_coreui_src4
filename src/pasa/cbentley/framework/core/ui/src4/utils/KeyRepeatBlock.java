package pasa.cbentley.framework.core.ui.src4.utils;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.framework.core.ui.src4.ctx.CoreUiCtx;
import pasa.cbentley.framework.core.ui.src4.ctx.ObjectCUC;

public class KeyRepeatBlock extends ObjectCUC implements IStringable {

   protected int             pressedKeyCounter;

   protected int[]           pressedKeys = new int[20];


   public KeyRepeatBlock(CoreUiCtx cuc) {
      super(cuc);
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
   public void toString(Dctx dc) {
      dc.root(this, KeyRepeatBlock.class, 70);
      toStringPrivate(dc);
      super.toString(dc.sup());

      for (int i = 0; i < pressedKeyCounter; i++) {
         dc.append(pressedKeys[i]);
         dc.append(" ");
      }
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, KeyRepeatBlock.class, 70);
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   private void toStringPrivate(Dctx dc) {
      dc.appendVarWithSpace("pressedKeyCounter", pressedKeyCounter);

   }
   //#enddebug

}
