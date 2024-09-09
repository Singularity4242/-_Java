import javafx.scene.layout.VBox;

public class Controller {
    public static VBox tempt = new VBox();

    public static void refresh(){
        //刷新图像
        PicturePane.picturePane.getChildren().clear();
        PicturePane.reFreshPicture(PicturePane.item);
        TopPane.imagePane.getChildren().clear();
    }
}