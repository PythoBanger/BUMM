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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pupil.buum.Data.Article;
import com.example.pupil.buum.Data.Customer;
import com.example.pupil.buum.Data.Database;

import java.util.ArrayList;

public class ProduktansichtActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private Database db;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView navigationView;
    private Article selectedArticle;
    private int pr;
    private Spinner onStock;
    private TextView txtName;
    private TextView txtPreis;
    private TextView txtKB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produktansicht);

        try{
            initComponents();
            setListener();
        }catch (Exception ex){
            ex.printStackTrace();
            Toast.makeText(this,"Error in ProduktansichtActivity - method 'onCreate': "+pr,Toast.LENGTH_LONG).show();
        }
    }

    private void showProduct() throws Exception{
        pr =  this.getIntent().getExtras().getInt("selectedProduct");
        selectedArticle = db.getArticle(pr);

        txtName.setText("" + selectedArticle.getName());
        txtPreis.setText("" + selectedArticle.getPrice() + "0€");
        txtKB.setText("" + selectedArticle.getDescription());
        fillSpinnerWithAvailableSock();
    }

    private void fillSpinnerWithAvailableSock() {
        ArrayList<String> Stock= new ArrayList<>();

        for(int i=0; i< selectedArticle.getOnStock();i++){
            int count = i+1;
            Stock.add(""+count);
        }

        ArrayAdapter<String> itemsAdapter =
                new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, Stock);

        onStock.setAdapter(itemsAdapter);
    }

    private void setListener() throws Exception {
        mDrawerLayout.addDrawerListener(mToggle);
        navigationView.setNavigationItemSelectedListener(this);

        showProduct();
    }

    private void initComponents() throws Exception {
        db= Database.newInstance();
        txtName = (TextView) findViewById(R.id.txtName);
        txtPreis = (TextView) findViewById(R.id.txtPreis);
        txtKB = (TextView) findViewById(R.id.txtKB);
        mDrawerLayout =(DrawerLayout) findViewById(R.id.drawer);
        navigationView = (NavigationView) findViewById(R.id.navigation);
        onStock = (Spinner) findViewById(R.id.spinnerAnzahl);
        mToggle= new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
                startActivity(new Intent(ProduktansichtActivity.this, HomepageActivity.class));
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
                startActivity(new Intent(ProduktansichtActivity.this, KontoActivity.class));
            }
            if(id == R.id.warenkorb){
                startActivity(new Intent(ProduktansichtActivity.this, WarenkorbActivity.class));
            }
            if(id == R.id.meineBestellungen){
                startActivity(new Intent(ProduktansichtActivity.this, BestellungenActivity.class));
            }
        }catch(Exception ex){
            Toast.makeText(this,"Error caused by Menu: " + ex.getMessage(),Toast.LENGTH_LONG).show();
        }

        return false;
    }
}
