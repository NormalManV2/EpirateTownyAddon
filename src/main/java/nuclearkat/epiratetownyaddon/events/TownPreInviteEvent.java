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

    public TownPreInviteEvent(EpirateTownyAddon epirateTownyAddon) {
        this.epirateTownyAddon = epirateTownyAddon;
    }
    @EventHandler
    public void onInviteSent(TownPreInvitePlayerEvent event) {

        Player invitedPlayer = event.getInvitedResident().getPlayer();
        Player inviter = (Player) event.getInvite().getDirectSender();

        if (invitedPlayer == null || inviter == null) {
            Bukkit.getLogger().log(Level.WARNING, "Could not process invite as either inviter / invited player is null!");
            return;
        }
        if (CooldownTimerTask.hasCooldown(invitedPlayer.getName(), "TownHop Cooldown")) {
            long remainingCooldownHours = epirateTownyAddon.getRemainingCooldownHours(invitedPlayer);
            String inviteMessage = ChatColor.translateAlternateColorCodes('&', epirateTownyAddon.inviteCooldownMessage
                    .replace("%player%", invitedPlayer.getName())
                    .replace("%hours%", String.valueOf(remainingCooldownHours)));

                event.setCancelled(true);
                event.setCancelMessage(ChatColor.translateAlternateColorCodes('&', inviteMessage));

        }
    }
}