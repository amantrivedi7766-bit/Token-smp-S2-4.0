package com.tokensmp.data;

import com.tokensmp.token.TokenType;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class PlayerData {
    private final UUID uuid;
    private Set<TokenType> tokens;
    private int lives;

    public PlayerData(UUID uuid, int lives) {
        this.uuid = uuid;
        this.tokens = new HashSet<>();
        this.lives = lives;
    }

    public UUID getUuid() { return uuid; }
    public Set<TokenType> getTokens() { return tokens; }
    public int getLives() { return lives; }
    public void setLives(int lives) { this.lives = lives; }
    public void addToken(TokenType token) { tokens.add(token); }
    public void removeToken(TokenType token) { tokens.remove(token); }
    public boolean hasToken(TokenType token) { return tokens.contains(token); }
}
