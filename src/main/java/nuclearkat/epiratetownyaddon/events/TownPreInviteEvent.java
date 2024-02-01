package nuclearkat.epiratetownyaddon.events;

import com.palmergames.bukkit.towny.event.town.TownPreInvitePlayerEvent;
import nuclearkat.epiratetownyaddon.EpirateTownyAddon;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

public class TownPreInviteEvent implements Listener {

    private final EpirateTownyAddon epirateTownyAddon;

    public TownPreInviteEvent(EpirateTownyAddon epirateTownyAddon){
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

            if (inviter != null) {
                e.setCancelMessage(cooldownMessage);
                e.setCancelled(true);
            } else {
                Bukkit.getLogger().log(Level.WARNING, "Inviter = null cannot process function!");
            }
        } else if (invitedPlayer != null && isCooldownExpired(invitedPlayer)){
            e.setCancelled(false);
        } else {
            Bukkit.getLogger().log(Level.WARNING, "TownPreInviteEvent Failed! : either ln 26 / ln 36");
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
