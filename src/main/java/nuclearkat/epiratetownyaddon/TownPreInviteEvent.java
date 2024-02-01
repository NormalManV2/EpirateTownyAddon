package nuclearkat.epiratetownyaddon;

import com.palmergames.bukkit.towny.event.town.TownPreInvitePlayerEvent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.concurrent.TimeUnit;

public class TownPreInviteEvent implements Listener {

    private final EpirateTownyAddon epirateTownyAddon;

    TownPreInviteEvent(EpirateTownyAddon epirateTownyAddon){
        this.epirateTownyAddon = epirateTownyAddon;
    }
    @EventHandler
    public void onInviteSent(TownPreInvitePlayerEvent e) {
        Player invitedPlayer = e.getInvitedResident().getPlayer();
        Player inviter = (Player) e.getInvite().getDirectSender();

        if (invitedPlayer != null && !isCooldownExpired(invitedPlayer)) {
            long remainingCooldownHours = getRemainingCooldownHours(invitedPlayer);
            String cooldownMessage = ChatColor.translateAlternateColorCodes('&', epirateTownyAddon.inviteCooldownMessage
                    .replace("%player%", invitedPlayer.getName())
                    .replace("%hours%", String.valueOf(remainingCooldownHours)));

            if (inviter instanceof Player) {
                e.setCancelMessage(cooldownMessage);
                e.setCancelled(true);
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
    long getRemainingCooldownHours(Player player) {
        if (epirateTownyAddon.cooldowns.containsKey(player.getUniqueId())) {
            long cooldownEnd = epirateTownyAddon.cooldowns.get(player.getUniqueId()) + epirateTownyAddon.cooldownDurationMillis;
            long remainingMillis = cooldownEnd - System.currentTimeMillis();
            return TimeUnit.MILLISECONDS.toHours(remainingMillis);
        }
        return 0;
    }

}
