package de.nikolas.schoolbot.storage;

public class Storage<T> {
    public T value;

    public T getValue() {
        return value;
    }

    void setValue(Mediator<T> mediator, String storageName, T value) {
        this.value = value;
        mediator.notifyObservers(storageName);
    }

}
