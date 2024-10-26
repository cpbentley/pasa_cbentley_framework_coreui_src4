package pasa.cbentley.framework.core.ui.src4.user;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.memory.IMemFreeable;
import pasa.cbentley.core.src4.structs.IntToObjects;
import pasa.cbentley.framework.core.ui.src4.ctx.CoreUiCtx;
import pasa.cbentley.framework.core.ui.src4.ctx.ObjectCUC;

/**
 * The resources behind a {@link UserInteraction} is not managed here.
 * <br>
 * This class tracks the {@link UserInteraction} happening
 * <br>
 * Class must be used in the update thread.
 * 
 * @author Charles Bentley
 *
 */
public class UserInteractionCtrl extends ObjectCUC implements IMemFreeable {

   IUiACreator  c;

   IntToObjects itos;

   public UserInteractionCtrl(CoreUiCtx cuc) {
      super(cuc);
      UCtx uc = cuc.getUC();
      itos = new IntToObjects(uc);

      //how can you unregister a module?
      uc.getMem().addMemFreeable(this);

   }

   public void freeMemory() {
      itos.clear();
   }

   public UserInteraction getUIA(int uiaid) {
      //creates a new entry of a user interactions
      UserInteraction ui = c.createUIA(uiaid);
      itos.add(ui);
      //check if
      return ui;
   }

   /**
    * Must be accessed in the update thread
    * @param ui
    */
   public void log(UserInteraction ui) {
      itos.add(ui);
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, UserInteractionCtrl.class, 59);
      toStringPrivate(dc);
      super.toString(dc.sup());
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, UserInteractionCtrl.class, 59);
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   private void toStringPrivate(Dctx dc) {

   }
   //#enddebug

}
