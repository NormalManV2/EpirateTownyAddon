package nuclearkat.epiratetownyaddon.events;

import com.palmergames.bukkit.towny.event.CancellableTownyEvent;
import nuclearkat.epiratetownyaddon.EpirateTownyAddon;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class TownLeaveEvent implements Listener {

    private final EpirateTownyAddon epirateTownyAddon;
    public TownLeaveEvent(EpirateTownyAddon epirateTownyAddon){
        this.epirateTownyAddon = epirateTownyAddon;
    }

    @EventHandler
    public void onTownLeave(com.palmergames.bukkit.towny.event.town.TownLeaveEvent e) {
        Player player = (Player) e.getResident();
        System.out.println("Town Leave Event Fired Successfully!");

        handleTownAction(player, e);
    }

    public void handleTownAction(Player player, CancellableTownyEvent e) {
        if (player != null && !epirateTownyAddon.isCooldownExpired(player)) {
            long remainingCooldownHours = epirateTownyAddon.getRemainingCooldownHours(player);
            String remainingTimeMsg = ChatColor.translateAlternateColorCodes('&', epirateTownyAddon.remainingTimeMessage.replace("%hours%", String.valueOf(remainingCooldownHours)));

            player.sendMessage(ChatColor.RED + epirateTownyAddon.onCooldownMessage);
            player.sendMessage(remainingTimeMsg);

            e.setCancelled(true);

        } else if (player != null && epirateTownyAddon.isCooldownExpired(player)) {
            e.setCancelled(false);
            epirateTownyAddon.setCooldown(player);
        }
    }
}
