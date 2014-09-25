/*    */ package com.APOCRPG.jnbt;
/*    */ 
/*    */ import java.util.Collections;
/*    */ import java.util.Map;
/*    */ 
/*    */ public final class CompoundTag extends Tag
/*    */ {
/*    */   private final Map<String, Tag> value;
/*    */ 
/*    */   public CompoundTag(String name, Map<String, Tag> value)
/*    */   {
/* 62 */     super(name);
/* 63 */     this.value = Collections.unmodifiableMap(value);
/*    */   }
/*    */ 
/*    */   public Map<String, Tag> getValue()
/*    */   {
/* 68 */     return this.value;
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 73 */     String name = getName();
/* 74 */     String append = "";
/* 75 */     if ((name != null) && (!name.equals(""))) {
/* 76 */       append = new StringBuilder().append("(\"").append(getName()).append("\")").toString();
/*    */     }
/* 78 */     StringBuilder bldr = new StringBuilder();
/* 79 */     bldr.append(new StringBuilder().append("TAG_Compound").append(append).append(": ").append(this.value.size()).append(" entries\r\n{\r\n").toString());
/*    */ 
/* 81 */     for (Map.Entry<String, Tag> entry : this.value.entrySet()) {
/* 82 */       bldr.append(new StringBuilder().append("   ").append(((Tag)entry.getValue()).toString().replaceAll("\r\n", "\r\n   ")).append("\r\n").toString());
/*    */     }
/*    */ 
/* 86 */     bldr.append("}");
/* 87 */     return bldr.toString();
/*    */   }
/*    */ }

/* Location:           C:\Users\Simon's Account\Desktop\PluginTesting\plugins\WorldEdit.jar
 * Qualified Name:     com.sk89q.jnbt.CompoundTag
 * JD-Core Version:    0.6.2
 */