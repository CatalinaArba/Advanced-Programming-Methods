package Repository;

import Domain.ProgramState.PrgState;
import Exceptions.ADTException;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
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
    public void logPrgStateExec(PrgState prgState) throws IOException , ADTException {
        PrintWriter logFile;
        logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
        logFile.println(prgState.programStateToString());
        logFile.close();
    }

    @Override
    public List<PrgState> getPrgList() {
        return programStates;
    }

    @Override
    public void setPrgList(List<PrgState> prgStates) {
        this.programStates=prgStates;
    }
}
