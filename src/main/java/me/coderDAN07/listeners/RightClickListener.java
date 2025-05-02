package me.coderDAN07.listeners;

import me.coderDAN07.CompassFollowsPlayer;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CompassMeta;
import org.bukkit.persistence.PersistentDataType;

public class RightClickListener implements Listener {

    @EventHandler
    public void onRightClick(PlayerInteractEvent e) {

        CompassFollowsPlayer plugin = CompassFollowsPlayer.getPlugin(CompassFollowsPlayer.class);

        Player player = e.getPlayer();

        NamespacedKey key = new NamespacedKey(CompassFollowsPlayer.getPlugin(CompassFollowsPlayer.class), "tracker");

        if (e.getItem() instanceof ItemStack item && e.getItem().getType() == Material.COMPASS && e.getItem().getPersistentDataContainer().has(key, PersistentDataType.STRING)) {

            if (plugin.getTrackPlayer() == null) {
                player.sendMessage("No player is currently being tracked");
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

            player.getInventory().setItemInMainHand(item);

        }

    }

    private Boolean offsetToggle = false;

}
