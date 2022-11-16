package Domain.Expression;
import Domain.Value.*;
import Exceptions.ADTException;
import Exceptions.ExpressionEvaluationException;
import Domain.ADT.*;
public class ValueExp  implements Exp{
    Value e;

    public ValueExp(Value newValue){
        e=newValue;
    }
    @Override
    public Value eval(MyIDictionary<String,Value> tbl)  throws ADTException, ExpressionEvaluationException {
        return e;
    }
    //....
    public String toString(){
        return e.toString();
    }

}
