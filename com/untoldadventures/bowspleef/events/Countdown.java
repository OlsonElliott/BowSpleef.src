/*    */ package com.untoldadventures.bowspleef.events;
/*    */ 
/*    */ import com.untoldadventures.bowspleef.BowSpleef;
/*    */ import com.untoldadventures.bowspleef.Methods;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.Server;
/*    */ import org.bukkit.configuration.file.FileConfiguration;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.plugin.PluginManager;
/*    */ import org.bukkit.scheduler.BukkitRunnable;
/*    */ 
/*    */ public class Countdown extends BukkitRunnable
/*    */ {
/*    */   private String arena;
/* 16 */   private int cntDwn = 10;
/*    */ 
/*    */   public Countdown(String arena)
/*    */   {
/* 20 */     this.arena = arena;
/*    */   }
/*    */ 
/*    */   public void run()
/*    */   {
/* 26 */     List players = BowSpleef.arenaConfig.getStringList("arenas." + this.arena + ".players");
/*    */ 
/* 28 */     if (this.cntDwn > 0)
/*    */     {
/* 30 */       Iterator i = players.iterator();
/* 31 */       while (i.hasNext())
/*    */       {
/* 33 */         pm("The Game will start in " + this.cntDwn + " seconds!", Bukkit.getPlayer((String)i.next()));
/*    */       }
/*    */ 
/* 36 */       this.cntDwn -= 1;
/*    */     }
/*    */ 
/* 39 */     if (this.cntDwn == 0)
/*    */     {
/* 41 */       Iterator i = players.iterator();
/* 42 */       while (i.hasNext())
/*    */       {
/* 44 */         pm("The Game has Begun!", Bukkit.getPlayer((String)i.next()));
/*    */       }
/*    */ 
/* 48 */       BowSpleefStartEvent event = new BowSpleefStartEvent(this.arena);
/* 49 */       Bukkit.getServer().getPluginManager().callEvent(event);
/*    */ 
/* 51 */       Methods.start(this.arena);
/*    */ 
/* 53 */       cancel();
/*    */     }
/*    */   }
/*    */ 
/*    */   private void pm(String message, Player player)
/*    */   {
/* 60 */     player.sendMessage(ChatColor.AQUA + "[BowSpleef] " + ChatColor.GRAY + message);
/*    */   }
/*    */ }

/* Location:           /Users/elliottolson/Downloads/BowSpleef.jar
 * Qualified Name:     com.untoldadventures.bowspleef.events.Countdown
 * JD-Core Version:    0.6.2
 */