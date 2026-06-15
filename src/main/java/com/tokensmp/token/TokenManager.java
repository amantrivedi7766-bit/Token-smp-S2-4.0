package com.tokensmp.abilities;

import com.tokensmp.TokenSMPPlugin;
import com.tokensmp.token.Token;
import com.tokensmp.token.TokenType;
import com.tokensmp.abilities.impl.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AbilityManager {
    private final Map<TokenType, Token> tokenMap = new HashMap<>();
    private final CooldownManager cooldownManager;

    public AbilityManager() {
        this.cooldownManager = new CooldownManager();
        registerTokens();
    }

    private void registerTokens() {
        tokenMap.put(TokenType.BLAZING_PHOENIX, new Token(TokenType.BLAZING_PHOENIX, BlazingPhoenixAbilities.getAll()));
        tokenMap.put(TokenType.FROST_WALKER, new Token(TokenType.FROST_WALKER, FrostWalkerAbilities.getAll()));
        tokenMap.put(TokenType.THUNDER_STRIKE, new Token(TokenType.THUNDER_STRIKE, ThunderStrikeAbilities.getAll()));
        tokenMap.put(TokenType.EARTH_SHAKER, new Token(TokenType.EARTH_SHAKER, EarthShakerAbilities.getAll()));
        tokenMap.put(TokenType.SHADOW_DANCER, new Token(TokenType.SHADOW_DANCER, ShadowDancerAbilities.getAll()));
        tokenMap.put(TokenType.NATURE_SPIRIT, new Token(TokenType.NATURE_SPIRIT, NatureSpiritAbilities.getAll()));
        tokenMap.put(TokenType.VOID_WALKER, new Token(TokenType.VOID_WALKER, VoidWalkerAbilities.getAll()));
        // Admin tokens have abilities too
        tokenMap.put(TokenType.GODLIKE, new Token(TokenType.GODLIKE, AdminAbilitiesPlaceholder.getGodlikeAbilities()));
        tokenMap.put(TokenType.OMNI, new Token(TokenType.OMNI, AdminAbilitiesPlaceholder.getOmniAbilities()));
        tokenMap.put(TokenType.CREATIVE, new Token(TokenType.CREATIVE, AdminAbilitiesPlaceholder.getCreativeAbilities()));
    }

    public Token getToken(TokenType type) {
        return tokenMap.get(type);
    }

    public void useAbility(Player player, TokenType tokenType, int abilityIndex) {
        Token token = tokenMap.get(tokenType);
        if (token == null) return;
        if (abilityIndex < 0 || abilityIndex >= token.getAbilities().size()) return;
        var ability = token.getAbilities().get(abilityIndex);
        UUID uuid = player.getUniqueId();
        if (cooldownManager.isOnCooldown(uuid, tokenType, abilityIndex)) {
            long remaining = cooldownManager.getRemainingCooldown(uuid, tokenType, abilityIndex);
            player.sendMessage("§cAbility on cooldown! §e" + remaining + " seconds remaining.");
            return;
        }
        ability.execute(player);
        cooldownManager.setCooldown(uuid, tokenType, abilityIndex, ability.getCooldownSeconds());
        player.sendMessage("§aYou used §6" + ability.getName() + "§a! §7Cooldown: §e" + ability.getCooldownSeconds() + "s");
    }

    public CooldownManager getCooldownManager() { return cooldownManager; }
}
