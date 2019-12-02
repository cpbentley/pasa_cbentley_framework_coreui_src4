package pasa.cbentley.framework.coreui.src4.interfaces;

import pasa.cbentley.framework.coreui.src4.engine.VisualState;

public interface IFontFactory {

   
   /**
    * Tries to increase the size of the all the fonts
    * @return
    */
   public VisualState fontSizeDecrease();

   /**
    * Caller must call the hooks for updating computations based on current font sizes.
    * <br>
    * <br>
    * 
    * @return
    */
   public VisualState fontSizeIncrease();

   public IMFont getDefaultFont();

   /**
    * Creates a Framework font.
    * Faces are
    * <li> {@link IMFont#FACE_MONOSPACE}
    * <li> {@link IMFont#FACE_MONOSPACE}
    * <li> {@link IMFont#FACE_MONOSPACE}
    * <br>
    * Styles are
    * <li> {@link IMFont#STYLE_BOLD}
    * <li> {@link IMFont#STYLE_ITALIC}
    * <li> {@link IMFont#STYLE_PLAIN}
    * <li> {@link IMFont#STYLE_UNDERLINED}
    * <br>
    * Sizes are
    * <li> {@link IMFont#SIZE_LARGE}
    * <li> {@link IMFont#SIZE_MEDIUM}
    * <li> {@link IMFont#SIZE_SMALL}
    * <li> {@link IMFont#SIZE_VERY_LARGE}
    * <br>
    * 
    * For a pure custom font with font points 
    * 
    * @param face
    * @param style
    * @param size
    * @return
    */
   public IMFont getFont(int face, int style, int size);

   /**
    * If Hosts does not support Font face, it will returns Default Font.
    * <br>
    * Font point are...
    * 
    * @param face
    * @param style
    * @param fontPoint
    * @return
    */
   public IMFont getFont(String face, int style, int fontPoint);

   public IMFont getFontDebug();

   /**
    * 
    * @param string
    * @return
    */
   public int getFontFaceID(String string);

   public String getFontName();

   public String[] getFontNames();

   /**
    * 
    * @return
    */
   public int[] getFontPoints();

   public float getFontScale(int size);

   /**
    * Returns the font that best fit the w and h according to sizeHint.
    * <br>
    * @param sizeHint {@link ISizer#SIZE_1_SMALLEST}
    * @param w
    * @param h
    * @return
    */
   public IMFont getFontScaled(int sizeHint, int w, int h);

   /**
    * Get the Pixel value of a scale value
    * <br>
    * <li>{@link ISizer#SIZE_1_SMALLEST}
    * <li>{@link ISizer#SIZE_2_SMALL}
    * <li>{@link ISizer#SIZE_3_MEDIUM}
    * <br>
    * <br>
    * For the scale id
    * <li>{@link ISizer#SCALE_0_PADDING}  Mostly used to find how much padding is possible
    * <li>{@link ISizer#SCALE_1_EXPO}
    * <br>
    * <br>
    * Return values may change when Font size or other events change the Host
    * dimensions.
    * <br>
    * Scales are managed by the Host config files and may dynamically changes if screen sizes
    * changes.
    * <br>
    * @param valu {@link ISizer#SIZE_1_SMALLEST}
    * @param fun
    * @return
    */
   public int getScalePixel(int valu, int fun);

   /**
    * Look up in the ressources for the font.
    * returns Face ID or -1 if not found
    * @param string
    * @return
    */
   public int loadCustomFont(String string);

   public void setFontName(String name);

   /**
    * Some Host might not be able to change Font size. in which case
    * the method returns
    */
   public void setFontRatio(int ratio, int etalon);
}
