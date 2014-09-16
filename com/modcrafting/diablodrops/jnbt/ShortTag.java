/*    */ package com.modcrafting.diablodrops.jnbt;
/*    */ 
/*    */ public final class ShortTag extends Tag
/*    */ {
/*    */   private final short value;
/*    */ 
/*    */   public ShortTag(String name, short value)
/*    */   {
/* 60 */     super(name);
/* 61 */     this.value = value;
/*    */   }
/*    */ 
/*    */   public Short getValue()
/*    */   {
/* 66 */     return Short.valueOf(this.value);
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 71 */     String name = getName();
/* 72 */     String append = "";
/* 73 */     if ((name != null) && (!name.equals(""))) {
/* 74 */       append = "(\"" + getName() + "\")";
/*    */     }
/* 76 */     return "TAG_Short" + append + ": " + this.value;
/*    */   }
/*    */ }

/* Location:           C:\Users\Simon's Account\Desktop\PluginTesting\plugins\WorldEdit.jar
 * Qualified Name:     com.sk89q.jnbt.ShortTag
 * JD-Core Version:    0.6.2
 */