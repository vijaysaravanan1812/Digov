 package com.example.digov;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

 public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

     private BottomNavigationView bottomNavigationView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        /**
         * Bottom Navigation
         */
        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.home);
    }




    /**
        * Two windows
     * 1) MicFragment
     * 2) History fragment
     */
     HomeFragment homeFragment = new HomeFragment();
     ScanFragment scanFragment = new ScanFragment();
     BillFragment billFragment = new BillFragment();

     /**
      *
      * Switch case to Navigate the windows
      */
     @Override
     public boolean onNavigationItemSelected(@NonNull MenuItem item) {
         switch (item.getItemId())
         {
             case R.id.home:
                 getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
                 return true;

             case R.id.scan:
                 getSupportFragmentManager().beginTransaction().replace(R.id.container, scanFragment).commit();
                 return  true;

             case R.id.bill:
                 getSupportFragmentManager().beginTransaction().replace(R.id.container, billFragment).commit();
                 return  true;
         }
         return false;
     }
 }
