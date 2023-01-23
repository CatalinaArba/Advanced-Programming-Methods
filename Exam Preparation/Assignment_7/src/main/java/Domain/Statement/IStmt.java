package Domain.Statement;

import Domain.ADT.MyIDictionary;
import Domain.ProgramState.PrgState;
import Domain.Type.Type;
import Exceptions.ADTException;
import Exceptions.ExpressionEvaluationException;
import Exceptions.InterpreterException;
import Exceptions.StatementExecutionException;

public interface IStmt {
    PrgState execute(PrgState state) throws ADTException, ExpressionEvaluationException, StatementExecutionException;
    MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws InterpreterException,StatementExecutionException, ExpressionEvaluationException, ADTException;
}
