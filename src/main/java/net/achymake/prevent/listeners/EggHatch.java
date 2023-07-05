package net.achymake.prevent.listeners;

import net.achymake.prevent.Prevent;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class EggHatch implements Listener {
    private FileConfiguration getConfig() {
        return Prevent.getConfiguration();
    }
    public EggHatch(Prevent plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onEggHatch(CreatureSpawnEvent event) {
        if (!getConfig().getBoolean("prevent.egg-hatch"))return;
        if (!event.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.EGG))return;
        event.setCancelled(true);
    }
}