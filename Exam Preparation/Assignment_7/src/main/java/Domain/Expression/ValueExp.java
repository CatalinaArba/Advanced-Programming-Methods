package Domain.Expression;

import Domain.ADT.MyIDictionary;
import Domain.ADT.MyIHeap;
import Domain.Type.Type;
import Domain.Value.Value;
import Exceptions.ADTException;
import Exceptions.ExpressionEvaluationException;

public class ValueExp implements Exp {
    Value e;

    @Override
    public Type typeCheck(MyIDictionary<String, Type> typeEnv) throws ExpressionEvaluationException, ADTException {
        return e.getType();
    }

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
