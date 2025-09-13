package me.elaineqheart.simpleDiamondEconomy.listeners;

import me.elaineqheart.simpleDiamondEconomy.GUI.Sidebar;
import me.elaineqheart.simpleDiamondEconomy.data.SettingManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player p = event.getPlayer();
        if(SettingManager.sidebarEnabled) {
            Sidebar.updateScoreboard(p);
        } else if (p.getPersistentDataContainer().has(Sidebar.key)) {
            p.getPersistentDataContainer().remove(Sidebar.key);
            Sidebar.removeScoreBoard(p);
        }
    }

}
