/*     */ package com.Plugin.jnbt;
/*     */ 
/*     */ import java.io.Closeable;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.util.List;
/*     */ import java.util.zip.GZIPOutputStream;
/*     */ 
/*     */ public final class NBTOutputStream
/*     */   implements Closeable
/*     */ {
/*     */   private final DataOutputStream os;
/*     */ 
/*     */   public NBTOutputStream(OutputStream os)
/*     */     throws IOException
/*     */   {
/*  89 */     this.os = new DataOutputStream(new GZIPOutputStream(os));
/*     */   }
/*     */ 
/*     */   public void writeTag(Tag tag)
/*     */     throws IOException
/*     */   {
/* 101 */     int type = NBTUtils.getTypeCode(tag.getClass());
/* 102 */     String name = tag.getName();
/* 103 */     byte[] nameBytes = name.getBytes(NBTConstants.CHARSET);
/*     */ 
/* 105 */     this.os.writeByte(type);
/* 106 */     this.os.writeShort(nameBytes.length);
/* 107 */     this.os.write(nameBytes);
/*     */ 
/* 109 */     if (type == 0) {
/* 110 */       throw new IOException("Named TAG_End not permitted.");
/*     */     }
/*     */ 
/* 113 */     writeTagPayload(tag);
/*     */   }
/*     */ 
/*     */   private void writeTagPayload(Tag tag)
/*     */     throws IOException
/*     */   {
/* 125 */     int type = NBTUtils.getTypeCode(tag.getClass());
/* 126 */     switch (type) {
/*     */     case 0:
/* 128 */       writeEndTagPayload((EndTag)tag);
/* 129 */       break;
/*     */     case 1:
/* 131 */       writeByteTagPayload((ByteTag)tag);
/* 132 */       break;
/*     */     case 2:
/* 134 */       writeShortTagPayload((ShortTag)tag);
/* 135 */       break;
/*     */     case 3:
/* 137 */       writeIntTagPayload((IntTag)tag);
/* 138 */       break;
/*     */     case 4:
/* 140 */       writeLongTagPayload((LongTag)tag);
/* 141 */       break;
/*     */     case 5:
/* 143 */       writeFloatTagPayload((FloatTag)tag);
/* 144 */       break;
/*     */     case 6:
/* 146 */       writeDoubleTagPayload((DoubleTag)tag);
/* 147 */       break;
/*     */     case 7:
/* 149 */       writeByteArrayTagPayload((ByteArrayTag)tag);
/* 150 */       break;
/*     */     case 8:
/* 152 */       writeStringTagPayload((StringTag)tag);
/* 153 */       break;
/*     */     case 9:
/* 155 */       writeListTagPayload((ListTag)tag);
/* 156 */       break;
/*     */     case 10:
/* 158 */       writeCompoundTagPayload((CompoundTag)tag);
/* 159 */       break;
/*     */     case 11:
/* 161 */       writeIntArrayTagPayload((IntArrayTag)tag);
/* 162 */       break;
/*     */     default:
/* 164 */       throw new IOException("Invalid tag type: " + type + ".");
/*     */     }
/*     */   }
/*     */ 
/*     */   private void writeByteTagPayload(ByteTag tag)
/*     */     throws IOException
/*     */   {
/* 177 */     this.os.writeByte(tag.getValue().byteValue());
/*     */   }
/*     */ 
/*     */   private void writeByteArrayTagPayload(ByteArrayTag tag)
/*     */     throws IOException
/*     */   {
/* 189 */     byte[] bytes = tag.getValue();
/* 190 */     this.os.writeInt(bytes.length);
/* 191 */     this.os.write(bytes);
/*     */   }
/*     */ 
/*     */   private void writeCompoundTagPayload(CompoundTag tag)
/*     */     throws IOException
/*     */   {
/* 203 */     for (Tag childTag : tag.getValue().values()) {
/* 204 */       writeTag(childTag);
/*     */     }
/* 206 */     this.os.writeByte(0);
/*     */   }
/*     */ 
/*     */   @SuppressWarnings("unchecked")
private void writeListTagPayload(ListTag tag)
/*     */     throws IOException
/*     */   {
/* 218 */     @SuppressWarnings("rawtypes")
			  Class clazz = tag.getType();
/* 219 */     List<Tag> tags = tag.getValue();
/* 220 */     int size = tags.size();
/* 222 */     this.os.writeByte(NBTUtils.getTypeCode(clazz));
/* 223 */     this.os.writeInt(size);
/* 224 */     for (int i = 0; i < size; i++)
/* 225 */       writeTagPayload((Tag)tags.get(i));
/*     */   }
/*     */ 
/*     */   private void writeStringTagPayload(StringTag tag)
/*     */     throws IOException
/*     */   {
/* 238 */     byte[] bytes = tag.getValue().getBytes(NBTConstants.CHARSET);
/* 239 */     this.os.writeShort(bytes.length);
/* 240 */     this.os.write(bytes);
/*     */   }
/*     */ 
/*     */   private void writeDoubleTagPayload(DoubleTag tag)
/*     */     throws IOException
/*     */   {
/* 252 */     this.os.writeDouble(tag.getValue().doubleValue());
/*     */   }
/*     */ 
/*     */   private void writeFloatTagPayload(FloatTag tag)
/*     */     throws IOException
/*     */   {
/* 264 */     this.os.writeFloat(tag.getValue().floatValue());
/*     */   }
/*     */ 
/*     */   private void writeLongTagPayload(LongTag tag)
/*     */     throws IOException
/*     */   {
/* 276 */     this.os.writeLong(tag.getValue().longValue());
/*     */   }
/*     */ 
/*     */   private void writeIntTagPayload(IntTag tag)
/*     */     throws IOException
/*     */   {
/* 288 */     this.os.writeInt(tag.getValue().intValue());
/*     */   }
/*     */ 
/*     */   private void writeShortTagPayload(ShortTag tag)
/*     */     throws IOException
/*     */   {
/* 300 */     this.os.writeShort(tag.getValue().shortValue());
/*     */   }
/*     */ 
/*     */   private void writeEndTagPayload(EndTag tag)
/*     */   {
/*     */   }
/*     */ 
/*     */   private void writeIntArrayTagPayload(IntArrayTag tag)
/*     */     throws IOException
/*     */   {
/* 316 */     int[] data = tag.getValue();
/* 317 */     this.os.writeInt(data.length);
/* 318 */     for (int i = 0; i < data.length; i++)
/* 319 */       this.os.writeInt(data[i]);
/*     */   }
/*     */ 
/*     */   public void close() throws IOException
/*     */   {
/* 324 */     this.os.close();
/*     */   }
/*     */ }

/* Location:           C:\Users\Simon's Account\Desktop\PluginTesting\plugins\WorldEdit.jar
 * Qualified Name:     com.sk89q.jnbt.NBTOutputStream
 * JD-Core Version:    0.6.2
 */