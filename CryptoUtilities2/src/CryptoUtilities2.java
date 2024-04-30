import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber2;
import components.random.Random;
import components.random.Random1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Utilities that could be used with RSA cryptosystems.
 *
 * @author Hussam Mohamednour
 *
 */
public final class CryptoUtilities2 {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private CryptoUtilities2() {
    }

    /**
     * Useful constant, not a magic number: 3.
     */
    private static final int THREE = 3;

    /**
     * Pseudo-random number generator.
     */
    private static final Random GENERATOR = new Random1L();

    /**
     * Returns a random number uniformly distributed in the interval [0, n].
     *
     * @param n
     *            top end of interval
     * @return random number in interval
     * @requires n > 0
     * @ensures <pre>
     * randomNumber = [a random number uniformly distributed in [0, n]]
     * </pre>
     */
    public static NaturalNumber randomNumber(NaturalNumber n) {
        assert !n.isZero() : "Violation of: n > 0";
        final int base = 10;
        NaturalNumber result;
        int d = n.divideBy10();
        if (n.isZero()) {
            /*
             * Incoming n has only one digit and it is d, so generate a random
             * number uniformly distributed in [0, d]
             */
            int x = (int) ((d + 1) * GENERATOR.nextDouble());
            result = new NaturalNumber2(x);
            n.multiplyBy10(d);
        } else {
            /*
             * Incoming n has more than one digit, so generate a random number
             * (NaturalNumber) uniformly distributed in [0, n], and another
             * (int) uniformly distributed in [0, 9] (i.e., a random digit)
             */
            result = randomNumber(n);
            int lastDigit = (int) (base * GENERATOR.nextDouble());
            result.multiplyBy10(lastDigit);
            n.multiplyBy10(d);
            if (result.compareTo(n) > 0) {
                /*
                 * In this case, we need to try again because generated number
                 * is greater than n; the recursive call's argument is not
                 * "smaller" than the incoming value of n, but this recursive
                 * call has no more than a 90% chance of being made (and for
                 * large n, far less than that), so the probability of
                 * termination is 1
                 */
                result = randomNumber(n);
            }
        }
        return result;
    }

    /**
     * Finds the greatest common divisor of n and m.
     *
     * @param n
     *            one number
     * @param m
     *            the other number
     * @updates n
     * @clears m
     * @ensures n = [greatest common divisor of #n and #m]
     */
    public static void reduceToGCD(NaturalNumber n, NaturalNumber m) {

        /*
         * Use Euclid's algorithm; in pseudocode: if m = 0 then GCD(n, m) = n
         * else GCD(n, m) = GCD(m, n mod m)
         */

        /*
         * keeps original value of n
         */
        NaturalNumber copyN = n.newInstance();
        copyN.copyFrom(n);
        /*
         * if m is zero, then GCD(n,m) is just n
         */
        if (m.isZero()) {
            n.copyFrom(copyN);
        } else {
            /*
             * n2 copies n to be divided by m, to get the remainder. this is
             * done so n doesn't become affected by the division
             */
            NaturalNumber n2 = n.newInstance();
            n2.copyFrom(n);
            NaturalNumber remainder = n2.divide(m);
            /*
             * if the remainder is zero, then the last value of m is transfered
             * to n, and m is cleared. otherwise, this method will recursively
             * call it self until the remainder of n divided by m ends up as 0.
             * Each loop, n2 mod m(the remainder) is used as one of the formal
             * parameters because of Euclid's algorithm. GCD(n, m) = GCD(m, n
             * mod m) is the same as reduceToGCD(m,remainder)
             */
            if (remainder.isZero()) {
                n.transferFrom(m);

            } else {
                reduceToGCD(m, remainder);
                n.transferFrom(m);

            }
        }

    }

    /**
     * Reports whether n is even.
     *
     * @param n
     *            the number to be checked
     * @return true iff n is even
     * @ensures isEven = (n mod 2 = 0)
     */
    public static boolean isEven(NaturalNumber n) {

        boolean isEven = false;
        /*
         * last digit of the NaturalNumber n is taken and modded by 2. if it
         * evaluates to 0, then n is even. If not, then n is odd.
         */
        int lastDigit = n.divideBy10();
        if (lastDigit % 2 == 0) {
            isEven = true;
        }
        /*
         * n is restored
         */
        n.multiplyBy10(lastDigit);

        return isEven;
    }

