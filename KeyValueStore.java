import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Defines remote method of KeyValueStore that can be invoked by clients.
 */
public interface KeyValueStore extends Remote {

        /**
         *  Function which process the client request and send response back
         * @param request request from client
         * @return returns response to client
         */
        String processRequest(String request) throws RemoteException;

}
