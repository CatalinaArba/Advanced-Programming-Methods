package Domain.ADT;

import Domain.Value.Value;
import Exceptions.ADTException;

import java.util.HashMap;

public interface MyIHeap {
    int getFreeValue();

    HashMap<Integer, Value> getContent();

    void setHeap(HashMap<Integer, Value> newHeap);

    int add(Value value);

    void remove(Integer key) throws ADTException;

    void update(Integer position, Value value) throws ADTException;

    boolean containsKey(Integer position);

    Value get(Integer position) throws ADTException;


}
