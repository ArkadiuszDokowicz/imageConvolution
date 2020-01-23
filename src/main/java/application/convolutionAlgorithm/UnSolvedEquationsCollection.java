package application.convolutionAlgorithm;


import java.util.ArrayList;
import java.util.List;

public class UnSolvedEquationsCollection {
    private ArrayList<Equation> equations = new ArrayList<>();
    private static UnSolvedEquationsCollection single_instance = null;

    public static UnSolvedEquationsCollection getInstance()
    {
        if (single_instance == null)
            single_instance = new UnSolvedEquationsCollection();

        return single_instance;
    }
    public synchronized void addToEquations(List<Equation> newEquations){
        this.equations.addAll(newEquations);
    }
    public void flushMemory(){
        this.equations.clear();
    }
    public ArrayList<Equation> getEquations() {
        return equations;
    }
}