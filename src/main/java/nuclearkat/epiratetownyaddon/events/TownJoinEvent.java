package nuclearkat.epiratetownyaddon.events;

import com.palmergames.bukkit.towny.event.TownPreAddResidentEvent;
import nuclearkat.epiratetownyaddon.EpirateTownyAddon;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.logging.Level;

public class TownJoinEvent implements Listener {

    private final EpirateTownyAddon epirateTownyAddon;

    public TownJoinEvent(EpirateTownyAddon epirateTownyAddon) {
        this.epirateTownyAddon = epirateTownyAddon;
    }

    @EventHandler
    public void onTownJoin(TownPreAddResidentEvent event) {
        Player player = event.getResident().getPlayer();
        System.out.println("Town Pre Add Resident Event Fired Successfully!");

        if (player != null && !epirateTownyAddon.isCooldownExpired(player)) {
            long remainingCooldownHours = epirateTownyAddon.getRemainingCooldownHours(player);
            String remainingTimeMsg = ChatColor.translateAlternateColorCodes('&', epirateTownyAddon.remainingTimeMessage.replace("%hours%", String.valueOf(remainingCooldownHours)));

            player.sendMessage(ChatColor.translateAlternateColorCodes('&', epirateTownyAddon.onCooldownMessage));
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', remainingTimeMsg));

            try {
                event.getResident().removeTown();
            } catch (Exception ex) {
                Bukkit.getLogger().log(Level.SEVERE, "An error occurred while removing town for player: " + player.getName(), ex);
            }

        } else if (player != null && epirateTownyAddon.isCooldownExpired(player)) {
            epirateTownyAddon.setCooldown(player);
        } else {
            Bukkit.getLogger().log(Level.FINE, "TownJoinEvent Failed: either ln 25 / ln 32");
        }
    }
}
