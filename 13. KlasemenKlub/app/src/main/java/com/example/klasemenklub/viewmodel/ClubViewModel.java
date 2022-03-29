package com.example.klasemenklub.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.klasemenklub.Clubs;
import com.example.klasemenklub.R;
import com.example.klasemenklub.repository.ClubRepository;

import java.util.ArrayList;

public class ClubViewModel extends AndroidViewModel {

    private ClubRepository clubRepository;

    private MutableLiveData<ArrayList<Clubs>> clubList;

    public MutableLiveData<ArrayList<Clubs>> getClubListObserver() {
        return clubList;
    }

    public ClubViewModel(Application application) {
        super(application);
        clubRepository = new ClubRepository();
        clubList = new MutableLiveData<>();
        clubList = clubRepository.getAllClub();
    }

    public void insert() { clubRepository.insert(); }

}
