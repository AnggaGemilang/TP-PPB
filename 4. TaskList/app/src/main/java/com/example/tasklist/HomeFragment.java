package com.example.tasklist;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.tasklist.adapter.TaskDateFilterAdapter;
import com.example.tasklist.adapter.TaskListAdapter;
import com.example.tasklist.adapter.TaskListTodayAdapter;
import com.example.tasklist.databinding.FragmentHomeBinding;
import com.example.tasklist.db.Tasks;
import com.example.tasklist.viewmodel.TasksViewModel;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private TasksViewModel viewModel;
    private TaskListTodayAdapter taskListAdapter;
    private TaskDateFilterAdapter taskDateFilterAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(getLayoutInflater(), container, false);
        return binding.getRoot();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd MMM");
        LocalDate localDate = LocalDate.now();
        binding.txtTanggalHome.setText(dtf.format(localDate));

        initViewModel();
        initRecyclerView();
        viewModel.getTaskDeadlineToday();
        viewModel.getTaskFilter();
    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        binding.recyclerViewToday.setLayoutManager(linearLayoutManager2);
        taskListAdapter = new TaskListTodayAdapter(getActivity());
        binding.recyclerViewToday.setAdapter(taskListAdapter);
        taskListAdapter.notifyDataSetChanged();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        binding.recyclerViewFilter.setLayoutManager(linearLayoutManager);
        taskDateFilterAdapter = new TaskDateFilterAdapter(getActivity());
        binding.recyclerViewFilter.setAdapter(taskDateFilterAdapter);
        taskDateFilterAdapter.notifyDataSetChanged();
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(TasksViewModel.class);
        viewModel.getTaskListObserver().observe(getActivity(), new Observer<List<Tasks>>() {
            @Override
            public void onChanged(List<Tasks> tasks) {
                if (tasks == null) {
                    binding.noResult.setVisibility(View.VISIBLE);
                    binding.recyclerViewToday.setVisibility(View.GONE);
                } else {
                    taskListAdapter.setTasksList(tasks);
                    binding.noResult.setVisibility(View.GONE);
                    binding.recyclerViewToday.setVisibility(View.VISIBLE);
                }
            }
        });

        viewModel = new ViewModelProvider(this).get(TasksViewModel.class);
        viewModel.getStringListObserver().observe(getActivity(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> stringList) {
                if (stringList == null) {
                    binding.noResultFilter.setVisibility(View.VISIBLE);
                    binding.recyclerViewFilter.setVisibility(View.GONE);
                } else {
                    taskDateFilterAdapter.setTasksList(stringList);
                    binding.noResultFilter.setVisibility(View.GONE);
                    binding.recyclerViewFilter.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        binding.getRoot().requestFocus();
        initViewModel();
    }

}