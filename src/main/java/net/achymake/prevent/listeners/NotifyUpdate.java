package net.achymake.prevent.listeners;

import net.achymake.prevent.Prevent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class NotifyUpdate implements Listener {
    private Prevent getPlugin() {
        return Prevent.getInstance();
    }
    public NotifyUpdate(Prevent plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onNotifyUpdate(PlayerJoinEvent event) {
        if (!event.getPlayer().hasPermission("prevent.command"))return;
        getPlugin().getUpdate(event.getPlayer());
    }
}