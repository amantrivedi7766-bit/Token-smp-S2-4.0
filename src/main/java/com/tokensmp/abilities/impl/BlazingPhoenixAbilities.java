package com.tokensmp.abilities.impl;

import com.tokensmp.token.Ability;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import java.util.List;

public class BlazingPhoenixAbilities {
    public static List<Ability> getAbilities() {
        return List.of(
            new Ability("Inferno Dive", "Dash forward with fire, damaging enemies", 20,
                player -> {
                    World w = player.getWorld();
                    Vector dir = player.getLocation().getDirection().normalize().multiply(1.5);
                    player.setVelocity(dir);
                    for (int i = 0; i < 20; i++) {
                        Location loc = player.getLocation().add(dir.clone().multiply(i * 0.3));
                        w.spawnParticle(Particle.FLAME, loc, 10, 0.2, 0.2, 0.2, 0.05);
                    }
                    w.playSound(player.getLocation(), Sound.ENTITY_BLAZE_SHOOT, 1f, 1.5f);
                    player.getNearbyEntities(3,3,3).forEach(e -> {
                        if (e instanceof Player t && t != player) t.damage(6, player);
                    });
                }),
            new Ability("Ashes to Life", "Heal yourself and nearby allies", 35,
                player -> {
                    World w = player.getWorld();
                    Location center = player.getLocation();
                    for (int i = 0; i < 360; i+=20) {
                        double rad = Math.toRadians(i);
                        double x = Math.cos(rad)*2;
                        double z = Math.sin(rad)*2;
                        w.spawnParticle(Particle.HEART, center.clone().add(x,1,z), 1,0,0,0,0);
                    }
                    w.playSound(center, Sound.ENTITY_PHANTOM_AMBIENT, 1f, 0.8f);
                    player.setHealth(Math.min(player.getHealth()+8, player.getMaxHealth()));
                    player.getNearbyEntities(5,5,5).stream()
                        .filter(e -> e instanceof Player)
                        .map(e -> (Player)e)
                        .forEach(p -> p.setHealth(Math.min(p.getHealth()+4, p.getMaxHealth())));
                }),
            new Ability("Firestorm", "Create a tornado of fire around you", 45,
                player -> {
                    World w = player.getWorld();
                    Location loc = player.getLocation();
                    w.playSound(loc, Sound.ENTITY_DRAGON_FIREBALL_EXPLOSION, 1f, 0.7f);
                    for (double r = 0; r <= 2*Math.PI; r+=Math.PI/8) {
                        double x = Math.cos(r)*3;
                        double z = Math.sin(r)*3;
                        w.spawnParticle(Particle.FLAME, loc.clone().add(x,1,z), 15,0.3,0.3,0.3,0);
                    }
                    player.getNearbyEntities(4,3,4).forEach(e -> {
                        if (e instanceof Player t && t != player) {
                            t.damage(9, player);
                            t.setFireTicks(100);
                        }
                    });
                })
        );
    }
}
