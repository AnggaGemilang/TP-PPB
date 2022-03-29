package com.example.tasklist.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tasklist.DetailTaskActivity;
import com.example.tasklist.R;
import com.example.tasklist.db.Tasks;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TaskListTodayAdapter extends RecyclerView.Adapter<TaskListTodayAdapter.MyViewHolder> {

    private Context context;
    private List<Tasks> tasksList;

    public TaskListTodayAdapter(Context context) {
        this.context = context;
    }

    public void setTasksList(List<Tasks> tasksList) {
        this.tasksList = tasksList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TaskListTodayAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.frame_task_today_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskListTodayAdapter.MyViewHolder holder, int position) {

        holder.txtTitle.setText(this.tasksList.get(position).title);
        holder.txtCourse.setText(this.tasksList.get(position).course);
        holder.txtDeadline.setText(this.tasksList.get(position).deadline);

        if(tasksList.get(position).status.equals("On Progress")){
            holder.linearLayout.setBackgroundResource(R.drawable.edge_border_bottom_yellow);
        } else if (tasksList.get(position).status.equals("Not Attempt")){
            holder.linearLayout.setBackgroundResource(R.drawable.edge_border_bottom_grey);
        } else {
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
                holder.linearLayout.setBackgroundResource(R.drawable.edge_border_bottom_green);
            } else {
                holder.linearLayout.setBackgroundResource(R.drawable.edge_border_bottom_red);            }
        }

        holder.cardView.setOnClickListener(new View.OnClickListener() {
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
    }

    @Override
    public int getItemCount() {
        if(tasksList == null || tasksList.size() == 0)
            return 0;
        else
            return tasksList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView txtTitle;
        TextView txtCourse;
        TextView txtDeadline;
        LinearLayout linearLayout;

        public MyViewHolder(View view) {
            super(view);
            cardView = view.findViewById(R.id.card_view_deadline_today);
            txtTitle = view.findViewById(R.id.txt_title_today1);
            txtCourse = view.findViewById(R.id.txt_course_today1);
            txtDeadline = view.findViewById(R.id.txt_deadline_today1);
            linearLayout = view.findViewById(R.id.linear_layout_bg);
        }
    }
}