    /**
     * Updates n to its p-th power modulo m.
     *
     * @param n
     *            number to be raised to a power
     * @param p
     *            the power
     * @param m
     *            the modulus
     * @updates n
     * @requires m > 1
     * @ensures n = #n ^ (p) mod m
     */
    public static void powerMod(NaturalNumber n, NaturalNumber p,
            NaturalNumber m) {
        assert m.compareTo(new NaturalNumber2(1)) > 0 : "Violation of: m > 1";
        NaturalNumber one = n.newInstance();
        one.setFromInt(1);
        NaturalNumber two = n.newInstance();
        two.setFromInt(2);
        NaturalNumber originalN = n.newInstance();
        originalN.copyFrom(n);
        /*
         * if p is zero, then n is set to 1 because anything powered to 0 is 1.
         * else if p is bigger than 0, p is stored in p2 as a copy and p2 is
         * divided by two. this way, p isn't changed so the code doesn't go
         * against the default restores parameter. Method is recursively called
         * with p2. n2 copies n to be used as n*n. if p is odd, then n is
         * Multiplied by the original value of n.
         */
        if (p.isZero()) {
            n.setFromInt(1);
        } else if (p.compareTo(one) > 0) {
            NaturalNumber p2 = p.newInstance();
            p2.copyFrom(p);
            p2.divide(two);
            powerMod(n, p2, m);
            NaturalNumber n2 = n.newInstance();
            n2.copyFrom(n);
            n.multiply(n2);
            if (!isEven(p)) {
                n.multiply(originalN);
            }
        }
        /*
         * copyN is set up to be n mod m(returned value of n.divide(m)) and n
         * copies that
         */
        NaturalNumber copyN = n.divide(m);
        n.copyFrom(copyN);

    }

    /**
     * Reports whether w is a "witness" that n is composite, in the sense that
     * either it is a square root of 1 (mod n), or it fails to satisfy the
     * criterion for primality from Fermat's theorem.
     *
     * @param w
     *            witness candidate
     * @param n
     *            number being checked
     * @return true iff w is a "witness" that n is composite
     * @requires n > 2 and 1 < w < n - 1
     * @ensures <pre>
     * isWitnessToCompositeness =
     *     (w ^ 2 mod n = 1)  or  (w ^ (n-1) mod n /= 1)
     * </pre>
     */
    public static boolean isWitnessToCompositeness(NaturalNumber w,
            NaturalNumber n) {
        assert n.compareTo(new NaturalNumber2(2)) > 0 : "Violation of: n > 2";
        assert (new NaturalNumber2(1)).compareTo(w) < 0 : "Violation of: 1 < w";
        n.decrement();
        assert w.compareTo(n) < 0 : "Violation of: w < n - 1";
        n.increment();

        boolean witness = false;

        NaturalNumber one = n.newInstance();
        one.setFromInt(1);

        NaturalNumber two = n.newInstance();
        two.setFromInt(2);
        /*
         * nDecremented will be used for the second power mod check, which is:
         * w^(n-1) mod n
         */
        NaturalNumber nDecremented = n.newInstance();
        nDecremented.copyFrom(n);
        nDecremented.decrement();
        NaturalNumber wCopy1 = n.newInstance();
        wCopy1.copyFrom(w);
        NaturalNumber wCopy2 = n.newInstance();
        wCopy2.copyFrom(w);
        /**
         * wCopy1 and wCopy2 are both power modded to check if they're equal to
         * 1. If they are equal, then witness returns true, meaning w is a
         * witness that n is a composite. The static method will return false if
         * the if condition statement isn't true.
         */
        powerMod(wCopy1, two, n);
        powerMod(wCopy2, nDecremented, n);
        if (wCopy1.compareTo(one) == 0 || wCopy2.compareTo(one) != 0) {
            witness = true;
        }

        return witness;
    }

    /**
     * Reports whether n is a prime; may be wrong with "low" probability.
     *
     * @param n
     *            number to be checked
     * @return true means n is very likely prime; false means n is definitely
     *         composite
     * @requires n > 1
     * @ensures <pre>
     * isPrime1 = [n is a prime number, with small probability of error
     *         if it is reported to be prime, and no chance of error if it is
     *         reported to be composite]
     * </pre>
     */
    public static boolean isPrime1(NaturalNumber n) {
        assert n.compareTo(new NaturalNumber2(1)) > 0 : "Violation of: n > 1";
        boolean isPrime;
        if (n.compareTo(new NaturalNumber2(THREE)) <= 0) {

            isPrime = true;
        } else if (isEven(n)) {

            isPrime = false;
        } else {

            isPrime = !isWitnessToCompositeness(new NaturalNumber2(2), n);
        }
        return isPrime;
    }

