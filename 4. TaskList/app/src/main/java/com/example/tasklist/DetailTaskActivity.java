package com.example.tasklist;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.tasklist.databinding.ActivityDetailTaskBinding;
import com.example.tasklist.db.Tasks;
import com.example.tasklist.viewmodel.TasksViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class DetailTaskActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private ActivityDetailTaskBinding binding;
    private TasksViewModel viewModel;

    private String title, course, deadline, desc, status, doneTime;
    private Integer uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailTaskBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(TasksViewModel.class);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setTitle("Detail Task");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = getIntent().getExtras();
        uid = bundle.getInt("uid");
        title = bundle.getString("title");
        course = bundle.getString("course");
        deadline = bundle.getString("deadline");
        desc = bundle.getString("desc");
        status = bundle.getString("status");
        doneTime = bundle.getString("doneTime");

        binding.txtTitle.setText(title);
        binding.txtCourse.setText(course);
        binding.txtDeadline.setText(deadline);
        binding.txtDesc.setText(desc);
        binding.txtStatus.setText(status);

        if(status.equals("Not Attempt")){
            notAttempt();
            binding.spinner.setSelection(1, true);
        } else if(status.equals("On Progress")){
            onProgress();
            binding.spinner.setSelection(2, true);
        } else if(status.equals("Done")){
            Date end = null;
            try {
                end = new SimpleDateFormat("HH:mm, dd-MM-yyyy", Locale.ENGLISH)
                        .parse(doneTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if(isSubmittedLate(end)){
                submmitedLate();
            } else {
                taskSubmitted();
            }
        }
        binding.spinner.setOnItemSelectedListener(this);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.item_menu_actionbar_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case (R.id.act_edit):
                Bundle bundle = new Bundle();
                Intent intent = new Intent(this, AddDataActivity.class);
                bundle.putInt("uid", uid);
                bundle.putString("id", "edit");
                bundle.putString("title", title);
                bundle.putString("desc", desc);
                bundle.putString("course", course);
                bundle.putString("status", status);
                bundle.putString("deadline", deadline);
                bundle.putString("doneTime", doneTime);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case (R.id.act_delete):
                Toast.makeText(this, "Data Removed Successfully", Toast.LENGTH_SHORT).show();
                viewModel = new ViewModelProvider(this).get(TasksViewModel.class);
                viewModel.deleteTask(uid);
                startActivity(new Intent(this, MainActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if(binding.spinner.getSelectedItem().toString().equals("Done")){
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm, dd-MM-yyyy");
            LocalDateTime localDateTime = LocalDateTime.now();
            viewModel.updateStatusTask(uid, dtf.format(localDateTime), binding.spinner.getSelectedItem().toString());
            Date end = null;
            try {
                end = new SimpleDateFormat("HH:mm, dd-MM-yyyy", Locale.ENGLISH)
                        .parse(dtf.format(localDateTime));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if(isSubmittedLate(end)){
                submmitedLate();
            } else {
                taskSubmitted();
            }
        } else {
            viewModel.updateStatusTask(uid, "null", binding.spinner.getSelectedItem().toString());
            if(binding.spinner.getSelectedItem().toString().equals("Not Attempt")){
                notAttempt();
            } else if(binding.spinner.getSelectedItem().toString().equals("On Progress")){
                onProgress();
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public Boolean isSubmittedLate(Date doneTime){
        binding.spinner.setSelection(3, true);
        Date start = null;
        try {
            start = new SimpleDateFormat("HH:mm, dd-MM-yyyy", Locale.ENGLISH)
                    .parse(deadline);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(start.compareTo(doneTime) > 0){
            return false;
        } else {
            return true;
        }
    }

    public void taskSubmitted(){
        binding.cardViewStatus.getBackground().setTint(getColor(R.color.green_light));
        binding.txtStatus.setTextColor(getColor(R.color.white));
        binding.txtStatus.setText("Task Submitted");
    }

    public void submmitedLate(){
        binding.cardViewStatus.getBackground().setTint(getColor(R.color.red_light));
        binding.txtStatus.setTextColor(getColor(R.color.white));
        binding.txtStatus.setText("Submitted Late");
    }

    public void onProgress(){
        binding.txtStatus.setText("On Progress");
        binding.cardViewStatus.getBackground().setTint(getColor(R.color.yellow_light));
        binding.txtStatus.setTextColor(getColor(R.color.black_soft));
    }

    public void notAttempt(){
        binding.txtStatus.setText("Not Attempt");
        binding.cardViewStatus.getBackground().setTint(getColor(R.color.black_soft));
        binding.txtStatus.setTextColor(getColor(R.color.white));
    }

}