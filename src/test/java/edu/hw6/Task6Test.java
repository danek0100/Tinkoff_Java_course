package edu.hw6;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static edu.hw6.Task6.scanPorts;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Task6Test {

    /**
     * Creates and returns a default map of known ports and their associated services.
     *
     * @return A map where each key is a port number and each value is the name of the service
     * commonly associated with that port.
     */
    public static Map<Integer, String> getDefaultKnownPortsMap() {
        Map<Integer, String> knownPorts = new HashMap<>();
        knownPorts.put(46, "MPM [default send]");
        knownPorts.put(48, "Digital Audit Daemon");
        knownPorts.put(49, "Login Host Protocol (TACACS)");
        knownPorts.put(50, "Remote Mail Checking Protocol");
        knownPorts.put(52, "XNS Time Protocol");
        knownPorts.put(53, "Domain Name Server");
        knownPorts.put(55, "ISI Graphics Language");
        knownPorts.put(56, "XNS Authentication");
        knownPorts.put(58, "XNS Mail");
        knownPorts.put(79, "Finger");
        knownPorts.put(80, "World Wide Web HTTP");
        knownPorts.put(135, "EPMAP");
        knownPorts.put(137, "NetBIOS Name Service");
        knownPorts.put(138, "NetBIOS Datagram Service");
        return knownPorts;
    }


    @Test
    @DisplayName("Test for Both Occupied and Free Ports")
    public void testForBothOccupiedAndFreePorts() {
        Map<Integer, String> knownPorts = getDefaultKnownPortsMap();
        List<String> scanResults = scanPorts(knownPorts);

        boolean foundOccupied = false;
        boolean foundFree = false;
        boolean foundKnownServiceName = false;

        for (String result : scanResults) {
            if (result.endsWith("Occupied")) {
                foundOccupied = true;
            }
            if (result.endsWith("Free")) {
                foundFree = true;
            }
            if (!foundKnownServiceName) {
                for (String service : knownPorts.values()) {
                    if (result.contains(service)) {
                        foundKnownServiceName = true;
                        break;
                    }
                }
            }

            if (foundOccupied && foundFree && foundKnownServiceName) {
                break;
            }
        }

        assertTrue(foundOccupied, "Should find at least one occupied port");
        assertTrue(foundFree, "Should find at least one free port");
        assertTrue(foundKnownServiceName, "Should find at least one known service");
    }
}
