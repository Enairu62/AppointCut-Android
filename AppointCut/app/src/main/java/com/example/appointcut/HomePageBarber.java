package com.example.appointcut;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

import fragments.FragmentFeedback;
import fragments.FragmentIncome;
import fragments.FragmentProfileBarber;
import fragments.FragmentProfileCustomer;
import fragments.FragmentSchedule;

public class HomePageBarber extends AppCompatActivity {

    BottomNavigationView bottomNavBarber;
    DrawerLayout drawerLayoutBarber;
    ImageView imageMenu, imageClose;
    NavController navController;
    NavigationView navigationViewBarber;
    NavHostFragment navHostFragment;
    View headerViewBarber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage_barber);
        declareViews();
        openAndCloseDrawer();
        navigationViewLogout();
    }

    private void declareViews(){
        bottomNavBarber = (BottomNavigationView) findViewById(R.id.bottomNavBarber);
        drawerLayoutBarber= (DrawerLayout) findViewById(R.id.drawerLayoutBarber);
        navigationViewBarber = (NavigationView) findViewById(R.id.navigationViewBarber);
        imageMenu= (ImageView) findViewById(R.id.imageMenu);

        headerViewBarber = navigationViewBarber.getHeaderView(0);
        imageClose = (ImageView) headerViewBarber.findViewById(R.id.imageClose);

        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);

        navController = navHostFragment.getNavController();

        NavigationUI.setupWithNavController(bottomNavBarber, navController);
        NavigationUI.setupWithNavController(navigationViewBarber, navController);
    }

    private void openDrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);
        int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.70);
        ViewGroup.LayoutParams layoutParams = navigationViewBarber.getLayoutParams();
        layoutParams.width = width;
        navigationViewBarber.setLayoutParams(layoutParams);
    }

    private void closeDrawer(DrawerLayout drawerLayout) {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    private void Logout(HomePageBarber homePage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(homePage);
        builder.setTitle("Logout");
        builder.setMessage("Are your sure you want to logout?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                homePage.finishAffinity();
                Intent intent = new Intent(HomePageBarber.this, Login.class);
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

    private void openAndCloseDrawer(){
        imageMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDrawer(drawerLayoutBarber);
            }
        });
        imageClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeDrawer(drawerLayoutBarber);
            }
        });
    }

    private void navigationViewLogout(){
        navigationViewBarber.getMenu().findItem(R.id.navDrawerLogout).setOnMenuItemClickListener(menuItem -> {
            Logout(HomePageBarber.this);
            return true;
        });

    }
}