package ifly.morefish.fishpack.pack.reward;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.swing.text.html.parser.Entity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
}
