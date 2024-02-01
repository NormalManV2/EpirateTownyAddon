package nuclearkat.epiratetownyaddon.cooldownutil;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

public class CooldownFileUtil {

    public static void loadCooldownsFromFile(Map<UUID, Long> cooldowns, File dataFolder) {
        File configFile = new File(dataFolder, "cooldowns.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(configFile);

        for (String key : config.getKeys(false)) {
            UUID playerUUID = UUID.fromString(key);
            long cooldownEnd = config.getLong(key);
            cooldowns.put(playerUUID, cooldownEnd);
        }
    }

    public static void saveCooldownsToFile(Map<UUID, Long> cooldowns, File dataFolder) {
        File configFile = new File(dataFolder, "cooldowns.yml");
        YamlConfiguration config = new YamlConfiguration();

        for (Map.Entry<UUID, Long> entry : cooldowns.entrySet()) {
            String key = entry.getKey().toString();
            long cooldownEnd = entry.getValue();
            config.set(key, cooldownEnd);
        }

        try {
            config.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
