package Domain.ADT;
import java.util.List;
import Exceptions.*;
public interface MyIList<T> {
    void add (T newElement);
    T pop() throws ADTException;
    Boolean isEmpty();
}
