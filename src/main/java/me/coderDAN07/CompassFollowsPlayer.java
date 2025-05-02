package me.coderDAN07;

import me.coderDAN07.commands.AutotrackCommand;
import me.coderDAN07.commands.TrackerCommand;
import me.coderDAN07.commands.TrackmeCommand;
import me.coderDAN07.listeners.DimensionListener;
import me.coderDAN07.listeners.PlayerJoinListener;
import me.coderDAN07.listeners.RightClickListener;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public final class CompassFollowsPlayer extends JavaPlugin {

    @Override
    public void onEnable() {

        getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
        getServer().getPluginManager().registerEvents(new RightClickListener(), this);
        getServer().getPluginManager().registerEvents(new DimensionListener(), this);

        Objects.requireNonNull(getCommand("tracker")).setExecutor(new TrackerCommand());
        Objects.requireNonNull(getCommand("trackme")).setExecutor(new TrackmeCommand());
        Objects.requireNonNull(getCommand("autotrack")).setExecutor(new AutotrackCommand());
        Objects.requireNonNull(getCommand("autotrack")).setTabCompleter(new TabCompleter() {
            @Override
            public @NotNull List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {
                return List.of("enable", "disable");
            }
        });

    }

    public Player trackPlayer;
    public Player getTrackPlayer() {
        return trackPlayer;
    }
    public void setTrackPlayer(Player trackPlayer) {
        this.trackPlayer = trackPlayer;
    }

    public final Map<UUID, Boolean> playerList = new HashMap<>();
    public Map<UUID, Boolean> getPlayerList() {
        return playerList;
    }

    public final Map<UUID, Location> playerDimension = new HashMap<>();
    public Map<UUID, Location> getPlayerDimension() {
        return playerDimension;
    }

}
