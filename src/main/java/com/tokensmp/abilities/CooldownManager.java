package com.tokensmp.abilities;

import com.tokensmp.TokenSMPPlugin;
import com.tokensmp.token.Ability;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CooldownManager {
    private final Map<UUID, Map<String, Long>> cooldowns = new HashMap<>();
    private final Map<UUID, BossBar> activeBossBars = new HashMap<>();

    public boolean isOnCooldown(Player player, Ability ability) {
        Map<String, Long> playerCD = cooldowns.get(player.getUniqueId());
        if (playerCD == null) return false;
        Long end = playerCD.get(ability.getName());
        return end != null && end > System.currentTimeMillis();
    }

    public long getRemaining(Player player, Ability ability) {
        Map<String, Long> playerCD = cooldowns.get(player.getUniqueId());
        if (playerCD == null) return 0;
        Long end = playerCD.get(ability.getName());
        if (end == null) return 0;
        long remaining = (end - System.currentTimeMillis()) / 1000;
        return Math.max(0, remaining);
    }

    public void setCooldown(Player player, Ability ability) {
        long end = System.currentTimeMillis() + (ability.getCooldownSeconds() * 1000L);
        cooldowns.computeIfAbsent(player.getUniqueId(), k -> new HashMap<>()).put(ability.getName(), end);
        startBossBar(player, ability);
    }

    private void startBossBar(Player player, Ability ability) {
        if (!TokenSMPPlugin.getInstance().getConfig().getString("cooldown-indicator").equals("BOSSBAR")) return;
        BossBar bar = activeBossBars.get(player.getUniqueId());
        if (bar != null) bar.removeAll();
        bar = Bukkit.createBossBar("§cCooldown: " + ability.getName(), BarColor.RED, BarStyle.SEGMENTED_10);
        bar.addPlayer(player);
        bar.setProgress(1.0);
        activeBossBars.put(player.getUniqueId(), bar);

        Bukkit.getScheduler().runTaskTimer(TokenSMPPlugin.getInstance(), new Runnable() {
            long start = System.currentTimeMillis();
            long total = ability.getCooldownSeconds() * 1000L;
            @Override
            public void run() {
                long elapsed = System.currentTimeMillis() - start;
                double progress = 1.0 - (double) elapsed / total;
                if (progress <= 0 || !isOnCooldown(player, ability)) {
                    bar.removeAll();
                    activeBossBars.remove(player.getUniqueId());
                    player.sendActionBar("§aAbility ready: " + ability.getName());
                    this.cancelTask();
                } else {
                    bar.setProgress(progress);
                    player.sendActionBar("§c" + ability.getName() + " cooldown: " + (int)(progress * total / 1000) + "s");
                }
            }
            void cancelTask() { Bukkit.getScheduler().cancelTask(this.hashCode()); }
        }, 0L, 20L);
    }
}
