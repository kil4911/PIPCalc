package nodes;

/**
 * Created by King David Lawrence on 11/30/2017.
 *Power Node
 */
public class PowerNode extends BinaryOperatorNode{
    //Fields inherited from class Nodes.BinaryOperatorNode
    //leftChild, operator, precedence, rightChild

    //Methods inherited from class Nodes.BinaryOperatorNode
    //getNodeType, isOperation, setLeftChild, setRightChild,
    // toInfixString, toPostfixString, toPrefixString


    /**
     * Constructor that sets the left/right children
     * and sets the operator to the string ^
     * The precedence is set to POWER
     *  @param left the MerpNode representing the left child
     * @param right the MerpNode representing the right child
     */
    public PowerNode(PIPCalcNode left,
                     PIPCalcNode right) {
        super(left, right, Precedence.POWER, "^");
    }

    /**
     * Evaluates the node to determine its integer value
     * @return the integer value of this node
     */
    public int evaluate() {
        return (int) Math.pow( leftChild.evaluate(), rightChild.evaluate() );
    }



}
