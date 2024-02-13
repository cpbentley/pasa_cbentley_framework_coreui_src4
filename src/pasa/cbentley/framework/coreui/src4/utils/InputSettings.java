package pasa.cbentley.framework.coreui.src4.utils;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.event.BusEvent;
import pasa.cbentley.core.src4.event.IEventConsumer;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.framework.coreui.src4.ctx.CoreUiCtx;
import pasa.cbentley.framework.coreui.src4.ctx.IEventsCoreUI;
import pasa.cbentley.framework.coreui.src4.tech.IInput;
import pasa.cbentley.framework.coreui.src4.tech.ITechInputConstants;
import pasa.cbentley.framework.coreui.src4.tech.ITechHostUI;

/**
 * Gives values relative to Input. Those values are based on the host (its screen size,)
 * Values can be tweaked by the user. TODO how?
 * Long press time out for example.
 * 
 * Class registers for Device update event, in which case, every value is invalidated.
 * <br>
 * 
 * {@link IEventsBentleyFw#PID_01_DEVICE_05_UPDATE}
 * 
 * 
 * 
 * @author Charles Bentley
 *
 */
public class InputSettings implements IInput, IEventConsumer, ITechHostUI, IStringable {

   private int               allerRetourMinAmplitude;

   private int               allerRetourSlop;

   protected final CoreUiCtx cuc;

   private int               dragIntention;

   private int               flingSpeedMax;

   private int               flingSpeedMin;

   private int               keyDoubleTimeout     = 0;

   private int               keyFastType          = 0;

   private int               keyRepeatDelay       = 0;

   private int               keyRepeatTimeOut     = 0;

   private int               longPressTimeOut     = 0;

   private int               pointerDoubleSlop;

   private int               pointerDoubleTimeout = 0;

   private int               pointerDragSlop      = 0;

   private int               simultaneousTimeout;

   private int               slideMargin;

   private int               slideMinAmplitude;

   public InputSettings(CoreUiCtx fc) {
      this.cuc = fc;
      cuc.getEventBus().addConsumer(this, IEventsCoreUI.PID_01_DEVICE, IEventsCoreUI.PID_01_DEVICE_05_UPDATE);
   }

   public void consumeEvent(BusEvent e) {
      if (e.getEventID() == IEventsCoreUI.PID_01_DEVICE_05_UPDATE) {
         //reset all
         longPressTimeOut = 0;
         keyRepeatTimeOut = 0;
         keyRepeatDelay = 0;
      }
   }

   public int getAllerRetourMinAmplitude() {
      if (allerRetourMinAmplitude == 0) {
         allerRetourMinAmplitude = cuc.getHostInt(DATA_ID_15_ALLER_RETOUR_MIN_AMPLITUDE);
         if (allerRetourMinAmplitude == 0) {
            allerRetourMinAmplitude = ITechInputConstants.BF_ALLER_RETOUR_MIN_AMPLITUDE;
         }
      }
      return allerRetourMinAmplitude;
   }

   public int getAllerRetourSlop() {
      if (allerRetourSlop == 0) {
         allerRetourSlop = cuc.getHostInt(DATA_ID_14_ALLER_RETOUR_SLOP);
         if (allerRetourSlop == 0) {
            allerRetourSlop = ITechInputConstants.BF_ALLER_RETOUR_SLOP;
         }
      }
      return allerRetourSlop;
   }

   /**
    * Maximum velocity to initiate a fling, as measured in pixels per second. 
    * @return
    */
   public int getFlingSpeedMax() {
      if (flingSpeedMax == 0) {
         flingSpeedMax = cuc.getHostInt(DATA_ID_11_FLING_SPEED_MAX);
         if (flingSpeedMax == 0) {
            flingSpeedMax = ITechInputConstants.BF_FLING_SPEED_MAX;
         }
      }
      return flingSpeedMax;
   }

   /**
    * Minimum velocity to initiate a fling, as measured in pixels per second.
    * @return
    */
   public int getFlingSpeedMin() {
      if (flingSpeedMin == 0) {
         flingSpeedMin = cuc.getHostInt(DATA_ID_12_FLING_SPEED_MIN);
         if (flingSpeedMin == 0) {
            flingSpeedMin = ITechInputConstants.BF_FLING_SPEED_MIN;
         }
      }
      return flingSpeedMin;
   }

   public int getKeyFastTypeTimeout() {
      if (keyFastType == 0) {
         keyFastType = cuc.getHostInt(DATA_ID_26_KEY_FAST_TYPE_TIMEOUT);
         if (keyFastType <= 0) {
            keyFastType = ITechInputConstants.BF_TIME_FAST_TYPE;
         }
      }
      return keyFastType;

   }

   public int getKeyNupleTimeout() {
      if (keyDoubleTimeout == 0) {
         keyDoubleTimeout = cuc.getHostInt(DATA_ID_27_KEY_NUPLE_TIMEOUT);
         if (keyDoubleTimeout == 0) {
            keyDoubleTimeout = ITechInputConstants.BF_KEY_DOUBLE_TIMEOUT;
         }
      }
      return keyDoubleTimeout;
   }

   /**
    * the time between successive key repeats in milliseconds. 
    * @return
    */
   public int getKeyRepeatDelay() {
      if (keyRepeatDelay == 0) {
         keyRepeatDelay = cuc.getHostInt(DATA_ID_25_KEY_REPEAT_DELAY);
         if (keyRepeatDelay <= 0) {
            keyRepeatDelay = ITechInputConstants.BF_KEY_REPEAT_DELAY;
         }
      }
      return keyRepeatDelay;
   }

