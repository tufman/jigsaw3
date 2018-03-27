public class Element {

    int id;
    int left;
    int top;
    int right;
    int bottom;

    public Element(int id, int left, int top, int right, int bottom){
        this.id = id;
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }

    @Override
    public boolean equals (Object obj){
        if (!(obj instanceof Element)){
            return false;
        }

        return (((Element) obj).id == this.id &&
                ((Element) obj).left == this.left &&
                ((Element) obj).top == this.top &&
                ((Element) obj).right == this.right &&
                ((Element) obj).bottom == this.bottom);
    }

    @Override
    public String toString() {
//        String temp = String.format("Element ID: %d [LEFT %d] [UP %d] [RIGHT %d] [BOTTOM %d]", id, left, top, right, bottom);
//        return temp;
        return String.format("Element ID: %d [LEFT %d] [UP %d] [RIGHT %d] [BOTTOM %d]", id, left, top, right, bottom);
    }
}
