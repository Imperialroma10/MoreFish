package ifly.morefish.fishpack.pack.reward;


import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

import java.util.HashMap;
import java.util.Map;


public class RewardItem extends RewardAbstract {


    public RewardItem(ItemStack is, int chance) {
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

    public void addEnchantments(Enchantment enchantment, int level) {

        this.item.addUnsafeEnchantment(enchantment, level);
    }

    public void addBookEnchantments(Enchantment enchantment, int level) {
        EnchantmentStorageMeta meta = (EnchantmentStorageMeta) item.getItemMeta();

        if (meta != null) {
            meta.addStoredEnchant(enchantment, level, true);
        }
        item.setItemMeta(meta);
        //this.item.getItemMeta().addEnchant(enchantment, level, true);
    }


    public void Save(ConfigurationSection section) {
        int num = confSize(section);
        num++;
        String displayname = null;
        int modeldata = -1;
        if (this.item.hasItemMeta()) {
            ItemMeta im = item.getItemMeta();
            if (im.hasDisplayName()) {
                displayname = im.getDisplayName();
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
        Map<Enchantment, Integer> enchs;
        Map<Enchantment, Integer> benchs = new HashMap<>();
        Map<Enchantment, Integer> effects;

        ItemMeta meta = item.getItemMeta();

        enchs = item.getEnchantments();
        if (meta instanceof EnchantmentStorageMeta) {
            EnchantmentStorageMeta bench = (EnchantmentStorageMeta) meta;
            benchs = bench.getStoredEnchants();
        }
        if (meta instanceof PotionMeta) {
            PotionMeta potionMeta = (PotionMeta) meta;
            //PotionData data = potionMeta.getBasePotionData();

            //section.set(num + ".potion", data.getType().name());
        }

//        if (item.getType() == Material.ENCHANTED_BOOK) {
//            enchs = ((EnchantmentStorageMeta) item.getItemMeta()).getStoredEnchants();
//        } else {
//            enchs = item.getEnchantments();
//        }

        for (Map.Entry<Enchantment, Integer> bench : benchs.entrySet()) {
            section.set(num + ".benchants" + bench.getKey() + ".level", bench.getValue());
        }
        for (Map.Entry<Enchantment, Integer> ench : enchs.entrySet()) {
            section.set(num + ".enchants." + ench.getKey().getKey().getKey() + ".level", ench.getValue());
        }
    }
}
