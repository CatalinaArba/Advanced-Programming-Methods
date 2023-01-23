package Domain.Expression;

import Domain.ADT.MyIDictionary;
import Domain.ADT.MyIHeap;
import Domain.Value.IntValue;
import Domain.Type.IntType;
import Domain.Type.Type;
import Exceptions.ADTException;
import Exceptions.ExpressionEvaluationException;
import Domain.Value.Value;

public class ArithExp implements Exp {
    Exp e1;
    Exp e2;
    char op;

    //...


    @Override
    public Type typeCheck(MyIDictionary<String, Type> typeEnv) throws ExpressionEvaluationException, ADTException {
        Type type1, type2;
        type1 = e1.typeCheck(typeEnv);
        type2 = e2.typeCheck(typeEnv);
        if (type1.equals(new IntType())) {
            if (type2.equals(new IntType())) {
                return new IntType();
            } else
                throw new ExpressionEvaluationException("Second operand is not an integer.");
        } else
            throw new ExpressionEvaluationException("First operand is not an integer.");
    }

    public ArithExp(char operation, Exp expression1, Exp expression2) {
        e1 = expression1;
        e2 = expression2;
        op = operation;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, MyIHeap heap) throws ExpressionEvaluationException, ADTException {
        Value v1, v2;
        v1 = e1.eval(tbl, heap);
        if (v1.getType().equals(new IntType())) {
            v2 = e2.eval(tbl, heap);
            if (v2.getType().equals(new IntType())) {
                IntValue i1 = (IntValue) v1;
                IntValue i2 = (IntValue) v2;
                int n1, n2;
                n1 = i1.getVal();
                n2 = i2.getVal();
                if (op == '+')
                    return new IntValue(n1 + n2);
                if (op == '-')
                    return new IntValue(n1 - n2);
                if (op == '*')
                    return new IntValue(n1 * n2);
                if (op == '/')
                    if (n2 == 0) throw new ExpressionEvaluationException("Error:ArithExp: division by zero");
                    else return new IntValue(n1 / n2);
            } else
                throw new ExpressionEvaluationException("Error:ArithExp: second operand is not an integer");
        } else
            throw new ExpressionEvaluationException("Error:ArithExp: first operand is not an integer");
        return null;
    }

    @Override
    public String toString() {
        return e1.toString() + " " + op + " " + e2.toString();
    }
}
