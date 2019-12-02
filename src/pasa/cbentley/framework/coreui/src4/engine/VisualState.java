package pasa.cbentley.framework.coreui.src4.engine;

/**
 * Encapsulates the visual elements of a Graphical Interface
 * <br>
 * Style?
 * @author Charles Bentley
 *
 */
public class VisualState {

   /**
    * index 
    */
   public int[] fontPoints;

   public String toString1Line() {
      String s = "";
      for (int i = 0; i < fontPoints.length; i++) {
         s += fontPoints[i] + " ";
      }
      return s;
   }
}
