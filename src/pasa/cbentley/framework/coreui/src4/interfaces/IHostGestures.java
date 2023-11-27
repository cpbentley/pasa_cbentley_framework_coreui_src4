package pasa.cbentley.framework.coreui.src4.interfaces;

import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.framework.coreui.src4.tech.ITechFeaturesUI;

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
    * <li> {@link ITechFeaturesUI#HOST_FLAGY_1_LONGPRESS}
    * <li> {@link ISenses#GESTURE_TYPE_09_VOICE}
    * <br>
    * <br>
    * 
    * @param flag
    */
   public void enableGesture(int flag);

   public void disableGesture(int flag);

   public boolean isGestureSupported(int flag);
}
