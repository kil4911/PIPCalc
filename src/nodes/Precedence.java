package nodes;

/**
 * Enum representing the precedences of MerpNodes
 * The lower the value, the higher the precedence
 */
public enum Precedence {
    POWER(0),
    MULT_DIVIDE(1),
    ADD_SUBTRACT(2),
    CONSTANT(3),
    BOOLEAN(4);

    private final int precedence;

    Precedence(int precedence ){
        this.precedence = precedence;
    }

    public int getPrecedence(){
        return this.precedence;
    }
}
