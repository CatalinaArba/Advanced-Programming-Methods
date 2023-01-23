package Domain.Expression;

import Domain.Value.BoolValue;
import Domain.ADT.MyIDictionary;
import Domain.ADT.MyIHeap;
import Domain.Type.BoolType;
import Domain.Type.Type;
import Exceptions.ADTException;
import Exceptions.ExpressionEvaluationException;
import Domain.Value.Value;

import java.util.Objects;

public class LogicExp implements Exp {
    Exp e1;
    Exp e2;
    int op;
    //....


    @Override
    public Type typeCheck(MyIDictionary<String, Type> typeEnv) throws ExpressionEvaluationException, ADTException {
        Type type1, type2;
        type1 = e1.typeCheck(typeEnv);
        type2 = e2.typeCheck(typeEnv);
        if (type1.equals(new BoolType())) {
            if (type2.equals(new BoolType())) {
                return new BoolType();
            } else
                throw new ExpressionEvaluationException("Second operand is not a boolean.");
        } else
            throw new ExpressionEvaluationException("First operand is not a boolean.");
    }

    @Override
    public Value eval(MyIDictionary<String,Value> tbl, MyIHeap heap) throws ADTException,ExpressionEvaluationException {
        Value v1,v2;
        v1= e1.eval(tbl,heap);
        if (v1.getType().equals(new BoolType())) {
            v2 = e2.eval(tbl,heap);
            if (v2.getType().equals(new BoolType())) {
                BoolValue i1 = (BoolValue)v1;
                BoolValue i2 = (BoolValue)v2;
                boolean n1,n2;
                n1= i1.getVal();
                n2 = i2.getVal();
                if (Objects.equals(op,"and"))
                    return new BoolValue(n1&&n2);
                if (Objects.equals(op,"or"))
                    return new BoolValue(n1||n2);

            }else
                throw new ExpressionEvaluationException("Error:LogoicExp: second operand is not a boolean");
        }else
            throw new ExpressionEvaluationException("Error:LogicExp: first operand is not a boolean");
        return null;
    }
}
