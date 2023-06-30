package net.achymake.prevent.listeners;

import net.achymake.prevent.Prevent;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMove implements Listener {
    private FileConfiguration getConfig() {
        return Prevent.getConfiguration();
    }
    public PlayerMove(Prevent plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerMove(PlayerMoveEvent event) {
        if (!getConfig().getBoolean("prevent.moving-into-unloaded-chunks"))return;
        if (isNull(event.getTo()) || isUnloaded(event.getTo().getChunk())) {
            event.setCancelled(true);
        }
    }
    private boolean isNull(Location location) {
        return location == null;
    }
    private boolean isUnloaded(Chunk chunk) {
        return !chunk.isLoaded();
    }
}