package net.achymake.prevent.files;

import net.achymake.prevent.Prevent;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class EntityData {
    private final Prevent plugin;
    public EntityData(Prevent plugin) {
        this.plugin = plugin;
    }
    private FileConfiguration getConfig() {
        return Prevent.getConfiguration();
    }
    private PersistentDataContainer data(Entity entity) {
        return entity.getPersistentDataContainer();
    }
    public boolean hasTask(Entity entity) {
        return data(entity).has(NamespacedKey.minecraft("prevent.task"), PersistentDataType.INTEGER);
    }
    public void setTask(Entity entity, int value) {
        data(entity).set(NamespacedKey.minecraft("prevent.task"), PersistentDataType.INTEGER, value);
    }
    public int getTask(Entity entity) {
        return data(entity).get(NamespacedKey.minecraft("prevent.task"), PersistentDataType.INTEGER);
    }
    public void removeTask(Entity entity) {
        data(entity).remove(NamespacedKey.minecraft("prevent.task"));
    }
    public void cancelTask(Entity shooter, Entity projectile) {
        if (getConfig().getBoolean("projectile-launch." + shooter.getType() + ".cancel-on-hit")) {
            if (hasTask(projectile)) {
                int value = getTask(projectile);
                if (plugin.getServer().getScheduler().isQueued(value)) {
                    plugin.getServer().getScheduler().cancelTask(value);
                    removeTask(projectile);
                }
            }
        }
    }
    public void forceCancel(Entity projectile) {
        if (hasTask(projectile)) {
            int value = getTask(projectile);
            if (plugin.getServer().getScheduler().isQueued(value)) {
                plugin.getServer().getScheduler().cancelTask(value);
                removeTask(projectile);
            }
        }
    }
    public void runTask(Entity shooter, Entity projectile) {
        if (getConfig().getInt("projectile-launch." + shooter.getType() + "." + projectile.getType()) > 0) {
            int taskId = plugin.getServer().getScheduler().runTaskLater(plugin, new Runnable() {
                @Override
                public void run() {
                    projectile.remove();
                }
            }, getConfig().getInt("projectile-launch." + shooter.getType() + "." + projectile.getType())).getTaskId();
            setTask(projectile, taskId);
        }
    }
}