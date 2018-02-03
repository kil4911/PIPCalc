package nodes;

/**
 * Created by King David Lawrence on 3/11/2017
 Node representing the greater than operator *
 */
public class GreaterThanNode extends BooleanOperatorNode{
    //Fields inherited from class Nodes.BinaryOperatorNode:
    //leftChild, operator, precedence, rightChild

   //Methods inherited from class Nodes.BooleanOperatorNode
   //getPrecedence

   //Methods inherited from class Nodes.BinaryOperatorNode
    //getNodeType, isOperation, setLeftChild, setRightChild,
    // toInfixString, toPostfixString, toPrefixString

    /**
     * Constructor that sets the left/right children
     * and sets the operator to the string >
     * @param left the MerpNode representing the left child
     * @param right the MerpNode representing the right child
     */
    public GreaterThanNode(PIPCalcNode left,
                           PIPCalcNode right) {
        super(left,right,">");
    }

    /**
     * Evaluates the node to determine its integer value  1 for true, zero for false
     * @return the integer value of this node
     */
    public int evaluate() {
        return leftChild.evaluate() > rightChild.evaluate() ? 1 : 0;
    }
}