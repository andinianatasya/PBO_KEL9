package user;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.produk;
import model.DataProduk;

import java.util.ArrayList;

public class ProdukUserView extends Application {

    @Override
    public void start(Stage primaryStage) {
        // ===== LOGO =====
        Image logo = new Image(getClass().getResourceAsStream("/img/logo.png"));
        ImageView logoView = new ImageView(logo);
        logoView.setFitHeight(40);
        logoView.setPreserveRatio(true);

        // ===== SEARCH BAR =====
        TextField searchField = new TextField();
        searchField.setPromptText("Cari Produk (Kode)");
        searchField.setPrefWidth(300);
        HBox searchBar = new HBox(10, new Label("🔍"), searchField);
        searchBar.setAlignment(Pos.CENTER);

        // ===== ICON KERANJANG & USER =====
        Image cartImage = new Image(getClass().getResourceAsStream("/img/cart.png"));
        ImageView cartIcon = new ImageView(cartImage);
        cartIcon.setFitHeight(30);
        cartIcon.setPreserveRatio(true);

        Image userImage = new Image(getClass().getResourceAsStream("/img/user.png"));
        ImageView userIcon = new ImageView(userImage);
        userIcon.setFitHeight(30);
        userIcon.setPreserveRatio(true);

        HBox iconContainer = new HBox(10, cartIcon, userIcon);
        iconContainer.setAlignment(Pos.CENTER_RIGHT);

        BorderPane topBar = new BorderPane();
        topBar.setLeft(logoView);
        topBar.setCenter(searchBar);
        topBar.setRight(iconContainer);
        topBar.setPadding(new Insets(10));

        // ===== TABLE PRODUK =====
        TableView<produk> table = new TableView<>();
        table.setPrefWidth(900);
        table.setMaxWidth(900);
        table.setMinWidth(900);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setStyle("-fx-table-cell-border-color: transparent;");

        TableColumn<produk, String> kodeCol = new TableColumn<>("Kode");
        kodeCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getKode()));

        TableColumn<produk, String> namaCol = new TableColumn<>("Nama Barang");
        namaCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNama()));

        TableColumn<produk, String> hargaCol = new TableColumn<>("Harga");
        hargaCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getHargaFormatted()));

        TableColumn<produk, Void> aksiCol = new TableColumn<>("");
        aksiCol.setStyle("-fx-border-color: transparent;");
        aksiCol.setPrefWidth(100);

        aksiCol.setCellFactory(col -> new TableCell<>() {
            private final Button addBtn = new Button();

            {
                Image cartImg = new Image(getClass().getResourceAsStream("/img/cart.png"));
                ImageView cartView = new ImageView(cartImg);
                cartView.setFitHeight(20);
                cartView.setPreserveRatio(true);
                addBtn.setGraphic(cartView);
                addBtn.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");
                addBtn.setTooltip(new Tooltip("Tambahkan ke Keranjang"));
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                    setStyle("");
                } else {
                    produk p = getTableView().getItems().get(getIndex());

                    addBtn.setOnAction(e -> {
                        System.out.println("Tambah ke keranjang: " + p.getNama());
                    });

                    setGraphic(addBtn);
                    setAlignment(Pos.CENTER);
                    setStyle("-fx-border-width: 0; -fx-background-color: transparent; -fx-padding: 0;");
                }
            }
        });

        table.getColumns().addAll(kodeCol, namaCol, hargaCol, aksiCol);

        // ===== Ambil data dari DataProduk.java =====
        ArrayList<produk> produkList = DataProduk.getProdukList();
        table.getItems().addAll(produkList);

        // ===== WRAPPER TABEL =====
        HBox tableWrapper = new HBox(table);
        tableWrapper.setAlignment(Pos.CENTER);

        // ===== ROOT LAYOUT =====
        VBox root = new VBox(30);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.TOP_CENTER);
        root.getChildren().addAll(topBar, tableWrapper);

        Scene scene = new Scene(root, 950, 500);
        primaryStage.setTitle("Tampilan User");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
