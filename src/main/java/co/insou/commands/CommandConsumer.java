package co.insou.commands;

import org.bukkit.command.CommandSender;

public abstract class CommandConsumer {

    private final String name;
    private final boolean playerOnly;

    public CommandConsumer(String name, boolean playerOnly) {
        this.name = name;
        this.playerOnly = playerOnly;
    }

    public String getName() {
        return name;
    }

    public boolean isPlayerOnly() {
        return playerOnly;
    }

    public abstract void onCommand(CommandSender sender, String label, String[] args);

}
