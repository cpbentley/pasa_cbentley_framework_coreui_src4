package pasa.cbentley.framework.coreui.src4.ctx;

import pasa.cbentley.byteobjects.src4.ctx.ConfigAbstractBO;
import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.framework.coreui.src4.tech.ITechInputConstants;

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
      return ITechInputConstants.BF_ALLER_RETOUR_MIN_AMPLITUDE;
   }
   
   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, ConfigCoreUiDefault.class, 35);
      toStringPrivate(dc);
      super.toString(dc.sup());
   }

   private void toStringPrivate(Dctx dc) {
      
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, ConfigCoreUiDefault.class);
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   //#enddebug
   


}
