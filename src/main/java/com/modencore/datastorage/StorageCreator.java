package com.modencore.datastorage;

public class StorageCreator {
    private static IStorage storage;

    public StorageCreator() {
        storage = new FileStorage();
    }

    public static IStorage getStorageIns() {

        return storage;
    }

    public IStorage getStorage() {
        return storage;
    }
}
