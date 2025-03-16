package ifly.morefish.gui.menus.admin;


import com.liba.gui.Gui;
import com.liba.gui.MenuSlot;
import com.liba.gui.guis.AboutGui;
import com.liba.utils.ItemUtil;
import com.liba.utils.chat.Message;
import com.liba.utils.headcreator.HeadCache;
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

    AboutGui aboutPlugin = new AboutGui("§eAbout us", 3, this);

    public MainMenu() {
        super(MenuMsgs.get().MainMenu.title, 3);
        menu = MenuMsgs.get().MainMenu;
        setGlobalcancel(true);
    }


    @Override
    public void setInventoryItems() {
        addSlot(4, new MenuSlot(ItemUtil.create(HeadCache.getItem("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmM0OGRkZmRjZDZkOThhMWIwYWEzYzcxZThkYWQ0ZWRkZTczMmE2OGIyYjBhNWFiMTQyNjAwZGNhNzU4N2MzMiJ9fX0="),
                "§bInformation",
                "§eIf you have an idea for a plugin",
                "§eBe sure to email us",
                "  ",
                "§eIf you like our plugin",
                "§eLeave a review",
                "§eRight click to see urls"), e->{
            Message.tellraw(getOwner(), "[\"\",{\"text\":\"Thanks for the feedback.\",\"color\":\"yellow\"},{\"text\":\"\\n\"},{\"text\":\"Url 1 (\",\"color\":\"gray\",\"clickEvent\":{\"action\":\"open_url\",\"value\":\"https://www.spigotmc.org/resources/111966/\"}},{\"text\":\"spigotmc.org\",\"color\":\"yellow\",\"clickEvent\":{\"action\":\"open_url\",\"value\":\"https://www.spigotmc.org/resources/111966/\"}},{\"text\":\") (\",\"color\":\"gray\",\"clickEvent\":{\"action\":\"open_url\",\"value\":\"https://www.spigotmc.org/resources/111966/\"}},{\"text\":\"click\",\"color\":\"yellow\",\"clickEvent\":{\"action\":\"open_url\",\"value\":\"https://www.spigotmc.org/resources/111966/\"}},{\"text\":\")\",\"color\":\"gray\",\"clickEvent\":{\"action\":\"open_url\",\"value\":\"https://www.spigotmc.org/resources/111966/\"}},{\"text\":\"\\n\"},{\"text\":\"Url 2 (\",\"color\":\"gray\",\"clickEvent\":{\"action\":\"open_url\",\"value\":\"https://www.curseforge.com/minecraft/bukkit-plugins/fishrewards\"}},{\"text\":\"curseforge.com\",\"color\":\"yellow\",\"clickEvent\":{\"action\":\"open_url\",\"value\":\"https://www.curseforge.com/minecraft/bukkit-plugins/fishrewards\"}},{\"text\":\")\",\"color\":\"gray\",\"clickEvent\":{\"action\":\"open_url\",\"value\":\"https://www.curseforge.com/minecraft/bukkit-plugins/fishrewards\"}},{\"text\":\"\\n\"},{\"text\":\"Don't point out errors in the reviews, write them to us in discord.\",\"color\":\"red\"}]");
            e.setCancelled(true);
        }));
        addSlot(11, new MenuSlot(menu.packs_item, e -> {
            packList.open((Player) e.getWhoClicked());
            e.setCancelled(true);
        }));
        addSlot(13, new MenuSlot(menu.packs_reload, e -> {
            FishController.packList.clear();
            FishController.packList.addAll(StorageCreator.getStorageIns().getPacks());
            e.getWhoClicked().sendMessage(main.getPlugin().getChecker().getParam("plugin-prefix").toString() + ("Pack reloaded"));
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
