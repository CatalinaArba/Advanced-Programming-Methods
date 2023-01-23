package Domain.Expression;

import Domain.ADT.MyIDictionary;
import Domain.ADT.MyIHeap;
import Domain.Type.Type;
import Domain.Value.BoolValue;
import Domain.Value.Value;
import Exceptions.ADTException;
import Exceptions.ExpressionEvaluationException;

public class NotExp implements  Exp{
    private final Exp expression;

    public NotExp(Exp expression){
        this.expression=expression;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, MyIHeap heap) throws ADTException, ExpressionEvaluationException {
        BoolValue value= (BoolValue) expression.eval(tbl,heap);
        if(!value.getVal())
            return new BoolValue(true);
        return new BoolValue(false);
    }

    @Override
    public Type typeCheck(MyIDictionary<String, Type> typeEnv) throws ExpressionEvaluationException, ADTException {
        return expression.typeCheck(typeEnv);
    }

    @Override
    public String toString() {
        return String.format("!(%s)", expression);
    }
}
