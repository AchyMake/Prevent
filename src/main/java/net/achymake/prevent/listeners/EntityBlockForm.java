package net.achymake.prevent.listeners;

import net.achymake.prevent.Prevent;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.EntityBlockFormEvent;
import org.bukkit.plugin.Plugin;

public class EntityBlockForm implements Listener {
    private FileConfiguration getConfig() {
        return Prevent.getInstance().getConfig();
    }
    public EntityBlockForm(Plugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onEntityBlockForm(EntityBlockFormEvent event) {
        if (!getConfig().getBoolean("prevent.block-form." + event.getEntity().getType()))return;
        event.setCancelled(true);
    }
}