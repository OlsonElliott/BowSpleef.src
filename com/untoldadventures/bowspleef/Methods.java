/*     */ package com.untoldadventures.bowspleef;
/*     */ 
/*     */ import com.untoldadventures.bowspleef.events.BowSpleefCountdownEvent;
/*     */ import com.untoldadventures.bowspleef.events.BowSpleefJoinEvent;
/*     */ import com.untoldadventures.bowspleef.events.BowSpleefQuitEvent;
/*     */ import com.untoldadventures.bowspleef.events.Countdown;
/*     */ import java.util.List;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.GameMode;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.Server;
/*     */ import org.bukkit.World;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.configuration.file.FileConfiguration;
/*     */ import org.bukkit.enchantments.Enchantment;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.inventory.Inventory;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.PlayerInventory;
/*     */ import org.bukkit.plugin.PluginManager;
/*     */ 
/*     */ public class Methods
/*     */ {
/*     */   public static void quitNoWin(Player player, BowSpleef plugin)
/*     */   {
/*  25 */     if (BowSpleef.invConfig.contains(player.getName()))
/*     */     {
/*  28 */       String arena = BowSpleef.invConfig.getString(player.getName() + ".arena");
/*     */ 
/*  30 */       int gmnum = BowSpleef.invConfig.getInt(player.getName() + ".return.gamemode");
/*  31 */       GameMode gm = GameMode.getByValue(gmnum);
/*  32 */       player.setGameMode(gm);
/*     */ 
/*  34 */       Inventory inv = InventoryStringDeSerializer.StringToInventory(BowSpleef.invConfig.getString(player.getName() + ".inventory"));
/*  35 */       player.getInventory().setContents(inv.getContents());
/*     */ 
/*  37 */       int x = BowSpleef.invConfig.getInt(player.getName() + ".return.x");
/*  38 */       int y = BowSpleef.invConfig.getInt(player.getName() + ".return.y");
/*  39 */       int z = BowSpleef.invConfig.getInt(player.getName() + ".return.z");
/*  40 */       World w = Bukkit.getWorld(BowSpleef.invConfig.getString(player.getName() + ".return.world"));
/*  41 */       Location retpos = new Location(w, x, y, z);
/*  42 */       player.teleport(retpos);
/*     */ 
/*  44 */       BowSpleef.invConfig.set(player.getName(), null);
/*     */ 
/*  46 */       List players = BowSpleef.arenaConfig.getStringList("arenas." + arena + ".players");
/*  47 */       List voted = BowSpleef.arenaConfig.getStringList("arenas." + arena + ".voted");
/*  48 */       players.remove(player.getName());
/*  49 */       voted.remove(player.getName());
/*  50 */       BowSpleef.arenaConfig.set("arenas." + arena + ".players", players);
/*  51 */       BowSpleef.arenaConfig.set("arenas." + arena + ".voted", voted);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void quit(Player player, BowSpleef plugin)
/*     */   {
/*  58 */     if (BowSpleef.invConfig.contains(player.getName()))
/*     */     {
/*  62 */       String arena = BowSpleef.invConfig.getString(player.getName() + ".arena");
/*     */ 
/*  64 */       int gmnum = BowSpleef.invConfig.getInt(player.getName() + ".return.gamemode");
/*  65 */       GameMode gm = GameMode.getByValue(gmnum);
/*  66 */       player.setGameMode(gm);
/*     */ 
/*  68 */       Inventory inv = InventoryStringDeSerializer.StringToInventory(BowSpleef.invConfig.getString(player.getName() + ".inventory"));
/*  69 */       player.getInventory().setContents(inv.getContents());
/*     */ 
/*  71 */       int x = BowSpleef.invConfig.getInt(player.getName() + ".return.x");
/*  72 */       int y = BowSpleef.invConfig.getInt(player.getName() + ".return.y");
/*  73 */       int z = BowSpleef.invConfig.getInt(player.getName() + ".return.z");
/*  74 */       World w = Bukkit.getWorld(BowSpleef.invConfig.getString(player.getName() + ".return.world"));
/*  75 */       Location retpos = new Location(w, x, y, z);
/*  76 */       player.teleport(retpos);
/*     */ 
/*  78 */       BowSpleef.invConfig.set(player.getName(), null);
/*     */ 
/*  80 */       List players = BowSpleef.arenaConfig.getStringList("arenas." + arena + ".players");
/*  81 */       List voted = BowSpleef.arenaConfig.getStringList("arenas." + arena + ".voted");
/*  82 */       players.remove(player.getName());
/*  83 */       voted.remove(player.getName());
/*  84 */       BowSpleef.arenaConfig.set("arenas." + arena + ".players", players);
/*  85 */       BowSpleef.arenaConfig.set("arenas." + arena + ".voted", voted);
/*     */ 
/*  87 */       BowSpleefQuitEvent eventS = new BowSpleefQuitEvent(player, arena);
/*  88 */       Bukkit.getServer().getPluginManager().callEvent(eventS);
/*     */ 
/*  90 */       if (players.size() == 1)
/*     */       {
/*  92 */         Player player1 = Bukkit.getPlayer((String)players.get(0));
/*     */ 
/*  94 */         Bukkit.getServer().broadcastMessage(ChatColor.AQUA + "[BowSpleef] " + ChatColor.GRAY + (String)players.get(0) + " Won BowSpleef!");
/*  95 */         BowSpleef.arenaConfig.set("arenas." + arena + ".inGame", Boolean.valueOf(false));
/*     */ 
/*  97 */         regen(arena, plugin);
/*     */ 
/* 100 */         String arena1 = BowSpleef.invConfig.getString(player1.getName() + ".arena");
/*     */ 
/* 102 */         int gmnum1 = BowSpleef.invConfig.getInt(player1.getName() + ".return.gamemode");
/* 103 */         GameMode gm1 = GameMode.getByValue(gmnum1);
/* 104 */         player1.setGameMode(gm1);
/*     */ 
/* 106 */         Inventory inv1 = InventoryStringDeSerializer.StringToInventory(BowSpleef.invConfig.getString(player1.getName() + ".inventory"));
/* 107 */         player1.getInventory().setContents(inv1.getContents());
/*     */ 
/* 109 */         int x1 = BowSpleef.invConfig.getInt(player1.getName() + ".return.x");
/* 110 */         int y1 = BowSpleef.invConfig.getInt(player1.getName() + ".return.y");
/* 111 */         int z1 = BowSpleef.invConfig.getInt(player1.getName() + ".return.z");
/* 112 */         World w1 = Bukkit.getWorld(BowSpleef.invConfig.getString(player1.getName() + ".return.world"));
/* 113 */         Location retpos1 = new Location(w1, x1, y1, z1);
/* 114 */         player1.teleport(retpos1);
/*     */ 
/* 116 */         BowSpleef.invConfig.set(player1.getName(), null);
/*     */ 
/* 118 */         List players1 = BowSpleef.arenaConfig.getStringList("arenas." + arena1 + ".players");
/* 119 */         List voted1 = BowSpleef.arenaConfig.getStringList("arenas." + arena1 + ".voted");
/* 120 */         players1.remove(player1.getName());
/* 121 */         voted1.remove(player1.getName());
/* 122 */         BowSpleef.arenaConfig.set("arenas." + arena1 + ".players", players1);
/* 123 */         BowSpleef.arenaConfig.set("arenas." + arena1 + ".voted", voted1);
/* 124 */         return;
/*     */       }
/* 126 */       plugin.saveConfig();
/* 127 */       return;
/*     */     }
/* 129 */     pm("You aren't in an Arena!", player);
/*     */   }
/*     */ 
/*     */   public static boolean join(Player player, String arena, BowSpleef plugin)
/*     */   {
/* 135 */     if (player.hasPermission("bs.join"))
/*     */     {
/* 137 */       if (BowSpleef.arenaConfig.contains("arenas." + arena))
/*     */       {
/* 139 */         if (BowSpleef.arenaConfig.getBoolean("arenas." + arena + ".enabled"))
/*     */         {
/* 141 */           if (!BowSpleef.arenaConfig.getBoolean("arenas." + arena + ".inGame"))
/*     */           {
/* 143 */             if (BowSpleef.invConfig.getString(player.getName() + "arena") != "")
/*     */             {
/* 145 */               List players = BowSpleef.arenaConfig.getStringList("arenas." + arena + ".players");
/* 146 */               int max = BowSpleef.arenaConfig.getInt("arenas." + arena + ".max-players");
/* 147 */               if (players.size() == max)
/*     */               {
/* 149 */                 pm("That arena is already full!", player);
/*     */               }
/* 151 */               if (players.size() >= Math.round(max * 0.66D))
/*     */               {
/* 155 */                 BowSpleefCountdownEvent event = new BowSpleefCountdownEvent(arena);
/*     */ 
/* 157 */                 Bukkit.getServer().getPluginManager().callEvent(event);
/*     */ 
/* 159 */                 new Countdown(event.getArena()).runTaskTimer(plugin, 0L, 20L);
/*     */ 
/* 161 */                 BowSpleef.arenaConfig.set("arenas." + arena + ".inGame", Boolean.valueOf(true));
/*     */ 
/* 163 */                 plugin.saveConfig();
/*     */               }
/*     */ 
/* 167 */               int gm = player.getGameMode().getValue();
/*     */ 
/* 169 */               BowSpleef.invConfig.set(player.getName() + ".return.gamemode", Integer.valueOf(gm));
/*     */ 
/* 171 */               Location returnPos = player.getLocation();
/* 172 */               BowSpleef.invConfig.set(player.getName() + ".return.x", Integer.valueOf(returnPos.getBlockX()));
/* 173 */               BowSpleef.invConfig.set(player.getName() + ".return.y", Integer.valueOf(returnPos.getBlockY()));
/* 174 */               BowSpleef.invConfig.set(player.getName() + ".return.z", Integer.valueOf(returnPos.getBlockZ()));
/* 175 */               BowSpleef.invConfig.set(player.getName() + ".return.world", returnPos.getWorld().getName());
/*     */ 
/* 177 */               String inv = InventoryStringDeSerializer.InventoryToString(player.getInventory());
/* 178 */               BowSpleef.invConfig.set(player.getName() + ".inventory", inv);
/* 179 */               BowSpleef.invConfig.set(player.getName() + ".arena", arena);
/*     */ 
/* 181 */               player.getInventory().clear();
/*     */ 
/* 183 */               World world = Bukkit.getServer().getWorld(BowSpleef.arenaConfig.getString("arenas." + arena + ".world"));
/* 184 */               int x = BowSpleef.arenaConfig.getInt("arenas." + arena + ".lobby.x"); int y = BowSpleef.arenaConfig.getInt("arenas." + arena + ".lobby.y"); int z = BowSpleef.arenaConfig.getInt("arenas." + arena + ".lobby.z");
/* 185 */               Location lobby = new Location(world, x, y, z);
/* 186 */               player.teleport(lobby);
/*     */ 
/* 188 */               player.setGameMode(GameMode.ADVENTURE);
/*     */ 
/* 190 */               BowSpleefJoinEvent event = new BowSpleefJoinEvent(player, arena);
/* 191 */               Bukkit.getServer().getPluginManager().callEvent(event);
/*     */ 
/* 193 */               players.add(player.getName());
/* 194 */               BowSpleef.arenaConfig.set("arenas." + arena + ".players", players);
/* 195 */               if (players.size() == Math.round(BowSpleef.arenaConfig.getInt("arenas." + arena + ".max-players") * 2 / 3))
/*     */               {
/* 197 */                 BowSpleefJoinEvent eventS = new BowSpleefJoinEvent(player, arena);
/* 198 */                 Bukkit.getServer().getPluginManager().callEvent(eventS);
/*     */               }
/* 200 */               for (int p = 0; p < players.size(); p++)
/*     */               {
/* 202 */                 pm(player.getName() + " joined the arena!" + " " + players.size() + "/" + max, Bukkit.getPlayer((String)players.get(p)));
/*     */               }
/* 204 */               plugin.saveConfig();
/* 205 */               return true;
/*     */             }
/* 207 */             pm("You are already in an arena!", player);
/* 208 */             return true;
/*     */           }
/*     */ 
/* 211 */           pm("That arena is in game!", player);
/* 212 */           return true;
/*     */         }
/* 214 */         pm("That arena is Disabled", player);
/* 215 */         return true;
/*     */       }
/* 217 */       pm("That Arena doesn't Exist!", player);
/* 218 */       return true;
/*     */     }
/* 220 */     pm("You do not have the required permissions!", player);
/* 221 */     return true;
/*     */   }
/*     */ 
/*     */   public static void regen(String arena, BowSpleef plugin)
/*     */   {
/* 226 */     if (BowSpleef.arenaConfig.contains("arenas." + arena))
/*     */     {
/* 229 */       World world = Bukkit.getWorld(BowSpleef.arenaConfig.getString("arenas." + arena + ".world"));
/*     */ 
/* 231 */       int pos1X = BowSpleef.arenaConfig.getInt("arenas." + arena + ".pos1.x");
/* 232 */       int pos1Y = BowSpleef.arenaConfig.getInt("arenas." + arena + ".pos1.y");
/* 233 */       int pos1Z = BowSpleef.arenaConfig.getInt("arenas." + arena + ".pos1.z");
/* 234 */       int pos2X = BowSpleef.arenaConfig.getInt("arenas." + arena + ".pos2.x");
/* 235 */       int pos2Y = BowSpleef.arenaConfig.getInt("arenas." + arena + ".pos2.y");
/* 236 */       int pos2Z = BowSpleef.arenaConfig.getInt("arenas." + arena + ".pos2.z");
/* 237 */       Location pos1 = new Location(world, pos1X, pos1Y, pos1Z);
/* 238 */       Location pos2 = new Location(world, pos2X, pos2Y, pos2Z);
/*     */ 
/* 240 */       regen(pos1, pos2, world);
/*     */ 
/* 242 */       plugin.saveConfig();
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void start(String arenaName)
/*     */   {
/* 248 */     List players = BowSpleef.arenaConfig.getStringList("arenas." + arenaName + ".players");
/* 249 */     World world = Bukkit.getServer().getWorld(BowSpleef.arenaConfig.getString("arenas." + arenaName + ".world"));
/* 250 */     int x = BowSpleef.arenaConfig.getInt("arenas." + arenaName + ".spawn.x");
/* 251 */     int y = BowSpleef.arenaConfig.getInt("arenas." + arenaName + ".spawn.y");
/* 252 */     int z = BowSpleef.arenaConfig.getInt("arenas." + arenaName + ".spawn.z");
/*     */ 
/* 254 */     Location spawn = new Location(world, x, y, z);
/* 255 */     for (String givee : players)
/*     */     {
/* 257 */       Player pgivee = Bukkit.getPlayer(givee);
/* 258 */       pgivee.teleport(spawn);
/* 259 */       giveItems(pgivee);
/*     */     }
/*     */ 
/* 262 */     BowSpleef.arenaConfig.set("arenas." + arenaName + ".inGame", Boolean.valueOf(true));
/*     */   }
/*     */ 
/*     */   private static void regen(Location loc1, Location loc2, World w)
/*     */   {
/* 268 */     int minx = Math.min(loc1.getBlockX(), loc2.getBlockX()); int miny = Math.min(loc1.getBlockY(), loc2.getBlockY()); int minz = Math.min(loc1.getBlockZ(), loc2.getBlockZ()); int maxx = Math.max(loc1.getBlockX(), loc2.getBlockX()); int maxy = Math.max(loc1.getBlockY(), loc2.getBlockY()); int maxz = Math.max(loc1.getBlockZ(), loc2.getBlockZ());
/* 269 */     for (int x = minx; x <= maxx; x++)
/*     */     {
/* 271 */       for (int y = miny; y <= maxy; y++)
/*     */       {
/* 273 */         for (int z = minz; z <= maxz; z++)
/*     */         {
/* 275 */           Block b = w.getBlockAt(x, y, z);
/* 276 */           if (b.getType() == Material.AIR)
/* 277 */             b.setType(Material.TNT);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void pm(String message, Player player)
/*     */   {
/* 285 */     player.sendMessage(ChatColor.AQUA + "[BowSpleef] " + ChatColor.GRAY + message);
/*     */   }
/*     */ 
/*     */   private static void giveItems(Player player)
/*     */   {
/* 290 */     ItemStack bow = new ItemStack(Material.BOW);
/* 291 */     bow.addEnchantment(Enchantment.ARROW_FIRE, 1);
/* 292 */     bow.addEnchantment(Enchantment.ARROW_INFINITE, 1);
/* 293 */     bow.addEnchantment(Enchantment.DURABILITY, 3);
/* 294 */     player.getInventory().addItem(new ItemStack[] { bow });
/* 295 */     player.getInventory().addItem(new ItemStack[] { new ItemStack(Material.ARROW) });
/*     */   }
/*     */ }

/* Location:           /Users/elliottolson/Downloads/BowSpleef.jar
 * Qualified Name:     com.untoldadventures.bowspleef.Methods
 * JD-Core Version:    0.6.2
 */