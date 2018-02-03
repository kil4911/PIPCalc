package tester;

import nodes.*;

/**
 * Used to test the various nodes in PIPCalc
 */
public class NodeTester {

    /**
     * Interface for representing test parameters
     */
    private abstract class TestParams{
        Class<?> c;
        PIPCalcNode left;
        PIPCalcNode right;
        int expected;
        String operator;
        boolean displaySuccess;
        PIPCalcNode node;
        boolean isBinary;

        private TestParams(Class c, PIPCalcNode left,
                           PIPCalcNode right, int expected,
                           String operator, boolean displaySuccess,
                           boolean isBinary) {
            this.c = c;
            this.left = left;
            this.right = right;
            this.expected = expected;
            this.displaySuccess = displaySuccess;
            this.operator = operator;
            this.isBinary = isBinary;
        }

        /**
         * Start the test on this set of testing data
         */
        private void test(){
            testNode(this);
        }

        private boolean testString(String value, String expected,
                                   String type){
            if(value.equals(expected)){
                if(displaySuccess) {
                    System.out.println(type + " test of "
                            + c.getName() + " passed.");
                    System.out.println("\tLeft Child: "
                            + left.getClass().getName());
                    if(isBinary)
                        System.out.println("\tRight Child: "
                                + right.getClass().getName());
                    System.out.println();
                }
                return true;
            }
            else{
                System.out.println( type + " test of "
                        + c.getName() + " failed. Got "
                        + value + " expected " + expected);
                System.out.println("\tLeft Child: "
                        + left.getClass().getName());
                if(isBinary)
                    System.out.println("\tRight Child: "
                            + right.getClass().getName());
                System.out.println();
                return false;
            }
        }

        /**
         * Tests the given set of testing params
         * Functionality tested:
         *     evaluate
         *     toInfixString
         *     toPrefixString
         *     toPostfixString
         * @param params the params to test.
         */
        private void testNode(TestParams params){
            try {
                boolean testPassed;

                // Test Evaluate
                int value = node.evaluate();
                testPassed = testString(Integer.toString(value),
                        Integer.toString(params.expected),
                        "Eval");

                // test InfixString
                String infix = node.toInfixString();
                String result = makeInfixString(params.operator,
                        left, right);
                testPassed = testPassed &&
                        testString(infix, result,
                                "toInfixString");

                // test PrefixString
                String prefix = node.toPrefixString();
                result = makePrefixString(params.operator,
                        left, right);
                testPassed = testPassed &&
                        testString(prefix, result,
                                "toPrefixString");

                // test PrefixString
                String postfix = node.toPostfixString();
                result = makePostfixString(params.operator,
                        left, right);
                testPassed = testPassed &&
                        testString(postfix, result,
                                "toPostfixString");

                if(testPassed){
                    passed += 1;
                }
                else{
                    failed += 1;
                }
                total += 1;

            }catch (Exception e){
                e.printStackTrace();
            }
        }

        /**
         * Constructs the unary infix string.
         * @param operator the unary operator
         * @param left the PIPCalcNode representing the child.
         * @return string representing the infix notation
         */
        private String makeInfixString(String operator, PIPCalcNode left){
            return "(" + operator + " " + left.toInfixString() + ")";
        }

        /**
         * Constructs the unary prefix string.
         * @param operator the unary operator
         * @param left the PIPCalcNode representing the child.
         * @return string representing the prefix notation
         */
        private String makePrefixString(String operator, PIPCalcNode left){
            return operator + " " + left.toPrefixString();
        }

        /**
         * Constructs the unary postfix string.
         * @param operator the unary operator
         * @param left the PIPCalcNode representing the child.
         * @return string representing the postfix notation
         */
        private String makePostfixString(String operator, PIPCalcNode left){
            return left.toPostfixString() + " " + operator;
        }

        /**
         * Constructs the binary infix string.
         * @param operator the binary operator
         * @param left the PIPCalcNode representing the  left child
         * @param right the PIPCalcNode representing the right child
         * @return string representing the infix notation
         */
        private String makeInfixString(String operator,
                                       PIPCalcNode left,
                                       PIPCalcNode right){
            if(right == null){
                return makeInfixString(operator, left);
            }
            return "(" + left.toInfixString()
                    + " " + operator + " "
                    + right.toInfixString() + ")";
        }

