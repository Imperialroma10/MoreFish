package ifly.morefish.fishpack;

import ifly.morefish.datastorage.IStorage;
import ifly.morefish.datastorage.StorageCreator;
import ifly.morefish.fishpack.pack.Pack;
import ifly.morefish.main;
import ifly.morefish.stats.PlayerStatistic;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Random;

public class FishController {

    static public List<Pack> packList;

    StorageCreator storage;
    static public PlayerStatistic playerStatistic;

    public FishController(StorageCreator storageCreator) {
        playerStatistic = new PlayerStatistic(this);
        this.storage = storageCreator;
        List<Pack> packs = storage.getStorage().getPacks();

        setPackList(packs);
        Bukkit.getLogger().info("[" + main.mainPlugin.getDescription().getName() + "] loaded " + packs.size() + " packs");
    }

    public Pack getPack(ItemStack itemStack) {
        for (Pack p : packList) {
            ItemStack item = p.getChest();
            if (item.isSimilar(itemStack)) {
                return p;
            }
        }
        return null;
    }

    public boolean Reload(String packname) {
        for (int i = 0; i < packList.size(); i++) {
            if (packList.get(i).Name.equals(packname)) {
                IStorage fs = storage.getStorage();
                Pack pack = fs.laodFromFile(packList.get(i));

                if (pack == null) {
                    return false;
                }
                packList.set(i, pack);
                return true;
            }
        }
        return false;
    }

    public Pack getPack(int customModelData) {
        return packList.stream().filter(pack -> pack.getCustomModelData() == customModelData).findFirst().orElseGet(null);
    }

    public void init(Player p, Location location) {

        Random random = new Random();

        int chance = packList.stream().mapToInt(Pack::getDropChance).sum();

        if (chance <= 100) {
            chance = 100;
        }
        int x = random.nextInt(chance);
        int back = 0;
        for (Pack pack : packList) {
            if (pack.isEnablepermission()){
                if (!(p.hasPermission("*") || p.hasPermission(pack.getPermissionsToOpen()))){
                    continue;
                }
            }

            if (back <= x && x <= pack.getDropChance() + back) {
                new FishTask(p, pack, location);
                playerStatistic.addCaughtPacks();
                return;
            }
            back += pack.getDropChance();
        }
    }

    public IStorage getStorage() {
        return storage.getStorage();
    }

    public List<Pack> getPackList() {
        return packList;
    }

    public void setPackList(List<Pack> packList) {
        FishController.packList = packList;
    }

    public void saveALlPacks(){
        for (Pack pack : packList){
            getStorage().update(pack);
        }
    }

    public PlayerStatistic getPlayerStatistic() {
        return playerStatistic;
    }
}
