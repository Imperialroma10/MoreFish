package ifly.morefish.gui.menus;

import ifly.morefish.datastorage.StorageCreator;
import ifly.morefish.fishpack.FishController;
import ifly.morefish.fishpack.lang.EditMenuMsg;
import ifly.morefish.fishpack.lang.MenuMsgs;
import ifly.morefish.fishpack.pack.Pack;
import ifly.morefish.gui.Menu;
import ifly.morefish.gui.PlayerMenuUtil;
import ifly.morefish.gui.anvil.AnvilController;
import ifly.morefish.gui.anvil.actions.EditPackDisplayName;
import ifly.morefish.gui.helper.ItemCreator;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.awt.font.TextHitInfo;

public class EditMenu extends Menu {
    private final EditMenuMsg menu;
    Pack pack;
    boolean isnewpack;
    public EditMenu(PlayerMenuUtil playerMenuUtil, boolean editnewpack, Pack pack) {
        super(playerMenuUtil);
        this.pack = pack;
        isnewpack = editnewpack;
        menu = MenuMsgs.get().EditMenu;
    }
    public EditMenu(PlayerMenuUtil playerMenuUtil) {
        super(playerMenuUtil);
        menu = MenuMsgs.get().EditMenu;
    }

    @Override
    public String getMenuName() {
        return menu.title.replace("{packname}", pack.getDisplayname());
    }

    @Override
    public int getSlots() {
        return 3;
    }

    @Override
    public void handleInventoryClick(InventoryClickEvent e) {

        if (e.getSlot() == 12-9){
            pack.setDropChance(pack.getDropChance()+5);
            getInventory().setItem(12, ItemCreator.replace(getInventory().getItem(12), menu.chance_status.replace("{chance}", String.valueOf(pack.getDropChance()))));
            getPlayerMenuUtil().getOwner().sendMessage(Component.text("§bPack chance set to: §a"+pack.getDropChance()+"%"));
        }
        if (e.getSlot() == 12+9){
            pack.setDropChance(pack.getDropChance()-5);
            getInventory().setItem(12, ItemCreator.replace(getInventory().getItem(12), menu.chance_status.replace("{chance}", String.valueOf(pack.getDropChance()))));
            getPlayerMenuUtil().getOwner().sendMessage(Component.text("§bPack chance set to: §a"+pack.getDropChance()+"%"));
        }
        if (e.getSlot() == 14){
            PackRewardsMenu packRewardsMenu = new PackRewardsMenu(getPlayerMenuUtil(), pack);
            packRewardsMenu.open();
        }
        if (e.getSlot() == 3*9-1){
            if(isnewpack) {
                FishController.packList.add(pack);
            }
            StorageCreator.getStorageIns().Save(pack, isnewpack);
            isnewpack = false;
        }
        if (e.getSlot() == 3*9-9){
            new PackListMenu(getPlayerMenuUtil()).open();
        }
        if (e.getSlot() == 10){
            AnvilController.createAnvil((Player) e.getWhoClicked(), new EditPackDisplayName((Player) e.getWhoClicked(), pack, isnewpack));
        }
        if (e.getSlot() == 3*9-2){
            Pack newPack = StorageCreator.getStorageIns().laodFromFile(pack);
            if (newPack != null){
                FishController.packList.remove(pack);
                FishController.packList.add(newPack);
                this.pack = newPack;
                open();
            }

        }
        if (e.getSlot() == 3*9-3){
            FishController.packList.remove(pack);
            new PackListMenu(getPlayerMenuUtil()).open();
            StorageCreator.getStorageIns().removePack(pack);
        }
        e.setCancelled(true);
    }


    public Pack getPack()
    {
        return pack;
    }
    @Override
    public void setMenuItems() {
        getInventory().setItem(12-9, menu.add_chance);
        ItemStack itemchance = menu.chance_status_item.clone();
        itemchance.editMeta(im->im.displayName(Component.text(menu.chance_status.replace("{chance}", String.valueOf(pack.getDropChance())))));
        getInventory().setItem(12, itemchance);
        getInventory().setItem(12+9, menu.sub_chance);
        getInventory().setItem(14, menu.rewards_item);
        getInventory().setItem(10, menu.change_pack_name);
        getInventory().setItem(3*9-1, menu.save_item);
        getInventory().setItem(3*9-3, menu.remove_pack);
        getInventory().setItem(3*9-2, menu.reload_pack);
        getInventory().setItem(3*9-9, menu.back_item);
    }
}
