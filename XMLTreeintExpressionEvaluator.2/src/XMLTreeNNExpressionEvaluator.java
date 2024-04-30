import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber2;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.utilities.Reporter;
import components.xmltree.XMLTree;
import components.xmltree.XMLTree1;

/**
 * Put a short phrase describing the program here.
 *
 * @author Hussam Mohamednour
 *
 */
public final class XMLTreeNNExpressionEvaluator {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private XMLTreeNNExpressionEvaluator() {
    }

    /**
     * Evaluate the given expression.
     *
     * @param exp
     *            the {@code XMLTree} representing the expression
     * @return the value of the expression
     * @requires <pre>
     * [exp is a subtree of a well-formed XML arithmetic expression]  and
     *  [the label of the root of exp is not "expression"]
     * </pre>
     * @ensures evaluate = [the value of the expression]
     */
    private static NaturalNumber evaluate(XMLTree exp) {
        assert exp != null : "Violation of: exp is not null";

        // Initialize and declare return value
        NaturalNumber number = new NaturalNumber2(0);

        // Extract number from number attribute tag
        if (exp.label().equals("number") && exp.hasAttribute("value")) {
            number.setFromString(exp.attributeValue("value"));
        }

        /*
         * If tag is plus, set value equal to the recursive addition of its
         * children
         */
        if (exp.label().equals("plus")) {
            NaturalNumber value = number.newInstance();
            value.copyFrom(evaluate(exp.child(0)));
            value.add(evaluate(exp.child(1)));

            number.copyFrom(value);
        }

        /*
         * If tag is minus, add the recursive subtraction of its children to
         * return value
         */
        if (exp.label().equals("minus")) {
            NaturalNumber value = number.newInstance();
            NaturalNumber value2 = number.newInstance();

            value.copyFrom(evaluate(exp.child(0)));
            value2.copyFrom(evaluate(exp.child(1)));

            // Throw error if difference is negative
            if (value2.compareTo(value) > 0) {
                Reporter.fatalErrorToConsole(
                        "NaturalNumber cannot be negative.");
            }

            value.subtract(value2);
            number.add(value);
        }

        /*
         * If tag is times, add the recursive multiplication of its children to
         * return value
         */
        if (exp.label().equals("times")) {
            NaturalNumber value = number.newInstance();
            value.copyFrom(evaluate(exp.child(0)));
            value.multiply(evaluate(exp.child(1)));

            number.add(value);
        }

        /*
         * If tag is divide, add the recursive division of its children to
         * return value
         */
        if (exp.label().equals("divide")) {
            NaturalNumber value = number.newInstance();
            NaturalNumber denominator = number.newInstance();
            NaturalNumber zero = number.newInstance();

            value.copyFrom(evaluate(exp.child(0)));
            denominator.copyFrom(evaluate(exp.child(1)));

            // Throw error if zero denominator
            if (denominator.compareTo(zero) == 0) {
                Reporter.fatalErrorToConsole("Cannot divide by 0");
            }

            value.divide(denominator);
            number.add(value);
        }

        // Return value
        return number;
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();

        out.print("Enter the name of an expression XML file: ");
        String file = in.nextLine();
        while (!file.equals("")) {
            XMLTree exp = new XMLTree1(file);
            out.println(evaluate(exp.child(0)));
            out.print("Enter the name of an expression XML file: ");
            file = in.nextLine();
        }

        in.close();
        out.close();
    }

}
