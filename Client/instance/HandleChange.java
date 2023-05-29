package instance;

import java.io.DataOutputStream;
import java.io.File;

import connection.ServerConnection;

public class HandleChange implements Runnable {
    File preList[], list[];
    long[] modified;

    public HandleChange(File[] preList, File[] list, long[] modified) {
        this.preList = preList;
        this.list = list;
        this.modified = modified;
    }

    private boolean checkExist(File file, File[] list) {
        for (int i = 0; i < list.length; i++)
            if (file.compareTo(list[i]) == 0)
                return true;
        return false;
    }

    @Override
    public void run() {
        DataOutputStream out = ServerConnection.outStream;
        try {
            if (preList.length > list.length)
                for (int i = 0; i < preList.length; i++)
                    if (!checkExist(preList[i], list)) {
                        System.out.println("File da bi xoa: " + preList[i].getName());
                        out.writeUTF("File da bi xoa: " + preList[i].getName());
                        return;
                    }
            // --------------------
            if (preList.length < list.length)
                for (int i = 0; i < list.length; i++)
                    if (!checkExist(list[i], preList)) {
                        System.out.println("File vua them: " + list[i].getName());
                        out.writeUTF("File vua them: " + list[i].getName());
                        return;
                    }
            // -----------------
            for (int i = 0; i < list.length; i++) {
                if ((list[i].compareTo(preList[i]) == 0) && (list[i].lastModified() != modified[i])) {
                    System.out.println("File bi chinh sua noi dung: " + preList[i].getName());
                    out.writeUTF("File bi chinh sua noi dung: " + preList[i].getName());
                    return;
                }
                if (!checkExist(preList[i], list)) {
                    for (int j = 0; j < preList.length; j++)
                        if (!checkExist(list[j], preList)) {
                            System.out.println(
                                    "File da bi doi ten: " + preList[i].getName() + " -------- " + list[j].getName());
                            out.writeUTF(
                                    "File da bi doi ten: " + preList[i].getName() + " -------- " + list[j].getName());
                            return;
                        }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Thread stoped");
        return;
    }
}
