package model;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

public class DataProduk {
    private static final ArrayList<produk> produkList = new ArrayList<>();

    static {
        try {
            // Non-Perishable Products
            produkList.add(new produk.NonPerishableProduct("1", "Sabun", 12000.0));
            produkList.add(new produk.NonPerishableProduct("2", "Sikat Gigi", 10000.0));
            produkList.add(new produk.NonPerishableProduct("3", "Pasta Gigi", 15000.0));

            // Perishable Products
            produkList.add(new produk.PerishableProduct("4", "Susu", 22000.0, LocalDate.of(2025, 5, 15)));
            produkList.add(new produk.PerishableProduct("5", "Roti", 8000.0, LocalDate.of(2025, 4, 10)));

            // Digital Products
            produkList.add(new produk.DigitalProduct("6", "eBook Java", 50000.0, new URL("https://example.com/java-ebook"), "EduBooks"));
            produkList.add(new produk.DigitalProduct("7", "Subscription Musik", 75000.0, new URL("https://example.com/music-subscription"), "MusicStream"));

            // Bundle Product
            ArrayList<produk> isiBundle = new ArrayList<>();
            isiBundle.add(new produk.NonPerishableProduct("8", "Handuk", 30000.0));
            isiBundle.add(new produk.PerishableProduct("9", "Keju", 25000.0, LocalDate.of(2025, 4, 20)));
            produkList.add(new produk.BundleProduct("10", "Paket Mandi & Camilan", isiBundle));

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<produk> getProdukList() {
        return produkList;
    }
}
