To run the server and client components of this project using Remote Method Invocation (RMI), follow the steps outlined below. Ensure you have two separate terminals open, one for the server and one for the client, both located in the /src directory.
Default port for server and client is 1099.
Server Terminal:
1. Compile the RMI registry code:
   - `javac RMIRegistry.java`
2. Run the RMI registry:
   - `java RMIRegistry <Port Number>`
     Example: `java RMIRegistry 3200`

Client Terminal:
1. Compile the client code:
   - `javac Client.java`
2. Run the client:
   - `java Client <Server IP> <Port Number>`
     Example: `java Client 127.0.0.1 3200`
3. Upon starting the client, the first five PUT, GET, and DELETE requests are automated to be sent to the server directly. Afterward, users can input their requests manually.


Additional Details:

1. Both the server and client utilize Remote Method Invocation (RMI) for communication.
2. Requests from the client to the server should be provided in the format: PUT <key> <value>, GET <key>, or DELETE <key>, with each word separated by a space. Ensure to start with PUT to avoid key not found errors.
   Valid examples:
    - `PUT 3 4`
    - `PUT mango fruits`
    - `GET 3`
    - `GET mango`
    - `DELETE 3`
      Invalid examples:
    - `PUT`
    - `PUT 5`
    - `PUT 6 7 8`
    - `GET`
    - `DELETE 5 6`
    - `DELETE`
3. Keys and values are of string data type in the hashmap.
4. The main code for the Key-Value Store logic resides in KeyValueStore.java and Helper.java contains common helper functions such as logging and validating checksum.

Executive Summary:

The purpose of this assignment is to enhance a Key-Value Store server to enable communication with clients using Remote Procedure Calls (RPC) instead of sockets, and to make the server multi-threaded to handle concurrent operations.
This entails implementing a server capable of processing multiple client requests simultaneously, including PUT, GET, and DELETE operations, while ensuring thread safety and mutual exclusion.
The assignment emphasizes leveraging RPC frameworks such as Java RMI for communication and employing multi-threading techniques to manage concurrent requests effectively.


Technical Impression:

During the implementation of the assignment, I utilized Java RMI to enable RPC communication between the client and server.
The server was designed to be multi-threaded, employing synchronized methods and a ConcurrentHashMap to ensure thread safety and handle concurrent operations seamlessly.
By encapsulating the functionality of the Key-Value Store within a Remote Object,I have facilitated remote method invocation from the client, allowing seamless interaction across the network.
Threading mechanisms, including synchronized blocks and the ConcurrentHashmap data structure, were employed to manage access to shared resources and prevent race conditions effectively.

Overall, the assignment provided valuable insights into building distributed systems with RPC communication and handling concurrency in server applications.
Leveraging Java RMI facilitated seamless integration between client and server components, while multi-threading techniques ensured efficient processing of concurrent requests. 

Example:

In the realm of distributed systems, Remote Method Invocation (RMI) finds extensive use in various real-world applications.
One example is in the context of enterprise-level software solutions, where RMI facilitates communication between different components of a distributed system. For instance, in a banking system, RMI could be employed to enable seamless interaction between a client application and a remote server responsible for handling transactions, account management, and authentication.
Through RMI, the client can invoke methods on remote objects hosted on the server, allowing for secure and efficient communication over a network. This capability not only streamlines the development process by abstracting away networking complexities but also ensures robustness and scalability in handling client requests, thereby enhancing the overall reliability and performance of the banking system.