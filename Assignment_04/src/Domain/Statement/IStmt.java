package Domain.Statement;

import Domain.ProgramState.PrgState;
import Exceptions.ADTException;
import Exceptions.ExpressionEvaluationException;
import Exceptions.StatementExecutionException;

public interface IStmt {
    PrgState execute(PrgState state) throws ADTException, ExpressionEvaluationException, StatementExecutionException;
    //which is the execution method for a statement.
}