    /**
     * Reports whether n is a prime; may be wrong with "low" probability.
     *
     * @param n
     *            number to be checked
     * @return true means n is very likely prime; false means n is definitely
     *         composite
     * @requires n > 1
     * @ensures <pre>
     * isPrime2 = [n is a prime number, with small probability of error
     *         if it is reported to be prime, and no chance of error if it is
     *         reported to be composite]
     * </pre>
     */
    public static boolean isPrime2(NaturalNumber n) {
        assert n.compareTo(new NaturalNumber2(1)) > 0 : "Violation of: n > 1";

        boolean isPrime = false;
        if (n.compareTo(new NaturalNumber2(THREE)) <= 0) {

            isPrime = true;
        } else if (isEven(n)) {

            isPrime = false;
        } else {

            NaturalNumber copyN = n.newInstance();
            copyN.copyFrom(n);
            copyN.decrement();
            NaturalNumber one = n.newInstance();
            one.setFromInt(1);
            /*
             * prevents checkstyle warning
             */
            final int fifty = 50;
            /*
             * the amount of witness candidates generated is 50
             */
            int witnessCanidateAmount = fifty;
            /*
             * while loop will continue indefinitely until isPrime is true or
             * guessAmount reaches 0. guessAmount will decrement by 1 every
             * loop.
             */
            while (witnessCanidateAmount >= 0 && !isPrime) {
                NaturalNumber randomWitness = randomNumber(copyN);
                /*
                 * if randomWitness is in the interval 1<randomWitness<n-1, then
                 * loop will check will make a check for isPrime by sending it
                 * to the isWitnessToComposite method.
                 */
                if (randomWitness.compareTo(copyN) < 0
                        && randomWitness.compareTo(one) > 0) {
                    isPrime = !isWitnessToCompositeness(randomWitness, n);
                }
                witnessCanidateAmount--;
            }
        }

        return isPrime;
    }

    /**
     * Generates a likely prime number at least as large as some given number.
     *
     * @param n
     *            minimum value of likely prime
     * @updates n
     * @requires n > 1
     * @ensures n >= #n and [n is very likely a prime number]
     */
    public static void generateNextLikelyPrime(NaturalNumber n) {
        assert n.compareTo(new NaturalNumber2(1)) > 0 : "Violation of: n > 1";

        /*
         * Use isPrime2 to check numbers, starting at n and increasing through
         * the odd numbers only, until n is likely prime
         */
        boolean isPrime = false;
        /*
         * increments n to next value assuming its not a prime
         */
        n.increment();
        /*
         * as long as isPrime is NOT true, the loop will continue to find the
         * next prime number. if n is even, then it will only increment once to
         * produce an odd number. Other wise, it will increment twice to move on
         * to the next odd number
         */
        while (!isPrime) {
            if (isEven(n)) {
                n.increment();
            } else {
                n.increment();
                n.increment();
            }
            isPrime = isPrime2(n);
        }
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

        /*
         * Sanity check of randomNumber method -- just so everyone can see how
         * it might be "tested"
         */
        final int testValue = 17;
        final int testSamples = 100000;
        NaturalNumber test = new NaturalNumber2(testValue);
        int[] count = new int[testValue + 1];
        for (int i = 0; i < count.length; i++) {
            count[i] = 0;
        }
        for (int i = 0; i < testSamples; i++) {
            NaturalNumber rn = randomNumber(test);
            assert rn.compareTo(test) <= 0 : "Help!";
            count[rn.toInt()]++;
        }
        for (int i = 0; i < count.length; i++) {
            out.println("count[" + i + "] = " + count[i]);
        }
        out.println("  expected value = "
                + (double) testSamples / (double) (testValue + 1));

        /*
         * Check user-supplied numbers for primality, and if a number is not
         * prime, find the next likely prime after it
         */
        while (true) {
            out.print("n = ");
            NaturalNumber n = new NaturalNumber2(in.nextLine());
            if (n.compareTo(new NaturalNumber2(2)) < 0) {
                out.println("Bye!");
                break;
            } else {
                if (isPrime1(n)) {
                    out.println(n + " is probably a prime number"
                            + " according to isPrime1.");
                } else {
                    out.println(n + " is a composite number"
                            + " according to isPrime1.");
                }
                if (isPrime2(n)) {
                    out.println(n + " is probably a prime number"
                            + " according to isPrime2.");
                } else {
                    out.println(n + " is a composite number"
                            + " according to isPrime2.");
                    generateNextLikelyPrime(n);
                    out.println("  next likely prime is " + n);
                }
            }
        }

        /*
         * Close input and output streams
         */
        in.close();
        out.close();
    }

}
