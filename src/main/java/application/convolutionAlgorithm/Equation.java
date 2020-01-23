package application.convolutionAlgorithm;

import java.awt.*;

public class Equation {
    private int xPosition;
    private int yPosition;
    private int[][] imageMatrix;
    private int[][] filter;
    private boolean isToProcess;
    private int resultR,resultG,resultB;
    private int[][] imageMatrixR,imageMatrixG,imageMatrixB;


    public int getResultR() {
        return resultR;
    }

    public void setResultR(int resultR) {
        this.resultR = resultR;
    }

    public int getResultG() {
        return resultG;
    }

    public void setResultG(int resultG) {
        this.resultG = resultG;
    }

    public int getResultB() {
        return resultB;
    }

    public void setResultB(int resultB) {
        this.resultB = resultB;
    }

    public int[][] getImageMatrixR() {
        return imageMatrixR;
    }

    public void setImageMatrixR(int[][] imageMatrixR) {
        this.imageMatrixR = imageMatrixR;
    }

    public int[][] getImageMatrixG() {
        return imageMatrixG;
    }

    public void setImageMatrixG(int[][] imageMatrixG) {
        this.imageMatrixG = imageMatrixG;
    }

    public int[][] getImageMatrixB() {
        return imageMatrixB;
    }

    public void setImageMatrixB(int[][] imageMatrixB) {
        this.imageMatrixB = imageMatrixB;
    }

    public Equation(int xPosition, int yPosition, int[][] imageMatrix, int[][] filter, boolean isToProcess) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.imageMatrix = imageMatrix;
        this.filter = filter;
        this.isToProcess = isToProcess;
        if(isToProcess){
            splitIntoColors();
        }
        else{

        }
    }

    public int getxPosition() {
        return xPosition;
    }

    public void setxPosition(int xPosition) {
        this.xPosition = xPosition;
    }

    public int getyPosition() {
        return yPosition;
    }

    public void setyPosition(int yPosition) {
        this.yPosition = yPosition;
    }

    public int[][] getImageMatrix() {
        return imageMatrix;
    }

    public void setImageMatrix(int[][] imageMatrix) {
        this.imageMatrix = imageMatrix;
    }

    public int[][] getFilter() {
        return filter;
    }

    public void setFilter(int[][] filter) {
        this.filter = filter;
    }

    public boolean isToProcess() {
        return isToProcess;
    }

    public void setToProcess(boolean toProcess) {
        isToProcess = toProcess;
    }
    public void splitIntoColors(){
        imageMatrixR = new int[3][3];
        imageMatrixG = new int[3][3];
        imageMatrixB = new int[3][3];
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                Color mycolor = new Color((int) this.imageMatrix[i][j]);
                imageMatrixR[i][j]=mycolor.getRed();
                imageMatrixG[i][j]=mycolor.getGreen();
                imageMatrixB[i][j]=mycolor.getBlue();
            }
        }
    }
}
