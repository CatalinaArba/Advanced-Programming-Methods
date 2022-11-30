package Exceptions;

public class ADTException extends Exception{
    // exceptional situations for your 3 ADTs (Stack, Dictionary and List) operations
    // (e.g. writing into a full collection, reading from an empty collection, etc)
    public ADTException(String message){
        super(message);
    }
}
