package pasa.cbentley.framework.core.ui.src4.interfaces;

import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.framework.core.ui.src4.ctx.CoreUiCtx;

/**
 * Interface towards Gesture Services/
 * <br>
 * Raw sensors send raw data everytime a value is changed.
 * Gesture sensors monitor raw data and generate an event when a threhold is reached.
 * <br> 
 * <li> enable a Gesture/Sensor
 * <li> disble it
 * <li> Query capabilities
 * <br>
 * <br>
 * 
 * For the interface to use tools provided by the host, see {@link IHostUITools}
 * <br>
 * <br>
 * @author Charles Bentley
 *
 */
public interface IHostGestures extends IStringable {
   /**
    * Ask the Host listeners to start listening to the given Gestures
    * 
    * <li> {@link ITechSenses#GESTURE_TYPE_05_SHAKE}
    * <li> {@link ITechSenses#GESTURE_TYPE_07_MOVE}
    * <li> {@link ITechSenses#GESTURE_TYPE_09_VOICE}
    * <li> {@link ITechSenses#GESTURE_TYPE_10_LOCATION}
    * <li> {@link ITechSenses#GESTURE_TYPE_12_DEVICE}
    * <li> {@link ITechSenses#GESTURE_TYPE_11_COMPASS}
    * <br>
    * <br>
    * 
    * @param flag
    */
   public void enableGesture(int flag);

   /**
    * 
    * @param flag
    */
   public void disableGesture(int flag);

   /**
    * Implementation tells {@link CoreUiCtx} whether it supports the type of Gesture.
    * 
    * <li> {@link ITechSenses#GESTURE_TYPE_05_SHAKE}
    * <li> {@link ITechSenses#GESTURE_TYPE_07_MOVE}
    * <li> {@link ITechSenses#GESTURE_TYPE_09_VOICE}
    * <li> {@link ITechSenses#GESTURE_TYPE_10_LOCATION}
    * <li> {@link ITechSenses#GESTURE_TYPE_12_DEVICE}
    * <li> {@link ITechSenses#GESTURE_TYPE_11_COMPASS}
    * 
    * @param flag
    * @return
    */
   public boolean isGestureSupported(int flag);
}
