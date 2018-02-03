package nodes;

/**
 * Created by King David Lawrence on 11/30/2017.
 * Negation PIPCalcNode
 */
public class NegationNode extends UnaryOperatorNode{
    //Fields inherited from class Nodes.UnaryOperatorNode:
    //child, operator, precedence

    //Methods inherited from class Nodes.BooleanOperatorNode
    //getPrecedence

    //Methods inherited from class Nodes.UnaryOperatorNode:
    //getNodeType, getPrecedence, isOperation, setChild,
    // toInfixString, toPostfixString, toPrefixString

    /**
     * Constructor that sets the left/right children
     * and sets the operator to the string _
     * The precedence is set to MULT_DIVIDE
     *  @param child the PIPCalcNode representing the left child
     */
    public NegationNode(PIPCalcNode child) {
        super(child, Precedence.MULT_DIVIDE, "_");
    }

    /**
     * Evaluates the node to determine its integer value
     * @return the integer value of this node
     */
    public int evaluate() {
        return -child.evaluate();
    }
}
