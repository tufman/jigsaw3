public enum PuzzleDirections {
    LEFT_PLUS("LEFT_PLUS"),
    LEFT_ZERO("LEFT_ZERO"),
    LEFT_MINUS("LEFT_MINUS"),

    TOP_PLUS("TOP_PLUS"),
    TOP_ZERO("TOP_ZERO"),
    TOP_MINUS("TOP_MINUS"),

    RIGHT_PLUS("RIGHT_PLUS"),
    RIGHT_ZERO("RIGHT_ZERO"),
    RIGHT_MINUS("RIGHT_MINUS"),

    BOTTOM_PLUS("BOTTOM_PLUS"),
    BOTTOM_ZERO("BOTTOM_ZERO"),
    BOTTOM_MINUS("BOTTOM_MINUS"),

    TOP_LEFT_CORNER("TOP_LEFT_CORNER"),
    BOTTOM_LEFT_CORNER("COTTOM_LEFT_CORNER"),
    TOP_RIGHT_CORNER("TOP_RIGHT_CORNER"),
    BOTTOM_RIGHT_CORNER("BOTTOM_RIGHT_CORNER");

    private String direction;

    PuzzleDirections(String direction){
        this.direction = direction;
    }

    public String getDirection(){
        return this.direction;
    }
}
