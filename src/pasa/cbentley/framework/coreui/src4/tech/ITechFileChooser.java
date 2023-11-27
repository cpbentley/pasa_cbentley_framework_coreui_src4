package pasa.cbentley.framework.coreui.src4.tech;

import pasa.cbentley.byteobjects.src4.tech.ITechByteObject;

/**
 * Describe the configuration of a File Chooser called by {@link IHostTools#getFileChooser(mordan.bentleyfw.interfaces.ui.ICanvasHost)}
 * <br>
 * <br>
 * 
 * @author Charles-Philip
 *
 */
public interface ITechFileChooser extends ITechByteObject {

   public static final int FILECHOOSER_OFFSET_01_FLAG       = A_OBJECT_BASIC_SIZE;

   public static final int FILECHOOSER_OFFSET_02_FLAGX      = A_OBJECT_BASIC_SIZE + 1;

   public static final int FILECHOOSER_OFFSET_03_FLAGZ      = A_OBJECT_BASIC_SIZE + 2;

   /**
    * 2 bytes defines where is the String stored.
    * If it is localized or not
    */
   public static final int FILECHOOSER_OFFSET_04_STR_TITLE2 = A_OBJECT_BASIC_SIZE + 2;

   /**
    * Genetics of the Host.
    */
   public static final int FILECHOOSER_OFFSET_04_FLAGY      = A_OBJECT_BASIC_SIZE + 3;

   public static final int FILECHOOSER_BASIC_SIZE           = A_OBJECT_BASIC_SIZE + 6;

   public static final int FC_FLAG_1_ALLOW_DIRS = 1 << 1;

}
