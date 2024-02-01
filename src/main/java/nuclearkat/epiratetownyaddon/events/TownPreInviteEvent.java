package nuclearkat.epiratetownyaddon.events;

import com.palmergames.bukkit.towny.event.town.TownPreInvitePlayerEvent;
import nuclearkat.epiratetownyaddon.EpirateTownyAddon;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.logging.Level;

public class TownPreInviteEvent implements Listener {

    private final EpirateTownyAddon epirateTownyAddon;

    public TownPreInviteEvent(EpirateTownyAddon epirateTownyAddon) {
        this.epirateTownyAddon = epirateTownyAddon;
    }

    @EventHandler
    public void onInviteSent(TownPreInvitePlayerEvent event) {
        Player invitedPlayer = event.getInvitedResident().getPlayer();
        Player inviter = (Player) event.getInvite().getDirectSender();

        System.out.println("Town Pre Invite Player Event Fired Successfully!");

        if (invitedPlayer != null && !epirateTownyAddon.isCooldownExpired(invitedPlayer)) {
            long remainingCooldownHours = epirateTownyAddon.getRemainingCooldownHours(invitedPlayer);
            String cooldownMessage = ChatColor.translateAlternateColorCodes('&', epirateTownyAddon.inviteCooldownMessage
                    .replace("%player%", invitedPlayer.getName())
                    .replace("%hours%", String.valueOf(remainingCooldownHours)));

            if (inviter != null) {
                try {
                    inviter.sendMessage(ChatColor.translateAlternateColorCodes('&', cooldownMessage));
                    event.getInvite().decline(true);
                } catch (Exception ex) {
                    Bukkit.getLogger().log(Level.SEVERE, "An error occurred while declining invite for inviter: " + inviter.getName(), ex);
                }
            } else {
                Bukkit.getLogger().log(Level.FINE, "Inviter = null cannot process function!");
            }
        } else if (invitedPlayer != null && epirateTownyAddon.isCooldownExpired(invitedPlayer)) {
            try {
                event.getInvite().decline(false);
            } catch (Exception ex) {
                Bukkit.getLogger().log(Level.SEVERE, "An error occurred while declining invite for invited player: " + invitedPlayer.getName(), ex);
            }
        } else {
            Bukkit.getLogger().log(Level.FINE, "TownPreInviteEvent Failed: either ln 26 / ln 36");
        }
    }
}
