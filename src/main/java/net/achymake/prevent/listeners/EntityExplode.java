package net.achymake.prevent.listeners;

import net.achymake.prevent.Prevent;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

public class EntityExplode implements Listener {
    private FileConfiguration getConfig() {
        return Prevent.getConfiguration();
    }
    public EntityExplode(Prevent plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onEntityExplode(EntityExplodeEvent event) {
        if (!getConfig().getBoolean("prevent.explode." + event.getEntity().getType()))return;
        if (event.blockList().isEmpty())return;
        event.blockList().clear();
    }
}