package pasa.cbentley.framework.coreui.src4.ctx;

import pasa.cbentley.byteobjects.src4.ctx.IConfigBO;
import pasa.cbentley.framework.coreui.src4.tech.ITechFeaturesUI;

/**
 * {@link ITechFeaturesUI}
 * 
 * Not all values in the config goes to the {@link ITechCtxSettingsCoreUI}
 * 
 * @author Charles Bentley
 *
 */
public interface IConfigCoreUI extends IConfigBO {

   public boolean isFullscreen();

   /**
    * The default icon to be used by Canvases
    * 
    * null if none
    * @return
    */
   public String getIconPathDefault();

   /**
    * TODO this value should be made relative to screensize/font size
    * @return
    */
   public int getAllerRetourMinAmplitudePixel();
}
