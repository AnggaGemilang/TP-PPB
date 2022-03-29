package com.example.klasemenklub;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.klasemenklub.databinding.ActivityDetailClubBinding;

public class DetailClub extends AppCompatActivity {

    private ActivityDetailClubBinding binding;
    private String namaKlub, totalMain, menang, seri, kalah, golMasuk, golKemasukan, selisihGol, poin, peringkat;
    private Integer logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailClubBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Bundle bundle = getIntent().getExtras();
        namaKlub = bundle.getString("nama_klub");
        totalMain = bundle.getString("total_main");
        menang = bundle.getString("menang");
        seri = bundle.getString("seri");
        kalah = bundle.getString("kalah");
        golMasuk = bundle.getString("gol_masuk");
        golKemasukan = bundle.getString("gol_kemasukan");
        selisihGol = bundle.getString("selisih_gol");
        poin = bundle.getString("poin");
        peringkat = bundle.getString("peringkat");
        logo = bundle.getInt("logo");

        getSupportActionBar().setElevation(0);
        getSupportActionBar().setTitle("Detail " + namaKlub);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        binding.txtGolKemasukkan.setText(golKemasukan);
        binding.txtGolMasuk.setText(golMasuk);
        binding.txtJmlPoin.setText(poin);
        binding.txtNamaKlub.setText(namaKlub);
        binding.txtTotalMain.setText(totalMain);
        binding.txtPeringkat.setText("Peringkat ke-" + peringkat);
        binding.txtSeri.setText(seri);
        binding.txtMenang.setText(menang);
        binding.txtSelisihGol.setText(selisihGol);
        binding.txtJmlPoin.setText(poin);
        binding.txtKalah.setText(kalah);
        binding.imgLogoKlub2.setImageResource(logo);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

}