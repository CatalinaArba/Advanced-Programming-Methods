package Model;

public class Sfera implements InterfaceForme{
    public int volum;

    //Constructor
    public Sfera(int v){
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
        return  "Sfera: V="+this.volum;
    }
}
