package com.example.klasemenklub;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.klasemenklub.adapter.AdapterClub;
import com.example.klasemenklub.databinding.ActivityMainBinding;
import com.example.klasemenklub.viewmodel.ClubViewModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ClubViewModel clubViewModel;
    private AdapterClub adapterClub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().setElevation(0);
        getSupportActionBar().setTitle("Klasemen Sementara Liga 1");

        initViewModel();
        initRecyclerView();
    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.recyclerView.setLayoutManager(linearLayoutManager);
        adapterClub = new AdapterClub(this);
        binding.recyclerView.setAdapter(adapterClub);
        adapterClub.notifyDataSetChanged();
    }

    public void initViewModel(){
        clubViewModel = new ViewModelProvider(this).get(ClubViewModel.class);
        clubViewModel.getClubListObserver().observe(this, new Observer<ArrayList<Clubs>>() {
            @Override
            public void onChanged(ArrayList<Clubs> clubs) {
                if(clubs == null){
                    Toast.makeText(getApplication(), "Kosong", Toast.LENGTH_SHORT).show();
                } else {
                    for(Clubs club : clubs){
                        Log.d("nama", club.nama_klub);
                    }
                    adapterClub.setClubList(clubs);
                }
            }
        });
    }

}