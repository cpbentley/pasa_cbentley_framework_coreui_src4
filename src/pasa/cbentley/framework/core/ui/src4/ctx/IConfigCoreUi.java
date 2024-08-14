package pasa.cbentley.framework.core.ui.src4.ctx;

import pasa.cbentley.byteobjects.src4.ctx.IConfigBO;

/**
 * 
 * Not all values in the config goes to the {@link IBOCtxSettingsCoreUi}
 * 
 * @author Charles Bentley
 *
 */
public interface IConfigCoreUi extends IConfigBO {

   /**
    * TODO this value should be made relative to screensize/font size
    * @return
    */
   public int getAllerRetourMinAmplitudePixel();

   /**
    * The default icon to be used by Canvases
    * 
    * null if none
    * @return
    */
   public String getIconPathDefault();

   /**
    * 
    * @return
    */
   public boolean isFullscreen();
}
