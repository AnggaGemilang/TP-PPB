package com.example.tasklist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.example.tasklist.databinding.ActivityMainBinding;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        HomeFragment homeFragment = new HomeFragment();
        TaskListFragment taskListFragment = new TaskListFragment();

        getSupportActionBar().setElevation(0);
        getSupportActionBar().setTitle("Dashboard");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#4044C9")));
        binding.navBottom.setItemSelected(R.id.nav_home, true);

        binding.navBottom.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                switch (i){
                    case (R.id.nav_home):
                        makeCurrentFragment(homeFragment);
                        Objects.requireNonNull(getSupportActionBar()).setTitle("Dashboard");
                        break;
                    case (R.id.nav_tasklist):
                        makeCurrentFragment(taskListFragment);
                        Objects.requireNonNull(getSupportActionBar()).setTitle("TaskList");
                        break;
                }
            }
        });
    }

    private void makeCurrentFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_layout, fragment)
                .commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.getRoot().requestFocus();
        makeCurrentFragment(new HomeFragment());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.item_menu_actionbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case (R.id.act_profile):
                Toast.makeText(this, "Profile diklik!", Toast.LENGTH_SHORT).show();
                break;
            case (R.id.act_add_task):
                Intent intent = new Intent(this, AddDataActivity.class);
                intent.putExtra("id", "tambah");
                startActivity(intent);
                break;
            case  (R.id.act_about):
                new AlertDialog.Builder(this)
                        .setTitle("Versi Aplikasi")
                        .setMessage("Beta 1.0.0")
                        .setCancelable(true)
                        .setPositiveButton("OK", null)
                        .create()
                        .show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}