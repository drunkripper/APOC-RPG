/*    */ package com.modcrafting.diablodrops.sets;
/*    */ 
/*    */ import java.util.List;
/*    */ 
/*    */ public class ArmorSet
/*    */ {
/*    */   private final List<String> bonuses;
/*    */   private final String name;
/*    */ 
/*    */   public ArmorSet(String name, List<String> bonuses)
/*    */   {
/* 13 */     this.name = name;
/* 14 */     this.bonuses = bonuses;
/*    */   }
/*    */ 
/*    */   public List<String> getBonuses()
/*    */   {
/* 24 */     return this.bonuses;
/*    */   }
/*    */ 
/*    */   public String getName()
/*    */   {
/* 34 */     return this.name;
/*    */   }
/*    */ }

/* Location:           C:\Users\Simon's Account\Downloads\DiabloDrops(1).jar
 * Qualified Name:     com.modcrafting.diablodrops.sets.ArmorSet
 * JD-Core Version:    0.6.2
 */