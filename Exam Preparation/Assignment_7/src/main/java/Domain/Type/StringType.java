package Domain.Type;

import Domain.Value.StringValue;
import Domain.Value.Value;

public class StringType implements Type {

    @Override
    public Value defaultValue() {
        return new StringValue("");
    }

    @Override
    public String toString() {
        return "string";
    }

    @Override
    public boolean equals(Object anotherType) {
        return anotherType instanceof StringType;
    }
}
