/*     */ package com.modcrafting.diablodrops.name;
/*     */ 
/*     */ import com.modcrafting.diablodrops.DiabloDrops;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.FileReader;
/*     */ import java.io.InputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Material;
/*     */ 
/*     */ public class NamesLoader
/*     */ {
/*     */   File dataFolder;
/*     */   DiabloDrops plugin;
/*     */ 
/*     */   public NamesLoader(DiabloDrops instance)
/*     */   {
/*  24 */     this.plugin = instance;
/*  25 */     this.dataFolder = instance.getDataFolder();
/*     */   }
/*     */ 
/*     */   public void loadChatColorFile(HashMap<ChatColor, List<String>> hm, File f)
/*     */   {
/*  31 */     ChatColor m = ChatColor.valueOf(f.getName().replace(".txt", "").toUpperCase());
/*     */ 
/*  33 */     List<String> l = new ArrayList<String>();
/*     */     try
/*     */     {
/*  36 */       BufferedReader list = new BufferedReader(new FileReader(f));
/*     */       String p;
/*  38 */       while ((p = list.readLine()) != null)
/*     */       {
/*  40 */         if ((!p.contains("#")) && (p.length() > 0))
/*     */         {
/*  42 */           l.add(p);
/*     */         }
/*     */       }
/*     */ 
/*  46 */       if (m != null)
/*     */       {
/*  48 */         hm.put(m, l);
/*     */       }
/*     */ 
/*  51 */       list.close();
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*  55 */       if (this.plugin.getDebug())
/*     */       {
/*  57 */         this.plugin.log.warning(e.getMessage());
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void loadFile(List<String> l, String name)
/*     */   {
/*     */     try
/*     */     {
/*  74 */       BufferedReader list = new BufferedReader(new FileReader(new File(this.dataFolder, name)));
/*     */       String p;
/*  77 */       while ((p = list.readLine()) != null)
/*     */       {
/*  79 */         if ((!p.contains("#")) && (p.length() > 0))
/*     */         {
/*  81 */           l.add(p);
/*     */         }
/*     */       }
/*  84 */       list.close();
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*  88 */       if (this.plugin.getDebug())
/*     */       {
/*  90 */         this.plugin.log.warning(e.getMessage());
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void loadMaterialFile(HashMap<Material, List<String>> hm, File f)
/*     */   {
/*  98 */     Material m = Material.getMaterial(f.getName().replace(".txt", "").toUpperCase());
/*     */ 
/* 100 */     List<String> l = new ArrayList<String>();
/*     */     try
/*     */     {
/* 103 */       BufferedReader list = new BufferedReader(new FileReader(f));
/*     */       String p;
/* 105 */       while ((p = list.readLine()) != null)
/*     */       {
/* 107 */         if ((!p.contains("#")) && (p.length() > 0))
/*     */         {
/* 109 */           l.add(p);
/*     */         }
/*     */       }
/*     */ 
/* 113 */       if (m != null)
/*     */       {
/* 115 */         hm.put(m, l);
/*     */       }
/*     */       else
/*     */       {
/* 119 */         hm.put(Material.AIR, l);
/*     */       }
/*     */ 
/* 122 */       list.close();
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 126 */       if (this.plugin.getDebug())
/*     */       {
/* 128 */         this.plugin.log.warning(e.getMessage());
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void writeDefault(String name, boolean overwrite)
/*     */   {
/* 141 */     File actual = new File(this.dataFolder, name);
/* 142 */     if (name.contains(".jar"))
/*     */     {
/* 144 */       actual = new File(this.dataFolder.getParent(), name);
/*     */     }
/* 146 */     if ((!actual.exists()) || (overwrite))
/*     */     {
/*     */       try
/*     */       {
/* 150 */         InputStream input = getClass().getResourceAsStream("/" + name);
/*     */ 
/* 152 */         FileOutputStream output = new FileOutputStream(actual, false);
/* 153 */         byte[] buf = new byte[1024];
/* 154 */         int length = 0;
/* 155 */         while ((length = input.read(buf)) > 0)
/*     */         {
/* 157 */           output.write(buf, 0, length);
/*     */         }
/* 159 */         output.close();
/* 160 */         input.close();
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 164 */         if (this.plugin.getDebug())
/*     */         {
/* 166 */           this.plugin.log.warning(e.getMessage());
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Simon's Account\Downloads\DiabloDrops(1).jar
 * Qualified Name:     com.modcrafting.diablodrops.name.NamesLoader
 * JD-Core Version:    0.6.2
 */