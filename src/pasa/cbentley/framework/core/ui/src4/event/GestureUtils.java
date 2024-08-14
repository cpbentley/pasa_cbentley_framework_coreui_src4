package pasa.cbentley.framework.core.ui.src4.event;

import pasa.cbentley.core.src4.utils.BitUtils;
import pasa.cbentley.framework.core.ui.src4.ctx.CoreUiCtx;
import pasa.cbentley.framework.core.ui.src4.tech.ITechGestures;

public class GestureUtils implements ITechGestures {

   public static int computeXYGrid1x1Position(int x, int y, GestureArea ga) {
      int pos = FLAGG_32_POSITION_ID;
      pos = gridFlagX1(pos, x, ga);
      pos = gridFlagY1(pos, y, ga);
      return pos;
   }

   public static int computeXYGrid1x2Position(int x, int y, GestureArea ga) {
      int pos = FLAGG_32_POSITION_ID;
      pos = gridFlagX1(pos, x, ga);
      pos = gridFlagY2(pos, y, ga);
      return pos;
   }

   public static int computeXYGrid2x1Position(int x, int y, GestureArea ga) {
      int pos = FLAGG_32_POSITION_ID;
      pos = gridFlagX2(pos, x, ga);
      pos = gridFlagY1(pos, y, ga);
      return pos;
   }

   public static int computeXYGrid2x2Position(int x, int y, GestureArea ga) {
      int pos = FLAGG_32_POSITION_ID;
      pos = gridFlagX2(pos, x, ga);
      pos = gridFlagY2(pos, y, ga);
      return pos;
   }

   /**
    * Returns a flag position relative to a 2x3 grid on gesture area
    * @param x
    * @param y
    * @param GestureArea
    * @return
    */
   public static int computeXYGrid2x3Position(int x, int y, GestureArea ga) {
      int pos = FLAGG_32_POSITION_ID;
      pos = gridFlagX2(pos, x, ga);
      pos = gridFlagY3(pos, y, ga);
      return pos;
   }

   public static int computeXYGrid3x2Position(int x, int y, GestureArea ga) {
      int pos = FLAGG_32_POSITION_ID;
      pos = gridFlagX3(pos, x, ga);
      pos = gridFlagY2(pos, y, ga);
      return pos;
   }

   public static int computeXYGrid3x3Position(int x, int y, GestureArea ga) {
      int pos = FLAGG_32_POSITION_ID;
      pos = gridFlagX3(pos, x, ga);
      pos = gridFlagY3(pos, y, ga);
      return pos;
   }

   /**
    * Compute the position of x,y on the grid over the {@link GestureArea}.
    * <br>
    * What if x,y is right on a boundary? It is inside
    * <br>
    * returns
    * <li>{@link ITechGestures#GRID_TYPE_11_1x1}
    * <li>...
    * <li>{@link ITechGestures#GRID_TYPE_33_3x3}
    * <br>
    * <br>
    * 
    * @param x
    * @param y
    * @param ga
    * @param grid
    * @return
    */
   public static int computeXYGridPosition(int x, int y, GestureArea ga, int grid) {
      switch (grid) {
         case ITechGestures.GRID_TYPE_11_1x1:
            return computeXYGrid1x1Position(x, y, ga);
         case ITechGestures.GRID_TYPE_12_1x2:
            return computeXYGrid1x2Position(x, y, ga);
         case ITechGestures.GRID_TYPE_21_2x1:
            return computeXYGrid2x1Position(x, y, ga);
         case ITechGestures.GRID_TYPE_22_2x2:
            return computeXYGrid2x2Position(x, y, ga);
         case ITechGestures.GRID_TYPE_23_2x3:
            return computeXYGrid2x3Position(x, y, ga);
         case ITechGestures.GRID_TYPE_33_3x3:
            return computeXYGrid3x3Position(x, y, ga);
         case ITechGestures.GRID_TYPE_32_3x2:
            return computeXYGrid3x3Position(x, y, ga);
         default:
            throw new IllegalArgumentException("" + grid);
      }

   }

   private static int gridFlagX1(int pos, int x, GestureArea ga) {
      int xw = ga.x + ga.w;
      if (x < ga.x) {
         //outside left
         pos |= ITechGestures.FLAGG_01_OUTSIDE_X_LEFT;
      } else if (x > xw) {
         //outside right
         pos |= ITechGestures.FLAGG_03_OUTSIDE_X_RIGHT;
      } else {
         //inside x
         pos |= ITechGestures.FLAGG_02_INSIDE_X;
         if (x == ga.x || x == xw) {
            pos |= ITechGestures.FLAGG_17_BOUNDARY_X;
         }
      }
      return pos;
   }

