package com.example.tasklist.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TaskListDao {

    @Query("Select * from Tasks")
    List<Tasks> getAllTaskList();

    @Insert
    void insertTask(Tasks...tasks);

    @Query("Update Tasks SET title = :title, description = :description, course = :course, deadline = :deadline, status = :status, doneTime = :doneTime where uid = :idTask")
    void updateTask(int idTask, String title, String description, String course, String deadline, String status, String doneTime);

    @Query("Update Tasks SET status = :status, doneTime = :doneTime where uid = :idTask")
    void updateStatusTask(int idTask, String status, String doneTime);

    @Query("Delete from Tasks where uid = :idTask")
    void deleteTask(int idTask);

    @Query("Select * from Tasks where course = :crName")
    List<Tasks> getTaskByCourse(String crName);

    @Query("Select * from Tasks where SUBSTR(deadline, 8, 16) = :deadline")
    List<Tasks> getTaskByDeadline(String deadline);

    @Query("Select * from Tasks where SUBSTR(deadline, 8, 16) = strftime('%d-%m-%Y','now','localtime')")
    List<Tasks> getTaskDeadlineToday();

    @Query("Select SUBSTR(deadline, 8, 16) from Tasks where SUBSTR(deadline, 8, 16) >= strftime('%d-%m-%Y','now','localtime') group by SUBSTR(deadline, 8, 16)")
    List<String> getTaskFilterDate();

    @Query("SELECT COUNT(title) FROM Tasks")
    Integer getTaskCount();

    @Query("SELECT COUNT(title) FROM Tasks WHERE status = 'On Progress'")
    Integer getOnProgressCount();

    @Query("SELECT COUNT(title) FROM Tasks WHERE status = 'Not Attempt'")
    Integer getNotAttemptCount();

}
