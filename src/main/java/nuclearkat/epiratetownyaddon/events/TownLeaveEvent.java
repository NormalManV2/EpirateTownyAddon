package nuclearkat.epiratetownyaddon.events;

import com.palmergames.bukkit.towny.event.CancellableTownyEvent;
import nuclearkat.epiratetownyaddon.EpirateTownyAddon;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.logging.Level;

public class TownLeaveEvent implements Listener {

    private final EpirateTownyAddon epirateTownyAddon;

    public TownLeaveEvent(EpirateTownyAddon epirateTownyAddon) {
        this.epirateTownyAddon = epirateTownyAddon;
    }

    @EventHandler
    public void onTownLeave(com.palmergames.bukkit.towny.event.town.TownLeaveEvent event) {
        Player player = (Player) event.getResident();
        System.out.println("Town Leave Event Fired Successfully!");

        handleTownAction(player, event);
    }

    public void handleTownAction(Player player, CancellableTownyEvent event) {
        if (player != null && !epirateTownyAddon.isCooldownExpired(player)) {
            long remainingCooldownHours = epirateTownyAddon.getRemainingCooldownHours(player);
            String remainingTimeMsg = ChatColor.translateAlternateColorCodes('&', epirateTownyAddon.remainingTimeMessage.replace("%hours%", String.valueOf(remainingCooldownHours)));

            player.sendMessage(ChatColor.RED + epirateTownyAddon.onCooldownMessage);
            player.sendMessage(remainingTimeMsg);

            try {
                event.setCancelled(true);
            } catch (Exception ex) {
                Bukkit.getLogger().log(Level.SEVERE, "An error occurred while cancelling town leave event for player: " + player.getName(), ex);
            }

        } else if (player != null && epirateTownyAddon.isCooldownExpired(player)) {
            try {
                event.setCancelled(false);
                epirateTownyAddon.setCooldown(player);
            } catch (Exception ex) {
                Bukkit.getLogger().log(Level.SEVERE, "An error occurred while allowing town leave event for player: " + player.getName(), ex);
            }
        } else {
            Bukkit.getLogger().log(Level.FINE, "TownLeaveEvent Failed: either ln 30 / ln 43");
        }
    }
}
