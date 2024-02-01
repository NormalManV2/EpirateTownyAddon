package nuclearkat.epiratetownyaddon;

import nuclearkat.epiratetownyaddon.cooldownutil.CooldownFileUtil;
import nuclearkat.epiratetownyaddon.events.TownJoinEvent;
import nuclearkat.epiratetownyaddon.events.TownLeaveEvent;
import nuclearkat.epiratetownyaddon.events.TownPreInviteEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

public final class EpirateTownyAddon extends JavaPlugin implements Listener {

    private final Map<UUID, Long> cooldowns = new HashMap<>();
    private long cooldownDurationMillis;
    public String onCooldownMessage;
    public String remainingTimeMessage;
    public String inviteCooldownMessage;

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new TownJoinEvent(this), this);
        getServer().getPluginManager().registerEvents(new TownPreInviteEvent(this), this);
        getServer().getPluginManager().registerEvents(new TownLeaveEvent(this), this);

        Bukkit.getLogger().log(Level.INFO, "Epirate Towny addon created by NormalMan_V2 { Contact on discord for support : normalmanv2 } ");
        loadConfig();
        loadCooldowns();
    }


    public boolean isCooldownExpired(Player player) {
        if (cooldowns.containsKey(player.getUniqueId())) {
            long cooldownEnd = cooldowns.get(player.getUniqueId()) + cooldownDurationMillis;
            return System.currentTimeMillis() > cooldownEnd;
        }
        return true;
    }

    public void setCooldown(Player player) {
        cooldowns.put(player.getUniqueId(), System.currentTimeMillis());
    }

    public long getRemainingCooldownHours(Player player) {
        if (cooldowns.containsKey(player.getUniqueId())) {
            long cooldownEnd = cooldowns.get(player.getUniqueId()) + cooldownDurationMillis;
            long remainingMillis = cooldownEnd - System.currentTimeMillis();
            return TimeUnit.MILLISECONDS.toHours(remainingMillis);
        }
        return 0;
    }

    private void loadCooldowns() {
        CooldownFileUtil.loadCooldownsFromFile(cooldowns, getDataFolder());
    }

    private void saveCooldowns() {
        CooldownFileUtil.saveCooldownsToFile(cooldowns, getDataFolder());
    }

    private void loadConfig() {
        FileConfiguration config = getConfig();
        config.options().copyDefaults(true);
        saveConfig();

        cooldownDurationMillis = config.getLong("cooldowns.duration", TimeUnit.HOURS.toMillis(24));
        onCooldownMessage = ChatColor.translateAlternateColorCodes('&', config.getString("cooldowns.messages.onCooldown", "&cYou are on cooldown. Cannot join or leave another town."));
        remainingTimeMessage = ChatColor.translateAlternateColorCodes('&', config.getString("cooldowns.messages.remainingTime", "&eRemaining cooldown: %hours% hours."));
        inviteCooldownMessage = ChatColor.translateAlternateColorCodes('&', config.getString("cooldowns.messages.inviteCooldown", "&fCannot invite &c&l%player%&f as they are on cooldown. Remaining cooldown: &c&l%hours% &fhours."));
    }

    @Override
    public void onDisable() {
        saveCooldowns();
    }
}
