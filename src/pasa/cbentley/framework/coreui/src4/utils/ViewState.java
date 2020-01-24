package pasa.cbentley.framework.coreui.src4.utils;

import pasa.cbentley.byteobjects.src4.core.ByteObject;
import pasa.cbentley.byteobjects.src4.core.ByteObjectManaged;
import pasa.cbentley.byteobjects.src4.ctx.BOCtx;
import pasa.cbentley.byteobjects.src4.ctx.IBOTypesBOC;
import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.io.BADataIS;
import pasa.cbentley.core.src4.io.BADataOS;
import pasa.cbentley.core.src4.structs.IntBuffer;
import pasa.cbentley.core.src4.structs.IntToObjects;
import pasa.cbentley.framework.coreui.src4.interfaces.IBentleyFwSerial;

/**
 * Interface implemented to save and restore state of an application.
 * <br>
 * Serialize encapsulates the <b>user</b> visual state of an {@link IDrawable} and allows to recreate it using {@link IDrawable#setViewState(ViewState)}.
 * <br>
 * The Bentley Serialization framework is platform independant. Therefore it cannot use
 * <li>No Class info is used because that's not compatible with
 * unknown Host class model
 * <li>No reflexion. No optimization possible
 * <br>
 * <br>
 * Therefore, every {@link IBentleyFwSerial} must create objects type and call the S
 * <br>
 * Cycles are supported because inside a serial file, each object is assigned an serialID.
 * <br>
 * 
 * This allows
 * <br>
 * {@link ViewState} is initialized with a Blob of bytes inside of {@link ByteObject} of type {@link IViewTypes#FTYPE_07_VIEW_STATE}
 * <br>
 * <br>
 * ViewState is linked to AppliID
 * When unwrapping a ViewState, there is a Page ID.
 * Loads page from page creator.
 * set viewstate, there must be a clear match.
 * If any exception is thrown, ViewState is ignored and Page ID loads as new
 * <br>
 * Model data? It is saved as well. What if model is taken from a database?
 * A browsing list displays items from a DB. upon serializing, it will save index, value.
 * Model will recieve the call to serialize. But it is a View serialization, not a full serialization.
 * <br>
 * One could take a full serialization and send the data to another 
 * device and rebuild the whole application structure, including local DB.
 * and run it as a copy alongside an existing instance.
 * <br>
 * Backward compatible? Yes. Using a VersionID
 * <br>  differences and possible optimizations.
 * <br>
 * Application singletons like {@link GuiContext} are not serialized. They are
 * fetched upon deserialization.
 * <br>
 * What about TBLR relationships between Drawables ? serialUID is used
 * <br>
 * Any state decided by the user is stored in this class. The rest is not stored. It is like a preference storage mechanism.
 * <br>
 * Host Devices like Android will require this because a phone call kills the Application.
 * <br>
 * <br>
 * Style is not user decided and thus is not stored.
 * <br>
 * 
 * Model class used by the View to store its state.
 * <br>
 * <br>
 * 
 * State includes its children. It does nothing for model state.
 * <br>
 * However setting a ViewState may change the model selection.
 * <br>
 * <b>Options</b>: They may not always be needed. <br>
 * <li>Takes a physical copy of style and all its links. That means they will survive a Repository cleaning.
 * <br>
 * 
 * <li>{@link ScrollConfig}
 * <li>Selected elements. 
 * <li>Marked elements
 * <li>behavior flags, states flags
 * <br>
 * <br>
 * Uses:
 * <li>The history chain of visible states allows the framework to keep a long list of Drawable active.
 * <br>
 * <br>
 * Stores delta between {@link ViewState} of a {@link Drawable} and sub classes.
 * Base delta stores everything in byte array.
 * ViewState stores all the history of the Drawable.
 * It is possible to undo a command and roll back to a previous ViewState ID.
 * <br>
 * Provide serialization service (optional)
 * <br>
 * Complex drawable must then write the content inside the ViewState byte array
 * <br>
 *   //TODO how do you save Listeners????
 *   Interface getState.
 *   Unwrap state? Problem with Object constructors
 *   Each Module has Class ID, every class must implements Constructor with ViewState as sole
 *   Const
 * <br>
 * heavy serialize = full state.
 * light serialize = light means just save the String of a {@link StringDrawable}.
 * 
 * TODO when serializing a live object like a page, that use a shared object
 * .. upon deserialization, how do you get the reference to that shared object which may have changed
 * content! For example Page Input. with Listener X. Help page is shown and Page Input serialized because live
 * memory is out. Page Help needs Listener X and create it.
 * When doing back back we go back to the Page Input. Which Listener X? We say Listener is not local.
 * Content is unknown. So we need a special process.
 * <br>
 * TODO Agents with instance {@link ByteObjectManaged}
 * we have to match it with live Objects how?
 * Every object that is serialized must be reachable. we don't have weak references... :(
 * You can implement Object Pool host side.
 * in J2ME, none and partial deserialization is not possible
 * <br>
 * <br>
 * Use Unique UIID?  Even a model has a UUID. When a page is created back, it looks up the model
 * it was using the UIID, if it cannot be found, create it.
 * How does the class know that the model should not be serialized? because the model
 * only serialize its UUID. model knows that it will not be serialized.
 * <br>
 * <br>
 *  //TODO this an example of start up code that when unserialized.. does not need a UIID
   // this code must be run during deserialize... as in 
   figDrwTop.setIPActor(this); //to compute width
    Some kind of Connect to Framework method<br>
    <br>
    
 * @author Charles-Philip Bentley
 *
 */
