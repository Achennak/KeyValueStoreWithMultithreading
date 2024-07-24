import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Helper class to store common methods which can we used across server and client.
 */
public class Helper {

    /**
     * Log messages from specific address and port with timestamp
     * @param message  request message from the client
     */
    public void logMessage(String message) {
        String timestamp = getFormattedTimestamp();
        System.out.println(timestamp + " [INFO] " + message);
    }

    /**
     * Log error messages with timestamp.
     * @param errorMessage error occurred
     */
    public void logError(String errorMessage) {
        String timestamp = getFormattedTimestamp();
        System.err.println(timestamp + " [ERROR] " + errorMessage);
    }

    /**
     * Helper function to calculate checksum
     * @param data data transferred between server and client
     * @return returns the checksum of the data given.
     */
    public int calculateChecksum(byte[] data) {
        int checksum = 0;
        for (byte b : data) {
            checksum += b & 0xFF;
        }
        return checksum;
    }

    /**
     * Helper function to validate the checksum of the request from client
     * @param data request from the client
     * @param receivedChecksum checksum set along with request
     * @return returns true if data is valid else false
     */
    public boolean validateChecksum(byte[] data, int receivedChecksum) {
        int calculatedChecksum = calculateChecksum(data);
        return receivedChecksum == calculatedChecksum;
    }

    /**
     * Helper function get current system time in milliseconds.
     * @return time in millis
     */
    private String getFormattedTimestamp() {
        long currentTimeMillis = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS");
        return sdf.format(new Date(currentTimeMillis));
    }
}
