package ifly.morefish.datastorage;


import ifly.morefish.fishpack.pack.Pack;
import java.util.List;

public interface IStorage {
    List<Pack> getPacks();
    void addReward(Pack pack);
    Pack UpdatePack(Pack pack);

}
