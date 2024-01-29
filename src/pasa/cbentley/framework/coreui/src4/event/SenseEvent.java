package pasa.cbentley.framework.coreui.src4.event;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.utils.StringUtils;
import pasa.cbentley.framework.coreui.src4.ctx.CoreUiCtx;
import pasa.cbentley.framework.coreui.src4.ctx.ToStringStaticCoreUi;
import pasa.cbentley.framework.coreui.src4.interfaces.IExternalDevice;
import pasa.cbentley.framework.coreui.src4.interfaces.ITechSenses;
import pasa.cbentley.framework.coreui.src4.tech.IInput;

/**
 * 
 * Sensor is of type {@link IInput#DEVICE_7_SENSOR}
 * <br>
 * <br>
 * Sensor Type:<br>
 * <li> {@link ITechSenses#GESTURE_TYPE_05_SHAKE}
 * <li> {@link ITechSenses#GESTURE_TYPE_08_LIGHT}
 * <li> {@link ITechSenses#GESTURE_TYPE_09_VOICE}
 * <li> {@link ITechSenses#GESTURE_TYPE_10_LOCATION} 
 * 
 * <br>
 * <br>
 * Subtype
 * @author Charles Bentley
 *
 */
public class SenseEvent extends DeviceEvent {
   /**
    * <li> {@link ITechSenses#GESTURE_TYPE_05_SHAKE}
    * <li> {@link ITechSenses#GESTURE_TYPE_08_LIGHT}
    * <li> {@link ITechSenses#GESTURE_TYPE_09_VOICE}
    * <li> {@link ITechSenses#GESTURE_TYPE_10_LOCATION}
    * 
    */
   private int     sensorType;

   /**
    * 
    */
   private int     sensorSubType;

   private int     intValue;

   /**
    * 
    */
   private float[] values;

   /**
    * 
    * @param fc
    * @param sensorType
    */
   public SenseEvent(CoreUiCtx fc, int sensorType) {
      super(fc, IInput.DEVICE_7_SENSOR, 0, IInput.MOD_4_SENSED, 0);
      this.sensorType = sensorType;
   }

   public SenseEvent(CoreUiCtx fc, int sensorType, int sensorSubType, int deviceID) {
      super(fc, IInput.DEVICE_7_SENSOR, deviceID, IInput.MOD_4_SENSED, 0);
      this.sensorType = sensorType;
      this.sensorSubType = sensorSubType;
   }

   public void setSubType(int subType) {
      this.sensorSubType = subType;
   }

   /**
    * For Light
    * <li> 
    * @return
    */
   public int getSubType() {
      return sensorSubType;
   }

   public int getSensorType() {
      return sensorType;
   }

   public String getUserLineString() {
      if (sensorType == ITechSenses.GESTURE_TYPE_12_DEVICE) {
         IExternalDevice ed = (IExternalDevice) this.getParamO1();
         String padName = ed.getName() + " #" + deviceID;
         if (sensorSubType == ITechSenses.DEVICE_CONNECTED) {
            padName += " connected";
         } else if (sensorSubType == ITechSenses.DEVICE_DISCONNECTED) {
            padName += " disconnected";
         }
         return padName;
      }
      return "Sense " + ToStringStaticCoreUi.toStringSense(sensorType);
   }

   public float getValue() {
      return values[0];
   }

   public float[] getValues() {
      return values;
   }

   public void setValue(float f) {
      values = new float[] { f };
   }

   public void setValues(float[] values) {
      this.values = values;
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, "SenseEvent");
      dc.appendVarWithSpace("Sub", ToStringStaticCoreUi.toStringSense(sensorType));
      if (values != null) {
         for (int i = 0; i < values.length; i++) {
            dc.nl();
            dc.append(StringUtils.prettyFloat(values[i]));
         }
      }
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "SenseEvent");
      dc.appendVarWithSpace("Sub", ToStringStaticCoreUi.toStringSense(sensorType));
   }
   //#enddebug

   public int getIntValue() {
      return intValue;
   }

   /**
    * also sets it as a float
    * @param intValue
    */
   public void setIntValue(int intValue) {
      this.intValue = intValue;
      setValue(intValue);
   }
}
