package admin;

import javafx.application.Application;
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

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;

public class ProdukAdminView extends Application {

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
        HBox searchBar = new HBox(10, new Label("\uD83D\uDD0D"), searchField);
        searchBar.setAlignment(Pos.CENTER);

        // ===== TOMBOL TAMBAH & USER =====
        Image tambahImage = new Image(getClass().getResourceAsStream("/img/tambah.png"));
        ImageView tambahIcon = new ImageView(tambahImage);
        tambahIcon.setFitHeight(30);
        tambahIcon.setPreserveRatio(true);
        Button tambahBtn = new Button();
        tambahBtn.setGraphic(tambahIcon);
        tambahBtn.setStyle("-fx-background-color: transparent;");

        Image userImage = new Image(getClass().getResourceAsStream("/img/user.png"));
        ImageView userIcon = new ImageView(userImage);
        userIcon.setFitHeight(30);
        userIcon.setPreserveRatio(true);

        HBox iconContainer = new HBox(10, tambahBtn, userIcon);
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
        table.setStyle("-fx-table-cell-border-color: transparent; -fx-border-color: transparent;");

        TableColumn<produk, String> kodeCol = new TableColumn<>("Kode");
        kodeCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getKode()));

        TableColumn<produk, String> namaCol = new TableColumn<>("Nama Barang");
        namaCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNama()));

        TableColumn<produk, String> hargaCol = new TableColumn<>("Harga");
        hargaCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getHargaFormatted()));

        TableColumn<produk, Void> aksiCol = new TableColumn<>();
        aksiCol.setPrefWidth(100);
        aksiCol.setStyle("-fx-border-color: transparent; -fx-background-color: transparent;");

        aksiCol.setCellFactory(col -> new TableCell<>() {
            private final Button editBtn = new Button();
            private final Button delBtn = new Button();
            private final HBox btnBox = new HBox(10);

            {
                Image editImg = new Image(getClass().getResourceAsStream("/img/edit.png"));
                ImageView editView = new ImageView(editImg);
                editView.setFitHeight(20);
                editView.setPreserveRatio(true);
                editBtn.setGraphic(editView);
                editBtn.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");
                editBtn.setTooltip(new Tooltip("Edit"));

                Image deleteImg = new Image(getClass().getResourceAsStream("/img/delete.png"));
                ImageView deleteView = new ImageView(deleteImg);
                deleteView.setFitHeight(20);
                deleteView.setPreserveRatio(true);
                delBtn.setGraphic(deleteView);
                delBtn.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");
                delBtn.setTooltip(new Tooltip("Hapus"));

                btnBox.setAlignment(Pos.CENTER);
                btnBox.getChildren().addAll(editBtn, delBtn);
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    produk p = getTableView().getItems().get(getIndex());

                    editBtn.setOnAction(e -> {
                        System.out.println("Edit produk: " + p.getNama());
                    });

                    delBtn.setOnAction(e -> {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Konfirmasi Hapus");
                        alert.setHeaderText("Hapus Produk");
                        alert.setContentText("Apakah Anda yakin ingin menghapus produk \"" + p.getNama() + "\"?");

                        alert.showAndWait().ifPresent(response -> {
                            if (response == ButtonType.OK) {
                                getTableView().getItems().remove(p);
                            }
                        });
                    });

                    setGraphic(btnBox);
                    setStyle("-fx-background-color: transparent;");
                }
            }
        });

        table.getColumns().addAll(kodeCol, namaCol, hargaCol, aksiCol);

        // ===== Ambil data dari DataProduk.java =====
        ArrayList<produk> produkList = DataProduk.getProdukList();
        table.getItems().addAll(produkList);

        // ===== WRAPPER TABEL BIAR TENGAH =====
        HBox tableWrapper = new HBox(table);
        tableWrapper.setAlignment(Pos.CENTER);

        // ===== ROOT LAYOUT =====
        VBox root = new VBox(30);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.TOP_CENTER);
        root.getChildren().addAll(topBar, tableWrapper);

        Scene scene = new Scene(root, 950, 500);
        primaryStage.setTitle("Tampilan Admin");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
