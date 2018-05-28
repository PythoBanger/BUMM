package com.example.pupil.buum;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pupil.buum.Data.Article;
import com.example.pupil.buum.Data.Customer;
import com.example.pupil.buum.Data.Database;

import java.util.ArrayList;

public class ProduktansichtActivity extends AppCompatActivity implements View.OnClickListener,NavigationView.OnNavigationItemSelectedListener{

    private Database db;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView navigationView;
    private Article selectedArticle;
    private int pr;
    private Spinner onStock;
    private TextView txtName;
    private TextView txtPrice;
    private TextView txtDesc;
    private TextView btnRate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produktansicht);

        try{
            initComponents();
            setListener();
        }catch (Exception ex){
            ex.printStackTrace();
            Toast.makeText(this,"Error: "+pr,Toast.LENGTH_LONG).show();
        }
    }

    private void showProduct() throws Exception{
        pr =  this.getIntent().getExtras().getInt("selectedProduct");
        selectedArticle = db.getArticle(pr);

        txtName.setText("" + selectedArticle.getName());
        txtPrice.setText("" + selectedArticle.getPrice() + "0€");
        txtDesc.setText("" + selectedArticle.getDescription());
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
        btnRate.setOnClickListener(this);

        showProduct();
    }

    private void initComponents() throws Exception {
        db= Database.newInstance();
        txtName = (TextView) findViewById(R.id.txtProductName);
        txtPrice = (TextView) findViewById(R.id.txtPrice);
        txtDesc = (TextView) findViewById(R.id.txtDesc);
        btnRate = (TextView) findViewById(R.id.btnRateProduct);
        mDrawerLayout =(DrawerLayout) findViewById(R.id.drawer);
        navigationView = (NavigationView) findViewById(R.id.navigation);
        onStock = (Spinner) findViewById(R.id.countProducts);
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

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnRateProduct:{
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setTitle("Rate " + selectedArticle.getName());
                alert.setMessage("Message");
                // Create TextView
                final TextView input = new TextView (this);
                alert.setView(input);

                alert.setPositiveButton("btnRate", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        input.setText("Rate");
                        // Do something with value!
                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Canceled.
                    }
                });
                alert.show();
                break;
            }
        }
    }
}
