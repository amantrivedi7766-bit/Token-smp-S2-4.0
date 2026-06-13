// BlazingPhoenixAbilities.java
package com.tokensmp.abilities.impl;

import com.tokensmp.abilities.Ability;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class BlazingPhoenixAbilities {

    public static Ability ABILITY_1 = new Ability(
            "Inferno Dive",
            "Launch yourself forward, leaving a trail of fire and damaging enemies in your path.",
            20, // cooldown seconds
            (player) -> {
                World w = player.getWorld();
                Location start = player.getLocation();
                Vector direction = player.getLocation().getDirection().normalize().multiply(1.5);
                player.setVelocity(direction);
                // particles
                for (int i = 0; i < 30; i++) {
                    Location loc = start.clone().add(direction.clone().multiply(i * 0.3));
                    w.spawnParticle(Particle.FLAME, loc, 10, 0.2, 0.2, 0.2, 0.05);
                    w.spawnParticle(Particle.LAVA, loc, 3, 0.1, 0.1, 0.1, 0);
                }
                w.playSound(player.getLocation(), Sound.ENTITY_BLAZE_SHOOT, 1f, 1.5f);
                // damage nearby entities
                player.getNearbyEntities(3, 3, 3).forEach(e -> {
                    if (e instanceof Player target && target != player) {
                        target.damage(6, player);
                        target.setFireTicks(60);
                    }
                });
                return true;
            }
    );

    public static Ability ABILITY_2 = new Ability(
            "Ashes to Life",
            "Summon a phoenix that heals you and nearby allies.",
            35,
            (player) -> {
                World w = player.getWorld();
                Location center = player.getLocation();
                // circle particles
                for (int i = 0; i < 360; i += 10) {
                    double rad = Math.toRadians(i);
                    double x = Math.cos(rad) * 2;
                    double z = Math.sin(rad) * 2;
                    w.spawnParticle(Particle.FIREWORK, center.clone().add(x, 1, z), 0, 0, 0, 0, 1);
                }
                w.playSound(center, Sound.ENTITY_PHANTOM_AMBIENT, 1f, 0.8f);
                // heal
                player.setHealth(Math.min(player.getHealth() + 8, player.getMaxHealth()));
                player.getNearbyEntities(5, 5, 5).stream()
                        .filter(e -> e instanceof Player)
                        .map(e -> (Player) e)
                        .forEach(p -> p.setHealth(Math.min(p.getHealth() + 4, p.getMaxHealth())));
                return true;
            }
    );

    public static Ability ABILITY_3 = new Ability(
            "Firestorm",
            "Create a tornado of fire around you, damaging and pushing enemies.",
            45,
            (player) -> {
                World w = player.getWorld();
                Location loc = player.getLocation();
                w.playSound(loc, Sound.ENTITY_DRAGON_FIREBALL_EXPLOSION, 1f, 0.7f);
                for (double r = 0; r <= 2 * Math.PI; r += Math.PI / 8) {
                    double x = Math.cos(r) * 3;
                    double z = Math.sin(r) * 3;
                    Location particleLoc = loc.clone().add(x, 1, z);
                    w.spawnParticle(Particle.FLAME, particleLoc, 15, 0.3, 0.3, 0.3, 0);
                    w.spawnParticle(Particle.SMOKE_LARGE, particleLoc, 5, 0.2, 0.2, 0.2, 0);
                }
                player.getNearbyEntities(4, 3, 4).forEach(e -> {
                    if (e instanceof Player target && target != player) {
                        target.damage(9, player);
                        Vector away = target.getLocation().toVector().subtract(player.getLocation().toVector()).normalize().multiply(1.2);
                        target.setVelocity(away);
                        target.setFireTicks(100);
                    }
                });
                return true;
            }
    );
}
