package Domain.Expression;

import Domain.ADT.MyIDictionary;
import Domain.ADT.MyIHeap;
import Domain.Type.Type;
import Exceptions.ADTException;
import Exceptions.ExpressionEvaluationException;
import Domain.Value.Value;


public interface Exp {
    Value eval(MyIDictionary<String,Value> tbl, MyIHeap heap) throws ADTException, ExpressionEvaluationException;
    Type typeCheck(MyIDictionary<String, Type> typeEnv) throws ExpressionEvaluationException, ADTException;
}
