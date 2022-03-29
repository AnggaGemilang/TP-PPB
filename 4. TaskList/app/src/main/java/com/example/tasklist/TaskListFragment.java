package com.example.tasklist;

import android.hardware.lights.LightsManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tasklist.databinding.FragmentTaskListBinding;
import com.example.tasklist.viewmodel.TasksViewModel;
import com.google.android.material.tabs.TabLayout;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TaskListFragment extends Fragment {

    private FragmentTaskListBinding binding;
    private TasksViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTaskListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("APSI P"));
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("APSI T"));
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("PKN"));
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("PPB"));
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("PPL P"));
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("PPL T"));
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("PPL 4"));
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("STATP"));
        binding.tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        replaceFragment(new CoursesFragment("Analisa dan Peranc. Sistem Informasi Praktek"));
        initViewModel();

        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:
                        replaceFragment(new CoursesFragment("Analisa dan Peranc. Sistem Informasi Praktek"));
                        break;
                    case 1:
                        replaceFragment(new CoursesFragment("Analisa dan Peranc. Sistem Informasi Teori"));
                        break;
                    case 2:
                        replaceFragment(new CoursesFragment("Pancasila"));
                        break;
                    case 3:
                        replaceFragment(new CoursesFragment("Pemrograman Perangkat Bergerak"));
                        break;
                    case 4:
                        replaceFragment(new CoursesFragment("Pengembangan Perangkat Lunak 1 Praktek"));
                        break;
                    case 5:
                        replaceFragment(new CoursesFragment("Pengembangan Perangkat Lunak 1 Teori"));
                        break;
                    case 6:
                        replaceFragment(new CoursesFragment("Proyek Perangkat Lunak 4"));
                        break;
                    case 7:
                        replaceFragment(new CoursesFragment("Statistika dan Probabilitas"));
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(TasksViewModel.class);
        binding.txtTaskCount.setText(viewModel.getTaskCount());
        binding.txtNotattemptCount.setText(viewModel.getNotAttemptCount());
        binding.txtOnProgressCount.setText(viewModel.getOnProgressCount());
        binding.txtTaskSubmittedCount.setText(viewModel.getTaskSubmittedCount());
        binding.txtSubmittedLateCount.setText(viewModel.getTaskSubmittedLateCount());
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fm = requireActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frame_container, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();
    }

    @Override
    public void onResume() {
        super.onResume();
        binding.getRoot().requestFocus();
        initViewModel();
    }
}