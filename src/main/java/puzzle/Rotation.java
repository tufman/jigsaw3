package puzzle;

public enum Rotation {
    _0(""),
    _1("[90]"),
    _2("[180]"),
    _3("[270]");

    private String monetaryValue;
    Rotation(String monetaryValue) {
        this.monetaryValue = monetaryValue;
    }
    public String getMonetaryValue() {
        return this.monetaryValue;
    }

}