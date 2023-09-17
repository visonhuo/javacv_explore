package com.vison.javacv.gui.chapter1;

import com.vison.javacv.gui.utils.SwingFXUtils;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.bytedeco.opencv.global.opencv_core;
import org.bytedeco.opencv.global.opencv_imgcodecs;
import org.bytedeco.opencv.global.opencv_imgproc;
import org.bytedeco.opencv.opencv_core.Mat;

import java.io.File;

public class Ex2MyFirstGUIApp {

    public static class App extends Application {

        // 主界面
        private Stage primaryStage;

        // 文件选择控件
        private FileChooser fileChooser;

        // 图片展示画布
        private ImageView imageView;

        // 文件保存路径
        private String imageFilePath;

        // 读取的图片文件
        private Mat imageMat;

        @Override
        public void start(Stage primaryStage) {
            this.primaryStage = primaryStage;
            this.fileChooser = new FileChooser();
            this.imageView = new ImageView();

            Button btnOpenImage = new Button("Open Image");
            btnOpenImage.setOnAction(this::onOpenImage);
            Button btnProcess = new Button("Flip upside down");
            btnProcess.setOnAction(this::onProcess);
            Button btnSave = new Button("Save Image");
            btnSave.setOnAction(this::onSave);
            VBox left = new VBox(btnOpenImage, btnProcess, btnSave);
            left.setSpacing(5);
            left.setPrefWidth(120);

            BorderPane borderPane = new BorderPane();
            borderPane.setLeft(left);
            borderPane.setCenter(new VBox(this.imageView));

            Scene scene = new Scene(borderPane);
            primaryStage.setTitle("My JavaFX Image Browser");
            primaryStage.setScene(scene);
            primaryStage.show();
        }

        private void onOpenImage(ActionEvent event) {
            File file = fileChooser.showOpenDialog(this.primaryStage);
            Mat newImage = opencv_imgcodecs.imread(file.getAbsolutePath());
            if (newImage.empty()) {
                showErrorAlert("Open Image", String.format("Cannot open image file: %s", file.getPath()));
                return;
            }

            if (this.imageMat != null) {
                this.imageMat.release();
            }
            this.imageFilePath = file.getAbsolutePath();
            this.imageMat = newImage;
            onRefreshImageView();
        }

        private void onProcess(ActionEvent event) {
            if (this.imageMat == null || this.imageMat.empty()) {
                showErrorAlert("Process", "Image not opened!");
                return;
            }

            // Flip upside down
            opencv_core.flip(this.imageMat, this.imageMat, 0);
            opencv_imgproc.cvtColor(this.imageMat, this.imageMat, opencv_imgproc.COLOR_BGR2RGB);
            onRefreshImageView();
        }

        private void onSave(ActionEvent event) {
            if (this.imageMat == null || this.imageMat.empty()) {
                showErrorAlert("Save", "Image not opened!");
                return;
            }

            // Save current image
            StringBuilder sb = new StringBuilder();
            sb.append(imageFilePath, 0, imageFilePath.lastIndexOf(".")).append("_updated")
                    .append(imageFilePath.substring(imageFilePath.lastIndexOf(".")));
            if (opencv_imgcodecs.imwrite(sb.toString(), this.imageMat)) {
                showInfoAlert("Save", String.format("Save file success, path: %s", sb));
            } else {
                showErrorAlert("Save", String.format("Save file failed, path: %s", sb));
            }
        }

        private void onRefreshImageView() {
            if (this.imageMat != null && !this.imageMat.empty()) {
                this.imageView.setImage(SwingFXUtils.toFxImage(this.imageMat));
                this.primaryStage.sizeToScene();
            }
        }

        private void showErrorAlert(String title, String headerText) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(this.primaryStage);
            alert.setTitle(title);
            alert.setHeaderText(headerText);
            alert.showAndWait();
        }

        private void showInfoAlert(String title, String contentText) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.initOwner(this.primaryStage);
            alert.setTitle(title);
            alert.setContentText(contentText);
            alert.showAndWait();
        }
    }

    public static void main(String[] args) {
        Application.launch(App.class);
    }
}
