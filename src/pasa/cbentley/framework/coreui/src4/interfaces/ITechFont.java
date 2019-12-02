package pasa.cbentley.framework.coreui.src4.interfaces;

public interface ITechFont {
   /**
    * 
    */
   public static final int STYLE_PLAIN       = 0;

   public static final int STYLE_BOLD        = 1;

   public static final int STYLE_ITALIC      = 2;

   public static final int STYLE_UNDERLINED  = 4;

   /**
    * Relative font size
    */
   public static final int SIZE_MEDIUM       = 0;

   public static final int SIZE_SMALL        = 8;

   public static final int SIZE_LARGE        = 16;

   public static final int SIZE_VERY_SMALL   = 32;

   public static final int SIZE_VERY_LARGE   = 64;

   /**
    * Font Face
    */
   public static final int FACE_SYSTEM       = 0;

   /**
    * Monospace font. i.e. all letters have the same width
    */
   public static final int FACE_MONOSPACE    = 32;

   /**
    * Proportional font: width varies
    */
   public static final int FACE_PROPORTIONAL = 64;
}
