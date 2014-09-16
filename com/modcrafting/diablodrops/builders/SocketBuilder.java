/*    */ package com.modcrafting.diablodrops.builders;
/*    */ 
/*    */ import com.modcrafting.diablodrops.DiabloDrops;
/*    */ import com.modcrafting.diablodrops.items.IdentifyTome;
/*    */ import java.util.List;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.inventory.FurnaceRecipe;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ import org.bukkit.inventory.ShapelessRecipe;
/*    */ 
/*    */ public class SocketBuilder
/*    */ {
/*    */   DiabloDrops plugin;
/*    */ 
/*    */   public SocketBuilder(DiabloDrops plugin)
/*    */   {
/* 19 */     this.plugin = plugin;
/*    */   }
/*    */ 
/*    */   public void build()
/*    */   {
/* 28 */     List<String> l = this.plugin.getConfig().getStringList("SocketItem.Items");
/* 29 */     for (String name : l)
/*    */     {
/* 31 */       for (Material mat : this.plugin.getItemAPI().allItems())
/*    */       {
/* 33 */         FurnaceRecipe recipe = new FurnaceRecipe(new ItemStack(mat), Material.valueOf(name.toUpperCase()));
/*    */ 		 
/* 35 */         recipe.setInput(mat);
/* 36 */         this.plugin.getServer().addRecipe(recipe);
/*    */       }
/*    */     }
/*    */ 
/* 40 */     ShapelessRecipe re = new ShapelessRecipe(new IdentifyTome());
/* 41 */     re.addIngredient(3, Material.BOOK);
/* 42 */     re.addIngredient(Material.EYE_OF_ENDER);
/* 43 */     this.plugin.getServer().addRecipe(re);
/*    */   }
/*    */ }

/* Location:           C:\Users\Simon's Account\Downloads\DiabloDrops(1).jar
 * Qualified Name:     com.modcrafting.diablodrops.builders.SocketBuilder
 * JD-Core Version:    0.6.2
 */