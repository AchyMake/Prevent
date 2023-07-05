package net.achymake.prevent.listeners;

import net.achymake.prevent.Prevent;
import org.bukkit.GameMode;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreak implements Listener {
    private FileConfiguration getConfig() {
        return Prevent.getConfiguration();
    }
    public BlockBreak(Prevent plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onBlockBreak(BlockBreakEvent event) {
        if (!getConfig().getBoolean("prevent.creative-block-drops"))return;
        if (!event.getPlayer().getGameMode().equals(GameMode.CREATIVE))return;
        event.getBlock().getDrops().clear();
    }
}