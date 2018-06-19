package com.example.pupil.buum;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pupil.buum.pkgData.User;
import com.example.pupil.buum.pkgData.Database;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private CardView btnLogin;
    private TextView btnRegister;
    private TextView txtUName;
    private TextView txtPW;

    Database db = Database.newInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        try{
            db.setURL("http://192.168.196.169:28389/");
            ActionBar actionBar = getSupportActionBar();
            actionBar.hide();
            initComponents();
            setListener();
        }catch (Exception ex){
            Toast.makeText(this,"Error: " +ex.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    private void setListener() throws Exception{
        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
    }

    private void initComponents() throws Exception{
        btnLogin = (CardView) findViewById(R.id.btnLogin);
        btnRegister = (TextView) findViewById(R.id.btnRegisterHere);
        txtUName = (TextView) findViewById(R.id.txtLoginUsername);
        txtPW=(TextView) findViewById(R.id.txtLoginPasswort);
        checkIfAnyUserIsLoggedIn();
    }

    private void checkIfAnyUserIsLoggedIn() {
        User c = db.getCurUser();
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
                case R.id.btnLogin:{
                    checkAndLogin();
                    db.loginUser(new User(txtUName.getText().toString(),txtPW.getText().toString()));
                    Intent intent =new Intent(this, HomepageActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    break;
                }
                case R.id.btnRegisterHere:{
                    startActivity(new Intent(this, RegestrierenActivity.class));
                    break;
                }
            }

        }catch (Exception ex){
            Toast.makeText(this, "Error: " + ex.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    private void checkAndLogin() throws Exception {
        if(txtUName.getText().toString().equals("")){
            Toast.makeText(this, "Fill in your username",Toast.LENGTH_LONG).show();
        }
        else if(txtPW.getText().toString().equals("")){
            Toast.makeText(this, "Fill in your password",Toast.LENGTH_LONG).show();
        }

        db.loginUser(new User(txtUName.getText().toString(),txtPW.getText().toString()));
    }
}
