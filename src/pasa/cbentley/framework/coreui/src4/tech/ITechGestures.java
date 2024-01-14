package pasa.cbentley.framework.coreui.src4.tech;

import pasa.cbentley.byteobjects.src4.core.interfaces.IByteObject;
import pasa.cbentley.framework.coreui.src4.event.GestureArea;

/**
 * 
 * 
 * 
 * @author Charles Bentley
 *
 */
public interface ITechGestures extends IByteObject {
   
   public static final int FLAG_08_IS_DO_DISTANCE_MOD             = 1 << 7;

   /**
    * When logic gesturing, adapt the algo
    */
   public static final int FLAG_09_IS_LOGIC_GESTURING             = 1 << 8;

   /**
    * Tells that this dimension is included in the gesture
    */
   public static final int FLAG_10_IS_GESTURING                   = 1 << 9;

   public static final int FLAG_11_IS_USED                        = 1 << 10;

   /**
    * Set to true when the boundary has been reached
    */
   public static final int FLAG_13_VAL_IS_FINISHED                = 1 << 12;

   public static final int FLAG_14_VAL_DO_IT                      = 1 << 13;

   /**
    * When not set, integer represents something else, like a grid definition
    */
   public static final int FLAGG_32_POSITION_ID                   = 1 << 31;

   public static final int FLAGG_01_OUTSIDE_X_LEFT                = 1 << 0;

   /**
    * set when inside single 
    */
   public static final int FLAGG_02_INSIDE_X                      = 1 << 1;

   public static final int FLAGG_03_OUTSIDE_X_RIGHT               = 1 << 2;

   public static final int FLAGG_04_OUTSIDE_Y_TOP                 = 1 << 3;

   public static final int FLAGG_05_INSIDE_Y                      = 1 << 4;

   public static final int FLAGG_06_OUTSIDE_Y_BOTTOM              = 1 << 5;

   /**
    * 
    */
   public static final int FLAGG_07_INSIDE_X_21                   = 1 << 6;

   public static final int FLAGG_08_INSIDE_X_22                   = 1 << 7;

   /**
    * 1st of the 3 blocks
    */
   public static final int FLAGG_09_INSIDE_X_31                   = 1 << 8;

   /**
    * 2nd of the 3 blocks
    */
   public static final int FLAGG_10_INSIDE_X_32                   = 1 << 9;

   /**
    * 3rd of the 3 blocks
    */
   public static final int FLAGG_11_INSIDE_X_33                   = 1 << 10;

   public static final int FLAGG_12_INSIDE_Y_21                   = 1 << 11;

   public static final int FLAGG_13_INSIDE_Y_22                   = 1 << 12;

   public static final int FLAGG_14_INSIDE_Y_31                   = 1 << 13;

   public static final int FLAGG_15_INSIDE_Y_32                   = 1 << 14;

   public static final int FLAGG_16_INSIDE_Y_33                   = 1 << 15;

   /**
    * Flag telling on the boundary of X.
    */
   public static final int FLAGG_17_BOUNDARY_X                    = 1 << 16;

   /**
    * 
    */
   public static final int FLAGG_18_BOUNDARY_X_22                 = 1 << 17;

   public static final int FLAGG_19_BOUNDARY_X_32                 = 1 << 18;

   public static final int FLAGG_20_BOUNDARY_X_33                 = 1 << 19;

   /**
    * Simply removes dragged drawable
    */
   public static final int GESTURE_0_NONE                         = 0;

   /**
    * Trail stops are boundary.
    * <br>
    * {@link ITechGestures#GESTURE_OFFSET_10_BOUNDARY_START}
    * {@link ITechGestures#GESTURE_OFFSET_11_BOUNDARY_END}
    * 
    */
   public static final int GESTURE_1_BOUNDARY                     = 1;

   /**
    * Trail bounces at boundaries.
    */
   public static final int GESTURE_2_BOUNCE                       = 2;

   public static final int GESTURE_BASIC_SIZE                     = 16;

