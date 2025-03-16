package ifly.morefish.gui.menus.admin;


import com.liba.gui.Gui;
import com.liba.gui.ListedGui;
import com.liba.gui.MenuSlot;
import com.liba.gui.buttons.BackButton;
import com.liba.utils.ItemUtil;
import com.liba.utils.headcreator.HeadCache;
import ifly.morefish.datastorage.StorageCreator;
import ifly.morefish.fishpack.FishController;
import ifly.morefish.fishpack.lang.MenuMsgs;
import ifly.morefish.fishpack.lang.PacksMenuMsg;
import ifly.morefish.fishpack.pack.Pack;
import ifly.morefish.gui.helper.ItemCreator;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class PackList extends ListedGui {

    PacksMenuMsg menumsg;

    EditMenu editMenu;


    public PackList(List<Pack> data, Gui backGui) {
        super(MenuMsgs.get().PacksMenuMsg.title, 5, data, 4 * 9, backGui);
        menumsg = MenuMsgs.get().PacksMenuMsg;
        setGlobalcancel(true);
    }


    //35 - 10 * 3
    @Override
    public void setInventoryItems() {
        setData(FishController.packList);
        this.menuSlot.clear();
        this.getInventory().clear();

        for (int i = 0; i < getDataPerPage(); i++) {
            int id = getDataPerPage() * getPage() + i;
            if (id < getData().size()) {
                Pack pack = (Pack) getData().get(id);
                addSlot(i, new MenuSlot(ItemUtil.create(new ItemStack(pack.getChest()), pack.getDisplayname(), "§bPack drop chance: §a" + pack.getDropChance() + "%",
                        "§bPack contains §a" + pack.getRewards().size() + " §bawards",
                        "§bTotal chance of all rewards §a" + pack.getAllRewardsChance() + "%",
                        pack.isEnablepermission() ? "§bNeed permission §a" + pack.getEnablepermission() : ""
                ), e -> {
                    editMenu = new EditMenu("Edit " + pack.getDisplayname() + " pack", 3, pack, this);
                    editMenu.open((Player) e.getWhoClicked());
                    e.setCancelled(true);
                }));
            }
        }

        addSlot(44, new MenuSlot(ItemUtil.create(HeadCache.getItem("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmM0OGRkZmRjZDZkOThhMWIwYWEzYzcxZThkYWQ0ZWRkZTczMmE2OGIyYjBhNWFiMTQyNjAwZGNhNzU4N2MzMiJ9fX0=")
                ,menumsg.title2, menumsg.list2), e -> {

            Pack pack = new Pack("Pack_Name", "New pack", 0, new ItemStack(Material.CHEST), null);
            editMenu = new EditMenu("Edit " + pack.getDisplayname() + " pack", 3, pack, this);
            StorageCreator.getStorageIns().Save(pack, true);
            FishController.packList.add(pack);
            editMenu.setPack(pack);
            editMenu.open((Player) e.getWhoClicked());

            e.setCancelled(true);
        }));

        addSlot(36, new BackButton(new ItemStack(Material.BARRIER), getBackGui(), "back"));


        super.setInventoryItems();
    }

    public EditMenu getEditMenu() {
        return editMenu;
    }
}
