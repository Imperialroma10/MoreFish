package ifly.morefish.fishpack.pack.reward;

import net.kyori.adventure.text.TextComponent;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Map;


public class RewardItem extends RewardAbstract{



   public RewardItem(ItemStack is, int chance){
       item = is;
       setChance(chance);
   }
    @Override
    public void giveReward(Player player) {
        player.getInventory().addItem(item.clone());
    }

	@Override
	public ItemStack getItem() {
		return item;
	}

	public void addEnchantments(Enchantment enchantment, int level){
        this.item.addUnsafeEnchantment(enchantment, level);
    }



    public void Save(ConfigurationSection section) {
		int num = confSize(section);
		num++;
		String displayname = null;
		int modeldata = -1;
		if (this.item.hasItemMeta()) {
			ItemMeta im = item.getItemMeta();
			if (im.hasDisplayName()) {
				displayname = ((TextComponent) im.displayName()).content();
			}
			if (im.hasCustomModelData()) {
				modeldata = im.getCustomModelData();
			}
		}

		section.set(num + ".type", "item");
		section.set(num + ".material", item.getType().name());
		section.set(num + ".displayname", displayname);
		section.set(num + ".amount", item.getAmount());
		if (modeldata > -1) {
			section.set(num + ".custommodeldata", modeldata);
		}
		section.set(num + ".chance", chance);
		Map<Enchantment, Integer> enchs = item.getEnchantments();
		for (Map.Entry<Enchantment, Integer> ench : enchs.entrySet()) {
			section.set(num + ".enchants." + ench.getKey().getKey().getKey() + ".level", ench.getValue());
		}
	}
}
