package model;

import java.time.LocalDate;
import java.net.URL;
import java.util.List;

public abstract class produk {
    protected String kode;
    protected String nama;
    protected double harga;

    public produk(String kode, String nama, double harga) {
        this.kode = kode;
        this.nama = nama;
        this.harga = harga;
    }

    public String getKode() {
        return kode;
    }

    public String getNama() {
        return nama;
    }

    public double getHarga() {
        return harga;
    }

    public String getHargaFormatted() {
        return String.format("Rp%,.2f", harga).replace('.', ',');
    }

    public abstract String getTipe();
    public abstract String getDetail();

    // --------------------------------------------
    // Class NonPerishableProduct
    public static class NonPerishableProduct extends produk {
        public NonPerishableProduct(String kode, String nama, double harga) {
            super(kode, nama, harga);
        }

        @Override
        public String getTipe() {
            return "Non-Perishable";
        }

        @Override
        public String getDetail() {
            return "Produk tahan lama";
        }
    }

    // --------------------------------------------
    // Class PerishableProduct
    public static class PerishableProduct extends produk {
        private LocalDate tanggalKadaluarsa;

        public PerishableProduct(String kode, String nama, double harga, LocalDate tanggalKadaluarsa) {
            super(kode, nama, harga);
            this.tanggalKadaluarsa = tanggalKadaluarsa;
        }

        @Override
        public String getTipe() {
            return "Perishable";
        }

        @Override
        public String getDetail() {
            return "Kadaluarsa: " + tanggalKadaluarsa;
        }
    }

    // --------------------------------------------
    // Class DigitalProduct
    public static class DigitalProduct extends produk {
        private URL url;
        private String vendor;

        public DigitalProduct(String kode, String nama, double harga, URL url, String vendor) {
            super(kode, nama, harga);
            this.url = url;
            this.vendor = vendor;
        }

        @Override
        public String getTipe() {
            return "Digital";
        }

        @Override
        public String getDetail() {
            return "Vendor: " + vendor + " | URL: " + url.toString();
        }
    }

    // --------------------------------------------
    // Class BundleProduct
    public static class BundleProduct extends produk {
        private List<produk> isiBundle;

        public BundleProduct(String kode, String nama, List<produk> isiBundle) {
            super(kode, nama, hitungHargaTotal(isiBundle));
            this.isiBundle = isiBundle;
        }

        public double getHargaDiskon() {
            return harga * 0.9;
        }

        private static double hitungHargaTotal(List<produk> list) {
            return list.stream().mapToDouble(produk::getHarga).sum();
        }

        @Override
        public String getTipe() {
            return "Bundle";
        }

        @Override
        public String getDetail() {
            return "Isi bundle: " + isiBundle.size() + " produk, Harga diskon: Rp" + getHargaDiskon();
        }
    }
}