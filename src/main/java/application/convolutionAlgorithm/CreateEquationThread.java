package application.convolutionAlgorithm;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class CreateEquationThread implements Runnable{
    private BufferedImage inputImage;
    private int[][] filter;
    private ArrayList<Equation> equations = new ArrayList<>();
    private List<Equation> solvedEquations= new ArrayList<>();
    private int range1;
    private int range0;
    public CreateEquationThread(BufferedImage inputImage,int range0,int range1, int[][] filter) {
        this.inputImage = inputImage;
        this.filter = filter;
        this.range0=range0;
        this.range1=range1;
    }
    public void createEquation(){
    for (int x = 0; x < inputImage.getWidth(); x++) {
            for (int y = range0; y < range1; y++) {

                int[][] matrix = new int[filter.length][filter.length];
                try {
                    if (x != 0 && y != 0 && x != inputImage.getWidth() - 1 && y != inputImage.getHeight() - 1) {
                        matrix[0][0] = inputImage.getRGB(x - 1, y - 1);
                        matrix[0][1] = inputImage.getRGB(x, y - 1);
                        matrix[0][2] = inputImage.getRGB(x + 1, y - 1);
                        matrix[1][0] = inputImage.getRGB(x - 1, y);
                        matrix[1][1] = inputImage.getRGB(x, y);
                        matrix[1][2] = inputImage.getRGB(x + 1, y);
                        matrix[2][0] = inputImage.getRGB(x - 1, y + 1);
                        matrix[2][1] = inputImage.getRGB(x, y + 1);
                        matrix[2][2] = inputImage.getRGB(x + 1, y + 1);
                        equations.add(new Equation(x, y, matrix, filter, true));
                    } else {
                        Color mycolor = new Color(inputImage.getRGB(x, y));
                        Equation equation = new Equation(x, y, null, filter, false);
                        equation.setResultR(mycolor.getRed());
                        equation.setResultG(mycolor.getGreen());
                        equation.setResultB(mycolor.getBlue());
                        solvedEquations.add(equation);
                    }
                } catch (ArrayIndexOutOfBoundsException exception) {
                    System.out.println(x + " " + y);
                    exception.printStackTrace();
                }

            }
        }       UnSolvedEquationsCollection.getInstance().addToEquations(equations);
                ResultsCollection.getInstance().addToEquations(solvedEquations);
    }

    @Override
    public void run() {
        createEquation();
    }
}

