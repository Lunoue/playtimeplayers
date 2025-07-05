package me.anya.playtimeplayers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Logger;

public class PlaytimePlayers extends JavaPlugin {
    public static PlaytimePlayers instance;
    private final HashMap<UUID, Long> sessionStart = new HashMap<>();

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        new PlaytimePlaceholder().register();

        for (Player player : Bukkit.getOnlinePlayers()) {
            sessionStart.put(player.getUniqueId(), System.currentTimeMillis());
        }

        getServer().getPluginManager().registerEvents(new SessionListener(this, sessionStart), this);

        // Автосохранение
        new BukkitRunnable() {
            @Override
            public void run() {
                savePlaytimes();
            }
        }.runTaskTimerAsynchronously(this, 20 * 60, 20 * 60); // каждые 60 секунд
    }

    @Override
    public void onDisable() {
        savePlaytimes();
    }

    public void savePlaytimes() {
        for (UUID uuid : sessionStart.keySet()) {
            long sessionMinutes = (System.currentTimeMillis() - sessionStart.get(uuid)) / 60000;
            long totalMinutes = getConfig().getLong("players." + uuid, 0) + sessionMinutes;
            getConfig().set("players." + uuid, totalMinutes);
        }
        saveConfig();
    }

    public long getPlaytime(UUID uuid) {
        long stored = getConfig().getLong("players." + uuid, 0);
        long current = sessionStart.containsKey(uuid) ? (System.currentTimeMillis() - sessionStart.get(uuid)) / 60000 : 0;
        return stored + current;
    }
}
