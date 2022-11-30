package Domain.ADT;
import Exceptions.ADTException;

import java.util.Collections;
import java.util.Stack;
public class MyStack<T> implements MyIStack<T>{
    Stack<T>  stack;
    public MyStack(){
        this.stack=new Stack<>();
    }
    //a field whose type  CollectionType is an appropriate
    // generic java library  collection
    // ......................
    @Override
    public boolean isEmpty(){
        return this.stack.isEmpty();
    }
    @Override
    public T pop() throws ADTException{
        if(this.isEmpty())
            throw new ADTException("Error:MyStack: The stack is empty!");
        return stack.pop();

    }
    public void push(T newElement){
        stack.push(newElement);
    }

    public String toString(){
        return stack.toString();
    }
}
