import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ThumbnailStage {
    private final Label imageLabel = new Label();

    ThumbnailStage(String imagePath, int width, int height){
        TopPane.imagePane.getChildren().clear();
        imageLabel.setPrefSize(width,height);
        imageLabel.setAlignment(Pos.BASELINE_CENTER);
        Image image = new Image(imagePath);
        ImageView view = new ImageView(image);
        view.setFitWidth(width);
        view.setFitHeight(height);
        view.setPreserveRatio(true);
        imageLabel.setGraphic(view);
    }

    public Label getLabel(){
        return imageLabel;
    }

    public void addLabel(Label label){
        TopPane.imagePane.getChildren().add(label);
    }
}
