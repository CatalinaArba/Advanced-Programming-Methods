package Controller;

import Model.Cilindru;
import Model.Cub;
import Model.InterfaceForme;
import Model.Sfera;
import Repository.InterfaceRepository;
import Exception.LoggerUtil;
import Exception.MyException;

import java.util.Objects;

public class Controller {
    private InterfaceRepository repo;

    public Controller(InterfaceRepository r){
        repo=r;
    }

    public void add(String type,int value)throws MyException {
        if(Objects.equals(type,"sfera")){
            Sfera obj=new Sfera(value);
            repo.add(obj);
        }
        else if(Objects.equals(type,"cilindru")){
            Cilindru obj=new Cilindru(value);
            repo.add(obj);
        }
        else if(Objects.equals(type,"cub")){
            Cub obj=new Cub(value);
            repo.add(obj);
        }
        else
            throw new MyException("Error: Controller: invalid data type!");

    }

    /*public Object[] getAll(){
        return repo.getAll();
    }*/
    public InterfaceForme[] getAll(){
        return repo.getAll2();
    }

    /*public Object[] findAll(int v){
        return repo.findAll(v);
    }*/

    public InterfaceForme[] findAll(int v) throws MyException{
        return repo.findAll2(v);
    }

}
