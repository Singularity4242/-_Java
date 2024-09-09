import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;

public class TopPane extends Pane {
        public static FlowPane imagePane = new FlowPane();

        TopPane(){
            super();
            setPrefSize(350,718);
            setStyle("-fx-background-color: rgb(255,255,255)");
            setStyle("-fx-border-color: rgb(255,255,255)");

            imagePane.setPadding(new Insets(12,13,14,15));
            imagePane.setOrientation(Orientation.VERTICAL);
            imagePane.setHgap(8);
            imagePane.setVgap(5);

            imagePane.setPrefSize(350,718);
            getChildren().add(imagePane);
        }
}
