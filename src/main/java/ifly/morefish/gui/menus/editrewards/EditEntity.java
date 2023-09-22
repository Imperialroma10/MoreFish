package ifly.morefish.gui.menus.editrewards;

import ifly.morefish.fishpack.pack.Pack;
import ifly.morefish.fishpack.pack.reward.RewardEntity;
import ifly.morefish.gui.Menu;
import ifly.morefish.gui.PlayerMenuUtil;
import ifly.morefish.gui.helper.ItemCreator;
import ifly.morefish.gui.menus.EditMenu;
import ifly.morefish.gui.menus.PackRewardsMenu;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;

public class EditEntity extends Menu {
    RewardEntity entity;
    Pack pack;

    public EditEntity(PlayerMenuUtil playerMenuUtil, RewardEntity entity, Pack pack) {
        super(playerMenuUtil);
        this.entity = entity;
        this.pack = pack;
    }


    @Override
    public String getMenuName() {
        return "Edit entyty";
    }

    @Override
    public int getSlots() {
        return 3;
    }

    @Override
    public void handleInventoryClick(InventoryClickEvent e) {
        if (e.getSlot() == 11){
            if (e.isLeftClick()){
                if (entity.getAmount()+1 <= 64){
                    entity.setAmount(entity.getAmount()+1);
                }
            }
            if (e.isRightClick()){
                if (entity.getAmount()-1 >= 0){
                    entity.setAmount(entity.getAmount()-1);
                }
            }
            setMenuItems();
        }
        if (e.getSlot() == 15){
            if (e.isLeftClick()){
                if (entity.getChance()+ 5 <= 100){
                    entity.setChance(entity.getChance()+5);
                }else{
                    entity.setChance(100);
                }
            }
            if (e.isRightClick()){
                if (entity.getChance() -5 >= 0){
                    entity.setChance(entity.getChance()-5);
                }else{
                    entity.setChance(0);
                }
            }
            setMenuItems();
        }
        if (e.getSlot() == getSlots()*9-9){
            new PackRewardsMenu(getPlayerMenuUtil(), pack).open();
        }
        e.setCancelled(true);
    }

    @Override
    public void setMenuItems() {

        getInventory().setItem(11, ItemCreator.replace(entity.getItem(), "entity count "+entity.getAmount(),entity.getAmount(),
                "left click +1 "+entity.getEntityType(),
                "right click -1 "+ entity.getEntityType()));
        getInventory().setItem(15, ItemCreator.create(Material.COMPASS, entity.getEntityType().name().toLowerCase() +" entity spawn chance "+entity.getChance()+"%"));
        getInventory().setItem(getSlots()*9-9, ItemCreator.create(Material.BARRIER, "Back"));
    }
}
