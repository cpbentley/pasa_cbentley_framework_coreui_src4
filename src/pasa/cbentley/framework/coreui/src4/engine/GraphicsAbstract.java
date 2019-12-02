package pasa.cbentley.framework.coreui.src4.engine;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.framework.coreui.src4.ctx.CoreUiCtx;
import pasa.cbentley.framework.coreui.src4.interfaces.IGraphics;

/**
 * Base implementation of the Host Graphics
 * @author Charles Bentley
 *
 */
public abstract class GraphicsAbstract extends AbstractUITemplate implements IGraphics {
   
   protected GraphicsAbstract(CoreUiCtx fc) {
    super(fc);
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, "GraphicsAbstract");
   }
   
   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "GraphicsAbstract");
   }
   //#enddebug
}
