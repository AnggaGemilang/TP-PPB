package com.example.tasklist.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tasklist.CourseFilterFragment;
import com.example.tasklist.CoursesFragment;
import com.example.tasklist.DetailTaskActivity;
import com.example.tasklist.R;
import com.example.tasklist.db.Tasks;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TaskDateFilterAdapter extends RecyclerView.Adapter<TaskDateFilterAdapter.MyViewHolder> {

    private Context context;
    private List<String> stringList;
    List<CardView> cardViewList = new ArrayList<>();
    List<TextView> textViewList = new ArrayList<>();

    public TaskDateFilterAdapter(Context context) {
        this.context = context;
    }

    public void setTasksList(List<String> stringList) {
        this.stringList = stringList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TaskDateFilterAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.frame_filter_date_item, parent, false);
        return new MyViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("SimpleDateFormat")
    @Override
    public void onBindViewHolder(@NonNull TaskDateFilterAdapter.MyViewHolder holder, int position) {
        SimpleDateFormat formatter1 = new SimpleDateFormat("dd-MM-yyyy");
        Date dateValue = null;
        try {
            dateValue = formatter1.parse(this.stringList.get(position));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat output = new SimpleDateFormat("dd\nMMM");
        holder.txtDateFilter.setText(output.format(dateValue));

        if (!cardViewList.contains(holder.cardViewWrapper)) {
            cardViewList.add(holder.cardViewWrapper);
        }

        if (!textViewList.contains(holder.txtDateFilter)) {
            textViewList.add(holder.txtDateFilter);
        }

        cardViewList.get(0).getBackground().setTint(ContextCompat.getColor(context, R.color.mainColor));
        textViewList.get(0).setTextColor(ContextCompat.getColor(context, R.color.white));

        replaceFragment(new CourseFilterFragment(stringList.get(0)));

        holder.cardViewWrapper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(CardView cardView : cardViewList){
                    cardView.getBackground().setTint(ContextCompat.getColor(context, R.color.grey));
                    for(TextView textView : textViewList){
                        textView.setTextColor(ContextCompat.getColor(context, R.color.black_soft));
                    }
                }
                holder.txtDateFilter.setTextColor(ContextCompat.getColor(context, R.color.white));
                holder.cardViewWrapper.getBackground().setTint(ContextCompat.getColor(context, R.color.mainColor));
                replaceFragment(new CourseFilterFragment(stringList.get(position)));
            }
        });
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fm = (((FragmentActivity) context).getSupportFragmentManager());
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frame_layout_home, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();
    }

    @Override
    public int getItemCount() {
        if(stringList == null || stringList.size() == 0)
            return 0;
        else
            return stringList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CardView cardViewWrapper;
        TextView txtDateFilter;

        public MyViewHolder(View view) {
            super(view);
            cardViewWrapper = view.findViewById(R.id.card_view_filter);
            txtDateFilter = view.findViewById(R.id.txt_tanggal_filter);
        }
    }
}
