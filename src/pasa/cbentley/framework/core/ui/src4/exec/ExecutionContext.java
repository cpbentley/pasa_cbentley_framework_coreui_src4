package pasa.cbentley.framework.core.ui.src4.exec;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.core.src4.structs.IntToObjects;
import pasa.cbentley.framework.core.ui.src4.ctx.CoreUiCtx;
import pasa.cbentley.framework.core.ui.src4.ctx.ObjectCUC;
import pasa.cbentley.framework.core.ui.src4.event.BEvent;
import pasa.cbentley.framework.core.ui.src4.input.InputState;

/**
 * An {@link ExecutionContext} tracks state changes during the process of an event or command. It also provides a reference to the input {@link InputState}.
 * 
 * <p>
 * <li>It belongs to one thread. 
 * <li>It exists to allow multi threading.
 * <li>Atomise changes into chunks
 * </p>
 * <br>
 * <p>
 * At the end of the command execution, the state changes records are acted upon.
 * <li>Events are processed first
 * <li>Commands executed ?
 * <li>Runnables to be run once the command ends.
 * <br>
 * Because the command might run in a thread in which it is not safe to modify the state of objects that are drawn.
 * <br>
 * </p>
 * 
 * Commands always run in the update thread. However the update thread may be merged with the render thread.
 * <p>
 * 
 * Records such as events, describes which state change occurs in which object (producer)
 * The {@link ExecutionContext} provides the thread in which it runs. So if the state change made in the 
 * Update thread, impacts state local to the Render thread, the event consumer can do 2 things
 * <li> Sync on its owner thread and make the changes
 * <li> Wrapper the changes in a Runnable and queue the Runnable with the {@link ExecutionContext}
 * </p>
 * <br>
 * 
 * Must be thread local
 * Each thread must have one.
 * <br>
 * 
 * The events and Runnable will execute asynchronously and do not return values.
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
public class ExecutionContext extends ObjectCUC implements ITechExecutionContext, IStringable {

   protected IntToObjects data;

   protected IntToObjects execEntries;

   private Object         pending;

   private int            pointer;

   private int            threadid;

   protected IntToObjects types;

   protected InputState     is;

   protected OutputState    os;

   /**
    * 
    * @param cuc
    */
   public ExecutionContext(CoreUiCtx cuc) {
      super(cuc);
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

   public void clear() {

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

   public void setOutputState(OutputState os) {
      this.os = os;
   }

   public void setInputState(InputState is) {
      this.is = is;
   }

   public InputState getInputState() {
      return is;
   }

   public OutputState getOutputState() {
      return os;
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
   public void toString(Dctx dc) {
      dc.root(this, ExecutionContext.class, 180);
      toStringPrivate(dc);
      super.toString(dc.sup());
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, ExecutionContext.class, 180);
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   private void toStringPrivate(Dctx dc) {

   }
   //#enddebug

}
