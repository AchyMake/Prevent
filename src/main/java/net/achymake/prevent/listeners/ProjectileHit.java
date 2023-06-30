package net.achymake.prevent.listeners;

import net.achymake.prevent.Prevent;
import net.achymake.prevent.files.EntityData;
import org.bukkit.GameMode;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.projectiles.ProjectileSource;

public class ProjectileHit implements Listener {
    private FileConfiguration getConfig() {
        return Prevent.getConfiguration();
    }
    private EntityData getEntityData() {
        return Prevent.getEntityData();
    }
    public ProjectileHit(Prevent plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onProjectileHit(ProjectileHitEvent event) {
        if (getShooter(event.getEntity().getShooter()) == null) {
            event.getEntity().remove();
        } else {
            if (isPlayer(event.getEntity().getShooter())) {
                Player player = (Player) event.getEntity().getShooter();
                if (player.getGameMode().equals(GameMode.CREATIVE)) {
                    if (getConfig().getBoolean("projectile-launch.PLAYER.remove-on-hit-if-creative")) {
                        if (getEntityData().hasTask(event.getEntity())) {
                            getEntityData().forceCancel(event.getEntity());
                        }
                        if (isEntity(event.getHitEntity())) {
                            event.getEntity().remove();
                        }
                        if (isBlock(event.getHitBlock())) {
                            event.getEntity().remove();
                        }
                    } else {
                        if (isEntity(event.getHitEntity())) {
                            getEntityData().forceCancel(event.getEntity());
                        }
                        if (isBlock(event.getHitBlock())) {
                            getEntityData().cancelTask(getShooter(event.getEntity().getShooter()), event.getEntity());
                        }
                    }
                }
            } else {
                if (isEntity(event.getHitEntity())) {
                    getEntityData().forceCancel(event.getEntity());
                }
                if (isBlock(event.getHitBlock())) {
                    getEntityData().cancelTask(getShooter(event.getEntity().getShooter()), event.getEntity());
                }
            }
        }
    }
    private Entity getShooter(ProjectileSource projectileSource) {
        return (Entity) projectileSource;
    }
    private boolean isPlayer(ProjectileSource projectileSource) {
        return projectileSource instanceof Player;
    }
    private boolean isBlock(Block block) {
        return block != null;
    }
    private boolean isEntity(Entity entity) {
        return entity != null;
    }
}