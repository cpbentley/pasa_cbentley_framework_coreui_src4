package pasa.cbentley.framework.coreui.src4.exec;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.core.src4.structs.IntToObjects;
import pasa.cbentley.framework.coreui.src4.ctx.CoreUiCtx;
import pasa.cbentley.framework.coreui.src4.event.BEvent;

/**
 * An {@link ExecutionContext} records or queue state changes during the process of a command.
 * It belongs to one thread. 
 * 
 * 
 * 
 * <li>It exists to allow multi threading.
 * <li>.
 * 
 * <p>
 * At the end of the command execution, the state changes records are acted upon.
 * </p>
 * <li>Events are processed first
 * <li>Commands executed ?
 * <li>Runnables to be run once the command ends.
 * <br>
 * Because the command might run in a thread in which it is not safe to modify the state of objects that are drawn.
 * <br>
 * Commands run in the update thread. Always. However the update thread may or maybe not merged with the render thread.
 * <br>
 * <br>
 * Records such as events, describes which state change occurs in which object (producer)
 * The {@link ExecutionContext} provides the thread in which it runs. So if the state change made in the 
 * Update thread, impacts state local to the Render thread, the event consumer can do 2 things
 * <li> Sync on its owner thread and make the changes
 * <li> Wrapper the changes in a Runnable and queue the Runnable with the {@link ExecutionContext}
 * <br>
 * 
 * <br>
 * <br>
 * When a class {@link CanvasAppliInput} with default thread settings, an {@link ExecutionContext} is not required.
 * Everything is single threaded. 
 * <br>
 * <br>
 * Must be thread local
 * Each thread must have one.
 * <br>
 * 
 * The events and Runnable will be execute asynchronously and do not return values.
 * <br>
 * When the update thread receive an Event, it generates a command.
 * That command makes state changes, those changes generates events.
 * <br>
 * event may target other state entities or target GUI entities.
 * <br>
 * A String change is done at the GUI.
 * <br>
 * The order within a thread is respected. Order of 2 Items from different threads context
 * is random.
 * <br>
 * <br>
 * The main execution module will override this class and implement
 * its own execution context as well.
 * Usually you will have graphics thread.
 * <br>
 * @author Charles Bentley
 *
 */
public class ExecutionContext implements ITechExecutionContext, IStringable {

   protected IntToObjects data;

   protected IntToObjects execEntries;

   private CoreUiCtx       cuc;

   private Object         pending;

   private int pointer;

   private int threadid;

   protected IntToObjects types;

   /**
    * 
    * @param cuc
    */
   public ExecutionContext(CoreUiCtx cuc) {
      this.cuc = cuc;
      UCtx uc = cuc.getUC();
      data = new IntToObjects(uc);
      types = new IntToObjects(uc);
      execEntries = new IntToObjects(uc);
   }

   public void setPending(Object pending) {
      this.pending = pending;
   }
   /**
    * On which thread should an Event run? 
    * <br>
    * Thread ID is provided.
    * <br>
    * @param eventID
    * @param producer
    */
   public void addEvent(int eventID, Object producer) {
      types.add(EXEC_TYPE_0_EVENT, null); //flag for event
      data.add(eventID, producer);
   }

   /**
    * Run has a thread ID.
    * @param eventID
    * @param producer
    */
   public void addRun(int eventID, Object producer) {
      types.add(EXEC_TYPE_1_RUN, null); //flag for event
      data.add(eventID, producer);
   }

   /**
    * Execute all queue executions.
    * <br>
    * TODO what about Events that return a value for each execution?
    * Look into Java 8 CompletableFuture
    * <br>
    */
   public void executeAll() {

   }

   /**
    * An {@link ExecEntry} will be returned once.
    * @return
    */
   public ExecEntry getNext() {
      if (pointer >= types.nextempty) {
         return null;
      }
      ExecEntry ee = new ExecEntry();
      ee.thread = threadid;
      ee.type = types.ints[pointer];
      ee.action = data.ints[pointer];
      ee.o = data.objects[pointer];
      pointer++;
      return ee;
   }

   /**
    * 
    * @param ev
    */
   public void queueBEvent(BEvent ev) {
      ExecEntry ee = new ExecEntry();
      ee.o = ev;
      ee.type = EXEC_TYPE_0_EVENT;
      execEntries.add(ee);
   }

   //#mdebug
   public IDLog toDLog() {
      return toStringGetUCtx().toDLog();
   }

   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "ExecutionCtx");
      toStringPrivate(dc);
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "ExecutionCtx");
      toStringPrivate(dc);
   }

   public UCtx toStringGetUCtx() {
      return cuc.getUC();
   }

   private void toStringPrivate(Dctx dc) {

   }

   //#enddebug

}
