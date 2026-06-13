package com.tokensmp.data;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BountyManager {
    private final Map<UUID, Integer> bounties = new HashMap<>();

    public void setBounty(UUID target, int amount) {
        if (amount <= 0) bounties.remove(target);
        else bounties.put(target, amount);
    }

    public int getBounty(UUID target) {
        return bounties.getOrDefault(target, 0);
    }

    public void claimBounty(UUID hunter, UUID target) {
        int bounty = getBounty(target);
        if (bounty > 0) {
            // Reward handled in listener
            bounties.remove(target);
        }
    }
}