   /**
    * the time before the first key repeat in milliseconds. 
    * @return
    */
   public int getKeyRepeatTimeout() {
      if (keyRepeatTimeOut == 0) {
         //compute it
         keyRepeatTimeOut = cuc.getHostInt(DATA_ID_24_KEY_REPEAT_TIMEOUT);
         if (keyRepeatTimeOut <= 0) {
            keyRepeatTimeOut = ITechInputConstants.BF_KEY_REPEAT_TIMEOUT;
         }
      }
      return keyRepeatTimeOut;
   }

   /**
    * 
    * @return
    */
   public int getNumStartPointers() {
      int v = cuc.getHostInt(DATA_ID_06_POINTER_FAST_TYPE_TIMEOUT);
      if (v == 0) {
         v = 1;
      }
      return v;
   }

   /**
    * Distance in pixels a touch can wander before we think the user is scrolling 
    * @return
    */
   public int getPointerDragSlop() {
      if (pointerDragSlop == 0) {
         //always read it from framework.
         pointerDragSlop = cuc.getHostInt(DATA_ID_04_POINTER_DRAG_SLOP);
         if (pointerDragSlop == 0) {
            throw new IllegalStateException();
         }
      }
      return pointerDragSlop;
   }

   /**
    * the duration in milliseconds before a press turns into a long press 
    * @return
    */
   public int getPointerLongTimeout() {
      if (longPressTimeOut == 0) {
         longPressTimeOut = cuc.getHostInt(DATA_ID_01_POINTER_LONG_TIMEOUT);
         if (longPressTimeOut <= 0) {
            longPressTimeOut = ITechInputConstants.BF_LONG_PRESS_TIMEOUT;
         }
      }
      return longPressTimeOut;
   }

   /**
    * Distance in pixels between the first touch and second touch to still be considered a double tap
    * @return
    */
   public int getPointerNupleSlop() {
      if (pointerDoubleSlop == 0) {
         pointerDoubleSlop = cuc.getHostInt(DATA_ID_02_POINTER_NUPLE_SLOP);
         if (pointerDoubleSlop <= 0) {
            pointerDoubleSlop = ITechInputConstants.BF_POINTER_DOUBLE_SLOP;
         }
      }
      return pointerDoubleSlop;
   }

   public int getPointerNupleTimeout() {
      if (pointerDoubleTimeout == 0) {
         if (pointerDoubleTimeout <= 0) {
            pointerDoubleTimeout = ITechInputConstants.BF_POINTER_DOUBLE_TIMEOUT;
         }
      }
      return pointerDoubleTimeout;
   }

   public int getSimultaneousTimeOut() {
      if (simultaneousTimeout == 0) {
         simultaneousTimeout = cuc.getHostInt(DATA_ID_10_SIMULTANEOUS_TIMEOUT);
         if (simultaneousTimeout == 0) {
            simultaneousTimeout = ITechInputConstants.BF_SIMULTANEOUS_TIMEOUT;
         }
      }
      return simultaneousTimeout;
   }

   /**
    * Minimum of Drag before Slide is registered....
    * <li> host dependant for the default values
    * <li> code dependant. some commands will want slide asap.
    * <br>
    * @return
    */
   public int getSlideMinAmplitude() {
      if (slideMinAmplitude == 0) {
         slideMinAmplitude = cuc.getHostInt(DATA_ID_09_SLIDE_MIN_AMPLITUDE);
         if (slideMinAmplitude == 0) {
            slideMinAmplitude = ITechInputConstants.BF_SLIDE_MIN_AMPLITUDE;
         }
      }
      return slideMinAmplitude;
   }

   /**
    * Pixels deflection until the horizontal/vertical slide is invalidated as being "straight".
    * <br>
    * @return
    */
   public int getXYAxisSlop() {
      if (slideMargin == 0) {
         slideMargin = ITechInputConstants.BF_SLIDE_MARGIN;
      }
      return slideMargin;
   }

   //#mdebug

   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, InputSettings.class);
      dc.appendVarWithNewLine("AllerRetourMinAmplitude", getAllerRetourMinAmplitude());
      dc.appendVarWithNewLine("AllerRetourSlop", getAllerRetourSlop());
      dc.appendVarWithNewLine("FlingSpeedMax", getFlingSpeedMax());
      dc.appendVarWithNewLine("FlingSpeedMin", getFlingSpeedMin());
      dc.appendVarWithNewLine("KeyFastTypeTimeout", getKeyFastTypeTimeout());
      dc.appendVarWithNewLine("KeyNupleTimeout", getKeyNupleTimeout());
      dc.appendVarWithNewLine("KeyRepeatDelay", getKeyRepeatDelay());
      dc.nl();
      dc.appendVarWithNewLine("NumStartPointers", getNumStartPointers());
      dc.appendVarWithNewLine("PointerDragSlop", getPointerDragSlop());
      dc.appendVarWithNewLine("PointerLongTimeout", getPointerLongTimeout());
      dc.appendVarWithNewLine("PointerNupleSlop", getPointerNupleSlop());
      dc.appendVarWithNewLine("PointerNupleTimeout", getPointerNupleTimeout());
      dc.appendVarWithNewLine("SimultaneousTimeOut", getSimultaneousTimeOut());
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "InputSettings");
   }
   //#enddebug

   public UCtx toStringGetUCtx() {
      return cuc.getUCtx();
   }

}
