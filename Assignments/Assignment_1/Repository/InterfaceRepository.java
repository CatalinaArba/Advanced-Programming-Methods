package Repository;
import Model.InterfaceForme;
import Exception.LoggerUtil;
import Exception.MyException;
public interface InterfaceRepository {
    void add(InterfaceForme obj) throws MyException;
    //Object[] findAll(int v);
    InterfaceForme[] findAll2(int v)throws MyException;
    //Object[] getAll();

    InterfaceForme[] getAll2();
}
