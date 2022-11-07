package Repository;

import Model.InterfaceForme;
import Exception.LoggerUtil;
import Exception.MyException;

public class Repository implements InterfaceRepository{
    private InterfaceForme[] arr;
    private int sizeOfArray;
    private int maxSize;

    public Repository(int size)throws MyException{
        if (size<=0)
            throw new MyException("Error: Repository: Invalid value for repository initialization!");
        arr=new InterfaceForme[size];
        sizeOfArray=0;
        maxSize=size;
    }

    @Override
    public void add (InterfaceForme obj) throws MyException {
        if (obj.getV()<0) {
            throw new MyException("Error: an negative value can't be added!");
        }
        this.arr[this.sizeOfArray]=obj;
        this.sizeOfArray+=1;
    }

    /*@Override
    public Object[] findAll(int v) {
        InterfaceForme[] obj=new InterfaceForme[this.maxSize];
        int nr=0;
        for(InterfaceForme e:arr)
        {
            if(e.getV()>v){
                obj[nr]=e;
                nr+=1;
            }
        }
        return obj;
    }*/

    @Override
    public InterfaceForme[] findAll2(int v)throws MyException{
        if (v<0) {
            throw new MyException("Error: an negative value can't be found!");
        }

        InterfaceForme[] obj=new InterfaceForme[this.maxSize];
        int nr=0;
        for(InterfaceForme e:arr)
        {
            if(e!=null && e.getV()>v){
                obj[nr]=e;
                nr+=1;
            }
        }
        return obj;
    }

   /* @Override
    public Object[] getAll() {
        return this.arr;
    }*/

    @Override
    public InterfaceForme[] getAll2() {
        return arr;
    }
}
