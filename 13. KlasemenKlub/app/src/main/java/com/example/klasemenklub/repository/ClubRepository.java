package com.example.klasemenklub.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.klasemenklub.Clubs;
import com.example.klasemenklub.R;

import java.util.ArrayList;

public class ClubRepository {

    private MutableLiveData<ArrayList<Clubs>> clubList;

    public ClubRepository(){
        clubList = new MutableLiveData<>();
        insert();
    }

    public MutableLiveData<ArrayList<Clubs>> getAllClub() {
        return clubList;
    }

    public void insert(){
        ArrayList<Clubs> clubsDataList = new ArrayList<>();
        clubsDataList.add(new Clubs("Bali United", 31, 21, 6, 4, 52, 22, 30, 69, 1, R.drawable.bali));
        clubsDataList.add(new Clubs("Persib", 31, 20, 6, 5, 46, 20, 26, 66, 2, R.drawable.persib));
        clubsDataList.add(new Clubs("Persebaya", 31, 17, 8, 6, 51, 32, 19, 59, 3, R.drawable.persebaya));
        clubsDataList.add(new Clubs("Bhayangkara", 31, 17, 8, 6, 41, 26, 15, 59, 4, R.drawable.bhayangkara));
        clubsDataList.add(new Clubs("Arema", 31, 16, 10, 5, 38, 22, 16, 58, 5, R.drawable.arema));
        clubsDataList.add(new Clubs("Borneo", 31, 13, 9, 9, 39, 31, 8, 48, 5, R.drawable.borneo));
        clubList.postValue(clubsDataList);
    }

}
