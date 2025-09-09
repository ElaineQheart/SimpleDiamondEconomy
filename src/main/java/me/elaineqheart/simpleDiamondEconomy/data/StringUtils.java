package me.elaineqheart.simpleDiamondEconomy.data;

import org.bukkit.ChatColor;

import java.text.DecimalFormat;

public class StringUtils {

    private static DecimalFormat formatter;

    static {
        formatter = new DecimalFormat("#,###.##");
    }

    public static String formatNumber(double number) {
        // if the price is a whole number, format it without decimal places
        return ChatColor.GOLD + formatter.format(number) + ChatColor.RESET;
    }
    public static String formatPrice(double price) {
        return formatNumber(price) + ChatColor.YELLOW + SettingManager.currency;
    }


}
