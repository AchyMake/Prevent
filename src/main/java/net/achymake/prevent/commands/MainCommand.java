package net.achymake.prevent.commands;

import net.achymake.prevent.Prevent;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class MainCommand implements CommandExecutor, TabCompleter {
    private Prevent getPlugin() {
        return Prevent.getInstance();
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (args.length == 1) {
                Player player = (Player) sender;
                if (args[0].equalsIgnoreCase("reload")) {
                    getPlugin().reload();
                    Prevent.send(player, "&6Prevent reloaded");
                }
            }
        }
        if (sender instanceof ConsoleCommandSender) {
            if (args.length == 1) {
                ConsoleCommandSender commandSender = (ConsoleCommandSender) sender;
                if (args[0].equalsIgnoreCase("reload")) {
                    getPlugin().reload();
                    Prevent.send(commandSender, "&6Prevent reloaded");
                }
            }
        }
        return true;
    }
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> commands = new ArrayList<>();
        if (sender instanceof Player) {
            if (args.length == 1) {
                commands.add("reload");
            }
        }
        return commands;
    }
}