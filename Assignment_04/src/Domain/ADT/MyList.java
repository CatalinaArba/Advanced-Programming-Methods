package Domain.ADT;

import Exceptions.ADTException;

import java.util.ArrayList;
import java.util.List;

public class MyList<T> implements MyIList<T> {
    List<T> list;

    public MyList() {
        list = new ArrayList<>();
    }

    @Override
    public Boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public void add(T newElement) {
        list.add(newElement);
    }


    @Override
    public T pop() throws ADTException {
        if (list.isEmpty()) throw new ADTException("Error: MyList: The list is Empty!");
        return list.remove(0);
    }

    public String toString() {
        return list.toString();
    }
}
