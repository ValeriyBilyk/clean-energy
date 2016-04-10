package com.example.advokat.cleanenergy.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.advokat.cleanenergy.R;
import com.example.advokat.cleanenergy.app.App;
import com.example.advokat.cleanenergy.entities.CurrentAsset;
import com.example.advokat.cleanenergy.fragments.ExpenditureFragment;
import com.example.advokat.cleanenergy.fragments.StatisticDayFragment;
import com.example.advokat.cleanenergy.fragments.StatisticMonthFragment;
import com.example.advokat.cleanenergy.fragments.StatisticWeekFragment;
import com.example.advokat.cleanenergy.rest.ApiClient;
import com.example.advokat.cleanenergy.rest.services.MainService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    //Defining Variables
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private MainService mainService;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainService = ApiClient.retrofit().getMainService();
        mainService.getCurrentAsset().enqueue(new Callback<CurrentAsset>() {
            @Override
            public void onResponse(Call<CurrentAsset> call, Response<CurrentAsset> response) {
                if (response.isSuccessful()) {
                    App.setCurrentAsset(response.body());
                    if (pDialog.isShowing())
                        pDialog.dismiss();
                    if (savedInstanceState == null) {
                        navigationView.getMenu().performIdentifierAction(R.id.costs, 0);
                    }
                }
            }

            @Override
            public void onFailure(Call<CurrentAsset> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // Initializing Toolbar and setting it as the actionbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Initializing NavigationView
        navigationView = (NavigationView) findViewById(R.id.navigation_view);

        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }

                //Closing drawer on item click
                drawerLayout.closeDrawers();

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {

                    //Replacing the main content with StatisticDayFragment Which is our Inbox View;
                    case R.id.costs:
                        replaceFragment(new ExpenditureFragment());
                        return true;

                    case R.id.statistic_item_day:
                        replaceFragment(new StatisticDayFragment());
                        return true;

                    // For rest of the options we just show a toast on click

                    case R.id.statistic_item_week:
                        replaceFragment(new StatisticWeekFragment());
                        return true;

                    case R.id.statistic_item_month:
                        replaceFragment(new StatisticMonthFragment());
                        return true;

                    case R.id.exit:
                        Toast.makeText(getApplicationContext(), "Logout", Toast.LENGTH_SHORT).show();
                        return true;

                    default:
                        Toast.makeText(getApplicationContext(), "Some", Toast.LENGTH_SHORT).show();
                        return true;

                }
            }
        });

        // Initializing Drawer Layout and ActionBarToggle
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank

                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessay or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();

       /* pDialog = new ProgressDialog(MainActivity.this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
        pDialog.show();*/

    }

    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame, fragment)
                .commit();
    }

}