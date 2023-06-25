package net.achymake.prevent.listeners;

import net.achymake.prevent.Prevent;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockRedstoneEvent;

public class BlockRedstone implements Listener {
    private FileConfiguration getConfig() {
        return Prevent.getConfiguration();
    }
    public BlockRedstone(Prevent plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onBlockRedstone(BlockRedstoneEvent event) {
        if (!getConfig().getBoolean("prevent.redstone"))return;
        event.setNewCurrent(0);
    }
}