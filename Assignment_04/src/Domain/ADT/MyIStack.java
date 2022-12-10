package Domain.ADT;

import Exceptions.ADTException;

public interface MyIStack<T> {
    T pop() throws ADTException;

    void push(T newElement);

    boolean isEmpty();


}
