package pasa.cbentley.framework.core.ui.src4.utils;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.framework.core.ui.src4.event.GestureArea;

public class CircleArea extends GestureArea {

   private int ww;
   private int hh;
   
   /**
    * 
    * @param x
    * @param y
    * @param rw
    * @param rh
    */
   public CircleArea(int x, int y, int rw, int rh) {
      this.x = x;
      this.y = y;
      this.w = rw;
      this.h = rh;
      ww = w * w;
      hh = h * h;
   }
   
   public boolean isInside(int x, int y) {
      int diffX = this.x - x;
      int diffY = this.y - y;
      int diffX2 = diffX * diffX;
      int diffY2 = diffY * diffY;
      if(ww == hh) {
         return diffX2 + diffY2 <= ww;
      } else {
         return (diffX2 / ww) + (diffY2 / hh) <= 1;
      }
   }
   
   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, CircleArea.class, 41);
      toStringPrivate(dc);
      super.toString(dc.sup());
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, CircleArea.class, 41);
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());

   }

   private void toStringPrivate(Dctx dc) {
      dc.appendVarWithSpace("ww", ww);
      dc.appendVarWithSpace("hh", hh);
   }
   //#enddebug
}
