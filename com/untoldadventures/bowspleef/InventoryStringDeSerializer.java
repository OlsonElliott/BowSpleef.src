/*    */ package com.untoldadventures.bowspleef;
/*    */ 
/*    */ import java.util.Map;
/*    */ import java.util.Map.Entry;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.Server;
/*    */ import org.bukkit.enchantments.Enchantment;
/*    */ import org.bukkit.inventory.Inventory;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ public class InventoryStringDeSerializer
/*    */ {
/*    */   public static String InventoryToString(Inventory invInventory)
/*    */   {
/* 15 */     String serialization = invInventory.getSize() + ";";
/* 16 */     for (int i = 0; i < invInventory.getSize(); i++)
/*    */     {
/* 18 */       ItemStack is = invInventory.getItem(i);
/* 19 */       if (is != null)
/*    */       {
/* 21 */         String serializedItemStack = new String();
/*    */ 
/* 23 */         String isType = String.valueOf(is.getType().getId());
/* 24 */         serializedItemStack = serializedItemStack + "t@" + isType;
/*    */ 
/* 26 */         if (is.getDurability() != 0)
/*    */         {
/* 28 */           String isDurability = String.valueOf(is.getDurability());
/* 29 */           serializedItemStack = serializedItemStack + ":d@" + isDurability;
/*    */         }
/*    */ 
/* 32 */         if (is.getAmount() != 1)
/*    */         {
/* 34 */           String isAmount = String.valueOf(is.getAmount());
/* 35 */           serializedItemStack = serializedItemStack + ":a@" + isAmount;
/*    */         }
/*    */ 
/* 38 */         Map isEnch = is.getEnchantments();
/* 39 */         if (isEnch.size() > 0)
/*    */         {
/* 41 */           for (Map.Entry ench : isEnch.entrySet())
/*    */           {
/* 43 */             serializedItemStack = serializedItemStack + ":e@" + ((Enchantment)ench.getKey()).getId() + "@" + ench.getValue();
/*    */           }
/*    */         }
/*    */ 
/* 47 */         serialization = serialization + i + "#" + serializedItemStack + ";";
/*    */       }
/*    */     }
/* 50 */     return serialization;
/*    */   }
/*    */ 
/*    */   public static Inventory StringToInventory(String invString)
/*    */   {
/* 55 */     String[] serializedBlocks = invString.split(";");
/* 56 */     String invInfo = serializedBlocks[0];
/* 57 */     Inventory deserializedInventory = Bukkit.getServer().createInventory(null, Integer.valueOf(invInfo).intValue());
/*    */ 
/* 59 */     for (int i = 1; i < serializedBlocks.length; i++)
/*    */     {
/* 61 */       String[] serializedBlock = serializedBlocks[i].split("#");
/* 62 */       int stackPosition = Integer.valueOf(serializedBlock[0]).intValue();
/*    */ 
/* 64 */       if (stackPosition < deserializedInventory.getSize())
/*    */       {
/* 69 */         ItemStack is = null;
/* 70 */         Boolean createdItemStack = Boolean.valueOf(false);
/*    */ 
/* 72 */         String[] serializedItemStack = serializedBlock[1].split(":");
/* 73 */         for (String itemInfo : serializedItemStack)
/*    */         {
/* 75 */           String[] itemAttribute = itemInfo.split("@");
/* 76 */           if (itemAttribute[0].equals("t"))
/*    */           {
/* 78 */             is = new ItemStack(Material.getMaterial(Integer.valueOf(itemAttribute[1]).intValue()));
/* 79 */             createdItemStack = Boolean.valueOf(true);
/* 80 */           } else if ((itemAttribute[0].equals("d")) && (createdItemStack.booleanValue()))
/*    */           {
/* 82 */             is.setDurability(Short.valueOf(itemAttribute[1]).shortValue());
/* 83 */           } else if ((itemAttribute[0].equals("a")) && (createdItemStack.booleanValue()))
/*    */           {
/* 85 */             is.setAmount(Integer.valueOf(itemAttribute[1]).intValue());
/* 86 */           } else if ((itemAttribute[0].equals("e")) && (createdItemStack.booleanValue()))
/*    */           {
/* 88 */             is.addEnchantment(Enchantment.getById(Integer.valueOf(itemAttribute[1]).intValue()), Integer.valueOf(itemAttribute[2]).intValue());
/*    */           }
/*    */         }
/* 91 */         deserializedInventory.setItem(stackPosition, is);
/*    */       }
/*    */     }
/* 94 */     return deserializedInventory;
/*    */   }
/*    */ }

/* Location:           /Users/elliottolson/Downloads/BowSpleef.jar
 * Qualified Name:     com.untoldadventures.bowspleef.InventoryStringDeSerializer
 * JD-Core Version:    0.6.2
 */