package pasa.cbentley.framework.coreui.src4.ctx;

import pasa.cbentley.core.src4.ctx.ACtx;
import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.framework.coreui.src4.interfaces.IFontFactory;
import pasa.cbentley.framework.coreui.src4.interfaces.IGraphics;
import pasa.cbentley.framework.coreui.src4.interfaces.IImage;
import pasa.cbentley.framework.coreui.src4.interfaces.IImageFactory;
import pasa.cbentley.framework.coreui.src4.interfaces.IMFont;

/**
 * Provides just the bare minimum UI
 * 
 * <li> {@link IMFont} and its factory {@link IFontFactory}
 * <li> {@link IImage} and its factory {@link IImageFactory}
 * <li> {@link IGraphics} the drawing interface
 * 
 * The context is independant from other framework contexts.
 * 
 * Implementation
 * 
 * @author Charles Bentley
 *
 */
public abstract class CoreUiCtx extends ACtx {

   public CoreUiCtx(UCtx uc) {
      super(uc);
   }

   public abstract IImageFactory getImageFactory();
   
   public abstract IFontFactory getFontFactory();
}
