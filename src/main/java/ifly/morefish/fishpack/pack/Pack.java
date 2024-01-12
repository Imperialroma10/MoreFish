package ifly.morefish.fishpack.pack;


import ifly.morefish.fishpack.Config;
import ifly.morefish.fishpack.pack.reward.RewardAbstract;
import ifly.morefish.fishpack.pack.reward.RewardItem;
import ifly.morefish.gui.helper.ItemCreator;
import ifly.morefish.main;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Pack {

    public String Name;
    String Displayname;
    List<RewardAbstract> rewards;
    int dropChance;
    int customModelData;

    boolean enablepermission;
    ItemStack chest;

    NamespacedKey key;

    public Pack(String name, String displayname, int customModelData) {
        this.Displayname = displayname;
        this.customModelData = customModelData;
        Name = name.replace(".yml", "");
        rewards = new ArrayList<>();
        chest = setItemNBT(ItemCreator.create(Material.CHEST, getDisplayname()));

    }

    public Pack(String name, String displayname, int customModelData, List<RewardAbstract> rwds) {
        this(name, displayname, customModelData);
        rewards = rwds;

    }

    public ItemStack setItemNBT(ItemStack itemStack) {
        if (itemStack.getItemMeta() != null) {
            ItemMeta meta = itemStack.getItemMeta();
            meta.getPersistentDataContainer().set(getKey(), PersistentDataType.STRING, getName());
            itemStack.setItemMeta(meta);
        }
        return itemStack;
    }

    public NamespacedKey getKey() {
        if (this.key == null) {
            this.key = new NamespacedKey(main.mainPlugin, getName());
        }
        return key;
    }

    int freeCountOfItem(PlayerInventory inv, ItemStack is) {
        ItemStack[] contents = inv.getStorageContents();
        int c = 0;
        for (ItemStack stack : contents) {
            if (stack == null) {
                c += is.getMaxStackSize();
                continue;
            }
            if (stack.isSimilar(is)) {
                c += stack.getMaxStackSize() - stack.getAmount();
            }
        }
        return c;
    }

    public boolean enoughSpace(Player p) {
        for (RewardAbstract reward : rewards) {
            if (reward instanceof RewardItem) {
                RewardItem rewardItem = (RewardItem) reward;
                int amount = freeCountOfItem(p.getInventory(), rewardItem.getItem());
                if (amount < rewardItem.getItem().getAmount()) {
                    return false;
                }
            }
        }
        return true;
    }

    public void addReward(RewardAbstract rewardAbstract) {
        this.rewards.add(rewardAbstract);
    }

    public String getDisplayname() {
        return Displayname.replace("$", "§");
    }

    public void setDisplayname(String displayname) {
        Displayname = displayname;
    }

    public int getDropChance() {
        return dropChance;
    }

    public void setDropChance(int dropChance) {
        this.dropChance = dropChance;
    }

    public int getCustomModelData() {
        return customModelData;
    }

    public ItemStack getChest() {
        return chest;
    }

    public void giveReward(Player player) {
        if (isEnablepermission()) {
            if (!(player.hasPermission("*") || player.hasPermission(getPermissionsToOpen()))) {
                player.sendMessage(Config.getMessage("You don't have permission to open"));
                return;
            }
        }
        Random a = new Random();
        for (RewardAbstract reward : rewards) {
            int random = a.nextInt(100);
            if (reward.getChance() == 100) {
                reward.giveReward(player);
            } else {
                if (reward.checkChance(random)) {
                    reward.giveReward(player);
                }
            }
        }
        player.sendMessage(Config.getMessage(Config.getConfig().openpackmessage.replace("[pack]", this.Displayname)));
        ItemStack itemStack = player.getInventory().getItemInMainHand();
        itemStack.setAmount(itemStack.getAmount() - 1);
    }


    public List<RewardAbstract> getRewards() {
        return rewards;
    }

    public void setRewards(List<RewardAbstract> rewards) {
        this.rewards = rewards;
    }

    public String getName() {
        return Name;
    }

    public boolean isEnablepermission() {
        return enablepermission;
    }

    public void setEnablepermission(boolean enablepermission) {
        this.enablepermission = enablepermission;
    }

    public String getPermissionsToOpen() {
        String permission = "fish.pack." + this.getName();
        return permission.replace("_", "");
    }
}
