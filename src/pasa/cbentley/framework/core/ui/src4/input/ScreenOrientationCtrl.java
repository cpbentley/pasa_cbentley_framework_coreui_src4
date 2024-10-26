package pasa.cbentley.framework.core.ui.src4.input;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.framework.core.ui.src4.ctx.CoreUiCtx;
import pasa.cbentley.framework.core.ui.src4.ctx.ObjectCUC;
import pasa.cbentley.framework.core.ui.src4.ctx.ToStringStaticCoreUi;
import pasa.cbentley.framework.core.ui.src4.engine.CanvasAppliAbstract;
import pasa.cbentley.framework.core.ui.src4.event.EventCanvasHost;
import pasa.cbentley.framework.core.ui.src4.interfaces.ITechEventHost;
import pasa.cbentley.framework.core.ui.src4.tech.ITechCodes;
import pasa.cbentley.framework.core.ui.src4.tech.ITechHostUI;

/**
 * The screen control class
 * @author Charles Bentley
 *
 */
public class ScreenOrientationCtrl extends ObjectCUC implements IStringable {

   private CanvasAppliAbstract canvas;

   private int              screenConfig;

   public ScreenOrientationCtrl(CoreUiCtx ic, CanvasAppliAbstract canvas) {
      super(ic);
      this.canvas = canvas;
   }

   /**
    * Paint method
    * 
    * @return
    */
   public int getOrientation() {
      return screenConfig;
   }

   public boolean isRotated() {
      return screenConfig != ITechHostUI.SCREEN_0_TOP_NORMAL;
   }

   /**
    * Check if Host had its own way to flipping the screen
    */
   public void rotate(int newConfig) {
      boolean supportRot = canvas.hasCanvasFeatureSupport(ITechHostUI.SUP_ID_05_SCREEN_ROTATIONS);
      //Can device Android can check if screen rotation is locked?
      if (supportRot) {
         //check if hardware did right. otherwise use 
      } else {
         //ask the canvas to deal with new config
      }
      //canvas.postRotation();
      
      int oldw = canvas.getWidth();
      int oldh = canvas.getHeight();
      //size for the image layer
      int w = 0;
      int h = 0;
      if (newConfig == ITechHostUI.SCREEN_1_BOT_UPSIDEDOWN || newConfig == ITechHostUI.SCREEN_0_TOP_NORMAL) {
         w = canvas.getWidth();
         h = canvas.getHeight();
      } else {
         w = canvas.getHeight();
         h = canvas.getWidth();
      }
      EventCanvasHost ch = new EventCanvasHost(cuc, ITechEventHost.ACTION_03_RESIZED, canvas.getCanvasHost());
      ch.setW(w);
      ch.setH(h);
      canvas.event(ch);

   }

   /**
    * Called
    */
   public void rotateLeft() {
      if (screenConfig == ITechHostUI.SCREEN_0_TOP_NORMAL) {
         screenConfig = ITechHostUI.SCREEN_2_LEFT_ROTATED;
      } else if (screenConfig == ITechHostUI.SCREEN_2_LEFT_ROTATED) {
         screenConfig = ITechHostUI.SCREEN_1_BOT_UPSIDEDOWN;
      } else if (screenConfig == ITechHostUI.SCREEN_1_BOT_UPSIDEDOWN) {
         screenConfig = ITechHostUI.SCREEN_3_RIGHT_ROTATED;
      } else {
         screenConfig = ITechHostUI.SCREEN_0_TOP_NORMAL;
      }
      rotate(screenConfig);
   }

   public void rotateRight() {
      if (screenConfig == ITechHostUI.SCREEN_0_TOP_NORMAL) {
         screenConfig = ITechHostUI.SCREEN_3_RIGHT_ROTATED;
      } else if (screenConfig == ITechHostUI.SCREEN_3_RIGHT_ROTATED) {
         screenConfig = ITechHostUI.SCREEN_1_BOT_UPSIDEDOWN;
      } else if (screenConfig == ITechHostUI.SCREEN_1_BOT_UPSIDEDOWN) {
         screenConfig = ITechHostUI.SCREEN_2_LEFT_ROTATED;
      } else {
         screenConfig = ITechHostUI.SCREEN_0_TOP_NORMAL;
      }
      rotate(screenConfig);
   }

