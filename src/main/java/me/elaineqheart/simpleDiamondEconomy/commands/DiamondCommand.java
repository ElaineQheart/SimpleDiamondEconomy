package me.elaineqheart.simpleDiamondEconomy.commands;


import me.elaineqheart.simpleDiamondEconomy.GUI.DepositGUI;
import me.elaineqheart.simpleDiamondEconomy.data.Messages;
import org.bukkit.Material;
import studio.mevera.imperat.BukkitSource;
import studio.mevera.imperat.annotations.*;

@Command("diamonds")
public final class DiamondCommand {

    @Usage
    public void defaultUsage(BukkitSource source) {
        source.reply(Messages.getFormatted("chat.no-args"));
    }

    @SubCommand("withdraw")
    public static class withdrawSubCommand {
        @Usage
        public void defaultUsage(BukkitSource source) {
            DepositGUI.open(source.asPlayer(), true, null);
        }

        @Usage
        public void specifiedItem(
                BukkitSource source,
                @Named("item") @SuggestionProvider("materials") String item
        ) {
            DepositGUI.open(source.asPlayer(), true, Material.matchMaterial(item));
        }
    }

    @SubCommand("deposit")
    public void deposit(BukkitSource source) {
        DepositGUI.open(source.asPlayer(),false, null);
    }
}
