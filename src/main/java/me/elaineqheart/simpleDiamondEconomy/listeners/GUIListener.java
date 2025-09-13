package me.elaineqheart.simpleDiamondEconomy.listeners;

import me.elaineqheart.simpleDiamondEconomy.GUI.DepositGUI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class GUIListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {

    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        DepositGUI.onClose((Player) event.getPlayer());
    }

}
