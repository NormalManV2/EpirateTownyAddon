package nuclearkat.epiratetownyaddon.events;

import com.palmergames.bukkit.towny.event.TownInvitePlayerEvent;
import com.palmergames.bukkit.towny.event.town.TownPreInvitePlayerEvent;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import nuclearkat.epiratetownyaddon.EpirateTownyAddon;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.logging.Level;

public class TownPreInviteEvent implements Listener {

    private final EpirateTownyAddon epirateTownyAddon;

    public TownPreInviteEvent(EpirateTownyAddon epirateTownyAddon){
        this.epirateTownyAddon = epirateTownyAddon;
    }
    @EventHandler
    public void onInviteSent(TownInvitePlayerEvent e) {
        Player invitedPlayer = e.getInvite().getReceiver().getPlayer();
        Player inviter = (Player) e.getInvite().getDirectSender();

        if (invitedPlayer != null && !epirateTownyAddon.isCooldownExpired(invitedPlayer)) {
            long remainingCooldownHours = epirateTownyAddon.getRemainingCooldownHours(invitedPlayer);
            String cooldownMessage = ChatColor.translateAlternateColorCodes('&', epirateTownyAddon.inviteCooldownMessage
                    .replace("%player%", invitedPlayer.getName())
                    .replace("%hours%", String.valueOf(remainingCooldownHours)));

            if (inviter != null) {
                inviter.sendMessage(ChatColor.translateAlternateColorCodes('&', cooldownMessage));
                e.getInvite().decline(true);
            } else {
                Bukkit.getLogger().log(Level.WARNING, "Inviter = null cannot process function!");
            }
        } else if (invitedPlayer != null && epirateTownyAddon.isCooldownExpired(invitedPlayer)){
            e.getInvite().decline(false);
        } else {
            Bukkit.getLogger().log(Level.WARNING, "TownPreInviteEvent Failed! : either ln 26 / ln 36");
        }
    }
}
