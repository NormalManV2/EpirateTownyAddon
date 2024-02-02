package nuclearkat.epiratetownyaddon;

import com.palmergames.bukkit.towny.tasks.CooldownTimerTask;
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


    // Config variables.
    private int cooldownDurationHours;
    public String onCooldownMessage;
    public String remainingTimeMessage;
    public String inviteCooldownMessage;

    // Default cooldown duration.
    private static final int DEFAULT_COOLDOWN_DURATION = (int) TimeUnit.HOURS.toHours(24);

    // On enable we initialize our registerEvents method, our logAddonInfo method, and our loadConfig method.
    @Override
    public void onEnable() {
        registerEvents();
        logAddonInfo();
        loadConfig();
    }

    // Register our events.
    private void registerEvents() {
        getServer().getPluginManager().registerEvents(new TownJoinEvent(this), this);
        getServer().getPluginManager().registerEvents(new TownPreInviteEvent(this), this);
        getServer().getPluginManager().registerEvents(new TownLeaveEvent(this), this);
    }

    // Send console log message.
    private void logAddonInfo() {
        Bukkit.getLogger().log(Level.CONFIG, "Epirate Towny addon created by NormalMan_V2 { Contact on discord for support : normalmanv2 } ");
    }

    //                      Helper methods to handle functions in our event classes :


    // If the cooldown timer task does not contain the player related in function, it will return true : player has no cooldown.
    public boolean isCooldownExpired(Player player) {
        return !CooldownTimerTask.hasCooldown(player.getName(), "TownHop Cooldown");
    }

    // Method used to create a cooldown for the player related in the function.
    public void setCooldown(Player player) {
        CooldownTimerTask.addCooldownTimer(player.getName(), "TownHop Cooldown", cooldownDurationHours);
    }

    // Method used to return the remaining time in the player's cooldown.
    public long getRemainingCooldownHours(Player player) {
        // We check the timer task for our player and if they are found, it will return the remaining time in their cooldown.
        if (CooldownTimerTask.hasCooldown(player.getName(), "TownHop Cooldown")){
            return CooldownTimerTask.getCooldownRemaining(player.getName(), "TownHop Cooldown");
        }
        // Else return 0 as there is no cooldown active.
        return 0;
    }

    // Helper method to load our config.
    private void loadConfig() {
        FileConfiguration config = getConfig();
        config.options().copyDefaults(true);
        saveConfig();

        cooldownDurationHours = config.getInt("cooldowns.duration", DEFAULT_COOLDOWN_DURATION);
        onCooldownMessage = ChatColor.translateAlternateColorCodes('&', config.getString("cooldowns.messages.onCooldown", "&cYou are on cooldown. Cannot join or leave another town."));
        remainingTimeMessage = ChatColor.translateAlternateColorCodes('&', config.getString("cooldowns.messages.remainingTime", "&eRemaining cooldown: %hours% hours."));
        inviteCooldownMessage = ChatColor.translateAlternateColorCodes('&', config.getString("cooldowns.messages.inviteCooldown", "&fCannot invite &c&l%player%&f as they are on cooldown. Remaining cooldown: &c&l%hours% &fhours."));
    }

    // On disable we save the config file as well.
    @Override
    public void onDisable() {
        saveConfig();
    }
}
