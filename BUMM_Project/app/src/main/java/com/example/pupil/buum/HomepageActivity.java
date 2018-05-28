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

import com.example.pupil.buum.Data.Article;
import com.example.pupil.buum.Data.Customer;
import com.example.pupil.buum.Data.Database;

public class HomepageActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, NavigationView.OnNavigationItemSelectedListener{

    private Database db;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private ListView ProductListView;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
    try {
        initComponents();
        setListener();
        insertDummyValues();
    }catch(Exception ex){
        Toast.makeText(this,"Error: "+ex.getMessage(),Toast.LENGTH_LONG).show();
    }
    }

    private void setListener() {
        mDrawerLayout.addDrawerListener(mToggle);
        navigationView.setNavigationItemSelectedListener(this);
        ProductListView.setOnItemClickListener(this);
    }

    private void initComponents() {
        db= Database.newInstance();
        mDrawerLayout =(DrawerLayout) findViewById(R.id.drawer);
        navigationView = (NavigationView) findViewById(R.id.navigation);
        ProductListView = (ListView) findViewById(R.id.listView);
        mToggle= new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void insertDummyValues() throws Exception {
        EventArticleListAdapter itemsAdapter =
                new EventArticleListAdapter(this, db.getArticles());

        ProductListView.setAdapter(itemsAdapter);
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
            switch (item.getItemId()){
                case R.id.homepage:{
                    startActivity(new Intent(this, HomepageActivity.class));
                    break;
                }
                case R.id.logout:{
                    Customer c = db.getCurrCustomer();
                    c.setStatus("logged off");
                    db.updateCustomer(c);
                    db.setCurrCustomer(null);

                    Intent intent= new Intent(this, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    break;
                }
                case R.id.meinKonto:{
                    startActivity(new Intent(this, KontoActivity.class));
                    break;
                }
                case R.id.warenkorb:{
                    startActivity(new Intent(HomepageActivity.this, WarenkorbActivity.class));
                    break;
                }

                case R.id.meineBestellungen:{
                    startActivity(new Intent(HomepageActivity.this, BestellungenActivity.class));
                    break;
                }

                default: {
                    Toast.makeText(this," Nothing selected in the menu!!",Toast.LENGTH_LONG).show();
                    break;
                }
            }

        }catch (Exception ex){
            Toast.makeText(this,"Error caused by Menu: " + ex.getMessage(),Toast.LENGTH_LONG).show();
        }

        return false;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        try{
            Article a = (Article)adapterView.getItemAtPosition(i);
            if(a==null) {
                throw new Exception("no article selected");
            }

            Intent intent = new Intent(this,ProduktansichtActivity.class);
            intent.putExtra("selectedProduct", a.getId());
            startActivity(intent);

        }catch(Exception ex){
            Toast.makeText(this,"Error caused by selecting spinner item: " + ex.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
}
