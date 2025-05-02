package me.coderDAN07.listeners;

import me.coderDAN07.CompassFollowsPlayer;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CompassMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerJoinListener implements Listener {

    CompassFollowsPlayer plugin = CompassFollowsPlayer.getPlugin(CompassFollowsPlayer.class);

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {

        Player player = e.getPlayer();

        plugin.getPlayerList().put(player.getUniqueId(), true);

        new BukkitRunnable() {

            @Override
            public void run() {

                NamespacedKey key = new NamespacedKey(CompassFollowsPlayer.getPlugin(CompassFollowsPlayer.class), "tracker");

                if (plugin.getPlayerList().get(player.getUniqueId())) {
                    for (ItemStack item : player.getInventory().getContents()) {
                        if (item != null && item.getType() == Material.COMPASS && item.getPersistentDataContainer().has(key, PersistentDataType.STRING)) {

                            if (plugin.getTrackPlayer() == null) {
                                return;
                            }

                            CompassMeta meta = (CompassMeta) item.getItemMeta();

                            Location lodestone;

                            if (player.getWorld().getEnvironment() == plugin.getTrackPlayer().getWorld().getEnvironment()) {
                                lodestone = plugin.getTrackPlayer().getLocation().clone();
                            } else {
                                lodestone = plugin.getPlayerDimension().get(plugin.getTrackPlayer().getUniqueId()).clone();
                            }

                            if (offsetToggle) {
                                lodestone.add(0, 1, 0); // shift one block east
                            }
                            offsetToggle = !offsetToggle;

                            meta.setLodestone(lodestone);
                            meta.setLodestoneTracked(false);

                            item.setItemMeta(meta);

                        }
                    }

                    if (!player.isOnline()) {
                        this.cancel();
                    }
                }

            }

            private Boolean offsetToggle = false;

        }.runTaskTimer(plugin, 0, 20L);

    }

/*
    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e) {

        Player player = e.getPlayer();

        plugin.getPlayerList().remove(player.getUniqueId());

    }
*/

}
