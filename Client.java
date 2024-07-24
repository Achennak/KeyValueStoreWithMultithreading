import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

/**
 * Implementation of Client code,locates the remote object in the registry and invokes its methods remotely.
 */
public class Client {
    private final Helper helper;

    public Client(Helper helper){
        this.helper = helper;
    }

    public static void main(String[] args) {
        String hostname = "127.0.0.1";
        int port = 1099;
        if (args.length == 2) {
            hostname = args[0];
            port = Integer.parseInt(args[1]);
        }

        Client client = new Client(new Helper());
        client.getRegistry(hostname,port);

    }

    /**
     * Connects to the RMI registry and obtains the remote object.
     * @param hostname The hostname or IP address of the RMI registry.
     * @param port The port number of the RMI registry.
     */
    private void getRegistry(String hostname,int port){
        try {
            Registry registry = LocateRegistry.getRegistry(hostname, port);
            KeyValueStore keyValueStoreObj = (KeyValueStore) registry.lookup("keyValueStore");
            // Automated PUTs,GETs and Deletes
            for (int i = 0; i < 5; i++) {
                String automatedPut = "PUT key" + i + " v" + (i+1);
                sendAndReceiveMessage(automatedPut,keyValueStoreObj);
                String automatedGet = "GET key" + i;
                sendAndReceiveMessage(automatedGet,keyValueStoreObj);
                String automatedDelete = "DELETE key" + i;
                sendAndReceiveMessage(automatedDelete,keyValueStoreObj);
            }
            Scanner scanner = new Scanner(System.in);

            while (true) {
                helper.logMessage("Enter operation: PUT <key> <value> or GET <key> or DELETE <key>");
                String userInput = scanner.nextLine();
                sendAndReceiveMessage(userInput,keyValueStoreObj);
            }
        } catch(Exception ex){
            helper.logError("Communication with Server failed with :"+ex.getMessage());
        }
    }

    /**
     * Sends a message to the server and receives the response.
     * @param message The message to be sent to the server.
     * @param keyValueStoreObj The remote object representing the key-value store.
     * @throws RemoteException if a communication-related exception occurs.
     */
    private void sendAndReceiveMessage(String message,KeyValueStore keyValueStoreObj) throws RemoteException {
        // Calculate checksum for the message
        int checksum = helper.calculateChecksum(message.getBytes());
        // Concatenate checksum and message and send to the server
        String messageWithChecksum = checksum + " " + message;
        helper.logMessage("sending message :"+message);
        String response = keyValueStoreObj.processRequest(messageWithChecksum);
        if(response !=null){
            // Extract checksum and data from the response
            String[] tokens = response.split(" ", 2);
            int receivedChecksum = Integer.parseInt(tokens[0]);
            String responseData = tokens[1];

            // Validate the checksum
            if (helper.validateChecksum(responseData.getBytes(), receivedChecksum)) {
                helper.logMessage("Server response :" + responseData);
            } else{
                helper.logError("Checksum validation failed for the response from the server.");
            }
        }

    }
}
