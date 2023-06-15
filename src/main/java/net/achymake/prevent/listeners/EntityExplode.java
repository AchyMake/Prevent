package net.achymake.prevent.listeners;

import net.achymake.prevent.Prevent;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.plugin.Plugin;

public class EntityExplode implements Listener {
    private FileConfiguration getConfig() {
        return Prevent.getInstance().getConfig();
    }
    public EntityExplode(Plugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onEntityExplode(EntityExplodeEvent event) {
        if (!getConfig().getBoolean("prevent.explode." + event.getEntity().getType()))return;
        event.setCancelled(true);
    }
}