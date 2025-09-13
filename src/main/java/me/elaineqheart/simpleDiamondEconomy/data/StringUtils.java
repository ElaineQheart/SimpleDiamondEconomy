package me.elaineqheart.simpleDiamondEconomy.data;

import java.text.DecimalFormat;

public class StringUtils {

    private static final DecimalFormat formatter = new DecimalFormat("#,###.##");

    public static String formatNumber(double number) {
        // if the price is a whole number, format it without decimal places
        return formatter.format(number);
    }

}
