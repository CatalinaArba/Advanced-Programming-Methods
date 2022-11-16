package Domain.Value;
import Domain.Type.*;
public class IntValue implements Value{
    public final int val;
    public IntValue(int v){
        val=v;
    }
    public int getVal() {

        return val;
    }
    @Override
    public String toString() {

        return String.format("%d", this.val);
    }
    @Override
    public Type getType() {

        return new IntType();
    }


}
