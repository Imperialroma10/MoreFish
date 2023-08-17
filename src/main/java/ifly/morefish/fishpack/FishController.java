package ifly.morefish.fishpack;

import ifly.morefish.datastorage.FileStorage;
import ifly.morefish.datastorage.StorageCreator;
import ifly.morefish.fishpack.lang.Lang;
import ifly.morefish.fishpack.pack.Pack;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class FishController {

    List<Pack> packList;

    StorageCreator storage;
    public FishController(StorageCreator storageCreator){
        this.storage = storageCreator;
       List<Pack> packs = storage.getStorage().getPacks();
        setPackList(packs);
        Bukkit.getLogger().info(Lang.getMessage("loaded "+ packs.size() + " packs"));
    }

    public Pack getPack(ItemStack itemStack){
        for (Pack p : packList){
            ItemStack item = p.getChest();
            if (item.isSimilar(itemStack)){
                return p;
            }
        }
        return null;
    }
    public boolean Reload(String packname)
    {
        for(int i = 0; i < packList.size(); i++) {
            if(packList.get(i).Name.equals(packname)) {
                FileStorage fs = storage.getStorage();
                Pack pack = fs.UpdatePack(packList.get(i));

                if(pack == null) { return false; }
                packList.set(i, pack);
                return true;
            }
        }
        return false;
    }

    public Pack getPack(int customModelData){
        return packList.stream().filter(pack -> pack.getCustomModelData() == customModelData).findFirst().orElseGet(null);
    }

    public void setPackList(List<Pack> packList) {
        this.packList = packList;
    }

    public void init(Player p, Location location){

        Random random = new Random();

        int x = random.nextInt(100);

        int back = 0;

        for (Pack pack: packList){
            if (back <= x && x <= pack.getDropChance()+back){
               new FishTask(p, pack, location);
                return;
            }
            back += pack.getDropChance();
        }
    }

    public FileStorage getStorage()
    {
        return storage.getStorage();
    }

    public List<Pack> getPackList() {
        return packList;
    }
}
