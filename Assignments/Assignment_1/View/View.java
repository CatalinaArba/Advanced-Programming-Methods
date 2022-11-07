package View;

import Controller.Controller;
import Model.InterfaceForme;
import Exception.MyException;
import java.util.Scanner;
public class View {
    private Controller cntrlr;

    public View(Controller c){
        cntrlr=c;
    }
    private void printAll(){
        InterfaceForme[] all=this.cntrlr.getAll();
        if(all.length==0){
            System.out.println("Error:no objects");
            return;

        }
        for(InterfaceForme e: all){
            if (e!=null)
                System.out.println(e.toString());
        }


    }

    private void printValid()throws  MyException{
        Scanner in = new Scanner(System.in);
        System.out.println("Enter the minimum value:");
        String s = in.nextLine();
        int v=Integer.parseInt(s);
        if (v<=0)
            throw new MyException("Error: View: Invalid value!");
        InterfaceForme[] all=this.cntrlr.findAll(v);
        if(all.length==0){
            System.out.println("Error:no objects");
            return;
        }

        for(InterfaceForme e: all){
            if (e!=null)
                System.out.println(e.toString());
        }

    }

    private void reading(String[] args)throws MyException {
        for (int i=0;i< args.length;i+=2){
            String type=args[i];
            int value=Integer.parseInt(args[i+1]);
            this.cntrlr.add(type,value);
        }
    }

    public void mainView(String[] args) throws MyException{
        reading(args);
        System.out.println("All");
        printAll();
        System.out.println("Valid ");
        printValid();

    }

}
