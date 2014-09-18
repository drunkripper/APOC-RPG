/*     */ package com.Plugin.jnbt;
/*     */ 
/*     */ import java.nio.charset.Charset;
/*     */ 
/*     */ public final class NBTConstants
/*     */ {
/*  49 */   public static final Charset CHARSET = Charset.forName("UTF-8");
/*     */   public static final int TYPE_END = 0;
/*     */   public static final int TYPE_BYTE = 1;
/*     */   public static final int TYPE_SHORT = 2;
/*     */   public static final int TYPE_INT = 3;
/*     */   public static final int TYPE_LONG = 4;
/*     */   public static final int TYPE_FLOAT = 5;
/*     */   public static final int TYPE_DOUBLE = 6;
/*     */   public static final int TYPE_BYTE_ARRAY = 7;
/*     */   public static final int TYPE_STRING = 8;
/*     */   public static final int TYPE_LIST = 9;
/*     */   public static final int TYPE_COMPOUND = 10;
/*     */   public static final int TYPE_INT_ARRAY = 11;
/*     */ 
/*     */   public static Class<? extends Tag> getClassFromType(int id)
/*     */   {
/*  74 */     switch (id) {
/*     */     case 0:
/*  76 */       return EndTag.class;
/*     */     case 1:
/*  78 */       return ByteTag.class;
/*     */     case 2:
/*  80 */       return ShortTag.class;
/*     */     case 3:
/*  82 */       return IntTag.class;
/*     */     case 4:
/*  84 */       return LongTag.class;
/*     */     case 5:
/*  86 */       return FloatTag.class;
/*     */     case 6:
/*  88 */       return DoubleTag.class;
/*     */     case 7:
/*  90 */       return ByteArrayTag.class;
/*     */     case 8:
/*  92 */       return StringTag.class;
/*     */     case 9:
/*  94 */       return ListTag.class;
/*     */     case 10:
/*  96 */       return CompoundTag.class;
/*     */     case 11:
/*  98 */       return IntArrayTag.class;
/*     */     }
/* 100 */     throw new IllegalArgumentException("Unknown tag type ID of " + id);
/*     */   }
/*     */ }

/* Location:           C:\Users\Simon's Account\Desktop\PluginTesting\plugins\WorldEdit.jar
 * Qualified Name:     com.sk89q.jnbt.NBTConstants
 * JD-Core Version:    0.6.2
 */