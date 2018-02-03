package nodes;

/**
 * Created by King David Lawrence on 11/30/2017.
 * Abstract class to represent a boolean operator
 * Has a left/right child, precedence, and operator.
 * Assumes non-zero values are true, zero is false.
 */
public abstract class BooleanOperatorNode extends BinaryOperatorNode {
    //Directly known subclasses:
    //EqualityNode, GreaterThanEqualNode, GreaterThanNode,
    // LessThanEqualNode, LessThanNode, NotEqualityNode

    //Fields inherited from class Nodes.BinaryOperatorNode
    //leftChild, operator, precedence, rightChild

    //Methods inherited from class Nodes.BinaryOperatorNode:
    //getNodeType, isOperation, setLeftChild, setRightChild,
    // toInfixString, toPostfixString, toPrefixString

    /**
     * Default Constructor
     */
    public BooleanOperatorNode(){};

    /**
     * Constructor for Boolean operation nodes
     * The precedence is set to BOOLEAN
     * @param left the left child for this operation
     * @param right the right child for this operation
     * @param operator the string representing the operation for this node
     */
    public BooleanOperatorNode(PIPCalcNode left,
                               PIPCalcNode right,
                               String operator) {
        super(left,right,Precedence.BOOLEAN, operator);
    }

    /**
     *Returns the precedence of this node
     * @return returns the precedence of BOOLEAN
     */
    @Override
    public int getPrecedence() {
        return precedence.getPrecedence();
    }
}
