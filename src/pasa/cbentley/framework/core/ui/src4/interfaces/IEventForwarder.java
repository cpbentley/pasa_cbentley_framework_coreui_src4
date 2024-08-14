package pasa.cbentley.framework.core.ui.src4.interfaces;

import pasa.cbentley.framework.core.ui.src4.event.BEvent;

public interface IEventForwarder {

   /**
    * 
    * @param e
    * @param canvasAppli
    */
   public void eventForward(BEvent e, ICanvasAppli canvasAppli);

}
