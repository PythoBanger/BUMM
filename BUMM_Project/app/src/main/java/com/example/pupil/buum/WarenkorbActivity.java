package com.example.pupil.buum;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.pupil.buum.Data.Customer;
import com.example.pupil.buum.Data.Database;

import java.util.ArrayList;

public class WarenkorbActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{


    private Database db = Database.newInstance();
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

        this.setTitle("Dein Warenkorb");
        setUp();
    }


    private void setUp(){
        ArrayList<String> dummyList = new ArrayList<>();
        dummyList.add("IPhone 10    1000€");
        dummyList.add("Adidas T-Shirt   20€");
        dummyList.add("Nike T-Shirt   30€");


        ArrayAdapter<String> itemsAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, dummyList);

        ListView SearchListView = (ListView) findViewById(R.id.listView3);
        SearchListView.setAdapter(itemsAdapter);
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
        try{
            int id = item.getItemId();

            if(id == R.id.homepage){
                startActivity(new Intent(WarenkorbActivity.this, HomepageActivity.class));
            }
            if(id == R.id.logout){
                Customer c = db.getCurrCustomer();
                c.setStatus("logged off");
                db.updateCustomer(c);

                Intent intent= new Intent(this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
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
        }catch(Exception ex){
            Toast.makeText(this,"Error caused by Menu: " + ex.getMessage(),Toast.LENGTH_LONG).show();
        }

        return false;
    }
}
