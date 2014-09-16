/*    */ package com.modcrafting.diablodrops.jnbt;
/*    */ 
/*    */ public final class IntTag extends Tag
/*    */ {
/*    */   private final int value;
/*    */ 
/*    */   public IntTag(String name, int value)
/*    */   {
/* 60 */     super(name);
/* 61 */     this.value = value;
/*    */   }
/*    */ 
/*    */   public Integer getValue()
/*    */   {
/* 66 */     return Integer.valueOf(this.value);
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 71 */     String name = getName();
/* 72 */     String append = "";
/* 73 */     if ((name != null) && (!name.equals(""))) {
/* 74 */       append = "(\"" + getName() + "\")";
/*    */     }
/* 76 */     return "TAG_Int" + append + ": " + this.value;
/*    */   }
/*    */ }

/* Location:           C:\Users\Simon's Account\Desktop\PluginTesting\plugins\WorldEdit.jar
 * Qualified Name:     com.sk89q.jnbt.IntTag
 * JD-Core Version:    0.6.2
 */