package io.alerium.actionregions.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@RequiredArgsConstructor @Getter
public class RegionEnterEvent extends Event {
    
    private static final HandlerList HANDLERS = new HandlerList();
    
    private final Player player;
    private final String region;
    
    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }
    
    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
    
}
