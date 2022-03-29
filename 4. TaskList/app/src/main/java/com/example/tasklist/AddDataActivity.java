package com.example.tasklist;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.tasklist.viewmodel.TasksViewModel;
import com.example.tasklist.databinding.ActivityAddDataBinding;
import com.example.tasklist.db.Tasks;

import java.util.Calendar;

public class AddDataActivity extends AppCompatActivity implements View.OnClickListener {

    private TasksViewModel viewModel;
    private ActivityAddDataBinding binding;
    private int mYear, mMonth, mDay, mHour, mMinute, uid;
    private String id, title, course, deadline, desc, status, doneTime;
    private Tasks editTask;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        binding = ActivityAddDataBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().setElevation(0);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
        id = getIntent().getStringExtra("id");

        if(id.equals("tambah")){
            getSupportActionBar().setTitle("Add Task");
        } else {
            Bundle bundle = getIntent().getExtras();
            uid = bundle.getInt("uid");
            title = bundle.getString("title");
            course = bundle.getString("course");
            deadline = bundle.getString("deadline");
            desc = bundle.getString("desc");
            status = bundle.getString("status");
            doneTime = bundle.getString("doneTime");

            getSupportActionBar().setTitle("Edit " + title);

            binding.edtTitle.setText(title);
            binding.edtDescription.setText(desc);
            binding.spinner.setSelection(((ArrayAdapter<String>) binding.spinner.getAdapter()).getPosition(course));

            String waktu = deadline.split(",")[0];
            String tanggal = deadline.split(",")[1];
            binding.txtWaktu.setText(waktu);
            binding.txtTanggal.setText(tanggal);
        }

        binding.txtTanggal.setOnClickListener(this);
        binding.txtWaktu.setOnClickListener(this);
        binding.btnSubmitData.setOnClickListener(this);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_tanggal:
                Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                int month = monthOfYear+1;
                                String fm = ""+ month;
                                String fd = ""+ dayOfMonth;
                                if (month<10){
                                    fm ="0"+month;
                                }
                                if (dayOfMonth<10){
                                    fd="0"+dayOfMonth;
                                }
                                binding.txtTanggal.setText(fd + "-" + fm + "-" + year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
                break;
            case R.id.txt_waktu:
                c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                String fh = ""+ hourOfDay;
                                String fs = ""+ minute;
                                if(hourOfDay < 10){
                                    fh = "0" + hourOfDay;
                                }
                                if (minute < 10){
                                    fs = "0" + minute;
                                }
                                binding.txtWaktu.setText(fh + ":" + fs);
                            }
                        }, mHour, mMinute, true);
                timePickerDialog.show();
                break;
            case R.id.btn_submit_data:
                String title = binding.edtTitle.getText().toString();
                String desc = binding.edtDescription.getText().toString();
                String course = binding.spinner.getSelectedItem().toString();
                String waktu = binding.txtWaktu.getText().toString();
                String tanggal = binding.txtTanggal.getText().toString();
                if (!title.equals("") && !desc.equals("") && !course.equals("") && !waktu.equals("00:00") && !tanggal.equals("12-12-1999")){
                    viewModel = new ViewModelProvider(this).get(TasksViewModel.class);
                    if(id.equals("tambah")){
                        viewModel.insertTask(title, desc, course, waktu, tanggal);
                        binding.edtTitle.setText("");
                        binding.edtDescription.setText("");
                        binding.spinner.setSelection(0);
                        binding.txtWaktu.setText("00:00");
                        binding.txtTanggal.setText("12-12-1999");
                        Toast.makeText(this, "Data Added Successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        editTask = new Tasks();
                        editTask.uid = this.uid;
                        editTask.status = this.status;
                        editTask.doneTime = this.doneTime;
                        editTask.title = title;
                        editTask.deadline = waktu + "," + tanggal;
                        editTask.description = desc;
                        editTask.course = course;
                        viewModel.updateTask(editTask);
                        startActivity(new Intent(this, MainActivity.class));
                        Toast.makeText(this, "Data Updated Successfully", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Fill data completely", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}