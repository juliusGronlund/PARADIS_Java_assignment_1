import java.math.BigInteger;
import java.util.Scanner;

public class Factorizer implements Runnable {
    private Scanner scanner = new Scanner(System.in);

    private BigInteger product;
    private int nrOfThreads;

    private long min = 1;
    private long max = 100000;

    public static void main(String[] args) throws InterruptedException {
        Factorizer factorizer = new Factorizer();
        Thread[] threads;

        long startTime, endTime, totalTime;


        factorizer.getProduct();
        factorizer.getNrOfThreads();

        //Start the timer
        startTime = System.nanoTime();

        //Createing the Threads
        threads = new Thread[factorizer.nrOfThreads];
        for(int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(factorizer);
            threads[i].start();
        }

        //Joining the Threads
        for(int i = 0; i < threads.length; i++) {
            threads[i].join();
        }

        //End and print the timer
        endTime = System.nanoTime();
        totalTime = endTime - startTime;
        System.out.println(totalTime);
    }

    private void getProduct() {
        System.out.print("Product of two prime numbers: ");
        product = new BigInteger(scanner.nextLine());
    }

    private void getNrOfThreads() {
        System.out.print("Nr of threads: ");
        nrOfThreads = scanner.nextInt();
    }

    @Override
    public void run() {
        BigInteger factor1, factor2;

        BigInteger number = BigInteger.valueOf(min);
        while (number.compareTo(BigInteger.valueOf(max)) <= 0) {
            if (product.remainder(number).compareTo(BigInteger.ZERO) == 0) {
                factor1 = number;
                factor2 = product.divide(factor1);
                System.out.println(factor1 + " : " + factor2);
                return;
            }
            number = number.add(BigInteger.valueOf(1));
        }
    }
}
