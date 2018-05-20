package com.example.schueler.project;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.schueler.project.data.Article;
import com.example.schueler.project.data.Category;
import com.example.schueler.project.data.Database;
import com.example.schueler.project.data.LocalDate;
import com.example.schueler.project.data.User;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Array;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements OnClickListener,AdapterView.OnItemSelectedListener{
    Button btnLol;
    Button btnLol2;
    Button btnLol3;
    ImageView imageView7;
    SearchView sv;
    EditText editText, username,password;
    private static int RESULT_LOAD_IMAGE = 1;
    byte[] byteArray=null;
    Database db = Database.newInstance();
    Spinner s,s2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db.setURL("http://192.168.1.11:28389/");
        try {
            getAllViews();

        } catch (Exception e) {
            Toast.makeText(this,"exception"+e.getMessage(),Toast.LENGTH_LONG).show();
        }

    }

    public void getAllViews() {
        btnLol=(Button)this.findViewById(R.id.btnLol);
        btnLol2=(Button)this.findViewById(R.id.btnLol2);
        btnLol3=(Button)this.findViewById(R.id.btnLol3);
        //imageView7= (ImageView) this.findViewById(R.id.imageView7);
        //editText=(EditText)this.findViewById(R.id.editText);
        username=(EditText)this.findViewById(R.id.un);
        password=(EditText)this.findViewById(R.id.pw);
        btnLol.setOnClickListener(this);
        btnLol2.setOnClickListener(this);
        btnLol3.setOnClickListener(this);
        s= (Spinner) this.findViewById(R.id.spinner);
        s2= (Spinner) this.findViewById(R.id.spinner2);
        sv=(SearchView) this.findViewById(R.id.sView);
        s.setOnItemSelectedListener(this);
    }


    @Override
    public void onClick(View v) {
        try{
            if(v.getId()==R.id.btnLol){ //login
                if(username.getText().toString().replace(" ","").length()==0 || password.getText().toString().length()==0)
                    throw new Exception("fields cant be empty");
                 db.loginUser(new User(username.getText().toString(),password.getText().toString()));
                Toast.makeText(this,"my man is logged in",Toast.LENGTH_LONG).show();
            }else if(v.getId()==R.id.btnLol2){
                //TODO: data checking
                fillArticleDetails();
               // User u = new User("username2","pw","firstName2","lastName","email2@gmail.com","location","address2","role",new LocalDate(11,11,1111),7500,"active");
             //  db.addUser(u);
                //db.updateUser(u);
            }else if(v.getId()==R.id.btnLol3){
                ArrayList<Article> a = db.filterArticles("ho",((Category)s2.getSelectedItem()).getCurCategory());
                Toast.makeText(this,"ma:"+a,Toast.LENGTH_LONG).show();
            }
        }catch (Exception ex){
            Toast.makeText(this,"error:"+ex.getMessage(),Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == RESULT_LOAD_IMAGE) {
            try {

                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                imageView7.setImageBitmap(selectedImage);

                /*Bitmap bmp = selectedImage;
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byteArray = stream.toByteArray();
                bmp.recycle();*/

                System.out.println("njknk");
            } catch (Exception e) {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void fillArticleDetails() throws Exception {
        ArrayAdapter<Article> adapterArticles = new ArrayAdapter<Article>(
                this,
                android.R.layout.simple_spinner_item, db.getArticles());
        s.setAdapter(adapterArticles);

        ArrayAdapter<Category> adapterProducer = new ArrayAdapter<Category>(
                this,
                android.R.layout.simple_spinner_item, db.getCategories());
        s2.setAdapter(adapterProducer);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Article a = (Article) parent.getItemAtPosition(position);

        Intent intent = new Intent(MainActivity.this,ArticleDetail.class);
        intent.putExtra("artnr", a.getArtNr());
        startActivity(intent);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

