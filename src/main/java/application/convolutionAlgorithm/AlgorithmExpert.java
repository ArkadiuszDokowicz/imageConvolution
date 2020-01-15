package application.convolutionAlgorithm;

import com.google.common.collect.Lists;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static application.Main.NUMBER_OF_THREADS;


public class AlgorithmExpert {
    private List<Equation> solvedEquations= new ArrayList<>();
    long start;
    long elapsedTimeMillis;

    public BufferedImage singleConvolution(File file, int[][] filter) throws IOException, InterruptedException {
       BufferedImage inputImage = ImageIO.read(file);

       ArrayList<Equation> equations = createEquations(inputImage,filter);
       List<List<Equation>> splitEquations= splitLists(equations);
        solveEquationOnThreads(splitEquations);





        BufferedImage outputImage = new BufferedImage(inputImage.getWidth(), inputImage.getHeight(),BufferedImage.TYPE_INT_RGB );
        //TODO create new image
        for(Equation equation:ResultsCollection.getInstance().getEquations()) {
            outputImage.setRGB(equation.getxPosition(), equation.getyPosition(),
                    new Color(equation.getResultR(),equation.getResultB(),equation.getResultB()).getRGB());
        }
        ResultsCollection.getInstance().flushMemory();
        return outputImage;
    }
    private  List<List<Equation>> splitLists(ArrayList<Equation> equations){
        int singleListSize =Math.round(equations.size()/NUMBER_OF_THREADS)+1;
        List<List<Equation>> equationsParts=Lists.partition(equations,singleListSize);
        return equationsParts;
    }

    private ArrayList<Equation> createEquations(BufferedImage inputImage,int[][] filter){
        start = System.currentTimeMillis();
      //Create thread synchronized class for list
        ArrayList<Equation> equations = new ArrayList<>();
        //create thread algorithm for this equations
        for (int x = 0; x <inputImage.getWidth() ; x++) {
            for (int y = 0; y < inputImage.getHeight(); y++) {

                int[][] matrix = new int[filter.length][filter.length];
                try {
                    if(x!=0 && y!=0 && x!=inputImage.getWidth()-1 && y!=inputImage.getHeight()-1){
                        matrix[0][0] = inputImage.getRGB(x-1,y-1);
                        matrix[0][1] = inputImage.getRGB(x,y-1);
                        matrix[0][2] = inputImage.getRGB(x+1,y-1);
                        matrix[1][0] = inputImage.getRGB(x-1,y);
                        matrix[1][1] = inputImage.getRGB(x,y);
                        matrix[1][2] = inputImage.getRGB(x+1, y);
                        matrix[2][0] = inputImage.getRGB(x-1,y+1);
                        matrix[2][1] = inputImage.getRGB(x,y+1);
                        matrix[2][2] = inputImage.getRGB(x+1,y+1);
                    equations.add(new Equation(x,y,matrix,filter,true));
                    }
                    else{
                        Color mycolor = new Color(inputImage.getRGB(x,y));
                        Equation equation = new Equation(x,y,null,filter,false);
                        equation.setResultR(mycolor.getRed());
                        equation.setResultG(mycolor.getGreen());
                        equation.setResultB(mycolor.getBlue());
                        solvedEquations.add(equation);
                    }
                }
                catch (ArrayIndexOutOfBoundsException exception){
                    System.out.println(x+" "+y);
                    exception.printStackTrace();
                }

            }
        }
        ResultsCollection.getInstance().addToEquations(solvedEquations);
        elapsedTimeMillis = System.currentTimeMillis()-start;
        System.out.println(elapsedTimeMillis +" ms passed for divide equations");
        return equations;
    }

    private void solveEquationOnThreads(List<List<Equation>> splitEquations) throws InterruptedException {
        start = System.currentTimeMillis();
        ThreadPoolExecutor thread_factory= (ThreadPoolExecutor) Executors.newFixedThreadPool(NUMBER_OF_THREADS);
        for(int i=0;i<splitEquations.size();i++){
            int finalI = i;
            thread_factory.submit(new Runnable() {
            @Override
            public void run() {
                new CounterThread(splitEquations.get(finalI)).run();
            }
        });
    }
        thread_factory.shutdown();
        thread_factory.awaitTermination(1, TimeUnit.DAYS);
         elapsedTimeMillis = System.currentTimeMillis()-start;
        System.out.println(elapsedTimeMillis +" ms passed for "+NUMBER_OF_THREADS +"threads");
    }
}
