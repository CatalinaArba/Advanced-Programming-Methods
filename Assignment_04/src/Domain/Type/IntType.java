package Domain.Type;

import Domain.Value.IntValue;
import Domain.Value.Value;

public class IntType implements Type {
    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        return obj instanceof IntType;
    }

    @Override
    public Value defaultValue() {
        return new IntValue(0);
    }

    public String toString() {
        return "int";
    }
}