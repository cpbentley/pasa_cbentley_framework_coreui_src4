package pasa.cbentley.framework.coreui.src4.engine;

import pasa.cbentley.framework.coreui.src4.ctx.CoreUiCtx;
import pasa.cbentley.framework.coreui.src4.interfaces.IImage;

/**
 * J2SE bridge class for the {@link javax.microedition.lcdui.Image} class of MIDP 2.0.
 * <br>
 * <br>
 * @author Charles Bentley
 *
 */
public abstract class ImageAbstract extends AbstractUITemplate implements IImage {

   protected ImageAbstract(CoreUiCtx cac) {
      super(cac);
   }
}
