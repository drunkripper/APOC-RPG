/*    */ package com.APOCRPG.jnbt;
/*    */ 
/*    */ public final class IntArrayTag extends Tag
/*    */ {
/*    */   private final int[] value;
/*    */ 
/*    */   public IntArrayTag(String name, int[] value)
/*    */   {
/* 60 */     super(name);
/* 61 */     this.value = value;
/*    */   }
/*    */ 
/*    */   public int[] getValue()
/*    */   {
/* 66 */     return this.value;
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 71 */     StringBuilder hex = new StringBuilder();
/* 72 */     for (int b : this.value) {
/* 73 */       String hexDigits = Integer.toHexString(b).toUpperCase();
/* 74 */       if (hexDigits.length() == 1) {
/* 75 */         hex.append("0");
/*    */       }
/* 77 */       hex.append(hexDigits).append(" ");
/*    */     }
/* 79 */     String name = getName();
/* 80 */     String append = "";
/* 81 */     if ((name != null) && (!name.equals(""))) {
/* 82 */       append = new StringBuilder().append("(\"").append(getName()).append("\")").toString();
/*    */     }
/* 84 */     return new StringBuilder().append("TAG_Int_Array").append(append).append(": ").append(hex.toString()).toString();
/*    */   }
/*    */ }

/* Location:           C:\Users\Simon's Account\Desktop\PluginTesting\plugins\WorldEdit.jar
 * Qualified Name:     com.sk89q.jnbt.IntArrayTag
 * JD-Core Version:    0.6.2
 */