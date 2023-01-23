package Domain.ADT;

import Exceptions.ADTException;
import Domain.Value.Value;

import java.util.HashMap;
import java.util.Set;

public interface MyIHeap {
    int getFreeValue();

    HashMap<Integer, Value> getContent();

    void setHeap(HashMap<Integer, Value> newHeap);

    int add(Value value);

    void remove(Integer key) throws ADTException;

    void update(Integer position, Value value) throws ADTException;

    boolean containsKey(Integer position);

    Value get(Integer position) throws ADTException;

    Set<Integer> keySet();
}
