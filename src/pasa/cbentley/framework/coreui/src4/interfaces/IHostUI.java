package pasa.cbentley.framework.coreui.src4.interfaces;

import pasa.cbentley.byteobjects.src4.core.ByteObject;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.framework.coredraw.src4.interfaces.ITechFeaturesDraw;
import pasa.cbentley.framework.coreui.src4.ctx.CoreUiCtx;
import pasa.cbentley.framework.coreui.src4.tech.ITechHostUI;

public interface IHostUI extends IStringable, ITechHostUI {

   /**
    * Enable/Disable a feature. 
    * 
    * <li> {@link ITechHostUI#SUP_ID_28_ALWAYS_ON_TOP}
    * 
    * <li> {@link ITechHostCore#SUP_ID_20_CLIPBOARD}
    * 
    * <li> {@link ITechFeaturesDraw#SUP_ID_06_CUSTOM_FONTS}
    * <li> {@link ITechFeaturesDraw#SUP_ID_07_IMAGE_SCALING}
    * 
    * @param featureID
    * @param b
    * @return true if the change was successful
    */
   public boolean enableFeature(int featureID, boolean b);

   /**
    * 
    * @return
    */
   public CoreUiCtx getCUC();

   /**
    * Sets the Feature at the factory settings. overides and forces the reset of all user defined
    * <br>
    * <br>
    * @param featureID
    * @param b
    * @return
    */
   public boolean enableFeatureFactory(int featureID, boolean b);

   /**
    * 
    * @param dataID
    * @return
    */
   public float getHostFloat(int dataID);

   /**
    * Returns a configuration value from host framework context.
    * 
    * <li> {@link ITechHostCore#DATA_ID_11_FLING_SPEED_MAX}
    * <li> {@link ITechHostCore#DATA_ID_12_FLING_SPEED_MIN}
    * 
    * @param dataID
    * @return
    */
   public int getHostInt(int dataID);

   /**
    * Object class is defined by 
    * <li> {@link ITechHostCore#DATA_ID_OBJ_01_SCREENS}
    * @param dataID
    * @return
    */
   public Object getHostObject(int dataID);

   /**
    * Returns the data from the host as a String.
    * 
    * @param dataID
    * @return
    */
   public String getHostString(int dataID);

   /**
    * {@link IBOHost}
    * @return
    */
   public ByteObject getBOHostUI();

   /**
    * Query Host for support of features
    * <li> {@link ITechHostCore#SUP_ID_02_POINTERS}
    * <li> {@link ITechHostCore#SUP_ID_03_OPEN_GL}
    * <br>
    * <br>
    * 
    * @param supportID
    * @return
    */
   public boolean hasFeatureSupport(int supportID);

   /**
    * Return true when the feature is currently active/enabled.
    * 
    * <li> {@link ITechHostCore#SUP_04_WHEEL_EVENTS}
    * <li> {@link ITechHostCore#SUP_ID_04_ALIAS}
    * @param featureID
    * @return
    */
   public boolean isFeatureEnabled(int featureID);
}
