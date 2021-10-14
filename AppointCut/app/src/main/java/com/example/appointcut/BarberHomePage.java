package com.example.appointcut;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import fragments.FragmentFeedback;
import fragments.FragmentReports;
import fragments.FragmentSchedule;

public class BarberHomePage extends AppCompatActivity {

    BottomNavigationView bottomNavBarber;
    DrawerLayout drawerLayoutBarber;
    ImageView imageMenu, imageClose;
    TextView navDrawerLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barber_home_page);

        declareViews();

        bottomNavBarber.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.bottomNavSchedule:
                        openFragment(FragmentSchedule.newInstance("",""));
                        return true;
                    case R.id.bottomNavFeedback:
                        openFragment(FragmentFeedback.newInstance("",""));
                        return true;
                    case R.id.bottomNavReports:
                        openFragment(FragmentReports.newInstance("",""));
                        return true;
                }
                return false;
            }
        });

        imageMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDrawer(drawerLayoutBarber);
            }
        });

        imageClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeDrawer(drawerLayoutBarber);
            }
        });

        navDrawerLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Logout(BarberHomePage.this);
            }
        });
    }

    private void openFragment(Fragment fragment) {
        Log.d(TAG, "openFragment: ");
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.constraintLayoutBarber, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void openDrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    private void closeDrawer(DrawerLayout drawerLayout) {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    @Override
    public void onBackPressed() {
        if (drawerLayoutBarber.isDrawerOpen(GravityCompat.START)) {
            drawerLayoutBarber.closeDrawer(GravityCompat.START);
        } else {
            moveTaskToBack(true);
        }
    }

    private void Logout(BarberHomePage homePage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(homePage);
        builder.setTitle("Logout");
        builder.setMessage("Are your sure you want to logout?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                homePage.finishAffinity();
                Intent intent = new Intent(BarberHomePage.this, Login.class);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    private void declareViews(){
        bottomNavBarber= (BottomNavigationView) findViewById(R.id.bottomNavBarber);
        getSupportFragmentManager().beginTransaction().replace(R.id.constraintLayoutBarber, new FragmentSchedule()).commit();
        bottomNavBarber.setSelectedItemId(R.id.bottomNavSchedule);

        drawerLayoutBarber= (DrawerLayout) findViewById(R.id.drawerLayoutBarber);
        navDrawerLogout = (TextView) findViewById(R.id.navDrawerLogout);
        imageMenu= (ImageView) findViewById(R.id.imageMenu);
        imageClose = (ImageView) findViewById(R.id.imageClose);
    }
}