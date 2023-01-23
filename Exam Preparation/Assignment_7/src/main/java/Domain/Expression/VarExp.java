package Domain.Expression;

import Domain.ADT.MyIDictionary;
import Domain.ADT.MyIHeap;
import Domain.Type.Type;
import Exceptions.ADTException;
import Exceptions.ExpressionEvaluationException;
import Domain.Value.Value;

public class VarExp implements Exp {
    String id;

    public VarExp(String id) {
        this.id = id;
    }

    @Override
    public Type typeCheck(MyIDictionary<String, Type> typeEnv) throws ExpressionEvaluationException, ADTException {
        return typeEnv.lookUp(id);
    }

    public Value eval(MyIDictionary<String, Value> tbl, MyIHeap heap) throws ExpressionEvaluationException, ADTException {
        return tbl.lookUp(id);
    }

    public String toString() {
        return id;
    }
}
