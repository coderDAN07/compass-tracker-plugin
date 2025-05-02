package me.coderDAN07.commands;

import me.coderDAN07.CompassFollowsPlayer;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

public class TrackmeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, String @NotNull [] args) {

        CompassFollowsPlayer plugin = CompassFollowsPlayer.getPlugin(CompassFollowsPlayer.class);

        if (commandSender instanceof Player player) {

            plugin.getPlayerDimension().put(player.getUniqueId(), player.getLocation());

            Bukkit.broadcast(Component.text("Now tracking " + player.getName()));

            new BukkitRunnable() {

                @Override
                public void run() {

                    plugin.setTrackPlayer(player);

                    if (!player.isOnline()) {

                        plugin.getPlayerDimension().clear();
                        plugin.setTrackPlayer(null);
                        plugin.getPlayerList().put(player.getUniqueId(), true);

                        this.cancel();

                    }

                }

            }.runTaskTimer(plugin, 0, 5L);

        }

        return true;

    }

}
