// TokenType.java
package com.tokensmp.token;

public enum TokenType {
    // Normal tokens (7)
    BLAZING_PHOENIX("Blazing Phoenix", Rarity.LEGENDARY),
    FROST_WALKER("Frost Walker", Rarity.EPIC),
    THUNDER_STRIKE("Thunder Strike", Rarity.EPIC),
    EARTH_SHAKER("Earth Shaker", Rarity.RARE),
    SHADOW_DANCER("Shadow Dancer", Rarity.RARE),
    NATURE_SPIRIT("Nature Spirit", Rarity.LEGENDARY),
    VOID_WALKER("Void Walker", Rarity.MYTHIC),

    // Admin tokens (never spin)
    GODLIKE("Godlike", Rarity.ADMIN),
    OMNI("Omni", Rarity.ADMIN),
    CREATIVE("Creative", Rarity.ADMIN);

    private final String displayName;
    private final Rarity rarity;

    TokenType(String displayName, Rarity rarity) {
        this.displayName = displayName;
        this.rarity = rarity;
    }

    public String getDisplayName() { return displayName; }
    public Rarity getRarity() { return rarity; }
    public boolean isAdminToken() { return rarity == Rarity.ADMIN; }

    public enum Rarity {
        COMMON, RARE, EPIC, LEGENDARY, MYTHIC, ADMIN
    }
}
