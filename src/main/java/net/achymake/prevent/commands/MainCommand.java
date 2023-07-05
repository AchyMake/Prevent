package net.achymake.prevent.commands;

import net.achymake.prevent.Prevent;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class MainCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("reload")) {
                    Prevent.reload();
                    Player player = (Player) sender;
                    Prevent.send(player, "&6Prevent reloaded");
                }
            }
        }
        if (sender instanceof ConsoleCommandSender) {
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("reload")) {
                    Prevent.reload();
                    ConsoleCommandSender commandSender = (ConsoleCommandSender) sender;
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