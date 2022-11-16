package Domain.Type;

import Domain.Value.IntValue;
import Domain.Value.*;


public class BoolType implements Type {
    @Override
    public boolean equals(Object obj) {
        if (obj==null)
            return false;
        if (obj instanceof  BoolType)
            return true;
        else
            return false;

    }


    @Override
    public Value defaultValue(){

        return new BoolValue(false);
    }

    public String toString() {
        return "bool";}
}
