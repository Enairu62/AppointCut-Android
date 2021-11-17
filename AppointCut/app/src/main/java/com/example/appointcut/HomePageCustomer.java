package com.example.appointcut;



import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class HomePageCustomer extends AppCompatActivity {

    BottomNavigationView bottomNavCustomer;
    DrawerLayout drawerLayoutCustomer;
    ImageView imageMenu, imageClose, imageNotif;
    TextView txtFullName, navFullName;
    NavController navController;
    NavHostFragment navHostFragment;
    NavigationView navigationViewCustomer;
    View headerViewCustomer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page_customer);
        declareViews();
        navigationViewLogout();
        openAndCloseDrawer();
        displayCustomerName();
        openNotif();

    }

    private void declareViews() {
        bottomNavCustomer = (BottomNavigationView) findViewById(R.id.bottomNavCustomer);

        drawerLayoutCustomer = (DrawerLayout) findViewById(R.id.drawerLayoutCustomer);
        navigationViewCustomer = (NavigationView) findViewById(R.id.navigationViewCustomer);

        imageMenu = (ImageView) findViewById(R.id.imageMenu);

        headerViewCustomer = navigationViewCustomer.getHeaderView(0);
        imageClose = (ImageView) headerViewCustomer.findViewById(R.id.imageClose);

        imageNotif = (ImageView) findViewById(R.id.imageNotif);

        txtFullName = (TextView) findViewById(R.id.txtFullName);
        navFullName = (TextView) headerViewCustomer.findViewById(R.id.navFullName);

        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
        navController = navHostFragment.getNavController();

        NavigationUI.setupWithNavController(bottomNavCustomer, navController);
        NavigationUI.setupWithNavController(navigationViewCustomer, navController);
    }

    private void openDrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);
        int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.70);
        ViewGroup.LayoutParams layoutParams = navigationViewCustomer.getLayoutParams();
        layoutParams.width = width;
        navigationViewCustomer.setLayoutParams(layoutParams);
    }

    private void closeDrawer(DrawerLayout drawerLayout) {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    private void Logout(HomePageCustomer homePage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(homePage);
        builder.setTitle("Logout");
        builder.setMessage("Are your sure you want to logout?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                homePage.finishAffinity();
                Intent intent = new Intent(HomePageCustomer.this, Login.class);
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

    private void navigationViewLogout() {
        navigationViewCustomer.getMenu().findItem(R.id.navDrawerLogout).setOnMenuItemClickListener(menuItem -> {
            Logout(HomePageCustomer.this);
            return true;
        });
    }

    private void openAndCloseDrawer(){
       imageMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDrawer(drawerLayoutCustomer);
            }
        });
        imageClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeDrawer(drawerLayoutCustomer);
            }
        });
    }

    private void displayCustomerName(){
        //String fullName = getIntent().getStringExtra("fullName");
        Bundle customerProfile = SignUp.passDataIntoProfile();
        String firstName = customerProfile.getString("profileFirstName");
        String lastName = customerProfile.getString("profileLastName");
        String fullName = firstName + " " + lastName;
        txtFullName.setText(fullName);
        navFullName.setText(fullName);
    }
    private void openNotif(){
       imageNotif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.fragmentNotifications);
            }
        });
    }
}

