package com.modencore.fishpack.pack.reward;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public abstract class RewardFun extends RewardAbstract {

    @Override
    public void giveReward(Player player) {
        Bukkit.broadcastMessage("spawn envil");
        action(player);
    }

    @Override
    public void Save(ConfigurationSection sect) {

    }

    public abstract void action(Player player);
}
