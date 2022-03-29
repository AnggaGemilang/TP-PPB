package com.example.tasklist;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.tasklist.adapter.TaskListAdapter;
import com.example.tasklist.databinding.FragmentCourseFilterBinding;
import com.example.tasklist.db.Tasks;
import com.example.tasklist.viewmodel.TasksViewModel;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CourseFilterFragment extends Fragment implements TaskListAdapter.TaskListener {

    private FragmentCourseFilterBinding binding = null;
    final private String deadline;
    private TasksViewModel viewModel;
    private TaskListAdapter taskListAdapter;
    private TaskListAdapter taskListAdapter2;

    public CourseFilterFragment(String deadline){
        this.deadline = deadline;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViewModel();
        initRecyclerView();
        viewModel.getAllTaskListByDeadline(this.deadline, "sudah");
        viewModel.getAllTaskListByDeadline(this.deadline, "belum");

        binding.edittextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                taskListAdapter.getFilter().filter(charSequence);
                taskListAdapter2.getFilter().filter(charSequence);
                if(taskListAdapter.getItemCount() == 0){
                    binding.noResultBelumFilter.setVisibility(View.VISIBLE);
                    binding.recyclerViewBelumFilter.setVisibility(View.GONE);
                } else {
                    binding.noResultBelumFilter.setVisibility(View.GONE);
                    binding.recyclerViewBelumFilter.setVisibility(View.VISIBLE);
                }

                binding.titleBelumFilter.setText("Haven't Done Yet - " + taskListAdapter.getItemCount());

                if(taskListAdapter2.getItemCount() == 0){
                    binding.noResultSudahFilter.setVisibility(View.VISIBLE);
                    binding.recyclerViewSudahFilter.setVisibility(View.GONE);
                } else {
                    binding.noResultSudahFilter.setVisibility(View.GONE);
                    binding.recyclerViewSudahFilter.setVisibility(View.VISIBLE);
                }

                binding.titleSudahFilter.setText("Done - " + taskListAdapter2.getItemCount());

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCourseFilterBinding.inflate(getLayoutInflater(), container, false);
        return binding.getRoot();
    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        binding.recyclerViewSudahFilter.setLayoutManager(linearLayoutManager2);
        taskListAdapter2 = new TaskListAdapter(getActivity(), this);
        binding.recyclerViewSudahFilter.setAdapter(taskListAdapter2);
        taskListAdapter2.notifyDataSetChanged();

        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        binding.recyclerViewBelumFilter.setLayoutManager(linearLayoutManager1);
        taskListAdapter = new TaskListAdapter(getActivity(), this);
        binding.recyclerViewBelumFilter.setAdapter(taskListAdapter);
        taskListAdapter.notifyDataSetChanged();
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(TasksViewModel.class);
        viewModel.getTaskListObserver2().observe(getActivity(), new Observer<List<Tasks>>() {
            @Override
            public void onChanged(List<Tasks> tasks) {
                if(tasks == null) {
                    binding.noResultSudahFilter.setVisibility(View.VISIBLE);
                    binding.recyclerViewSudahFilter.setVisibility(View.GONE);
                } else {
                    binding.titleSudahFilter.setText("Done - " + tasks.size());
                    taskListAdapter2.setTasksList(tasks);
                    binding.noResultSudahFilter.setVisibility(View.GONE);
                    binding.recyclerViewSudahFilter.setVisibility(View.VISIBLE);
                }
            }
        });

        viewModel = new ViewModelProvider(this).get(TasksViewModel.class);
        viewModel.getTaskListObserver().observe(getActivity(), new Observer<List<Tasks>>() {
            @Override
            public void onChanged(List<Tasks> tasks) {
                if(tasks == null) {
                    binding.noResultBelumFilter.setVisibility(View.VISIBLE);
                    binding.recyclerViewBelumFilter.setVisibility(View.GONE);
                } else {
                    binding.titleBelumFilter.setText("Haven't Done Yet - " + tasks.size());
                    taskListAdapter.setTasksList(tasks);
                    binding.noResultBelumFilter.setVisibility(View.GONE);
                    binding.recyclerViewBelumFilter.setVisibility(View.VISIBLE);
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
                        viewModel.getAllTaskListByDeadline(deadline, "sudah");
                        viewModel.getAllTaskListByDeadline(deadline, "belum");
                        break;
                    case R.id.act_on_progress:
                        Toast.makeText(getContext(), "Status Updated Successfully", Toast.LENGTH_SHORT).show();
                        viewModel.updateStatusTask(task.uid, "null", "On Progress");
                        initViewModel();
                        viewModel.getAllTaskListByDeadline(deadline, "sudah");
                        viewModel.getAllTaskListByDeadline(deadline, "belum");
                        break;
                    case R.id.act_done:
                        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm, dd-MM-yyyy");
                        LocalDateTime localDateTime = LocalDateTime.now();
                        viewModel.updateStatusTask(task.uid, dtf.format(localDateTime), "Done");
                        Toast.makeText(getContext(), "Status Updated Successfully", Toast.LENGTH_SHORT).show();
                        initViewModel();
                        viewModel.getAllTaskListByDeadline(deadline, "sudah");
                        viewModel.getAllTaskListByDeadline(deadline, "belum");
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