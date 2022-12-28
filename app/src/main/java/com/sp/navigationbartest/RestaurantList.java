package com.sp.navigationbartest;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

public class RestaurantList extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;
    private BottomNavigationView navView;
    private DetailsFragment detailsFragment;
    private ListFragment listFragment;
    private int bottomSelectedMenu = R.id.restlist;
    private FragmentManager fragmentManager = getSupportFragmentManager();
    //private boolean showMenu = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        navView = findViewById(R.id.bottomNavigationView);
        navView.setOnItemSelectedListener(menuSelected);
        detailsFragment = new DetailsFragment();
        listFragment = new ListFragment();


        //toolbar=findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.restdrawer);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.nav_open,R.string.nav_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(navSelected);

    }
    @Override
    protected void onStart() {
        navView.setSelectedItemId(R.id.restlist);
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.option, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        //if(actionBarDrawerToggle.onOptionsItemSelected(item)) {
        Intent intent;
            //return true;
       // }
        switch (item.getItemId()){
            case (R.id.add):
                navView.setSelectedItemId(R.id.restdetail);
                Bundle bundle = new Bundle();
                bundle.putString("id", null);
                detailsFragment.getParentFragmentManager().setFragmentResult("listToDetailKey", bundle);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    NavigationBarView.OnItemSelectedListener menuSelected = new NavigationBarView.OnItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int id = item.getItemId();
            invalidateOptionsMenu();
            switch (id){
                //Select detail menu option
                case(R.id.restdetail):fragmentManager.beginTransaction()
                        .replace(R.id.restaurantFragmentContainer,detailsFragment)
                        .setReorderingAllowed(true)
                        .addToBackStack(null)
                        .commit();
                    return true;
                case (R.id.restlist):fragmentManager.beginTransaction()
                        .replace(R.id.restaurantFragmentContainer, listFragment)
                        .setReorderingAllowed(true)
                        .addToBackStack(null)
                        .commit();
                    return true;
            }
            return false;
        }
    };
    NavigationView.OnNavigationItemSelectedListener navSelected = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int id = item.getItemId();
            if(id == R.id.nav_exit){
                finish();
            } else{
                Fragment fragment = null;
                switch (id){
                    case R.id.nav_list: fragment = new ListFragment();
                                        break;
                    case R.id.nav_add: fragment = new DetailsFragment();
                        break;
                    case R.id.nav_about: fragment = new AboutFragment();
                        break;
                }
                fragmentManager.beginTransaction().replace(R.id.restaurantFragmentContainer,fragment).commit();
                drawerLayout.closeDrawer(GravityCompat.START);
            }
            return true;
        }
    };
}