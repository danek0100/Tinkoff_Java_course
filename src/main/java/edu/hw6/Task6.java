package edu.hw6;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This class provides methods to scan network ports and determine if they are free or occupied.
 */
public class Task6 {

    private static final String TCP = "TCP";
    private static final String UDP = "UDP";
    private static final String FREE = "Free";
    private static final String OCCUPIED = "Occupied";
    private static final int MAX_PORT_FOR_SCAN = 49151;

    /**
     * Scans all TCP and UDP ports from 0 to 49151 to determine if they are free or occupied.
     * Additionally, it checks against a provided map of known ports to identify common services.
     *
     * @param knownPorts A map of known port numbers and their associated service names.
     * @return A list of strings, each representing the status of a port (free or occupied) along with
     * the protocol type (TCP/UDP) and service name (if known).
     */
    public static List<String> scanPorts(Map<Integer, String> knownPorts) {
        List<String> results = new ArrayList<>();

        for (int port = 0; port <= MAX_PORT_FOR_SCAN; port++) {
            StringBuilder service = new StringBuilder(knownPorts.getOrDefault(port, ""));
            if (!service.isEmpty()) {
                service.append(" ");
            }

            try (ServerSocket tcpSocket = new ServerSocket(port)) {
                results.add(TCP + " " + port + " " + service + "- " + FREE);
            } catch (IOException e) {
                results.add(TCP + " " + port + " " + service + "- Occupied");
            }

            try (DatagramSocket udpSocket = new DatagramSocket(port)) {
                results.add(UDP + " " + port + " " + service + "- " + FREE);
            } catch (IOException e) {
                results.add(UDP + " " + port + " " + service + "- " + OCCUPIED);
            }
        }
        return results;
    }

    private Task6() {}
}
