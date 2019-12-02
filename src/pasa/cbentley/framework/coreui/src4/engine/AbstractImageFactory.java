package pasa.cbentley.framework.coreui.src4.engine;

import pasa.cbentley.framework.coreui.src4.ctx.CoreUiCtx;
import pasa.cbentley.framework.coreui.src4.interfaces.IImageFactory;

public abstract class AbstractImageFactory implements IImageFactory {

   protected final CoreUiCtx fc;

   public AbstractImageFactory(CoreUiCtx fc) {
      this.fc = fc;

   }
}
