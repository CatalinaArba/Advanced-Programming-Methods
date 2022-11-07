package Model;

public class Cilindru implements InterfaceForme{
    public int volum;

    //Constructor
    public Cilindru(int v){
        this.volum=v;
    }

    //Interface methods implementations
    public void setV(int volum){
        this.volum=volum;
    }

    public int getV(){
        return this.volum;
    }
    public String toString(){
        return  "Colindru: V="+this.volum;
    }
}
