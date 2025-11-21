package com.modencore.datastorage;


import com.modencore.fishpack.pack.Pack;

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
