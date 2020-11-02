package com.example.android.e_commerce.ui.ui.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.android.e_commerce.R;
import com.example.android.e_commerce.databinding.ActivityMainBinding;
import com.example.android.e_commerce.ui.ui.Fragment.BookMarkFragment;
import com.example.android.e_commerce.ui.ui.Fragment.HomeFragment;
import com.example.android.e_commerce.ui.ui.Fragment.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        getSupportActionBar().hide();
        mAuth = FirebaseAuth.getInstance();

        BottomNavigationView bottomNavigationView = findViewById(R.id.mainBottomNav);
        NavController navController = Navigation.findNavController(this,R.id.fragment);
        NavigationUI.setupWithNavController(bottomNavigationView,navController);
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() == null) {
            finishAffinity();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }
    }

}
