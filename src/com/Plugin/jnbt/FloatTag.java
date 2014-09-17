/*    */ package com.Plugin.jnbt;
/*    */ 
/*    */ public final class FloatTag extends Tag
/*    */ {
/*    */   private final float value;
/*    */ 
/*    */   public FloatTag(String name, float value)
/*    */   {
/* 60 */     super(name);
/* 61 */     this.value = value;
/*    */   }
/*    */ 
/*    */   public Float getValue()
/*    */   {
/* 66 */     return Float.valueOf(this.value);
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 71 */     String name = getName();
/* 72 */     String append = "";
/* 73 */     if ((name != null) && (!name.equals(""))) {
/* 74 */       append = "(\"" + getName() + "\")";
/*    */     }
/* 76 */     return "TAG_Float" + append + ": " + this.value;
/*    */   }
/*    */ }

/* Location:           C:\Users\Simon's Account\Desktop\PluginTesting\plugins\WorldEdit.jar
 * Qualified Name:     com.sk89q.jnbt.FloatTag
 * JD-Core Version:    0.6.2
 */