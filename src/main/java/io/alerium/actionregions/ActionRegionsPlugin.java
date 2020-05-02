package io.alerium.actionregions;

import io.alerium.actionregions.commands.MainCommand;
import io.alerium.actionregions.listeners.PlayerListener;
import io.alerium.actionregions.regions.RegionManager;
import io.alerium.actionregions.tasks.MoveTask;
import io.samdev.actionutil.ActionUtil;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.codemc.worldguardwrapper.WorldGuardWrapper;

public class ActionRegionsPlugin extends JavaPlugin {
    
    @Getter private static ActionRegionsPlugin instance;
    
    @Getter private WorldGuardWrapper worldGuard;
    @Getter private ActionUtil actionUtil;
    @Getter private RegionManager regionManager;
    
    @Override
    public void onEnable() {
        instance = this;
        long time = System.currentTimeMillis();
        
        saveDefaultConfig();
        registerInstances();
        registerListener();
        registerCommands();
        registerTasks();
        
        getLogger().info("Plugin enabled in " + (System.currentTimeMillis()-time) + "ms");
    }
    
    private void registerInstances() {
        worldGuard = WorldGuardWrapper.getInstance();
        actionUtil = ActionUtil.init(this);

        regionManager = new RegionManager();
        regionManager.enable();
    }
    
    private void registerListener() {
        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
    }
    
    private void registerCommands() {
        getCommand("actionregions").setExecutor(new MainCommand());
    }
    
    private void registerTasks() {
        new MoveTask().runTaskTimerAsynchronously(this, 20, getConfig().getInt("checkEvery"));
    }
    
    public String getMessage(String path) {
        return ChatColor.translateAlternateColorCodes('&', getConfig().getString(path));
    }
    
}
