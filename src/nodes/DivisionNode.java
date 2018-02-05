package nodes;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;

/**
 * Created by King David Lawrence on 3/11/2017.
 * Division Node
 */
public class DivisionNode extends BinaryOperatorNode{
    //Fields inherited from class Nodes.BinaryOperatorNode:
    //leftChild, operator, precedence, rightChild

    //Methods inherited from class Nodes.BinaryOperatorNode:
    //getNodeType, getPrecedence, isOperation, setLeftChild, setRightChild,
    // toInfixString, toPostfixString, toPrefixString

    /**
     * Constructor that sets the left/right children
     * and sets the operator to the string //
     * The precedence is set to MULT_DIVIDE
     * @param left the MerpNode representing the left child
     * @param right the MerpNode representing the right child
     */
    public DivisionNode(PIPCalcNode left,
                        PIPCalcNode right) {
        super(left, right, Precedence.MULT_DIVIDE, "//");
    }

    /**
     * Evaluates the node to determine its integer value
     * Errors if the right child evaluates to zero
     * @return the integer value of this node
     */
    public int evaluate() {
        return leftChild.evaluate() / rightChild.evaluate();
    }

}
