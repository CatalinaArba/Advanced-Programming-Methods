package Domain.Expression;

import Domain.ADT.MyIDictionary;
import Domain.ADT.MyIHeap;
import Domain.Value.RefValue;
import Domain.Value.Value;
import Exceptions.ADTException;
import Exceptions.ExpressionEvaluationException;

public class ReadHeapExpression implements Exp {
    private final Exp expression;

    public ReadHeapExpression(Exp expression) {
        this.expression = expression;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, MyIHeap heap) throws ADTException, ExpressionEvaluationException {
        //the expression must be evaluated to a RefValue. If not, the execution is stopped with an appropriate error message.
        Value value = expression.eval(tbl, heap);
        if (!(value instanceof RefValue refValue))
            throw new ExpressionEvaluationException(String.format("Error: ReadHeapExpression: %s not of RefType", value));
        //Take the address component of the RefValue computed before and use it to access Heap table and return the value associated to that address. If the address is not a key in Heap table, the execution is stopped with an appropriate error message.
        return heap.get(refValue.getAddress());

    }

    @Override
    public String toString() {
        return String.format("ReadHeap(%s)", expression);
    }
}
