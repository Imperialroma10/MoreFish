package ifly.morefish.fishpack.pack.reward;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

abstract public class RewardAbstract {

    int chance;

    public int getChance() {
        return chance;
    }

    public void setChance(int chance) {
        this.chance = chance;
    }
    abstract public void giveReward(Player player);
     public boolean checkChance(int chance){
         if (chance == 100){
             return true;
         }else{
             if (this.chance < chance){
                 return true;
             }else{
                 return false;
             }
         }

     }
}
