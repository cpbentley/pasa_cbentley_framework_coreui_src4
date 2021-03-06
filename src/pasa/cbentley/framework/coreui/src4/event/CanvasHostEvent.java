package pasa.cbentley.framework.coreui.src4.event;

import pasa.cbentley.byteobjects.src4.core.ByteObject;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.framework.coreui.src4.ctx.CoreUiCtx;
import pasa.cbentley.framework.coreui.src4.interfaces.ICanvasHost;
import pasa.cbentley.framework.coreui.src4.interfaces.IHostEvents;
import pasa.cbentley.framework.coreui.src4.tech.IInput;

/**
 * An action on the UI of the {@link ICanvasHost} is made.
 * <br>
 * @author Charles Bentley
 *
 */
public class CanvasHostEvent extends BEvent {

   private int         actionType;

   private ICanvasHost canvasHot;

   private int         h;

   public ByteObject   tech;

   private int         w;

   private int         x;

   private int         y;

   public CanvasHostEvent(CoreUiCtx fc, int actionType, ICanvasHost canvasHot) {
      super(fc);
      this.type = IInput.TYPE_3_CANVAS;
      this.actionType = actionType;
      this.canvasHot = canvasHot;
   }

   /**
    * 
    * @return
    */
   public int getActionType() {
      return actionType;
   }

   public ICanvasHost getCanvasHot() {
      return canvasHot;
   }

   public int getH() {
      return h;
   }

   public String getUserLineString() {
      String str = "";
      if (actionType == IHostEvents.ACTION_3_RESIZED) {
         str = "Canvas resized to " + w + " " + h;
      } else if (actionType == IHostEvents.ACTION_2_MOVED) {
         str = "Canvas moved to " + x + " " + y;
      } else {
         str = "canvas " + actionType;
      }
      return str;
   }

   public int getW() {
      return w;
   }

   public int getX() {
      return x;
   }

   public int getY() {
      return y;
   }

   public void setH(int h) {
      this.h = h;
   }

   public void setW(int w) {
      this.w = w;
   }

   public void setX(int x) {
      this.x = x;
   }

   public void setY(int y) {
      this.y = y;
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, "CanvasHostEvent");
      dc.append(" " + x + "," + y);
      super.toString(dc.sup());
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "CanvasHostEvent");
      dc.append(" " + x + "," + y);
      super.toString1Line(dc.sup1Line());
   }
   //#enddebug
}
