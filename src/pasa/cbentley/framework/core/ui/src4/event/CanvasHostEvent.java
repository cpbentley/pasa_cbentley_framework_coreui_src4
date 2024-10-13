package pasa.cbentley.framework.core.ui.src4.event;

import pasa.cbentley.byteobjects.src4.core.ByteObject;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.framework.core.ui.src4.ctx.CoreUiCtx;
import pasa.cbentley.framework.core.ui.src4.ctx.ToStringStaticCoreUi;
import pasa.cbentley.framework.core.ui.src4.interfaces.ICanvasHost;
import pasa.cbentley.framework.core.ui.src4.interfaces.ITechEventHost;
import pasa.cbentley.framework.core.ui.src4.tech.IInput;

/**
 * An action on the UI of the {@link ICanvasHost} is made.
 * 
 * <p>
 * <li>{@link ITechEventHost#ACTION_01_CLOSE}
 * <li>{@link ITechEventHost#ACTION_02_MOVED}
 * <li>{@link ITechEventHost#ACTION_03_RESIZED}
 * <li>{@link ITechEventHost#ACTION_04_FOCUS_GAIN}
 * </p>
 * 
 * @see AppliEvent
 * @author Charles Bentley
 *
 */
public class CanvasHostEvent extends BEvent {
   /**
    * 
    */
   private int         actionType;

   private ICanvasHost canvasHost;

   private int         h;

   public ByteObject   tech;

   private int         w;

   private int         x;

   private int         y;

   /**
    * {@link ITechEventHost#ACTION_01_CLOSE}
    * {@link ITechEventHost#ACTION_02_MOVED}
    * {@link ITechEventHost#ACTION_10_DRAG_DROP}
    * @param cuc
    * @param actionType
    * @param canvasHot
    */
   public CanvasHostEvent(CoreUiCtx cuc, int actionType, ICanvasHost canvasHot) {
      super(cuc);
      this.type = IInput.TYPE_3_CANVAS;
      this.actionType = actionType;
      this.canvasHost = canvasHot;
   }

   /**
    * 
    * @return
    */
   public int getActionType() {
      return actionType;
   }

   public ICanvasHost getCanvasHot() {
      return canvasHost;
   }

   public int getH() {
      return h;
   }

   public String getUserLineString() {
      String str = "";
      if (actionType == ITechEventHost.ACTION_03_RESIZED) {
         str = "Canvas resized to " + w + " " + h;
      } else if (actionType == ITechEventHost.ACTION_02_MOVED) {
         str = "Canvas moved to " + x + " " + y;
      } else {
         str = "canvas " + actionType;
      }
      return str;
   }

   public int getW() {
      return w;
   }

   public int getX() {
      return x;
   }

   public int getY() {
      return y;
   }

   public void setH(int h) {
      this.h = h;
   }

   public void setW(int w) {
      this.w = w;
   }

   public void setX(int x) {
      this.x = x;
   }

   public void setY(int y) {
      this.y = y;
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, CanvasHostEvent.class, 110);
      toStringPrivate(dc);
      super.toString(dc.sup());

      dc.nlLvl(tech, "tech");

      dc.nlLvl(canvasHost, "canvasHost");
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, CanvasHostEvent.class);
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   private void toStringPrivate(Dctx dc) {
      dc.appendVarWithSpace("actionType", actionType);
      dc.appendBracketedWithSpace(ToStringStaticCoreUi.toStringEventCanvas(actionType));

      dc.appendVarWithSpace("x", x);
      dc.appendVarWithSpace("y", y);
      dc.appendVarWithSpace("w", w);
      dc.appendVarWithSpace("h", h);
   }

   //#enddebug

}
