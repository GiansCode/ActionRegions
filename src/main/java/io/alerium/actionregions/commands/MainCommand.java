package io.alerium.actionregions.commands;

import io.alerium.actionregions.ActionRegionsPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class MainCommand implements CommandExecutor {
    
    private final ActionRegionsPlugin plugin = ActionRegionsPlugin.getInstance();
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("actionregions.admin")) {
            sender.sendMessage(plugin.getMessage("messages.noPermission"));
            return true;
        }
        
        if (args.length == 0 || !args[0].equalsIgnoreCase("reload")) {
            sender.sendMessage(plugin.getMessage("messages.usage"));
            return true;
        }
        
        plugin.reloadConfig();
        plugin.getRegionManager().enable();
        
        sender.sendMessage(plugin.getMessage("messages.reloaded"));
        return true;
    }
    
}
