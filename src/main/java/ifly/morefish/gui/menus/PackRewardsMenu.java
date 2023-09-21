package ifly.morefish.gui.menus;

import ifly.morefish.datastorage.StorageCreator;
import ifly.morefish.fishpack.lang.MenuMsgs;
import ifly.morefish.fishpack.lang.RewardsMenuMsg;
import ifly.morefish.fishpack.pack.Pack;
import ifly.morefish.fishpack.pack.reward.RewardAbstract;
import ifly.morefish.gui.Menu;
import ifly.morefish.gui.PlayerMenuUtil;
import ifly.morefish.gui.anvil.AnvilController;
import ifly.morefish.gui.anvil.actions.CreateNewComandReward;
import ifly.morefish.gui.helper.ItemCreator;
import ifly.morefish.gui.menus.rewardcreator.EntityReward;
import ifly.morefish.gui.menus.rewardcreator.ItemReward;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class PackRewardsMenu extends Menu {
    private final RewardsMenuMsg menu;

    int id = 0;



    Pack pack;
    public PackRewardsMenu(PlayerMenuUtil playerMenuUtil, Pack pack) {
        super(playerMenuUtil);
        menu = MenuMsgs.get().RewardsMenu;
        this.pack = pack;

    }
    @Override
    public String getMenuName() {
        return menu.title.replace("{packname}", pack.getDisplayname());
    }


    @Override
    public int getSlots() {
        return 4;
    }

    @Override
    public void handleInventoryClick(InventoryClickEvent e) {

        if (e.getSlot() <= getSlots()*9-9){
            if (e.getSlot() < pack.getRewards().size()){
                RewardAbstract rewardAbstract = pack.getRewards().get(e.getSlot());

                if (e.isShiftClick() && e.isLeftClick()){
                    pack.getRewards().remove(rewardAbstract);
                    this.open();
                }
            }
        }
        if (e.getSlot() >= getSlots()*9-9 && e.getSlot() <= getSlots()*9){

            if (e.getSlot() == getSlots()*9-9){
                new EditMenu(getPlayerMenuUtil(),false , pack).open();
            }
            if (e.getSlot() == getSlots()*9-1){
                StorageCreator.getStorageIns().Save(pack);
            }

            if (e.getSlot() == 29){
                new ItemReward(getPlayerMenuUtil(), pack).open();
            }
            if (e.getSlot() == 31){
                new EntityReward(getPlayerMenuUtil(), pack).open();
            }
            if (e.getSlot() == 33){
                AnvilController.createAnvil(getPlayerMenuUtil().getOwner(),new CreateNewComandReward(getPlayerMenuUtil().getOwner(), pack));
            }

            e.setCancelled(true);
        }
    }

    @Override
    public void setMenuItems() {
        int i = 0;
        for (RewardAbstract rewardAbstract : pack.getRewards()){
            if (i < getSlots()*9-9){
                this.getInventory().setItem(i, rewardAbstract.getItem());
            }

            i++;
        }

        getInventory().setItem(29, ItemCreator.create(Material.ITEM_FRAME, "Add an item"));
        getInventory().setItem(31, ItemCreator.create(Material.ZOMBIE_HEAD, "Add entity"));
        getInventory().setItem(33, ItemCreator.create(Material.PAPER, "Add command"));

        getInventory().setItem(getSlots()*9-1, menu.save_item);
        getInventory().setItem(getSlots()*9-9, menu.back_item);
    }
}
