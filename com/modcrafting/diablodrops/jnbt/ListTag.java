/*     */ package com.modcrafting.diablodrops.jnbt;
/*     */ 
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ 
/*     */ public final class ListTag extends Tag
/*     */ {
/*     */   private final Class<? extends Tag> type;
/*     */   private final List<Tag> value;
/*     */ 
/*     */   public ListTag(String name, Class<? extends Tag> type, List<? extends Tag> value)
/*     */   {
/*  70 */     super(name);
/*  71 */     this.type = type;
/*  72 */     this.value = Collections.unmodifiableList(value);
/*     */   }
/*     */ 
/*     */   public Class<? extends Tag> getType()
/*     */   {
/*  81 */     return this.type;
/*     */   }
/*     */ 
/*     */   public List<Tag> getValue()
/*     */   {
/*  86 */     return this.value;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  91 */     String name = getName();
/*  92 */     String append = "";
/*  93 */     if ((name != null) && (!name.equals(""))) {
/*  94 */       append = new StringBuilder().append("(\"").append(getName()).append("\")").toString();
/*     */     }
/*  96 */     StringBuilder bldr = new StringBuilder();
/*  97 */     bldr.append(new StringBuilder().append("TAG_List").append(append).append(": ").append(this.value.size()).append(" entries of type ").append(NBTUtils.getTypeName(this.type)).append("\r\n{\r\n").toString());
/*     */ 
/* 100 */     for (Tag t : this.value) {
/* 101 */       bldr.append(new StringBuilder().append("   ").append(t.toString().replaceAll("\r\n", "\r\n   ")).append("\r\n").toString());
/*     */     }
/*     */ 
/* 104 */     bldr.append("}");
/* 105 */     return bldr.toString();
/*     */   }
/*     */ }

/* Location:           C:\Users\Simon's Account\Desktop\PluginTesting\plugins\WorldEdit.jar
 * Qualified Name:     com.sk89q.jnbt.ListTag
 * JD-Core Version:    0.6.2
 */