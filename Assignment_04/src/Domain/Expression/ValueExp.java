package Domain.Expression;

import Domain.ADT.MyIDictionary;
import Domain.ADT.MyIHeap;
import Domain.Value.Value;
import Exceptions.ADTException;
import Exceptions.ExpressionEvaluationException;

public class ValueExp implements Exp {
    Value e;

    public ValueExp(Value newValue) {
        e = newValue;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, MyIHeap heap) throws ADTException, ExpressionEvaluationException {
        return e;
    }

    public String toString() {
        return e.toString();
    }

}