   /**
    * Trigger will match any slide, pinch
    */
   public static final int GESTURE_DIR_00_ANY                     = 0;

   /**
    * Up or Down
    */
   public static final int GESTURE_DIR_01_VERTICAL                = 1;

   /**
    * Horizontal slides, pinch, flinch only
    */
   public static final int GESTURE_DIR_02_HORIZONTAL              = 2;

   /**
   * Starting from the pressed point
   */
   public static final int GESTURE_DIR_03_TOP                     = 3;

   public static final int GESTURE_DIR_04_BOT                     = 4;

   public static final int GESTURE_DIR_05_LEFT                    = 5;

   public static final int GESTURE_DIR_06_RIGHT                   = 6;

   public static final int GESTURE_DIR_07_TOP_LEFT                = 7;

   public static final int GESTURE_DIR_08_TOP_RIGHT               = 8;

   public static final int GESTURE_DIR_09_BOT_LEFT                = 9;

   /**
    * Equivalent SouthWest
    */
   public static final int GESTURE_DIR_10_BOT_RIGHT               = 10;

   /**
    * 
    */
   public static final int GESTURE_DIR_11_TOP_90_LEFT             = 11;

   public static final int GESTURE_DIR_12_TOP_90_RIGHT            = 12;

   public static final int GESTURE_DIR_13_BOT_90_LEFT             = 13;

   public static final int GESTURE_DIR_14_BOT_90_RIGHT            = 14;

   public static final int GESTURE_DIR_15_LEFT_90_TOP             = 15;

   public static final int GESTURE_DIR_16_LEFT_90_BOT             = 16;

   public static final int GESTURE_DIR_17_RIGHT_90_TOP            = 17;

   public static final int GESTURE_DIR_18_RIGHT_90_BOT            = 18;

   /**
    * A pinch is actually 2 pointers pressed and
    * one drag event.
    * When a pointer is dragged in a pinch gesture, it computes
    * its position. a distance between the its original position.
    * <br>
    * When a command is linked to any kind of pinches, it will get fired
    * whenever a pinch is detected. pointer start positions, current positions
    * and distance are included in the trigger.
    * <br>
    * <br>
    * When a pointer is pressed, and moved, it is a drag..
    * then when another pointer is pressed, distance is computed
    * and any move create a pinch gesure.
    * <br>
    * <br>
    * <br>
    * When resizing, pinch starts, size value is 100.
    * as pinch evolves, the distance shrinks or grows.
    * the size value is modified proportionally
    * <br>
    * <br>
    * 
    */
   public static final int GESTURE_DOUBLE_TYPE_1_PINCH            = 6;

   /**
    * Dragging of 2 pointers together
    */
   public static final int GESTURE_DOUBLE_TYPE_2_DRAG_PARA        = 2;

   public static final int GESTURE_NOT_DETECTED                   = -1;

   public static final int GESTURE_UNKNOWN                        = -2;

   /**
    * 
    */
   public static final int GESTURE_OFFSET_00_FLAGS                = 0;

   /**
    * Value registered when press/starting event occurs. Usually the start increment of a {@link ScrollConfig}
    */
   public static final int GESTURE_OFFSET_01_BUSINESS_PRESSED     = 1;

   /**
    * Business value when the release event occurs.
    */
   public static final int GESTURE_OFFSET_02_BUSINESS_RELEASED    = 2;

   /**
    * Business value to be modified within the boundary defined by {@link ITechGestures#GESTURE_OFFSET_10_BOUNDARY_START}
    * and {@link ITechGestures#GESTURE_OFFSET_10_BOUNDARY_START}.
    * <br>
    * The Drawable calling the Gesture code is responsible to correctly initialize
    */
   public static final int GESTURE_OFFSET_03_BUSINESS_CURRENT     = 3;

   /**
    * Increment of the business value at each step in order to reach the boundary.
    * Usually this increment starts big and gets smaller with time for a smooth landing.
    * <br>
    * It modifies the {@link ITechGestures#GESTURE_OFFSET_03_BUSINESS_CURRENT} variable.
    * <br>
    * <br>
    * Positive or negative value
    */
   public static final int GESTURE_OFFSET_04_INCREMENT            = 4;