   private static int gridFlagX2(int pos, int x, GestureArea ga) {
      int xw = ga.x + ga.w;
      int wu = ga.w / 2;
      if (x < ga.x) {
         //outside left
         pos |= ITechGestures.FLAGG_01_OUTSIDE_X_LEFT;
      } else if (x > xw) {
         //outside right
         pos |= ITechGestures.FLAGG_03_OUTSIDE_X_RIGHT;
      } else {
         //inside x
         pos |= ITechGestures.FLAGG_02_INSIDE_X;
         //do the inside flags
         if (x < ga.x + wu) {
            pos |= ITechGestures.FLAGG_07_INSIDE_X_21;
         } else {
            pos |= ITechGestures.FLAGG_08_INSIDE_X_22;
            if (x == ga.x + wu) {
               pos |= ITechGestures.FLAGG_18_BOUNDARY_X_22;
            }
         }
      }
      return pos;
   }

   static int gridFlagX3(int pos, int x, GestureArea ga) {
      int xw = ga.x + ga.w;
      int wu = ga.w / 3;
      if (x < ga.x) {
         //outside left
         pos |= ITechGestures.FLAGG_01_OUTSIDE_X_LEFT;
      } else if (x > xw) {
         //outside right
         pos |= ITechGestures.FLAGG_03_OUTSIDE_X_RIGHT;
      } else {
         //inside x
         pos |= ITechGestures.FLAGG_02_INSIDE_X;
         //do the inside flags
         if (x < ga.x + wu) {
            pos |= ITechGestures.FLAGG_09_INSIDE_X_31;
         } else if (x > xw - wu) {
            pos |= ITechGestures.FLAGG_11_INSIDE_X_33;
         } else {
            pos |= ITechGestures.FLAGG_10_INSIDE_X_32;
         }
      }
      return pos;
   }

   private static int gridFlagY1(int pos, int y, GestureArea ga) {
      int yh = ga.y + ga.h;
      if (y < ga.y) {
         //outside top
         pos |= ITechGestures.FLAGG_04_OUTSIDE_Y_TOP;
      } else if (y > yh) {
         //outside bottom
         pos |= ITechGestures.FLAGG_06_OUTSIDE_Y_BOTTOM;
      } else {
         //inside y
         pos |= ITechGestures.FLAGG_05_INSIDE_Y;
      }
      return pos;
   }

   private static int gridFlagY2(int pos, int y, GestureArea ga) {
      int yh = ga.y + ga.h;
      int hu = ga.h / 2;
      if (y < ga.y) {
         //outside top
         pos |= ITechGestures.FLAGG_04_OUTSIDE_Y_TOP;
      } else if (y > yh) {
         //outside bottom
         pos |= ITechGestures.FLAGG_06_OUTSIDE_Y_BOTTOM;
      } else {
         //inside y
         pos |= ITechGestures.FLAGG_05_INSIDE_Y;
         if (y < ga.y + hu) {
            pos |= ITechGestures.FLAGG_12_INSIDE_Y_21;
         } else {
            pos |= ITechGestures.FLAGG_13_INSIDE_Y_22;
         }
      }
      return pos;
   }

   private static int gridFlagY3(int pos, int y, GestureArea ga) {
      int yh = ga.y + ga.h;
      int hu = ga.h / 3;
      if (y < ga.y) {
         //outside top
         pos |= ITechGestures.FLAGG_04_OUTSIDE_Y_TOP;
      } else if (y > yh) {
         //outside bottom
         pos |= ITechGestures.FLAGG_06_OUTSIDE_Y_BOTTOM;
      } else {
         //inside y
         pos |= ITechGestures.FLAGG_05_INSIDE_Y;
         if (y < ga.y + hu) {
            pos |= ITechGestures.FLAGG_14_INSIDE_Y_31;
         } else if (y > yh - hu) {
            pos |= ITechGestures.FLAGG_16_INSIDE_Y_33;
         } else {
            pos |= ITechGestures.FLAGG_15_INSIDE_Y_32;
         }
      }
      return pos;
   }

   public static boolean isInside(GesturePointer gp, int x, int y, int w, int h) {
      return GestureArea.isInside(gp.getX(), gp.getY(), x, y, w, h);
   }

   public static boolean isInside(int pos) {
      return BitUtils.hasFlag(pos, ITechGestures.FLAGG_02_INSIDE_X) && BitUtils.hasFlag(pos, ITechGestures.FLAGG_05_INSIDE_Y);
   }

