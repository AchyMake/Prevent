package net.achymake.prevent;

import net.achymake.prevent.commands.MainCommand;
import net.achymake.prevent.files.*;
import net.achymake.prevent.listeners.*;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Consumer;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;
import java.util.logging.Level;

public final class Prevent extends JavaPlugin {
    private static Prevent instance;
    public static Prevent getInstance() {
        return instance;
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
        instance = this;
        configuration = getConfig();
        message = new Message(getLogger());
        entityData = new EntityData(this);
        reload();
        commands();
        events();
        getMessage().sendLog(Level.INFO, "Enabled " + getName() + " " + getDescription().getVersion());
        sendUpdate();
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
        new NotifyUpdate(this);
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
            try {
                getConfig().load(file);
                getMessage().sendLog(Level.INFO, "loading config.yml");
            } catch (IOException | InvalidConfigurationException e) {
                getMessage().sendLog(Level.WARNING, e.getMessage());
            }
            saveConfig();
        } else {
            getConfig().options().copyDefaults(true);
            saveConfig();
            getMessage().sendLog(Level.INFO, "created config.yml");
        }
    }
    public void sendUpdate(Player player) {
        if (getConfig().getBoolean("notify-update.enable")) {
            checkLatest((latest) -> {
                if (!getDescription().getVersion().equals(latest)) {
                    getMessage().send(player,"&6" + getName() + " Update:&f " + latest);
                    getMessage().send(player,"&6Current Version: &f" + getDescription().getVersion());
                }
            });
        }
    }
    public void sendUpdate() {
        if (getConfig().getBoolean("notify-update.enable")) {
            checkLatest((latest) -> {
                getMessage().sendLog(Level.INFO, "Checking latest release");
                if (getDescription().getVersion().equals(latest)) {
                    getMessage().sendLog(Level.INFO, "You are using the latest version");
                } else {
                    getMessage().sendLog(Level.INFO, "New Update: " + latest);
                    getMessage().sendLog(Level.INFO, "Current Version: " + getDescription().getVersion());
                }
            });
        }
    }
    public void checkLatest(Consumer<String> consumer) {
        try {
            InputStream inputStream = (new URL("https://api.spigotmc.org/legacy/update.php?resource=" + 110441)).openStream();
            Scanner scanner = new Scanner(inputStream);
            if (scanner.hasNext()) {
                consumer.accept(scanner.next());
                scanner.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        } catch (IOException e) {
            getMessage().sendLog(Level.WARNING, e.getMessage());
        }
    }
}