package nodes;

/**
 * Created by King David Lawrence on 11/30/2017.
 * Addition PIPCalcNode
 */
public class AdditionNode extends BinaryOperatorNode{

    //Inherited fields form BinaryOperatorNode:
    //leftChild, operator, precedence, rightChild

    //Methods inherited from class Nodes.BinaryOperatorNode
    //getNodeType, getPrecedence, isOperation, setLeftChild, setRightChild,
    //toInfixString, toPostfixString, toPrefixString

    /**
     *Constructor that sets the left/right children
     * and sets the operator to the string +
     * The precedence is set to ADD_SUBTRACT
     * @param left the MerpNode representing the left child
     * @param right the MerpNode representing the right child
     */
    public AdditionNode(PIPCalcNode left, PIPCalcNode right) {
        super(left,right, Precedence.ADD_SUBTRACT, "+");
    }

    /**
     * Evaluates the node to determine its integer value
     * @return the integer value of this node
     */
    public int evaluate() {
        int left = super.leftChild.evaluate();
        int right = rightChild.evaluate();

        return left+right;
    }

}