   /**
    * Does the pos flags matches the dir value
    * <li> {@link ITechGestures#GESTURE_DIR_00_ANY} will return ture
    * <li> {@link ITechGestures#GESTURE_DIR_01_VERTICAL} will return true when pos is top or bottom
    * <br>
    * Position are the flags
    * @param pos the position to match {@link ITechGestures#FLAGG_07_INSIDE_X_21}
    * @param dir the expected direction, or an exit position
    * @return
    */
   public static boolean isMatchPath(int pos, int dir) {
      //
      if (BitUtils.hasFlag(dir, ITechGestures.FLAGG_32_POSITION_ID)) {
         return pos == dir;
      }
      if (dir == GESTURE_DIR_00_ANY) {
         return true;
      }
      if (dir != 0) {
         //on which boundary we cross.. depends on the grid
         //difference between direction and position
         if (dir == ITechGestures.GESTURE_DIR_01_VERTICAL) {
            //vertical exit means bot or top
            return BitUtils.hasFlag(pos, FLAGG_04_OUTSIDE_Y_TOP) || BitUtils.hasFlag(pos, FLAGG_06_OUTSIDE_Y_BOTTOM);
         } else if (dir == ITechGestures.GESTURE_DIR_02_HORIZONTAL) {
            return BitUtils.hasFlag(pos, FLAGG_01_OUTSIDE_X_LEFT) || BitUtils.hasFlag(pos, FLAGG_03_OUTSIDE_X_RIGHT);
         } else if (dir == ITechGestures.GESTURE_DIR_03_TOP) {
            //exits on top
            return BitUtils.hasFlag(pos, FLAGG_04_OUTSIDE_Y_TOP);
         } else if (dir == ITechGestures.GESTURE_DIR_04_BOT) {
            return BitUtils.hasFlag(pos, FLAGG_06_OUTSIDE_Y_BOTTOM);
         } else if (dir == ITechGestures.GESTURE_DIR_05_LEFT) {
            return BitUtils.hasFlag(pos, FLAGG_01_OUTSIDE_X_LEFT);
         } else if (dir == ITechGestures.GESTURE_DIR_06_RIGHT) {
            return BitUtils.hasFlag(pos, FLAGG_03_OUTSIDE_X_RIGHT);
         } else if (dir == ITechGestures.GESTURE_DIR_07_TOP_LEFT) {
            //exits on top left?
            return BitUtils.hasFlag(pos, FLAGG_04_OUTSIDE_Y_TOP);
         }
      }

      return false;
   }

   /**
    * Does the actualPosition matches the matcher.
    * <br>
    * We want a top entry to be valid whatever the grid used to compute pos
    * <br>
    * 
    * @param actualPosition
    * @param matcher
    * @return
    */
   public static boolean isMatchPosition(int actualPosition, int matcher) {
      if (actualPosition == matcher) {
         return true;
      } else {
         return false;
      }
   }

   public static String toStringFlag(int pos) {
      if (BitUtils.hasFlag(pos, FLAGG_32_POSITION_ID)) {
         String str = "";
         if (BitUtils.hasFlag(pos, FLAGG_01_OUTSIDE_X_LEFT)) {
            str += "OutsideXLeft";
         }
         if (BitUtils.hasFlag(pos, FLAGG_02_INSIDE_X)) {
            str += "InX";
         }
         if (BitUtils.hasFlag(pos, FLAGG_03_OUTSIDE_X_RIGHT)) {
            str += "OutXRight";
         }
         if (BitUtils.hasFlag(pos, FLAGG_04_OUTSIDE_Y_TOP)) {
            str += "OutYTop";
         }
         if (BitUtils.hasFlag(pos, FLAGG_05_INSIDE_Y)) {
            str += "InY";
         }
         if (BitUtils.hasFlag(pos, FLAGG_06_OUTSIDE_Y_BOTTOM)) {
            str += "OutYBot";
         }
         if (BitUtils.hasFlag(pos, FLAGG_07_INSIDE_X_21)) {
            str += "X21";
         }
         if (BitUtils.hasFlag(pos, FLAGG_08_INSIDE_X_22)) {
            str += "X22";
         }
         if (BitUtils.hasFlag(pos, FLAGG_09_INSIDE_X_31)) {
            str += "X31";
         }
         if (BitUtils.hasFlag(pos, FLAGG_10_INSIDE_X_32)) {
            str += "X32";
         }
         if (BitUtils.hasFlag(pos, FLAGG_11_INSIDE_X_33)) {
            str += "X33";
         }
         if (BitUtils.hasFlag(pos, FLAGG_12_INSIDE_Y_21)) {
            str += "Y21";
         }
         if (BitUtils.hasFlag(pos, FLAGG_13_INSIDE_Y_22)) {
            str += "Y22";
         }
         if (BitUtils.hasFlag(pos, FLAGG_14_INSIDE_Y_31)) {
            str += "Y31";
         }
         if (BitUtils.hasFlag(pos, FLAGG_15_INSIDE_Y_32)) {
            str += "Y32";
         }
         if (BitUtils.hasFlag(pos, FLAGG_15_INSIDE_Y_32)) {
            str += "Y32";
         }
         if (BitUtils.hasFlag(pos, FLAGG_16_INSIDE_Y_33)) {
            str += "Y32";
         }
         if (BitUtils.hasFlag(pos, FLAGG_17_BOUNDARY_X)) {
            str += "Bx";
         }
         if (BitUtils.hasFlag(pos, FLAGG_18_BOUNDARY_X_22)) {
            str += "Bx22";
         }
         if (BitUtils.hasFlag(pos, FLAGG_19_BOUNDARY_X_32)) {
            str += "Bx32";
         }
         if (BitUtils.hasFlag(pos, FLAGG_20_BOUNDARY_X_33)) {
            str += "Bx33";
         }
         return str;
      } else {
         return "Not a Position";
      }
   }

   protected final CoreUiCtx cuc;

   public GestureUtils(CoreUiCtx cuc) {
      this.cuc = cuc;
   }

}
