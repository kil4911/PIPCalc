package nodes;

/**
 * Created by kdl4u_000 on 11/30/2017.
 */
/**
 * Interface for all PIPCalcNodes
 */
public interface PIPCalcNode {

    /**
     * Enum to represent Nodes types
     */
    public enum NodeType{
        BinaryOperation,
        UnaryOperation,
        Constant,
    }

    /**
     * Evaluates the node
     * @return integer value of the node
     */
    public int evaluate();

    /**
     * Constructs prefix representation of the node
     * @return prefix representation of the node
     */
    public String toPrefixString();

    /**
     * Constructs infix representation of the node
     * @return infix representation of the node
     */
    public String toInfixString();

    /**
     * Constructs postfix representation of the node
     * @return prefix representation of the node
     */
    public String toPostfixString();

    /**
     * gets the precedence of this node
     * @return integer value of the node's precedence
     */
    public int getPrecedence();

    /**
     * determines if the node is an operation node
     * @return - true if an operation node, false otherwise
     */
    public boolean isOperation();

    /**
     * Determines the node type
     * @return the type of this node
     */
    public NodeType getNodeType();
}


