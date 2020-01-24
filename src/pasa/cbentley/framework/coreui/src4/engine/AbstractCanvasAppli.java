package pasa.cbentley.framework.coreui.src4.engine;

import pasa.cbentley.byteobjects.src4.core.ByteObject;
import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.core.src4.structs.IntToObjects;
import pasa.cbentley.framework.coredraw.src4.interfaces.IGraphics;
import pasa.cbentley.framework.coreui.src4.ctx.CoreUiCtx;
import pasa.cbentley.framework.coreui.src4.event.BEvent;
import pasa.cbentley.framework.coreui.src4.interfaces.IBEventListener;
import pasa.cbentley.framework.coreui.src4.interfaces.ICanvasAppli;
import pasa.cbentley.framework.coreui.src4.interfaces.ICanvasHost;
import pasa.cbentley.framework.coreui.src4.tech.ITechCanvas;
import pasa.cbentley.framework.coreui.src4.tech.ITechUI;

/**
 * Base implementation of {@link ICanvasAppli}. UI on the side of the Bentley framework.
 * <br>
 * I
 * @author Charles-Philip Bentley
 *
 */
public abstract class AbstractCanvasAppli implements ICanvasAppli, IStringable {

   /**
   * Link to the Framework implementation of a Canvas.
   * <br>
   * Used to request repaints,  query the width/height
   */
   protected ICanvasHost   ca;

   protected final CoreUiCtx cac;

   protected ICanvasHost[] duplicates;

   protected IntToObjects  dups;

   private IBEventListener listener;

   /**
    * When the Canvas is created, a reference to the Swing/SWT/J2ME Canvas is fetched
    * @param cac
    */
   public AbstractCanvasAppli(CoreUiCtx cac) {
      this(cac, cac.createDefaultCanvasTech());
   }

   /**
    * Creates a {@link ICanvasHost} based on the given tech
    * @param cac
    * @param canvasTech {@link ITechCanvas}
    */
   public AbstractCanvasAppli(CoreUiCtx cac, ByteObject canvasTech) {
      this.cac = cac;
      if (cac == null) {
         throw new NullPointerException();
      }
      ca = cac.createCanvas(this, canvasTech);
      if (ca == null) {
         throw new NullPointerException();
      }
   }

   /**
    * A Duplicate is {@link ICanvasHost} that is linked to this Canvas.
    * @param c
    */
   public void addDuplicate(ICanvasHost c) {
      if (dups == null) {
         dups = new IntToObjects(cac.getUCtx(), 1);
      }
      dups.add(c);
   }

   public void event(BEvent g) {
      eventToCanvas(g);
      if (listener != null) {
         listener.newEvent(g);
      }
   }

   /**
    * All input event from devices.
    * All events relative external business logic events.
    * <li> Buys results
    * <li> Activity Results
    */
   protected abstract void eventToCanvas(BEvent g);

   /**
    * 
    */
   public void flushGraphics() {
      ca.flushGraphics();
   }

   /**
    * Gets a graphics with the given size of the {@link ActiveCanvas}.
    * @return
    */
   public IGraphics getGraphics() {
      return ca.getGraphics();
   }

   public int getHeight() {
      return ca.getICHeight();
   }

   /**
    * Returns the {@link ICanvasHost} rendering this {@link AbstractCanvasAppli}
    * <br>
    * <br>
    * @return
    */
   public ICanvasHost getICanvas() {
      return ca;
   }
   
   /**
    * by default returns the title from the config
    * @return
    */
   public String getTitle() {
      // TODO Auto-generated method stub
      return null;
   }

   public String getIcon() {
      return null;
   }
   
   /**
    * Returns the {@link CanvasCtx} of this canvas.
    * <br>
    * 
    * @return
    */
   public CoreUiCtx getCAC() {
      return cac;
   }

   public int getWidth() {
      return ca.getICWidth();
   }

   public void hideNotify() {
   }

   public boolean isShown() {
      return ca.isShown();
   }

   /**
    * Abstract method that needs to be implemented by concrete midlet supplied subclasses.
    */
   public abstract void paint(IGraphics g);

   public void removeDuplicate(ICanvasHost c) {
      if (dups != null) {
         dups.removeRef(c);
      }
   }

   /**
    * @see ICanvasHost#icRepaint()
    */
   public void repaint() {
      ca.icRepaint();
      if (dups != null) {
         for (int i = 0; i < dups.nextempty; i++) {
            ((ICanvasHost) dups.getObjectAtIndex(i)).icRepaint();
         }
      }
   }

   public void repaint(int x, int y, int w, int h) {
      ca.icRepaint(x, y, w, h);
      if (dups != null) {
         for (int i = 0; i < dups.nextempty; i++) {
            ((ICanvasHost) dups.getObjectAtIndex(i)).icRepaint(x, y, w, h);
         }
      }
   }

   /**
    */
   public void setFullScreenMode(boolean mode) {
      ca.setCanvasFeature(ITechUI.SUP_ID_27_FULLSCREEN, mode);
   }

   public void setListener(IBEventListener lis) {
      this.listener = lis;
   }

   public void showNotify() {
   }

   //#mdebug
   
   public IDLog toDLog() {
      return cac.toDLog();
   }
   
   public String toString() {
      return Dctx.toString(this);
   }

   public UCtx toStringGetUCtx() {
      return cac.getUCtx();
   }

   public void toString(Dctx dc) {
      dc.root(this, "DisplayableAbstract");
      dc.appendVarWithSpace("CanvasHostWidth", getWidth());
      dc.appendVarWithSpace("CanvasHostHeight", getHeight());
      dc.appendVarWithSpace("IsShown", isShown());
      dc.nlLvl(ca, "ICanvas");
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   /**
    * Called when  {@link Dctx} see the same object for another time
    * @param dc
    */
   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "DisplayableAbstract");
      dc.appendVarWithSpace("CanvasHostWidth", getWidth());
      dc.appendVarWithSpace("CanvasHostHeight", getHeight());
   }
   //#encacebug
}
