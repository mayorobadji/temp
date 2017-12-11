import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;

// Compilation (CryptoLibTest contains the main-method):
//   javac CryptoLibTest.java
// Running:
//   java CryptoLibTest

public class CryptoLib {

	/**
	 * Returns an array "result" with the values "result[0] = gcd", "result[1] =
	 * s" and "result[2] = t" such that "gcd" is the greatest common divisor of
	 * "a" and "b", and "gcd = a * s + b * t".
	 **/
	public static int[] EEA(int a, int b) {
		// Note: as you can see in the test suite,
		// your function should work for any (positive) value of a and b.
		/*
		 * R x y Q a 1 0 b 0 1 a/b a - b (a/b) 1 - 0 (a/b) 0 - 1 (a/b) b / (b -
		 * a/b) ... ... ... ...
		 */
		if (a < 0 || b < 0) {
			System.out.println("At least one wrong number !! exiting...");
			System.exit(1);
		}
		// initialize the x coefficients
		int x = 1, x_next = 0;
		// initialize the y coefficients
		int y = 0, y_next = 1;
		// initialize the remainders
		int r = a, r_next = b;
		// initialize the quotient
		int q = 0;
		// initialize temporary variables
		int x_temp, y_temp, r_temp;

		if (a != b) {
			// loop until we find r_next = 0
			while (r_next != 0) {
				// quotient
				q = r / r_next;
				// store the values in temp
				x_temp = x_next;
				y_temp = y_next;
				r_temp = r;
				// update the next coefficients
				x_next = x - (q * x_next);
				y_next = y - (q * y_next);
				// update the current coefficients
				x = x_temp;
				y = y_temp;
				r = r_next;
				// get the new remainder
				r_next = r_temp - (q * r_next);
			}
		}
		int[] result = new int[3];
		result[0] = r;
		result[1] = x;
		result[2] = y;
		return result;
	}

	/**
	 * Returns Euler's Totient for value "n".
	 **/
	public static int EulerPhi(int n) {

		int i, count = 0;
		int[] result = new int[3];

		if (n < 0)
			return 0;
		for (i = n; i >= 1; i--) {
			result = EEA(n, i);
			if (result[0] == 1)
				count++;
		}

		return count;
	}

	/**
	 * Returns the value "v" such that "n*v = 1 (mod m)". Returns 0 if the
	 * modular inverse does not exist.
	 **/
	public static int ModInv(int n, int m) {
		/*
		 * R x Q a 1 b 0 a/b a - b (a/b) 1 - 0 (a/b) b / (b - a/b) ... ... ...
		 * ...
		 */
		// initialize the coefficients
		int x = 1, x_next = 0;

		// initialize the remainders
		int r = n, r_next = m;
		// initialize the quotient
		int q = 0;

		// initialize temporary variables
		int x_temp, r_temp;

		// loop until we find r_next = 0
		while (r_next != 0) {
			// quotient
			q = r / r_next;

			// store the values in temp
			x_temp = x_next;
			r_temp = r;

			// update the next coefficients
			x_next = x - (q * x_next);

			// update the current coefficients
			x = x_temp;
			r = r_next;

			// get the new remainder
			r_next = r_temp - (q * r_next);

		}
		// we get out the loop when r_next reaches 0
		// therefore r should be 1 if n has a modular inverse

		if (r == 1) {
			if (x < 0)
				return m + x;

			else if (x > m)
				return x - m;
			else
				return x;
		} else if (r == -1)
			return m - x;

		else
			return 0;

	}

	/**
	 * Returns 0 if "n" is a Fermat Prime, otherwise it returns the lowest
	 * Fermat Witness. Tests values from 2 (inclusive) to "n/3" (exclusive).
	 **/
	public static int FermatPT(int n) {
		int witness = 0;
		int[] result = new int[3];
		double temp;
		BigInteger numb, exponent, mod, res;

		if (n < 2) {
			System.out.println("Wrong number !! exiting...");
			System.exit(1);
		}
		// since we start from 2 to n/3
		// we need to manually return
		// the values for 2 to 8
		if (n < 9) {
			if (n == 4 || n == 6 || n == 8)
				return 2;
			else
				return 0;
		}
		for (int i = 2; i < n / 3; i++) {
			// check if GCD (i,n) == 1
			result = EEA(n, i);
			// GCD != 1 --> not prime
			if (result[0] != 1) {
				witness = i;
				break;
			} else {
				// check if i^n-1 = 1 (mod n)
				numb = new BigInteger(Integer.toString(i));
				exponent = new BigInteger(Integer.toString(n - 1));
				mod = new BigInteger(Integer.toString(n));

				res = numb.modPow(exponent, mod);
				
				// it can be a liar
				// so continue to the next
				if (res.intValue() == 1)
					continue;
				// if it is different
				// definitely not prime
				else {
					witness = i;
					break;
				}
			}

		}

		return witness;
	}

	/**
	 * Returns the probability that calling a perfect hash function with
	 * "n_samples" (uniformly distributed) will give one collision (i.e. that
	 * two samples result in the same hash) -- where "size" is the number of
	 * different output values the hash function can produce.
	 **/
	public static double HashCP(double n_samples, double size) {
		// Using  1- size! /((size - n_samples)!*size a la puissance n)
		BigDecimal size_fact = factorial(size);
		BigDecimal size_sample_fact = factorial(size-n_samples);
		BigDecimal size_pow_n = new BigDecimal(Double.toString(size)).pow((int)n_samples);
		BigDecimal denominator = size_sample_fact.multiply(size_pow_n);
		BigDecimal quotient = size_fact.divide(denominator,MathContext.DECIMAL128);
		double probability = 1 - quotient.doubleValue();
		
		
		return probability;
	}
	
	/**
	 * Factorial Function
	 * */
	public static BigDecimal factorial(double n)
	{
		BigDecimal resultat = new BigDecimal(Integer.toString(1));
		
		if (n==0)
			return resultat;
		
		for (double i = n; i>=1; i--)
		{
			resultat = resultat.multiply(new BigDecimal(Double.toString(i)));
		}
		return resultat;
	}

}
