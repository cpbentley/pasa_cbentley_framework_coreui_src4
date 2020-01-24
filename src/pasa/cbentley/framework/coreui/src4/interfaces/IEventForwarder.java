package pasa.cbentley.framework.coreui.src4.interfaces;

import pasa.cbentley.framework.coreui.src4.event.BEvent;

public interface IEventForwarder {

   /**
    * 
    * @param e
    * @param canvasAppli
    */
   public void eventForward(BEvent e, ICanvasAppli canvasAppli);

}
