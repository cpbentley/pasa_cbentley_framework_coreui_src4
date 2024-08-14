package pasa.cbentley.framework.core.ui.src4.ctx;

import pasa.cbentley.byteobjects.src4.core.BOModuleAbstract;
import pasa.cbentley.byteobjects.src4.core.ByteObject;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDebugStringable;

/**
 * Managed all the Types of this Business Model module.
 * <br>
 * <br>
 * 
 * @author Charles-Philip Bentley
 *
 */
public class BOModuleCoreUi extends BOModuleAbstract implements IDebugStringable, IBOTypesCoreUi {

   protected final CoreUiCtx cuc;

   public BOModuleCoreUi(CoreUiCtx cdc) {
      super(cdc.getBOC());
      this.cuc = cdc;
      //#debug
      toDLog().pInit("", this, BOModuleCoreUi.class, "Created@25", LVL_04_FINER, true);
   }

   public ByteObject getFlagOrderedBO(ByteObject bo, int offset, int flag) {
      // TODO Auto-generated method stub
      return null;
   }

   public ByteObject merge(ByteObject root, ByteObject merge) {
      int type = merge.getType();

      switch (type) {
         case TYPE_8_FRAME_POS:
            //merge 2 frame positions
            throw new RuntimeException();
         case TYPE_5_CANVAS_HOST:
      }
      return null;
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, BOModuleCoreUi.class, 120);
      toStringPrivate(dc);
      super.toString(dc.sup());
   }


   public boolean toString(Dctx dc, ByteObject bo) {
      int type = bo.getType();
      switch (type) {
         case TYPE_5_CANVAS_HOST:
            dc.append(toStringType(type));
            break;
         case TYPE_7_FILECHOOSER:
            dc.append(toStringType(type));
            break;
         case TYPE_8_FRAME_POS:
            dc.append(toStringType(type));
            break;
         default:
            return false;
      }
      return true;
   }


   public void toString1Line(Dctx dc) {
      dc.root1Line(this, BOModuleCoreUi.class);
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   public boolean toString1Line(Dctx dc, ByteObject bo) {
      int type = bo.getType();
      switch (type) {
         case TYPE_5_CANVAS_HOST:
            dc.append(toStringType(type));
            break;
         case TYPE_7_FILECHOOSER:
            dc.append(toStringType(type));
            break;
         case TYPE_8_FRAME_POS:
            dc.append(toStringType(type));
            break;
         default:
            return false;
      }
      return true;
   }

   public String toStringGetDIDString(int did, int value) {
      return null;
   }

   public String toStringOffset(ByteObject bo, int offset) {
      int type = bo.getType();
      switch (type) {
         case TYPE_5_CANVAS_HOST:
            return null;
         case TYPE_7_FILECHOOSER:
            return null;
         case TYPE_8_FRAME_POS:
         default:
      }
      return null;
   }

   private void toStringPrivate(Dctx dc) {

   }

   public String toStringType(int type) {
      switch (type) {
         case IBOTypesCoreUi.TYPE_5_CANVAS_HOST:
            return "CanvasHost";
         case IBOTypesCoreUi.TYPE_7_FILECHOOSER:
            return "FileChooser";
         case IBOTypesCoreUi.TYPE_8_FRAME_POS:
            return "FramePos";

         default:
            return null;
      }
   }
   //#enddebug

}
