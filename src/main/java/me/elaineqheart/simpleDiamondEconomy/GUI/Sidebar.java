package me.elaineqheart.simpleDiamondEconomy.GUI;

import me.elaineqheart.simpleDiamondEconomy.SimpleDiamondEconomy;
import me.elaineqheart.simpleDiamondEconomy.data.Messages;
import me.elaineqheart.simpleDiamondEconomy.data.SettingManager;
import me.elaineqheart.simpleDiamondEconomy.data.StringUtils;
import me.elaineqheart.simpleDiamondEconomy.vault.VaultHook;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scoreboard.*;

import java.util.Objects;

public class Sidebar {

    public static final NamespacedKey key = new NamespacedKey(SimpleDiamondEconomy.getInstance(), "sidebarEnabled");

    private static void setScoreBoard(Player player) {
        Scoreboard board = Objects.requireNonNull(Bukkit.getScoreboardManager()).getNewScoreboard();
        Objective obj = board.registerNewObjective("SimpleDiamondEconomy", Criteria.DUMMY, Messages.getFormatted("side-bar.title"));
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);

        Team moneyCounter = board.registerNewTeam("moneyCounter");
        moneyCounter.addEntry(ChatColor.RED + "" + ChatColor.WHITE);
        double purse = VaultHook.getEconomy().getBalance(player);
        moneyCounter.setPrefix(Messages.getFormatted("side-bar.text","%balance%", StringUtils.formatNumber(purse)));
        obj.getScore(ChatColor.RED + "" + ChatColor.WHITE).setScore(0);

        player.setScoreboard(board);
    }

    public static void removeScoreBoard(Player player) {
        Scoreboard board = Objects.requireNonNull(Bukkit.getScoreboardManager()).getNewScoreboard();
        player.setScoreboard(board);
    }

    private static void rewriteScoreBoard(Player player) {
        Scoreboard board = player.getScoreboard();
        Team moneyCounter = board.getTeam("moneyCounter");
        double purse = VaultHook.getEconomy().getBalance(player);
        assert moneyCounter != null;
        moneyCounter.setPrefix(Messages.getFormatted("side-bar.text","%balance%", StringUtils.formatNumber(purse)));
    }

    public static void init() {
        SimpleDiamondEconomy.getInstance().getServer().getScheduler().runTaskTimer(SimpleDiamondEconomy.getInstance(), () -> {
            for(Player p : Bukkit.getOnlinePlayers()) {
                if(isSidebarEnabled(p) && !DepositGUI.inventories.containsValue(p.getUniqueId())) rewriteScoreBoard(p);
            }
        }, 0, SettingManager.sidebarUpdateInterval);

    }

    public static void changedSidebarState(Player player) {
        if(isSidebarEnabled(player)) {
            player.getPersistentDataContainer().set(key, PersistentDataType.BOOLEAN, false);
        } else {
            player.getPersistentDataContainer().set(key, PersistentDataType.BOOLEAN, true);
        }
        updateScoreboard(player);
    }

    public static void updateScoreboard(Player player) {
        if (isSidebarEnabled(player)) {
            setScoreBoard(player);
        } else {
            removeScoreBoard(player);
        }

    }

    private static boolean isSidebarEnabled(Player player) {
        if(!player.getPersistentDataContainer().has(key))
            player.getPersistentDataContainer().set(key, PersistentDataType.BOOLEAN, true);
        return Boolean.TRUE.equals(player.getPersistentDataContainer().get(key, PersistentDataType.BOOLEAN));
    }

}
