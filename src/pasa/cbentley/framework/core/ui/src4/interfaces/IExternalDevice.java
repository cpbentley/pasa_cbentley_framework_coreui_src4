package pasa.cbentley.framework.core.ui.src4.interfaces;

import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.framework.core.ui.src4.tech.IInput;

/**
 * Allows the framework to query static state of an external device such a a gamepad a mouse 
 * or a keyboard
 * @author Charles Bentley
 *
 */
public interface IExternalDevice extends IStringable {

   /**
    * 
    * @param button
    * @return
    */
   public boolean isStart(int button);

   public String getName();

   /**
    * <li> {@link IInput#DEVICE_0_KEYBOARD}
    * @param buttonID
    * @return
    */
   public String getNameButton(int buttonID);

   /**
    * <li> {@link IInput#DEVICE_0_KEYBOARD}
    * <li> {@link IInput#DEVICE_1_MOUSE}
    * <li> {@link IInput#DEVICE_2_GAMEPAD}
    * <li> {@link IInput#DEVICE_3_FINGER}
    * <li> {@link IInput#DEVICE_4_SCREEN}
    * <li> {@link IInput#DEVICE_6_POINTER}
    * <li> {@link IInput#DEVICE_7_SENSOR}
    * 
    * @return
    */
   public int getType();
}
