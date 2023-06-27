package ifly.morefish.fishpack.pack;

import ifly.morefish.fishpack.pack.reward.RewardCommand;
import ifly.morefish.fishpack.pack.reward.RewardEntity;
import ifly.morefish.fishpack.pack.reward.RewardItem;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.ItemStack;

public class DefaultPack extends Pack{
    public DefaultPack(){
        super("Default",1);
        setDropChance(50);

        this.rewards.add(new RewardItem(Material.DIAMOND, 64, 50));
        this.rewards.add(new RewardEntity(EntityType.ZOMBIE.name(), 10, 100 ));
        this.rewards.add(new RewardCommand("time set day", 100));

    }
}
