package net.achymake.prevent;

import net.achymake.prevent.commands.MainCommand;
import net.achymake.prevent.files.EntityData;
import net.achymake.prevent.files.Message;
import net.achymake.prevent.listeners.*;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

public final class Prevent extends JavaPlugin {
    private static Prevent instance;
    public static Prevent getInstance() {
        return instance;
    }
    private static Message message;
    public static Message getMessage() {
        return message;
    }
    private static EntityData entityData;
    public static EntityData getEntityData() {
        return entityData;
    }
    @Override
    public void onEnable() {
        instance = this;
        message = new Message(this);
        entityData = new EntityData(this);
        reload();
        getCommand("prevent").setExecutor(new MainCommand());
        new CreatureSpawn(this);
        new EntityBlockForm(this);
        new EntityChangeBlock(this);
        new EntityExplode(this);
        new EntityTarget(this);
        new PlayerMove(this);
        new ProjectileHit(this);
        new ProjectileHitByPlayerCreative(this);
        new ProjectileLaunch(this);
        new Trample(this);
        message.sendLog(Level.INFO, "Enabled " + getName() + " " + getDescription().getVersion());
    }
    @Override
    public void onDisable() {
        getServer().getScheduler().cancelTasks(this);
        message.sendLog(Level.INFO, "Disabled " + getName() + " " + getDescription().getVersion());
    }
    public void reload() {
        if (new File(getDataFolder(), "config.yml").exists()) {
            try {
                getConfig().load(new File(getDataFolder(), "config.yml"));
                saveConfig();
            } catch (IOException | InvalidConfigurationException e) {
                message.sendLog(Level.WARNING, e.getMessage());
            }
        } else {
            getConfig().options().copyDefaults(true);
            saveConfig();
        }
    }
}