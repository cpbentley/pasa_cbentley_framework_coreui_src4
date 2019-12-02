package pasa.cbentley.framework.coreui.src4.interfaces;

/**
 * Font interface for the Bentley framework.
 * <br>
 * Generic definitions are used to describe fonts. Specific implementations will do their best
 * to implement this specification.
 * <br>
 * Dynamic Font changes (size)
 * <br>
 * An application runs but fonts are too smal for the user, the application framework adapts font size
 * <li>at the framework System level without modifying the application
 * <li>at the Bentley framework system where all definitions are upped one grade. until all are maxed VERY LARGE
 * 
 * This is done at the creation level where the global font upgrade/downgrade is saved. Upon repaint, when the font is created
 * the size is upgraded.
 * <br>
 * 
 * TODO how do you provide total custom fonts? 
 * <br>
 * Can create a Font with any size from the host provided sizes and
 * the font faces and styles
 * 
 * @author Charles Bentley
 *
 */
public interface IMFont extends ITechFont {


   public int charsWidth(char[] c, int ofs, int len);

   public int charWidth(char ch);

   public int stringWidth(String s);

   public int substringWidth(String s, int offset, int length);

   public int getHeight();

   /**
    * Returns a pixel weight of the font.
    * <br>
    * usually a measure of the biggest char for proportional face.
    * <br>
    * Any char with monospace fonts
    * @return
    */
   public int getWidthWeigh();

   public boolean isSupported(int flag);

   public int getFace();

   public int getStyle();

   public int getSize();
}
