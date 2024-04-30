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
public final class Newton2 {

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private Newton2() {
    }

    /**
     * Computes estimate of square root of x to within relative error 0.01%.
     *
     * @param x
     *            positive number to compute square root of
     * @return estimate of square root
     */
    private static double sqrt(double x) {

        double guess = x;
        final double error = 0.0001;
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

        out.print("Do you want me to calculate a square root: ");
        String response = in.nextLine();
        while (response.charAt(0) == 'y') {
            out.print("Give me a positive double: ");
            double sort = in.nextDouble();

            double sqrt = sqrt(sort);
            out.println("Your square root is " + sqrt);
            out.println();

            out.print("Do you want me to calculate a square root: ");
            response = in.nextLine();

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
