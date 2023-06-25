package net.achymake.prevent.listeners;

import net.achymake.prevent.Prevent;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBurnEvent;

public class BlockBurn implements Listener {
    private FileConfiguration getConfig() {
        return Prevent.getConfiguration();
    }
    public BlockBurn(Prevent plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onBlockBurn(BlockBurnEvent event) {
        if (!getConfig().getBoolean("prevent.fire-spread"))return;
        event.setCancelled(true);
        if (event.getIgnitingBlock() == null)return;
        event.getIgnitingBlock().setType(Material.AIR);
    }
}