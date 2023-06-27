package ifly.morefish.fishpack.pack.reward;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.swing.text.html.parser.Entity;

public class RewardItem extends RewardAbstract{

    ItemStack item;

   public RewardItem(ItemStack is, int chance){
       item = is;
       this.chance = chance;
   }

    @Override
    public void giveReward(Player player) {
        player.getInventory().addItem(item.clone());
    }

}
