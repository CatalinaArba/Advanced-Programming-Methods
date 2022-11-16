package Domain.Type;
import Domain.Value.*;
public class IntType implements Type{
    @Override
    public boolean equals(Object obj){
        if (obj == null)
            return false;
        if (obj instanceof  IntType)
            return true;
        else
            return false;
    }

    @Override
    public Value defaultValue(){
        return new IntValue(0);
    }
    public String toString() {
        return "int";}
}