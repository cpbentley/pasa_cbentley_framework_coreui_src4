package pasa.cbentley.framework.coreui.src4.engine;

import pasa.cbentley.core.src4.structs.IntToStrings;
import pasa.cbentley.framework.coreui.src4.ctx.CoreUiCtx;
import pasa.cbentley.framework.coreui.src4.interfaces.IFontFactory;
import pasa.cbentley.framework.coreui.src4.interfaces.IMFont;

public abstract class AbstractFontFactory implements IFontFactory {

   /**
    * In some frameworks like J2ME and android, font points are not used
    */
   protected int[]              fontPoints = null;

   protected final CoreUiCtx fc;

   public AbstractFontFactory(CoreUiCtx cac) {
      this.fc = cac;

   }

   /**
    * 
    * @param string
    * @return
    */
   public int getFontFaceID(String string) {
      if (customFonts == null) {
         return IMFont.FACE_SYSTEM;
      } else {
         int index = customFonts.getFirstStringIndex(string);
         if (index != -1) {
            return customFonts.ints[index];
         }
         return IMFont.FACE_SYSTEM;
      }
   }

   public IntToStrings customFonts;

   /**
    * Look up in the ressources for the font.
    * returns Face ID or -1 if not found
    * @param string
    * @return
    */
   public int loadCustomFont(String string) {
      return -1;
   }

   /**
    * Caller must call the hooks for updating current fonts in the GUI layout, i.e. it has to renew the
    * layout of all graphical components including cached.
    * <br>
    * 
    * @return
    */
   public VisualState fontSizeIncrease() {
      VisualState vs = new VisualState();
      for (int i = 0; i < fontPoints.length; i++) {
         fontPoints[i]++;
      }
      vs.fontPoints = fontPoints;
      clearFontCache();
      return vs;
   }

   public VisualState fontSizeDecrease() {
      VisualState vs = new VisualState();
      for (int i = 0; i < fontPoints.length; i++) {
         if (fontPoints[i] > 0)
            fontPoints[i]--;
      }
      //update all fonts
      vs.fontPoints = fontPoints;
      clearFontCache();
      return vs;
   }

   protected IMFont     z_refFont;

   protected IMFont     z_refFontDebug;

   /**
    * Cache of Fonts
    * <li> Face ID
    * <li> Style ID
    * <li> Size IDs
    */
   private IMFont[][][] fonts = new IMFont[3][4][5];

   public void load() {

   }

   public IMFont getDefaultFont() {
      if (z_refFont == null)
         z_refFont = getFont(IMFont.FACE_SYSTEM, IMFont.STYLE_PLAIN, IMFont.SIZE_LARGE);
      return z_refFont;
   }

   public IMFont getFontDebug() {
      if (z_refFontDebug == null)
         z_refFontDebug = getFont(IMFont.FACE_SYSTEM, IMFont.STYLE_BOLD, IMFont.SIZE_MEDIUM);
      return z_refFontDebug;
   }

   public void clearFontCache() {
      z_refFontDebug = null;
      z_refFont = null;
      for (int i = 0; i < fonts.length; i++) {
         for (int j = 0; j < fonts[i].length; j++) {
            for (int k = 0; k < fonts[i][j].length; k++) {
               fonts[i][j][k] = null;
            }
         }
      }
   }

   public IMFont getFontScaled(int sizeHint, int w, int h) {
      return getDefaultFont();
   }

   public void setFontFromCache(int face, int style, int size, IMFont font) {
      int idFace = 0;
      if (face == IMFont.FACE_PROPORTIONAL) {
         idFace = 1;
      } else if (face == IMFont.FACE_SYSTEM) {
         idFace = 2;
      }
      int idStyle = 0;
      if (style == IMFont.STYLE_PLAIN) {
         idStyle = 1;
      } else if (style == IMFont.STYLE_BOLD) {
         idStyle = 2;
      } else if (style == IMFont.STYLE_ITALIC) {
         idStyle = 3;
      }
      int idSize = 0;
      if (face == IMFont.SIZE_LARGE) {
         idSize = 1;
      } else if (size == IMFont.SIZE_MEDIUM) {
         idSize = 2;
      } else if (size == IMFont.SIZE_VERY_LARGE) {
         idSize = 4;
      } else if (size == IMFont.SIZE_VERY_SMALL) {
         idSize = 3;
      }
      fonts[idFace][idStyle][idSize] = font;
   }

   /**
    * Returns null if Font was not previously created in the current cycle.
    * @param face
    * @param style
    * @param size
    * @return
    */
   public IMFont getFontCached(int face, int style, int size) {
      int idFace = 0;
      if (face == IMFont.FACE_PROPORTIONAL) {
         idFace = 1;
      } else if (face == IMFont.FACE_SYSTEM) {
         idFace = 2;
      }
      int idStyle = 0;
      if (style == IMFont.STYLE_PLAIN) {
         idStyle = 1;
      } else if (style == IMFont.STYLE_BOLD) {
         idStyle = 2;
      } else if (style == IMFont.STYLE_ITALIC) {
         idStyle = 3;
      }
      int idSize = 0;
      if (face == IMFont.SIZE_LARGE) {
         idSize = 1;
      } else if (size == IMFont.SIZE_MEDIUM) {
         idSize = 2;
      } else if (size == IMFont.SIZE_VERY_LARGE) {
         idSize = 4;
      } else if (size == IMFont.SIZE_VERY_SMALL) {
         idSize = 3;
      }
      return fonts[idFace][idStyle][idSize];
   }
}
