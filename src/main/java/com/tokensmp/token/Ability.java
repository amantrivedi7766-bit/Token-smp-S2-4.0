package com.tokensmp.token;

import org.bukkit.entity.Player;
import java.util.function.Consumer;

public class Ability {
    private final String name;
    private final String description;
    private final int cooldownSeconds;
    private final Consumer<Player> action;

    public Ability(String name, String description, int cooldownSeconds, Consumer<Player> action) {
        this.name = name;
        this.description = description;
        this.cooldownSeconds = cooldownSeconds;
        this.action = action;
    }

    public String getName() { return name; }
    public String getDescription() { return description; }
    public int getCooldownSeconds() { return cooldownSeconds; }
    public Consumer<Player> getAction() { return action; }

    public void execute(Player player) {
        action.accept(player);
    }
}
