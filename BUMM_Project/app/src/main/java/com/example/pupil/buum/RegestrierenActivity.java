package com.example.pupil.buum;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pupil.buum.pkgData.Database;
import com.example.pupil.buum.pkgData.LocalDate;
import com.example.pupil.buum.pkgData.User;

import java.text.SimpleDateFormat;
import java.util.Date;


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
                    LocalDate birthdate = checkFormatOfDate();
                    User u = checkIfInputIsValidAndGetUser(birthdate);

                    String res = db.addUser(u);
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

    private LocalDate checkFormatOfDate() {
        int day= 0;
        int month = 0;
        int year = 0;
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("dd.mm.yyyy");
            Date d=sdf.parse(txtGebDate.getText().toString());
            String [] date = (txtGebDate.getText().toString().split("\\."));
            day = Integer.parseInt(date[0]);
            month = Integer.parseInt(date[1]);
            year = Integer.parseInt(date[2]);

        }catch(Exception ex){
            Toast.makeText(this,"Data has no valid format!",Toast.LENGTH_LONG).show();
        }

        return new LocalDate(day, month, year);
    }

    private User checkIfInputIsValidAndGetUser(LocalDate birthdate) throws Exception {
        String vn= txtVorname.getText().toString();
        String nn= txtNachname.getText().toString();
        String un= txtUsername.getText().toString();
        String pw= txtPW.getText().toString();
        String pwWh =txtPWWH.getText().toString();
        String strasse= txtStrasse.getText().toString();
        String ort= txtOrt.getText().toString();
        String email= txtEmail.getText().toString();
        String plzString = txtPlz.getText().toString();
        int plz= Integer.parseInt(plzString);

        if(un.isEmpty()){
            throw new Exception("Fill in an username");
        }

        if(vn.isEmpty() || vn.length()==1){
            throw new Exception("First name has to be at least 2 characters long");
        }

        if(vn.charAt(0) != vn.toUpperCase().charAt(0)){
            throw new Exception("First name has to start with an upper case");
        }

        if(nn.isEmpty() || nn.length()==1){
            throw new Exception("Last name has to be at least 2 characters long");
        }

        if(nn.charAt(0) != nn.toUpperCase().charAt(0)){
            throw new Exception("Last name has to start with an upper case");
        }

        if(un.isEmpty() || pw.length()<5 || pwWh.length()<5 || strasse.isEmpty() || ort.isEmpty() || email.isEmpty()) {
            throw new Exception("invalid register data");
        }

        if((plzString.length() != 4) || plzString.charAt(0) == '0'){
            throw new Exception("Zip code has to contain 4 digits and cannot start with 0!");
        }

        return new User(un,pw,vn,nn,email,ort,strasse,"customer",birthdate,plz,"active");
    }
}
