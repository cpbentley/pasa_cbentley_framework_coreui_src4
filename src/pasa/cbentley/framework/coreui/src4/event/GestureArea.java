package pasa.cbentley.framework.coreui.src4.event;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IStringable;

/**
 * Manage a grid area and their extra pixels.
 * A sub class will make it more specific.
 * <br>
 * @author Charles Bentley
 *
 */
public class GestureArea implements IStringable {

   public static boolean isInside(int x, int y, int rx, int ry, int rw, int rh) {
      if (x <= rx && y <= ry) {
         if (rx < x + rw && ry < y + rh) {
            return true;
         }
      }
      return false;

   }

   public int  h;

   private int h2;

   private int h3;

   public int  w;

   public int  x;

   public int  y;

   public GestureArea() {

   }

   public GestureArea(int x, int y, int w, int h) {
      this.x = x;
      this.y = y;
      this.w = w;
      this.h = h;
   }
   public int getH() {
      return h;
   }
   public int getW() {
      return w;
   }
   /**
    * Does the inside take into account the last line?
    * @param x
    * @param y
    * @return
    */
   public boolean isInside(int x, int y) {
      if (this.x <= x && this.y <= y) {
         if (x < this.x + w && y < this.y + h) {
            return true;
         }
      }
      return false;
   }

   //#mdebug
   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "GestureArea");
      dc.append(" x=" + x + " y=" + y + " w=" + w + " h=" + h);
   }


   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "GestureArea");
      dc.append(" x=" + x + " y=" + y + " w=" + w + " h=" + h);
   }
   //#enddebug

   public String toStringCompact() {
      return x + "," + y + " " + w + " " + h;
   }

   public UCtx toStringGetUCtx() {
      // TODO Auto-generated method stub
      return null;
   }
}
