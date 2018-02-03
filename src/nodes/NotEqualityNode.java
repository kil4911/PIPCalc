package nodes;

/**
 * Created by King David Lawrence on 11/30/2017.
 *Node representing the not equals operator
 */
public class NotEqualityNode extends BooleanOperatorNode{
    //Fields inherited from class Nodes.BinaryOperatorNode
    //leftChild, operator, precedence, rightChild

    //Methods inherited from class Nodes.BooleanOperatorNode
    //getPrecedence

    //Methods inherited from class Nodes.BinaryOperatorNode
    //getNodeType, isOperation, setLeftChild, setRightChild,
    //toInfixString, toPostfixString, toPrefixString

    /**
     * Constructor that sets the left/right children
     * and sets the operator to the string !=
     *  @param left the PIPCalcNode representing the left child
     * @param right the PIPCalcNode representing the right child
     */
    public NotEqualityNode(PIPCalcNode left,
                           PIPCalcNode right) {
        super(left, right, "!=");

    }

    /**
     * Evaluates the node to determine its integer value 1 for true, zero for false
     * @return the integer value of this node
     */
    public int evaluate() {
        return leftChild.evaluate() != rightChild.evaluate() ? 1 : 0;
    }
}
