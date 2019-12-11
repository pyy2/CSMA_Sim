
import java.io.IOException;
import java.lang.Thread;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Station extends Thread {
    private static int status = 0;
    private String name;
    private int num;
    private int tS;
    private int probability;
    private Random generator = new Random();
    private int W;
    private int tP;
    long avgTotal;
    long avgStart;
    long avgStop;
    long busyStart;
    long busyStop;
    long busyTotal;

    private int randomGenerator(int max, int min) {
        return generator.nextInt(max) + min;
    }

    public Station(String name, int total, int tS, int tP, int probability) {
        this.name = name;
        num = total;
        this.tS = tS;
        this.probability = probability;
        this.tP = tP;
    }

    private void delay(int value) {
        try {
            Thread.sleep((int) tS * value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void isDataReady() {
        // check if data is ready
        while (probability < randomGenerator(100, 0)) {
            busyStart = System.currentTimeMillis();
            // System.out.println(name + " Data is NOT ready");
            delay(240); // if not sleep
            busyStop = System.currentTimeMillis();
            busyTotal += (busyStop - busyStart);
        }
    }

    // check medium status
    private synchronized void checkStatus() {

        // System.out.println("Medium is busy");
        busyStart = System.currentTimeMillis();
        busyStop = System.currentTimeMillis();
        busyTotal += (busyStop - busyStart);
    }

    public void run() {

        long startTime = System.currentTimeMillis();

        // sleep all threads
        delay(240);
        int count = 0;

        // start transactions for number of packets
        do {
            avgStart = System.currentTimeMillis();

            // System.out.println(name + " TRANSMISSION #: " + Integer.toString(count));

            isDataReady(); // check if data is ready
            // System.out.println(name + " Data is READY");

            checkStatus(); // lock medium
            delay(randomGenerator(20, 10)); // wait for tDIFS
            delay(tP * num); // wait for tP * # packets
            delay(10 * tS);// wait for tIFS
            delay(randomGenerator(40, 20)); // wait for tDIFS

            avgStop = System.currentTimeMillis();
            avgTotal += (avgStop - avgStart);
            count++;
        } while ((tS * 5000) > (System.currentTimeMillis() - startTime));
        long endTime = System.currentTimeMillis();
        long duration = (endTime - startTime);
        // System.out.println(name + ";TOTAL;" + Long.toString(duration));
        // System.out.println(name + ";DURATION;" + Long.toString(duration));
        // System.out.println(Long.toString(avgTotal / count));
        // System.out.println(name + ";BUSYTOTAL;" + Long.toString(busyTotal));
        System.out.println((float) duration / (float) busyTotal);
    }
}