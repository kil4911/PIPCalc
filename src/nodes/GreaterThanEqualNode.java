package nodes;

/**
 * Created by King David Lawrence on 3/11/2017.
 * Node representing the greater than equal to operator
 */
public class GreaterThanEqualNode extends BooleanOperatorNode {
    //Fields inherited from class Nodes.BinaryOperatorNode:
    //leftChild, operator, precedence, rightChild

    /**
     * Constructor that sets the left/right children
     * and sets the operator to the string >=
     * @param left the MerpNode representing the left child
     * @param right the MerpNode representing the right child
     */
    public GreaterThanEqualNode(PIPCalcNode left,
                        PIPCalcNode right) {
        super(left, right,">=");
    }

    /**
     * Evaluates the node to determine its integer value  1 for true, zero for false
     * @return the integer value of this node
     */
    public int evaluate() {
        return leftChild.evaluate() >= rightChild.evaluate() ? 1 : 0;
    }


}