   /**
    * The minimum increment value. Increment will start high and slowly diminish towards
    * its minimum value.
    * Part of the increment function.
    */
   public static final int GESTURE_OFFSET_05_INCREMENT_MINIMUM    = 5;

   /**
    * 
    */
   public static final int GESTURE_OFFSET_06_MINIMUM_DISTANCE     = 6;

   /**
    * Magnifies the effect of distance press/release on the initial business increment.
    */
   public static final int GESTURE_OFFSET_07_DISTANCE_COEFFICIENT = 7;

   public static final int GESTURE_OFFSET_07_STEP                 = 7;

   /**
    * Coordinate of pointer when press event occurs
    */
   public static final int GESTURE_OFFSET_08_POINTER_PRESSED      = 8;

   /**
    * Coordinate of pointer when release event occurs
    */
   public static final int GESTURE_OFFSET_09_POINTER_RELEASED     = 9;

   /**
    * Boundary for a {@link ScrollConfig} is starting inc
    */
   public static final int GESTURE_OFFSET_10_BOUNDARY_START       = 10;

   /**
    * 
    */
   public static final int GESTURE_OFFSET_11_BOUNDARY_END         = 11;

   /**
    * Which function modifies the business value
    */
   public static final int GESTURE_OFFSET_12_FUNCTION_TYPE        = 12;

   public static final int GRID_TYPE_11_1x1                       = 11;

   public static final int GRID_TYPE_12_1x2                       = 12;

   public static final int GRID_TYPE_22_2x2                       = 22;

   public static final int GRID_TYPE_23_2x3                       = 23;

   public static final int GRID_TYPE_21_2x1                       = 21;

   public static final int GRID_TYPE_33_3x3                       = 33;

   public static final int GRID_TYPE_32_3x2                       = 32;

   public static final int GRID_TYPE_31_3x1                       = 31;

   public static final int GRID_TYPE_13_1x3                       = 13;

   /**
    * Gesture if fired normally
    */
   public static final int GESTURE_STATE_0_RUNNING                = 0;

   /**
    * Gesture was finished as planned
    */
   public static final int GESTURE_STATE_1_FINISHED               = 1;

   /**
    * Gesture was canceled
    */
   public static final int GESTURE_STATE_2_CANCELED               = 2;

   /**
    * Gesture is fired as a pre Gesture
    */
   public static final int GESTURE_STATE_3_RUNNING_PRE            = 3;

   /**
    * Ponctual Gesture
    * Called when the gesture key is released
    */
   public static final int GESTURE_TYPE_0_RAW                     = 0;

   /**
    * Drag of a single pointer in the google lingo.
    * We call is a slide. A slide is a drag with some constraints.
    * <br>
    * Continuous trigger.
    * <br>
    * 
    * A drag is a low level event.
    * <br>
    * Swipe/Slide on the screen.
    * <li> {@link ITechGestures#POINTER_SLIDE_1_UP}
    * A slide becomes a fling with speed
    * <br>
    * Any slide. Call backs 
    * <br>
    * <br>
    * A slide can also be used to create shapes gestures.
    * <br>
    */
   public static final int GESTURE_TYPE_1_DRAG_SLIDE              = 1;

   /**
    * A pointer slide in a straight line with a given speed
    * Follows
    * <li> {@link ITechGestures#GESTURE_DIR_00_ANY}
    * <li> {@link ITechGestures#GESTURE_DIR_01_VERTICAL}
    * <li> {@link ITechGestures#GESTURE_DIR_02_HORIZONTAL}
    * <li> {@link ITechGestures#GESTURE_DIR_03_TOP}
    * <li> {@link ITechGestures#GESTURE_DIR_04_BOT}
    * <li> {@link ITechGestures#GESTURE_DIR_05_LEFT}
    * <li> {@link ITechGestures#GESTURE_DIR_06_RIGHT}
    * <li> {@link ITechGestures#GESTURE_DIR_07_TOP_LEFT}
    * <li> {@link ITechGestures#GESTURE_DIR_08_TOP_RIGHT}
    * <li> {@link ITechGestures#GESTURE_DIR_09_BOT_LEFT}
    * <br>
    * <br>
    * May have to originates from a grid position in a {@link GestureArea}.
    * <br>
    */
   public static final int GESTURE_TYPE_2_SWIPE                   = 2;

