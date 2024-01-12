package ifly.morefish.gui.menus.admin;

import ifly.imperial.gui.Gui;
import ifly.imperial.gui.MenuSlot;
import ifly.imperial.gui.buttons.BackButton;
import ifly.imperial.utils.Debug;
import ifly.imperial.utils.ItemUtil;
import ifly.morefish.fishpack.pack.Pack;
import ifly.morefish.fishpack.pack.reward.RewardAbstract;
import ifly.morefish.fishpack.pack.reward.RewardCommand;
import ifly.morefish.fishpack.pack.reward.RewardEntity;
import ifly.morefish.fishpack.pack.reward.RewardItem;
import ifly.morefish.gui.helper.ItemCreator;
import ifly.morefish.gui.menus.admin.editrewards.EditEntity;
import ifly.morefish.gui.menus.admin.editrewards.EditItem;
import ifly.morefish.gui.menus.admin.rewardcreator.EntityReward;
import ifly.morefish.gui.menus.admin.rewardcreator.ItemReward;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PackRewards extends Gui {
    Pack pack;
    EntityReward entityReward;

    ItemReward itemReward;

    EditItem editItem = new EditItem(this);
    EditEntity editEntity = new EditEntity(this);

    public PackRewards(Gui gui) {
        super("Pack rewards", 4, gui);

        List<EntityType> entityTypes = Arrays.stream(EntityType.values()).toList();
        List<EntityType> entityTypeList = new ArrayList<>();
        for (EntityType entityType : entityTypes) {
            if (Material.getMaterial(entityType.name() + "_SPAWN_EGG") != null) {
                entityTypeList.add(entityType);
            }
        }
        entityReward = new EntityReward(entityTypeList,this);
        itemReward = new ItemReward(this);

    }

    @Override
    public void setInventoryItems() {

        for (int i = 0; i < pack.getRewards().size() && i< getSlots()-9 ; i++){
            RewardAbstract rewardAbstract = pack.getRewards().get(i);
            int rewardid = pack.getRewards().indexOf(rewardAbstract);
            addSlot(i, new MenuSlot(ItemUtil.addLore(rewardAbstract.getItem().clone(), "§eDrop chance §b"+rewardAbstract.getChance()+"%",
                    "Shift+Left click to remove reward from pack"), e->{
                if (e.isShiftClick()){
                    pack.getRewards().remove(rewardid);
                    open(getOwner().getPlayer(),pack);
                }else{
                    if (e.isLeftClick()){
                        if (rewardAbstract instanceof RewardItem){
                            editItem.setItem(rewardAbstract);
                            editItem.open(getOwner(), pack);
                        }
                        if (rewardAbstract instanceof RewardEntity){
                            editEntity.setRewardEntity((RewardEntity) rewardAbstract);
                            editEntity.open(getOwner());
                        }
                        if (rewardAbstract instanceof RewardCommand){

                        }
                    }
                }
                e.setCancelled(true);
            }));
        }

        addSlot(29, new MenuSlot(ItemCreator.create(Material.CRAFTING_TABLE, "§6Add items reward"), e->{
            itemReward.setPack(pack);
            itemReward.open(getOwner());
            e.setCancelled(true);
        }));

        addSlot(31, new MenuSlot(ItemCreator.create(Material.PAPER, "§6Add command reward","§7Coming soon","You can use the standard pack to create an award command."), e->{

            e.setCancelled(true);
        }));
        addSlot(33, new MenuSlot(ItemCreator.create(Material.ZOMBIE_HEAD, "§6Add entity reward"), e->{
            entityReward.setPack(pack);
            entityReward.open(getOwner());
            e.setCancelled(true);
        }));
        addSlot(27, new BackButton(ItemCreator.create(Material.BARRIER, "Back"), getBackGui()));

    }



    public void open(Player player, Pack pack){
        this.pack = pack;
        super.open(player);
    }
}
