package net.achymake.prevent.listeners;

import net.achymake.prevent.Prevent;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.plugin.Plugin;

public class EntityChangeBlock implements Listener {
    private FileConfiguration getConfig() {
        return Prevent.getConfiguration();
    }
    public EntityChangeBlock(Plugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onEntityChangeBlock(EntityChangeBlockEvent event) {
        if (!getConfig().getBoolean("prevent.change-block." + event.getEntity().getType()))return;
        event.setCancelled(true);
    }
}