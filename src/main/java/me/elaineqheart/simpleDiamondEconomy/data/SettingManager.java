package me.elaineqheart.simpleDiamondEconomy.data;

import me.elaineqheart.simpleDiamondEconomy.SimpleDiamondEconomy;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SettingManager {

    public static boolean sidebarEnabled;
    public static String currency;
    public static Map<Material, Double> itemMaterials;
    public static List<String> itemNames;

    static {
        loadData();
    }

    public static void loadData() {
        FileConfiguration c = SimpleDiamondEconomy.getInstance().getConfig();
        sidebarEnabled = c.getBoolean("sidebar-enabled", true);
        currency = c.getString("currency", " coins");

        ConfigurationSection itemConf = c.getConfigurationSection("items");
        if(itemConf == null) return;

        Set<String> itemKeys = itemConf.getKeys(false);
        itemNames = itemKeys.stream().toList();
        Map<Material, Double> tempMap = new HashMap<>();
        for(String key : itemKeys) {
            Material mat = Material.matchMaterial(key);
            if(mat == null) {
                SimpleDiamondEconomy.getInstance().getLogger().warning("Invalid material in config: " + key);
                continue;
            }
            double value = itemConf.getDouble(key, 0);
            tempMap.put(mat, value);
        }
        itemMaterials = tempMap;
    }

}
