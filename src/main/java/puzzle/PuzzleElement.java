package puzzle;

import java.util.ArrayList;

public class PuzzleElement {

    private int id;
    private int left;
    private int top;
    private int right;
    private int bottom;
    private Rotation rotation;
    private int key;
    //public PuzzleElement(int id, int left, int top, int right, int bottom){
    public PuzzleElement(ArrayList<Integer> numFromLine){
        this.id = numFromLine.get(0);
        this.left = numFromLine.get(1);
        this.top = numFromLine.get(2);
        this.right =numFromLine.get(3);;
        this.bottom = numFromLine.get(4);
    }
    public PuzzleElement(ArrayList<Integer> numFromLine, int rotation){
        //TODO Change the switch to something with %4

        switch(rotation){
            case 0:  {
                this.rotation = Rotation._0;
                this.id = numFromLine.get(0);
                this.left = numFromLine.get(1);
                this.top = numFromLine.get(2);
                this.right =numFromLine.get(3);;
                this.bottom = numFromLine.get(4);
                break;
            }
            case 1: {
                this.rotation = Rotation._1;
                this.id = numFromLine.get(0);
                this.left = numFromLine.get(4);
                this.top = numFromLine.get(1);
                this.right =numFromLine.get(2);;
                this.bottom = numFromLine.get(3);
                break;
            }
            case 2: {
                this.rotation = Rotation._2;
                this.id = numFromLine.get(0);
                this.left = numFromLine.get(3);
                this.top = numFromLine.get(4);
                this.right =numFromLine.get(1);;
                this.bottom = numFromLine.get(2);
                break;
            }
            case 3: {
                this.rotation = Rotation._3;
                this.id = numFromLine.get(0);
                this.left = numFromLine.get(2);
                this.top = numFromLine.get(3);
                this.right =numFromLine.get(4);;
                this.bottom = numFromLine.get(1);
            }
        }
    }

    public int getSumOfEdges() {
        return left*1000 + top*100 + right*10 + bottom;
    }
    public int getSumOfEdgesJokerBottom() {
        return left*1000 + top*100 + right*10 + 5;
    }
    public int getSumOfEdgesJokerRight() {
        return left*1000 + top*100 + 50 + bottom;
    }
    public int getSumOfEdgesJokerRightAndBottom() {
        return left*1000 + top*100 + 55;
    }


    @Override
    public boolean equals (Object obj){
        if (!(obj instanceof PuzzleElement)){
            return false;
        }

        return (((PuzzleElement) obj).id == this.id &&
                ((PuzzleElement) obj).left == this.left &&
                ((PuzzleElement) obj).top == this.top &&
                ((PuzzleElement) obj).right == this.right &&
                ((PuzzleElement) obj).bottom == this.bottom &&
                ((PuzzleElement) obj).rotation == this.rotation);
    }

    public int getId() {
        return id;
    }

    public int getLeft() {
        return left;
    }

    public int getTop() {
        return top;
    }

    public int getRight() {
        return right;
    }

    public int getBottom() {
        return bottom;
    }

    public Rotation getRotation() {
        return rotation;
    }

    @Override
    public String toString() {
        return String.format("[ID %d] [ %d] [ %d] [ %d] [ %d] [%s]", id, left, top, right, bottom, rotation);
    }


    public boolean getTopLeftCorner() {
        return ((left == 0) && (top == 0));
    }

    public boolean getTopRightCorner() {
        return ((top == 0) && (right == 0));
    }

    public boolean getBottomLeftCorner() {
        return ((bottom == 0) && (left == 0));
    }

    public boolean getBottomRightCorner() {
        return ((bottom == 0) && (right == 0));
    }
}
