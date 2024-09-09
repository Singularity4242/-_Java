import javafx.scene.control.Label;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;

import java.io.File;
import java.io.IOException;

public class TreeDocumentary extends TreeView <File> {
    public StackPane root = new StackPane();

    TreeDocumentary() throws IOException{
        setPrefSize(300, 700);//初始化窗口大小
        setTreeDoc();
    }

    public void setTreeDoc() throws IOException{

        PicturePane pp = new PicturePane();//缩略图窗口对象
        ImageView view = new ImageView();//图像对象

        /*初始化图像大小及*/
        view.setFitWidth(16);
        view.setFitHeight(16);
        view.setPreserveRatio(true);//设置保存缩放比例
        /*初始化图像大小*/

        /*根目录*/
        String path = "D:\\DeskTop\\test";
        /*根目录*/

        File file = new File(path);
        File[] files = file.listFiles();//取路径下文件目录

        TreeItem<File> treeItem = new TreeItem<>(file);
        TDController tdController = new TDController();//目录树事件控制器

        for(File f : files){
            if(f.isDirectory()&&f.getName().charAt(0)!='.'){
                TreeItem<File> treeItem1 = new TreeItem<>(f);
                treeItem.getChildren().add(treeItem1);
                addItem(treeItem1, 0);
            }
        }
        TreeView<File> treeView = new TreeView<>(treeItem);
        root.getChildren().add(treeView);
        treeView.setRoot(treeItem);
        treeView.setShowRoot(false);

        treeView.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {

            String path1 = newValue.getValue().getAbsolutePath();
            /*控制事件*/
            tdController.initFolder(path1);
            try{
                addItem(newValue, 0);
                pp.getPicture(newValue);
                Message.folder = newValue;
                Message message = new Message();
                message.getMessageFromFolder();
            } catch (IOException e){
                e.printStackTrace();
            }
            /*利用控制器*/
        });

        treeView.setCellFactory(new Callback<TreeView<File>, TreeCell<File>>() {

            @Override
            public TreeCell<File> call(TreeView<File> param) {
                return new TreeCell<File>() {
                    @Override
                    protected void updateItem(File item, boolean empty) {

                        if (!empty) {
                            super.updateItem(item, false);
                            HBox hBox = new HBox();
                            Label label = new Label(listRoot(item));
                            this.setGraphic(hBox);
                            this.setStyle("-fx-border-color: rgb(244,244,244)");
                            if (this.getTreeItem().isExpanded()) {
                                this.setStyle("-fx-background-color: rgb(170,170,170)");
                            }
                            hBox.getChildren().add(label);//把label加到hBox面板中

                        } else {
                            this.setGraphic(null);
                        }
                    }
                };
            }
        });
    }

    public void addItem(TreeItem<File> Fin, int flag) throws IOException{
        File[] files = Fin.getValue().listFiles();
        if(files != null){
            if(flag == 0){
                Fin.getChildren().remove(0,Fin.getChildren().size());
            }
            if(files.length > 0){
                for(File f : files){
                    if(f.isDirectory() && !f.isHidden()){
                        TreeItem<File> Item = new TreeItem<>(f);
                        if(flag < 1){
                            addItem(Item, flag + 1);
                        }
                        Fin.getChildren().add(Item);
                    }
                }
            }
        }
    }

    public String listRoot(File file){
        File[] rootList = file.listRoots();
        for(File is : rootList){
            if(file.toString().equals(is.toString())){
                return file.toString();
            }
        }
        return file.getName();
    }
}
