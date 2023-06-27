package ifly.morefish.fishpack.pack.reward;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class RewardEntity extends RewardAbstract{

    EntityType entityType;

    int amount;

    int chance;

    public RewardEntity(EntityType type, int amount, int chance){
        entityType = type;
        this.amount = amount;
        this.chance = chance;
    }

    @Override
    public void giveReward(Player player) {
        for (int i = 0 ; i <= amount ; i++){
            player.getLocation().getWorld().spawnEntity(player.getLocation(), this.entityType);
        }

    }
}
