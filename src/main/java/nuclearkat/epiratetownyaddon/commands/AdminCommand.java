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

        if (args.length >=2) {
            Player targetPlayer = Bukkit.getPlayer(args[1]);
            String subcommand = args[0];
            switch (subcommand) {
                case "remove":
                    if (targetPlayer != null) {
                        removeCooldown(player, targetPlayer);
                    }
                    break;

                case "start":
                    if (args.length < 3) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f Usage : &l /cda &a start &f {Player Name} (duration)"));
                        break;
                    }
                    if (targetPlayer != null) {
                        int duration = Integer.parseInt(args[2]);
                        startCooldown(player, targetPlayer, duration);
                    }
                    break;
                default:
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f Usage : &l /cda &c remove &f | &a start &f {Player Name} (duration, in the case of starting a cooldown)"));
                    break;
            }
        }
        return true;
    }
    public void removeCooldown(Player player, Player targetPlayer){
        Object cooldowns = CooldownTimerTask.getCooldowns().remove(targetPlayer.getName());

        if (cooldowns == null){
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "Target &l&c" + targetPlayer.getName() + "&r does not currently have a cooldown!"));
        } else {
            CooldownTimerTask.getCooldowns().remove(targetPlayer.getName(), "TownHop Cooldown");
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&l&c " + targetPlayer.getName() + "&r&f has had their cooldown &l&c removed&r!"));
        }
    }
    public void startCooldown(Player player, Player targetPlayer, int duration){

        Object cooldowns = CooldownTimerTask.getCooldowns().remove(targetPlayer.getName());

        if (cooldowns == null){
            CooldownTimerTask.addCooldownTimer(targetPlayer.getName(), "TownHop Cooldown", duration);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f Started cooldown on &l&c" + targetPlayer.getName() + "&r for &c&l" + duration + "&r seconds!"));
        } else {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&l&c " + targetPlayer.getName() + "&r is already on&l&c cooldown!"));
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', epirateTownyAddon.getRemainingCooldownHours(targetPlayer)));
        }
    }

}
