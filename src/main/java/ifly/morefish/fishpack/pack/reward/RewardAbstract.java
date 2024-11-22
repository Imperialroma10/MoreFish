package ifly.morefish.fishpack.pack.reward;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Set;

abstract public class RewardAbstract {

    int chance = 0;
    ItemStack item;


    public abstract String getRewardMessage();

    public int getChance() {
        return chance;
    }


    public void setChance(int chance) {
        this.chance = chance;
    }

    abstract public void giveReward(Player player);


    public abstract ItemStack getItem();

    public void setItem(ItemStack item) {
        this.item = item;
    }

    protected int confSize(ConfigurationSection section) {
        int num = 0;
        Set<String> keys = section.getKeys(false);
        String[] arr = keys.toArray(new String[0]);
        num = arr.length > 0 ? Integer.parseInt(arr[arr.length - 1]) : 0;
        return num;
    }

    public abstract void Save(ConfigurationSection sect);
}
