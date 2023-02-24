import java.math.BigInteger;
import java.util.Scanner;

public class Factorizer implements Runnable {
    private Scanner scanner = new Scanner(System.in);

    private BigInteger product;
    private BigInteger factor1, factor2;
    private BigInteger number;
    private int nrOfThreads;

    private long min = 1;
    private long max = 100000;

    public static void main(String[] args) throws InterruptedException {
        Factorizer factorizer = new Factorizer();
        Thread[] threads;

        long startTime, endTime, totalTime;

        factorizer.setProduct();
        factorizer.setNrOfThreads();

        //If the product is a prime number
        if(factorizer.isPrime(factorizer.product)) {
            System.out.println("No factorization possible");
        }
        else {
            //Start the timer
            startTime = System.nanoTime();

            //Creating the Threads
            threads = new Thread[factorizer.nrOfThreads];
            for(int i = 0; i < threads.length; i++) {
                threads[i] = new Thread(factorizer);
                threads[i].start();
            }

            //Joining the Threads
            for(int i = 0; i < threads.length; i++) {
                threads[i].join();
            }

            //End timer and print stats
            endTime = System.nanoTime();
            totalTime = endTime - startTime;
            System.out.println("\nResult");
            System.out.println("Computation time: " + ((double) totalTime / 1_000_000_000) + " seconds");
            System.out.println("The number of threads: " + threads.length);
            System.out.println("Product: " + factorizer.getProduct());
            System.out.println("Factors: " + factorizer.getFactor1() + ", " + factorizer.getFactor2());
        }


    }

    private void setProduct() {
        System.out.print("Product of two prime numbers: ");
        product = new BigInteger(scanner.nextLine());
    }

    private void setNrOfThreads() {
        System.out.print("Nr of threads: ");
        nrOfThreads = scanner.nextInt();
    }

    public BigInteger getFactor1() {
        return factor1;
    }

    public BigInteger getFactor2() {
        return factor2;
    }

    public BigInteger getProduct() {
        return product;
    }

    public BigInteger getNumber() {
        return number;
    }



    @Override
    public void run() {
        number = BigInteger.valueOf(min);
        while (number.compareTo(BigInteger.valueOf(max)) <= 0) {
            if (product.remainder(number).compareTo(BigInteger.ZERO) == 0) {
                factor1 = number;
                factor2 = product.divide(factor1);
                if(isPrime(factor1) && isPrime(factor2)) {
                    return;
                }
            }
            number = number.add(BigInteger.valueOf(1));
        }

        //factor1 = BigInteger.valueOf(69);
        //factor2 = BigInteger.valueOf(69);
    }

    private boolean isPrime(BigInteger number) {
        if (number.compareTo(BigInteger.ONE) <= 0) {
            // Return false if number is less than or equal to 1
            return false;
        } else if (number.compareTo(BigInteger.valueOf(2)) == 0) {
            // Return true if number is equal to 2
            return true;
        } else if (number.mod(BigInteger.valueOf(2)).equals(BigInteger.ZERO)) {
            // Return false if number is even
            return false;
        }

        // Check if number is divisible by any odd number between 3 and the square root of number
        BigInteger limit = number.sqrt();
        for (BigInteger i = BigInteger.valueOf(3); i.compareTo(limit) <= 0; i = i.add(BigInteger.valueOf(2))) {
            if (number.mod(i).equals(BigInteger.ZERO)) {
                return false;
            }
        }

        //number is prime
        return true;
    }
}
