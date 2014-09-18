package com.Plugin.jnbt;

/*     */ 
/*     */ 
/*     */ import java.util.Map;
/*     */ 
/*     */ public final class NBTUtils
/*     */ {
/*     */   public static String getTypeName(Class<? extends Tag> clazz)
/*     */   {
/*  69 */     if (clazz.equals(ByteArrayTag.class))
/*  70 */       return "TAG_Byte_Array";
/*  71 */     if (clazz.equals(ByteTag.class))
/*  72 */       return "TAG_Byte";
/*  73 */     if (clazz.equals(CompoundTag.class))
/*  74 */       return "TAG_Compound";
/*  75 */     if (clazz.equals(DoubleTag.class))
/*  76 */       return "TAG_Double";
/*  77 */     if (clazz.equals(EndTag.class))
/*  78 */       return "TAG_End";
/*  79 */     if (clazz.equals(FloatTag.class))
/*  80 */       return "TAG_Float";
/*  81 */     if (clazz.equals(IntTag.class))
/*  82 */       return "TAG_Int";
/*  83 */     if (clazz.equals(ListTag.class))
/*  84 */       return "TAG_List";
/*  85 */     if (clazz.equals(LongTag.class))
/*  86 */       return "TAG_Long";
/*  87 */     if (clazz.equals(ShortTag.class))
/*  88 */       return "TAG_Short";
/*  89 */     if (clazz.equals(StringTag.class))
/*  90 */       return "TAG_String";
/*  91 */     if (clazz.equals(IntArrayTag.class)) {
/*  92 */       return "TAG_Int_Array";
/*     */     }
/*  94 */     throw new IllegalArgumentException("Invalid tag classs (" + clazz.getName() + ").");
/*     */   }
/*     */ 
/*     */   public static int getTypeCode(Class<? extends Tag> clazz)
/*     */   {
/* 109 */     if (clazz.equals(ByteArrayTag.class))
/* 110 */       return 7;
/* 111 */     if (clazz.equals(ByteTag.class))
/* 112 */       return 1;
/* 113 */     if (clazz.equals(CompoundTag.class))
/* 114 */       return 10;
/* 115 */     if (clazz.equals(DoubleTag.class))
/* 116 */       return 6;
/* 117 */     if (clazz.equals(EndTag.class))
/* 118 */       return 0;
/* 119 */     if (clazz.equals(FloatTag.class))
/* 120 */       return 5;
/* 121 */     if (clazz.equals(IntTag.class))
/* 122 */       return 3;
/* 123 */     if (clazz.equals(ListTag.class))
/* 124 */       return 9;
/* 125 */     if (clazz.equals(LongTag.class))
/* 126 */       return 4;
/* 127 */     if (clazz.equals(ShortTag.class))
/* 128 */       return 2;
/* 129 */     if (clazz.equals(StringTag.class))
/* 130 */       return 8;
/* 131 */     if (clazz.equals(IntArrayTag.class)) {
/* 132 */       return 11;
/*     */     }
/* 134 */     throw new IllegalArgumentException("Invalid tag classs (" + clazz.getName() + ").");
/*     */   }
/*     */ 
/*     */   public static Class<? extends Tag> getTypeClass(int type)
/*     */   {
/* 149 */     switch (type) {
/*     */     case 0:
/* 151 */       return EndTag.class;
/*     */     case 1:
/* 153 */       return ByteTag.class;
/*     */     case 2:
/* 155 */       return ShortTag.class;
/*     */     case 3:
/* 157 */       return IntTag.class;
/*     */     case 4:
/* 159 */       return LongTag.class;
/*     */     case 5:
/* 161 */       return FloatTag.class;
/*     */     case 6:
/* 163 */       return DoubleTag.class;
/*     */     case 7:
/* 165 */       return ByteArrayTag.class;
/*     */     case 8:
/* 167 */       return StringTag.class;
/*     */     case 9:
/* 169 */       return ListTag.class;
/*     */     case 10:
/* 171 */       return CompoundTag.class;
/*     */     case 11:
/* 173 */       return IntArrayTag.class;
/*     */     }
/* 175 */     throw new IllegalArgumentException("Invalid tag type : " + type + ".");
/*     */   }
/*     */ 
/*     */   public static <T extends Tag> T getChildTag(Map<String, Tag> items, String key, Class<T> expected)
/*     */     throws InvalidFormatException
/*     */   {
/* 198 */     if (!items.containsKey(key)) {
/* 199 */       throw new InvalidFormatException("Missing a \"" + key + "\" tag");
/*     */     }
/* 201 */     Tag tag = (Tag)items.get(key);
/* 202 */     if (!expected.isInstance(tag)) {
/* 203 */       throw new InvalidFormatException(key + " tag is not of tag type " + expected.getName());
/*     */     }
/* 205 */     return (T)expected.cast(tag);
/*     */   }
/*     */ }

/* Location:           C:\Users\Simon's Account\Desktop\PluginTesting\plugins\WorldEdit.jar
 * Qualified Name:     com.sk89q.jnbt.NBTUtils
 * JD-Core Version:    0.6.2
 */