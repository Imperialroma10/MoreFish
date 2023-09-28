package ifly.morefish.fishpack.pack.reward;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RewardEntity extends RewardAbstract {

    EntityType entityType;

    List<LivingEntity> livingEntities = new ArrayList<>();

    ItemStack[] armors;
    int amount;


    public RewardEntity(EntityType type, int amount, int chance) {
        entityType = type;
        this.amount = amount;
        setChance(chance);
        armors = new ItemStack[4];
    }

    @Override
    public ItemStack getItem() {
        return new ItemStack(Material.getMaterial(entityType.name() + "_SPAWN_EGG"), 1);
    }

    @Override
    public void giveReward(Player player) {
        for (int i = 0; i < amount; i++) {
            livingEntities.add((LivingEntity) player.getLocation().getWorld().spawnEntity(player.getLocation(), this.entityType));
            equip();
        }
    }

    public void setArmor(int ind, ItemStack is) {
        armors[ind] = is;
    }

    public ItemStack getArmor(int ind) {
        return armors[ind];
    }

    public void setArmor(String type, ItemStack is) {
        if (type.endsWith("HELMET")) {
            armors[0] = is;
        }
        if (type.endsWith("CHESTPLATE")) {
            armors[1] = is;
        }
        if (type.endsWith("LEGGINGS")) {
            armors[2] = is;
        }
        if (type.endsWith("BOOTS")) {
            armors[3] = is;
        }
    }

    public ItemStack getArmor(String type) {
        if (type.endsWith("HELMET")) {
            return armors[0];
        }
        if (type.endsWith("CHESTPLATE")) {
            return armors[1];
        }
        if (type.endsWith("LEGGINGS")) {
            return armors[2];
        }
        if (type.endsWith("BOOTS")) {
            return armors[3];
        }
        return null;
    }

    public void equip() {
        for (LivingEntity livingEntity : livingEntities) {
            if (armors[0] != null) {
                livingEntity.getEquipment().setHelmet(armors[0]);
            }
            if (armors[1] != null) {
                livingEntity.getEquipment().setChestplate(armors[1]);
            }
            if (armors[2] != null) {
                livingEntity.getEquipment().setBoots(armors[2]);
            }
            if (armors[3] != null) {
                livingEntity.getEquipment().setLeggings(armors[3]);
            }
        }
    }

    @Override
    public void Save(ConfigurationSection section) {
        int num = confSize(section);
        num++;

        section.set(num + ".type", "mob");
        section.set(num + ".entitytype", entityType.name());
        section.set(num + ".amount", amount);
        section.set(num + ".chance", getChance());

        for (ItemStack armor : armors) {
            if (armor == null) {
                continue;
            }
            Map<Enchantment, Integer> enchs = armor.getEnchantments();
            for (Map.Entry<Enchantment, Integer> ench : enchs.entrySet()) {
                section.set(num + ".equipment." + armor.getType().name() + ".enchants." + ench.getKey().getKey().getKey() + ".level", ench.getValue());
            }
        }
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
