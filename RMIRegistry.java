import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 *Implementation of rmi registry server code,Hosts and exports the remote object implementation to the RMI registry.
 */
public class RMIRegistry {

    /**
     * Helper instance for logging and other utilities.
     */
    private final Helper helper;

    public RMIRegistry(Helper helper){
        this.helper = helper;
    }

    public static void main(String[] args) {
        int port=1099;

        if (args.length == 1) {
            port = Integer.parseInt(args[0]);
        }

        RMIRegistry rmiRegistry = new RMIRegistry(new Helper());
        rmiRegistry.bindRemoteObject(port);

    }

    /**
     * Binds the remote object implementation to the RMI registry.
     * @param port The port number on which the RMI registry will be created and the remote object will be bound.
     */
    private void bindRemoteObject(int port){
        try{
            KeyValueStore keyValueStore = new KeyValueStoreImpl(helper);
            Registry registry = LocateRegistry.createRegistry(port);
            registry.bind("keyValueStore", keyValueStore);
            helper.logMessage("Remote Object binding successful.");

        } catch(Exception ex){
         helper.logError("Binding remote object failed with exception :"+ex.getMessage());
        }
    }

}
