/*    */ package com.Plugin.jnbt;
/*    */ 
/*    */ public final class StringTag extends Tag
/*    */ {
/*    */   private final String value;
/*    */ 
/*    */   public StringTag(String name, String value)
/*    */   {
/* 60 */     super(name);
/* 61 */     this.value = value;
/*    */   }
/*    */ 
/*    */   public String getValue()
/*    */   {
/* 66 */     return this.value;
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 71 */     String name = getName();
/* 72 */     String append = "";
/* 73 */     if ((name != null) && (!name.equals(""))) {
/* 74 */       append = "(\"" + getName() + "\")";
/*    */     }
/* 76 */     return "TAG_String" + append + ": " + this.value;
/*    */   }
/*    */ }

/* Location:           C:\Users\Simon's Account\Desktop\PluginTesting\plugins\WorldEdit.jar
 * Qualified Name:     com.sk89q.jnbt.StringTag
 * JD-Core Version:    0.6.2
 */