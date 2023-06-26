package ifly.morefish.fishpack.pack.reward;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.swing.text.html.parser.Entity;

public class RewardItem extends RewardAbstract{

    Material material;
    int amount;


   public RewardItem(Material material, int amount, int chance){
       this.material = material;
       this.amount = amount;
       this.chance = chance;
   }

    @Override
    public void getReward(Player player) {
        player.getInventory().addItem(new ItemStack(material, amount));
    }

}
