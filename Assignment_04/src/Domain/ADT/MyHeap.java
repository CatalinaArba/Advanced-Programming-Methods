package Domain.ADT;

import Domain.Value.Value;
import Exceptions.ADTException;

import java.util.HashMap;

public class MyHeap implements MyIHeap {
    HashMap<Integer, Value> heap;
    Integer freeLocationValue;

    public MyHeap() {
        this.heap = new HashMap<>();
        freeLocationValue = 1;
    }

    @Override
    public int getFreeValue() {
        return freeLocationValue;
    }

    @Override
    public HashMap<Integer, Value> getContent() {
        return heap;
    }

    @Override
    public void setHeap(HashMap<Integer, Value> newHeap) {
        heap = newHeap;
    }

    @Override
    public int add(Value value) {
        heap.put(freeLocationValue, value);
        Integer returnedLocation = freeLocationValue;
        while (freeLocationValue == 0 || heap.containsKey(freeLocationValue))
            freeLocationValue += 1;
        return returnedLocation;

    }

    @Override
    public boolean containsKey(Integer position) {
        return this.heap.containsKey(position);
    }

    @Override
    public void remove(Integer key) throws ADTException {
        if (!containsKey(key))
            throw new ADTException("Error: MyHeap: Removing a key that is not in the heap: " + key);
        freeLocationValue = key;
        heap.remove(key);
    }

    @Override
    public void update(Integer position, Value value) throws ADTException {
        if (!heap.containsKey(position))
            throw new ADTException("Error: MyHeap: Updating a position that is not in the heap: " + position);
        heap.put(position, value);
    }

    @Override
    public Value get(Integer position) throws ADTException {
        if (!heap.containsKey(position))
            throw new ADTException("Error: MyHeap: Updating a position that is not in the heap: " + position);
        return heap.get(position);
    }

    @Override
    public String toString() {
        return heap.toString();
    }
}
