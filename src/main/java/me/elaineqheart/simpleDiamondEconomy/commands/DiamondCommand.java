package me.elaineqheart.simpleDiamondEconomy.commands;


import me.elaineqheart.simpleDiamondEconomy.GUI.DepositGUI;
import me.elaineqheart.simpleDiamondEconomy.data.SettingManager;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public final class DiamondCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if(commandSender instanceof Player p) {
            if(strings.length == 0) return false;

            if(strings[0].equals("deposit")) {
                DepositGUI.open(p, false, null);
                return true;
            }

            if(strings[0].equals("withdraw")) {
                if(strings.length == 1) {
                    DepositGUI.open(p, true, null);
                } else if(strings.length == 2) {
                    Material mat = Material.matchMaterial(strings[1]);
                    if(mat == null) return true;
                    DepositGUI.open(p, true, mat);
                }
                return true;
            }
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        List<String> params = new ArrayList<>();
        List<String> assetParams = new ArrayList<>();
        if(strings.length == 1) {
            assetParams = List.of("deposit", "withdraw");
        } else if(strings.length == 2 && strings[0].equals("withdraw")) {
            assetParams = SettingManager.itemNames;
        }
        for (String p : assetParams) {
            if (p.indexOf(strings[strings.length-1]) == 0){
                params.add(p);
            }
        }
        return params;
    }
}
