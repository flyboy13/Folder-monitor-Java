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
            System.out.println(fileName);
            out.writeUTF(fileName);
            System.out.println(fileName);
            FileInputStream fileStream = new FileInputStream(file);
            byte[] buffer = new byte[40 * 1024];
            int count;
            while ((count = fileStream.read(buffer)) > 0)
                out.write(buffer, 0, count);
        } catch (Exception e) {
            System.out.println("Error in while send file");
            return -1;
        }
        return 1;
    }

    public static int receiveFile(DataInputStream in, String path, String note) {
        try {
            String fileName = in.readUTF();
            if (note == "AVATAR_IMG")
                fileName = "avatar_img";
            System.out.println("Pre-read");
            FileOutputStream fileStream = new FileOutputStream(path + fileName);
            System.out.println("after-read");
            byte[] buffer = new byte[16 * 1024];
            int count;
            while ((count = in.read(buffer)) > 0) {
                System.out.println(count);
                fileStream.write(buffer, 0, count);
                System.out.println(count + 1);
                if (count < 16 * 1024)
                    break;
            }
            fileStream.close();
        } catch (Exception e) {
            System.out.println("Error in while receive file");
            e.printStackTrace();
            return -1;
        }
        return 1;
    }
}