   /**
    * Swipe that is fast accelerated at the end becoming a fling.
    * Same
    * The Fling can change direction.
    * Start with a left swipe then do a Up Fling.
    * <br>
    */
   public static final int GESTURE_TYPE_3_FLING                   = 3;

   /**
    * 
    */
   public static final int GESTURE_TIME_1_FAST                    = 1;

   public static final int GESTURE_TIME_0_NORMAL                  = 0;

   public static final int GESTURE_TIME_2_SLOW                    = 2;

   /**
    * Enters the {@link GestureArea} defined in the event.
    * <br>
    * Could be a grid position of the {@link GestureArea}.
    * <br>
    */
   public static final int GESTURE_TYPE_4_ENTER                   = 4;

   /**
    * The Bentley Framework introduce a new gesture.
    * Dragging the pointer from context
    * Press in Ctx ID
    * Dragged to Ctx And Released
    * <br>
    * Context ID are defined statically.
    * <br>
    * Enable a visual command pad of 9 squares. Each squares is a context.
    * Press one context and drag to another.
    * Releasing in ctx id x, may fire up a command. 
    * 
    * To cancel move away from pad and release pointer.
    * 
    */
   public static final int GESTURE_TYPE_5_CONTEXT_HOP             = 5;

   /**
    * Exits the {@link GestureArea} defined in the event
    */
   public static final int GESTURE_TYPE_5_EXIT                    = 5;

   /**
    * A Gesture as a serie of path position.
    * <li> 
    */
   public static final int GESTURE_TYPE_6_PATH                    = 6;

   /**
    * Gesture where pointer is slide in a direction and come back
    * to original position
    */
   public static final int GESTURE_TYPE_7_ALLER_RETOUR            = 7;

   /**
    * First look for 
    * <li> {@link ITechGestures#GESTURE_TYPE_7_ALLER_RETOUR}
    * <li> {@link ITechGestures#GESTURE_TYPE_3_FLING}
    * <li> {@link ITechGestures#GESTURE_TYPE_2_SWIPE}
    * 
    */
   public static final int GESTURE_TYPE_8_SWINGS                  = 8;

   /**
    * Identifies the grid sub area 1.1 of any grid
    */
   public static final int GRID_X1_Y1                             = FLAGG_09_INSIDE_X_31 | FLAGG_14_INSIDE_Y_31;

   public static final int GRID_X1_Y2                             = FLAGG_09_INSIDE_X_31 | FLAGG_15_INSIDE_Y_32;

   public static final int GRID_X1_Y3                             = FLAGG_09_INSIDE_X_31 | FLAGG_16_INSIDE_Y_33;

   public static final int GRID_X2_Y1                             = FLAGG_10_INSIDE_X_32 | FLAGG_14_INSIDE_Y_31;

   public static final int GRID_X2_Y2                             = FLAGG_10_INSIDE_X_32 | FLAGG_15_INSIDE_Y_32;

   public static final int GRID_X2_Y3                             = FLAGG_10_INSIDE_X_32 | FLAGG_16_INSIDE_Y_33;

   public static final int GRID_X3_Y1                             = FLAGG_11_INSIDE_X_33 | FLAGG_14_INSIDE_Y_31;

   public static final int GRID_X3_Y2                             = FLAGG_11_INSIDE_X_33 | FLAGG_15_INSIDE_Y_32;

   /**
    * Identifies the grid sub area 3.3 of any grid with at least 3 on X and Y
    */
   public static final int GRID_X3_Y3                             = FLAGG_11_INSIDE_X_33 | FLAGG_16_INSIDE_Y_33;

   public static final int MAX_GESTURE                            = 15;

}
