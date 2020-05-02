package io.alerium.actionregions.tasks;

import io.alerium.actionregions.ActionRegionsPlugin;
import io.alerium.actionregions.event.RegionEnterEvent;
import io.alerium.actionregions.event.RegionLeaveEvent;
import io.alerium.actionregions.regions.RegionManager;
import io.alerium.actionregions.regions.objects.TrackedRegion;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.codemc.worldguardwrapper.region.IWrappedRegion;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class MoveTask extends BukkitRunnable {
    
    private final ActionRegionsPlugin plugin = ActionRegionsPlugin.getInstance();
    private final RegionManager regionManager = plugin.getRegionManager();
    
    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            Set<IWrappedRegion> regions = plugin.getWorldGuard().getRegions(player.getLocation());
            
            List<String> oldRegions = regionManager.getPlayerRegions().get(player.getUniqueId());
            List<String> newRegions = regions.stream()
                    .map(IWrappedRegion::getId)
                    .collect(Collectors.toList());

            if (oldRegions == null) {
                regionManager.getPlayerRegions().put(player.getUniqueId(), newRegions);
                continue;
            }
            
            for (String region : newRegions) {
                if (oldRegions.contains(region))
                    continue;

                TrackedRegion trackedRegion = plugin.getRegionManager().getTrackedRegion(region);
                Bukkit.getScheduler().runTask(plugin, () -> {
                    Bukkit.getPluginManager().callEvent(new RegionEnterEvent(player, region));
                    if (trackedRegion != null && trackedRegion.getEnterAction() != null)
                        trackedRegion.getEnterAction().execute(player);
                });
            }

            for (String region : oldRegions) {
                if (newRegions.contains(region))
                    continue;

                TrackedRegion trackedRegion = plugin.getRegionManager().getTrackedRegion(region);
                Bukkit.getScheduler().runTask(plugin, () -> {
                    Bukkit.getPluginManager().callEvent(new RegionLeaveEvent(player, region));
                    if (trackedRegion != null && trackedRegion.getLeaveAction() != null)
                        trackedRegion.getLeaveAction().execute(player);
                });
            }

            regionManager.getPlayerRegions().put(player.getUniqueId(), newRegions);
        }
    }
    
}
