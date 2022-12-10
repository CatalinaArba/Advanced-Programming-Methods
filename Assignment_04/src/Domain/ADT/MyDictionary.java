package Domain.ADT;

import Exceptions.ADTException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MyDictionary<K, V> implements MyIDictionary<K, V> {
    HashMap<K, V> dictionary;

    public MyDictionary() {
        dictionary = new HashMap<>();
    }

    @Override
    public boolean isDefined(K key) {
        return dictionary.containsKey(key);
    }

    @Override
    public V lookUp(K key) throws ADTException {
        if (!isDefined(key))
            throw new ADTException("Error:MyDictionary: The key is not defined!");
        return dictionary.get(key);
    }

    @Override
    public void put(K key, V value) {
        dictionary.put(key, value);
    }

    @Override
    public void update(K key, V value) throws ADTException {
        if (!isDefined(key))
            throw new ADTException("Error:MyDictionary: The key is not defined!");
        dictionary.put(key, value);
    }

    @Override
    public void remove(K key) throws ADTException {
        if (!isDefined(key))
            throw new ADTException("Error:MyDictionary: The key is not defined!");
        dictionary.remove(key);
    }

    @Override
    public Collection<V> values() {
        return this.dictionary.values();
    }

    @Override
    public String toString() {
        return this.dictionary.toString();
    }

    @Override
    public Map<K, V> getContent() {
        return  dictionary;
    }
}
