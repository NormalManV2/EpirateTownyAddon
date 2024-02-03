package nuclearkat.epiratetownyaddon.commands;

import com.palmergames.bukkit.towny.tasks.CooldownTimerTask;
import nuclearkat.epiratetownyaddon.EpirateTownyAddon;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlayerCommand implements CommandExecutor {

    private final EpirateTownyAddon epirateTownyAddon;

    public PlayerCommand(EpirateTownyAddon epirateTownyAddon){
        this.epirateTownyAddon = epirateTownyAddon;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String sring, String[] args) {
        Player player = (Player) sender;

        if (player == null) return false;
        if (!player.hasPermission("towny.coodlowns.check")) return false;

        if (CooldownTimerTask.hasCooldown(player.getName(), "TownHop Cooldown")){
            player.sendMessage(epirateTownyAddon.getRemainingCooldownHours(player));
        } else {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&l&c " + epirateTownyAddon.getRemainingCooldownHours(player)));
        }
        return true;
    }
}
