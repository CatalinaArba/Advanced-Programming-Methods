package Domain.Value;

import Domain.Type.BoolType;
import Domain.Type.Type;

public class BoolValue implements Value {
    //....
    boolean val;
     public BoolValue (boolean value){

         this.val=value;
     }
    @Override
    public Type getType() {

         return new BoolType();
    }

    public boolean getVal() {

        return val;
    }

    @Override
    public String toString() {
        return this.val ? "true" : "false";
    }

    @Override
    public boolean equals(Object another) {
        return another instanceof BoolValue;
    }
}
