package processors;

import nodes.*;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by King David Lawrence on 11/30/2017.
 */
public class PIPCalcPrefixProcessor extends PIPCalcProcessor {

    public PIPCalcPrefixProcessor() {}

    /**
     * Constructs and assigns a PIPCalc tree from the provided list of
     * MerpNode tokens using prefix notation
     * @param tokens list of PIPCalcNodes used to create the parse tree
     */
    public void constructTree(ArrayList<String> tokens) {
        Stack<PIPCalcNode> stack = new Stack<>();
        if (tokens.size() == 1 && tokens.get(0).equals("")) {
            tree = null;
            return;
        }
        int countDown = tokens.size()-1;
        while (countDown > -1 ) {
            PIPCalcNode n = createPIPCalcNode(tokens.get(countDown));
            if (n.getNodeType() == PIPCalcNode.NodeType.Constant) {
                ConstantNode c = (ConstantNode) n;
                stack.push(c);
            }
            if (n.getNodeType() == PIPCalcNode.NodeType.BinaryOperation) {
                BinaryOperatorNode b = (BinaryOperatorNode) n;
                b.setRightChild(stack.pop());
                b.setLeftChild(stack.pop());
                stack.push(b);
            }
            if (n.getNodeType() == PIPCalcNode.NodeType.UnaryOperation) {
                UnaryOperatorNode u = (UnaryOperatorNode) n;
                u.setChild(stack.pop());
                stack.push(u);
            }
            countDown--;
        }
        tree = stack.peek();
    }

}
