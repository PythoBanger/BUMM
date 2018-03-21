package com.example.pupil.buum;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

public class WarenkorbActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{


    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warenkorb);


        mDrawerLayout =(DrawerLayout) findViewById(R.id.drawer);
        mToggle= new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.homepage){
            startActivity(new Intent(WarenkorbActivity.this, HomepageActivity.class));
        }
        if(id == R.id.logout){
            //startActivity(new Intent(HomepageActivity.this, HomepageActivity.class));
        }
        if(id == R.id.meinKonto){
            startActivity(new Intent(WarenkorbActivity.this, KontoActivity.class));
        }
        if(id == R.id.warenkorb){
            startActivity(new Intent(WarenkorbActivity.this, WarenkorbActivity.class));
        }
        if(id == R.id.meineBestellungen){
            startActivity(new Intent(WarenkorbActivity.this, BestellungenActivity.class));
        }


        return false;
    }
}
