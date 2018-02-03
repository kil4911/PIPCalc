package nodes;

/**
 * Created by King David Lawrence on 11/30/2017.
 *Absolute Value PIPCalcNode
 */
public class AbsValueNode extends UnaryOperatorNode{

    //inherited fields from UnaryOperator:
    //child, operator, precedence

    //Methods inherited from class Nodes.UnaryOperatorNode
    //getNodeType, getPrecedence, isOperation, setChild,
    // toInfixString, toPostfixString, toPrefixString

    /**
     * Constructor that sets the left child
     * and sets the operator to the string |
     * The precedence is set to MULT_DIVIDE
     * @param child PIPCalcNode that is the child of this node
     */
    public AbsValueNode(PIPCalcNode child) {
        super(child,Precedence.MULT_DIVIDE, "|");
    }

    /**
     * Evaluates the node to determine its integer value
     * @return the integer value of this node
     */
    public int evaluate() {
        return Math.abs(child.evaluate());
    }
}
