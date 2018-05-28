package com.example.pupil.buum;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pupil.buum.Data.Database;

public class RegestrierenActivity extends AppCompatActivity implements View.OnClickListener{
    private CardView btnRegister, btnBack;
    private TextView txtVorname, txtNachname,txtUsername,txtPW,txtPWWH,
                        txtStrasse, txtOrt, txtPlz, txtEmail, txtGebDate;
    Database db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try{
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_regestrieren);


            ActionBar actionBar = getSupportActionBar();
            actionBar.hide();
            initComponents();
            setListener();
        }catch (Exception ex){
            Toast.makeText(this,"Error: " +ex.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    private void setListener() {
        btnRegister.setOnClickListener(this);
        btnBack.setOnClickListener(this);
    }

    private void initComponents() {
        db = Database.newInstance();
        btnRegister = (CardView) findViewById(R.id.btnRegister);
        btnBack = (CardView) findViewById(R.id.btnBack);
        txtVorname = (TextView) findViewById(R.id.txtVorname);
        txtNachname = (TextView) findViewById(R.id.txtNachname);
        txtPW = (TextView) findViewById(R.id.txtPasswort);
        txtPWWH = (TextView) findViewById(R.id.txtPasswortWH);
        txtUsername= (TextView) findViewById(R.id.txtUsername);
        txtOrt=(TextView) findViewById(R.id.txtOrt);
        txtStrasse=(TextView) findViewById(R.id.txtStrasse);
        txtPlz = (TextView) findViewById(R.id.txtPLZ);
        txtEmail=(TextView) findViewById(R.id.txtEmail);
        txtGebDate=(TextView) findViewById(R.id.txtGeburtstag);
    }

    @Override
    public void onClick(View view) {
        try{
            switch(view.getId()){
                case R.id.btnRegister:{
                        Intent intent = new Intent(this, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        break;
                }
                case R.id.btnBack:{
                    Intent intent = new Intent(this, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    break;
                }
            }

        }catch (Exception ex){
            Toast.makeText(this, "Error by registrate: " + ex.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
}
