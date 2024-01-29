package pasa.cbentley.framework.coreui.src4.ctx;

import pasa.cbentley.byteobjects.src4.ctx.ConfigAbstractBO;
import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.framework.coreui.src4.tech.ITechInputConstants;

public class ConfigCoreUiSettable extends ConfigAbstractBO implements IConfigCoreUI {

   protected int     allerAmplitude;

   protected String  iconPath;

   protected boolean isFullscreen = false;

   public ConfigCoreUiSettable(UCtx uc) {
      super(uc);
      allerAmplitude = ITechInputConstants.BF_ALLER_RETOUR_MIN_AMPLITUDE;
   }

   public int getAllerRetourMinAmplitudePixel() {
      return allerAmplitude;
   }

   public String getIconPath() {
      return iconPath;
   }

   public String getIconPathDefault() {
      return iconPath;
   }

   public boolean isFullscreen() {
      return isFullscreen;
   }

   public void setAllerAmplitude(int allerAmplitude) {
      this.allerAmplitude = allerAmplitude;
   }

   public void setFullscreen(boolean isFullscreen) {
      this.isFullscreen = isFullscreen;
   }

   public void setIconPath(String iconPath) {
      this.iconPath = iconPath;
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, ConfigCoreUiSettable.class, 35);
      toStringPrivate(dc);
      super.toString(dc.sup());
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, ConfigCoreUiSettable.class);
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   private void toStringPrivate(Dctx dc) {

   }

   //#enddebug

}
