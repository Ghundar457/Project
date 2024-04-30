import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.utilities.FormatChecker;

/**
 * Simple HelloWorld program (clear of Checkstyle and FindBugs warnings).
 *
 * @author Ayman Abumaike
 */
public final class ABCDGuesser1 {

    /**
     * Default constructor--private to prevent instantiation.
     */
    private ABCDGuesser1() {

    }

    /**
     * Repeatedly asks the user for a positive real number until the user enters
     * one. Returns the positive real number.
     *
     * @param in
     *            the input stream
     * @param out
     *            the output stream
     * @return a positive real number entered by the user
     */
    private static double getPositiveDouble(SimpleReader in, SimpleWriter out) {

        double num = -1; // Initialize num to a non-positive value

        //if the given number is less than one
        while (num <= 0) {
            out.println("Enter a positive double: ");
            String userInput = in.nextLine();

            if (FormatChecker.canParseDouble(userInput)) {
                num = Double.parseDouble(userInput);
            }

            if (num <= 0) {
                out.println("You must enter a positive double. Try again: ");
            }
        }

        return num;
    }

    /**
     * Repeatedly asks the user for a positive real number not equal to 1.0
     * until the user enters one. Returns the positive real number.
     *
     * @param in
     *            the input stream
     * @param out
     *            the output stream
     * @return a positive real number not equal to 1.0 entered by the user
     */
    private static double getPositiveDoubleNotOne(SimpleReader in,
            SimpleWriter out) {

        out.println("Enter a positive double: ");
        String numAsString = in.nextLine();

        //if number cannot be a double or it has the number 1
        //only runs when input is correct
        while (!FormatChecker.canParseDouble(numAsString)
                || Double.parseDouble(numAsString) < 1) {
            out.println(
                    "You must enter a positive double (not 1). Try again: ");
            numAsString = in.nextLine(); //user input will be the new input

        }

        //initializes the double to the double name "num"
        double num = Double.parseDouble(numAsString);

        return num; //returns positive double that is not 1

    }

    /**
     * Repeatedly asks the user for a positive real number not equal to 1.0
     * until the user enters one. Returns the positive real number.
     *
     * @param w
     *            first number
     * @param x
     *            second number
     * @param y
     *            third number
     * @param z
     *            fourth number
     * @param u
     *            number to estimate
     *
     * @return a positive real number not equal to 1.0 entered by the user
     */
    private static double deJager(double w, double x, double y, double z,
            double u) {

        SimpleWriter out = new SimpleWriter1L();

        //array of doubles for the powers
        final double[] powers = { -5, -4, -3, -2, -1, -1 / 2.0, -1 / 3.0,
                -1 / 4.0, 0, 1 / 4.0, 1 / 3.0, 1 / 2.0, 1, 2, 3, 4, 5 };

        //these two doubles will help us calculate the smallest error later
        double closestSoFar = 0;
        double estimate = 0;

        //exponents needed for the 17 numbers
        int a = 0;
        int b = 0;
        int c = 0;
        int d = 0;

        //user 4 numbers
        double exp1 = 0;
        double exp2 = 0;
        double exp3 = 0;
        double exp4 = 0;

        //each one of these while loops iterate through the loop to see if the
        //the number at the given point will satisfy the estimate
        while (a < powers.length) {

            while (b < powers.length) {

                while (c < powers.length) {

                    while (d < powers.length) {

                        //estimate being calculated using the powers
                        estimate = Math.pow(w, powers[a])
                                * Math.pow(x, powers[b])
                                * Math.pow(y, powers[c])
                                * Math.pow(z, powers[d]);

                        //if the estimate is less than the closest
                        //the closest must be the estimate
                        if (Math.abs(u - estimate) < Math
                                .abs(u - closestSoFar)) {
                            closestSoFar = estimate;

                            //exponents declared with the powers
                            exp1 = powers[a];
                            exp2 = powers[b];
                            exp3 = powers[c];
                            exp4 = powers[d];
                        }
                        d++;
                    }
                    c++;
                    d = 0;
                }
                b++;
                c = 0;
            }
            a++;
            b = 0;
        }
        a = 0;

        final int hundred = 100; //hundred for magic number

        //calculates the percentage error
        double error = Math.abs((closestSoFar - u) / u) * hundred;

        //estimate for your number which is the approximation
        out.println("Estimate for " + u + " is ");
        out.println(closestSoFar, 1, false);

        //prints out the exponents
        out.println("The exponents are " + exp1 + " " + exp2 + " " + exp3 + " "
                + exp4);

        //the percentage error
        out.print("The % error is ");

        //prints out the error
        out.print(error, 2, false);
        out.println(" %");
        out.close();

        return closestSoFar; //returns the double with smallest error

    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments; unused here
     */
    public static void main(String[] args) {
        SimpleWriter out = new SimpleWriter1L();
        SimpleReader in = new SimpleReader1L();

        // get number to estimate
        out.println("What value do you want to estimate? ");
        double u = getPositiveDouble(in, out);

        // get numbers to use for estimation ensures they are what is prompted
        out.println("Enter 4 numbers to use for the estimate: ");
        double w = getPositiveDoubleNotOne(in, out);
        double x = getPositiveDoubleNotOne(in, out);
        double y = getPositiveDoubleNotOne(in, out);
        double z = getPositiveDoubleNotOne(in, out);

        // perform de jager calculations in external method
        deJager(w, x, y, z, u);

        out.close();
    }

}
