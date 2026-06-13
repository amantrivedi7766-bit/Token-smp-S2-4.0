package com.tokensmp.token;

import com.tokensmp.TokenSMPPlugin;
import com.tokensmp.abilities.AbilityManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.List;

public class TokenManager {
    public ItemStack getTokenItem(TokenType type) {
        ItemStack item = new ItemStack(Material.NETHER_STAR);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§" + getColorCode(type) + type.getDisplayName() + " Token");
        List<Ability> abilities = TokenSMPPlugin.getInstance().getAbilityManager().getAbilitiesForToken(type);
        meta.setLore(List.of(
                "§7Rarity: " + getRarityColor(type) + type.getRarity(),
                "§7Right‑click to use abilities!",
                "§7Abilities:",
                "§a✧ " + abilities.get(0).getName(),
                "§a✧ " + abilities.get(1).getName(),
                "§a✧ " + abilities.get(2).getName()
        ));
        item.setItemMeta(meta);
        return item;
    }

    private char getColorCode(TokenType type) {
        return switch (type.getRarity()) {
            case RARE -> '9';
            case EPIC -> '5';
            case LEGENDARY -> '6';
            case MYTHIC -> 'c';
            case ADMIN -> '4';
            default -> '7';
        };
    }

    private String getRarityColor(TokenType type) { return "§" + getColorCode(type); }
}
