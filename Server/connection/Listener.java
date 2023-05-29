package connection;

import java.net.ServerSocket;
import java.net.Socket;

import instance.UserConnectionHandler;

public class Listener {
    ServerSocket serverSocket;

    public Listener(int port) {
        try {
            serverSocket = new ServerSocket(port);
            while (true) {
                Socket newSock = serverSocket.accept();
                UserConnectionHandler newCon = new UserConnectionHandler(newSock);
                Thread userHandleThread = new Thread(newCon);
                userHandleThread.start();
            }
        } catch (Exception e) {
            System.out.println("Error in while initialize server socket");
        }
    }
}
