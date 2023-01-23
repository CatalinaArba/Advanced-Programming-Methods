package View;

import Domain.ADT.MyDictionary;
import Domain.ADT.MyIDictionary;
import Exceptions.ADTException;

import java.util.Scanner;
public class TextMenu {
    private MyIDictionary<String, Command> commands;

    public TextMenu() {
        this.commands = new MyDictionary<>();
    }

    public void addCommand(Command c) {
        commands.put(c.getKey(), c);
    }

    private void printMenu() {
        for (Command com : commands.values()) {
            String line = String.format("%4s : %s", com.getKey(), com.getDescription());
            System.out.println(line);
        }
    }

    public void show(){
        Scanner scanner=new Scanner(System.in);
        while(true){
            printMenu();
            System.out.printf("Input the option: ");
            String key=scanner.nextLine();
            try{
                Command com=commands.lookUp(key);
                com.execute();
            }
            catch (ADTException e){
                System.out.println("Invalid Option");
            }


        }
    }
}
