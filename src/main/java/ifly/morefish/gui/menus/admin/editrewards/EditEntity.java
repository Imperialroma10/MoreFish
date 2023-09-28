package ifly.morefish.gui.menus.admin.editrewards;

import ifly.morefish.fishpack.lang.EditEntityMenuMsg;
import ifly.morefish.fishpack.lang.MenuMsgs;
import ifly.morefish.fishpack.pack.Pack;
import ifly.morefish.fishpack.pack.reward.RewardEntity;
import ifly.morefish.gui.Menu;
import ifly.morefish.gui.PlayerMenuUtil;
import ifly.morefish.gui.helper.ItemCreator;
import ifly.morefish.gui.menus.admin.PackRewardsMenu;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;

public class EditEntity extends Menu {
    RewardEntity entity;
    Pack pack;

    EditEntityMenuMsg menu;

    public EditEntity(PlayerMenuUtil playerMenuUtil, RewardEntity entity, Pack pack) {
        super(playerMenuUtil);
        this.entity = entity;
        this.pack = pack;
        menu = MenuMsgs.get().EditEntityMenu;
    }


    @Override
    public String getMenuName() {
        return menu.title.replace("{1}", entity.getEntityType().name());
    }

    @Override
    public int getSlots() {
        return 3;
    }

    @Override
    public void handleInventoryClick(InventoryClickEvent e) {
        if (e.getSlot() == 11) {
            if (e.isLeftClick()) {
                if (entity.getAmount() + 1 <= 64) {
                    entity.setAmount(entity.getAmount() + 1);
                }
            }
            if (e.isRightClick()) {
                if (entity.getAmount() - 1 >= 0) {
                    entity.setAmount(entity.getAmount() - 1);
                }
            }
            setMenuItems();
        }
        if (e.getSlot() == 15) {
            if (e.isLeftClick()) {
                if (entity.getChance() + 5 <= 100) {
                    entity.setChance(entity.getChance() + 5);
                } else {
                    entity.setChance(100);
                }
            }
            if (e.isRightClick()) {
                if (entity.getChance() - 5 >= 0) {
                    entity.setChance(entity.getChance() - 5);
                } else {
                    entity.setChance(0);
                }
            }
            setMenuItems();
        }
        if (e.getSlot() == getSlots() * 9 - 9) {
            new PackRewardsMenu(getPlayerMenuUtil(), pack).open();
        }
        e.setCancelled(true);
    }

    @Override
    public void setMenuItems() {

        String title = menu.amount_item.replace("{1}", String.valueOf(entity.getAmount()));
        getInventory().setItem(11, ItemCreator.replace(entity.getItem(), title, entity.getAmount(),
                menu.desc));
        getInventory().setItem(15, ItemCreator.create(Material.COMPASS, menu.spawnchance_item.replace("{1}", String.valueOf(entity.getChance())), menu.spawnchance_desc));
        getInventory().setItem(getSlots() * 9 - 9, menu.back_item);
    }
}
