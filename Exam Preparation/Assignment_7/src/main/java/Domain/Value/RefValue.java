package Domain.Value;

import Domain.Type.RefType;
import Domain.Type.Type;
public class RefValue implements Value{
    private final int address;
    private final Type locationType;

    public RefValue(int address, Type locationType) {
        this.address = address;
        this.locationType = locationType;
    }
    @Override
    public Type getType() {
        return new RefType(locationType);
    }

    public int getAddress() {
        return address;
    }

    public Type getLocationType() {
        return locationType;
    }


    @Override
    public String toString() {
        return String.format("(%d, %s)", address, locationType);
    }
}
