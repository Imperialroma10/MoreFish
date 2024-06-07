package ifly.morefish.datastorage;


import ifly.morefish.fishpack.pack.Pack;

import java.util.List;

public interface IStorage {
    List<Pack> getPacks();

    void addNewPack(Pack pack);

    void Save(Pack pack);

    void Save(Pack pack, boolean isnew);

    //Pack laodFromFile(Pack pack);

    boolean removePack(Pack pack);

    void update(Pack pack);
}
