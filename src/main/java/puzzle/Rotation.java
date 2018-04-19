package puzzle;

public enum Rotation {
    _90(90),
    _180(180),
    _270(270);

    private int monetaryValue;
    Rotation(int monetaryValue) {
        this.monetaryValue = monetaryValue;
    }
    public int getMonetaryValue() {
        return this.monetaryValue;
    }
}