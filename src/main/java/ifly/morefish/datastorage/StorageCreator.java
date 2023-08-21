package ifly.morefish.datastorage;

public class StorageCreator {
    private static IStorage storage;

    public StorageCreator() {
        storage = new FileStorage();
    }

    public IStorage getStorage() {
        return storage;
    }

    public static IStorage getStorageIns(){
        return storage;
    }
}
