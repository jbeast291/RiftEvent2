package SaveDataHandler;

import RiftEvent2.RiftEvent2;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;

public class SaveDataHandler {
    private FileConfiguration customdata = null;
    private File customdataFile = null;
    private String ConfigFileName = "CurrentRift.yml";

    public void reloadCustomConfig() {
        if (customdataFile == null) {
            customdataFile = new File(RiftEvent2.getInstance().getDataFolder(), ConfigFileName);
        }
        customdata = YamlConfiguration.loadConfiguration(customdataFile);

        // Look for defaults in the jar
        Reader defConfigStream = null;
        defConfigStream = new InputStreamReader(RiftEvent2.getInstance().getResource(ConfigFileName), StandardCharsets.UTF_8);
        if (defConfigStream != null) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
            customdata.setDefaults(defConfig);
        }
    }

    public FileConfiguration getConfig() {
        if (customdata == null) {
            reloadCustomConfig();
        }
        return customdata;
    }

    public void saveCustomConfig() {
        if (customdata == null || customdataFile == null) {
            return;
        }
        try {
            getConfig().save(customdataFile);
        } catch (IOException ex) {
            RiftEvent2.getInstance().getLogger().log(Level.SEVERE, "Could not save config to " + customdataFile, ex);
        }
    }

    public void saveDefaultCustomConfig() {
        if (customdataFile == null) {
            customdataFile = new File(RiftEvent2.getInstance().getDataFolder(), ConfigFileName);
        }
        if (!customdataFile.exists()) {
            RiftEvent2.getInstance().saveResource(ConfigFileName, false);
        }
    }
}
