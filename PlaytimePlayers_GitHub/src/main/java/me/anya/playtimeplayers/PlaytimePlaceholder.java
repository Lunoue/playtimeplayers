package me.anya.playtimeplayers;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

public class PlaytimePlaceholder extends PlaceholderExpansion {
    @Override
    public @NotNull String getIdentifier() {
        return "playtimeplayers";
    }

    @Override
    public @NotNull String getAuthor() {
        return "Anya";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }

    @Override
    public String onPlaceholderRequest(Player player, @NotNull String identifier) {
        if (player == null) return "";

        if (identifier.equals("playtime")) {
            long minutes = PlaytimePlayers.instance.getPlaytime(player.getUniqueId());

            long weeks = minutes / 10080;
            minutes %= 10080;

            long days = minutes / 1440;
            minutes %= 1440;

            long hours = minutes / 60;
            minutes %= 60;

            StringBuilder sb = new StringBuilder();
            if (weeks > 0) sb.append(weeks).append(" недель ");
            if (days > 0) sb.append(days).append(" дней ");
            if (hours > 0) sb.append(hours).append(" часов ");
            if (minutes > 0 || sb.length() == 0) sb.append(minutes).append(" минут");

            return sb.toString().trim();
        }

        return null;
    }
}
