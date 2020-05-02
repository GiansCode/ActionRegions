package io.alerium.actionregions.regions;

import io.alerium.actionregions.ActionRegionsPlugin;
import io.alerium.actionregions.regions.objects.RegionAction;
import io.alerium.actionregions.regions.objects.TrackedRegion;
import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;

import java.util.*;

public class RegionManager {

    private final ActionRegionsPlugin plugin = ActionRegionsPlugin.getInstance();
    
    private final List<TrackedRegion> regions = new ArrayList<>();
    @Getter private final Map<UUID, List<String>> playerRegions = new HashMap<>();

    /**
     * This method enables the RegionManager (loads all the TrackedRegions from the config file)
     */
    public void enable() {
        regions.clear();
        
        ConfigurationSection section = plugin.getConfig().getConfigurationSection("regions");
        if (section == null)
            return;

        for (String id : section.getKeys(false)) {
            ConfigurationSection regionSection = section.getConfigurationSection(id);
            ConfigurationSection enter = regionSection.getConfigurationSection("enter-actions");
            ConfigurationSection leave = regionSection.getConfigurationSection("leave-actions");

            RegionAction enterAction = enter == null ? null : new RegionAction(enter.getConfigurationSection("requirements"), enter.getStringList("actions"));
            RegionAction leaveAction = leave == null ? null : new RegionAction(leave.getConfigurationSection("requirements"), leave.getStringList("actions"));
        
            regions.add(new TrackedRegion(id, enterAction, leaveAction));
        }
        
        plugin.getLogger().info("Loaded " + regions.size() + " regions");
    }

    /**
     * This method gets a TrackedRegion
     * @param id The ID of the region
     * @return The TrackedRegion, null if not found
     */
    public TrackedRegion getTrackedRegion(String id) {
        for (TrackedRegion region : regions) {
            if (region.getId().equals(id))
                return region;
        }
        
        return null;
    }
    
}
