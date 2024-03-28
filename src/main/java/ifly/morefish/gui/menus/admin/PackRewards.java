package ifly.morefish.gui.menus.admin;

import com.liba.gui.Gui;
import com.liba.gui.ListedGui;
import com.liba.gui.MenuSlot;
import com.liba.gui.buttons.BackButton;
import com.liba.utils.ItemUtil;
import com.liba.utils.chat.ChatAwait;
import ifly.morefish.chatAction.CreateCommandAction;
import ifly.morefish.fishpack.pack.Pack;
import ifly.morefish.fishpack.pack.reward.RewardAbstract;
import ifly.morefish.fishpack.pack.reward.RewardCommand;
import ifly.morefish.fishpack.pack.reward.RewardEntity;
import ifly.morefish.fishpack.pack.reward.RewardItem;
import ifly.morefish.gui.helper.ItemCreator;
import ifly.morefish.gui.menus.admin.editrewards.EditCommand;
import ifly.morefish.gui.menus.admin.editrewards.EditEntity;
import ifly.morefish.gui.menus.admin.editrewards.EditItem;
import ifly.morefish.gui.menus.admin.rewardcreator.EntityReward;
import ifly.morefish.gui.menus.admin.rewardcreator.ItemReward;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PackRewards extends ListedGui {
    Pack pack;
    EntityReward entityReward;

    ItemReward itemReward;

    EditItem editItem = new EditItem(this);
    EditEntity editEntity = new EditEntity(this);
    EditCommand editCommand = new EditCommand(this);

    public PackRewards(Gui gui) {
        super("Pack rewards", 5, new ArrayList<>(), 3, gui);

        List<EntityType> entityTypes = Arrays.stream(EntityType.values()).toList();
        List<EntityType> entityTypeList = new ArrayList<>();
        for (EntityType entityType : entityTypes) {
            if (Material.getMaterial(entityType.name() + "_SPAWN_EGG") != null) {
                entityTypeList.add(entityType);
            }
        }
        entityReward = new EntityReward(entityTypeList, this);
        itemReward = new ItemReward(this);

    }

    @Override
    public void setInventoryItems() {


        for (int i = 0; i < getDataBlockSize(); i++) {
            int id = getDataBlockSize() * getPage() + i;
            if (id < getData().size()) {
                RewardAbstract rewardAbstract = pack.getRewards().get(id);
                int rewardid = pack.getRewards().indexOf(rewardAbstract);
                addSlot(i, new MenuSlot(ItemUtil.addLore(rewardAbstract.getItem().clone(), "§bDrop chance §a" + rewardAbstract.getChance() + "%",
                    "§bShift+Left click to remove reward from pack"), e -> {
                    if (e.isShiftClick()) {
                    pack.getRewards().remove(rewardid);
                    removeSlot(rewardid);
                    open(getOwner().getPlayer(), pack);
                } else {
                    if (e.isLeftClick()) {
                        if (rewardAbstract instanceof RewardItem) {
                            editItem.setItem(rewardAbstract);
                            editItem.open(getOwner(), pack);
                        }
                        if (rewardAbstract instanceof RewardEntity) {
                            editEntity.setRewardEntity((RewardEntity) rewardAbstract);
                            editEntity.open(getOwner());
                        }
                        if (rewardAbstract instanceof RewardCommand) {
                            editCommand.open((Player) e.getWhoClicked(), pack, (RewardCommand) rewardAbstract);
                        }
                    }
                }
                e.setCancelled(true);
                    e.setCancelled(true);
                }));
            }
        }
//        for (int i = 0; i < pack.getRewards().size() && i < getSlots() - 9; i++) {
//            RewardAbstract rewardAbstract = pack.getRewards().get(i);
//            int rewardid = pack.getRewards().indexOf(rewardAbstract);
//            addSlot(i, new MenuSlot(ItemUtil.addLore(rewardAbstract.getItem().clone(), "§bDrop chance §a" + rewardAbstract.getChance() + "%",
//                    "§bShift+Left click to remove reward from pack"), e -> {
//                if (e.isShiftClick()) {
//                    pack.getRewards().remove(rewardid);
//                    removeSlot(rewardid);
//                    open(getOwner().getPlayer(), pack);
//                } else {
//                    if (e.isLeftClick()) {
//                        if (rewardAbstract instanceof RewardItem) {
//                            editItem.setItem(rewardAbstract);
//                            editItem.open(getOwner(), pack);
//                        }
//                        if (rewardAbstract instanceof RewardEntity) {
//                            editEntity.setRewardEntity((RewardEntity) rewardAbstract);
//                            editEntity.open(getOwner());
//                        }
//                        if (rewardAbstract instanceof RewardCommand) {
//
//                        }
//                    }
//                }
//                e.setCancelled(true);
//            }));
//        }

        addSlot(38, new MenuSlot(ItemCreator.create(Material.CRAFTING_TABLE, "§6Add items reward"), e -> {
            itemReward.setPack(pack);
            itemReward.open(getOwner());
            e.setCancelled(true);
        }));

        addSlot(40, new MenuSlot(ItemCreator.create(Material.PAPER, "§6Add command reward", "§7Coming soon", "You can use the standard pack to create an award command."), e -> {
            e.getWhoClicked().closeInventory();
            e.getWhoClicked().sendMessage("§eEnter the command without the \"§a/§e\" symbol, to indicate the player's nickname, enter §a{player}§e in the place where it is indicated.");
            ChatAwait.getInstance().registerAction((Player) e.getWhoClicked(), new CreateCommandAction(pack));
            e.setCancelled(true);
        }));
        addSlot(42, new MenuSlot(ItemCreator.create(Material.ZOMBIE_HEAD, "§6Add entity reward"), e -> {
            entityReward.setPack(pack);
            entityReward.open(getOwner());
            e.setCancelled(true);
        }));
        addSlot(36, new BackButton(new ItemStack(Material.BARRIER),getBackGui(), "back"));

        super.setInventoryItems();
    }


    public void open(Player player, Pack pack) {
        this.pack = pack;
        setData(pack.getRewards());
        super.open(player);
    }
}