        /**
         * Constructs the binary prefix string.
         * @param operator the binary operator
         * @param left the PIPCalcNode representing the left child
         * @param right the PIPCalcNode representing the right child
         * @return string representing the prefix notation
         */
        private String makePrefixString(String operator,
                                        PIPCalcNode left,
                                        PIPCalcNode right){
            if(right == null){
                return makePrefixString(operator, left);
            }
            return operator + " " + left.toPrefixString()
                    + " " + right.toPrefixString();
        }

        /**
         * Constructs the binary postfix string.
         * @param operator the binary operator
         * @param left the PIPCalcNode representing the left child
         * @param right the PIPCalcNode representing the right child
         * @return string representing the postfix notation
         */
        private String makePostfixString(String operator,
                                         PIPCalcNode left,
                                         PIPCalcNode right){
            if(right == null){
                return makePostfixString(operator, left);
            }
            return left.toPostfixString() + " "
                    + right.toPostfixString() + " "
                    + operator;
        }
    }

    /**
     * Class for testing binary nodes
     */
    private class TestBinaryParams extends TestParams{

        private TestBinaryParams(Class<?> c, PIPCalcNode left,
                                 PIPCalcNode right, int expected,
                                 String operator, boolean displaySuccess)  throws Exception{
            super(c,left,right,expected,
                    operator,displaySuccess,
                    true);
            node = (BinaryOperatorNode)
                    (c.getConstructor(PIPCalcNode.class, PIPCalcNode.class)
                            .newInstance(left, right));
        }
    }

    /**
     * Class for testing unary nodes
     */
    private class TestUnaryParams extends TestParams{

        private TestUnaryParams(Class<?> c, PIPCalcNode left, int expected,
                                String operator, boolean displaySuccess) throws Exception {
            super(c,left,null,expected,
                    operator,displaySuccess,
                    false);
            node = (UnaryOperatorNode)
                    (c.getConstructor(PIPCalcNode.class).newInstance(left));
        }
    }

    private int passed;
    private int failed;
    private int total;
    private boolean displaySuccess;

    private NodeTester(boolean displaySuccess){
        this.passed = 0;
        this.failed = 0;
        this.total = 0;
        this.displaySuccess = displaySuccess;
    }

