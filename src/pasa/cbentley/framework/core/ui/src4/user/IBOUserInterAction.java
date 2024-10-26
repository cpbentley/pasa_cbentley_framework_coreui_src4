package pasa.cbentley.framework.core.ui.src4.user;

import pasa.cbentley.byteobjects.src4.core.interfaces.IByteObject;

public interface IBOUserInterAction extends IByteObject {

   public static final int UIA_BASIC_SIZE      = A_OBJECT_BASIC_SIZE + 10;

   public static final int UIA_OFFSET_01_FLAG  = A_OBJECT_BASIC_SIZE;

   public static final int TYPE_0_COMMAND      = 0;

   public static final int TYPE_1_SOUND        = 1;

   public static final int TYPE_2_ANIM         = 2;

   /**
    * Types
    * <li> {@link IBOUserInterAction#TYPE_0_COMMAND}
    * <li> {@link IBOUserInterAction#TYPE_1_SOUND}
    * <li> {@link IBOUserInterAction#TYPE_2_ANIM}
    * 
    */
   public static final int UIA_OFFSET_02_TYPE1 = A_OBJECT_BASIC_SIZE + 1;

   /**
    * Could be ID in a sound table list or a anim type
    */
   public static final int UIA_OFFSET_03_ID2   = A_OBJECT_BASIC_SIZE + 2;

   public static final int UIA_FLAG_1_REJECTED = 1;

}
