package net.achymake.prevent;

import net.achymake.prevent.commands.MainCommand;
import net.achymake.prevent.files.EntityData;
import net.achymake.prevent.files.Message;
import net.achymake.prevent.listeners.*;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

public final class Prevent extends JavaPlugin {
    private static Prevent plugin;
    public static Prevent getInstance() {
        return plugin;
    }
    private static FileConfiguration configuration;
    public static FileConfiguration getConfiguration() {
        return configuration;
    }
    private static Message message;
    public static Message getMessage() {
        return message;
    }
    private static EntityData entityData;
    public static EntityData getEntityData() {
        return entityData;
    }
    private void start() {
        plugin = this;
        configuration = getConfig();
        message = new Message(getLogger());
        entityData = new EntityData(this);
        reload();
        commands();
        events();
        getMessage().sendLog(Level.INFO, "Enabled " + getName() + " " + getDescription().getVersion());
    }
    private void stop() {
        getServer().getScheduler().cancelTasks(this);
        getMessage().sendLog(Level.INFO, "Disabled " + getName() + " " + getDescription().getVersion());
    }
    private void commands() {
        getCommand("prevent").setExecutor(new MainCommand());
    }
    private void events() {
        new BlockBurn(this);
        new BlockIgnite(this);
        new BlockRedstone(this);
        new BlockSpread(this);
        new CreatureSpawn(this);
        new EntityBlockForm(this);
        new EntityChangeBlock(this);
        new EntityExplode(this);
        new EntityTarget(this);
        new PlayerMove(this);
        new ProjectileHit(this);
        new ProjectileLaunch(this);
        new Trample(this);
    }
    @Override
    public void onEnable() {
        start();
    }
    @Override
    public void onDisable() {
        stop();
    }
    public void reload() {
        File file = new File(getDataFolder(), "config.yml");
        if (file.exists()) {
            getMessage().sendLog(Level.INFO, "loading config.yml");
            try {
                getConfig().load(file);
            } catch (IOException | InvalidConfigurationException e) {
                getMessage().sendLog(Level.WARNING, e.getMessage());
            }
            saveConfig();
            getMessage().sendLog(Level.INFO, "successfully loaded config.yml");
        } else {
            getConfig().options().copyDefaults(true);
            saveConfig();
            getMessage().sendLog(Level.INFO, "created config.yml");
        }
    }
}