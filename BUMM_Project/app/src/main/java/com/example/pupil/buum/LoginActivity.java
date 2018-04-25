package com.example.pupil.buum;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pupil.buum.Data.Customer;
import com.example.pupil.buum.Data.Database;

import java.nio.BufferUnderflowException;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private Button btnLogin;
    private Button btnRegister;
    private TextView txtUName;
    private TextView txtPW;

    Database db = Database.newInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        try{
            initComponents();
            setListener();
        }catch (Exception ex){
            Toast.makeText(this,"Error in ProduktansichtActivity - method 'onCreate': "+ex.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    private void setListener() throws Exception{
        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
    }

    private void initComponents() throws Exception{
        btnLogin = (Button) findViewById(R.id.buttonLogin);
        btnRegister = (Button) findViewById(R.id.buttonRegestrieren);
        txtUName = (TextView) findViewById(R.id.txtUName);
        txtPW=(TextView) findViewById(R.id.txtPW);
        this.setTitle("BUMM");
        checkIfAnyUserIsLoggedIn();

    }

    private void checkIfAnyUserIsLoggedIn() {
        Customer c = db.getLoggedInUser();
        if(c!=null){
            Intent intent =new Intent(this, HomepageActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    @Override
    public void onClick(View view) {
        try{
            switch(view.getId()){
                case R.id.buttonLogin:{
                    Intent intent =new Intent(this, HomepageActivity.class);
                    db.getCustomer(txtUName.getText().toString(),txtPW.getText().toString());
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    break;
                }
                case R.id.buttonRegestrieren:{
                    startActivity(new Intent(LoginActivity.this, RegestrierenActivity.class));
                    break;
                }
            }

        }catch (Exception ex){
            Toast.makeText(this, "Error by registrate: " + ex.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
}
