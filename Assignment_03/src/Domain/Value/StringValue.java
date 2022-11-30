package Domain.Value;

import Domain.Type.StringType;
import Domain.Type.Type;

public class StringValue implements Value{
    private String value;

    public StringValue(String value){
        this.value=value;
    }
    @Override
    public Type getType() {
        return new StringType();
    }

    public String getVal(){
        return this.value;
    }

    @Override
    public String toString() {
        return "\"" + this.value + "\"";
    }

    @Override
    public boolean equals(Object another) {
        return another instanceof StringValue;
    }
}
