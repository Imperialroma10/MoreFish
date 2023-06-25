package ifly.morefish.fishpack;

import com.google.common.base.Function;
import ifly.morefish.datastorage.StorageCreator;
import ifly.morefish.fishpack.pack.CustomPack;
import ifly.morefish.fishpack.pack.DefaultPack;
import ifly.morefish.fishpack.pack.Pack;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class FishMain {

    List<Pack> packList = new ArrayList<>();
    List<FishTask> fishTasks = new ArrayList<>();

    StorageCreator storage;
    public FishMain(StorageCreator storageCreator){
        this.storage = storageCreator;
        setPackList(storage.getStorage().getPacks());
    }

    public Pack getPack(ItemStack itemStack){
        for (Pack p : packList){
            if (p.getChest().isSimilar(itemStack)){
                return p;
            }
        }
        return null;
    }
    public Pack getPack(int customModelData){
        return packList.stream().filter(pack -> pack.getCustomModelData() == customModelData).findFirst().get();
    }

    public void setPackList(List<Pack> packList) {
        this.packList = packList;
    }

    public void init(Player p, Location location){

        Random random = new Random();

        int x = random.nextInt(100);

        int back = 0;

        //packList.sort(Comparator.comparingInt(Pack::getDropChance));



        for (Pack pack: packList){
            if (back <= x && x <= pack.getDropChance()+back){
                fishTasks.add(new FishTask(p, pack, location, this));
                return;
            }
            back += pack.getDropChance();
        }
    }

    public List<Pack> getPackList() {
        return packList;
    }
}
