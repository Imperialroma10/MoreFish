package ifly.morefish.fishpack.pack.reward;

import ifly.morefish.gui.helper.ItemCreator;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

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
