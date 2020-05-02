package io.alerium.actionregions.regions.objects;

import io.alerium.actionregions.ActionRegionsPlugin;
import lombok.AllArgsConstructor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import pw.valaria.requirementsprocessor.RequirementsUtil;

import java.util.List;

@AllArgsConstructor
public class RegionAction {

    private final ConfigurationSection requirements;
    private final List<String> actions;

    /**
     * This method executes all the actions
     * @param player The Player that joined/left the region
     */
    public void execute(Player player) {
        if (!RequirementsUtil.handle(player, requirements))
            return;

        ActionRegionsPlugin.getInstance().getActionUtil().executeActions(player, actions);
    }
    
}
