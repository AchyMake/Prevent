package net.achymake.prevent.listeners;

import net.achymake.prevent.Prevent;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;

public class Trample implements Listener {
    private FileConfiguration getConfig() {
        return Prevent.getConfiguration();
    }
    public Trample(Plugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onTrampleFarmland(PlayerInteractEvent event) {
        if (!event.getAction().equals(Action.PHYSICAL))return;
        if (event.getClickedBlock() == null)return;
        if (!getConfig().getBoolean("prevent.trample." + event.getClickedBlock().getType()))return;
        event.setCancelled(true);
    }
}