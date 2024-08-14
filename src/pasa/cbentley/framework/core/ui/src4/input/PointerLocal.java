package pasa.cbentley.framework.core.ui.src4.input;

import pasa.cbentley.core.src4.structs.IntBuffer;
import pasa.cbentley.framework.core.ui.src4.ctx.CoreUiCtx;
import pasa.cbentley.framework.core.ui.src4.ctx.ObjectCUC;

/**
 * Validate a pointer position inside an area
 * @author Charles Bentley
 *
 */
public class PointerLocal extends ObjectCUC {

   private IntBuffer ib;

   private int       maxDist;


   public PointerLocal(CoreUiCtx cuc, InputState is) {
      super(cuc);
      maxDist = is.getInputSettings().getPointerDragSlop();

   }

   /**
    * Nuple is invalidated when pointerID moves more than a given distance
    * 
    * @param pointerID
    */
   public void addLocalizedTo(Pointer pointer) {
      if (ib == null) {
         ib = new IntBuffer(cuc.getUC());
      }
      //when fingers are up and down. pointerIDs change
      ib.addInt(pointer.getPointerID());
      ib.addInt(pointer.getX());
      ib.addInt(pointer.getY());
   }

   public boolean isValid(Pointer pointer) {
      //check locality
      for (int i = 0; i < ib.getSize(); i++) {
         int pointerID = ib.get(i);
         int pointerX = ib.get(i + 1);
         int pointerY = ib.get(i + 2);
         if (pointer.getPointerID() == pointerID) {
            //check vectors
            int curX = pointer.getX();
            int absDistX = Math.abs(pointerX - curX);
            if (absDistX > maxDist) {
               return false;
            }
            int curY = pointer.getY();
            int absDistY = Math.abs(pointerY - curY);
            if (absDistY > maxDist) {
               return false;
            }
         }
      }
      return true;
   }
}