public class ViewState {

   public static final int VS_INDEX_TABLE        = 1;

   public static final int VS_INDEX_VIEWDRAWABLE = 0;

   public static final int VS_INDEX_VIEWPANE     = 1;

   public ViewState[]      allstates;

   /**
    * When state is set with mouse over.
    * A Drawable may have a behavior for being state less regarding
    * inner states values.
    *
    */
   public int              int1States;

   public int              int2Behaviors;

   private IntBuffer       integers;

   /**
    * 
    */
   public IntToObjects     itos;

   private int             subIndex              = 0;

   /**
    * First is Drawable, then depending on the class Hierarchy
    */
   public Object[]         subStates             = new Object[5];

   private int             type;

   private BOCtx           boc;

   public ViewState() {
   }

   public ViewState(int type) {
      this.type = type;
      UCtx uc = null;
      itos = new IntToObjects(uc);
      integers = new IntBuffer(uc);
   }

   /**
    * Unwrap state from {@link IViewTypes#FTYPE_07_VIEW_STATE}
    * <br>
    * <br>
    * 
    * @param state
    */
   public ViewState(ByteObject state) {
   }

   /**
    * 
    * @param vsd
    */
   public void add(ViewState vsd) {
      subStates[subIndex] = vsd;
      subIndex++;
      if (subIndex >= subStates.length) {
         subStates = getUCtx().getMem().increaseCapacity(subStates, 5);
      }
   }

   public UCtx getUCtx() {
      return boc.getUCtx();
   }

   /**
    * Adds a null marker for ordered view state stacking.
    */
   public void addNull() {
      add(null);
   }

   /**
    * 
    * @param d
    */
   public void addViewState(IBentleyFwSerial d) {
      if (d != null) {
         d.setStateTo(this);
      } else {
         //flag as null
         addNull();
      }
   }

   public int getType() {
      return type;
   }

   /**
    * 
    * @param i
    * @return
    */
   public ViewState getMyState(int i) {
      if (allstates == null || i >= allstates.length) {
         return null;
      }
      return (ViewState) allstates[i];
   }

   /**
    * 
    * @return
    */
   public ByteObject getState() {
      int num = itos.nextempty;
      int size = 0;
      ByteObject bo = new ByteObject(boc, IBOTypesBOC.TYPE_037_CLASS_STATE, size);
      return bo;
   }

   /**
    * Serialize to the form of a {@link ByteObject}.
    * @return
    */
   public byte[] serialize() {
      return null;
   }

   public ByteObject serializeBO() {
      return null;
   }

   private BADataOS dos;

   private BADataIS dis;

   /**
    * Serialize the ViewState. We cannot use Java Object serialization? No
    * because it is not portable between Java2ME
    * @param ito
    * @param dos
    * @return
    */
   public byte[] serialize(IntToObjects ito, BADataOS dos) {
      return null;
   }

   /**
    * Add Object as bytes
    * @param data
    */
   public void addBytes(byte[] data) {
      // TODO Auto-generated method stub

   }

   public ByteObject readNextByteObject() {
      // TODO Auto-generated method stub
      return null;
   }

   public byte[] readNextObject() {
      // TODO Auto-generated method stub
      return null;
   }

   /**
    * Tells {@link ViewState}, class is going to add x objects
    * @param size
    */
   public void addObjects(int size) {
      // TODO Auto-generated method stub

   }

   /**
    * Call after {@link ViewState#addObjects(int)}
    * Finalize Array of Objects
    */
   public void closeArrayObjects() {
      // TODO Auto-generated method stub

   }

   /**
    * Read object and flag it for deletion.
    * <br>
    * @return null if no data for key
    */
   public byte[] readObjectDel(int key) {
      // TODO Auto-generated method stub
      return null;
   }

   public void setVersion(int ver) {
      // TODO Auto-generated method stub

   }

   public int readVersion() {
      // TODO Auto-generated method stub
      return 0;
   }

   public void wrongVersion(int ver) {
      throw new IllegalArgumentException("Wrong Version" + ver);
   }

   public int readInt() {
      // TODO Auto-generated method stub
      return 0;
   }

   public void writeByteObject(ByteObject bo) {
      bo.serializeTo(itos, dos);
   }

   public void writeInt(int behaviors) {
      // TODO Auto-generated method stub

   }
}
