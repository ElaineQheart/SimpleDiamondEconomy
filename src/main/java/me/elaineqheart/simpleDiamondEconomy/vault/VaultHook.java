package me.elaineqheart.simpleDiamondEconomy.vault;

//import net.milkbowl.vault.chat.Chat;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

public final class VaultHook {

    private static Economy econ = null;

    private VaultHook(){
    }

    private static void setupEconomy() {
        RegisteredServiceProvider<Economy> rsp = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp != null)
            econ = rsp.getProvider();
    }

    public static Economy getEconomy() {
        return econ;
    }

    static {
        if (Bukkit.getPluginManager().getPlugin("Vault") != null){
            setupEconomy();
        }
    }


}
