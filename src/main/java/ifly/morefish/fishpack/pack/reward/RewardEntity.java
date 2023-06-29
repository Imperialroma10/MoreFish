package ifly.morefish.fishpack.pack.reward;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class RewardEntity extends RewardAbstract{

    EntityType entityType;

    List<LivingEntity> livingEntities = new ArrayList<>();

    ItemStack head;
    ItemStack body;
    ItemStack leggins;
    ItemStack boots;

    int amount;

    int chance;


    public RewardEntity(EntityType type, int amount, int chance){
        entityType = type;
        this.amount = amount;
        this.chance = chance;
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

    public void setHead(ItemStack head) {
        this.head = head;
    }

    public void setBoots(ItemStack boots) {
        this.boots = boots;
    }

    public void setLeggins(ItemStack leggins) {
        this.leggins = leggins;
    }

    public void setBody(ItemStack body) {
        this.body = body;
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
}
