package application.convolutionAlgorithm;

import com.google.common.collect.Lists;

import javax.imageio.ImageIO;
import javax.naming.ldap.UnsolicitedNotification;
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
    long start;
    long elapsedTimeMillis;

    public BufferedImage singleConvolution(File file, int[][] filter) throws IOException, InterruptedException {
       BufferedImage inputImage = ImageIO.read(file);

       createEquationsOnThreads(inputImage,filter);
        List<List<Equation>> splitEquations= splitLists(UnSolvedEquationsCollection.getInstance().getEquations());
        solveEquationOnThreads(splitEquations);
        BufferedImage outputImage = new BufferedImage(inputImage.getWidth(), inputImage.getHeight(),BufferedImage.TYPE_INT_RGB );

        for(Equation equation:ResultsCollection.getInstance().getEquations()) {
            outputImage.setRGB(equation.getxPosition(), equation.getyPosition(),
                    new Color(equation.getResultR(),equation.getResultB(),equation.getResultB()).getRGB());
        }
        ResultsCollection.getInstance().flushMemory();
        UnSolvedEquationsCollection.getInstance().flushMemory();
        return outputImage;
    }
    private  List<List<Equation>> splitLists(ArrayList<Equation> equations){
        int singleListSize =Math.round(equations.size()/NUMBER_OF_THREADS)+1;
        List<List<Equation>> equationsParts=Lists.partition(equations,singleListSize);
        return equationsParts;
    }

    private void createEquationsOnThreads(BufferedImage inputImage,int[][] filter) throws InterruptedException {
        ThreadPoolExecutor thread_factory= (ThreadPoolExecutor) Executors.newFixedThreadPool(NUMBER_OF_THREADS);
        start = System.currentTimeMillis();
        int height= inputImage.getHeight();
        int range = (int) Math.floor(height/NUMBER_OF_THREADS);
        int first,last;
        for(int i=0;i<NUMBER_OF_THREADS;i++) {
            if(i==0){
                first=0;last=range;
            }else
            if(i < NUMBER_OF_THREADS - 1){
                first=range*i;
                last=first+range;
            }else {
                first=range*i;
                last=height;
            }
            int finalFirst = first;
            int finalLast = last;
            thread_factory.submit(new Runnable() {
                @Override
                public void run() {
                    new CreateEquationThread(inputImage, finalFirst, finalLast,filter).run();
                }
            });

        }
        thread_factory.shutdown();
        thread_factory.awaitTermination(1, TimeUnit.DAYS);
        elapsedTimeMillis = System.currentTimeMillis()-start;
        System.out.println(elapsedTimeMillis +" ms passed for divide equations");

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
