package nodes;

/**
 * Created by King David Lawrence on 11/30/2017.
 */
public abstract class BinaryOperatorNode implements PIPCalcNode{
    //Direct Known Subclasses:
    //AdditionNode, BooleanOperatorNode, DivisionNode,
    // MultiplicationNode, PowerNode, SubtractionNode



    protected PIPCalcNode leftChild;
    protected String operator;
    protected Precedence precedence;
    protected PIPCalcNode rightChild;

    /**
     * Default Constructor
     */
    public BinaryOperatorNode() {}

    /**
     *Binary Node Constructor
     * @param leftChild MerpNode representing the left child
     * @param rightChild MerpNode representing the right child
     * @param precedence Precedence of the operator
     * @param operator String representing the operator
     */
    public BinaryOperatorNode(PIPCalcNode leftChild, PIPCalcNode rightChild,
                       Precedence precedence, String operator) {
        this.leftChild = leftChild;
        this.rightChild = rightChild;
        this.precedence = precedence;
        this.operator = operator;
    }

    /**
     * Setter for left child
     * @param leftChild The MerpNode to be set at this node's left child
     */
    public void setLeftChild(PIPCalcNode leftChild) {
        this.leftChild = leftChild;
    }

    /**
     * Setter for right child
     * @param rightChild The MerpNode to be set at this node's left child
     */
    public void setRightChild(PIPCalcNode rightChild) {
        this.rightChild = rightChild;
    }

    /**
     * Displays this node as prefix notation expression string
     * @return string representing the node as prefix notation
     */
    public String toPrefixString() {
        return "( " + operator + " " + leftChild.toPrefixString() + " " + rightChild.toPrefixString() + " )";
    }

    /**
     * Displays this node as infix notation expression string
     * @return string representing the node as infix notation
     */
    public String toInfixString() {
        return "( " + leftChild.toInfixString() + " " + operator + " " + rightChild.toInfixString()+ " )";
    }

    /**
     * Displays this node as prefix notation expression string
     * @return string representing the node as postfix notation
     */
    public String toPostfixString() {
        return "( " + leftChild.toPostfixString() + " " + rightChild.toPostfixString() + " " + operator + " )";
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
        return NodeType.BinaryOperation;
    }

}
