package Domain.Type;

import Domain.Value.BoolValue;
import Domain.Value.Value;


public class BoolType implements Type {
    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        return obj instanceof BoolType;

    }

    @Override
    public Value defaultValue() {

        return new BoolValue(false);
    }

    public String toString() {
        return "bool";
    }
}
