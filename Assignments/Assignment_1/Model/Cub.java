package Model;

public class Cub implements InterfaceForme{
    public int volum;

    //Constructor
    public Cub(int v){
        this.volum=v;
    }

    public void setV(int volum){
        this.volum=volum;
    }

    public int getV(){
        return this.volum;
    }
    public String toString(){
        return  "Cub: V="+this.volum;
    }
}
