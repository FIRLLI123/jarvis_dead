package com.example.barcode.Data;

public class Data_BayarEX {

    private String id,nama,jumlah,harga,keterangan,pembayaran;

    public Data_BayarEX() {
    }

    public Data_BayarEX(String id, String nama, String jumlah, String harga) {
        this.id = id;
        this.nama = nama;
        this.jumlah = jumlah;
        this.harga = harga;
        this.keterangan = keterangan;
        this.pembayaran = pembayaran;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getpembayaran() {
        return pembayaran;
    }

    public void setpembayaran(String pembayaran) {
        this.pembayaran = pembayaran;
    }
}
