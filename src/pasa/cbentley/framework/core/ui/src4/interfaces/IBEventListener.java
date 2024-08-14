package pasa.cbentley.framework.core.ui.src4.interfaces;

import pasa.cbentley.core.src4.stator.IStatorable;
import pasa.cbentley.framework.core.ui.src4.event.BEvent;

/**
 * 
 * @author Charles Bentley
 *
 */
public interface IBEventListener extends IStatorable {

   public void newEvent(BEvent e);
}
