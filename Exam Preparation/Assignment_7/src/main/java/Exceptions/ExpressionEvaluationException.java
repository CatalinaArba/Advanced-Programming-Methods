package Exceptions;

public class ExpressionEvaluationException extends Exception{
    // expressions evaluation: Division by zero, variable not defined in symbol table
    public ExpressionEvaluationException(String message){
        super(message);
    }
}
