import java.util.ArrayList;

public class PuzzleElement {

    int id;
    int left;
    int top;
    int right;
    int bottom;

    //public PuzzleElement(int id, int left, int top, int right, int bottom){
    public PuzzleElement(ArrayList<Integer> numFromLine){
        this.id = numFromLine.get(0);
        this.left = numFromLine.get(1);
        this.top = numFromLine.get(2);
        this.right =numFromLine.get(3);;
        this.bottom = numFromLine.get(4);
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
                ((PuzzleElement) obj).bottom == this.bottom);
    }

    @Override
    public String toString() {
        return String.format("[ID %d] [ %d] [ %d] [ %d] [ %d]", id, left, top, right, bottom);
    }
}
