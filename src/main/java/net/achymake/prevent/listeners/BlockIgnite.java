package net.achymake.prevent.listeners;

import net.achymake.prevent.Prevent;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockIgniteEvent;

public class BlockIgnite implements Listener {
    private FileConfiguration getConfig() {
        return Prevent.getConfiguration();
    }
    public BlockIgnite(Prevent plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onBlockIgnite(BlockIgniteEvent event) {
        if (!getConfig().getBoolean("prevent.fire-spread"))return;
        if (event.getCause().equals(BlockIgniteEvent.IgniteCause.LAVA)) {
            event.setCancelled(true);
        }
        if (event.getCause().equals(BlockIgniteEvent.IgniteCause.SPREAD)) {
            if (event.getIgnitingBlock() == null)return;
            event.getIgnitingBlock().setType(Material.AIR);
        }
    }
}