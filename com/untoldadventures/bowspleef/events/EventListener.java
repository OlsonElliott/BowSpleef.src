/*     */ package com.untoldadventures.bowspleef.events;
/*     */ 
/*     */ import com.untoldadventures.bowspleef.BowSpleef;
/*     */ import com.untoldadventures.bowspleef.InventoryStringDeSerializer;
/*     */ import com.untoldadventures.bowspleef.Methods;
/*     */ import java.util.List;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.GameMode;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.Server;
/*     */ import org.bukkit.World;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.block.Sign;
/*     */ import org.bukkit.configuration.file.FileConfiguration;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.EventPriority;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.event.block.Action;
/*     */ import org.bukkit.event.block.SignChangeEvent;
/*     */ import org.bukkit.event.entity.EntityDamageEvent;
/*     */ import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
/*     */ import org.bukkit.event.entity.PlayerDeathEvent;
/*     */ import org.bukkit.event.player.PlayerCommandPreprocessEvent;
/*     */ import org.bukkit.event.player.PlayerDropItemEvent;
/*     */ import org.bukkit.event.player.PlayerInteractEvent;
/*     */ import org.bukkit.event.player.PlayerJoinEvent;
/*     */ import org.bukkit.event.player.PlayerQuitEvent;
/*     */ import org.bukkit.event.player.PlayerRespawnEvent;
/*     */ import org.bukkit.inventory.Inventory;
/*     */ import org.bukkit.inventory.PlayerInventory;
/*     */ 
/*     */ public class EventListener
/*     */   implements Listener
/*     */ {
/*     */   BowSpleef plugin;
/*  35 */   int cntDwn = 10;
/*     */ 
/*     */   public EventListener(BowSpleef plugin)
/*     */   {
/*  39 */     this.plugin = plugin;
/*     */   }
/*     */ 
/*     */   public void pm(String message, Player player)
/*     */   {
/*  44 */     player.sendMessage(ChatColor.AQUA + "[BowSpleef] " + ChatColor.GRAY + message);
/*     */   }
/*     */ 
/*     */   @EventHandler(priority=EventPriority.NORMAL)
/*     */   public void onPlayerDeath(PlayerDeathEvent event)
/*     */   {
/*  50 */     Player player = event.getEntity();
/*     */ 
/*  52 */     if (BowSpleef.invConfig.contains(player.getName()))
/*     */     {
/*  55 */       String arena = BowSpleef.invConfig.getString(player.getName() + ".arena");
/*     */ 
/*  57 */       int gmnum = BowSpleef.invConfig.getInt(player.getName() + ".return.gamemode");
/*  58 */       GameMode gm = GameMode.getByValue(gmnum);
/*  59 */       player.setGameMode(gm);
/*     */ 
/*  61 */       Inventory inv = InventoryStringDeSerializer.StringToInventory(BowSpleef.invConfig.getString(player.getName() + ".inventory"));
/*  62 */       player.getInventory().setContents(inv.getContents());
/*     */ 
/*  64 */       List players = BowSpleef.arenaConfig.getStringList("arenas." + arena + ".players");
/*  65 */       List voted = BowSpleef.arenaConfig.getStringList("arenas." + arena + ".voted");
/*  66 */       players.remove(player.getName());
/*  67 */       voted.remove(player.getName());
/*  68 */       BowSpleef.arenaConfig.set("arenas." + arena + ".players", players);
/*  69 */       BowSpleef.arenaConfig.set("arenas." + arena + ".voted", voted);
/*     */ 
/*  71 */       event.setDeathMessage("");
/*     */ 
/*  73 */       if (players.size() == 1)
/*     */       {
/*  75 */         Bukkit.getServer().broadcastMessage(ChatColor.AQUA + "[BowSpleef] " + ChatColor.GRAY + (String)players.get(0) + " Won BowSpleef!");
/*  76 */         BowSpleef.arenaConfig.set("arenas." + arena + ".inGame", Boolean.valueOf(false));
/*     */ 
/*  78 */         Methods.regen(arena, this.plugin);
/*     */ 
/*  80 */         Player winner = Bukkit.getPlayer((String)players.get(0));
/*     */ 
/*  83 */         String arena1 = BowSpleef.invConfig.getString(winner.getName() + ".arena");
/*     */ 
/*  85 */         int gmnum1 = BowSpleef.invConfig.getInt(winner.getName() + ".return.gamemode");
/*  86 */         GameMode gm1 = GameMode.getByValue(gmnum1);
/*  87 */         winner.setGameMode(gm1);
/*     */ 
/*  89 */         Inventory inv1 = InventoryStringDeSerializer.StringToInventory(BowSpleef.invConfig.getString(winner.getName() + ".inventory"));
/*  90 */         winner.getInventory().setContents(inv1.getContents());
/*     */ 
/*  92 */         int x = BowSpleef.invConfig.getInt(winner.getName() + ".return.x");
/*  93 */         int y = BowSpleef.invConfig.getInt(winner.getName() + ".return.y");
/*  94 */         int z = BowSpleef.invConfig.getInt(winner.getName() + ".return.z");
/*  95 */         World w = Bukkit.getWorld(BowSpleef.invConfig.getString(winner.getName() + ".return.world"));
/*  96 */         Location retpos = new Location(w, x, y, z);
/*  97 */         winner.teleport(retpos);
/*     */ 
/*  99 */         BowSpleef.invConfig.set(winner.getName(), null);
/*     */ 
/* 101 */         List players1 = BowSpleef.arenaConfig.getStringList("arenas." + arena1 + ".players");
/* 102 */         List voted1 = BowSpleef.arenaConfig.getStringList("arenas." + arena1 + ".voted");
/* 103 */         players1.remove(winner.getName());
/* 104 */         voted1.remove(winner.getName());
/* 105 */         BowSpleef.arenaConfig.set("arenas." + arena1 + ".players", players1);
/* 106 */         BowSpleef.arenaConfig.set("arenas." + arena1 + ".voted", voted1);
/*     */       }
/*     */ 
/* 110 */       this.plugin.saveConfig();
/*     */     }
/*     */   }
/*     */ 
/*     */   @EventHandler(priority=EventPriority.NORMAL)
/*     */   public void onRespawn(PlayerRespawnEvent event)
/*     */   {
/* 118 */     Player player = event.getPlayer();
/*     */ 
/* 120 */     if (BowSpleef.invConfig.contains(player.getName()))
/*     */     {
/* 123 */       int x1 = BowSpleef.invConfig.getInt(player.getName() + ".return.x");
/* 124 */       int y1 = BowSpleef.invConfig.getInt(player.getName() + ".return.y");
/* 125 */       int z1 = BowSpleef.invConfig.getInt(player.getName() + ".return.z");
/* 126 */       World w1 = Bukkit.getWorld(BowSpleef.invConfig.getString(player.getName() + ".return.world"));
/* 127 */       Location retpos1 = new Location(w1, x1, y1, z1);
/* 128 */       event.setRespawnLocation(retpos1);
/*     */ 
/* 132 */       int gmnum1 = BowSpleef.invConfig.getInt(player.getName() + ".return.gamemode");
/* 133 */       GameMode gm1 = GameMode.getByValue(gmnum1);
/* 134 */       player.setGameMode(gm1);
/*     */ 
/* 136 */       Inventory inv1 = InventoryStringDeSerializer.StringToInventory(BowSpleef.invConfig.getString(player.getName() + ".inventory"));
/* 137 */       player.getInventory().setContents(inv1.getContents());
/*     */ 
/* 139 */       BowSpleef.invConfig.set(player.getName(), null);
/*     */     }
/*     */   }
/*     */ 
/*     */   @EventHandler(priority=EventPriority.NORMAL)
/*     */   public void onDropItem(PlayerDropItemEvent event)
/*     */   {
/* 146 */     Player player = event.getPlayer();
/*     */ 
/* 148 */     if (BowSpleef.invConfig.contains(player.getName()))
/*     */     {
/* 150 */       event.setCancelled(true);
/*     */     }
/*     */   }
/*     */ 
/*     */   @EventHandler(priority=EventPriority.NORMAL)
/*     */   public void onDamage(EntityDamageEvent event)
/*     */   {
/* 157 */     if (((event.getEntity() instanceof Player)) && (event.getCause() != EntityDamageEvent.DamageCause.VOID))
/*     */     {
/* 159 */       Player player = (Player)event.getEntity();
/*     */ 
/* 161 */       if (BowSpleef.invConfig.contains(player.getName()))
/*     */       {
/* 163 */         event.setCancelled(true);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   @EventHandler(priority=EventPriority.NORMAL)
/*     */   public void onCommand(PlayerCommandPreprocessEvent event)
/*     */   {
/* 172 */     Player player = event.getPlayer();
/* 173 */     if (BowSpleef.invConfig.contains(event.getPlayer().getName()))
/*     */     {
/* 175 */       List cmds = BowSpleef.bowtntConfig.getStringList("whitelisted-commands");
/* 176 */       String cmd = event.getMessage();
/* 177 */       if (cmds.contains(cmd))
/*     */       {
/* 179 */         event.setCancelled(true);
/* 180 */         pm("You cannot use that command in an game!", player);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   @EventHandler(priority=EventPriority.NORMAL)
/*     */   public void onInteract(PlayerInteractEvent e)
/*     */   {
/* 189 */     Player player = e.getPlayer();
/*     */ 
/* 191 */     if (e.getAction() == Action.RIGHT_CLICK_BLOCK)
/*     */     {
/* 193 */       if ((e.getClickedBlock().getState() instanceof Sign))
/*     */       {
/* 195 */         Sign s = (Sign)e.getClickedBlock().getState();
/*     */ 
/* 197 */         if ((s.getLine(0).equalsIgnoreCase(ChatColor.DARK_BLUE + "[BowSpleef]")) && (s.getLine(1).equalsIgnoreCase(ChatColor.GREEN + "Join")))
/*     */         {
/* 199 */           String arena = s.getLine(2);
/*     */ 
/* 201 */           if (arena != null)
/*     */           {
/* 203 */             if (BowSpleef.arenaConfig.getBoolean("arenas." + arena + ".enabled"))
/*     */             {
/* 206 */               if (!BowSpleef.arenaConfig.getBoolean("arenas." + arena + ".inGame"))
/*     */               {
/* 208 */                 Methods.join(player, arena, this.plugin);
/* 209 */                 return;
/*     */               }
/*     */ 
/* 212 */               pm("That arena is in game!", player);
/*     */ 
/* 214 */               return;
/*     */             }
/*     */ 
/* 217 */             pm("That arena is Disabled", player);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   @EventHandler(priority=EventPriority.NORMAL)
/*     */   public void signCreation(SignChangeEvent event)
/*     */   {
/* 228 */     if ((event.getLine(0).equalsIgnoreCase("[BowSpleef]")) && (event.getLine(1).equalsIgnoreCase("Join")))
/*     */     {
/* 230 */       if (BowSpleef.arenaConfig.contains("arenas." + event.getLine(2)))
/*     */       {
/* 232 */         pm("Sign creation successful!", event.getPlayer());
/* 233 */         event.setLine(0, ChatColor.DARK_BLUE + "[BowSpleef]");
/* 234 */         event.setLine(1, ChatColor.GREEN + "Join");
/* 235 */         return;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   @EventHandler
/*     */   public void onPlayerDisconnect(PlayerQuitEvent event)
/*     */   {
/* 244 */     Player player = event.getPlayer();
/*     */ 
/* 246 */     Methods.quit(player, this.plugin);
/*     */ 
/* 248 */     this.plugin.saveConfig();
/*     */   }
/*     */ 
/*     */   public void onPlayerConnect(PlayerJoinEvent event)
/*     */   {
/*     */   }
/*     */ }

/* Location:           /Users/elliottolson/Downloads/BowSpleef.jar
 * Qualified Name:     com.untoldadventures.bowspleef.events.EventListener
 * JD-Core Version:    0.6.2
 */