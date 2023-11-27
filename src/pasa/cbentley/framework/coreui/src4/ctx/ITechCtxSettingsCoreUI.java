package pasa.cbentley.framework.coreui.src4.ctx;

import pasa.cbentley.byteobjects.src4.tech.ITechCtxSettings;
import pasa.cbentley.framework.coredraw.src4.interfaces.IGraphics;
import pasa.cbentley.framework.coredraw.src4.interfaces.ITechDrawer;
import pasa.cbentley.framework.coreui.src4.tech.ITechFeaturesUI;

/**
 * 
 * @author Charles Bentley
 *
 */
public interface ITechCtxSettingsCoreUI extends ITechCtxSettings {

   public static final int CTX_COREUI_BASIC_SIZE        = CTX_BASIC_SIZE + 5;

   public static final int CTX_COREUI_FLAG_1_FULLSCREEN = 1 << 0;
   
   /**
    * Enable drag drop support.
    * 
    * Host might not. see
    * {@link ITechFeaturesUI#HOST_FLAG_1_FILEDROP}
    */
   public static final int CTX_COREUI_FLAG_2_DRAG_DROP = 1 << 1;

   public static final int CTX_COREUI_OFFSET_01_FLAG1   = CTX_BASIC_SIZE;

}
