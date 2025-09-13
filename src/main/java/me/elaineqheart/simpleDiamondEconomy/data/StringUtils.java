package me.elaineqheart.simpleDiamondEconomy.data;

import org.bukkit.ChatColor;

import java.text.DecimalFormat;

public class StringUtils {

    private static final DecimalFormat formatter;

    static {
        formatter = new DecimalFormat("#,###.##");
    }

    public static String formatNumber(double number) {
        // if the price is a whole number, format it without decimal places
        return formatter.format(number);
    }
    public static String formatPrice(double price) {
        return ChatColor.GOLD + formatNumber(price) + ChatColor.YELLOW + SettingManager.currency;
    }


}
