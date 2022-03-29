package com.example.tasklist.repository;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.tasklist.db.AppDatabase;
import com.example.tasklist.db.Tasks;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TasksRepository {

    private MutableLiveData<List<Tasks>> listOfTasks;
    private MutableLiveData<List<Tasks>> listOfTasks2;
    private MutableLiveData<List<String>> listOfString;
    private AppDatabase appDatabase;

    public TasksRepository(Context application) {
        appDatabase = AppDatabase.getDBinstance(application);
        listOfTasks = new MutableLiveData<>();
        listOfTasks2 = new MutableLiveData<>();
        listOfString = new MutableLiveData<>();
    }

    public MutableLiveData<List<Tasks>> getTaskListObserver() {
        return listOfTasks;
    }

    public MutableLiveData<List<Tasks>> getTaskListObserver2() { return listOfTasks2; }

    public MutableLiveData<List<String>> getStringListObserver() { return listOfString; }

    public String getTaskCount(){
        return appDatabase.taskListDao().getTaskCount().toString();
    }

    public String getNotAttemptCount(){ return appDatabase.taskListDao().getNotAttemptCount().toString(); }

    public String getOnProgressCount(){ return appDatabase.taskListDao().getOnProgressCount().toString(); }

    public String getTaskSubmittedCount() {
        Integer counter = 0;
        List<Tasks> taskList = appDatabase.taskListDao().getAllTaskList();
        for(int i = 0; i < taskList.size(); i++){
            String endDate = taskList.get(i).doneTime;
            if (!endDate.equals("null")){
                String startDate = taskList.get(i).deadline;
                Date start = null;
                try {
                    start = new SimpleDateFormat("HH:mm, dd-MM-yyyy", Locale.ENGLISH)
                            .parse(startDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Date end = null;
                try {
                    end = new SimpleDateFormat("HH:mm, dd-MM-yyyy", Locale.ENGLISH)
                            .parse(endDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (start.compareTo(end) > 0) {
                    counter++;
                }
            }
        }
        return counter.toString();
    }

    public String getTaskSubmittedLateCount() {
        Integer counter = 0;
        List<Tasks> taskList = appDatabase.taskListDao().getAllTaskList();
        for(int i = 0; i < taskList.size(); i++){
            String endDate = taskList.get(i).doneTime;
            if (!endDate.equals("null")){
                String startDate = taskList.get(i).deadline;
                Date start = null;
                try {
                    start = new SimpleDateFormat("HH:mm, dd-MM-yyyy", Locale.ENGLISH)
                            .parse(startDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Date end = null;
                try {
                    end = new SimpleDateFormat("HH:mm, dd-MM-yyyy", Locale.ENGLISH)
                            .parse(endDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (start.compareTo(end) <= 0) {
                    counter++;
                }
            }
        }
        return counter.toString();
    }

    public void getAllTaskList() {
        List<Tasks> taskList = appDatabase.taskListDao().getAllTaskList();
        if(taskList.size() > 0) {
            listOfTasks.postValue(taskList);
        } else {
            listOfTasks.postValue(null);
        }
    }

    public void getAllTaskListByCourse(String course, String status) {
        List<Tasks> taskList = appDatabase.taskListDao().getTaskByCourse(course);
        List<Tasks> taskListNew = new ArrayList<>();
        for(int i = 0; i < taskList.size(); i++) {
            if (status.equals("belum") && (taskList.get(i).status.equals("Not Attempt") || taskList.get(i).status.equals("On Progress"))) {
                taskListNew.add(taskList.get(i));
            } else if (status.equals("sudah") && taskList.get(i).status.equals("Done")) {
                taskListNew.add(taskList.get(i));
            }
        }
        if(status.equals("belum")) {
            if (taskListNew.size() > 0) {
                listOfTasks.postValue(taskListNew);
            } else {
                listOfTasks.postValue(null);
            }
        } else {
            if (taskListNew.size() > 0) {
                listOfTasks2.postValue(taskListNew);
            } else {
                listOfTasks2.postValue(null);
            }
        }
    }

    public void getAllTaskListByDeadline(String deadline, String status) {
        List<Tasks> taskList = appDatabase.taskListDao().getTaskByDeadline(deadline);
        List<Tasks> taskListNew = new ArrayList<>();
        for(int i = 0; i < taskList.size(); i++) {
            if (status.equals("belum") && (taskList.get(i).status.equals("Not Attempt") || taskList.get(i).status.equals("On Progress"))) {
                taskListNew.add(taskList.get(i));
            } else if (status.equals("sudah") && taskList.get(i).status.equals("Done")) {
                taskListNew.add(taskList.get(i));
            }
        }
        if(status.equals("belum")) {
            if (taskListNew.size() > 0) {
                listOfTasks.postValue(taskListNew);
            } else {
                listOfTasks.postValue(null);
            }
        } else {
            if (taskListNew.size() > 0) {
                listOfTasks2.postValue(taskListNew);
            } else {
                listOfTasks2.postValue(null);
            }
        }
    }

    public void insertTask(String title, String desc, String course, String waktu, String tanggal) {
        Tasks task = new Tasks();
        task.title = title;
        task.description = desc;
        task.course = course;
        task.deadline = waktu + ", " + tanggal;
        task.doneTime = "null";
        task.status = "Not Attempt";
        appDatabase.taskListDao().insertTask(task);
        getAllTaskList();
    }

    public void updateStatusTask(int idTask, String doneTime, String status){
        appDatabase.taskListDao().updateStatusTask(idTask, status, doneTime);
        getAllTaskList();
    }

    public void updateTask(Tasks task) {
        appDatabase.taskListDao().updateTask(task.uid, task.title, task.description, task.course, task.deadline, task.status, task.doneTime);
        getAllTaskList();
    }

    public void deleteTask(int uid) {
        appDatabase.taskListDao().deleteTask(uid);
        getAllTaskList();
    }

    public void getTaskDeadlineToday(){
        List<Tasks> taskList = appDatabase.taskListDao().getTaskDeadlineToday();
        if(taskList.size() > 0) {
            Log.d("dadang gordes", taskList.get(0).title);
            listOfTasks.postValue(taskList);
        } else {
            listOfTasks.postValue(null);
        }
    }

    public void getTaskFilter(){
        List<String> stringList = appDatabase.taskListDao().getTaskFilterDate();
        if(stringList.size() > 0) {
            listOfString.postValue(stringList);
        } else {
            listOfString.postValue(null);
        }
    }

}
