package ifly.morefish.fishpack.pack;


import com.liba.utils.HeadCreator;
import ifly.morefish.fishpack.Config;
import ifly.morefish.fishpack.pack.reward.RewardAbstract;
import ifly.morefish.fishpack.pack.reward.RewardItem;
import ifly.morefish.main;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Pack {

    String Name;
    String Displayname;
    List<RewardAbstract> rewards;
    int dropChance;
    int customModelData;

    String enablepermission;
    ItemStack chest;
    NamespacedKey key;

    public Pack(String name, String displayname, int customModelData, ItemStack itemStack, String skullString) {
        this.Displayname = displayname;
        this.customModelData = customModelData;
        this.Name = name.replace(".yml", "");
        this.rewards = new ArrayList<>();
        this.chest = itemStack;
        this.key = new NamespacedKey(main.mainPlugin, getName());


        setDataContainer();
        if (chest.getItemMeta() instanceof SkullMeta) {
            HeadCreator.setHeadTexture(chest, skullString);
        }
    }

    public void setDataContainer() {
        ItemMeta meta = chest.getItemMeta();
        meta.setDisplayName(getDisplayname());
        meta.getPersistentDataContainer().set(getKey(), PersistentDataType.STRING, getName());
        chest.setItemMeta(meta);
    }

    public NamespacedKey getKey() {
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

    public void setChest(ItemStack chest) {
        this.chest = chest;
    }

    public void giveReward(Player player) {
        if (isEnablepermission()) {
            if (!(player.hasPermission("*") || player.hasPermission(getEnablepermission()))) {
                player.sendMessage(Config.getMessage("You don't have permission to open"));
                return;
            }
        }

        Random a = new Random();

        for (int i = 0; i < 1; i++) {
            int chance = a.nextInt(Math.max(getAllRewardsChance(), 100));
            int backchance = 0;
            for (RewardAbstract reward : rewards) {

                if (backchance <= chance && chance <= backchance + reward.getChance()) {
                    reward.giveReward(player);
                    break;
                }
                backchance += reward.getChance();
            }


        }
        player.sendMessage(Config.getMessage(Config.getConfig().openpackmessage.replace("[pack]", getDisplayname())));
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

    public void setName(String name) {
        Name = name;
        setDataContainer();
    }

    public boolean isEnablepermission() {
        return enablepermission != null && !enablepermission.isEmpty();
    }

    public String getEnablepermission() {
        return enablepermission;
    }

    public void setEnablepermission(String enablepermission) {
        this.enablepermission = enablepermission;
    }

    public int getAllRewardsChance() {
        int chance = 0;
        for (RewardAbstract rewardAbstract : getRewards()) {
            chance += rewardAbstract.getChance();
        }
        return chance;
    }
}
