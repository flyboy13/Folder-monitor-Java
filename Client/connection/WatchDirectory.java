package connection;

import java.nio.file.*;
import java.io.DataOutputStream;

public class WatchDirectory {

    public void Running(String directoryPath) throws Exception {
        // Đường dẫn đến thư mục cần theo dõi
        // String directoryPath = "ĐƯỜNG_DẪN_THƯ_MỤC";

        // Tạo một đối tượng WatchService
        WatchService watchService = FileSystems.getDefault().newWatchService();

        // Đăng ký thư mục vào WatchService để theo dõi các sự kiện
        Path directory = Paths.get(directoryPath);
        directory.register(watchService, StandardWatchEventKinds.ENTRY_CREATE,
                StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY);

        // Vòng lặp chính để xử lý các sự kiện
        while (true) {
            DataOutputStream send = ServerConnection.outStream;
            WatchKey key;
            try {
                // Lấy một sự kiện từ WatchService
                key = watchService.take();
            } catch (InterruptedException e) {
                System.out.println("The watch service was interrupted. Exiting.");
                send.writeUTF("The watch service was interrupted. Exiting.");
                return;
            }

            // Xử lý tất cả các sự kiện trong WatchKey
            for (WatchEvent<?> event : key.pollEvents()) {
                WatchEvent.Kind<?> kind = event.kind();

                // Nhận được sự kiện tạo mới thư mục
                if (kind == StandardWatchEventKinds.ENTRY_CREATE) {
                    Path createdPath = directory.resolve((Path) event.context());
                    // System.out.println("Thư mục mới được tạo: " + createdPath);
                    if (Files.isDirectory(createdPath)) {
                        send.writeUTF("Thư mục mới được tạo: " + createdPath.toAbsolutePath());
                    }
                    if (Files.isRegularFile(createdPath)) {
                        send.writeUTF("Tập tin mới được tạo: " + createdPath.toAbsolutePath());
                    }

                }

                // Nhận được sự kiện xóa thư mục
                if (kind == StandardWatchEventKinds.ENTRY_DELETE) {
                    Path deletedPath = directory.resolve((Path) event.context());
                    // System.out.println("Thư mục đã bị xóa: " + deletedPath);
                    if (Files.isDirectory(deletedPath)) {
                        send.writeUTF("Thư mục đã bị xóa: " + deletedPath.toAbsolutePath());
                    }
                    if (Files.isDirectory(deletedPath)) {
                        send.writeUTF("Tập tin đã bị xóa: " + deletedPath.toAbsolutePath());
                    }

                }

                // Nhận được sự kiện chỉnh sửa tập tin hoặc thư mục
                if (kind == StandardWatchEventKinds.ENTRY_MODIFY) {
                    Path modifiedPath = directory.resolve((Path) event.context());
                    // int level = getDirectoryLevel(modifiedPath);
                    // System.out.println("Thư mục hoặc tập tin đã bị chỉnh sửa: " + modifiedPath);
                    if (Files.isDirectory(modifiedPath)) {
                        send.writeUTF("Thư mục đã bị chỉnh sửa: " + modifiedPath.toAbsolutePath());
                    }
                    if (Files.isRegularFile(modifiedPath)) {
                        send.writeUTF("Tập tin đã bị chỉnh sửa: " + modifiedPath.toAbsolutePath());
                    }
                }
            }

            // Reset và tiếp tục theo dõi các sự kiện tiếp theo
            boolean valid = key.reset();
            if (!valid) {
                // Đã xảy ra lỗi, thoát khỏi vòng lặp chính
                break;
            }
        }
    }

    private int getDirectoryLevel(Path path) {
        int level = 0;
        for (Path parent = path.getParent(); parent != null; parent = parent.getParent()) {
            level++;
        }
        return level;
    }
}
