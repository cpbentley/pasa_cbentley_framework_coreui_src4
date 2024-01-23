package pasa.cbentley.framework.coreui.src4.engine;

import pasa.cbentley.byteobjects.src4.core.ByteObject;
import pasa.cbentley.byteobjects.src4.stator.StatorReaderBO;
import pasa.cbentley.core.src4.ctx.ICtx;
import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.core.src4.stator.StatorReader;
import pasa.cbentley.core.src4.stator.StatorWriter;
import pasa.cbentley.core.src4.structs.IntToObjects;
import pasa.cbentley.framework.coredraw.src4.interfaces.IGraphics;
import pasa.cbentley.framework.coreui.src4.ctx.CoreUiCtx;
import pasa.cbentley.framework.coreui.src4.ctx.ObjectCUC;
import pasa.cbentley.framework.coreui.src4.event.BEvent;
import pasa.cbentley.framework.coreui.src4.interfaces.IBEventListener;
import pasa.cbentley.framework.coreui.src4.interfaces.ICanvasAppli;
import pasa.cbentley.framework.coreui.src4.interfaces.ICanvasHost;
import pasa.cbentley.framework.coreui.src4.tech.IBOCanvasHost;
import pasa.cbentley.framework.coreui.src4.tech.ITechHostUI;

/**
 * Base implementation of {@link ICanvasAppli}. UI on the side of the Bentley framework.
 * <br>
 * I
 * @author Charles-Philip Bentley
 *
 */
public abstract class CanvasAppliAbstract extends ObjectCUC implements ICanvasAppli, IStringable {

   /**
   * Link to the Framework implementation of a Canvas.
   * <br>
   * Used to request repaints,  query the width/height
   */
   protected ICanvasHost     canvasHost;

   /**
    * Freely settable by subclass
    * 
    * {@link CanvasHostAbstract#titleIconComesticUpdate()} must be call for update on Host canvas.
    */
   protected String          canvasIconPath;

   /**
    * Freely settable by subclass
    * 
    * {@link CanvasHostAbstract#titleIconComesticUpdate()} must be call for update on Host canvas.
    */
   protected String          canvasTitle;

   protected ICanvasHost[]   duplicates;

   protected IntToObjects    dups;

   protected final UCtx      uc;

   private IBEventListener   listener;

   /**
    * When the Canvas is created, a reference to the Swing/SWT/J2ME Canvas is fetched
    * @param cac
    */
   public CanvasAppliAbstract(CoreUiCtx cac) {
      this(cac, null);
   }



   /**
    * Creates a {@link ICanvasHost} based on the given tech.
    * 
    * At this stage, we don't know about the technicalities of the implementation
    * @param cuc
    * @param canvasHostTech {@link IBOCanvasHost}
    */
   public CanvasAppliAbstract(CoreUiCtx cuc, ByteObject canvasHostTech) {
      super(cuc);
      if (canvasHostTech == null) {
         canvasHostTech = cuc.createBOCanvasHostDefault();
      }
      this.uc = cuc.getUCtx();
      canvasHost = cuc.createCanvasHost(canvasHostTech);
      if (canvasHost == null) {
         throw new NullPointerException();
      }
      cuc.linkCanvasAppliToHost(this, canvasHost);
   }

   public void canvasHostUpdate() {
      //force title after the link. afterwards when changed inside app, event will call it
      canvasHost.titleIconComesticUpdate();
   }

   public int getStatorableClassID() {
      throw new RuntimeException("Must be implemented by subclass");
   }

   /**
    * A Duplicate is {@link ICanvasHost} that is linked to this Canvas.
    * @param c
    */
   public void addDuplicate(ICanvasHost c) {
      if (dups == null) {
         dups = new IntToObjects(cuc.getUCtx(), 1);
      }
      dups.add(c);
   }

