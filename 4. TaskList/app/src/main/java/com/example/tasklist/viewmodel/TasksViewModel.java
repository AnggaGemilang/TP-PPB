package com.example.tasklist.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.tasklist.db.AppDatabase;
import com.example.tasklist.db.Tasks;
import com.example.tasklist.repository.TasksRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TasksViewModel extends AndroidViewModel {

    private TasksRepository tasksRepository;
    private MutableLiveData<List<Tasks>> listOfTasks;
    private MutableLiveData<List<Tasks>> listOfTasks2;
    private MutableLiveData<List<String>> listOfString;

    public MutableLiveData<List<Tasks>> getTaskListObserver() {
        return listOfTasks;
    }

    public MutableLiveData<List<Tasks>> getTaskListObserver2() { return listOfTasks2; }

    public MutableLiveData<List<String>> getStringListObserver() { return listOfString; }

    public TasksViewModel(@NonNull Application application) {
        super(application);
        tasksRepository = new TasksRepository(getApplication().getApplicationContext());
        listOfTasks = new MutableLiveData<>();
        listOfTasks2 = new MutableLiveData<>();
        listOfString = new MutableLiveData<>();
        listOfTasks = tasksRepository.getTaskListObserver();
        listOfTasks2 = tasksRepository.getTaskListObserver2();
        listOfString = tasksRepository.getStringListObserver();
    }

    public String getTaskCount(){
        return tasksRepository.getTaskCount();
    }

    public String getNotAttemptCount(){ return tasksRepository.getNotAttemptCount(); }

    public String getOnProgressCount(){ return tasksRepository.getOnProgressCount(); }

    public String getTaskSubmittedCount() {
        return tasksRepository.getTaskSubmittedCount();
    }

    public String getTaskSubmittedLateCount() {
        return tasksRepository.getTaskSubmittedLateCount();
    }

    public void getAllTaskList() {
        tasksRepository.getAllTaskList();
    }

    public void getAllTaskListByCourse(String course, String status) {
        tasksRepository.getAllTaskListByCourse(course, status);
    }

    public void getAllTaskListByDeadline(String deadline, String status) {
        tasksRepository.getAllTaskListByDeadline(deadline, status);
    }

    public void insertTask(String title, String desc, String course, String waktu, String tanggal) {
        tasksRepository.insertTask(title, desc, course, waktu, tanggal);
        getAllTaskList();
    }

    public void updateStatusTask(int idTask, String doneTime, String status){
        tasksRepository.updateStatusTask(idTask, doneTime, status);
        getAllTaskList();
    }

    public void updateTask(Tasks task) {
        tasksRepository.updateTask(task);
        getAllTaskList();
    }

    public void deleteTask(int uid) {
        tasksRepository.deleteTask(uid);
        getAllTaskList();
    }

    public void getTaskDeadlineToday(){
        tasksRepository.getTaskDeadlineToday();
    }

    public void getTaskFilter(){
        tasksRepository.getTaskFilter();
    }

}
