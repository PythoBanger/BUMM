package com.example.pupil.buum;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.SparseBooleanArray;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.pupil.buum.pkgData.Database;
import com.example.pupil.buum.pkgData.Order;

import java.util.ArrayList;

public class BestellungenActivity extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemLongClickListener,NavigationView.OnNavigationItemSelectedListener{

    private Database db = Database.newInstance();
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    NavigationView navigationView;
    private CardView btnDelete;
    ListView userOrders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bestellungen);

        initComponents();
        setUpListener();
        fillListWithOrders();

        this.setTitle("Your Order");
    }

    private void initComponents(){
        mDrawerLayout =(DrawerLayout) findViewById(R.id.drawer);
        navigationView = (NavigationView) findViewById(R.id.navigation);
        btnDelete = (CardView)findViewById(R.id.btnDeleteOrder);
        userOrders = (ListView) findViewById(R.id.Orders);
        mToggle= new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setUpListener(){
        navigationView.setNavigationItemSelectedListener(this);
        btnDelete.setOnClickListener(this);
        userOrders.setOnItemLongClickListener(this);
    }

    private void fillListWithOrders(){
        try {
            userOrders.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
            ArrayAdapter<Order> itemsAdapter =
                    new ArrayAdapter<>(this,android.R.layout.simple_list_item_multiple_choice,db.getOrdersByUser(db.getCurUser()));

            userOrders.setAdapter(itemsAdapter);
        } catch (Exception e) {
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }


    private ArrayList<Order> getSelectedOrders(){
        ArrayList<Order> allSelOrder = new ArrayList<>();
        SparseBooleanArray selectedOrder = userOrders.getCheckedItemPositions();
        for (int i = 0; i < selectedOrder.size(); i++)
            if (selectedOrder.valueAt(i)) {
                Order order = (Order) userOrders.getAdapter().getItem(selectedOrder.keyAt(i));
                allSelOrder.add(order);
            }

        return allSelOrder;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        try{
            int id = item.getItemId();

            if(id == R.id.homepage){
                startActivity(new Intent(BestellungenActivity.this, HomepageActivity.class));
            }
            if(id == R.id.logout){
                db.setCurUser(null);

                Intent intent= new Intent(this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
            if(id == R.id.meinKonto){
                startActivity(new Intent(BestellungenActivity.this, KontoActivity.class));
            }
            if(id == R.id.warenkorb){
                startActivity(new Intent(BestellungenActivity.this, WarenkorbActivity.class));
            }
            if(id == R.id.meineBestellungen){
                startActivity(new Intent(BestellungenActivity.this, BestellungenActivity.class));
            }

        }catch (Exception ex){
            Toast.makeText(this,"Error caused by Menu: " + ex.getMessage(),Toast.LENGTH_LONG).show();
        }
        return false;
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        try{
            Order o = (Order)adapterView.getItemAtPosition(i);
            if(o==null) {
                throw new Exception("no article selected");
            }

            Intent intent = new Intent(this,BestellungenAnsichtActivity.class);
            intent.putExtra("selectedOrder", o.getOrderId());
            startActivity(intent);

        }catch(Exception ex){
            Toast.makeText(this,"Error caused by selecting: " + ex.getMessage(),Toast.LENGTH_LONG).show();
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        try{
            if(view.getId() == R.id.btnDeleteOrder){
                ArrayList<Order> orders =getSelectedOrders();
                for(Order o : orders){
                    db.deleteOrder(o.getOrderId());
                }
                fillListWithOrders();
            }

        }catch (Exception ex){
            Toast.makeText(this,ex.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
}
