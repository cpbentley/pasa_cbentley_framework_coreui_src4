package pasa.cbentley.framework.coreui.src4.engine;

import pasa.cbentley.framework.coreui.src4.ctx.CoreUiCtx;
import pasa.cbentley.framework.coreui.src4.interfaces.IMFont;

/**
 * J2SE bridge class for the {@link mordan.device.ui.FontAbstract.Font} class of MIDP 2.0 <br>
 * 
 * TODO how to provide antialiased font drawing in this bridge?
 * 
 * <br>
 * Font class is final in J2ME...
 * 
 * 
 * @author
 *
 */
public abstract class FontAbstract implements IMFont {

   protected int                face, style, size;

   protected final CoreUiCtx cac;

   protected FontAbstract(CoreUiCtx cac) {
      this.cac = cac;

   }

   protected FontAbstract(CoreUiCtx cac, int face, int style, int size) {
      this.cac = cac;
   }

   public abstract int charWidth(char c);

   public abstract int charsWidth(char[] c, int ofs, int len);

   public abstract int getBaselinePosition();

   public abstract int getHeight();

   public abstract int stringWidth(String s);

   public abstract int substringWidth(String s, int offset, int length);

   public int getFace() {
      return face;
   }

   public int getSize() {
      return size;
   }

   public int getStyle() {
      return style;
   }

   public boolean isBold() {
      return (style & IMFont.STYLE_BOLD) != 0;
   }

   public boolean isUnderlined() {
      return (style & IMFont.STYLE_UNDERLINED) != 0;
   }

   public boolean isItalic() {
      return (style & IMFont.STYLE_ITALIC) != 0;
   }

   public boolean isPLAIN() {
      return style == 0;
   }
}
