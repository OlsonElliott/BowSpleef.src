/*    */ package com.untoldadventures.bowspleef;
/*    */ 
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.Server;
/*    */ import org.bukkit.World;
/*    */ import org.bukkit.configuration.file.FileConfiguration;
/*    */ 
/*    */ public class Arena
/*    */ {
/*  8 */   private String name = null;
/*    */   private BowSpleef plugin;
/*    */ 
/*    */   public Arena(String name, BowSpleef plugin)
/*    */   {
/* 13 */     this.name = name;
/*    */ 
/* 15 */     this.plugin = plugin;
/*    */ 
/* 17 */     BowSpleef.arenaConfig.set("arenas." + name + ".votes", Integer.valueOf(0));
/*    */ 
/* 19 */     plugin.saveConfig();
/*    */   }
/*    */ 
/*    */   public String getName()
/*    */   {
/* 24 */     return this.name;
/*    */   }
/*    */ 
/*    */   public Location getPos1()
/*    */   {
/* 29 */     World world = Bukkit.getServer().getWorld(BowSpleef.arenaConfig.getString("arenas." + this.name + ".world"));
/* 30 */     int x = BowSpleef.arenaConfig.getInt("arenas." + this.name + ".pos1.x");
/* 31 */     int y = BowSpleef.arenaConfig.getInt("arenas." + this.name + ".pos1.y");
/* 32 */     int z = BowSpleef.arenaConfig.getInt("arenas." + this.name + ".pos2.z");
/*    */ 
/* 34 */     return new Location(world, x, y, z);
/*    */   }
/*    */ 
/*    */   public Location getPos2()
/*    */   {
/* 39 */     World world = Bukkit.getServer().getWorld(BowSpleef.arenaConfig.getString("arenas." + this.name + ".world"));
/* 40 */     int x = BowSpleef.arenaConfig.getInt("arenas." + this.name + ".pos2.x");
/* 41 */     int y = BowSpleef.arenaConfig.getInt("arenas." + this.name + ".pos2.y");
/* 42 */     int z = BowSpleef.arenaConfig.getInt("arenas." + this.name + ".pos2.z");
/*    */ 
/* 44 */     return new Location(world, x, y, z);
/*    */   }
/*    */ 
/*    */   public Location getSpawn()
/*    */   {
/* 49 */     World world = Bukkit.getServer().getWorld(BowSpleef.arenaConfig.getString("arenas." + this.name + ".world"));
/* 50 */     int x = BowSpleef.arenaConfig.getInt("arenas." + this.name + ".spawn.x");
/* 51 */     int y = BowSpleef.arenaConfig.getInt("arenas." + this.name + ".spawn.y");
/* 52 */     int z = BowSpleef.arenaConfig.getInt("arenas." + this.name + ".spawn.z");
/*    */ 
/* 54 */     return new Location(world, x, y, z);
/*    */   }
/*    */ 
/*    */   public Location getLobby()
/*    */   {
/* 59 */     World world = Bukkit.getServer().getWorld(BowSpleef.arenaConfig.getString("arenas." + this.name + ".world"));
/* 60 */     int x = BowSpleef.arenaConfig.getInt("arenas." + this.name + ".lobby.x");
/* 61 */     int y = BowSpleef.arenaConfig.getInt("arenas." + this.name + ".lobby.y");
/* 62 */     int z = BowSpleef.arenaConfig.getInt("arenas." + this.name + ".lobby.z");
/*    */ 
/* 64 */     return new Location(world, x, y, z);
/*    */   }
/*    */ 
/*    */   public void setVotes(int votes)
/*    */   {
/* 69 */     BowSpleef.arenaConfig.set("arenas." + this.name + ".votes", Integer.valueOf(votes));
/* 70 */     this.plugin.saveConfig();
/*    */   }
/*    */ 
/*    */   public void addVotes(int votes)
/*    */   {
/* 75 */     BowSpleef.arenaConfig.set("arenas." + this.name + ".votes", Integer.valueOf(BowSpleef.arenaConfig.getInt("arenas." + this.name + ".votes") + votes));
/* 76 */     this.plugin.saveConfig();
/*    */   }
/*    */ 
/*    */   public int getVotes()
/*    */   {
/* 81 */     return BowSpleef.arenaConfig.getInt("arenas." + this.name + ".votes");
/*    */   }
/*    */ }

/* Location:           /Users/elliottolson/Downloads/BowSpleef.jar
 * Qualified Name:     com.untoldadventures.bowspleef.Arena
 * JD-Core Version:    0.6.2
 */