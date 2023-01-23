package Domain.ADT;

import Exceptions.ADTException;

import java.util.List;
public interface MyIList<T> {
    void add(T newElement);

    T pop() throws ADTException;

    Boolean isEmpty();

    List<T> getList();
}
