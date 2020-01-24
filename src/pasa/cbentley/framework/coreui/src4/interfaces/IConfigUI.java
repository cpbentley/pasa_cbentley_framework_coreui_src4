package pasa.cbentley.framework.coreui.src4.interfaces;

import pasa.cbentley.framework.coreui.src4.ctx.ITechCtxSettingsCoreUI;
import pasa.cbentley.framework.coreui.src4.tech.ITechUI;

/**
 * {@link ITechUI}
 * 
 * Not all values in the config goes to the {@link ITechCtxSettingsCoreUI}
 * 
 * @author Charles Bentley
 *
 */
public interface IConfigUI {


   public boolean isFullscreen();
   
   /**
    * The default icon to be used by Canvases
    * 
    * null if none
    * @return
    */
   public String getIconPathDefault();
}
