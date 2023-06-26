package ifly.morefish.fishpack.pack;

import ifly.morefish.fishpack.pack.reward.RewardItem;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

public class DefaultPack extends Pack{
    public DefaultPack(){
        super("Default",1);
        setDropChance(50);
        this.rewards.add(new RewardItem(Material.DIAMOND, 64, 50));

    }
}
