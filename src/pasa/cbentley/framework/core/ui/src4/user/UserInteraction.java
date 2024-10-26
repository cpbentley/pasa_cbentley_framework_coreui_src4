package pasa.cbentley.framework.core.ui.src4.user;

import pasa.cbentley.byteobjects.src4.core.ByteObject;
import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.framework.core.ui.src4.ctx.CoreUiCtx;
import pasa.cbentley.framework.core.ui.src4.ctx.IBOTypesCoreUi;
import pasa.cbentley.framework.core.ui.src4.ctx.ObjectCUC;

/**
 * Represents a code event that interacts with the user, passively or actively annoying the user.
 * 
 * <p>
 * {@link UserInteraction} helps the user to change things he doesn't like about what just happened.
 * 
 * </p>
 * 
 * <p>
 * <b>Examples of Changes</b>:<br>
 * <li>Speed up an animation.
 * <li>Change the sound of music
 * <li>Flag a command as slow
 * </p>
 * 

 * 
 * <p>
 * Interactions are:
 * <li>Animation: a passive user interaction. 
 * <li>Sounds : Passive discrete or continuous interaction
 * <li>Commands: is an active user interaction.
 * <li>Key/Pointer events
 * <li>Gestures events
 * <li>Caret Blinking: passive continunous interaction.
 * <li>Music: passive continunous interaction.
 * <br>
 * </p>
 * 
 * <p>
 * a screen repaint is not a user interaction.
 * </p>
 * 
 * <p>
 * The user queries the history of interactions. Identifying an animation he wants to change, the user may decide to
 * <li>mute it if it plays sounds.
 * <li>Remove animation altogether
 * <li>Increase/Decrease its speed
 * </p>
 * 
 * 
 * <p>
 * 
 * <b>Animations</b>:<br>
 * Change the types of animations for that situation. Actually a choice is made randomly in the list for that situation.
 * When an Animation is playing, a user may shorten it by pressing any key or pointer. This will race the animation to its end
 * and remember to increase the speed for next instance of that situation. 
 * Giving the millisecond maximum recorded at the user event.
 * <br>
 * Input/Output animations are the most likely to be accelerated. There are separated from other animations.
 * In Android, you can speed up animations as a system wide setting. However it speeds up passive background animations
 * as well.
 * </p>
 * 
 * <p>
 * <b>Commands</b>:<br>
 * A command is also a user interaction (active). The user decides to change its triggers.
 * </p>
 * 
 * <p>
 * <b>Technical considerations</b>:
 * </p>
 * 
 * <p>
 * When an animation is running? how does code know if user wants to kill animation
 * or just interact? Well intro anims are killed as as soon as an event is registered
 * on its area
 * </p>
 * 
 * 
 * @see ByteObject
 * 
 * @author Charles-Philip Bentley
 *
 */
public class UserInteraction extends ObjectCUC implements IStringable {


   private ByteObject     me;

   private String         path;

   private String         pathAux;

   public UserInteraction(CoreUiCtx cuc, int type) {
      super(cuc);
      me = new ByteObject(cuc.getBOC(), IBOTypesCoreUi.TYPE_3_USER_INTERACTION, IBOUserInterAction.UIA_BASIC_SIZE);
      me.set1(IBOUserInterAction.UIA_OFFSET_02_TYPE1, type);
   }

   public String getAux() {
      return pathAux;
   }

   public String getString() {
      return path;
   }

   public ByteObject getTech() {
      return me;
   }

   public boolean isAccepted() {
      return !me.hasFlag(IBOUserInterAction.UIA_OFFSET_01_FLAG, IBOUserInterAction.UIA_FLAG_1_REJECTED);
   }

   public void setAux(String type) {
      pathAux = type;
   }

   public void setString(String string) {
      path = string;
   }


   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, UserInteraction.class, 135);
      toStringPrivate(dc);
      super.toString(dc.sup());
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, UserInteraction.class, 135);
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   private void toStringPrivate(Dctx dc) {
      
   }
   //#enddebug
   


}
