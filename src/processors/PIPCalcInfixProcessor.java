package processors;

import nodes.*;

import java.util.LinkedList;
import java.util.Stack;
import java.util.Queue;

/**
 * Created by King David Lawrence on 01/20/2018.
 * Class to process PIPCalc expressions using infix notation
 */
public class PIPCalcInfixProcessor extends PIPCalcProcessor {

    public PIPCalcInfixProcessor(){}

    /**
     *Constructs and assigns a PIPCalc tree from the provided
     * list of PIPCalcNode tokens using infix notation
     * @param tokens list of PIPCalcNodes used to create the parse tree
     */
    public void constructTree(java.util.ArrayList<String> tokens) {
        Stack<PIPCalcNode> oper_st = new Stack<>();
        Queue<PIPCalcNode> output_queue = new LinkedList<>();

        int count = 0;
        while (count < tokens.size()) {
            String token = tokens.get(count);

            if (token.equals(")") || token.equals("(")) continue;

            PIPCalcNode node = createPIPCalcNode(token);

            if (node.getNodeType() == PIPCalcNode.NodeType.Constant) {
                output_queue.add(node);
            }
            if (node.getNodeType() == PIPCalcNode.NodeType.BinaryOperation || node.getNodeType() == PIPCalcNode.NodeType.UnaryOperation) {

                while (!oper_st.empty() && (oper_st.peek().getPrecedence() < node.getPrecedence()
                        || (oper_st.peek().getPrecedence() == node.getPrecedence() && (!(node instanceof PowerNode))))) {
                    output_queue.add(oper_st.pop());
                }
                oper_st.push(node);
            }
            count++;
        }
        if (count == tokens.size()) {
            while(!oper_st.empty() && (oper_st.peek().getNodeType() == PIPCalcNode.NodeType.BinaryOperation
                    || oper_st.peek().getNodeType() == PIPCalcNode.NodeType.UnaryOperation)) {
                output_queue.add(oper_st.pop());
            }
        }
        //Parse Postfix to tree
        Stack<PIPCalcNode> new_stack = new Stack<>();
        while (!output_queue.isEmpty()) {
            PIPCalcNode n1 = output_queue.remove();
            switch (n1.getNodeType()) {
                case Constant:
                    ConstantNode c = (ConstantNode) n1;
                    new_stack.push(c);
                    break;
                case UnaryOperation:
                    UnaryOperatorNode u = (UnaryOperatorNode) n1;
                    u.setChild(new_stack.pop());
                    new_stack.push(u);
                    break;
                case BinaryOperation:
                    BinaryOperatorNode bin = (BinaryOperatorNode) n1;
                    bin.setRightChild(new_stack.pop());
                    //if empty stack for next pop, syntax error
                    bin.setLeftChild(new_stack.pop());
                    new_stack.push(bin);
                    break;
            }
        }
        tree = new_stack.peek(); 
    }
}
