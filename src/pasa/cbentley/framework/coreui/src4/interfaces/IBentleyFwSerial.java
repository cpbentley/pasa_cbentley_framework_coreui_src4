package pasa.cbentley.framework.coreui.src4.interfaces;

import pasa.cbentley.framework.coreui.src4.utils.ViewState;

/**
 * All objects that want to support the Bentley serialization framework 
 * @author Charles Bentley
 *
 */
public interface IBentleyFwSerial {

   
   /**
    * Model class and DB class will not serialize with this code.
    * <br>
    */
   public static final int SERIAL_TYPE_0_LIGHT = 0;
   /**
    * Serialize everything including databases,
    * Every single object gets an ID and is serialized.
    * Objects that don't implement this interface can be made serializable
    * using a custom code.
    * Usually 
    */
   public static final int SERIAL_TYPE_1_FULL = 1;
   
   /**
    * <li>Write the version of the class state.
    * <li>Write objects states
    * <br>
    * @param vs
    */
   public void setStateTo(ViewState vs);

   /**
    * <li>Read the version of the class state.
    * <li>Branch based on version or throw an {@link IllegalArgumentException}
    * <li>Set state
    * @param vs
    */
   public void setStateFrom(ViewState vs);
}
