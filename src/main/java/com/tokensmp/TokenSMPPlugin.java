package com.tokensmp;

import com.tokensmp.commands.*;
import com.tokensmp.data.DataManager;
import com.tokensmp.gui.AdminGUI;
import com.tokensmp.life.LifeManager;
import com.tokensmp.life.ReviveRecipe;
import com.tokensmp.spin.SpinManager;
import com.tokensmp.token.TokenManager;
import com.tokensmp.abilities.AbilityManager;
import com.tokensmp.listeners.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class TokenSMPPlugin extends JavaPlugin {
    private static TokenSMPPlugin instance;
    private DataManager dataManager;
    private TokenManager tokenManager;
    private SpinManager spinManager;
    private LifeManager lifeManager;
    private AbilityManager abilityManager;
    private AdminGUI adminGUI;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        createConfigs();

        dataManager = new DataManager();
        tokenManager = new TokenManager();
        spinManager = new SpinManager();
        lifeManager = new LifeManager();
        abilityManager = new AbilityManager();
        adminGUI = new AdminGUI();

        registerCommands();
        registerEvents();

        new ReviveRecipe();

        getLogger().info("TokenSMP enabled - 10 Tokens, Abilities, Life System Ready!");
    }

    @Override
    public void onDisable() {
        getLogger().info("TokenSMP disabled");
    }

    private void createConfigs() {
        getConfig().options().copyDefaults(true);
        saveConfig();
        saveResource("messages.yml", false);
    }

    private void registerCommands() {
        getCommand("tokens").setExecutor(new TokenCommand());
        getCommand("tokeninfo").setExecutor(new TokenInfoCommand());
        getCommand("spin").setExecutor(new SpinCommand());
        getCommand("tokensmp").setExecutor(new AdminCommands());
    }

    private void registerEvents() {
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerDeathListener(), this);
        Bukkit.getPluginManager().registerEvents(new ItemUseListener(), this);
    }

    public static TokenSMPPlugin getInstance() { return instance; }
    public DataManager getDataManager() { return dataManager; }
    public TokenManager getTokenManager() { return tokenManager; }
    public SpinManager getSpinManager() { return spinManager; }
    public LifeManager getLifeManager() { return lifeManager; }
    public AbilityManager getAbilityManager() { return abilityManager; }
    public AdminGUI getAdminGUI() { return adminGUI; }
}
