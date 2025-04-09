package user;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.Product;
import model.DataProduk;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

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
        cartIcon.setOnMouseClicked(event -> {
            try {
                KeranjangUser keranjang = new KeranjangUser();
                Stage keranjangStage = new Stage();
                keranjang.start(keranjangStage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

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
        TableView<Product> table = new TableView<>();
        table.setPrefWidth(900);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setStyle("-fx-table-cell-border-color: transparent; -fx-border-color: transparent;");

        TableColumn<Product, String> kodeCol = new TableColumn<>("Kode");
        kodeCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getKode()));

        TableColumn<Product, String> namaCol = new TableColumn<>("Nama Barang");
        namaCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNama()));

        TableColumn<Product, String> hargaCol = new TableColumn<>("Harga");
        hargaCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getHargaFormatted()));

        TableColumn<Product, Void> aksiCol = new TableColumn<>("");
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
                } else {
                    Product p = getTableView().getItems().get(getIndex());
                    addBtn.setOnAction(e -> {
                        System.out.println("Tambah ke keranjang: " + p.getNama());
                        // TODO: tambahkan logika simpan ke keranjang
                    });
                    setGraphic(addBtn);
                }
            }
        });

        table.getColumns().addAll(kodeCol, namaCol, hargaCol, aksiCol);

        // ===== Ambil data dari database via DataProduk.java =====
        ObservableList<Product> masterData = FXCollections.observableArrayList();
        try {
            Connection conn = DriverManager.getConnection("jdbc:postgresql://aws-0-ap-southeast-1.pooler.supabase.com:6543/postgres", "postgres.jnmxqxmrgwmmupkozavo", "kelompok9"); // sesuaikan
            DataProduk.loadProdukFromDatabase(conn);
            List<Product> produkList = DataProduk.getProdukList();
            masterData.addAll(produkList);
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Gagal mengambil data produk.");
            alert.show();
        }

        // FilteredList untuk pencarian
        FilteredList<Product> filteredData = new FilteredList<>(masterData, p -> true);
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(product -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                return product.getKode().toLowerCase().contains(lowerCaseFilter);
            });
        });

        table.setItems(filteredData);

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
