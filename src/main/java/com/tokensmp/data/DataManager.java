package com.tokensmp.data;

import com.tokensmp.TokenSMPPlugin;
import com.tokensmp.token.TokenType;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DataManager {
    private final Map<UUID, PlayerData> playerDataMap = new HashMap<>();
    private File dataFile;
    private FileConfiguration dataConfig;

    public DataManager() {
        loadData();
    }

    private void loadData() {
        dataFile = new File(TokenSMPPlugin.getInstance().getDataFolder(), "playerdata.yml");
        if (!dataFile.exists()) {
            try {
                dataFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        dataConfig = YamlConfiguration.loadConfiguration(dataFile);
        for (String key : dataConfig.getKeys(false)) {
            UUID uuid = UUID.fromString(key);
            int lives = dataConfig.getInt(key + ".lives", TokenSMPPlugin.getInstance().getConfig().getInt("max-lives", 5));
            PlayerData pd = new PlayerData(uuid, lives);
            for (String tokenName : dataConfig.getStringList(key + ".tokens")) {
                try {
                    pd.addToken(TokenType.valueOf(tokenName));
                } catch (IllegalArgumentException ignored) {}
            }
            playerDataMap.put(uuid, pd);
        }
    }

    public void saveData() {
        for (Map.Entry<UUID, PlayerData> entry : playerDataMap.entrySet()) {
            String uuid = entry.getKey().toString();
            PlayerData pd = entry.getValue();
            dataConfig.set(uuid + ".lives", pd.getLives());
            dataConfig.set(uuid + ".tokens", pd.getTokens().stream().map(Enum::name).toList());
        }
        try {
            dataConfig.save(dataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public PlayerData getPlayerData(UUID uuid) {
        return playerDataMap.computeIfAbsent(uuid, id -> new PlayerData(id,
                TokenSMPPlugin.getInstance().getConfig().getInt("max-lives", 5)));
    }

    public void addToken(UUID uuid, TokenType token) {
        PlayerData pd = getPlayerData(uuid);
        pd.addToken(token);
        saveData();
    }

    public void removeToken(UUID uuid, TokenType token) {
        PlayerData pd = getPlayerData(uuid);
        pd.removeToken(token);
        saveData();
    }

    public int getLives(UUID uuid) { return getPlayerData(uuid).getLives(); }
    public void setLives(UUID uuid, int lives) {
        getPlayerData(uuid).setLives(lives);
        saveData();
    }
}
