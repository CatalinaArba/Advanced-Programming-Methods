package Domain.Expression;

import Domain.ADT.MyIDictionary;
import Domain.ADT.MyIHeap;
import Domain.Value.Value;
import Exceptions.ADTException;
import Exceptions.ExpressionEvaluationException;

public class VarExp implements Exp {
    String id;

    public VarExp(String id) {
        this.id = id;
    }

    public Value eval(MyIDictionary<String, Value> tbl, MyIHeap heap) throws ExpressionEvaluationException, ADTException {
        return tbl.lookUp(id);
    }

    public String toString() {
        return id;
    }
}
