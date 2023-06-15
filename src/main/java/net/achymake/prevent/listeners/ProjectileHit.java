package net.achymake.prevent.listeners;

import net.achymake.prevent.Prevent;
import net.achymake.prevent.files.EntityData;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.projectiles.ProjectileSource;

public class ProjectileHit implements Listener {
    private EntityData getEntityData() {
        return Prevent.getEntityData();
    }
    public ProjectileHit(Plugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onProjectileHit(ProjectileHitEvent event) {
        if (getShooter(event.getEntity().getShooter()) == null) {
            event.getEntity().remove();
        } else {
            if (isEntity(event.getHitEntity())) {
                getEntityData().forceCancel(event.getEntity());
            }
            if (isBlock(event.getHitBlock())) {
                getEntityData().cancelTask(getShooter(event.getEntity().getShooter()), event.getEntity());
            }
        }
    }
    private Entity getShooter(ProjectileSource projectileSource) {
        return (Entity) projectileSource;
    }
    private boolean isBlock(Block block) {
        return block != null;
    }
    private boolean isEntity(Entity entity) {
        return entity != null;
    }
}