import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Implementation of KeyValueStore interface, providing the functionality for remote method invocation.
 */
public class KeyValueStoreImpl extends UnicastRemoteObject implements KeyValueStore {
    private final ConcurrentHashMap<String, String> store;
    private final Helper helper;


    public KeyValueStoreImpl(Helper helper) throws RemoteException {
        this.helper = helper;
        this.store = new ConcurrentHashMap<>();
    }

    /**
     * Handles put operation.
     * @param key key value for the store
     * @param value value intended to store
     */
    private synchronized void put(String key, String value) {
            store.put(key, value);
    }

    /**
     * Handles get operation.
     * @param key key value to retrieve
     * @return returns key
     */
    private String get(String key) {
        return store.getOrDefault(key, null);
    }

    /**
     * Handles delete operation
     * @param key key to be deleted
     * @return returns response to client
     */
    private synchronized boolean delete(String key) {
        if (!store.containsKey(key)) {
                return false;
            } else {
                store.remove(key);
                return true;
        }
    }

    @Override
    public String processRequest(String request) {
        String[] tokens = request.split(" ");

        if (tokens.length < 2) {
            helper.logError("Malformed request received from Client." );
            return null;
        }

        // Extract the checksum and data from the request
        int receivedChecksum = Integer.parseInt(tokens[0]);
        String requestData = request.substring(tokens[0].length() + 1);
        helper.logMessage("Received request from client " + requestData);
        if (helper.validateChecksum(requestData.getBytes(), receivedChecksum)) {
           String response = handleRequests(requestData);
            int responseChecksum = helper.calculateChecksum(response.getBytes());
            // Send the response back to the client with the checksum
            return responseChecksum + " " + response;
        } else{
            return null;
        }

    }

    /**
     * Handles requests from Clients
     * @param request request from client
     * @return returns response to client.
     */
    private String handleRequests(String request){
        String[] parts = request.split(" ");
        String operation = parts[0];
        switch (operation.toUpperCase()) {
            case "PUT":
                if (parts.length == 3) {
                    String key = parts[1];
                    String value = parts[2];
                    put(key, value);
                    return "PUT successful";
                } else {
                    return "Invalid PUT request";
                }

            case "GET":
                if (parts.length == 2) {
                    String key = parts[1];
                    String value = get(key);
                    return value != null ? "Get operation successfully returned with value "+ value : "Key not found";
                } else {
                    return "Invalid GET request";
                }

            case "DELETE":
                if (parts.length == 2) {
                    String key = parts[1];
                    boolean result = delete(key);
                    return result ? "DELETED successfully" :"Key not found";
                }   else {
                    return "Invalid DELETE request";
                }

            default:
                return "Invalid request";
        }
    }
}
