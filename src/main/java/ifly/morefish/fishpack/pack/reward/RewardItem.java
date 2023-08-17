package ifly.morefish.fishpack.pack.reward;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;


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



    public void addEnchantments(Enchantment enchantment, int level){
        this.item.addUnsafeEnchantment(enchantment, level);
    }

    public ItemStack getItem() {
        return item;
    }
}
