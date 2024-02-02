package nuclearkat.epiratetownyaddon.commands;

import com.palmergames.bukkit.towny.tasks.CooldownTimerTask;
import nuclearkat.epiratetownyaddon.EpirateTownyAddon;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AdminCommand implements CommandExecutor {

    private final EpirateTownyAddon epirateTownyAddon;
    public AdminCommand(EpirateTownyAddon epirateTownyAddon){
        this.epirateTownyAddon = epirateTownyAddon;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String string, String[] args) {
        Player player = (Player) sender;

        if (player == null) return false;
        if (!player.hasPermission("towny.cooldowns.admin")) return false;

        String subcommand = args[0];

        switch (subcommand){

            case "remove":
                Player targetPlayer = Bukkit.getPlayer(args[1]);

                if (targetPlayer == null) break;
                if (!CooldownTimerTask.hasCooldown(targetPlayer.getName(), "TownHop Cooldown")) break;

                if (CooldownTimerTask.hasCooldown(targetPlayer.getName(), "TownHop Cooldown")){
                    CooldownTimerTask.getCooldowns().remove(targetPlayer.getName(), "TownHop Cooldown");
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&l&c " + targetPlayer.getName() + "&r&f have had their cooldown &l&c removed&r!"));
                    break;
                }
                break;
            case "start":
                targetPlayer = Bukkit.getPlayer(args[1]);

                if (targetPlayer == null) break;
                if (CooldownTimerTask.hasCooldown(targetPlayer.getName(), "TownHop Cooldown")){
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&l&c " + targetPlayer.getName() + "&r is already on&l&c cooldown!"));
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', epirateTownyAddon.getRemainingCooldownHours(targetPlayer)));
                    break;
                }
                if (!CooldownTimerTask.hasCooldown(targetPlayer.getName(), "TownHop Cooldown")){
                    int cooldownTime = Integer.parseInt(args[2]);
                    CooldownTimerTask.addCooldownTimer(targetPlayer.getName(), "TownHop Cooldown", cooldownTime);
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f Started cooldown on &l&c" + targetPlayer.getName() + "&r for &c&l" + cooldownTime + "&r seconds!"));
                    break;
                }
                break;
            default:
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f Usage : &l /cooldown &c remove &f | &a start &f {Player Name} (duration, in the case of starting a cooldown)"));
                break;
        }
        return true;
    }
}
