/*     */ package com.untoldadventures.bowspleef;
/*     */ 
/*     */ import com.untoldadventures.bowspleef.events.EventListener;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.Server;
/*     */ import org.bukkit.command.PluginCommand;
/*     */ import org.bukkit.configuration.file.FileConfiguration;
/*     */ import org.bukkit.configuration.file.YamlConfiguration;
/*     */ import org.bukkit.plugin.PluginManager;
/*     */ import org.bukkit.plugin.java.JavaPlugin;
/*     */ 
/*     */ public class BowSpleef extends JavaPlugin
/*     */ {
/*  16 */   public String Version = "0.1";
/*     */   public static File pluginFolder;
/*     */   public static File configFile;
/*     */   public static File arenaFile;
/*     */   public static File invFile;
/*     */   public static FileConfiguration bowtntConfig;
/*     */   public static FileConfiguration arenaConfig;
/*     */   public static FileConfiguration invConfig;
/*  24 */   public static List<Arena> arenas = new ArrayList();
/*     */ 
/*     */   public void onEnable()
/*     */   {
/*  30 */     getCommand("bs").setExecutor(new BowSpleefCommandExecutor(this));
/*     */ 
/*  33 */     getServer().getPluginManager().registerEvents(new EventListener(this), this);
/*     */ 
/*  36 */     pluginFolder = getDataFolder();
/*  37 */     configFile = new File(pluginFolder, "config.yml");
/*  38 */     arenaFile = new File(pluginFolder, "arenas.yml");
/*  39 */     invFile = new File(pluginFolder, "inventories.yml");
/*  40 */     bowtntConfig = new YamlConfiguration();
/*  41 */     arenaConfig = new YamlConfiguration();
/*  42 */     invConfig = new YamlConfiguration();
/*     */ 
/*  44 */     if (!pluginFolder.exists())
/*     */     {
/*     */       try
/*     */       {
/*  48 */         pluginFolder.mkdir();
/*     */       }
/*     */       catch (Exception localException) {
/*     */       }
/*     */     }
/*  53 */     if (!configFile.exists())
/*     */     {
/*     */       try
/*     */       {
/*  57 */         configFile.createNewFile();
/*  58 */         List cmds = new ArrayList();
/*  59 */         cmds.add("tp");
/*  60 */         cmds.add("tpa");
/*  61 */         cmds.add("home");
/*  62 */         cmds.add("sethome");
/*  63 */         bowtntConfig.set("whitelisted-commands", cmds);
/*     */       }
/*     */       catch (Exception localException1) {
/*     */       }
/*     */     }
/*  68 */     if (!arenaFile.exists())
/*     */     {
/*     */       try
/*     */       {
/*  72 */         arenaFile.createNewFile();
/*     */       }
/*     */       catch (Exception localException2)
/*     */       {
/*     */       }
/*     */     }
/*  78 */     if (!invFile.exists())
/*     */     {
/*     */       try
/*     */       {
/*  82 */         invFile.createNewFile();
/*     */       }
/*     */       catch (Exception localException3)
/*     */       {
/*     */       }
/*     */     }
/*     */     try {
/*  89 */       bowtntConfig.load(configFile);
/*  90 */       arenaConfig.load(arenaFile);
/*  91 */       invConfig.load(invFile);
/*     */     }
/*     */     catch (Exception localException4)
/*     */     {
/*     */     }
/*     */   }
/*     */ 
/*     */   public void onDisable()
/*     */   {
/* 101 */     List arenas = arenaConfig.getStringList("List");
/*     */     Iterator localIterator2;
/* 103 */     for (Iterator localIterator1 = arenas.iterator(); localIterator1.hasNext(); 
/* 109 */       localIterator2.hasNext())
/*     */     {
/* 103 */       String arena = (String)localIterator1.next();
/*     */ 
/* 105 */       arenaConfig.set("arenas." + arena + ".inGame", Boolean.valueOf(false));
/*     */ 
/* 107 */       List players = arenaConfig.getStringList("arenas." + arena + ".players");
/*     */ 
/* 109 */       localIterator2 = players.iterator(); continue; String player = (String)localIterator2.next();
/*     */ 
/* 111 */       Methods.quitNoWin(Bukkit.getPlayer(player), this);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void saveConfig()
/*     */   {
/*     */     try
/*     */     {
/* 120 */       bowtntConfig.save(configFile);
/* 121 */       arenaConfig.save(arenaFile);
/* 122 */       invConfig.save(invFile);
/*     */     }
/*     */     catch (IOException e) {
/* 125 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void getArenas()
/*     */   {
/* 131 */     arenas.clear();
/* 132 */     int nofa = arenaConfig.getStringList("List").size();
/* 133 */     for (int i = 0; i <= nofa; i++)
/*     */     {
/* 135 */       String name = (String)arenaConfig.getStringList("List").get(i);
/*     */ 
/* 137 */       Arena arena = new Arena(name, this);
/*     */ 
/* 139 */       arenas.add(arena);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void loadConfig()
/*     */   {
/*     */     try
/*     */     {
/* 148 */       bowtntConfig.load(configFile);
/* 149 */       arenaConfig.load(arenaFile);
/* 150 */       invConfig.load(invFile);
/*     */     }
/*     */     catch (Exception ex)
/*     */     {
/* 154 */       ex.printStackTrace();
/*     */     }
/*     */   }
/*     */ }

/* Location:           /Users/elliottolson/Downloads/BowSpleef.jar
 * Qualified Name:     com.untoldadventures.bowspleef.BowSpleef
 * JD-Core Version:    0.6.2
 */