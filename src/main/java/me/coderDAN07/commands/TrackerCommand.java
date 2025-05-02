package me.coderDAN07.commands;

import me.coderDAN07.CompassFollowsPlayer;
import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CompassMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TrackerCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {

        if (commandSender instanceof Player player) {

            CompassFollowsPlayer plugin = CompassFollowsPlayer.getPlugin(CompassFollowsPlayer.class);

            NamespacedKey key = new NamespacedKey(CompassFollowsPlayer.getPlugin(CompassFollowsPlayer.class), "tracker");

            player.getInventory().remove(Material.COMPASS);

            ItemStack compass = new ItemStack(Material.COMPASS);
            CompassMeta meta = (CompassMeta) compass.getItemMeta();

            meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "true");
            meta.displayName(Component.text("Tracker"));
            meta.lore(List.of(Component.text("Right click while holding to update location, or use /autotrack to toggle automatic updates")));

            //noinspection deprecation
            player.sendMessage(ChatColor.YELLOW + "Right click while holding to update location, or use /autotrack to toggle automatic updates");

            if (plugin.getTrackPlayer() != null) {
                Location lodestone;
                if (player.getWorld().getEnvironment() == plugin.getTrackPlayer().getWorld().getEnvironment()) {
                    lodestone = plugin.getTrackPlayer().getLocation();
                } else {
                    lodestone = plugin.getPlayerDimension().get(plugin.getTrackPlayer().getUniqueId());
                }

                meta.setLodestone(lodestone);
            } else {
                player.sendMessage("No player is currently being tracked");
            }
            meta.setLodestoneTracked(false);

            compass.setItemMeta(meta);

            player.getInventory().addItem(compass);

        }

        return true;

    }

}
