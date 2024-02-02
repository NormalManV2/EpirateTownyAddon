package nuclearkat.epiratetownyaddon;

import com.palmergames.bukkit.towny.tasks.CooldownTimerTask;
import nuclearkat.epiratetownyaddon.commands.AdminCommand;
import nuclearkat.epiratetownyaddon.commands.PlayerCommand;
import nuclearkat.epiratetownyaddon.events.TownJoinEvent;
import nuclearkat.epiratetownyaddon.events.TownLeaveEvent;
import nuclearkat.epiratetownyaddon.events.TownPreInviteEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

public final class EpirateTownyAddon extends JavaPlugin implements Listener {


    //  Config variables.
    private int cooldownDurationHours;
    public String onCooldownMessage;
    public String remainingTimeMessage;
    public String inviteCooldownMessage;

    // Default cooldown duration.
    private static final int DEFAULT_COOLDOWN_DURATION = (int) TimeUnit.HOURS.toSeconds(24);
    @Override
    public void onEnable() {
        registerEvents();
        registerCommands();
        logAddonInfo();
        loadConfig();
    }
    private void registerCommands(){
        getCommand("cda").setExecutor(new AdminCommand(this));
        getCommand("cooldown").setExecutor(new PlayerCommand(this));
    }
    private void registerEvents() {
        getServer().getPluginManager().registerEvents(new TownJoinEvent(this), this);
        getServer().getPluginManager().registerEvents(new TownPreInviteEvent(this), this);
        getServer().getPluginManager().registerEvents(new TownLeaveEvent(this), this);
    }
    private void logAddonInfo() {
        Bukkit.getLogger().log(Level.CONFIG, "Epirate Towny addon created by NormalMan_V2 { Contact on discord for support : normalmanv2 } ");
    }

    public void setCooldown(Player player) {
        CooldownTimerTask.addCooldownTimer(player.getName(), "TownHop Cooldown", cooldownDurationHours);
    }
    private String formatRemainingTime(long seconds) {
        long hours = TimeUnit.SECONDS.toHours(seconds);
        long minutes = TimeUnit.SECONDS.toMinutes(seconds) % 60;
        long remainingSeconds = seconds % 60;

        return String.format("%02d:%02d:%02d", hours, minutes, remainingSeconds);
    }

    public String getRemainingCooldownHours(Player player) {
        if (CooldownTimerTask.hasCooldown(player.getName(), "TownHop Cooldown")) {
            long remainingCooldownSeconds = CooldownTimerTask.getCooldownRemaining(player.getName(), "TownHop Cooldown");
            return formatRemainingTime(remainingCooldownSeconds);
        }
        return "00:00:00";
    }
    private void loadConfig() {
        FileConfiguration config = getConfig();
        config.options().copyDefaults(true);
        saveConfig();

        cooldownDurationHours = config.getInt("cooldowns.duration", DEFAULT_COOLDOWN_DURATION);
        onCooldownMessage = ChatColor.translateAlternateColorCodes('&', config.getString("cooldowns.messages.onCooldown", "&cYou are on cooldown. Cannot join or leave another town."));
        remainingTimeMessage = ChatColor.translateAlternateColorCodes('&', config.getString("cooldowns.messages.remainingTime", "&eRemaining cooldown: %hours% hours."));
        inviteCooldownMessage = ChatColor.translateAlternateColorCodes('&', config.getString("cooldowns.messages.inviteCooldown", "&fCannot invite &c&l%player%&f as they are on cooldown. Remaining cooldown: &c&l%hours% &fhours."));
    }
    @Override
    public void onDisable() {
        saveConfig();
    }
}
