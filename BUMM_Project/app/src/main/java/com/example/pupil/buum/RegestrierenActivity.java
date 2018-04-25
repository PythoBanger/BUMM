package com.example.pupil.buum;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pupil.buum.Data.Database;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;

import oracle.jdbc.util.Login;

public class RegestrierenActivity extends AppCompatActivity implements View.OnClickListener{
    private Button btnRegister;
    private TextView txtVorname;
    private TextView txtNachname;
    private TextView txtPW;
    private TextView txtUName;
    private TextView txtStrasse, txtOrt, txtPlz, txtEmail, txtGebDate;
    Database db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regestrieren);

        initComponents();
        setListener();
    }

    private void setListener() {
        btnRegister.setOnClickListener(this);
    }

    private void initComponents() {
        db = Database.newInstance();
        btnRegister = (Button) findViewById(R.id.buttonRegister);
        txtVorname = (TextView) findViewById(R.id.editTextVorname);
        txtNachname = (TextView) findViewById(R.id.editTextNachname);
        txtPW = (TextView) findViewById(R.id.editTextPW);
        txtUName= (TextView) findViewById(R.id.editTextUN);
        txtOrt=(TextView) findViewById(R.id.editTextOrt);
        txtStrasse=(TextView) findViewById(R.id.editTextStraße);
        txtPlz = (TextView) findViewById(R.id.editTextPlz);
        txtEmail=(TextView) findViewById(R.id.editTextEmail);
        txtGebDate=(TextView) findViewById(R.id.editTextDate);
    }

    @Override
    public void onClick(View view) {
        try{
            switch(view.getId()){
                case R.id.buttonRegister:{
                    SimpleDateFormat sdf = new SimpleDateFormat("dd.mm.yyyy");
                    Date d=sdf.parse(txtGebDate.getText().toString());

                    db.addCustomer(txtUName.getText().toString(),txtPW.getText().toString(),txtVorname.getText().toString(),
                            txtNachname.getText().toString(),txtStrasse.getText().toString(),txtEmail.getText().toString(),
                            txtOrt.getText().toString(),"logged off","customer", d,
                            Integer.parseInt(txtPlz.getText().toString()));

                    Intent intent= new Intent(this, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    break;
                }
            }

        }catch (Exception ex){
            Toast.makeText(this, "Error by registrate: " + txtGebDate.getText().toString(),Toast.LENGTH_LONG).show();
        }
    }
}
