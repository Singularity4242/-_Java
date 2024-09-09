import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;

public class ThumbnailObject {
    private final String imageName, imagePath;
    private final Labeled imageLabel = new Label();
    private final VBox vBox = new VBox();
    private final HBox hBox = new HBox();

    ThumbnailObject(String imagePath, String imageName){
        this.imagePath = imagePath;
        this.imageName = imageName;

        Image image = new Image(imagePath);
        ImageView view = new ImageView(image);
        view.setPreserveRatio(true);
        view.setFitWidth(100);
        view.setFitHeight(100);
        imageLabel.setGraphic(view);
        Label ImageName = new Label();
        ImageName.setText(imageName);
        imageLabel.setAlignment(Pos.BASELINE_CENTER);
        imageLabel.setPrefSize(100,100);
        vBox.setPrefSize(100,100);
        vBox.setPadding(new Insets(5,5,5,5));
        vBox.getChildren().addAll(imageLabel, ImageName);


        //点击图片事件
        setClick();
        //右击图片事件
        ThumbnailFunction function = new ThumbnailFunction(imagePath);
        imageLabel.setContextMenu(function.getContextMenu());
    }

    public void setClick(){
        imageLabel.setOnMouseClicked(mouseEvent -> {
            if(Controller.tempt != null){
                Controller.tempt.setStyle("-fx-background-color: rgb(255,255,255)");
                Controller.tempt = vBox;
                vBox.setStyle("-fx-background-color: gray");
            }
            else{
                Controller.tempt = vBox;
                vBox.setStyle("-fx-background-color: rgb(255,255,255)");
            }

            if(mouseEvent.getButton() == MouseButton.PRIMARY && mouseEvent.getClickCount() == 2){
                new DoubleClickImage(imagePath,imageName);
            }
            else if (mouseEvent.getButton() == MouseButton.PRIMARY && mouseEvent.getClickCount() == 1){
                PicturePane.Integer = 1;
                //System.out.println("321\n");
                ThumbnailStage stage = new ThumbnailStage(imagePath, 300, 300);
                stage.addLabel(stage.getLabel());

                ThumbnailMessage Thumb = null;
                try{
                    Thumb = new ThumbnailMessage(imagePath, imageName);
                    PicturePane.Integer = 0;
                }catch (IOException e){
                    e.printStackTrace();
                }
                assert Thumb != null;
                Thumb.addMessage(Thumb.getMessageLabel());
            }
        });
    }

    public Node getLabel(){
        return vBox;
    }
}
