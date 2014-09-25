/*    */ package com.APOCRPG.jnbt;
/*    */ 
/*    */ public final class LongTag extends Tag
/*    */ {
/*    */   private final long value;
/*    */ 
/*    */   public LongTag(String name, long value)
/*    */   {
/* 60 */     super(name);
/* 61 */     this.value = value;
/*    */   }
/*    */ 
/*    */   public Long getValue()
/*    */   {
/* 66 */     return Long.valueOf(this.value);
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 71 */     String name = getName();
/* 72 */     String append = "";
/* 73 */     if ((name != null) && (!name.equals(""))) {
/* 74 */       append = "(\"" + getName() + "\")";
/*    */     }
/* 76 */     return "TAG_Long" + append + ": " + this.value;
/*    */   }
/*    */ }

/* Location:           C:\Users\Simon's Account\Desktop\PluginTesting\plugins\WorldEdit.jar
 * Qualified Name:     com.sk89q.jnbt.LongTag
 * JD-Core Version:    0.6.2
 */