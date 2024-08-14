package pasa.cbentley.framework.core.ui.src4.tech;

import pasa.cbentley.byteobjects.src4.core.interfaces.IByteObject;
import pasa.cbentley.framework.core.ui.src4.ctx.IBOTypesCoreUi;
import pasa.cbentley.framework.core.ui.src4.engine.WrapperAbstract;

/**
 * Defines a Frame/Windows positioning in Host environment.
 * <br>
 * <br>
 * 
 * Every {@link ICanvasHost} is located somewhere. 
 * 
 * This {@link IBOFramePos} encapsulates this information for the framework's {@link WrapperAbstract}. 
 * 
 * It requests an update with {@link ICanvasHost#updateSettings(mordan.memory.ByteObject)}.
 * <br>
 * <br>
 * When {@link IAppli} changes the {@link IBOFramePos} of one of its {@link ICanvasHost},
 * <br>
 * <br>
 * The {@link IAppli} can thus change the screen on which the {@link ICanvasHost} is being shown.
 * <br>
 * Only feasible when {@link ITechHostUI#SUP_ID_24_MULTIPLE_WINDOWS} feature is supported by host.
 * <br>
 * We want to be able to send a ICanvas to another screen. Query screen.
 * Screens are conveniences. when screen is not available, a Virtual Screen is used.
 * Therefore the application logic stays the same.
 * <br>
 * The command "cycle screens" gives focus to different screens.
 * @author Charles Bentley
 *
 */
public interface IBOFramePos extends IByteObject {

   public static final int FPOS_BASIC_SIZE             = A_OBJECT_BASIC_SIZE + 13;

   /**
    * One of the 256 framework types
    */
   public static final int FPOS_TYPE                   = IBOTypesCoreUi.TYPE_8_FRAME_POS;

   /**
    * Is the Frame in a full screen configuration
    */
   public static final int FPOS_FLAG_1_FULLSCREEN      = 1 << 0;

   /**
    * The Canvas will ignore X Y and set the default ratio defined by Host for new
    * Canvas by the host framework
    */
   public static final int FPOS_FLAG_2_DEFAULT_SIZING  = 1 << 1;

   /**
    * Padding the frame out the screen. Only usefull if no border with another screen.
    * 
    */
   public static final int FPOS_FLAG_3_OUTSIDE_PADDING = 1 << 2;

   /**
    * When flag is set, the x and y are relative to the 0,0 of the screen.
    * 
    */
   public static final int FPOS_FLAG_4_RELATIVE        = 1 << 3;

   public static final int FPOS_OFFSET_01_FLAG         = A_OBJECT_BASIC_SIZE;

   /**
    * 
    */
   public static final int FPOS_OFFSET_02_X2           = A_OBJECT_BASIC_SIZE + 1;

   public static final int FPOS_OFFSET_03_Y2           = A_OBJECT_BASIC_SIZE + 3;

   public static final int FPOS_OFFSET_04_W2           = A_OBJECT_BASIC_SIZE + 5;

   public static final int FPOS_OFFSET_05_H2           = A_OBJECT_BASIC_SIZE + 7;

   /**
    * Z index relative to other frames on the screen
    */
   public static final int FPOS_OFFSET_06_Z2           = A_OBJECT_BASIC_SIZE + 9;

   /**
    * Screen identification.
    */
   public static final int FPOS_OFFSET_07_SCREEN2      = A_OBJECT_BASIC_SIZE + 11;

}
