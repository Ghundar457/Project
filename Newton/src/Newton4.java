import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Put a short phrase describing the program here.
 *
 * @author Ayman Abumaike
 *
 */
public final class Newton4 {

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private Newton4() {
    }

    /**
     * Computes estimate of square root of x to within relative error 0.01%.
     *
     * @param x
     *            positive number to compute square root of
     * @param error
     *            positive number for error
     * @return estimate of square root
     */
    private static double sqrt(double x, double error) {

        double guess = x;
        if (guess != 0) {
            while (!(Math.abs(guess * guess - x) / x < error * error)) {

                guess = (guess + x / guess) / 2;

            }
        }
        return guess;

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

        out.print("Enter a number to find the square root: ");
        double userValue = in.nextDouble();

        while (userValue >= 0) {

            out.print("Enter a value for error: ");
            double error = in.nextDouble();

            double calculatedSqrRoot = sqrt(userValue, error);
            if (calculatedSqrRoot >= 0) {
                out.println("The square root of " + userValue + " would be "
                        + calculatedSqrRoot);
            }

            out.println();
            out.print("Enter a number to find the square root: ");
            userValue = in.nextDouble();
        }

        /*
         * Put your main program code here; it may call myMethod as shown
         */
        /*
         * Close input and output streams
         */
        in.close();
        out.close();
    }

}
