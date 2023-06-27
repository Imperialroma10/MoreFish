package ifly.morefish.datastorage;

public class StorageCreator {
    FileStorage storage;

    public StorageCreator() {
        storage = new FileStorage();
    }

    public FileStorage getStorage() {
        return storage;
    }
}
