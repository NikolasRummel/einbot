package de.nikolas.schoolbot.storage;

import java.util.HashMap;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

public class Mediator<T> {

    private final HashMap<String, Storage<T>> storageMap = new HashMap<>();
    private final CopyOnWriteArrayList<Consumer<String>> observers = new CopyOnWriteArrayList<>();

    public void setValue(String storageName, T value) {
        Storage storage = storageMap.computeIfAbsent(storageName, name -> new Storage<>());
        storage.setValue(this, storageName, value);
    }

    public void setValue(int storageName, T value) {
        Storage storage = storageMap.computeIfAbsent(String.valueOf(storageName), name -> new Storage<>());
        storage.setValue(this, String.valueOf(storageName), value);
    }

    public Optional<T> getValue(String storageName) {
        return Optional.ofNullable(storageMap.get(storageName)).map(Storage::getValue);
    }

    public void addObserver(String storageName, Runnable observer) {
        this.observers.add(eventName -> {
           if(eventName.equals(storageName)) {
               observer.run();
           }
        });
    }

    public void notifyObservers(String eventName) {
        this.observers.forEach(observers -> observers.accept(eventName));
    }

    public HashMap<String, Storage<T>> getStorageMap() {
        return storageMap;
    }
}
