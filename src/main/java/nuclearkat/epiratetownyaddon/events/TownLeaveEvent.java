package nuclearkat.epiratetownyaddon.events;

import com.palmergames.bukkit.towny.event.CancellableTownyEvent;
import com.palmergames.bukkit.towny.tasks.CooldownTimerTask;
import nuclearkat.epiratetownyaddon.EpirateTownyAddon;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class TownLeaveEvent implements Listener {

    private final EpirateTownyAddon epirateTownyAddon;
    public TownLeaveEvent(EpirateTownyAddon epirateTownyAddon) {
        this.epirateTownyAddon = epirateTownyAddon;
    }

    @EventHandler
    public void onTownLeave(com.palmergames.bukkit.towny.event.town.TownLeaveEvent event) {
        Player player = event.getResident().getPlayer();
        handleTownAction(player, event);
    }

    public void handleTownAction(Player player, CancellableTownyEvent event) {

        if (player == null) {
            return;
        }

        if (CooldownTimerTask.hasCooldown(player.getName(), "TownHop Cooldown")) {
            String remainingCooldownHours = epirateTownyAddon.getRemainingCooldownHours(player);
            String remainingTimeMsg = ChatColor.translateAlternateColorCodes('&', epirateTownyAddon.remainingTimeMessage.replace("%hours%", String.valueOf(remainingCooldownHours)));

                event.setCancelled(true);
                event.setCancelMessage(ChatColor.translateAlternateColorCodes('&', epirateTownyAddon.onCooldownMessage + " \n " + remainingTimeMsg));
        } else {
            epirateTownyAddon.setCooldown(player);
        }
    }
}
