package instance;

import javax.swing.ImageIcon;
import java.awt.Image;

public class IconList {
    public static ImageIcon favicon, phone_icon, call_video_icon, emoji_icon;

    public static void initializeListIcon() {

    }

    public static void initListIcon() {
        try {
            favicon = new ImageIcon("./image/messenger_icon.png");
            phone_icon = new ImageIcon("./image/phone.png");
            call_video_icon = new ImageIcon("./image/call_video.png");
            emoji_icon = new ImageIcon("./image/emoji.png");
        } catch (Exception e) {
            System.out.println("Error in while load IconList");
        }
    }

    public static ImageIcon scaleIcon(ImageIcon img, Double scale) {
        int width = (int) (img.getIconWidth() * scale);
        int height = (int) (img.getIconHeight() * scale);
        Image newImg = img.getImage();
        ImageIcon result = new ImageIcon(newImg.getScaledInstance(width, height, height));
        return result;
    }
}