    /**
     * Function for testing all binary nodes with the given
     * children and symbol table.
     * @param left PIPCalcNode representing the left child to test with
     * @param right PIPCalcNode representing the right child to test with
     */
    private void testBinaryNodes(PIPCalcNode left, PIPCalcNode right){
        int leftValue = left.evaluate();
        int rightValue = right.evaluate();
        try {
            TestBinaryParams[] tests = new TestBinaryParams[]{
                    new TestBinaryParams(MultiplicationNode.class,
                            left, right, leftValue * rightValue,
                            "*", displaySuccess),
                    new TestBinaryParams(DivisionNode.class, left, right,
                            leftValue / rightValue, "//",
                            displaySuccess),
                    new TestBinaryParams(AdditionNode.class, left, right,
                            leftValue + rightValue, "+",
                            displaySuccess),
                    new TestBinaryParams(SubtractionNode.class, left, right,
                            leftValue - rightValue, "-",
                            displaySuccess),
                    new TestBinaryParams(PowerNode.class, left, right,
                            (int) Math.pow(leftValue, rightValue), "^",
                            displaySuccess),
                    new TestBinaryParams(LessThanNode.class, left, right,
                            (leftValue < rightValue) ? 1 : 0, "<",
                            displaySuccess),
                    new TestBinaryParams(LessThanEqualNode.class, left, right,
                            (leftValue <= rightValue) ? 1 : 0, "<=",
                            displaySuccess),
                    new TestBinaryParams(GreaterThanNode.class, left, right,
                            (leftValue > rightValue) ? 1 : 0, ">",
                            displaySuccess),
                    new TestBinaryParams(GreaterThanEqualNode.class, left, right,
                            (leftValue >= rightValue) ? 1 : 0, ">=",
                            displaySuccess),
                    new TestBinaryParams(EqualityNode.class, left, right,
                            (leftValue == rightValue) ? 1 : 0, "==",
                            displaySuccess),
                    new TestBinaryParams(NotEqualityNode.class, left, right,
                            (leftValue != rightValue) ? 1 : 0, "!=",
                            displaySuccess)
            };

            for(TestParams t: tests){
                t.test();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Function for testing all unary nodes with the given
     * children and symbol table.
     * @param left PIPCalcNode representing the child to test with
     */
    private void testUnaryNodes(PIPCalcNode left){
        try {
            int leftValue = left.evaluate();
            TestUnaryParams[] tests = new TestUnaryParams[]{
                    new TestUnaryParams(AbsValueNode.class,
                            left, Math.abs(leftValue),
                            "|", displaySuccess),
                    new TestUnaryParams(NegationNode.class,
                            left, -1 * leftValue,
                            "_", displaySuccess),
                    new TestUnaryParams(SquareRootNode.class,
                            left, (int) Math.sqrt(leftValue),
                            "@", displaySuccess)
            };

            for (TestParams t : tests) {
                t.test();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Prints the results of the testing
     */
    private void getResults(){
        System.out.println(passed + " tests passed of " + total);
        System.out.println(failed + " tests failed of " + total);
    }


    /**
     * Main to run the testing simulation.
     * Displaying of success cases can be toogled on and off.
     * Off by default.
     * @param args display success toggle true
     */
    public static void main(String[] args) {

        boolean displaySuccess;
        displaySuccess = args.length == 1 && args[0].equals("true");

        NodeTester tester = new NodeTester(displaySuccess);

        // Test two constants
        PIPCalcNode left = new ConstantNode(3);
        PIPCalcNode right = new ConstantNode(5);
        tester.testBinaryNodes(left, right);

        tester.testBinaryNodes(left, right);

        // Test left child as Addition node
        PIPCalcNode childLeft = new ConstantNode(4);
        PIPCalcNode childRight = new ConstantNode(3);
        left = new AdditionNode(childLeft, childRight);

        tester.testBinaryNodes(left, right);

        // Test left child as Multiplication node
        left = new MultiplicationNode(childLeft, childRight);

        tester.testBinaryNodes(left, right);

        // Test left child as Power node
        left = new PowerNode(childLeft, childRight);

        tester.testBinaryNodes(left, right);

        // Test left child as Addition node, right child as AdditionNode
        PIPCalcNode childLeft2 = new ConstantNode(6);
        PIPCalcNode childRight2 = new ConstantNode(7);
        childLeft = new ConstantNode(4);
        childRight = new ConstantNode(3);
        left = new AdditionNode(childLeft, childRight);
        right = new AdditionNode(childLeft2, childRight2);

        tester.testBinaryNodes(left, right);

        // Test left child as Multiplication node, right child as AdditionNode
        left = new MultiplicationNode(childLeft, childRight);
        right = new AdditionNode(childLeft2, childRight2);

        tester.testBinaryNodes(left, right);

        // Test left child as Power node, right child as AdditionNode
        left = new PowerNode(childLeft, childRight);
        right = new AdditionNode(childLeft2, childRight2);

        tester.testBinaryNodes(left, right);

        //Test larger expression
        left = new PowerNode(
                new MultiplicationNode(
                        new AdditionNode(
                                new ConstantNode(-1),
                                new ConstantNode(5)),
                        new DivisionNode(
                                new SubtractionNode(
                                        new ConstantNode(-2),
                                        new ConstantNode(5)),
                                new ConstantNode(5))
                ),
                new ConstantNode(5));
        right = new GreaterThanNode(
                new ConstantNode(9),
                new ConstantNode(8));

        tester.testBinaryNodes(left, right);

        // Test unary nodes
        left = new ConstantNode(5);
        tester.testUnaryNodes(left);

        tester.testUnaryNodes(left);

        // Test binary in unary
        childLeft = new ConstantNode(5);
        childRight = new ConstantNode(6);
        left = new AdditionNode(childLeft, childRight);
        tester.testUnaryNodes(left);

        left = new PowerNode(left, right);
        tester.testUnaryNodes(left);

        // test unary in binary
        right = new AbsValueNode(new ConstantNode(-5));
        tester.testBinaryNodes(left, right);

        tester.testBinaryNodes(right, left);

        tester.getResults();
    }
}