/*     */ package com.untoldadventures.bowspleef;
/*     */ 
/*     */ import com.untoldadventures.bowspleef.events.BowSpleefCountdownEvent;
/*     */ import com.untoldadventures.bowspleef.events.BowSpleefVoteEvent;
/*     */ import com.untoldadventures.bowspleef.events.Countdown;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.Server;
/*     */ import org.bukkit.World;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.command.Command;
/*     */ import org.bukkit.command.CommandExecutor;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.configuration.file.FileConfiguration;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.plugin.PluginManager;
/*     */ 
/*     */ public class BowSpleefCommandExecutor
/*     */   implements CommandExecutor
/*     */ {
/*  22 */   public static List<String> arenaNames = new ArrayList();
/*     */   BowSpleef plugin;
/*     */ 
/*     */   public BowSpleefCommandExecutor(BowSpleef plugin)
/*     */   {
/*  28 */     this.plugin = plugin;
/*     */   }
/*     */ 
/*     */   public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
/*     */   {
/*  34 */     if ((sender instanceof Player))
/*     */     {
/*  36 */       if (args.length == 0)
/*     */       {
/*  38 */         return false;
/*     */       }
/*     */ 
/*  41 */       Player player = (Player)sender;
/*  42 */       if (args[0].equalsIgnoreCase("join"))
/*     */       {
/*  44 */         if (player.hasPermission("bs.join"))
/*     */         {
/*  46 */           if (args.length == 2)
/*     */           {
/*  48 */             String arena = args[1];
/*     */ 
/*  50 */             Methods.join(player, arena, this.plugin);
/*  51 */             return true;
/*     */           }
/*     */ 
/*  54 */           pm("Incorrect Usage!", player);
/*  55 */           return false;
/*     */         }
/*  57 */         return true;
/*     */       }
/*  59 */       if (args[0].equalsIgnoreCase("quit"))
/*     */       {
/*  61 */         if ((player instanceof Player))
/*     */         {
/*  63 */           if (player.hasPermission("bs.quit"))
/*     */           {
/*  66 */             Methods.quit(player, this.plugin);
/*  67 */             return true;
/*     */           }
/*     */         }
/*     */       }
/*  71 */       if (args[0].equalsIgnoreCase("vote"))
/*     */       {
/*  73 */         if ((player instanceof Player))
/*     */         {
/*  75 */           if (player.hasPermission("bs.vote"))
/*     */           {
/*  78 */             String arena = BowSpleef.invConfig.getString(player.getName() + ".arena");
/*  79 */             if (arena != null)
/*     */             {
/*  81 */               if (BowSpleef.arenaConfig.getBoolean("arenas." + arena + ".enabled"))
/*     */               {
/*  83 */                 if (!BowSpleef.arenaConfig.getBoolean("arenas." + arena + ".inGame"))
/*     */                 {
/*  85 */                   List players = BowSpleef.arenaConfig.getStringList("arenas." + arena + ".players");
/*  86 */                   if (players.contains(player.getName()))
/*     */                   {
/*  89 */                     List voted = BowSpleef.arenaConfig.getStringList("arenas." + arena + ".voted");
/*     */ 
/*  91 */                     if (!voted.contains(player.getName()))
/*     */                     {
/*  93 */                       voted.add(player.getName());
/*  94 */                       BowSpleef.arenaConfig.set("arenas." + arena + ".voted", voted);
/*     */ 
/*  96 */                       BowSpleefVoteEvent event = new BowSpleefVoteEvent(player, arena);
/*  97 */                       Bukkit.getServer().getPluginManager().callEvent(event);
/*     */ 
/*  99 */                       int votesNeeded = Math.round(players.size() * 2 / 3);
/* 100 */                       int amountVoted = voted.size();
/* 101 */                       int remaining = votesNeeded - amountVoted;
/*     */ 
/* 103 */                       pm("You voted to start the arena!", event.getPlayer());
/*     */ 
/* 105 */                       if ((remaining <= 0) && (players.size() > 1))
/*     */                       {
/* 107 */                         BowSpleef.arenaConfig.set("arenas." + arena + ".inGame", Boolean.valueOf(true));
/*     */ 
/* 110 */                         BowSpleefCountdownEvent eventCount = new BowSpleefCountdownEvent(arena);
/* 111 */                         Bukkit.getServer().getPluginManager().callEvent(eventCount);
/*     */ 
/* 113 */                         new Countdown(event.getArena()).runTaskTimer(this.plugin, 0L, 20L);
/*     */                       }
/*     */ 
/* 116 */                       this.plugin.saveConfig();
/* 117 */                       return true;
/*     */                     }
/*     */ 
/* 120 */                     pm("You already voted!", player);
/* 121 */                     return true;
/*     */                   }
/*     */ 
/* 124 */                   pm("You aren't in an Arena!", player);
/* 125 */                   this.plugin.saveConfig();
/* 126 */                   return true;
/*     */                 }
/*     */ 
/* 129 */                 pm("The arena is in-game!", player);
/* 130 */                 return true;
/*     */               }
/*     */ 
/* 133 */               pm("The arena is not enabled!", player);
/* 134 */               return true;
/*     */             }
/* 136 */             pm("You aren't in an Arena!", player);
/* 137 */             return true;
/*     */           }
/* 139 */           return true;
/*     */         }
/*     */ 
/* 142 */         sender.sendMessage("[BowSpleef] This command can only be run by a Player");
/* 143 */         return true;
/*     */       }
/* 145 */       if (args[0].equalsIgnoreCase("delete"))
/*     */       {
/* 147 */         if ((player instanceof Player))
/*     */         {
/* 149 */           if (player.hasPermission("bs.delete"))
/*     */           {
/* 151 */             if (args.length == 2)
/*     */             {
/* 153 */               String arena = args[1];
/* 154 */               if (BowSpleef.arenaConfig.contains("arenas." + arena))
/*     */               {
/* 156 */                 BowSpleef.arenaConfig.set("arenas." + arena, null);
/* 157 */                 pm("The arena, " + arena + ", has been deleted!", player);
/* 158 */                 this.plugin.saveConfig();
/* 159 */                 return true;
/*     */               }
/*     */ 
/* 162 */               pm("That arena doesnt exist!", player);
/* 163 */               this.plugin.saveConfig();
/* 164 */               return true;
/*     */             }
/* 166 */             player.sendMessage(ChatColor.AQUA + "[BowSpleef]" + ChatColor.GRAY + " Incorrect Usage!");
/* 167 */             return false;
/*     */           }
/* 169 */           this.plugin.saveConfig();
/* 170 */           return true;
/*     */         }
/* 172 */         sender.sendMessage("[BowSpleef] This command can only be run by a Player!");
/* 173 */         this.plugin.saveConfig();
/* 174 */         return true;
/*     */       }
/* 176 */       if (args[0].equalsIgnoreCase("create"))
/*     */       {
/* 178 */         if ((player instanceof Player))
/*     */         {
/* 180 */           if (player.hasPermission("bs.create"))
/*     */           {
/* 182 */             if (args.length == 2)
/*     */             {
/* 184 */               String arena = args[1];
/* 185 */               if (BowSpleef.arenaConfig.contains("arenas." + arena))
/*     */               {
/* 187 */                 sender.sendMessage(ChatColor.AQUA + "[BowSpleef]" + ChatColor.GRAY + " This arena already exists!");
/* 188 */                 return true;
/*     */               }
/* 190 */               sender.sendMessage(ChatColor.AQUA + "[BowSpleef]" + ChatColor.GRAY + " The arena, " + arena + ", has been created!");
/* 191 */               BowSpleef.arenaConfig.createSection("arenas." + arena);
/* 192 */               BowSpleef.arenaConfig.createSection("arenas." + arena + ".players");
/* 193 */               BowSpleef.arenaConfig.set("arenas." + arena + ".enabled", Boolean.valueOf(false));
/* 194 */               BowSpleef.arenaConfig.set("arenas." + arena + ".inGame", Boolean.valueOf(false));
/* 195 */               BowSpleef.arenaConfig.set("arenas." + arena + ".min-players", Integer.valueOf(2));
/* 196 */               BowSpleef.arenaConfig.set("arenas." + arena + ".max-players", Integer.valueOf(10));
/*     */ 
/* 199 */               arenaNames.add(arena);
/* 200 */               BowSpleef.arenaConfig.set("List", arenaNames);
/*     */ 
/* 202 */               this.plugin.saveConfig();
/* 203 */               return true;
/*     */             }
/* 205 */             return false;
/*     */           }
/* 207 */           this.plugin.saveConfig();
/* 208 */           return true;
/*     */         }
/* 210 */         sender.sendMessage("[BowSpleef] This command can only be run by a Player!");
/* 211 */         this.plugin.saveConfig();
/* 212 */         return true;
/*     */       }
/* 214 */       if ((args[0].equalsIgnoreCase("regen")) && (args.length == 2))
/*     */       {
/* 216 */         String arena = args[1];
/* 217 */         World world = player.getWorld();
/* 218 */         if ((sender instanceof Player))
/*     */         {
/* 220 */           if (player.hasPermission("bowspleef.admin.regen"))
/*     */           {
/* 222 */             if (BowSpleef.arenaConfig.contains("arenas." + arena))
/*     */             {
/* 224 */               int pos1X = BowSpleef.arenaConfig.getInt("arenas." + arena + ".pos1.x");
/* 225 */               int pos1Y = BowSpleef.arenaConfig.getInt("arenas." + arena + ".pos1.y");
/* 226 */               int pos1Z = BowSpleef.arenaConfig.getInt("arenas." + arena + ".pos1.z");
/* 227 */               int pos2X = BowSpleef.arenaConfig.getInt("arenas." + arena + ".pos2.x");
/* 228 */               int pos2Y = BowSpleef.arenaConfig.getInt("arenas." + arena + ".pos2.y");
/* 229 */               int pos2Z = BowSpleef.arenaConfig.getInt("arenas." + arena + ".pos2.z");
/* 230 */               Location pos1 = new Location(world, pos1X, pos1Y, pos1Z);
/* 231 */               Location pos2 = new Location(world, pos2X, pos2Y, pos2Z);
/* 232 */               regen(pos1, pos2, world);
/*     */ 
/* 234 */               sender.sendMessage(ChatColor.AQUA + "[BowSpleef]" + ChatColor.GRAY + " Arena Regenerated!");
/*     */ 
/* 236 */               this.plugin.saveConfig();
/* 237 */               return true;
/*     */             }
/* 239 */             sender.sendMessage(ChatColor.AQUA + "[BowSpleef]" + ChatColor.GRAY + " Arena doesn't exist!");
/* 240 */             return true;
/*     */           }
/* 242 */           return true;
/*     */         }
/* 244 */         sender.sendMessage("[BowSpleef] This command can only be run by a Player!");
/* 245 */         return true;
/*     */       }
/*     */ 
/* 248 */       if (args[0].equalsIgnoreCase("reload"))
/*     */       {
/* 250 */         if ((sender instanceof Player))
/*     */         {
/* 252 */           if (sender.hasPermission("cf.reload"))
/*     */           {
/* 254 */             if (args.length == 1)
/*     */             {
/* 256 */               this.plugin.loadConfig();
/* 257 */               pm("Config Reloaded!", player);
/* 258 */               return true;
/*     */             }
/* 260 */             pm("Invalid Arguments", player);
/* 261 */             return false;
/*     */           }
/* 263 */           return true;
/*     */         }
/* 265 */         sender.sendMessage("[ChatFilter] This Command may Only be run by a Player!");
/* 266 */         return true;
/*     */       }
/* 268 */       if (args[0].equalsIgnoreCase("set"))
/*     */       {
/* 270 */         if ((sender instanceof Player))
/*     */         {
/* 272 */           if (sender.hasPermission("bowspleef.admin.set"))
/*     */           {
/* 274 */             if ((args.length == 3) || (args.length == 4))
/*     */             {
/* 276 */               Location pos1 = player.getLocation(); Location pos2 = player.getLocation();
/* 277 */               int pos1X = pos1.getBlockX(); int pos1Y = pos1.getBlockY() - 1; int pos1Z = pos1.getBlockZ(); int pos2X = pos2.getBlockX(); int pos2Y = pos2.getBlockY() - 1; int pos2Z = pos2.getBlockZ();
/* 278 */               String arena = args[1];
/*     */ 
/* 281 */               BowSpleef.arenaConfig.set("arenas." + arena + ".world", pos1.getWorld().getName());
/*     */ 
/* 283 */               if (BowSpleef.arenaConfig.contains("arenas." + arena))
/*     */               {
/* 285 */                 if (args[2].equalsIgnoreCase("pos1"))
/*     */                 {
/* 288 */                   BowSpleef.arenaConfig.set("arenas." + arena + ".pos1.x", Integer.valueOf(pos1X));
/* 289 */                   BowSpleef.arenaConfig.set("arenas." + arena + ".pos1.y", Integer.valueOf(pos1Y));
/* 290 */                   BowSpleef.arenaConfig.set("arenas." + arena + ".pos1.z", Integer.valueOf(pos1Z));
/* 291 */                   sender.sendMessage(ChatColor.AQUA + "[BowSpleef]" + ChatColor.GRAY + " Pos1 Set!");
/* 292 */                   this.plugin.saveConfig();
/* 293 */                   return true;
/*     */                 }
/* 295 */                 if (args.length == 4)
/*     */                 {
/* 297 */                   if (args[2].equalsIgnoreCase("min"))
/*     */                   {
/* 300 */                     BowSpleef.arenaConfig.set("arenas." + arena + ".min", args[3]);
/* 301 */                     pm("Minimum Players set!", player);
/* 302 */                     this.plugin.saveConfig();
/* 303 */                     return true;
/*     */                   }
/* 305 */                   if (args[2].equalsIgnoreCase("max"))
/*     */                   {
/* 308 */                     BowSpleef.arenaConfig.set("arenas." + arena + ".max", args[3]);
/* 309 */                     pm("Maximum Players set!", player);
/* 310 */                     this.plugin.saveConfig();
/* 311 */                     return true;
/*     */                   }
/*     */                 }
/* 314 */                 if (args[2].equalsIgnoreCase("pos2"))
/*     */                 {
/* 317 */                   BowSpleef.arenaConfig.set("arenas." + arena + ".pos2.x", Integer.valueOf(pos2X));
/* 318 */                   BowSpleef.arenaConfig.set("arenas." + arena + ".pos2.y", Integer.valueOf(pos2Y));
/* 319 */                   BowSpleef.arenaConfig.set("arenas." + arena + ".pos2.z", Integer.valueOf(pos2Z));
/* 320 */                   this.plugin.saveConfig();
/* 321 */                   sender.sendMessage(ChatColor.AQUA + "[BowSpleef]" + ChatColor.GRAY + " Pos2 Set!");
/* 322 */                   return true;
/*     */                 }
/* 324 */                 if (args[2].equalsIgnoreCase("spawn"))
/*     */                 {
/* 326 */                   int x = player.getLocation().getBlockX(); int y = player.getLocation().getBlockY(); int z = player.getLocation().getBlockZ();
/* 327 */                   String world = player.getLocation().getWorld().getName();
/* 328 */                   if (world == BowSpleef.arenaConfig.getString("arenas." + arena + ".world"))
/*     */                   {
/* 330 */                     BowSpleef.arenaConfig.set("arenas." + arena + ".spawn.x", Integer.valueOf(x));
/* 331 */                     BowSpleef.arenaConfig.set("arenas." + arena + ".spawn.y", Integer.valueOf(y));
/* 332 */                     BowSpleef.arenaConfig.set("arenas." + arena + ".spawn.z", Integer.valueOf(z));
/*     */ 
/* 334 */                     this.plugin.saveConfig();
/* 335 */                     pm("Spawn Postion Set!", player);
/* 336 */                     return true;
/*     */                   }
/* 338 */                   pm("You are in the wrong world!", player);
/* 339 */                   this.plugin.saveConfig();
/* 340 */                   return true;
/*     */                 }
/* 342 */                 if (args[2].equalsIgnoreCase("enable"))
/*     */                 {
/* 344 */                   if (BowSpleef.arenaConfig.contains("arenas." + arena + ".pos1"))
/*     */                   {
/* 346 */                     if (BowSpleef.arenaConfig.contains("arenas." + arena + ".pos2"))
/*     */                     {
/* 348 */                       if (BowSpleef.arenaConfig.contains("arenas." + arena + ".spawn"))
/*     */                       {
/* 350 */                         if (BowSpleef.arenaConfig.contains("arenas." + arena + ".lobby"))
/*     */                         {
/* 352 */                           BowSpleef.arenaConfig.set("arenas." + arena + ".enabled", Boolean.valueOf(true));
/* 353 */                           this.plugin.saveConfig();
/* 354 */                           pm("The Arena, " + arena + ", has been enabled!", player);
/* 355 */                           this.plugin.saveConfig();
/*     */ 
/* 357 */                           World world = player.getWorld();
/*     */ 
/* 359 */                           int pos1X1 = BowSpleef.arenaConfig.getInt("arenas." + arena + ".pos1.x");
/* 360 */                           int pos1Y1 = BowSpleef.arenaConfig.getInt("arenas." + arena + ".pos1.y");
/* 361 */                           int pos1Z1 = BowSpleef.arenaConfig.getInt("arenas." + arena + ".pos1.z");
/* 362 */                           int pos2X1 = BowSpleef.arenaConfig.getInt("arenas." + arena + ".pos2.x");
/* 363 */                           int pos2Y1 = BowSpleef.arenaConfig.getInt("arenas." + arena + ".pos2.y");
/* 364 */                           int pos2Z1 = BowSpleef.arenaConfig.getInt("arenas." + arena + ".pos2.z");
/* 365 */                           Location pos11 = new Location(world, pos1X1, pos1Y1, pos1Z1);
/* 366 */                           Location pos21 = new Location(world, pos2X1, pos2Y1, pos2Z1);
/*     */ 
/* 368 */                           regen(pos11, pos21, world);
/*     */ 
/* 370 */                           return true;
/*     */                         }
/* 372 */                         pm("No Lobby Point Set!", player);
/* 373 */                         return true;
/*     */                       }
/* 375 */                       pm("Missing Spawn Point", player);
/* 376 */                       return true;
/*     */                     }
/* 378 */                     pm("Missing Pos2", player);
/* 379 */                     return true;
/*     */                   }
/* 381 */                   pm("Missing Pos1", player);
/* 382 */                   return true;
/*     */                 }
/* 384 */                 if (args[2].equalsIgnoreCase("enabled"))
/*     */                 {
/* 386 */                   if (BowSpleef.arenaConfig.contains("arenas." + arena + ".pos1"))
/*     */                   {
/* 388 */                     if (BowSpleef.arenaConfig.contains("arenas." + arena + ".pos2"))
/*     */                     {
/* 390 */                       if (BowSpleef.arenaConfig.contains("arenas." + arena + ".spawn"))
/*     */                       {
/* 392 */                         if (BowSpleef.arenaConfig.contains("arenas." + arena + ".lobby"))
/*     */                         {
/* 394 */                           BowSpleef.arenaConfig.set("arenas." + arena + ".enabled", Boolean.valueOf(true));
/* 395 */                           this.plugin.saveConfig();
/* 396 */                           pm("The Arena, " + arena + ", has been enabled!", player);
/*     */ 
/* 398 */                           World world = player.getWorld();
/*     */ 
/* 400 */                           int pos1X1 = BowSpleef.arenaConfig.getInt("arenas." + arena + ".pos1.x");
/* 401 */                           int pos1Y1 = BowSpleef.arenaConfig.getInt("arenas." + arena + ".pos1.y");
/* 402 */                           int pos1Z1 = BowSpleef.arenaConfig.getInt("arenas." + arena + ".pos1.z");
/* 403 */                           int pos2X1 = BowSpleef.arenaConfig.getInt("arenas." + arena + ".pos2.x");
/* 404 */                           int pos2Y1 = BowSpleef.arenaConfig.getInt("arenas." + arena + ".pos2.y");
/* 405 */                           int pos2Z1 = BowSpleef.arenaConfig.getInt("arenas." + arena + ".pos2.z");
/* 406 */                           Location pos11 = new Location(world, pos1X1, pos1Y1, pos1Z1);
/* 407 */                           Location pos21 = new Location(world, pos2X1, pos2Y1, pos2Z1);
/*     */ 
/* 409 */                           regen(pos11, pos21, world);
/*     */ 
/* 411 */                           this.plugin.saveConfig();
/*     */ 
/* 413 */                           return true;
/*     */                         }
/* 415 */                         pm("No Lobby Point Set!", player);
/* 416 */                         return true;
/*     */                       }
/* 418 */                       pm("Missing Spawn Point", player);
/* 419 */                       return true;
/*     */                     }
/* 421 */                     pm("Missing Pos2", player);
/* 422 */                     return true;
/*     */                   }
/* 424 */                   pm("Missing Pos1", player);
/* 425 */                   return true;
/*     */                 }
/* 427 */                 if (args[2].equalsIgnoreCase("disable"))
/*     */                 {
/* 429 */                   BowSpleef.arenaConfig.set("arenas." + arena + ".enabled", Boolean.valueOf(false));
/* 430 */                   this.plugin.saveConfig();
/* 431 */                   pm("The Arena, " + arena + ", has been disabled!", player);
/* 432 */                   return true;
/*     */                 }
/* 434 */                 if (args[2].equalsIgnoreCase("disabled"))
/*     */                 {
/* 436 */                   BowSpleef.arenaConfig.set("arenas." + arena + ".enabled", Boolean.valueOf(false));
/* 437 */                   this.plugin.saveConfig();
/* 438 */                   pm("The Arena, " + arena + ", has been disabled!", player);
/* 439 */                   return true;
/*     */                 }
/* 441 */                 if (args[2].equalsIgnoreCase("lobby"))
/*     */                 {
/* 443 */                   Location lobby = player.getLocation();
/* 444 */                   int x = lobby.getBlockX(); int y = lobby.getBlockY(); int z = lobby.getBlockZ();
/* 445 */                   String world = player.getWorld().getName();
/* 446 */                   if (world == BowSpleef.arenaConfig.getString("arenas." + arena + ".world"))
/*     */                   {
/* 448 */                     BowSpleef.arenaConfig.set("arenas." + arena + ".lobby.x", Integer.valueOf(x));
/* 449 */                     BowSpleef.arenaConfig.set("arenas." + arena + ".lobby.y", Integer.valueOf(y));
/* 450 */                     BowSpleef.arenaConfig.set("arenas." + arena + ".lobby.z", Integer.valueOf(z));
/* 451 */                     pm("Lobby Point Set!", player);
/* 452 */                     this.plugin.saveConfig();
/* 453 */                     return true;
/*     */                   }
/* 455 */                   pm("You are in the wrong world!", player);
/* 456 */                   return true;
/*     */                 }
/* 458 */                 pm("Incorrect Usage!", player);
/* 459 */                 return false;
/*     */               }
/* 461 */               sender.sendMessage(ChatColor.AQUA + "[BowSpleef]" + ChatColor.GRAY + " Arena doesn't Exist!");
/* 462 */               return true;
/*     */             }
/* 464 */             pm("Incorrect Usage!", player);
/* 465 */             return false;
/*     */           }
/* 467 */           this.plugin.saveConfig();
/* 468 */           return true;
/*     */         }
/* 470 */         sender.sendMessage("[BowSpleef] This command can only be run by a Player!");
/* 471 */         return true;
/*     */       }
/* 473 */       return false;
/*     */     }
/*     */ 
/* 476 */     sender.sendMessage("[BowSpleef] This command can only be run by a Player!");
/*     */ 
/* 478 */     return true;
/*     */   }
/*     */ 
/*     */   public void regen(Location loc1, Location loc2, World w)
/*     */   {
/* 483 */     int minx = Math.min(loc1.getBlockX(), loc2.getBlockX()); int miny = Math.min(loc1.getBlockY(), loc2.getBlockY()); int minz = Math.min(loc1.getBlockZ(), loc2.getBlockZ()); int maxx = Math.max(loc1.getBlockX(), loc2.getBlockX()); int maxy = Math.max(loc1.getBlockY(), loc2.getBlockY()); int maxz = Math.max(loc1.getBlockZ(), loc2.getBlockZ());
/* 484 */     for (int x = minx; x <= maxx; x++)
/*     */     {
/* 486 */       for (int y = miny; y <= maxy; y++)
/*     */       {
/* 488 */         for (int z = minz; z <= maxz; z++)
/*     */         {
/* 490 */           Block b = w.getBlockAt(x, y, z);
/* 491 */           if (b.getType() == Material.AIR)
/* 492 */             b.setType(Material.TNT);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void pm(String message, Player player)
/*     */   {
/* 500 */     player.sendMessage(ChatColor.AQUA + "[BowSpleef] " + ChatColor.GRAY + message);
/*     */   }
/*     */ }

/* Location:           /Users/elliottolson/Downloads/BowSpleef.jar
 * Qualified Name:     com.untoldadventures.bowspleef.BowSpleefCommandExecutor
 * JD-Core Version:    0.6.2
 */