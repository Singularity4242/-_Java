import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class DoubleClickImage {
        private BorderPane borderPane = new BorderPane();
        private ScrollPane scrollPane = new ScrollPane();
        private String imagePath, imageName;
        private Label imageLabel = new Label();
        private HBox hBox = new HBox();
        private double times = 1;
        private WindowSize windowSize;
        private Image image;
        private static boolean auto = false;
        ImageView view;Stage stage;

        DoubleClickImage(String path, String name){
            imagePath = path;imageName = name;

            stage = new Stage();
            stage.setTitle(imageName);
            windowSize = new WindowSize();
            init(imagePath);
            addMenu();
            borderPane.setPrefSize(windowSize.getWidth(), windowSize.getHeight());
            stage.setScene(new Scene(borderPane, windowSize.getWidth(), windowSize.getHeight()));
            stage.show();
        }

        private void init(String path){
            image = new Image(path);
            view = new ImageView(image);
            view.setFitWidth(WindowSize.getWidth() - 100);
            view.setFitHeight(WindowSize.getHeight() - 150);
            view.setPreserveRatio(true);
            borderPane.setCenter(scrollPane);
            addImage(view);
        }

        public void addImage(ImageView view){
            imageLabel.setPrefSize(windowSize.getWidth() - 100, windowSize.getHeight() - 150);
            imageLabel.setAlignment(Pos.BASELINE_CENTER);
            imageLabel.setGraphic(view);
            scrollPane.setContent(imageLabel);
            scrollPane.setPadding(new Insets(0, 50, 0, 50));
            scrollPane.setHvalue(0.5);
            scrollPane.setVvalue(0.5);
        }

        public void addMenu(){
            hBox.setPrefSize(windowSize.getWidth() - 100, 100);
            hBox.setSpacing(20);
            hBox.setAlignment(Pos.CENTER);
            hBox.getChildren().clear();

            Button enLarge = new Button("放大");
            Button narrow = new Button("缩小");
            Button moveLeft = new Button("<--");
            Button moveRight = new Button("-->");
            Button autoDisplay = new Button("自动播放");

            autoDisplay.setStyle("-fx-min-height: 30; -fx-min-width: 80; -fx-background-color: rgb(210, 210, 210)");
            enLarge.setStyle("-fx-min-height: 30; -fx-min-width: 80; -fx-background-color: rgb(210, 210, 210)");
            narrow.setStyle("-fx-min-height: 30; -fx-min-width: 80; -fx-background-color: rgb(210, 210, 210)");
            moveLeft.setStyle("-fx-min-height: 30; -fx-min-width: 80; -fx-background-color: rgb(210, 210, 210)");
            moveRight.setStyle("-fx-min-height: 30; -fx-min-width: 80; -fx-background-color: rgb(210, 210, 210)");

            autoDisplay.setPrefSize(60, 60);
            enLarge.setPrefSize(60, 60);
            narrow.setPrefSize(60, 60);
            moveLeft.setPrefSize(60, 60);
            moveRight.setPrefSize(60, 60);

            hBox.getChildren().addAll(moveLeft, enLarge, narrow, moveRight, autoDisplay);

            autoDisplay.setOnAction(event -> {
                if(auto){
                    auto = false;
                    autoDisplay.setText("自动播放");
                }else {
                    auto = true;
                    autoDisplay.setText("停止自动播放");
                }

                ProcessSupport support = new ProcessSupport(i ->{
                    try{
                        moveRightImage();
                        Thread.sleep(1000);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    return auto;
                });
            });

            enLarge.setOnAction(event -> enLargeImage());

            narrow.setOnAction(event -> narrowImage());

            moveLeft.setOnAction(event -> moveLeftImage());

            moveRight.setOnAction(event -> moveRightImage());

            borderPane.setBottom(hBox);
        }

        private void enLargeImage(){
            times += 0.15;
            changeSize();
        }

        private void narrowImage(){
            times -= 0.15;
            if(times <= 0){
                times += 0.15;
            }
            changeSize();
        }

        private void changeSize(){
            imageLabel.setPrefSize((windowSize.getWidth() - 100) * times, (windowSize.getHeight() - 150) * times);
            view.setFitWidth((windowSize.getWidth() - 100) * times);
            view.setFitHeight((windowSize.getHeight() - 150) * times);
            scrollPane.setVvalue(0.5);
            scrollPane.setHvalue(0.5);
        }

        private void moveLeftImage(){
            File file = new File(imagePath.substring(5));
            File parent = new File(file.getParent());
            File[] files = parent.listFiles();
            int cnt = 0;
            for(int i = 0; i < files.length; i ++ ){
                if(!files[i].isDirectory()){
                    String name = files[i].getName();
                    String suffix = name.substring(name.lastIndexOf(".") + 1);
                    if(suffix.equals("JPG")||suffix.equals("JPEG")
                            ||suffix.equals("BMP")||suffix.equals("GIF")||suffix.equals("PNG")
                            ||suffix.equals("jpg")||suffix.equals("jpeg")||suffix.equals("bmp")
                            ||suffix.equals("gif")||suffix.equals("png"))
                        cnt = i;
                }
            }
            int t1 = -1, t2 = -1;
            for(int i = 0; i < files.length; i ++ ){
                if(!files[i].isDirectory()){
                    String name = files[i].getName();
                    String suffix = name.substring(name.lastIndexOf(".") + 1);
                    if(suffix.equals("JPG")||suffix.equals("JPEG")
                            ||suffix.equals("BMP")||suffix.equals("GIF")||suffix.equals("PNG")
                            ||suffix.equals("jpg")||suffix.equals("jpeg")||suffix.equals("bmp")
                            ||suffix.equals("gif")||suffix.equals("png")){
                        t1 = t2;t2 = i;
                        if(files[i].getAbsolutePath().equals(imagePath.substring(5))){
                            if(t1 == -1) t1 = cnt;
                            break;
                        }
                    }
                }
            }
            imagePath = "File:" + files[t1].getAbsolutePath();
            view = new ImageView(new Image(imagePath));
            view.setFitWidth(windowSize.getWidth() - 100);
            view.setFitHeight(windowSize.getHeight() - 150);
            view.setPreserveRatio(true);

            files[t1].getName();
            addImage(view);
            times = 1;
        }

        private void moveRightImage(){
            File file = new File(imagePath.substring(5));
            File parent = new File(file.getParent());
            File[] files = parent.listFiles();
            int t = 0, t1 = 0, t2 = 0;
            First:for(int i = 0; i < files.length; i ++ ){
                if(!files[i].isDirectory()){
                    String name = files[i].getName();
                    String suffix = name.substring(name.lastIndexOf(".") + 1);
                    if(suffix.equals("JPG")||suffix.equals("JPEG")
                            ||suffix.equals("BMP")||suffix.equals("GIF")||suffix.equals("PNG")
                            ||suffix.equals("jpg")||suffix.equals("jpeg")||suffix.equals("bmp")
                            ||suffix.equals("gif")||suffix.equals("png")){
                        if (t1 == 0){
                            t2 = i;t1 ++;
                        }
                        if(files[i].getAbsolutePath().equals(imagePath.substring(5))){
                            for(int j = i + 1; j < files.length; j ++ ){
                                if(!files[j].isDirectory()){
                                    name = files[j].getName();
                                    suffix = name.substring(name.lastIndexOf(".") + 1);
                                    if(suffix.equals("JPG")||suffix.equals("JPEG")
                                            ||suffix.equals("BMP")||suffix.equals("GIF")||suffix.equals("PNG")
                                            ||suffix.equals("jpg")||suffix.equals("jpeg")||suffix.equals("bmp")
                                            ||suffix.equals("gif")||suffix.equals("png")){
                                        t = j;
                                        System.out.println(t);
                                        break First;
                                    }
                                }
                            }
                            t = t2;break;
                        }
                    }
                }
            }
            imagePath = "File:" + files[t].getAbsolutePath();
            view = new ImageView(new Image(imagePath));
            view.setPreserveRatio(true);
            stage.setTitle(files[t].getName());
            addImage(view);
            times = 1;
        }
}
