/*
Enum for the Rotation - mainly for print the results by their rotation

Author: Yelena Koviar
 */
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