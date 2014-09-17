/*    */ package com.Plugin.jnbt;
/*    */ 
/*    */ public final class DoubleTag extends Tag
/*    */ {
/*    */   private final double value;
/*    */ 
/*    */   public DoubleTag(String name, double value)
/*    */   {
/* 60 */     super(name);
/* 61 */     this.value = value;
/*    */   }
/*    */ 
/*    */   public Double getValue()
/*    */   {
/* 66 */     return Double.valueOf(this.value);
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 71 */     String name = getName();
/* 72 */     String append = "";
/* 73 */     if ((name != null) && (!name.equals(""))) {
/* 74 */       append = "(\"" + getName() + "\")";
/*    */     }
/* 76 */     return "TAG_Double" + append + ": " + this.value;
/*    */   }
/*    */ }

/* Location:           C:\Users\Simon's Account\Desktop\PluginTesting\plugins\WorldEdit.jar
 * Qualified Name:     com.sk89q.jnbt.DoubleTag
 * JD-Core Version:    0.6.2
 */