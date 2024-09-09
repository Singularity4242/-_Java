import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.VBox;

import java.io.File;

public class Message {
    public static VBox stage = new VBox();
    private static int imageCnt = 0;
    private static int imageSize = 0;
    public static TreeItem<File> folder = new TreeItem<>();
    public static Label labelCnt = new Label();
    public static Label labelSize = new Label();

    public Message(){
        stage.setPrefSize(50,50);
    }

    public void getMessageFromFolder(){
        stage.getChildren().clear();
        File[] files = folder.getValue().listFiles();
        if(files.length > 0){
            for(File f : files){
                if(!f.isDirectory()){
                    String name = f.getName();
                    String suffix = name.substring(name.lastIndexOf(".") + 1);
                    if(suffix.equals("JPG")||suffix.equals("JPEG")
                            ||suffix.equals("BMP")||suffix.equals("GIF")||suffix.equals("PNG")
                            ||suffix.equals("jpg")||suffix.equals("jpeg")||suffix.equals("bmp")
                            ||suffix.equals("gif")||suffix.equals("png")){
                        imageCnt++;
                        File file = new File(f.getAbsolutePath());
                        System.out.println(file);
                        imageSize += file.length() / 1024.0;
                    }
                }
            }
        }
        System.out.println(imageCnt);
        setMessageToFolder();
    }

    public void setMessageToFolder(){
        labelCnt.setText(imageCnt + "张图片");
        labelSize.setText("总大小：" + imageSize + "KB");
        stage.getChildren().addAll(labelCnt, labelSize);
        imageCnt = 0;imageSize = 0;
    }

}
