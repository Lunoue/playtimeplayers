package me.anya.playtimeplayers;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.UUID;

public class SessionListener implements Listener {
    private final PlaytimePlayers plugin;
    private final HashMap<UUID, Long> sessionStart;

    public SessionListener(PlaytimePlayers plugin, HashMap<UUID, Long> sessionStart) {
        this.plugin = plugin;
        this.sessionStart = sessionStart;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        sessionStart.put(event.getPlayer().getUniqueId(), System.currentTimeMillis());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        plugin.savePlaytimes();
        sessionStart.remove(event.getPlayer().getUniqueId());
    }
}
