package nuclearkat.epiratetownyaddon;

import com.palmergames.bukkit.towny.event.TownPreAddResidentEvent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.concurrent.TimeUnit;

public class TownJoinEvent implements Listener {

    private EpirateTownyAddon epirateTownyAddon;

    TownJoinEvent(EpirateTownyAddon epirateTownyAddon){
        this.epirateTownyAddon = epirateTownyAddon;
    }

    @EventHandler
    public void onTownJoin(TownPreAddResidentEvent e) {
        Player player = e.getResident().getPlayer();
        if (player != null && !isCooldownExpired(player)) {
            long remainingCooldownHours = getRemainingCooldownHours(player);
            String remainingTimeMsg = ChatColor.translateAlternateColorCodes('&', epirateTownyAddon.remainingTimeMessage.replace("%hours%", String.valueOf(remainingCooldownHours)));

            e.setCancelMessage(ChatColor.translateAlternateColorCodes('&', epirateTownyAddon.onCooldownMessage + remainingTimeMsg));
            e.setCancelled(true);

        } else {
            setCooldown(player);
        }
    }
    boolean isCooldownExpired(Player player) {
        if (epirateTownyAddon.cooldowns.containsKey(player.getUniqueId())) {
            long cooldownEnd = epirateTownyAddon.cooldowns.get(player.getUniqueId()) + epirateTownyAddon.cooldownDurationMillis;
            return System.currentTimeMillis() > cooldownEnd;
        }
        return true;
    }

    void setCooldown(Player player) {
        epirateTownyAddon.cooldowns.put(player.getUniqueId(), System.currentTimeMillis());
    }

    long getRemainingCooldownHours(Player player) {
        if (epirateTownyAddon.cooldowns.containsKey(player.getUniqueId())) {
            long cooldownEnd = epirateTownyAddon.cooldowns.get(player.getUniqueId()) + epirateTownyAddon.cooldownDurationMillis;
            long remainingMillis = cooldownEnd - System.currentTimeMillis();
            return TimeUnit.MILLISECONDS.toHours(remainingMillis);
        }
        return 0;
    }

}
