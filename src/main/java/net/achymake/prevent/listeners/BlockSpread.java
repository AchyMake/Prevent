package net.achymake.prevent.listeners;

import net.achymake.prevent.Prevent;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockSpreadEvent;

public class BlockSpread implements Listener {
    private FileConfiguration getConfig() {
        return Prevent.getConfiguration();
    }
    public BlockSpread(Prevent plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onBlockSpread(BlockSpreadEvent event) {
        if (!getConfig().getBoolean("prevent.fire-spread"))return;
        if (event.getSource().getType().equals(Material.FIRE) || event.getSource().getType().equals(Material.LAVA)) {
            event.setCancelled(true);
        }
    }
}