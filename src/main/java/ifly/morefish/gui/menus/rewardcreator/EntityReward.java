package ifly.morefish.gui.menus.rewardcreator;

import ifly.morefish.fishpack.pack.Pack;
import ifly.morefish.fishpack.pack.reward.RewardEntity;
import ifly.morefish.gui.Menu;
import ifly.morefish.gui.PlayerMenuUtil;
import ifly.morefish.gui.helper.ItemCreator;
import ifly.morefish.gui.menus.PackRewardsMenu;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.ArrayList;
import java.util.List;

public class EntityReward extends Menu {
    List<EntityType> entityTypeList = new ArrayList<>();
    Pack pack;
    public EntityReward(PlayerMenuUtil playerMenuUtil, Pack pack) {
        super(playerMenuUtil);
        this.pack = pack;
    }

    @Override
    public String getMenuName() {
        return null;
    }

    @Override
    public int getSlots() {
        return 6;
    }

    @Override
    public void handleInventoryClick(InventoryClickEvent e) {
        int slot = e.getSlot();
        if (slot < entityTypeList.size()){
            EntityType entity = entityTypeList.get(slot);
            RewardEntity rewardEntity = new RewardEntity(entity,1,100);
            pack.getRewards().add(rewardEntity);
            new PackRewardsMenu(getPlayerMenuUtil(), pack).open();
        }
        if (e.getSlot() == getSlots()*9-9){
            new PackRewardsMenu(getPlayerMenuUtil(), pack).open();
        }
        e.setCancelled(true);
    }

    @Override
    public void setMenuItems() {
        int i = 0;
        entityTypeList.add(EntityType.ZOMBIE);
        entityTypeList.add(EntityType.CREEPER);
        for (EntityType entity : entityTypeList){
            getInventory().setItem(i, ItemCreator.create(Material.getMaterial(entity.name()+"_SPAWN_EGG"),"spawn "+ entity.getName()));
            i++;
        }
        getInventory().setItem(getSlots()*9-9, ItemCreator.create(Material.BARRIER, "Back"));
    }
}
