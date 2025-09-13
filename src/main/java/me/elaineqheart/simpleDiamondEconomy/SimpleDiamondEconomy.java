package me.elaineqheart.simpleDiamondEconomy;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.event.PacketListenerPriority;
import io.github.retrooper.packetevents.factory.spigot.SpigotPacketEventsBuilder;
import me.elaineqheart.simpleDiamondEconomy.GUI.DepositGUI;
import me.elaineqheart.simpleDiamondEconomy.GUI.Sidebar;
import me.elaineqheart.simpleDiamondEconomy.commands.DiamondCommand;
import me.elaineqheart.simpleDiamondEconomy.data.Messages;
import me.elaineqheart.simpleDiamondEconomy.data.SettingManager;
import me.elaineqheart.simpleDiamondEconomy.listeners.GUIListener;
import me.elaineqheart.simpleDiamondEconomy.listeners.PlayerJoinListener;
import me.elaineqheart.simpleDiamondEconomy.listeners.ScoreboardPacketListener;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class SimpleDiamondEconomy extends JavaPlugin {

    private static SimpleDiamondEconomy instance;
    public static SimpleDiamondEconomy getInstance() {return instance;}

    @Override
    public void onLoad() {
        PacketEvents.setAPI(SpigotPacketEventsBuilder.build(this));
        PacketEvents.getAPI().load();
    }

    @Override
    public void onEnable() {
        long start = System.currentTimeMillis();
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

        getCommand("diamonds").setExecutor(new DiamondCommand());
        getCommand("diamonds").setTabCompleter(new DiamondCommand());

        getServer().getPluginManager().registerEvents(new GUIListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
        PacketEvents.getAPI().getEventManager().registerListener(new ScoreboardPacketListener(), PacketListenerPriority.NORMAL);
        PacketEvents.getAPI().init();

        if(SettingManager.sidebarEnabled) Sidebar.init();

        getLogger().info("SimpleDiamondEconomy enabled in " + (System.currentTimeMillis() - start) + "ms");
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
        PacketEvents.getAPI().terminate();
    }

}
