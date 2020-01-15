package application.convolutionAlgorithm;

import java.util.ArrayList;
import java.util.List;

public class ResultsCollection {
    private List<Equation> equations = new ArrayList<>();
    private static ResultsCollection single_instance = null;

    public static ResultsCollection getInstance()
    {
        if (single_instance == null)
            single_instance = new ResultsCollection();

        return single_instance;
    }
    public synchronized void addToEquations(List<Equation> newEquations){
        this.equations.addAll(newEquations);
    }
    public void flushMemory(){
        this.equations.clear();
    }
    public List<Equation> getEquations() {
        return equations;
    }
}
