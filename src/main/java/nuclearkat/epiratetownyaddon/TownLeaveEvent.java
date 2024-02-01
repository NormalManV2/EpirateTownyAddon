package nuclearkat.epiratetownyaddon;

import com.palmergames.bukkit.towny.event.CancellableTownyEvent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import java.util.concurrent.TimeUnit;

public class TownLeaveEvent implements Listener {

    private final EpirateTownyAddon epirateTownyAddon;
    TownLeaveEvent(EpirateTownyAddon epirateTownyAddon){
        this.epirateTownyAddon = epirateTownyAddon;
    }

    @EventHandler
    public void onTownLeave(com.palmergames.bukkit.towny.event.town.TownLeaveEvent e) {
        Player player = (Player) e.getResident();
        handleTownAction(player, e);
    }

    public void handleTownAction(Player player, CancellableTownyEvent e) {
        if (player != null && !isCooldownExpired(player)) {
            long remainingCooldownHours = getRemainingCooldownHours(player);
            String remainingTimeMsg = ChatColor.translateAlternateColorCodes('&', epirateTownyAddon.remainingTimeMessage.replace("%hours%", String.valueOf(remainingCooldownHours)));

            player.sendMessage(ChatColor.RED + epirateTownyAddon.onCooldownMessage);
            player.sendMessage(remainingTimeMsg);

            e.setCancelled(true);
        } else {
            if (player != null) {
                setCooldown(player);
            }
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
