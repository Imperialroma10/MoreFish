package ifly.morefish.gui.menus.admin;


import com.liba.gui.Gui;
import com.liba.gui.MenuSlot;
import ifly.morefish.datastorage.StorageCreator;
import ifly.morefish.fishpack.FishController;
import ifly.morefish.fishpack.lang.MainMenuMsg;
import ifly.morefish.fishpack.lang.MenuMsgs;
import ifly.morefish.gui.helper.ItemCreator;
import ifly.morefish.main;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class MainMenu extends Gui {

    private final MainMenuMsg menu;
    PackList packList = new PackList(FishController.packList, this);

    AboutPlugin aboutPlugin = new AboutPlugin(this);

    public MainMenu() {
        super(MenuMsgs.get().MainMenu.title, 3);
        menu = MenuMsgs.get().MainMenu;
        setGlobalcancel(true);
    }


    @Override
    public void setInventoryItems() {

        addSlot(11, new MenuSlot(menu.packs_item, e -> {
            packList.open((Player) e.getWhoClicked());
            e.setCancelled(true);
        }));
        addSlot(13, new MenuSlot(menu.packs_reload, e -> {
            FishController.packList.clear();
            FishController.packList.addAll(StorageCreator.getStorageIns().getPacks());
            e.getWhoClicked().sendMessage(main.mainPlugin.getChecker().getParam("plugin-prefix").toString() + ("Pack reloaded"));
            e.setCancelled(true);
        }));
        addSlot(15, new MenuSlot(ItemCreator.create(Material.END_CRYSTAL, "Caught packs", "§aSince the server was turned on, players :", "§ahave caught §b{count} §apacks"
                        .replace("{count}", FishController.playerStatistic.getCaughtPacks() + ""),
                "§b{count} §apacks have been opened".replace("{count}", FishController.playerStatistic.getOpenPacks() + "")), e -> {

            e.setCancelled(true);
        }));

        addSlot(26, new MenuSlot(ItemCreator.create(Material.BOOK, "§bAbout plugin"), e -> {
            aboutPlugin.open((Player) e.getWhoClicked());
        }));
    }


    public PackList getPackList() {

        return packList;
    }


}
