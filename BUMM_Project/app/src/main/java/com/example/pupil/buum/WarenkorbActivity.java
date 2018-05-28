package com.example.pupil.buum;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.pupil.buum.Data.Article;
import com.example.pupil.buum.Data.Customer;
import com.example.pupil.buum.Data.Database;

import java.util.ArrayList;

public class WarenkorbActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener, NavigationView.OnNavigationItemSelectedListener{


    private Database db = Database.newInstance();
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    NavigationView navigationView;
    private CardView btnBuy;
    private CardView btnDelete;
    private ListView remeberedArticleList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warenkorb);

        initComponents();
        setListener();


        this.setTitle("Your Shopping Cart");
        setUp();
    }

    private void initComponents(){
        mDrawerLayout =(DrawerLayout) findViewById(R.id.drawer);
        mToggle= new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close);
        remeberedArticleList = (ListView) findViewById(R.id.rememberedArticleList);
        btnBuy = (CardView) findViewById(R.id.btnBuy);
        btnDelete = (CardView) findViewById(R.id.btnDelete);
        navigationView = (NavigationView) findViewById(R.id.navigation);
        mToggle.syncState();

    }

    private void setListener(){
        mDrawerLayout.addDrawerListener(mToggle);
        navigationView.setNavigationItemSelectedListener(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btnBuy.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        remeberedArticleList.setOnItemClickListener(this);
    }

    private void setUp(){
        ArrayList<Article> dummyList = new ArrayList<>();
        try {
            dummyList.add(db.getArticle(2));
            dummyList.add(db.getArticle(4));
            dummyList.add(db.getArticle(1));
        } catch (Exception e) {
            e.printStackTrace();
        }

        EventCartListAdapter itemsAdapter =
                new EventCartListAdapter(this, dummyList);

        remeberedArticleList.setAdapter(itemsAdapter);
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

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btnBuy:{
                EventCartListAdapter adapter = (EventCartListAdapter) remeberedArticleList.getAdapter();
                ArrayList<Article> checkedItemList = adapter.getCheckedItemList();
                String msg= "";

                for(Article a:checkedItemList){
                    msg+=""+a.toString()+"\n";
                }
                Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
                break;
            }
        }
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
