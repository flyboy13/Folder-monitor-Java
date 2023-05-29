package connection;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

import javax.swing.SwingUtilities;

import view.MainView;

public class UserConnectionHandler implements Runnable {
    DataInputStream inputStream;
    DataOutputStream outputStream;
    Socket con;

    public UserConnectionHandler(Socket con) {
        this.con = con;
        try {
            inputStream = new DataInputStream(con.getInputStream());
            outputStream = new DataOutputStream(con.getOutputStream());
        } catch (Exception e) {
            System.out.println("Error in while prepare connect to " + con.getInetAddress().getHostAddress());
        }
    }

    @Override
    public void run() {
        System.out.println("Connecting with " + con.getInetAddress().getHostAddress());
        MainView.listConnectionModel.addElement(con.getInetAddress().getHostAddress());
        String receiveMsg = "";
        String log = "";
        int indexConnection = MainView.listLogChange.size();
        MainView.listLogChange.add(log);
        try {
            do {
                receiveMsg = inputStream.readUTF();
                System.out.println(con.getInetAddress().getHostAddress() + ": " + receiveMsg);
                log = log + "<br/>" + receiveMsg;
                MainView.listLogChange.set(indexConnection, "<html><pre>" + log +
                        "</pre></html>");
                if (MainView.jListConnection.getSelectedIndex() == indexConnection)
                    MainView.content.setText(MainView.listLogChange.get(indexConnection));
            } while (true);
        } catch (Exception e) {
            System.out.println("Error during connect with " + con.getInetAddress().getHostAddress());
            int index = MainView.listConnectionModel.indexOf(con.getInetAddress().getHostAddress());
            System.out.println("Index:" + index);
            System.out.println(" MainView.listConnectionModel: " +  MainView.listConnectionModel.elementAt(index));
            MainView.listConnectionModel.remove(index);
            System.out.println("Done");
            MainView.listLogChange.remove(index);
            ((MainView) SwingUtilities.getRoot(MainView.content)).refreshConnectionList();
          
            e.printStackTrace();
        }
    }
}
