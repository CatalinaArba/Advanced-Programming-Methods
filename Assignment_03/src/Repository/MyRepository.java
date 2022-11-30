package Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import Domain.ProgramState.PrgState;

import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.io.FileWriter;
public class MyRepository implements MyIRepository{
    private List<PrgState> programStates;
    private int currentPosition;
    private String logFilePath;

    public MyRepository(PrgState prgState, String newLogFilePath){
        programStates=new ArrayList<>();
        currentPosition=0;
        programStates.add(prgState);
        logFilePath=newLogFilePath;
    }
    @Override
    public void logPrgStateExec() {
        try(PrintWriter logFile= new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true))))
        {
            logFile.println(programStates.toString());
            //logFile.close();

        }
         catch (IOException e) {
            System.err.println(e);
        }


    }

    @Override
    public PrgState getCrtPrg(){

        return programStates.get(currentPosition);
    }
}
