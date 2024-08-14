package pasa.cbentley.framework.core.ui.src4.ctx;

import pasa.cbentley.byteobjects.src4.core.interfaces.IBOCtxSettings;

/**
 * 
 * @author Charles Bentley
 *
 */
public interface IBOCtxSettingsCoreUi extends IBOCtxSettings {

   public static final int CTX_COREUI_BASIC_SIZE        = CTX_BASIC_SIZE + 5;

   public static final int CTX_COREUI_FLAG_1_FULLSCREEN = 1 << 0;

   /**
    * Enable drag drop support.
    * 
    * Host might not support it. TODO
    */
   public static final int CTX_COREUI_FLAG_2_DRAG_DROP  = 1 << 1;

   public static final int CTX_COREUI_OFFSET_01_FLAG1   = CTX_BASIC_SIZE;

}
