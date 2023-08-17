package ifly.morefish.fishpack.pack.reward;

import net.kyori.adventure.text.TextComponent;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Map;


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

    @Override
    public void Save(YamlConfiguration conf)
    {
        int num = confSize(conf);
        num++;
        String displayname = null;
        int modeldata = -1;
        if(item.hasItemMeta())
        {
            ItemMeta im = item.getItemMeta();
            if(im.hasDisplayName())
            {
                displayname = ((TextComponent)im.displayName()).content();
            }
            if(im.hasCustomModelData())
            {
                modeldata = im.getCustomModelData();
            }
        }

        conf.set("Pack.rewards."+num+".type", "item");
        conf.set("Pack.rewards."+num+".material", item.getType().name());
        conf.set("Pack.rewards."+num+".displayname", displayname);
        conf.set("Pack.rewards."+num+".amount", item.getAmount());
        if(modeldata > -1)
        {
            conf.set("Pack.rewards."+num+".custommodeldata", modeldata);
        }
        conf.set("Pack.rewards."+num+".chance", chance);
        Map<Enchantment, Integer> enchs = item.getEnchantments();
        for(Map.Entry<Enchantment, Integer> ench: enchs.entrySet())
        {
            conf.set("Pack.rewards."+num+".enchants."+ench.getKey().getKey().getKey()+".level", ench.getValue());
        }
    }
}
