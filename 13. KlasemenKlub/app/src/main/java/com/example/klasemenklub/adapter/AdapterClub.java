package com.example.klasemenklub.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.klasemenklub.Clubs;
import com.example.klasemenklub.DetailClub;
import com.example.klasemenklub.R;

import java.util.ArrayList;

public class AdapterClub extends RecyclerView.Adapter<AdapterClub.MyViewHolder> {

    ArrayList<Clubs> clubs = new ArrayList<>();
    Context context;

    public AdapterClub(Context context){
        this.context = context;
    }

    public void setClubList(ArrayList<Clubs> clubs) {
        this.clubs = clubs;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.club_item, parent, false);
        return new MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.txtNamaKlub.setText(clubs.get(position).nama_klub);
        holder.txtNoUrut.setText(Integer.toString(position+1));
        holder.txtPoin.setText(Integer.toString(clubs.get(position).poin));
        holder.imageView.setImageResource(clubs.get(position).logo);

        holder.tableRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                Intent intent = new Intent(context, DetailClub.class);
                bundle.putInt("logo", clubs.get(position).logo);
                bundle.putString("nama_klub", clubs.get(position).nama_klub);
                bundle.putString("total_main", Integer.toString(clubs.get(position).total_main));
                bundle.putString("menang", Integer.toString(clubs.get(position).menang));
                bundle.putString("seri", Integer.toString(clubs.get(position).seri));
                bundle.putString("kalah", Integer.toString(clubs.get(position).kalah));
                bundle.putString("gol_masuk", Integer.toString(clubs.get(position).gol_masuk));
                bundle.putString("gol_kemasukan", Integer.toString(clubs.get(position).gol_kemasukan));
                bundle.putString("selisih_gol", Integer.toString(clubs.get(position).selisih_gol));
                bundle.putString("poin", Integer.toString(clubs.get(position).poin));
                bundle.putString("peringkat", Integer.toString(clubs.get(position).peringkat));
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return clubs.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TableLayout tableRow;
        TextView txtNoUrut;
        TextView txtNamaKlub;
        TextView txtPoin;
        ImageView imageView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tableRow = itemView.findViewById(R.id.table_row);
            txtNamaKlub = itemView.findViewById(R.id.txt_nama_klub_2);
            txtNoUrut = itemView.findViewById(R.id.txt_no_urut);
            txtPoin = itemView.findViewById(R.id.txt_nama_poin);
            imageView = itemView.findViewById(R.id.img_logo_klub);
        }
    }
}
