import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber2;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Program with implementation of {@code NaturalNumber} secondary operation
 * {@code root} implemented as static method.
 *
 * @author Ayman Abumaike
 *
 */
public final class NaturalNumberRoot {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private NaturalNumberRoot() {
    }

    /**
     * Updates {@code n} to the {@code r}-th root of its incoming value.
     *
     * @param n
     *            the number whose root to compute
     * @param r
     *            root
     * @updates n
     * @requires r >= 2
     * @ensures n ^ (r) <= #n < (n + 1) ^ (r)
     */
    public static void root(NaturalNumber n, int r) {
        assert n != null : "Violation of: n is  not null";
        assert r >= 2 : "Violation of: r >= 2";

        // Initialize NaturalNumber Variables
        NaturalNumber tooHigh = n.newInstance();
        NaturalNumber lowEnough = new NaturalNumber2(0);
        NaturalNumber guess = n.newInstance();

        //Declare useful numbers
        NaturalNumber one = new NaturalNumber2(1);

        NaturalNumber two = new NaturalNumber2(2);

        //Assign tooHigh to n and increment
        tooHigh.copyFrom(n);
        tooHigh.increment();

        // Find middle value between tooHigh and lowEnough
        guess.copyFrom(tooHigh);
        guess.add(lowEnough);
        guess.divide(two);

        //Set conditional for while: tooHigh - lowEnough
        NaturalNumber conditional = n.newInstance();
        conditional.copyFrom(tooHigh);
        conditional.subtract(lowEnough);

        //Loop through conditional: tooHigh - lowEnough > 1
        while (conditional.compareTo(one) > 0) {
            // get guess ^r
            NaturalNumber powerConditional = n.newInstance();
            powerConditional.copyFrom(guess);
            powerConditional.power(r);

            // Check if n >= guess^r
            if (n.compareTo(powerConditional) > 0
                    || n.compareTo(powerConditional) == 0) {
                //If so, set lowEnough equal to guess
                lowEnough.copyFrom(guess);

            } else {
                // Else, set tooHigh equal to guess
                tooHigh.copyFrom(guess);
            }

            guess.copyFrom(tooHigh);
            guess.add(lowEnough);
            guess.divide(two);

            // Reset conditional
            conditional.copyFrom(tooHigh);
            conditional.subtract(lowEnough);
        }

        // Copy value of lowEnough to be returned
        n.copyFrom(lowEnough);
    }

    // int lowEnough = 0;
    // int tooHigh = n + 1;
    // int x = (high - low) / 2;
    // while (tooHigh - lowEnough > 1) {
    //   if (n >= power(x, r)) {
    //     lowEnough = x;
    // } else {
    //   tooHigh = x;

    // }
    // x = (tooHigh + lowEnough) / 2;
    //   }
    //    return lowEnough;
    //  }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleWriter out = new SimpleWriter1L();

        final String[] numbers = { "0", "1", "13", "1024", "189943527", "0",
                "1", "13", "4096", "189943527", "0", "1", "13", "1024",
                "189943527", "82", "82", "82", "82", "82", "9", "27", "81",
                "243", "143489073", "2147483647", "2147483648",
                "9223372036854775807", "9223372036854775808",
                "618970019642690137449562111",
                "162259276829213363391578010288127",
                "170141183460469231731687303715884105727" };
        final int[] roots = { 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 15, 15, 15, 15, 15,
                2, 3, 4, 5, 15, 2, 3, 4, 5, 15, 2, 2, 3, 3, 4, 5, 6 };
        final String[] results = { "0", "1", "3", "32", "13782", "0", "1", "2",
                "16", "574", "0", "1", "1", "1", "3", "9", "4", "3", "2", "1",
                "3", "3", "3", "3", "3", "46340", "46340", "2097151", "2097152",
                "4987896", "2767208", "2353973" };

        for (int i = 0; i < numbers.length; i++) {
            NaturalNumber n = new NaturalNumber2(numbers[i]);
            NaturalNumber r = new NaturalNumber2(results[i]);
            root(n, roots[i]);
            if (n.equals(r)) {
                out.println("Test " + (i + 1) + " passed: root(" + numbers[i]
                        + ", " + roots[i] + ") = " + results[i]);
            } else {
                out.println("*** Test " + (i + 1) + " failed: root("
                        + numbers[i] + ", " + roots[i] + ") expected <"
                        + results[i] + "> but was <" + n + ">");
            }
        }

        out.close();
    }

}