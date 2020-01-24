package pasa.cbentley.framework.coreui.src4.ctx;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.framework.coreui.src4.interfaces.IConfigUI;

public class CoreUICfg implements IConfigUI {

  
   protected boolean    isFullscreen = false;

   protected String     iconPath;

   protected String     title;

   protected final UCtx uc;

   public CoreUICfg(UCtx uc) {
      this.uc = uc;

   }

   public String getIconPathDefault() {
      return iconPath;
   }

   public boolean isFullscreen() {
      return isFullscreen;
   }
}
