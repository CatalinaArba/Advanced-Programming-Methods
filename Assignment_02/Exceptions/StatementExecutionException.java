package Exceptions;

public class StatementExecutionException extends Exception{
    // statements execution: trying to execute when the execution stack is empty
    public StatementExecutionException(String message){
        super(message);
    }
}
