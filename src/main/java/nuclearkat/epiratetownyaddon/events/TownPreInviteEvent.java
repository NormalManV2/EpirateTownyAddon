package nuclearkat.epiratetownyaddon.events;

import com.palmergames.bukkit.towny.event.town.TownPreInvitePlayerEvent;
import com.palmergames.bukkit.towny.tasks.CooldownTimerTask;
import nuclearkat.epiratetownyaddon.EpirateTownyAddon;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.logging.Level;

public class TownPreInviteEvent implements Listener {

    private final EpirateTownyAddon epirateTownyAddon;
    // Instance of the main class.
    public TownPreInviteEvent(EpirateTownyAddon epirateTownyAddon) {
        this.epirateTownyAddon = epirateTownyAddon;
    }
    // Event Handler method to handle invites to players on cooldowns.
    @EventHandler
    public void onInviteSent(TownPreInvitePlayerEvent event) {

        // Get our player objects.
        Player invitedPlayer = event.getInvitedResident().getPlayer();
        Player inviter = (Player) event.getInvite().getDirectSender();

        // If the invited player is not null and currently on cooldown :
        if (invitedPlayer != null && CooldownTimerTask.hasCooldown(invitedPlayer.getName(), "TownHop Cooldown")) {
            // Get the appropriate message(s) related to this function.
            long remainingCooldownHours = epirateTownyAddon.getRemainingCooldownHours(invitedPlayer);
            String inviteMessage = ChatColor.translateAlternateColorCodes('&', epirateTownyAddon.inviteCooldownMessage
                    .replace("%player%", invitedPlayer.getName())
                    .replace("%hours%", String.valueOf(remainingCooldownHours)));
            // Never assume.
            if (inviter != null) {
                try {
                    // We cancel the event, and send the message(s) to the inviter.
                    event.setCancelled(true);
                    event.setCancelMessage(ChatColor.translateAlternateColorCodes('&', inviteMessage));
                // Catch any exceptions in the function.
                } catch (Exception ex) {
                    Bukkit.getLogger().log(Level.SEVERE, "An error occurred while declining invite from inviter : " + inviter.getName(), ex);
                }
            // Never assume.
            } else {
                Bukkit.getLogger().log(Level.FINE, "Inviter = null cannot process function!");
            }
        // If invited player is not null and not on cooldown :
        } else if (invitedPlayer != null && epirateTownyAddon.isCooldownExpired(invitedPlayer)) {
            try {
                // Simply do not cancel the event.
                event.setCancelled(false);
            // Catch any exception in the function.
            } catch (Exception ex) {
                Bukkit.getLogger().log(Level.SEVERE, "An error occurred while processing invite for invited player: " + invitedPlayer.getName(), ex);
            }
        // Inform of a mishap with the function.
        } else {
            Bukkit.getLogger().log(Level.FINE, "TownPreInviteEvent Failed: either ln 32 / ln 53");
        }
    }
}
