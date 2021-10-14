package com.example.appointcut;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import fragments.FragmentAppointment;
import fragments.FragmentBookmark;
import fragments.FragmentCameraAR;
import fragments.FragmentContactUs;
import fragments.FragmentGallery;
import fragments.FragmentHairStyle;
import fragments.FragmentHairTrend;
import fragments.FragmentMessage;
import fragments.FragmentProfile;
import fragments.FragmentSettings;
import fragments.FragmentTermsAndConditions;

public class CustomerHomePage extends AppCompatActivity {

    BottomNavigationView bottomNavCustomer;
    DrawerLayout drawerLayoutCustomer;
    ImageView imageMenu, imageClose;
    TextView navDrawerLogout, navDrawerAppoint ,navDrawerBookmark, navDrawerProfile, navDrawerGallery,
            navDrawerContact, navDrawerTermsAndCon, navDrawerSettings;
    NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_home_page);

        declareViews();
        defaultFragment();

        bottomNavCustomer.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.bottomNavAR:
                        openFragment(FragmentCameraAR.newInstance("",""));
                        bottomNavCustomer.setVisibility(View.GONE);
                        return true;
                    case R.id.bottomNavHairStyle:
                        openFragment(FragmentHairStyle.newInstance());
                        return true;
                    case R.id.bottomNavHairTrends:
                        openFragment(FragmentHairTrend.newInstance());
                        return true;
                    case R.id.bottomNavMessage:
                        openFragment(FragmentMessage.newInstance("",""));
                        return true;
                }
                return false;
            }
        });

        imageMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDrawer(drawerLayoutCustomer);
            }
        });

        imageClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeDrawer(drawerLayoutCustomer);
            }
        });

        navDrawerLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Logout(CustomerHomePage.this);
            }
        });

        navDrawerAppoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFragment(FragmentAppointment.newInstance("",""));
                bottomNavMethod();
            }
        });

        navDrawerBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFragment(FragmentBookmark.newInstance("",""));
                bottomNavMethod();
            }
        });

        navDrawerProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFragment(FragmentProfile.newInstance("",""));
                bottomNavMethod();
            }
        });
        navDrawerGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFragment(FragmentGallery.newInstance("",""));
                bottomNavMethod();
            }
        });

        navDrawerContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFragment(FragmentContactUs.newInstance("",""));
                bottomNavMethod();
            }
        });

        navDrawerTermsAndCon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFragment(FragmentTermsAndConditions.newInstance("",""));
                bottomNavMethod();
            }
        });

        navDrawerSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFragment(FragmentSettings.newInstance("",""));
                bottomNavMethod();
            }
        });
    }

    private void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.constraintLayoutCustomer, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void defaultFragment(){
        bottomNavCustomer.setSelectedItemId(R.id.bottomNavHairTrends);
        Fragment fragment = FragmentHairTrend.newInstance();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.constraintLayoutCustomer, fragment, "fragment_hairTrend");
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
        if (drawerLayoutCustomer.isDrawerOpen(GravityCompat.START)) {
            drawerLayoutCustomer.closeDrawer(GravityCompat.START);
        }
        else {
            moveTaskToBack(true);
        }
    }

    private void Logout(CustomerHomePage homePage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(homePage);
        builder.setTitle("Logout");
        builder.setMessage("Are your sure you want to logout?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                homePage.finishAffinity();
                Intent intent = new Intent(CustomerHomePage.this, Login.class);
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

    private void bottomNavMethod(){
        closeDrawer(drawerLayoutCustomer);
        bottomNavCustomer.setVisibility(View.VISIBLE);
        bottomNavCustomer.getMenu().setGroupCheckable(0, false, true);
    }

    private void declareViews(){
        bottomNavCustomer= (BottomNavigationView) findViewById(R.id.bottomNavCustomer);
        bottomNavCustomer.setSelectedItemId(R.id.fragmentHairTrend);

        drawerLayoutCustomer= (DrawerLayout) findViewById(R.id.drawerLayoutCustomer);
        imageMenu= (ImageView) findViewById(R.id.imageMenu);
        imageClose = (ImageView) findViewById(R.id.imageClose);

        navDrawerLogout = (TextView) findViewById(R.id.navDrawerLogout);
        navDrawerAppoint = (TextView) findViewById(R.id.navDrawerAppoint);
        navDrawerBookmark = (TextView) findViewById(R.id.navDrawerBookmark);
        navDrawerProfile = (TextView) findViewById(R.id.navDrawerProfile);

        navDrawerGallery = (TextView) findViewById(R.id.navDrawerGallery);
        navDrawerContact = (TextView) findViewById(R.id.navDrawerContact);
        navDrawerTermsAndCon = (TextView) findViewById(R.id.navDrawerTermsAndCon);
        navDrawerSettings = (TextView) findViewById(R.id.navDrawerSettings);
    }

}

