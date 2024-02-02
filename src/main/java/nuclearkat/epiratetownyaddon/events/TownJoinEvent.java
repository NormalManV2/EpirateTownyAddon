package nuclearkat.epiratetownyaddon.events;

import com.palmergames.bukkit.towny.event.TownPreAddResidentEvent;
import com.palmergames.bukkit.towny.tasks.CooldownTimerTask;
import nuclearkat.epiratetownyaddon.EpirateTownyAddon;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.logging.Level;

public class TownJoinEvent implements Listener {

    private final EpirateTownyAddon epirateTownyAddon;
    // Instance of the main class.
    public TownJoinEvent(EpirateTownyAddon epirateTownyAddon) {
        this.epirateTownyAddon = epirateTownyAddon;
    }
    // Event Handler method to manage player joining towns.
    @EventHandler
    public void onTownJoin(TownPreAddResidentEvent event) {
        // Get our player object.
        Player player = event.getResident().getPlayer();
        // If the player is not null and currently on cooldown :
        if (player != null && CooldownTimerTask.hasCooldown(player.getName(), "TownHop Cooldown")) {
            // Get the appropriate message(s) related to this function.
            long remainingCooldownHours = epirateTownyAddon.getRemainingCooldownHours(player);
            String remainingTimeMsg = ChatColor.translateAlternateColorCodes('&', epirateTownyAddon.remainingTimeMessage.replace("%hours%", String.valueOf(remainingCooldownHours)));
            try {
                // We cancel the event, and send the message(s) to the player related in this function.
                event.setCancelled(true);
                event.setCancelMessage(ChatColor.translateAlternateColorCodes('&', epirateTownyAddon.onCooldownMessage + " \n " + remainingTimeMsg));
            // Catch any exceptions in this function.
            } catch (Exception ex) {
                Bukkit.getLogger().log(Level.SEVERE, "An error occurred while cancelling town join for player: " + player.getName(), ex);
            }
        // If the player is not null and not currently on cooldown :
        } else if (player != null && epirateTownyAddon.isCooldownExpired(player)) {
            // We do not cancel the event, and rather put a cooldown on the player.
            epirateTownyAddon.setCooldown(player);
            event.setCancelled(false);
        // Inform of a mishap in this function.
        } else {
            Bukkit.getLogger().log(Level.FINE, "TownJoinEvent Failed: either ln 27 / ln 40");
        }
    }
}
