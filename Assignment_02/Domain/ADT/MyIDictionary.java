package Domain.ADT;
import Exceptions.ADTException;

import java.util.Dictionary;
public interface MyIDictionary<K,V> {
    boolean isDefined (K key);
    V lookUp(K key) throws ADTException;
    void put(K key,V value);
    void update(K key,V value) throws ADTException;
    void remove(K key)throws ADTException;
}
