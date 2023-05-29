package connection;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class FileHandle {
    public static int sendFile(DataOutputStream out, String path) {
        try {
            File file = new File(path);
            String fileName = file.getPath().substring(file.getPath().lastIndexOf('\\') + 1);
            out.writeUTF(fileName);
            FileInputStream fileStream = new FileInputStream(file);
            byte[] buffer = new byte[16 * 1024];
            int count;
            while ((count = fileStream.read(buffer)) > 0)
                out.write(buffer, 0, count);
        } catch (Exception e) {
            System.out.println("Error in while send file");
            return -1;
        }
        return 1;
    }

    public static int receiveFile(DataInputStream in, String path) {
        try {
            String fileName = in.readUTF();
            FileOutputStream fileStream = new FileOutputStream(path + fileName);
            byte[] buffer = new byte[40 * 1024];
            int count;
            while ((count = in.read(buffer)) > 0)
                fileStream.write(buffer, 0, count);
        } catch (Exception e) {
            System.out.println("Error in while receive file");
            return -1;
        }
        return 1;
    }
}
