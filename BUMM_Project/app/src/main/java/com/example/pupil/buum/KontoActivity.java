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
import android.widget.TextView;
import android.widget.Toast;

import com.example.pupil.buum.pkgData.User;
import com.example.pupil.buum.pkgData.Database;

public class KontoActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener{

    private Database db;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView navigationView;
    private CardView btnSaveChanges, btnDeleteAccount;
    private TextView txtChangeVorname, txtChangeNachname,
                        txtOldPW, txtPW,txtPWWH, txtStrasse,
                        txtOrt, txtPlz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konto);

        initComponents();
        setListener();
    }

    private void setListener() {
        btnSaveChanges.setOnClickListener(this);
        btnDeleteAccount.setOnClickListener(this);
        mDrawerLayout.addDrawerListener(mToggle);
        navigationView.setNavigationItemSelectedListener(this);

    }

    private void initComponents() {
        db= Database.newInstance();
        mDrawerLayout =(DrawerLayout) findViewById(R.id.drawer);
        navigationView = (NavigationView) findViewById(R.id.navigation);
        btnSaveChanges = (CardView) findViewById(R.id.btnSaveChanges);
        btnDeleteAccount = (CardView) findViewById(R.id.btnDeleteAccount);
        txtChangeVorname = (TextView) findViewById(R.id.txtVorname);
        txtChangeNachname = (TextView) findViewById(R.id.txtNachname);
        txtOldPW = (TextView) findViewById(R.id.txtOldPasswort);
        txtPW = (TextView) findViewById(R.id.txtPasswort                );
        txtPWWH = (TextView) findViewById(R.id.txtPasswortWH);
        txtOrt=(TextView) findViewById(R.id.txtOrt);
        txtStrasse=(TextView) findViewById(R.id.txtStrasse);
        txtPlz = (TextView) findViewById(R.id.txtPLZ);

        mToggle= new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        User cc = db.getCurUser();
        this.setTitle(db.getCurUser().getUsername());
        txtChangeVorname.setText(""+cc.getFirstName());
        txtChangeNachname.setText(""+cc.getLastName());
        txtOrt.setText(""+cc.getLocation());
        txtPlz.setText(""+cc.getZipCode());
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
                    db.setCurUser(null);

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
                case R.id.btnSaveChanges:{

                    checkInputAndUpdate();


                    break;
                }
                case R.id.btnDeleteAccount:{
                    User u = db.getCurUser();
                    u.setStatus("deactive");
                    db.updateUser(u);
                    db.setCurUser(null);

                    Intent intent= new Intent(this, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    break;
                }
            }
        }catch(Exception ex){
            Toast.makeText(this, "Error: " + ex.getMessage(),Toast.LENGTH_LONG).show();

        }
    }

    private void checkInputAndUpdate() throws Exception {

        User cc = db.getCurUser();

        if(!txtPW.getText().toString().equals(txtPWWH.getText().toString())){
            throw new Exception("'Passwort WH:' und 'Passwort' sind nicht identisch");
        }
        User u = new User(cc.getUsername().toString(),txtPW.getText().toString(),txtChangeVorname.getText().toString(),
                txtChangeNachname.getText().toString(),cc.getEmail(),
                txtOrt.getText().toString(),txtStrasse.getText().toString(),
                "customer", cc.getBirthdate(),Integer.parseInt(txtPlz.getText().toString()),"logged off");
        db.updateUser(u);

        Intent intent= new Intent(this, HomepageActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        Toast.makeText(this, "Your data has been changed!",Toast.LENGTH_LONG).show();
    }
}
