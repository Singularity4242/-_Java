import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ThumbnailFunction {
    private final ContextMenu contextMenu = new ContextMenu();

    ThumbnailFunction(String path){
        MenuItem delete = new MenuItem("删除");
        MenuItem copy = new MenuItem("复制");
        MenuItem rename = new MenuItem("重命名");

        delete.setStyle("-fx-text-fill:RED");
        delete.setOnAction(actionEvent -> deleteFuc(path));

        copy.setOnAction(actionEvent -> copyFuc(path));
        copy.setStyle("-fx-text-fill:YELLOW");

        rename.setOnAction(actionEvent -> renameFuc(path));
        rename.setStyle("-fx-text-fill:BLUE");

        contextMenu.getItems().addAll(delete,rename,copy);
        contextMenu.setStyle("-fx-background-color: rgb(255,255,255, .85)");
    }

    public ContextMenu getContextMenu(){
        return contextMenu;
    }

    public void deleteFuc(String path){
        //经测试，不可单击选中后进行删除
        //须在未选中状态下右击删除
        File file = new File(path.substring(5));
        //System.out.println(file);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"确定要删除该图片吗？\n删除后不可恢复！");
        alert.showAndWait().ifPresent(response ->{
            if(response == ButtonType.OK){
                if(file.delete()){
                    //System.out.println("OK");
                    Controller.refresh();
                    flash();
                }
                else System.out.println("False");
            }
        });

    }

    public void copyFuc(String path){
        File source = new File(path.substring(5));
        File dest = new File(source.getParentFile().getAbsolutePath() + '/' + getFileNameNoSuffix(source.getName())
                + "_1" + '.' + source.getAbsolutePath().substring(source.getAbsolutePath().lastIndexOf(".") + 1));
        System.out.println(dest);

        try{
            FileInputStream Fin = new FileInputStream(source);
            FileOutputStream Fout = new FileOutputStream(dest);
            byte[] bytes = new byte[1028];
            int cnt = 0;
            while((cnt = Fin.read(bytes, 0, bytes.length)) != -1){
                Fout.write(bytes, 0 , cnt);
            }
            Fout.flush();
            Fout.close();
            System.out.println("复制成功");
            flash();
            Controller.refresh();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void renameFuc(String path){
        //经测试，同删除功能相同
        //必须在未左键选中状态下右击使用该功能
        File file = new File(path.substring(5));
        TextField textField = new TextField();
        Stage stage = new Stage();
        stage.setAlwaysOnTop(false);
        stage.setTitle("重命名");
        stage.setScene(new Scene(textField, 250, 50));
        stage.show();

        String suffix = file.getAbsolutePath().substring(file.getAbsolutePath().lastIndexOf(".") + 1);



        textField.setOnAction(actionEvent -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"确定要重命名吗？");
            alert.showAndWait().ifPresent(response ->{
                if(response == ButtonType.OK){
                    File newName = new File(file.getParentFile().getAbsolutePath() + '/' + textField.getText() + '.' + suffix);
                    if(file.renameTo(newName)){
                        Controller.refresh();
                        //System.out.println("重命名成功");
                    }
                    else System.out.println("False");
                }
                stage.close();
            });
        });
    }

    public static String getFileNameNoSuffix(String name){
        if((name != null) && (name.length() > 0)){
            int dots = name.lastIndexOf('.');
            if((dots > -1) && dots < (name.length())){
                return name.substring(0, dots);
            }
        }
        return name;
    }

    public void flash(){
        Message message = new Message();
        message.getMessageFromFolder();
    }
}
