/*    */ package com.Plugin.jnbt;
/*    */ 
/*    */ public final class ByteTag extends Tag
/*    */ {
/*    */   private final byte value;
/*    */ 
/*    */   public ByteTag(String name, byte value)
/*    */   {
/* 60 */     super(name);
/* 61 */     this.value = value;
/*    */   }
/*    */ 
/*    */   public Byte getValue()
/*    */   {
/* 66 */     return Byte.valueOf(this.value);
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 71 */     String name = getName();
/* 72 */     String append = "";
/* 73 */     if ((name != null) && (!name.equals(""))) {
/* 74 */       append = "(\"" + getName() + "\")";
/*    */     }
/* 76 */     return "TAG_Byte" + append + ": " + this.value;
/*    */   }
/*    */ }

/* Location:           C:\Users\Simon's Account\Desktop\PluginTesting\plugins\WorldEdit.jar
 * Qualified Name:     com.sk89q.jnbt.ByteTag
 * JD-Core Version:    0.6.2
 */