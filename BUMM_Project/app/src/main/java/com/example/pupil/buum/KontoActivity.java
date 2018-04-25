package com.example.pupil.buum;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pupil.buum.Data.Customer;
import com.example.pupil.buum.Data.Database;

import java.util.Date;

public class KontoActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener{

    private Database db;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView navigationView;
    private Button btnSave;
    private Button btnDelete;
    private TextView txtVorname;
    private TextView txtNachname;
    private TextView txtPW,txtPWWH;
    private TextView txtStrasse, txtOrt, txtPlz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konto);

        initComponents();
        setListener();
    }

    private void setListener() {
        btnSave.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        mDrawerLayout.addDrawerListener(mToggle);
        navigationView.setNavigationItemSelectedListener(this);

    }

    private void initComponents() {
        db= Database.newInstance();
        mDrawerLayout =(DrawerLayout) findViewById(R.id.drawer);
        navigationView = (NavigationView) findViewById(R.id.navigation);
        btnSave = (Button) findViewById(R.id.buttonSpeichern);
        btnDelete = (Button) findViewById(R.id.buttonKontoLoeschne);
        txtVorname = (TextView) findViewById(R.id.editTextVorname);
        txtNachname = (TextView) findViewById(R.id.editTextNachname);
        txtPW = (TextView) findViewById(R.id.editTextPW);
        txtPWWH = (TextView) findViewById(R.id.editTextPWWH);
        txtOrt=(TextView) findViewById(R.id.editTextOrt);
        txtStrasse=(TextView) findViewById(R.id.editTextStraße);
        txtPlz = (TextView) findViewById(R.id.editTextPlz);

        mToggle= new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        this.setTitle(db.getCurrCustomer().getUsername());
        Customer cc = db.getCurrCustomer();
        txtVorname.setText(""+cc.getFirstName());
        txtNachname.setText(""+cc.getLastName());
        txtOrt.setText(""+cc.getLocation());
        txtPlz.setText(""+cc.getZipcode());
        txtStrasse.setText(""+cc.getAddress());
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
                    startActivity(new Intent(KontoActivity.this, HomepageActivity.class));
                    break;
                }
                case R.id.logout:{
                    Customer c = db.getCurrCustomer();
                    c.setStatus("logged off");
                    db.updateCustomer(c);

                    Intent intent= new Intent(this, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    break;
                }
                case R.id.meinKonto:{
                    startActivity(new Intent(KontoActivity.this, KontoActivity.class));
                    break;
                }
                case R.id.warenkorb:{
                    startActivity(new Intent(KontoActivity.this, WarenkorbActivity.class));
                    break;
                }

                case R.id.meineBestellungen:{
                    startActivity(new Intent(KontoActivity.this, BestellungenActivity.class));
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
    public void onClick(View view) {
        try{
            switch(view.getId()){
                case R.id.buttonSpeichern:{
                    Customer cc = db.getCurrCustomer();
                    Customer c = new Customer(cc.getUsername(),txtPW.getText().toString(),txtVorname.getText().toString(),
                            txtNachname.getText().toString(),txtStrasse.getText().toString(),cc.getEmail(),
                            txtOrt.getText().toString(),"logged off","customer", cc.getBirthdate(),Integer.parseInt(txtPlz.getText().toString()));
                    db.updateCustomer(c);

                    Intent intent= new Intent(this, HomepageActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    Toast.makeText(this, "Your data has been changed!",Toast.LENGTH_LONG).show();
                    break;
                }
                case R.id.buttonKontoLoeschne:{
                    Intent intent= new Intent(this, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    break;
                }
            }
        }catch(Exception ex){
            Toast.makeText(this, "Error by click on button: " + ex.getMessage(),Toast.LENGTH_LONG).show();

        }
    }
}
