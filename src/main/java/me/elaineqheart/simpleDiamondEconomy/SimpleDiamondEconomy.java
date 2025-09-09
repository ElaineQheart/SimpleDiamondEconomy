package me.elaineqheart.simpleDiamondEconomy;

import me.elaineqheart.simpleDiamondEconomy.data.SettingManager;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class SimpleDiamondEconomy extends JavaPlugin {

    private static SimpleDiamondEconomy instance;
    public static SimpleDiamondEconomy getInstance() {return instance;}

    @Override
    public void onEnable() {

        instance = this;
        boolean a  = SettingManager.sidebarEnabled;

        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            Bukkit.getLogger().severe("No registered Vault provider found!");
            getServer().getPluginManager().disablePlugin(this);
        }

        reloadConfig();
        File file = new File(this.getDataFolder(), "config.yml");
        if (!file.exists()) {
            this.saveResource("config.yml", false);
        }

    }

}
