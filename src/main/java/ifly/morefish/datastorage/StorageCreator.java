package ifly.morefish.datastorage;

public class StorageCreator {
    IStorage storage;

    public StorageCreator() {
        storage = new FileStorage();
    }

    public IStorage getStorage() {
        return storage;
    }
}
