package com.example.xcryptsms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    //views

    ActionBar actionBar;
    private BottomNavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ActionBar and its title
        actionBar = getSupportActionBar();
        actionBar.setTitle("xcryptSMS");


        //Bottom navigation


        navigationView = findViewById(R.id.navigation);
       //navigationView.setOnNavigationItemReselectedListener(selectedListener);

        //message view transaction (default, on start)
        actionBar.setTitle("xcryptSMS"); //change actionbar Title
        MessageFragment fragment1 = new MessageFragment();
        FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
        ft1.replace(R.id.content, fragment1, "" );
        ft1.commit();
        navigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        switch (menuItem.getItemId()){
                            case R.id.nav_message:
                                //message view transaction
                                actionBar.setTitle("xcryptSMS"); //change actionbar Title
                                MessageFragment fragment1 = new MessageFragment();
                                FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
                                ft1.replace(R.id.content, fragment1, "" );
                                ft1.commit();
                                return true;
                            case R.id.nav_contact:
                                //Contact view transaction
                                //message view transaction
                                actionBar.setTitle("Contact"); //change actionbar Title
                                ContactFragment fragment2 = new ContactFragment();
                                FragmentTransaction ft2 = getSupportFragmentManager().beginTransaction();
                                ft2.replace(R.id.content, fragment2, "" );
                                ft2.commit();
                                return true;


                        }
                        return false;
                    }
                }
        );


    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        //hide some menu
       // menu.findItem(R.id.action_search).setVisible(false);
        menu.findItem(R.id.action_encrypt).setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }*/
}
