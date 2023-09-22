package ifly.morefish.gui.menus.editrewards;

import ifly.morefish.fishpack.lang.EditItemMenuMsg;
import ifly.morefish.fishpack.lang.MenuMsgs;
import ifly.morefish.fishpack.pack.Pack;
import ifly.morefish.fishpack.pack.reward.RewardItem;
import ifly.morefish.gui.Menu;
import ifly.morefish.gui.PlayerMenuUtil;
import ifly.morefish.gui.anvil.AnvilController;
import ifly.morefish.gui.anvil.actions.EditItemName;
import ifly.morefish.gui.helper.ItemCreator;
import ifly.morefish.gui.menus.PackRewardsMenu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class EditItem extends Menu {
    RewardItem item;
    Pack pack;

    EditItemMenuMsg menu;
    public EditItem(PlayerMenuUtil playerMenuUtil, RewardItem item, Pack pack) {
        super(playerMenuUtil);
        this.item = item;
        this.pack = pack;
        menu = MenuMsgs.get().EditItemMenu;
    }

    @Override
    public String getMenuName() {
        return menu.title;
    }

    @Override
    public int getSlots() {
        return 3;
    }

    @Override
    public void handleInventoryClick(InventoryClickEvent e) {
        if (e.getSlot() == 10){
            AnvilController.createAnvil((Player) e.getWhoClicked(), new EditItemName((Player) e.getWhoClicked(), item, pack));
        }
        if (e.getSlot() == getSlots()*9-9){
            new PackRewardsMenu(getPlayerMenuUtil(), pack).open();
        }

        if (e.getSlot() == 13-9){
            if (item.getItem().getAmount()+1 <= 64){
                item.getItem().setAmount(item.getItem().getAmount()+1);
                setMenuItems();
            }

        }
        if (e.getSlot() == 13+9){
            if (item.getItem().getAmount()-1 > 0){
                item.getItem().setAmount(item.getItem().getAmount()-1);
                setMenuItems();
            }
        }
        if (e.getSlot() == 16){
            if (e.isLeftClick()){
                if (item.getChance()+5 <= 100){
                    item.setChance(item.getChance() + 5);
                }else{
                    item.setChance(100);
                }

            }
            if (e.isRightClick()) {
                if (item.getChance()-5 >= 0){
                    item.setChance(item.getChance()-5);
                }else{
                    item.setChance(0);
                }

            }
            setMenuItems();
        }
        e.setCancelled(true);
    }

    @Override
    public void setMenuItems() {
        getInventory().setItem(10, menu.editname_item);
        getInventory().setItem(13-9, menu.addamount_item);
        getInventory().setItem(13, item.getItem());
        String title = menu.dropchance.replace("{1}", String.valueOf(item.getChance()));
        getInventory().setItem(16, ItemCreator.create(Material.COMPASS, title,
                menu.dropchancelist));
        getInventory().setItem(13+9, menu.subtructamount_item);
        getInventory().setItem(getSlots()*9-9, menu.back_item);
    }
}
