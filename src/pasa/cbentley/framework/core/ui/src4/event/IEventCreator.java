package pasa.cbentley.framework.core.ui.src4.event;

/**
 * The {@link IEventCreator} can create framework defined events.
 * <br>
 * It allows pooling to be turned on/off if the feature is supported.
 * <br>
 * @author Charles Bentley
 *
 */
public interface IEventCreator {

   public static final int FLAG_1_POOLING_GESTURE = 1;

   public static final int FLAG_2_POOLING_DEVICE  = 1;

   public GesturePointer createGP(int pointerID);
}
