
import java.util.Scanner;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        int packetNum;
        int N; // # of wireless stations
        int probability;
        int unitTime; // set unit time 1000ms = 1s

        Scanner scan = new Scanner(System.in);
        // Number of Stations
        System.out.println("Number of Stations: ");
        N = scan.nextInt();

        System.out.println("Set Unit Time: (1000ms = 1s): ");
        unitTime = scan.nextInt();

        int tP = 60 * unitTime; // single packet transmission time

        // Probability %
        System.out.println("Probability: (30%-100%) ");
        probability = scan.nextInt();

        // Number of Packets
        System.out.println("Number of Packets: ");
        packetNum = scan.nextInt();

        // Array container of wireless stations
        Station[] stations = new Station[N];

        // Initialize stations
        for (int j = 0; j < N; j++) {
            stations[j] = new Station(Integer.toString(j), packetNum, unitTime, tP, probability);
            System.out.println("Initializing Station: " + Integer.toString(j));
        }
        System.out.println("\n");

        // Start sending data
        for (int i = 0; i < N; i++) {
            stations[i].start();
        }
    }
}