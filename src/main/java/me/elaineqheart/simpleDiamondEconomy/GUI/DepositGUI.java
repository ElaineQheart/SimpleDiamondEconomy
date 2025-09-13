package me.elaineqheart.simpleDiamondEconomy.GUI;

import me.elaineqheart.simpleDiamondEconomy.data.Messages;
import me.elaineqheart.simpleDiamondEconomy.data.SettingManager;
import me.elaineqheart.simpleDiamondEconomy.data.StringUtils;
import me.elaineqheart.simpleDiamondEconomy.vault.VaultHook;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class DepositGUI {

    public static final Map<Inventory, UUID> inventories = new HashMap<>();
    private static final Map<Inventory, Boolean> withdrawModes = new HashMap<>();

    public static void open(Player p, boolean withdraw, Material material) {
        Inventory inv = Bukkit.createInventory(null, 9, Messages.getFormatted("gui.title"));
        inventories.put(inv, p.getUniqueId());
        withdrawModes.put(inv, withdraw);
        p.openInventory(inv);
        p.playSound(p, Sound.BLOCK_SHULKER_BOX_OPEN, 0.5f, 1.0f);
        if(!withdraw) return;

        if(material != null) {
            if(!SettingManager.itemMaterials.containsKey(material)) return;
            double value = SettingManager.itemMaterials.get(material);
            double balance = VaultHook.getEconomy().getBalance(p);
            int amount = (int) Math.floor(balance / value);
            int rest = fillInventory(inv, material, amount);
            VaultHook.getEconomy().withdrawPlayer(p, value * (amount-rest));
            return;
        }

        List<Map.Entry<Material, Double>> sortedEntries = SettingManager.itemMaterials.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .toList()
                .reversed();
        for(Map.Entry<Material, Double> entry : sortedEntries) {
            Material mat = entry.getKey();
            double value = entry.getValue();
            double balance = VaultHook.getEconomy().getBalance(p);
            int amount = (int) Math.floor(balance / value);
            int rest = fillInventory(inv, mat, amount);
            VaultHook.getEconomy().withdrawPlayer(p, value * (amount-rest));
        }

    }

    public static void onClose(Player p) {
        Inventory inv = p.getOpenInventory().getTopInventory();
        if (inventories.containsKey(inv) && inventories.get(inv).equals(p.getUniqueId())) {
            inventories.remove(inv);
            boolean withdraw = withdrawModes.getOrDefault(inv, false);
            withdrawModes.remove(inv);
            p.playSound(p, Sound.BLOCK_SHULKER_BOX_CLOSE, 0.5f, 1.0f);
            if(!withdraw) p.playSound(p, Sound.BLOCK_NOTE_BLOCK_PLING, 0.5f, 1.8f);

            double total = 0;
            for (int i = 0; i < inv.getSize(); i++) {
                ItemStack item = inv.getItem(i);
                if (item == null) continue;
                if (!SettingManager.itemMaterials.containsKey(item.getType())) {
                    p.getWorld().dropItemNaturally(p.getLocation(), item);
                    continue;
                }
                double value = SettingManager.itemMaterials.get(item.getType());
                total += value * item.getAmount();
            }
            VaultHook.getEconomy().depositPlayer(p, total);
            if(!withdraw) p.sendMessage(Messages.getFormatted("chat.deposit-success", "%amount%", StringUtils.formatNumber(total)));
        }
    }

    private static int fillInventory(Inventory inv, Material item, int amount) {
        if(amount <= 0) return 0;
        for (int i = 0; i < inv.getSize(); i++) {
            if(inv.getItem(i) == null) {
                int toAdd = Math.min(amount, 64);
                inv.setItem(i, new ItemStack(item, toAdd));
                amount -= toAdd;
                if(amount <= 0) break;
            }
        }
        return amount;
    }

}