   /**
    * 
    */
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
    * When painting is done using {@link CanvasAppliAbstract#getGraphics()}, you need to call
    * this method to make the graphics seen on screen
    * <br>
    * Wrapper around {@link ICanvasHost#flushGraphics()}
    */
   public void flushGraphics() {
      //#debug
      toDLog().pFlow("", this, CanvasAppliAbstract.class, "flushGraphics", LVL_05_FINE, true);
      canvasHost.flushGraphics();
   }


   /**
    * TODO method for active rendering.
    * 
    * Gets a graphics with the given size of the {@link ActiveCanvas}.
    * @return
    */
   public IGraphics getGraphics() {
      return canvasHost.getGraphics();
   }

   /**
    * 0 when called inside the constructor
    */
   public int getHeight() {
      return canvasHost.getICHeight();
   }

   /**
    * Returns the {@link ICanvasHost} rendering this {@link CanvasAppliAbstract}
    * <br>
    * <br>
    * @return
    */
   public ICanvasHost getCanvasHost() {
      return canvasHost;
   }

   public String getIcon() {
      if (canvasIconPath == null) {
         canvasIconPath = cuc.getConfigUI().getIconPathDefault();
      }
      return canvasIconPath;
   }

   /**
    * return {@link IBOCanvasHost}
    * @return
    */
   public ByteObject getTechCanvasHost() {
      return canvasHost.getBOCanvasHost();
   }

   /**
    * by default returns the title from the config
    * @return
    */
   public String getTitle() {
      if (canvasTitle == null) {

      }
      return canvasTitle;
   }

   /**
    * 0 when called inside the constructor
    */
   public int getWidth() {
      return canvasHost.getICWidth();
   }

   public void hideNotify() {
   }

   public boolean isShown() {
      return canvasHost.isShown();
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
      canvasHost.icRepaint();
      if (dups != null) {
         for (int i = 0; i < dups.nextempty; i++) {
            ((ICanvasHost) dups.getObjectAtIndex(i)).icRepaint();
         }
      }
   }

   public void repaint(int x, int y, int w, int h) {
      canvasHost.icRepaint(x, y, w, h);
      if (dups != null) {
         for (int i = 0; i < dups.nextempty; i++) {
            ((ICanvasHost) dups.getObjectAtIndex(i)).icRepaint(x, y, w, h);
         }
      }
   }

   /**
    */
   public void setFullScreenMode(boolean mode) {
      canvasHost.setCanvasFeature(ITechHostUI.SUP_ID_27_FULLSCREEN, mode);
   }

   public void setListener(IBEventListener lis) {
      this.listener = lis;
   }

   /**
    * Sets the title to be used
    */
   public void setTitle(String title) {
      this.canvasTitle = title;
   }

   public void setIcon(String icon) {
      this.canvasIconPath = icon;
   }

   public void showNotify() {
   }

   /**
    */
   public void stateReadFrom(StatorReader state) {

      StatorReaderBO stator = (StatorReaderBO) state;

      canvasTitle = stator.getReader().readString();
      canvasIconPath = stator.getReader().readString();

      //
      if (canvasHost != null) {
         //we want to delete it.. remove it from view etc.
      }

      //canvas host is not critical. you can create a default one
      //create a new one or update the one we have? stator decides
      canvasHost = (ICanvasHost) stator.readObject(cuc, canvasHost);

      //critical and optional objects when rebuilding from state
      listener = (IBEventListener) stator.readObject(listener);
   }

   /**
    */
   public void stateWriteTo(StatorWriter state) {
      //it might already be written... who knows
      state.getWriter().writeString(canvasTitle);
      state.getWriter().writeString(canvasIconPath);
      state.writerToStatorable(canvasHost);
      state.writerToStatorable(listener);
   }


   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, CanvasAppliAbstract.class, 300);
      toStringPrivate(dc);
      super.toString(dc.sup());
      
      dc.appendVarWithSpace("canvasTitle", canvasTitle);
      dc.appendVarWithSpace("canvasIconPath", canvasIconPath);
      dc.nlLvl(listener, "IBEventListener");
      dc.nlLvlArray("Duplicates", duplicates);
      dc.nlLvl(canvasHost, "ICanvasHost");
   }

   private void toStringPrivate(Dctx dc) {
      dc.appendVarWithSpace("getWidth", getWidth());
      dc.appendVarWithSpace("getHeight", getHeight());
      dc.appendVarWithSpace("isShown", isShown());
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, CanvasAppliAbstract.class);
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   //#enddebug
   

}
