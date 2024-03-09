package ifly.morefish.gui.menus.admin;


import com.liba.gui.Gui;
import com.liba.gui.ListedGui;
import com.liba.gui.MenuSlot;
import com.liba.gui.buttons.BackButton;
import com.liba.utils.ItemUtil;
import ifly.morefish.datastorage.StorageCreator;
import ifly.morefish.fishpack.FishController;
import ifly.morefish.fishpack.lang.MenuMsgs;
import ifly.morefish.fishpack.lang.PacksMenuMsg;
import ifly.morefish.fishpack.pack.Pack;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class PackList extends ListedGui {

    PacksMenuMsg menumsg;

    EditMenu editMenu;


    public <T> PackList(List<T> data, Gui backGui) {
        super(MenuMsgs.get().PacksMenuMsg.title, 5, data, 4, backGui);
        menumsg = MenuMsgs.get().PacksMenuMsg;
    }


    //35 - 10 * 3
    @Override
    public void setInventoryItems() {


        for (int i = 0; i < getDataBlockSize(); i++) {
            int id = getDataBlockSize() * getPage() + i;
            if (id < getData().size()) {
                Pack pack = (Pack) getData().get(id);
                addSlot(i, new MenuSlot(ItemUtil.create(new ItemStack(Material.CHEST), pack.getDisplayname(), "§6Pack drop chance: §b" + pack.getDropChance() + "%",
                        "§6pack contains §b" + pack.getRewards().size() + " §6awards"), e -> {
                    editMenu = new EditMenu("Edit " + pack.getDisplayname() + " pack", 3, pack, this);
                    editMenu.open((Player) e.getWhoClicked());
                    e.setCancelled(true);
                }));
            }
        }

        addSlot(44, new MenuSlot(menumsg.create_new, e -> {

            Pack pack = new Pack("Pack_Name", "New pack", 0);
            editMenu = new EditMenu("Edit " + pack.getDisplayname() + " pack", 3, pack, this);
            FishController.packList.add(pack);
            StorageCreator.getStorageIns().Save(pack, true);
            editMenu.setPack(pack);
            editMenu.open((Player) e.getWhoClicked());

            e.setCancelled(true);
        }));

        addSlot(36, new BackButton(new ItemStack(Material.BARRIER),getBackGui(), "back"));


        super.setInventoryItems();
    }


    public EditMenu getEditMenu() {
        return editMenu;
    }
}
