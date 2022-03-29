package com.example.tasklist;

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

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.tasklist.adapter.TaskListAdapter;
import com.example.tasklist.databinding.FragmentCoursesBinding;
import com.example.tasklist.db.Tasks;
import com.example.tasklist.viewmodel.TasksViewModel;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CoursesFragment extends Fragment implements TaskListAdapter.TaskListener {

    private FragmentCoursesBinding binding = null;

    private final String course;
    private TasksViewModel viewModel;
    private TaskListAdapter taskListAdapter;
    private TaskListAdapter taskListAdapter2;

    public CoursesFragment(String course){
        this.course = course;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCoursesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViewModel();
        initRecyclerView();
        viewModel.getAllTaskListByCourse(this.course, "sudah");
        viewModel.getAllTaskListByCourse(this.course, "belum");
    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        binding.recyclerViewSudah.setLayoutManager(linearLayoutManager2);
        taskListAdapter2 = new TaskListAdapter(getActivity(), this);
        binding.recyclerViewSudah.setAdapter(taskListAdapter2);
        taskListAdapter2.notifyDataSetChanged();

        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        binding.recyclerViewBelum.setLayoutManager(linearLayoutManager1);
        taskListAdapter = new TaskListAdapter(getActivity(), this);
        binding.recyclerViewBelum.setAdapter(taskListAdapter);
        taskListAdapter.notifyDataSetChanged();
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(TasksViewModel.class);
        viewModel.getTaskListObserver2().observe(getActivity(), new Observer<List<Tasks>>() {
            @Override
            public void onChanged(List<Tasks> tasks) {
                if(tasks == null) {
                    binding.noResultSudah.setVisibility(View.VISIBLE);
                    binding.recyclerViewSudah.setVisibility(View.GONE);
                } else {
                    Log.d("sesudah", tasks.get(0).title);
                    binding.titleSudah.setText("Done - " + tasks.size());
                    taskListAdapter2.setTasksList(tasks);
                    binding.noResultSudah.setVisibility(View.GONE);
                    binding.recyclerViewSudah.setVisibility(View.VISIBLE);
                }
            }
        });

        viewModel = new ViewModelProvider(this).get(TasksViewModel.class);
        viewModel.getTaskListObserver().observe(getActivity(), new Observer<List<Tasks>>() {
            @Override
            public void onChanged(List<Tasks> tasks) {
                if(tasks == null) {
                    binding.noResultBelum.setVisibility(View.VISIBLE);
                    binding.recyclerViewBelum.setVisibility(View.GONE);
                } else {
                    Log.d("sebelum", tasks.get(0).title);
                    binding.titleBelum.setText("Haven't Done Yet - " + tasks.size());
                    taskListAdapter.setTasksList(tasks);
                    binding.noResultBelum.setVisibility(View.GONE);
                    binding.recyclerViewBelum.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public void statusClick(View view, Tasks task) {
        PopupMenu popupMenu = new PopupMenu(getContext(), view);
        popupMenu.getMenuInflater().inflate(R.menu.item_popup_action, popupMenu.getMenu());
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.act_not_attempt:
                        Toast.makeText(getContext(), "Status Updated Successfully", Toast.LENGTH_SHORT).show();
                        viewModel.updateStatusTask(task.uid, "null", "Not Attempt");
                        initViewModel();
                        viewModel.getAllTaskListByCourse(course, "sudah");
                        viewModel.getAllTaskListByCourse(course, "belum");
                        break;
                    case R.id.act_on_progress:
                        Toast.makeText(getContext(), "Status Updated Successfully", Toast.LENGTH_SHORT).show();
                        viewModel.updateStatusTask(task.uid, "null", "On Progress");
                        initViewModel();
                        viewModel.getAllTaskListByCourse(course, "sudah");
                        viewModel.getAllTaskListByCourse(course, "belum");
                        break;
                    case R.id.act_done:
                        Toast.makeText(getContext(), "Status Updated Successfully", Toast.LENGTH_SHORT).show();
                        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm, dd-MM-yyyy");
                        LocalDateTime localDateTime = LocalDateTime.now();
                        viewModel.updateStatusTask(task.uid, dtf.format(localDateTime), "Done");
                        initViewModel();
                        viewModel.getAllTaskListByCourse(course, "sudah");
                        viewModel.getAllTaskListByCourse(course, "belum");
                        break;
                }
                return false;
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