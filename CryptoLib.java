// Compilation (CryptoLibTest contains the main-method):
//   javac CryptoLibTest.java
// Running:
//   java CryptoLibTest

public class CryptoLib {

	/**
	 * Returns an array "result" with the values "result[0] = gcd",
	 * "result[1] = s" and "result[2] = t" such that "gcd" is the greatest
	 * common divisor of "a" and "b", and "gcd = a * s + b * t".
	 **/
	public static int[] EEA(int a, int b) {
		// Note: as you can see in the test suite,
		// your function should work for any (positive) value of a and b.
		/* R			  x			    y				Q
		   a			  1			    0		
		   b			  0			    1				a/b
		   a - b (a/b)    1 - 0 (a/b)	0 - 1 (a/b)		b / (b - a/b)
		   ...			  ...			...				...
		*/
		if (a<0 || b<0){
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
		return -1;
	}

	/**
	 * Returns the value "v" such that "n*v = 1 (mod m)". Returns 0 if the
	 * modular inverse does not exist.
	 **/
	public static int ModInv(int n, int m) {
		return -1;
	}

	/**
	 * Returns 0 if "n" is a Fermat Prime, otherwise it returns the lowest
	 * Fermat Witness. Tests values from 2 (inclusive) to "n/3" (exclusive).
	 **/
	public static int FermatPT(int n) {
		return -1;
	}

	/**
	 * Returns the probability that calling a perfect hash function with
	 * "n_samples" (uniformly distributed) will give one collision (i.e. that
	 * two samples result in the same hash) -- where "size" is the number of
	 * different output values the hash function can produce.
	 **/
	public static double HashCP(double n_samples, double size) {
		return -1;
	}

}
