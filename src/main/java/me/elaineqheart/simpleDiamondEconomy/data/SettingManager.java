package me.elaineqheart.simpleDiamondEconomy.data;

import me.elaineqheart.simpleDiamondEconomy.SimpleDiamondEconomy;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.*;

public class SettingManager {

    public static boolean sidebarEnabled;
    public static Map<Material, Double> itemMaterials = new HashMap<>();
    public static List<String> itemNames;
    public static int sidebarUpdateInterval;
    public static List<Map.Entry<Material, Double>> sortedEntries;

    static {
        loadData();
    }

    public static void loadData() {
        FileConfiguration c = SimpleDiamondEconomy.getInstance().getConfig();
        sidebarEnabled = c.getBoolean("sidebar-enabled", true);
        sidebarUpdateInterval = c.getInt("sidebar-update-ticks", 10);

        ConfigurationSection itemConf = c.getConfigurationSection("items");
        if(itemConf == null) return;

        Set<String> itemKeys = itemConf.getKeys(false);
        itemNames = itemKeys.stream().toList();
        for(String key : itemKeys) {
            Material mat = Material.matchMaterial(key);
            if(mat == null) {
                SimpleDiamondEconomy.getInstance().getLogger().warning("Invalid material in config: " + key);
                continue;
            }
            double value = itemConf.getDouble(key, 0);
            itemMaterials.put(mat, value);
        }

        sortedEntries = SettingManager.itemMaterials.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .toList()
                .reversed();
    }

}
