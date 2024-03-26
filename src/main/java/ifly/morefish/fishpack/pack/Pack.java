package ifly.morefish.fishpack.pack;




import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.liba.utils.Debug;
import com.liba.utils.HeadCreator;
import ifly.morefish.fishpack.Config;
import ifly.morefish.fishpack.pack.reward.RewardAbstract;
import ifly.morefish.fishpack.pack.reward.RewardItem;

import ifly.morefish.main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.profile.PlayerProfile;
import org.bukkit.profile.PlayerTextures;

import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.*;


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
        if (chest.getItemMeta() instanceof SkullMeta){
            HeadCreator.setHeadTexture(chest, skullString);
        }
    }

    public void setDataContainer(){
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
            if (reward instanceof RewardItem rewardItem) {
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
            if (!(player.hasPermission("*") || player.hasPermission(getEnablepermission()))) {
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
                    break;
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
        return enablepermission != null && !enablepermission.isEmpty();
    }

    public String getEnablepermission() {
        return enablepermission;
    }

    public void setEnablepermission(String enablepermission) {
        this.enablepermission = enablepermission;
    }


    public void setChest(ItemStack chest) {
        this.chest = chest;
    }

    public void setName(String name) {
        Name = name;
        setDataContainer();
    }

}
