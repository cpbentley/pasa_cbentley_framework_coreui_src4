package pasa.cbentley.framework.coreui.src4.ctx;

import pasa.cbentley.byteobjects.src4.ctx.IConfigBO;
import pasa.cbentley.framework.coreui.src4.tech.ITechHostUI;

/**
 * {@link ITechHostUI}
 * 
 * Not all values in the config goes to the {@link IBOCtxSettingsCoreUI}
 * 
 * @author Charles Bentley
 *
 */
public interface IConfigCoreUI extends IConfigBO {

   /**
    * 
    * @return
    */
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
