// Token.java
package com.tokensmp.token;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.List;

public class Token {
    private final TokenType type;
    private final List<Ability> abilities;

    public Token(TokenType type, List<Ability> abilities) {
        this.type = type;
        this.abilities = abilities;
    }

    public TokenType getType() { return type; }
    public List<Ability> getAbilities() { return abilities; }

    public ItemStack getTokenItem() {
        ItemStack item = new ItemStack(Material.NETHER_STAR);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§" + getColorCode() + type.getDisplayName() + " Token");
        meta.setLore(List.of(
                "§7Rarity: " + getRarityColor() + type.getRarity(),
                "§7Right‑click to use abilities!",
                "§7Abilities:",
                "§a✧ " + abilities.get(0).getName(),
                "§a✧ " + abilities.get(1).getName(),
                "§a✧ " + abilities.get(2).getName()
        ));
        item.setItemMeta(meta);
        return item;
    }

    private char getColorCode() {
        return switch (type.getRarity()) {
            case COMMON -> '7';
            case RARE -> '9';
            case EPIC -> '5';
            case LEGENDARY -> '6';
            case MYTHIC -> 'c';
            case ADMIN -> '4';
        };
    }
    private String getRarityColor() { return "§" + getColorCode(); }
}
