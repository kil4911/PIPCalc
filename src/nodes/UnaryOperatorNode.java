package nodes;


/**
 * Created by King David Lawrence on 11/30/2017.
 * Abstract class to represent a unary operator
 * Unary operators only have a single child
 */
public abstract class UnaryOperatorNode implements PIPCalcNode{

    //Direct Known Subclasses:
    //AbsValueNode, NegationNode, SquareRootNode

    protected PIPCalcNode child;
    protected String operator;
    protected Precedence precedence;


    /**
     *Binary Node Constructor
     * @param child PIPCalcNode representing the child
     * @param precedence Precedence of the operator
     * @param operator String representing the operator
     */
    public UnaryOperatorNode(PIPCalcNode child,
                             Precedence precedence,
                             String operator) {
        this.child = child;
        this.precedence = precedence;
        this.operator = operator;
    }

    /**
     * Sets the child of this node
     * @param child the PIPCalcNode representing the child
     */
    public void setChild(PIPCalcNode child) {
        this.child = child;
    }

    /**
     * Displays this node as prefix notation expression string
     * @return string representing the node as prefix notation
     */
    public String toPrefixString() {
        return ""+ operator + " " + child.toPrefixString();
    }

    /**
     * Displays this node as infix notation expression string
     * @return string representing the node as infix notation
     */
    public String toInfixString() {
        return "("+ operator + " " + child.toInfixString() + ")";
    }

    /**
     * Displays this node as prefix notation expression string
     * @return string representing the node as postfix notation
     */
    public String toPostfixString() {
        return ""+child.toPostfixString() + " " + operator;
    }

    /**
     *Returns the precedence of this node
     * @return returns the precedence of this node as an int value
     */
    public int getPrecedence() {
        return precedence.getPrecedence();
    }

    /**
     * determines if the node is an operation node
     * @return true if an operation node, false otherwise
     */
    public boolean isOperation() {
        return true;
    }

    /**
     *Determines the node type
     * @return the type of this node
     */
    public PIPCalcNode.NodeType getNodeType() {
        return NodeType.UnaryOperation;
    }

}
