package co.insou.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class CommandManager {

    private final JavaPlugin plugin;

    public CommandManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void register(final CommandConsumer consumer) {
        plugin.getCommand(consumer.getName()).setExecutor(new CommandExecutor() {
            @Override
            public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
                if (consumer.isPlayerOnly() && !(sender instanceof Player)) {
                    return true;
                }
                consumer.onCommand(sender, label, args);
                return true;
            }
        });
    }

}
