import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ScrollBar;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("电子图片管理程序");
        stage.setMaximized(true);//最大化开放
        stage.setResizable(true);//窗口可变
        BorderPane borderPane = new BorderPane();//底层窗口

        WindowSize windowSize = new WindowSize();
        Scene scene = new Scene(borderPane,windowSize.getWidths(), windowSize.getHeights());
        //System.out.println(windowSize.getWidths() + "+" + windowSize.getHeights());

        TreeDocumentary td = new TreeDocumentary();//目录树
        borderPane.setLeft(td.root);

        TopPane tp = new TopPane();//右侧信息
        borderPane.setRight(tp);

        PicturePane pp = new PicturePane();//中间的缩略图等
        borderPane.setCenter(pp);

        Message message = new Message();//底部信息
        borderPane.setBottom(message.stage);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}