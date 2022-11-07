//7. Se da o colectie de mai multe obiecte
//avind forme de cuburi, sfere si cilindrii.
//Sa se afiseze obiectele avind volumul mai mare
//decit 25cm3.

import Controller.Controller;
import Repository.Repository;
import View.View;
import Exception.LoggerUtil;
import Exception.MyException;


public class Main {
    public static void main(String[] args) throws MyException{
        try{
        Repository r = new Repository(15);
        Controller c = new Controller(r);
        View v = new View(c);
        v.mainView(args);
        }catch(MyException e1) {
            LoggerUtil.logError(e1.getMessage());
        }
    }
}

/*    try {
    ...something that can throw an ignorable exception
        } catch( Exception ex ) {
            LOGGER.log( Level.SEVERE, ex.toString(), ex );
        }
*/