  public int rotationKeyChange(int gk) {
      if (gk == ITechCodes.KEY_UP) {
         if (screenConfig == ITechHostUI.SCREEN_2_LEFT_ROTATED) {
            gk = ITechCodes.KEY_LEFT;
         } else if (screenConfig == ITechHostUI.SCREEN_3_RIGHT_ROTATED) {
            gk = ITechCodes.KEY_RIGHT;
         } else if (screenConfig == ITechHostUI.SCREEN_1_BOT_UPSIDEDOWN) {
            gk = ITechCodes.KEY_DOWN;
         }
      } else if (gk == ITechCodes.KEY_DOWN) {
         if (screenConfig == ITechHostUI.SCREEN_2_LEFT_ROTATED) {
            gk = ITechCodes.KEY_RIGHT;
         } else if (screenConfig == ITechHostUI.SCREEN_3_RIGHT_ROTATED) {
            gk = ITechCodes.KEY_LEFT;
         } else if (screenConfig == ITechHostUI.SCREEN_1_BOT_UPSIDEDOWN) {
            gk = ITechCodes.KEY_UP;
         }
      } else if (gk == ITechCodes.KEY_LEFT) {
         if (screenConfig == ITechHostUI.SCREEN_2_LEFT_ROTATED) {
            gk = ITechCodes.KEY_DOWN;
         } else if (screenConfig == ITechHostUI.SCREEN_3_RIGHT_ROTATED) {
            gk = ITechCodes.KEY_UP;
         } else if (screenConfig == ITechHostUI.SCREEN_1_BOT_UPSIDEDOWN) {
            gk = ITechCodes.KEY_DOWN;
         }
      } else if (gk == ITechCodes.KEY_RIGHT) {
         if (screenConfig == ITechHostUI.SCREEN_2_LEFT_ROTATED) {
            gk = ITechCodes.KEY_UP;
         } else if (screenConfig == ITechHostUI.SCREEN_3_RIGHT_ROTATED) {
            gk = ITechCodes.KEY_DOWN;
         } else if (screenConfig == ITechHostUI.SCREEN_1_BOT_UPSIDEDOWN) {
            gk = ITechCodes.KEY_UP;
         }
      }
      return gk;
   }

  public  int rotationXChange(int x, int y) {
      if (screenConfig == ITechHostUI.SCREEN_2_LEFT_ROTATED) {
         return y;
      } else if (screenConfig == ITechHostUI.SCREEN_3_RIGHT_ROTATED) {
         // 0,0 = 0,y
         return canvas.getWidth() - y;
      } else if (screenConfig == ITechHostUI.SCREEN_1_BOT_UPSIDEDOWN) {
         // 0,0 = x,y
         return canvas.getWidth() - x;
      }
      return x;
   }

  public int rotationYChange(int x, int y) {
      if (screenConfig == ITechHostUI.SCREEN_2_LEFT_ROTATED) {
         return canvas.getHeight() - x;
      } else if (screenConfig == ITechHostUI.SCREEN_3_RIGHT_ROTATED) {
         return x;
      } else if (screenConfig == ITechHostUI.SCREEN_1_BOT_UPSIDEDOWN) {
         return canvas.getHeight() - y;
      }
      return y;
   }


   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, ScreenOrientationCtrl.class, 170);
      toStringPrivate(dc);
      super.toString(dc.sup());
   }

   private void toStringPrivate(Dctx dc) {
      dc.appendVarWithSpace("Config", ToStringStaticCoreUi.toStringScreenConfig(screenConfig));
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, ScreenOrientationCtrl.class);
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   //#enddebug
   

   
}
