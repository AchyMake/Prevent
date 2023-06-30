package net.achymake.prevent.listeners;

import net.achymake.prevent.Prevent;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetEvent;

public class EntityTarget implements Listener {
    private FileConfiguration getConfig() {
        return Prevent.getConfiguration();
    }
    public EntityTarget(Prevent plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onEntityTarget(EntityTargetEvent event) {
        if (event.getTarget() == null)return;
        if (!getConfig().getBoolean("prevent.entity-target." + event.getEntity().getType() + "." + event.getTarget().getType()))return;
        event.setCancelled(true);
    }
}