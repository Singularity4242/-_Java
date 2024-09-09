import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;

import java.io.File;
import java.io.IOException;

public class PicturePane extends StackPane {
    public static final FlowPane picturePane = new FlowPane();
    public static TreeItem<File> item = new TreeItem<>();
    public static StackPane stackPane = new StackPane();//类似栈的布局
    public ScrollPane scrollPane = new ScrollPane();//滚动视窗
    public static int Integer = 0;

    public void setPicturePane(TreeItem<File> file){
        PicturePane.item = file;
    }

    PicturePane() throws IOException{
        super();
        picturePane.setOrientation(Orientation.HORIZONTAL);
        picturePane.setHgap(25);
        picturePane.setVgap(25);
        picturePane.setStyle("-fx-background-color: white");
        picturePane.setPrefSize(579,600);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefSize(579,600);
        scrollPane.setContent(picturePane);
        this.getChildren().add(scrollPane);
    }

    public void getPicture(TreeItem<File> file){
        setPicturePane(file);
        picturePane.getChildren().clear();
        File[] files = file.getValue().listFiles();
        if(files.length > 0){
            for(File f : files){
                if(!f.isDirectory()){
                    String name = f.getName();
                    String suffix = name.substring(name.lastIndexOf(".") + 1);
                    if(suffix.equals("JPG")||suffix.equals("JPEG")
                            ||suffix.equals("BMP")||suffix.equals("GIF")||suffix.equals("PNG")
                            ||suffix.equals("jpg")||suffix.equals("jpeg")||suffix.equals("bmp")
                            ||suffix.equals("gif")||suffix.equals("png")){
                        setPicture("File:" + f.getAbsolutePath(), name);
                    }
                }
            }
        }
    }

    public static void reFreshPicture(TreeItem<File> file){
        File[] files = file.getValue().listFiles();
        if(files.length > 0){
            for(File f : files){
                if(!f.isDirectory()){
                    String name = f.getName();
                    String suffix = name.substring(name.lastIndexOf(".") + 1);
                    if(suffix.equals("JPG")||suffix.equals("JPEG")
                                    ||suffix.equals("BMP")||suffix.equals("GIF")||suffix.equals("PNG")
                                    ||suffix.equals("jpg")||suffix.equals("jpeg")||suffix.equals("bmp")
                                    ||suffix.equals("gif")||suffix.equals("png")){
                        setPicture("File:" + f.getAbsolutePath(), name);
                    }
                }
            }
        }
    }

    public static void setPicture(String picturePath, String name){
        ThumbnailObject object = new ThumbnailObject(picturePath, name);
        picturePane.getChildren().add(object.getLabel());
    }
}
