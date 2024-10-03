package ifly.morefish.fishpack.pack.reward;


import ifly.morefish.fishpack.Config;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;

import java.util.HashMap;
import java.util.Map;


public class RewardItem extends RewardAbstract {


    public RewardItem(ItemStack is, int chance) {
        item = is;
        setChance(chance);

    }


    public String getRewardMessage() {

        return Config.getConfig().itemrewardmessage.replace("{itemname}", item.getType().name()).replace("{count}", item.getAmount()+"");
    }

    @Override
    public void giveReward(Player player) {
        player.getInventory().addItem(item.clone());
    }

    @Override
    public ItemStack getItem() {
        return item;
    }


    public void Save(ConfigurationSection section) {
        int num = confSize(section);
        num++;

        section.set(num + ".item", getItem());
        section.set(num + ".type", "item");

        section.set(num + ".chance", chance);

    }
}
