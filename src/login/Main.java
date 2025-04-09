package login;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

public class Main extends Application {

    private VBox createLoginForm(StackPane container, VBox registerForm) {
        VBox loginForm = new VBox(15);
        loginForm.setAlignment(Pos.CENTER);
        loginForm.setPadding(new Insets(30));
        loginForm.setStyle("-fx-background-color: white; -fx-background-radius: 12;");

        Label title = new Label("Masuk");
        title.setStyle("-fx-font-size: 26px; -fx-font-weight: bold; -fx-text-fill: #333;");

        TextField username = new TextField();
        username.setPromptText("Username");
        username.setPrefWidth(300);

        PasswordField password = new PasswordField();
        password.setPromptText("Password");
        password.setPrefWidth(300);

        Button loginButton = new Button("Masuk");
        loginButton.setStyle("-fx-background-color: #1e90ff; -fx-text-fill: white; -fx-font-size: 16px;");

        Text switchToRegister = new Text("Belum punya akun? Daftar di sini");
        switchToRegister.setStyle("-fx-fill: #1e90ff; -fx-underline: true;");
        switchToRegister.setOnMouseClicked(e -> {
            container.getChildren().setAll(registerForm);
        });

        loginForm.getChildren().addAll(title, username, password, loginButton, switchToRegister);
        return loginForm;
    }

    private VBox createRegisterForm(StackPane container, VBox loginForm) {
        VBox registerForm = new VBox(15);
        registerForm.setAlignment(Pos.CENTER);
        registerForm.setPadding(new Insets(30));
        registerForm.setStyle("-fx-background-color: white; -fx-background-radius: 12;");

        Label title = new Label("Daftar Akun");
        title.setStyle("-fx-font-size: 26px; -fx-font-weight: bold; -fx-text-fill: #333;");

        TextField username = new TextField();
        username.setPromptText("Username");
        username.setPrefWidth(300);

        PasswordField password = new PasswordField();
        password.setPromptText("Password");
        password.setPrefWidth(300);

        Button registerButton = new Button("Daftar");
        registerButton.setStyle("-fx-background-color: #28a745; -fx-text-fill: white; -fx-font-size: 16px;");

        Text switchToLogin = new Text("Sudah punya akun? Masuk di sini");
        switchToLogin.setStyle("-fx-fill: #1e90ff; -fx-underline: true;");
        switchToLogin.setOnMouseClicked(e -> {
            container.getChildren().setAll(loginForm);
        });

        registerForm.getChildren().addAll(title, username, password, registerButton, switchToLogin);
        return registerForm;
    }

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #c6bebe;");

        // Bagian kiri: logo
        VBox logoBox = new VBox();
        logoBox.setAlignment(Pos.CENTER);
        logoBox.setPrefWidth(400);
        Image logo = new Image(getClass().getResourceAsStream("/img/logo.png"));
        ImageView logoView = new ImageView(logo);
        logoView.setFitWidth(250);
        logoView.setPreserveRatio(true);
        logoBox.getChildren().add(logoView);

        // StackPane untuk bergantian form login dan register
        StackPane formContainer = new StackPane();

        // Buat form login dan daftar
        VBox loginForm = createLoginForm(formContainer, null);
        VBox registerForm = createRegisterForm(formContainer, loginForm);

        // Perbaiki referensi silang
        VBox fixedLoginForm = createLoginForm(formContainer, registerForm);
        VBox fixedRegisterForm = createRegisterForm(formContainer, fixedLoginForm);
        formContainer.getChildren().add(fixedLoginForm);

        // Gabungkan kiri dan kanan
        HBox mainLayout = new HBox(50);
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setPadding(new Insets(40));
        mainLayout.getChildren().addAll(logoBox, formContainer);

        root.setCenter(mainLayout);

        Scene scene = new Scene(root, 1200, 600);
        primaryStage.setTitle("Aplikasi POS");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
