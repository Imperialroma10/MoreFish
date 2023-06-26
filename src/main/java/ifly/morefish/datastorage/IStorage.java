package ifly.morefish.datastorage;


import ifly.morefish.fishpack.pack.Pack;

import java.util.List;

public interface IStorage {
    void getPacks();

    void savePacks(List<Pack> packs);

}
