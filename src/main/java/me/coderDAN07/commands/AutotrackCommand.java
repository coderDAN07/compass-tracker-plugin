package me.coderDAN07.commands;

import me.coderDAN07.CompassFollowsPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class AutotrackCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {

        if (commandSender instanceof Player player) {

            CompassFollowsPlayer plugin = CompassFollowsPlayer.getPlugin(CompassFollowsPlayer.class);

            UUID playerUUID = player.getUniqueId();

            Boolean currentValue = plugin.getPlayerList().getOrDefault(playerUUID, false);

            if (args.length > 0) {
                if (args[0].equalsIgnoreCase("enable")) {
                    plugin.getPlayerList().put(playerUUID, true);
                    player.sendMessage("Automatic tracking enabled");
                } else if (args[0].equalsIgnoreCase("disable")) {
                    plugin.getPlayerList().put(playerUUID, false);
                    player.sendMessage("Automatic tracking disabled");
                }
            } else {
                plugin.getPlayerList().put(playerUUID, !currentValue);
                player.sendMessage("Automatic tracking " + (!currentValue ? "enabled" : "disabled"));
            }

        }

        return true;

    }

}
