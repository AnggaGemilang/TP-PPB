package com.example.tasklist.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tasklist.DetailTaskActivity;
import com.example.tasklist.R;
import com.example.tasklist.db.Tasks;
import com.example.tasklist.viewmodel.TasksViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.MyViewHolder> implements Filterable {

    private Context context;
    private List<Tasks> tasksList;
    private List<Tasks> tasksListData;
    private TaskListener taskListener;

    public TaskListAdapter(Context context, TaskListener taskListener) {
        this.context = context;
        this.taskListener = taskListener;
    }

    public void setTasksList(List<Tasks> tasksList) {
        this.tasksListData = tasksList;
        this.tasksList = tasksList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TaskListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.frame_task_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskListAdapter.MyViewHolder holder, int position) {
        holder.txtTitle.setText(this.tasksList.get(position).title);
        holder.txtCourse.setText(this.tasksList.get(position).course);
        holder.txtDate.setText(this.tasksList.get(position).deadline);

        if(tasksList.get(position).status.equals("On Progress")){
            holder.cardViewOption.getBackground().setTint(ContextCompat.getColor(context, R.color.yellow_light));
            holder.imgIcon.setImageResource(R.drawable.ic_baseline_access_time_24);
            holder.txtStatus.setText("On \n Progress");
        } else if (tasksList.get(position).status.equals("Not Attempt")){
            holder.cardViewOption.getBackground().setTint(ContextCompat.getColor(context, R.color.black_soft));
            holder.imgIcon.setImageResource(R.drawable.ic_baseline_accessible_24);
            holder.txtStatus.setText("Not \n Attempt");
        } else if (tasksList.get(position).status.equals("Done")){
            Date start = null;
            try {
                start = new SimpleDateFormat("HH:mm, dd-MM-yyyy", Locale.ENGLISH)
                        .parse(this.tasksList.get(position).deadline);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Date end = null;
            try {
                end = new SimpleDateFormat("HH:mm, dd-MM-yyyy", Locale.ENGLISH)
                        .parse(this.tasksList.get(position).doneTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (start.compareTo(end) > 0) {
                holder.cardViewOption.getBackground().setTint(ContextCompat.getColor(context, R.color.green_light));
                holder.imgIcon.setImageResource(R.drawable.ic_baseline_check_circle_outline_24);
                holder.txtStatus.setText("Task \n Submitted");
            } else {
                holder.cardViewOption.getBackground().setTint(ContextCompat.getColor(context, R.color.red_light));
                holder.imgIcon.setImageResource(R.drawable.ic_baseline_error_outline_24);
                holder.txtStatus.setText("Submitted \n Late");
            }
        }

        holder.cardViewDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                Intent intent = new Intent(context, DetailTaskActivity.class);
                bundle.putInt("uid", tasksList.get(position).uid);
                bundle.putString("title", tasksList.get(position).title);
                bundle.putString("desc", tasksList.get(position).description);
                bundle.putString("course", tasksList.get(position).course);
                bundle.putString("status", tasksList.get(position).status);
                bundle.putString("deadline", tasksList.get(position).deadline);
                bundle.putString("doneTime", tasksList.get(position).doneTime);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

        holder.cardViewOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taskListener.statusClick(v, tasksList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        if(tasksList == null || tasksList.size() == 0)
            return 0;
        else
            return tasksList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (!charString.isEmpty()) {
                    List<Tasks> filteredList = new ArrayList<>();
                    for (Tasks row : tasksList) {
                        if (row.title.toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }
                    tasksList = filteredList;
                } else {
                    tasksList = tasksListData;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = tasksList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                tasksList = (ArrayList<Tasks>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CardView cardViewDetail;
        CardView cardViewOption;
        TextView txtTitle;
        TextView txtCourse;
        TextView txtDate;
        ImageView imgIcon;
        TextView txtStatus;
        public MyViewHolder(View view) {
            super(view);
            cardViewDetail = view.findViewById(R.id.bagian_kiri);
            cardViewOption = view.findViewById(R.id.bagian_kanan);
            txtTitle = view.findViewById(R.id.txt_title);
            txtCourse = view.findViewById(R.id.txt_course);
            txtDate = view.findViewById(R.id.txt_date);
            imgIcon = view.findViewById(R.id.img_icon_status);
            txtStatus = view.findViewById(R.id.txt_status);
        }
    }

    public interface TaskListener {
        void statusClick(View view, Tasks tasks);
    }

}
