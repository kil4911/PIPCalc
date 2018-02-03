package processors;

import nodes.*;

import java.util.Stack;

/**
 * Created by King David Lawrence on 11/30/2017.
 * Class to process PIPCalc expressions using postfix notation
 */
public class PIPCalcPostfixProcessor extends PIPCalcProcessor {

    /**
     * constructor
     */
    public PIPCalcPostfixProcessor(){}

    /**
     * Constructs and assigns a PIPCalc tree from the provided list of
     * MerpNode tokens using postfix notation
     * @param tokens list of PIPCalcNodes used to create the parse tree
     */
    public void constructTree(java.util.ArrayList<String> tokens) {
        Stack<PIPCalcNode> new_stack = new Stack<>();
        int count = 0;
        while (count < tokens.size()) {
            PIPCalcNode n1 = createPIPCalcNode(tokens.get(count));
            if (n1.getNodeType() == PIPCalcNode.NodeType.Constant) {
                ConstantNode c = (ConstantNode) n1;
                new_stack.push(c);
            }
            else if (n1.getNodeType() == PIPCalcNode.NodeType.BinaryOperation) {
                BinaryOperatorNode bin = (BinaryOperatorNode) n1;
                bin.setRightChild(new_stack.pop());
                bin.setLeftChild(new_stack.pop());
                new_stack.push(bin);
            }
            else if (n1.getNodeType() == PIPCalcNode.NodeType.UnaryOperation) {
                UnaryOperatorNode u = (UnaryOperatorNode) n1;
                u.setChild(new_stack.pop());
                new_stack.push(u);
            }
            count++;
        }
        tree = new_stack.peek();
    }
}
