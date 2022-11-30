package Domain.Expression;
import Domain.Value.*;
import Domain.ADT.*;
import Exceptions.ADTException;
import Exceptions.ExpressionEvaluationException;

public class VarExp implements Exp{
    String id;
    //....
    public VarExp(String id){
        this.id=id;
    }
    public Value eval(MyIDictionary<String,Value> tbl)  throws ExpressionEvaluationException, ADTException {
        return tbl.lookUp(id);
    }
    //....
    public String toString(){
        return id;
    }
}
