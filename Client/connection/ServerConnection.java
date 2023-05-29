package connection;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.*;

public class ServerConnection {
    static public Socket socket;
    static public DataOutputStream outStream;
    static public DataInputStream inputStream;

    static public void initConnect(String address, int port) {
        try {
            socket = new Socket(address, port);
            outStream = new DataOutputStream(socket.getOutputStream());
            inputStream = new DataInputStream(socket.getInputStream());
            System.out.println("Connected!");
        } catch (Exception e) {
            System.out.println("Error in while initialize connection");
        }
    }
}
