package ifly.morefish.gui.menus.admin.rewardcreator;

import ifly.morefish.fishpack.lang.EntityMenuMsg;
import ifly.morefish.fishpack.lang.MenuMsgs;
import ifly.morefish.fishpack.pack.Pack;
import ifly.morefish.fishpack.pack.reward.RewardEntity;
import ifly.morefish.gui.Menu;
import ifly.morefish.gui.PlayerMenuUtil;
import ifly.morefish.gui.helper.ItemCreator;
import ifly.morefish.gui.menus.admin.PackRewardsMenu;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.ArrayList;
import java.util.List;

public class EntityReward extends Menu {
    private final EntityMenuMsg menu;
    List<EntityType> entityTypeList = new ArrayList<>();
    Pack pack;

    public EntityReward(PlayerMenuUtil playerMenuUtil, Pack pack) {
        super(playerMenuUtil);
        this.pack = pack;
        menu = MenuMsgs.get().EntityMenu;
    }

    @Override
    public String getMenuName() {
        return menu.title;
    }

    @Override
    public int getSlots() {
        return 6;
    }

    @Override
    public void handleInventoryClick(InventoryClickEvent e) {
        int slot = e.getSlot();
        if (slot < entityTypeList.size()) {
            EntityType entity = entityTypeList.get(slot);
            RewardEntity rewardEntity = new RewardEntity(entity, 1, 100);
            pack.getRewards().add(rewardEntity);
            new PackRewardsMenu(getPlayerMenuUtil(), pack).open();
        }
        if (e.getSlot() == getSlots() * 9 - 9) {
            new PackRewardsMenu(getPlayerMenuUtil(), pack).open();
        }
        e.setCancelled(true);
    }

    @Override
    public void setMenuItems() {
        int i = 0;
        entityTypeList.add(EntityType.ZOMBIE);
        entityTypeList.add(EntityType.CREEPER);
        for (EntityType entity : entityTypeList) {
            getInventory().setItem(i, ItemCreator.create(Material.getMaterial(entity.name() + "_SPAWN_EGG"), entity.getName(),
                    menu.desc));
            i++;
        }
        getInventory().setItem(getSlots() * 9 - 9, menu.back_item);
    }
}
