package nuclearkat.epiratetownyaddon.events;

import com.palmergames.bukkit.towny.event.CancellableTownyEvent;
import com.palmergames.bukkit.towny.tasks.CooldownTimerTask;
import nuclearkat.epiratetownyaddon.EpirateTownyAddon;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.logging.Level;

public class TownLeaveEvent implements Listener {

    private final EpirateTownyAddon epirateTownyAddon;
    // Instance of the main class.
    public TownLeaveEvent(EpirateTownyAddon epirateTownyAddon) {
        this.epirateTownyAddon = epirateTownyAddon;
    }

    @EventHandler
    public void onTownLeave(com.palmergames.bukkit.towny.event.town.TownLeaveEvent event) {
        // Get our player object and initialize the town action handler.
        Player player = (Player) event.getResident();
        handleTownAction(player, event);
    }

    // Helper method to handle the leave event.
    public void handleTownAction(Player player, CancellableTownyEvent event) {
        // If the player is not null and is currently on cooldown :
        if (player != null && CooldownTimerTask.hasCooldown(player.getName(), "TownHop Cooldown")) {

            // Get the appropriate messages related to this function.
            long remainingCooldownHours = epirateTownyAddon.getRemainingCooldownHours(player);
            String remainingTimeMsg = ChatColor.translateAlternateColorCodes('&', epirateTownyAddon.remainingTimeMessage.replace("%hours%", String.valueOf(remainingCooldownHours)));

            try {
                // We cancel the event, and send the message(s) to the player involved in the event.
                event.setCancelled(true);
                event.setCancelMessage(ChatColor.translateAlternateColorCodes('&', epirateTownyAddon.onCooldownMessage + " \n " + remainingTimeMsg));

                // Catch any exceptions involving the function.
            } catch (Exception ex) {
                Bukkit.getLogger().log(Level.SEVERE, "An error occurred while cancelling town leave event for player: " + player.getName(), ex);
            }
            // If the player is not null and currently not on cooldown :
        } else if (player != null && epirateTownyAddon.isCooldownExpired(player)) {
            try {
                // We do not cancel the event, and rather put a cooldown on the player.
                event.setCancelled(false);
                epirateTownyAddon.setCooldown(player);
            // Catch any exceptions involcing the function.
            } catch (Exception ex) {
                Bukkit.getLogger().log(Level.SEVERE, "An error occurred while allowing town leave event for player: " + player.getName(), ex);
            }
        // Inform of a mishap with the function.
        } else {
            Bukkit.getLogger().log(Level.FINE, "TownLeaveEvent Failed: either ln 33 / ln 49");
        }
    }
}
