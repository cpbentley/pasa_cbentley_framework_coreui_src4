package pasa.cbentley.framework.coreui.src4.ctx;

import pasa.cbentley.byteobjects.src4.ctx.ConfigAbstractBO;
import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.framework.coreui.src4.tech.IInputConstants;

public class ConfigCoreUiDefault extends ConfigAbstractBO implements IConfigCoreUI {

   protected boolean isFullscreen = false;

   protected String  iconPath;

   protected String  title;

   public ConfigCoreUiDefault(UCtx uc) {
      super(uc);
   }

   public String getIconPathDefault() {
      return iconPath;
   }

   public boolean isFullscreen() {
      return isFullscreen;
   }

   public int getAllerRetourMinAmplitudePixel() {
      return IInputConstants.BF_ALLER_RETOUR_MIN_AMPLITUDE;
   }

}
