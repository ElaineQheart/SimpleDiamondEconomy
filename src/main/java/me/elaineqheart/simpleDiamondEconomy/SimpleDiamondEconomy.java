package me.elaineqheart.simpleDiamondEconomy;

import me.elaineqheart.simpleDiamondEconomy.GUI.DepositGUI;
import me.elaineqheart.simpleDiamondEconomy.commands.DiamondCommand;
import me.elaineqheart.simpleDiamondEconomy.data.Messages;
import me.elaineqheart.simpleDiamondEconomy.data.SettingManager;
import me.elaineqheart.simpleDiamondEconomy.listeners.GUIListener;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import studio.mevera.imperat.BukkitImperat;

import java.io.File;

public final class SimpleDiamondEconomy extends JavaPlugin {

    private static SimpleDiamondEconomy instance;
    public static SimpleDiamondEconomy getInstance() {return instance;}
    private BukkitImperat imperat;

    @Override
    public void onEnable() {

        instance = this;

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
        Messages.setup();
        System.out.println(SettingManager.itemNames);
        this.imperat = BukkitImperat.builder(this)
                .applyBrigadier(true)
                .namedSuggestionResolver("materials", (context, parameter) -> {
                    return SettingManager.itemNames;
                })
                .build();
        imperat.registerCommand(new DiamondCommand());

        //register events
        getServer().getPluginManager().registerEvents(new GUIListener(), this);

    }

    @Override
    public void onDisable() {
        for(Player p : Bukkit.getOnlinePlayers()) {
            if(DepositGUI.inventories.containsValue(p.getUniqueId())) {
                for (ItemStack item : p.getOpenInventory().getTopInventory().getContents()) {
                    if (item != null) p.getWorld().dropItem(p.getLocation(), item);
                }
                p.closeInventory();
            }
        }
    }

}
