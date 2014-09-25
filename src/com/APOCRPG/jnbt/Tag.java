/*    */ package com.APOCRPG.jnbt;
/*    */ 
/*    */ public abstract class Tag
/*    */ {
/*    */   private final String name;
/*    */ 
/*    */   public Tag(String name)
/*    */   {
/* 56 */     this.name = name;
/*    */   }
/*    */ 
/*    */   public final String getName()
/*    */   {
/* 65 */     return this.name;
/*    */   }
/*    */ 
/*    */   public abstract Object getValue();
/*    */ }

/* Location:           C:\Users\Simon's Account\Desktop\PluginTesting\plugins\WorldEdit.jar
 * Qualified Name:     com.sk89q.jnbt.Tag
 * JD-Core Version:    0.6.2
 */