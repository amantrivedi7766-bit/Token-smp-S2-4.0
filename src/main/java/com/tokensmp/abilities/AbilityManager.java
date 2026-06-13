package com.tokensmp.abilities;

import com.tokensmp.token.Ability;
import com.tokensmp.token.TokenType;
import com.tokensmp.abilities.impl.*;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class AbilityManager {
    private final Map<TokenType, List<Ability>> abilitiesMap = new EnumMap<>(TokenType.class);

    public AbilityManager() {
        abilitiesMap.put(TokenType.BLAZING_PHOENIX, BlazingPhoenixAbilities.getAbilities());
        abilitiesMap.put(TokenType.FROST_WALKER, FrostWalkerAbilities.getAbilities());
        abilitiesMap.put(TokenType.THUNDER_STRIKE, ThunderStrikeAbilities.getAbilities());
        abilitiesMap.put(TokenType.EARTH_SHAKER, EarthShakerAbilities.getAbilities());
        abilitiesMap.put(TokenType.SHADOW_DANCER, ShadowDancerAbilities.getAbilities());
        abilitiesMap.put(TokenType.NATURE_SPIRIT, NatureSpiritAbilities.getAbilities());
        abilitiesMap.put(TokenType.VOID_WALKER, VoidWalkerAbilities.getAbilities());
        // Admin tokens have placeholder abilities
        abilitiesMap.put(TokenType.GODLIKE, AdminAbilitiesPlaceholder.getPlaceholder());
        abilitiesMap.put(TokenType.OMNI, AdminAbilitiesPlaceholder.getPlaceholder());
        abilitiesMap.put(TokenType.CREATIVE, AdminAbilitiesPlaceholder.getPlaceholder());
    }

    public List<Ability> getAbilitiesForToken(TokenType token) {
        return abilitiesMap.get(token);
    }
}
