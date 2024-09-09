import javafx.scene.control.Label;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileOwnerAttributeView;
import java.nio.file.attribute.UserPrincipal;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ThumbnailMessage {
    private final String imagePath;
    private final String imageName;
    private final String imageSize;
    private final Label messageLabel = new Label();
    private String lastAccess,lastModified;
    private UserPrincipal admin;
    private int imageWidth, imageHeight;

    public String getImagePath(){
        return imagePath;
    }
    public String getImageName(){
        return imageName;
    }
    public String getImageSize(){
        return imageSize;
    }
    public Label getMessageLabel(){
        return messageLabel;
    }

    ThumbnailMessage(String path, String name) throws IOException{
        imagePath = path.substring(5);
        imageName = name;
        Image image = new Image(path);
        File file = new File(imagePath);
        imageSize = String.format("%.2f", file.length() / 1024.0);

        BufferedImage sourceImage = ImageIO.read(new FileInputStream(file));
        imageWidth = sourceImage.getWidth();
        imageHeight = sourceImage.getHeight();

        Path testPath = Paths.get(file.getPath());
        BasicFileAttributeView basicView = Files.getFileAttributeView(testPath, BasicFileAttributeView.class);
        BasicFileAttributes basicButes = basicView.readAttributes();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        lastAccess = dateFormat.format(new Date(basicButes.lastAccessTime().toMillis()));
        lastModified = dateFormat.format(new Date(basicButes.lastAccessTime().toMillis()));
        FileOwnerAttributeView ownerView = Files.getFileAttributeView(testPath, FileOwnerAttributeView.class);
        admin = ownerView.getOwner();

        setMessageLabel();
    }

    public void setMessageLabel(){
        messageLabel.setText("图片名： " + imageName + "\n"
                            + "位置： " + imagePath + "\n"
                            + "图片大小： " + imageSize + "KB\n"
                            + "图片宽度： " + imageWidth + " 像素\n"
                            + "图片高度： " + imageHeight + " 像素\n"
                            + "最后访问时间： "  + lastAccess + "\n"
                            + "最后修改时间： " + lastModified + "\n"
                            + "文件所有者： " + admin);
    }

    public void addMessage(Label label){
        TopPane.imagePane.getChildren().add(label);
    }
}
