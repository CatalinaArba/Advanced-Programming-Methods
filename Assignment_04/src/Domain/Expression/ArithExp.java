package Domain.Expression;

import Domain.ADT.MyIDictionary;
import Domain.ADT.MyIHeap;
import Domain.Type.IntType;
import Domain.Value.IntValue;
import Domain.Value.Value;
import Exceptions.ADTException;
import Exceptions.ExpressionEvaluationException;

public class ArithExp implements Exp {
    Exp e1;
    Exp e2;
    char op;

    //...
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
