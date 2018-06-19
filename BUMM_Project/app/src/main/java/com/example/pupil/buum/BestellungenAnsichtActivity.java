package com.example.pupil.buum;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.pupil.buum.pkgData.Database;
import com.example.pupil.buum.pkgData.Order;

import static android.widget.Toast.LENGTH_LONG;

public class BestellungenAnsichtActivity extends AppCompatActivity implements AdapterView.OnItemClickListener,NavigationView.OnNavigationItemSelectedListener{

    private Database db = Database.newInstance();
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    NavigationView navigationView;
    private int pr;
    ListView userOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bestellansicht);

        try{
            initComponents();
            setUpListener();
            fillInOrderArticlesInList();
        }catch (Exception ex){
            Toast.makeText(this,"Error: "+ex.getMessage(), LENGTH_LONG).show();
        }
    }

    private void fillInOrderArticlesInList() throws Exception {
        pr =  this.getIntent().getExtras().getInt("selectedOrder");
        this.setTitle("Order: "+pr);

        EventOrderListAdapter itemsAdapter =
                new EventOrderListAdapter(this, db.getOrderById(pr).getAllOrderesArticles());

        userOrder.setAdapter(itemsAdapter);
    }

    private void initComponents(){
        mDrawerLayout =(DrawerLayout) findViewById(R.id.drawer);
        navigationView = (NavigationView) findViewById(R.id.navigation);
        userOrder = (ListView) findViewById(R.id.listOrder);
        mToggle= new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setUpListener() throws Exception {
        navigationView.setNavigationItemSelectedListener(this);

        fillInOrderArticlesInList();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        try{
            int id = item.getItemId();

            if(id == R.id.homepage){
                startActivity(new Intent(BestellungenAnsichtActivity.this, HomepageActivity.class));
            }
            if(id == R.id.logout){
                db.setCurUser(null);

                Intent intent= new Intent(this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
            if(id == R.id.meinKonto){
                startActivity(new Intent(BestellungenAnsichtActivity.this, KontoActivity.class));
            }
            if(id == R.id.warenkorb){
                startActivity(new Intent(BestellungenAnsichtActivity.this, WarenkorbActivity.class));
            }
            if(id == R.id.meineBestellungen){
                startActivity(new Intent(BestellungenAnsichtActivity.this, BestellungenAnsichtActivity.class));
            }

        }catch (Exception ex){
            Toast.makeText(this,"Error caused by Menu: " + ex.getMessage(),Toast.LENGTH_LONG).show();
        }
        return false;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        try{
            Order o = (Order)adapterView.getItemAtPosition(i);
            if(o==null) {
                throw new Exception("no article selected");
            }

            Intent intent = new Intent(this,ProduktansichtActivity.class);
            intent.putExtra("selectedProduct", o.getOrderId());
            startActivity(intent);

        }catch(Exception ex){
            Toast.makeText(this,"Error caused by selecting spinner item: " + ex.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
}
