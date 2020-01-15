package application.convolutionAlgorithm;

import java.awt.*;
import java.util.List;

public class CounterThread implements Runnable {
    private List<Equation> equations;


    public CounterThread(List<Equation> equations) {
        this.equations = equations;
    }

    @Override
    public void run() {
        for(Equation equation:equations){
            equation.setResultR(multiplyMatrixAndSumElements(equation.getImageMatrixR(),equation.getFilter()));
            equation.setResultG(multiplyMatrixAndSumElements(equation.getImageMatrixG(),equation.getFilter()));
            equation.setResultB(multiplyMatrixAndSumElements(equation.getImageMatrixB(),equation.getFilter()));
        }
        ResultsCollection.getInstance().addToEquations(equations);
    }
    private int multiplyMatrixAndSumElements(int a[][],int b[][]){
        int c[][]=new int[3][3];
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++) {
                c[i][j] = 0;
                for (int k = 0; k < 3; k++) {
                    c[i][j] += a[i][k] * b[k][j];
                }
            }
        }
        int sum =0;
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                sum+=c[i][j];
            }
        }
        int checked=checkRange(sum);
        return checked;
    }

    private int checkRange(int sum) {
        if(sum>=255){return 255;}
        if(sum<0){return 0;}
        return sum;
    }
}
