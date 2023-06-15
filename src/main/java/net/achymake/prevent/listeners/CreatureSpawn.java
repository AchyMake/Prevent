package net.achymake.prevent.listeners;

import net.achymake.prevent.Prevent;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.plugin.Plugin;

public class CreatureSpawn implements Listener {
    private FileConfiguration getConfig() {
        return Prevent.getInstance().getConfig();
    }
    public CreatureSpawn(Plugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        if (!getConfig().getBoolean("prevent.spawn." + event.getEntity().getType()))return;
        event.setCancelled(true);
    }
}