package me.coderDAN07.listeners;

import me.coderDAN07.CompassFollowsPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;

import java.util.UUID;

public class DimensionListener implements Listener {

    @EventHandler
    public void onEnterPortal(PlayerPortalEvent e) {

        CompassFollowsPlayer plugin = CompassFollowsPlayer.getPlugin(CompassFollowsPlayer.class);

        if (plugin.getTrackPlayer() != null) {

            Player player = e.getPlayer();
            UUID playerUUID = player.getUniqueId();

            if (plugin.getTrackPlayer().getUniqueId() == playerUUID) {
                plugin.getPlayerDimension().replace(playerUUID, e.getFrom());
            }

        }

    }

}
