package Domain.Expression;
import Domain.Value.*;
import Domain.ADT.MyIDictionary;
import Exceptions.ADTException;
import Exceptions.ExpressionEvaluationException;

public interface Exp {
    Value eval(MyIDictionary<String,Value> tbl) throws ADTException, ExpressionEvaluationException;
}
