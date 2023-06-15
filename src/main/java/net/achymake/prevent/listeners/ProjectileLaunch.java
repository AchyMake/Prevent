package net.achymake.prevent.listeners;

import net.achymake.prevent.Prevent;
import net.achymake.prevent.files.EntityData;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.projectiles.ProjectileSource;

public class ProjectileLaunch implements Listener {
    private EntityData getEntityData() {
        return Prevent.getEntityData();
    }
    public ProjectileLaunch(Plugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onProjectileLaunch(ProjectileLaunchEvent event) {
        if (getShooter(event.getEntity().getShooter()) == null)return;
        getEntityData().runTask(getShooter(event.getEntity().getShooter()), event.getEntity());
    }
    private Entity getShooter(ProjectileSource projectileSource) {
        return (Entity) projectileSource;
    }
}