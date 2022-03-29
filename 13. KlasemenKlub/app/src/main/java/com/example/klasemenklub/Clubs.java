package com.example.klasemenklub;

public class Clubs {
    public String nama_klub;
    public Integer total_main;
    public Integer menang;
    public Integer seri;
    public Integer kalah;
    public Integer gol_masuk;
    public Integer gol_kemasukan;
    public Integer selisih_gol;
    public Integer poin;
    public Integer peringkat;
    public Integer logo;

    public Clubs(String nama_klub, Integer total_main, Integer menang, Integer seri, Integer kalah, Integer gol_masuk, Integer gol_kemasukan, Integer selisih_gol, Integer poin, Integer peringkat, Integer logo){
        this.nama_klub = nama_klub;
        this.total_main = total_main;
        this.menang = menang;
        this.seri = seri;
        this.kalah = kalah;
        this.gol_masuk = gol_masuk;
        this.gol_kemasukan = gol_kemasukan;
        this.selisih_gol = selisih_gol;
        this.poin = poin;
        this.peringkat = peringkat;
        this.logo = logo;
    }
}
