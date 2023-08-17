package ifly.morefish.fishpack.pack.reward;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RewardEntity extends RewardAbstract{

    EntityType entityType;

    List<LivingEntity> livingEntities = new ArrayList<>();

    ItemStack head;
    ItemStack body;
    ItemStack leggins;
    ItemStack boots;
    //ItemStack[] armors;
    int amount;

    int chance;


    public RewardEntity(EntityType type, int amount, int chance){
        entityType = type;
        this.amount = amount;
        this.chance = chance;
        //armors = new ItemStack[] {head, body, leggins, boots};
    }



    @Override
    public void giveReward(Player player) {
        for (int i = 0 ; i < amount ; i++){
           livingEntities.add((LivingEntity) player.getLocation().getWorld().spawnEntity(player.getLocation(), this.entityType));
           equip();
        }
    }

    public void setArmor(String type, ItemStack is)
    {
        if(type.endsWith("HELMET")) { head = is; }
        if(type.endsWith("CHESTPLATE")) { body = is; }
        if(type.endsWith("LEGGINGS")) { leggins = is; }
        if(type.endsWith("BOOTS")) { boots = is; }
    }

    public ItemStack getArmor(String type)
    {
        if(type.endsWith("HELMET")) { return head; }
        if(type.endsWith("CHESTPLATE")) { return body; }
        if(type.endsWith("LEGGINGS")) { return leggins; }
        if(type.endsWith("BOOTS")) { return boots; }
        return null;
    }

    public ItemStack getBody() {
        return body;
    }

    public ItemStack getHead() {
        return head;
    }

    public ItemStack getBoots() {
        return boots;
    }

    public ItemStack getLeggins() {
        return leggins;
    }

    public void equip(){
        for (LivingEntity livingEntity: livingEntities){
            if (getHead() != null){
                livingEntity.getEquipment().setHelmet(getHead());
            }
           if (getBody() != null){
                livingEntity.getEquipment().setChestplate(getBody());
           }
           if (getBoots() != null){
                livingEntity.getEquipment().setBoots(getBoots());
           }
           if (getLeggins() != null){
                livingEntity.getEquipment().setLeggings(getLeggins());
           }
        }
    }

    @Override
    public void Save(YamlConfiguration conf)
    {
        int num = confSize(conf);
        num++;

        conf.set("Pack.rewards."+num+".type", "mob");
        conf.set("Pack.rewards."+num+".entitytype", entityType.name());
        conf.set("Pack.rewards."+num+".amount", amount);
        conf.set("Pack.rewards."+num+".chance", chance);

        if(head != null)
        {
            Map<Enchantment, Integer> enchs = head.getEnchantments();
            for(Map.Entry<Enchantment, Integer> ench: enchs.entrySet())
            {
                conf.set("Pack.rewards."+num+".equipment."+head.getType().name()+".enchants."+ench.getKey().getKey().getKey()+".level", ench.getValue());
            }
        }
        if(body != null)
        {
            Map<Enchantment, Integer> enchs = body.getEnchantments();
            for(Map.Entry<Enchantment, Integer> ench: enchs.entrySet())
            {
                conf.set("Pack.rewards."+num+".equipment."+body.getType().name()+".enchants."+ench.getKey().getKey().getKey()+".level", ench.getValue());
            }
        }
        if(leggins != null)
        {
            Map<Enchantment, Integer> enchs = leggins.getEnchantments();
            for(Map.Entry<Enchantment, Integer> ench: enchs.entrySet())
            {
                conf.set("Pack.rewards."+num+".equipment."+leggins.getType().name()+".enchants."+ench.getKey().getKey().getKey()+".level", ench.getValue());
            }
        }
        if(boots != null)
        {
            Map<Enchantment, Integer> enchs = boots.getEnchantments();
            for(Map.Entry<Enchantment, Integer> ench: enchs.entrySet())
            {
                conf.set("Pack.rewards."+num+".equipment."+boots.getType().name()+".enchants."+ench.getKey().getKey().getKey()+".level", ench.getValue());
            }
        }
    }
}
