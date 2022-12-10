package Domain.ADT;

import Exceptions.ADTException;

public interface MyIList<T> {
    void add(T newElement);

    T pop() throws ADTException;

    Boolean isEmpty();
}
