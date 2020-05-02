package io.alerium.actionregions.listeners;

import io.alerium.actionregions.ActionRegionsPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {
    
    private final ActionRegionsPlugin plugin = ActionRegionsPlugin.getInstance();
    
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        plugin.getRegionManager().getPlayerRegions().remove(event.getPlayer().getUniqueId());
    }
    
}
