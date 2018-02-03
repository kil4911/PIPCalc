package nodes;

/**
 * Created by King David Lawrence on 11/30/2017.
 * Square Root PIPCalcNode
 */
public class SquareRootNode extends UnaryOperatorNode{
    //Fields inherited from class Nodes.UnaryOperatorNode:
    //child, operator, precedence

    //Methods inherited from class Nodes.BooleanOperatorNode
    //getPrecedence

    //Methods inherited from class Nodes.UnaryOperatorNode:
    //getNodeType, getPrecedence, isOperation, setChild,
    // toInfixString, toPostfixString, toPrefixString

    /**
     * Constructor that sets the left child
     * and sets the operator to the string @
     * The precedence is set to POWER
     *  @param child the PIPCalcNode representing the left child
     */
    public SquareRootNode(PIPCalcNode child) {
        super(child, Precedence.POWER, "@");
    }

    /**
     * Evaluates the node to determine its integer value
     * Errors if the child evaluates to a negative number
     * @return the integer value of this node
     */
    public int evaluate() {
        //if (child.evaluate() < 0) Errors.error("can't take square root of negative number", this);
        return (int) Math.sqrt(child.evaluate());
    }
}
