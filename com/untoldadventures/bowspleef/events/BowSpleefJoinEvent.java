/*    */ package com.untoldadventures.bowspleef.events;
/*    */ 
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.Event;
/*    */ import org.bukkit.event.HandlerList;
/*    */ 
/*    */ public class BowSpleefJoinEvent extends Event
/*    */ {
/*  8 */   private static final HandlerList handlers = new HandlerList();
/*  9 */   private Player player = null;
/* 10 */   private String arena = null;
/*    */ 
/*    */   public BowSpleefJoinEvent(Player player, String arena)
/*    */   {
/* 14 */     this.player = player;
/* 15 */     this.arena = arena;
/*    */   }
/*    */ 
/*    */   public Player getPlayer()
/*    */   {
/* 20 */     return this.player;
/*    */   }
/*    */ 
/*    */   public String getArena()
/*    */   {
/* 25 */     return this.arena;
/*    */   }
/*    */ 
/*    */   public HandlerList getHandlers()
/*    */   {
/* 30 */     return handlers;
/*    */   }
/*    */ 
/*    */   public static HandlerList getHandlerList()
/*    */   {
/* 35 */     return handlers;
/*    */   }
/*    */ }

/* Location:           /Users/elliottolson/Downloads/BowSpleef.jar
 * Qualified Name:     com.untoldadventures.bowspleef.events.BowSpleefJoinEvent
 * JD-Core Version:    0.6.2
 */