/*    */ package com.untoldadventures.bowspleef.events;
/*    */ 
/*    */ import org.bukkit.event.Event;
/*    */ import org.bukkit.event.HandlerList;
/*    */ 
/*    */ public class BowSpleefStartEvent extends Event
/*    */ {
/*  7 */   private static final HandlerList handlers = new HandlerList();
/*  8 */   private String arena = null;
/*    */ 
/*    */   public BowSpleefStartEvent(String arena)
/*    */   {
/* 12 */     this.arena = arena;
/*    */   }
/*    */ 
/*    */   public String getArena()
/*    */   {
/* 17 */     return this.arena;
/*    */   }
/*    */ 
/*    */   public HandlerList getHandlers()
/*    */   {
/* 22 */     return handlers;
/*    */   }
/*    */ 
/*    */   public static HandlerList getHandlerList()
/*    */   {
/* 27 */     return handlers;
/*    */   }
/*    */ }

/* Location:           /Users/elliottolson/Downloads/BowSpleef.jar
 * Qualified Name:     com.untoldadventures.bowspleef.events.BowSpleefStartEvent
 * JD-Core Version:    0.6.2
 */