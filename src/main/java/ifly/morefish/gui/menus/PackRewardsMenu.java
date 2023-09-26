package ifly.morefish.gui.menus;

import ifly.morefish.datastorage.StorageCreator;
import ifly.morefish.fishpack.lang.MenuMsgs;
import ifly.morefish.fishpack.lang.RewardsMenuMsg;
import ifly.morefish.fishpack.pack.Pack;
import ifly.morefish.fishpack.pack.reward.RewardAbstract;
import ifly.morefish.fishpack.pack.reward.RewardCommand;
import ifly.morefish.fishpack.pack.reward.RewardEntity;
import ifly.morefish.fishpack.pack.reward.RewardItem;
import ifly.morefish.gui.Menu;
import ifly.morefish.gui.PlayerMenuUtil;
import ifly.morefish.gui.anvil.AnvilController;
import ifly.morefish.gui.anvil.actions.CreateNewComandReward;
import ifly.morefish.gui.anvil.actions.EditCommand;
import ifly.morefish.gui.helper.ItemCreator;
import ifly.morefish.gui.menus.editrewards.EditEntity;
import ifly.morefish.gui.menus.editrewards.EditItem;
import ifly.morefish.gui.menus.rewardcreator.EntityReward;
import ifly.morefish.gui.menus.rewardcreator.ItemReward;
import ifly.morefish.main;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;

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
                if (rewardAbstract instanceof RewardItem){
                    new EditItem(getPlayerMenuUtil(), (RewardItem) rewardAbstract, pack).open();
                }
                if (rewardAbstract instanceof RewardEntity){
                    new EditEntity(getPlayerMenuUtil(), (RewardEntity) rewardAbstract, pack).open();
                }
                if (rewardAbstract instanceof RewardCommand){

                    AnvilGUI.Builder builder = new AnvilGUI.Builder();
                    ItemStack paper = new ItemStack(Material.PAPER);
                    ItemMeta meta = paper.getItemMeta();
                    meta.setDisplayName(((RewardCommand) rewardAbstract).getCommand());
                    paper.setItemMeta(meta);
                    builder.itemLeft(paper);
                    builder.
                            plugin(main.mainPlugin).
                            onClick((slot, stateSnapshot) -> {
                                if (slot == AnvilGUI.Slot.OUTPUT){
                                    stateSnapshot.getPlayer().sendMessage(stateSnapshot.getText());
                                    ((RewardCommand) rewardAbstract).setCommand(stateSnapshot.getText());
                                    stateSnapshot.getPlayer().closeInventory();
                                }
                                return Collections.emptyList();
                            }).
                            open(getPlayerMenuUtil().getOwner());


                    //AnvilController.createAnvil((Player) e.getWhoClicked(), new EditCommand((Player) e.getWhoClicked(), pack, (RewardCommand) rewardAbstract));
                }
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

                AnvilGUI.Builder builder = new AnvilGUI.Builder();
                ItemStack paper = new ItemStack(Material.PAPER);
                ItemMeta meta = paper.getItemMeta();
                meta.setDisplayName("command");
                paper.setItemMeta(meta);
                builder.itemLeft(paper);
                builder.
                        plugin(main.mainPlugin).
                        onClick((slot, stateSnapshot) -> {
                            if (slot == AnvilGUI.Slot.OUTPUT){
                                pack.getRewards().add(new RewardCommand(stateSnapshot.getText()));
                                stateSnapshot.getPlayer().closeInventory();
                            }
                            return Collections.emptyList();
                        }).
                        open(getPlayerMenuUtil().getOwner());


                //AnvilController.createAnvil(getPlayerMenuUtil().getOwner(),new CreateNewComandReward(getPlayerMenuUtil().getOwner(), pack));
            }

            e.setCancelled(true);
        }
    }

    @Override
    public void setMenuItems() {
        int i = 0;
        for (RewardAbstract rewardAbstract : pack.getRewards()){
            if (i < getSlots()*9-9){
                menu.makeLore(rewardAbstract.getItem(), rewardAbstract.getChance());
                getInventory().setItem(i, rewardAbstract.getItem());
            }

            i++;
        }

        getInventory().setItem(29, menu.additem);
        getInventory().setItem(31, menu.addentity);
        getInventory().setItem(33, menu.addcommand);

        getInventory().setItem(getSlots()*9-1, menu.save_item);
        getInventory().setItem(getSlots()*9-9, menu.back_item);
    }
}
