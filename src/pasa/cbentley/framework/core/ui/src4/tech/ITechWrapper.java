package pasa.cbentley.framework.core.ui.src4.tech;

import pasa.cbentley.core.src4.interfaces.ITech;

public interface ITechWrapper extends ITech {

   /**
    * The wrapper decided by the launcher
    */
   int WRAPPER_TYPE_0_DEFAULT              = 0;
   /**
    * Wrapper should be an independant frame
    */
   int WRAPPER_TYPE_1_FRAME                = 1;
   /**
    * 
    */
   int WRAPPER_TYPE_2_CONTROLLED           = 2;
   int TCANVAS_ID_0_DEFAULT                = 0;
   int TCANVAS_ID_1_ROOT                   = 1;

}
