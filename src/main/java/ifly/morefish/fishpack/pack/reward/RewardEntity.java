package ifly.morefish.fishpack.pack.reward;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class RewardEntity extends RewardAbstract{

    EntityType entityType;

    int amount;

    int chance;

    public RewardEntity(String entityName, int amount, int chance){
        entityType = EntityType.valueOf(entityName);
        this.amount = amount;
        this.chance = chance;
    }

    @Override
    public void getReward(Player player) {
        for (int i = 0 ; i <= amount ; i++){
            player.getLocation().getWorld().spawnEntity(player.getLocation(), this.entityType);
        }

    }
}
