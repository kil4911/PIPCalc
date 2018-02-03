package nodes;

/**
 * Created by King David Lawrence on 11/30/2017.
 * Constant MerpNode
 */
public class ConstantNode implements PIPCalcNode{
    private int value;

    /**
     * Constructor that sets the value of this node
     * @param value qinteger value of this node
     */
    public ConstantNode(int value) {
        this.value = value;
    }

    /**
     * Returns the value of this node
     * @return the integer value of this node
     */
    public int evaluate() {
        return value;
    }

    /**
     * Displays this node as prefix notation expression string
     * @return string representing the node as prefix notation
     */
    public String toPrefixString() {
        return "" + value;
    }

    /**
     * Displays this node as infix notation expression string
     * @return string representing the node as infix notation
     */
    public String toInfixString() {
        return "" + value;
    }

    /**
     * Displays this node as prefix notation expression string
     * @return string representing the node as postfix notation
     */
    public String toPostfixString() {
        return "" + value;
    }

    /**
     *Returns the precedence of this node
     * @return returns the precedence of this node as an int value
     */
    public int getPrecedence() {
        return Precedence.CONSTANT.getPrecedence();
    }

    /**
     * determines if the node is an operation node
     * @return true if an operation node, false otherwise
     */
    public boolean isOperation() {
        return false;
    }

    /**
     *Determines the node type
     * @return the type of this node
     */
    public PIPCalcNode.NodeType getNodeType() {
        return NodeType.Constant;
    }

}
