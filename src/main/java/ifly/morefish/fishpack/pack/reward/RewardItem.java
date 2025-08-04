package ifly.morefish.fishpack.pack.reward;


import com.liba.utils.ItemUtil;
import ifly.morefish.main;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class RewardItem extends RewardAbstract {


    public RewardItem(ItemStack is, int chance) {
        item = is;
        setChance(chance);

    }


    public String getRewardMessage() {
        if (item.hasItemMeta()) {
            ItemMeta meta = item.getItemMeta();
            if (meta != null && meta.hasDisplayName()) {
                return main.getPlugin().getChecker().getString("plugin-prefix") + main.getPlugin().getChecker().getString("item-reward-message").toString().replace("{itemname}", meta.getDisplayName()).replace("{count}", item.getAmount() + "");
            }
        }

        return main.getPlugin().getChecker().getString("plugin-prefix").toString() + main.getPlugin().getChecker().getString("item-reward-message").toString().replace("{itemname}", ItemUtil.getMaterialName(item.getType())).replace("{count}", item.getAmount() + "");
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
