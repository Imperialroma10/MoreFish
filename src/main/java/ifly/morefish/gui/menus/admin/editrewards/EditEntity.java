package ifly.morefish.gui.menus.admin.editrewards;

import ifly.imperial.gui.Gui;
import ifly.imperial.gui.MenuSlot;
import ifly.imperial.gui.buttons.BackButton;
import ifly.imperial.utils.Debug;
import ifly.imperial.utils.ItemUtil;
import ifly.morefish.fishpack.pack.reward.RewardEntity;
import ifly.morefish.gui.helper.ItemCreator;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class EditEntity extends Gui {
    RewardEntity rewardEntity;

    public EditEntity(Gui backGui) {
        super("Edit entity reward", 1, backGui);
    }

    @Override
    public void setInventoryItems() {

        String message = "§aNumber of entities§f: §b{count}";
        addSlot(2, new MenuSlot(ItemCreator.create(rewardEntity.getItem().getType(), message.replace("{count}", rewardEntity.getAmount()+""),
                "§6Left click to add §b§l1 §6unit",
                "§6Right-click to remove §b§l1 §6unit"), e->{
            if (e.isLeftClick()){
                if (rewardEntity.getAmount() +1 <= 64){
                    rewardEntity.setAmount(rewardEntity.getAmount() +1);
                }
            }
            if (e.isRightClick()){
                if (rewardEntity.getAmount() -1 >= 1){
                    rewardEntity.setAmount(rewardEntity.getAmount() -1);
                }
            }
            setInventoryItems();
            e.setCancelled(true);
        }));

        String itemessage  = "§aChance of entity spawning §b{chance} %";

        addSlot(4, new MenuSlot(ItemCreator.create(Material.REDSTONE_BLOCK, itemessage.replace("{chance}", rewardEntity.getChance()+""),
                "§6Left click to add §b§l5§b%",
                      "§6Right click to remove §b§l5§b%"), e->{

            if (e.isLeftClick()){
                if (rewardEntity.getChance() +5 <= 100){
                    rewardEntity.setChance(rewardEntity.getChance() +5);
                }
            }
            if (e.isRightClick()){
                if (rewardEntity.getChance() -5 >= 0){
                    rewardEntity.setChance(rewardEntity.getChance() -5);
                }
            }

            setInventoryItems();
            e.setCancelled(true);
        }));


        addSlot(getSlots()-9, new BackButton(ItemCreator.create(Material.BARRIER, "Back"), getBackGui()));
    }

    public void setRewardEntity(RewardEntity rewardEntity) {
        this.rewardEntity = rewardEntity;
    }
}
