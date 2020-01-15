package application;

import application.convolutionAlgorithm.AlgorithmExpert;
import application.convolutionAlgorithm.Filter;
import application.generators.ImageGenerator;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {
    public final static int NUMBER_OF_THREADS=4;
    private static final int ITERATIONS = 5;

    public static void main(String[] args) throws IOException, InterruptedException {
        AlgorithmExpert algorithmExpert = new AlgorithmExpert();

        String imagePath ="C:/Users/arkdo/IdeaProjects/steganography/PanMontana.jpg";
        //File file2 = new File(imagePath);

        File file2 = ImageGenerator.generateImage("first.jpg",500,450);

        long start = System.currentTimeMillis();
        long elapsedTimeMillis;

        for(int i=0;i<ITERATIONS;i++){
        BufferedImage imageAfterConvolution = algorithmExpert.singleConvolution(file2, Filter.depthInThePictureFilter);
        String filePath = "image"+i+".jpg";
            file2 = new File(filePath);
        ImageIO.write(imageAfterConvolution, "jpg", file2);
        System.out.println("file"+i+"done");
        }

        elapsedTimeMillis = System.currentTimeMillis()-start;
        System.out.println(elapsedTimeMillis +" ms passed for "+NUMBER_OF_THREADS +"threads");
    }
}
