public class PuzzleElement {

    int id;
    int left;
    int top;
    int right;
    int bottom;

    public PuzzleElement(int id, int left, int top, int right, int bottom){
        this.id = id;
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
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
        return String.format("PuzzleElement ID: %d [LEFT %d] [UP %d] [RIGHT %d] [BOTTOM %d]", id, left, top, right, bottom);
    }
}
