/*     */ package com.Plugin.jnbt;
/*     */ 
/*     */ import java.io.Closeable;
/*     */ import java.io.DataInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public final class NBTInputStream
/*     */   implements Closeable
/*     */ {
/*     */   private final DataInputStream is;
/*     */ 
/*     */   public NBTInputStream(InputStream is)
/*     */     throws IOException
/*     */   {
/*  92 */     this.is = new DataInputStream(is);
/*     */   }
/*     */ 
/*     */   public Tag readTag()
/*     */     throws IOException
/*     */   {
/* 103 */     return readTag(0);
/*     */   }
/*     */ 
/*     */   private Tag readTag(int depth)
/*     */     throws IOException
/*     */   {
/* 116 */     int type = this.is.readByte() & 0xFF;
/*     */     String name;
/* 119 */     if (type != 0) {
/* 120 */       int nameLength = this.is.readShort() & 0xFFFF;
/* 121 */       byte[] nameBytes = new byte[nameLength];
/* 122 */       this.is.readFully(nameBytes);
/* 123 */       name = new String(nameBytes, NBTConstants.CHARSET);
/*     */     } else {
/* 125 */       name = "";
/*     */     }
/*     */ 
/* 128 */     return readTagPayload(type, name, depth);
/*     */   }
/*     */ 
/*     */   private Tag readTagPayload(int type, String name, int depth)
/*     */     throws IOException
/*     */   {
/*     */     int length;
/*     */     byte[] bytes;
/* 146 */     switch (type) {
/*     */     case 0:
/* 148 */       if (depth == 0) {
/* 149 */         throw new IOException("TAG_End found without a TAG_Compound/TAG_List tag preceding it.");
/*     */       }
/*     */ 
/* 152 */       return new EndTag();
/*     */     case 1:
/* 155 */       return new ByteTag(name, this.is.readByte());
/*     */     case 2:
/* 157 */       return new ShortTag(name, this.is.readShort());
/*     */     case 3:
/* 159 */       return new IntTag(name, this.is.readInt());
/*     */     case 4:
/* 161 */       return new LongTag(name, this.is.readLong());
/*     */     case 5:
/* 163 */       return new FloatTag(name, this.is.readFloat());
/*     */     case 6:
/* 165 */       return new DoubleTag(name, this.is.readDouble());
/*     */     case 7:
/* 167 */       length = this.is.readInt();
/* 168 */       bytes = new byte[length];
/* 169 */       this.is.readFully(bytes);
/* 170 */       return new ByteArrayTag(name, bytes);
/*     */     case 8:
/* 172 */       length = this.is.readShort();
/* 173 */       bytes = new byte[length];
/* 174 */       this.is.readFully(bytes);
/* 175 */       return new StringTag(name, new String(bytes, NBTConstants.CHARSET));
/*     */     case 9:
/* 177 */       int childType = this.is.readByte();
/* 178 */       length = this.is.readInt();
/*     */ 
/* 180 */       List<Tag> tagList = new ArrayList<Tag>();
/* 181 */       for (int i = 0; i < length; i++) {
/* 182 */         Tag tag = readTagPayload(childType, "", depth + 1);
/* 183 */         if ((tag instanceof EndTag)) {
/* 184 */           throw new IOException("TAG_End not permitted in a list.");
/*     */         }
/* 186 */         tagList.add(tag);
/*     */       }
/*     */ 
/* 189 */       return new ListTag(name, NBTUtils.getTypeClass(childType), tagList);
/*     */     case 10:
/* 191 */       Map<String, Tag> tagMap = new HashMap<String, Tag>();
/*     */       while (true) {
/* 193 */         Tag tag = readTag(depth + 1);
/* 194 */         if ((tag instanceof EndTag)) {
/*     */           break;
/*     */         }
/* 197 */         tagMap.put(tag.getName(), tag);
/*     */       }
/*     */ 
/* 201 */       return new CompoundTag(name, tagMap);
/*     */     case 11:
/* 203 */       length = this.is.readInt();
/* 204 */       int[] data = new int[length];
/* 205 */       for (int i = 0; i < length; i++) {
/* 206 */         data[i] = this.is.readInt();
/*     */       }
/* 208 */       return new IntArrayTag(name, data);
/*     */     }
/* 210 */     throw new IOException("Invalid tag type: " + type + ".");
/*     */   }
/*     */ 
/*     */   public void close() throws IOException
/*     */   {
/* 215 */     this.is.close();
/*     */   }
/*     */ }

/* Location:           C:\Users\Simon's Account\Desktop\PluginTesting\plugins\WorldEdit.jar
 * Qualified Name:     com.sk89q.jnbt.NBTInputStream
 * JD-Core Version:    0.6.2
 */