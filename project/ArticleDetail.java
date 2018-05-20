package com.example.schueler.project;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.schueler.project.data.Article;
import com.example.schueler.project.data.Database;
import com.example.schueler.project.data.LocalDate;
import com.example.schueler.project.data.Rating;
import com.example.schueler.project.data.User;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ArticleDetail extends AppCompatActivity implements AdapterView.OnItemLongClickListener, View.OnClickListener {

    Database db;
    Button btn, btn2;
    TextView name,preis,onStock,descr;
    ListView listRatings;
    int artNr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail2);


        try{
            db=Database.newInstance();
            artNr =  this.getIntent().getExtras().getInt("artnr");
            Article selected = db.getArticle(artNr);
            initComponents();
            name.setText(""+selected.getName());
            preis.setText(""+selected.getPrice());
            onStock.setText(""+selected.getOnStock());
            descr.setText(""+selected.getDescription());

        }catch (Exception ex){
            Toast.makeText(this,"Error in ProduktansichtActivity - method 'onCreate': "+ex,Toast.LENGTH_LONG).show();
        }
    }

    private void initComponents() throws Exception {
        name = (TextView) findViewById(R.id.textView);
        preis = (TextView) findViewById(R.id.textView2);
        onStock = (TextView) findViewById(R.id.textView3);
        descr = (TextView) findViewById(R.id.textView4);
        listRatings = (ListView) findViewById(R.id.lView);
        btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(this);
        btn2 = (Button) findViewById(R.id.button2);
        btn2.setOnClickListener(this);

        listRatings.setOnItemLongClickListener(this);
        ArrayList<Rating> allRatings = db.getRatingOfArticle(artNr);
        fillList(allRatings);
    }

    private void fillList(ArrayList<Rating> allRatings) throws Exception {
        ArrayAdapter<Rating> adapterArticles = new ArrayAdapter<Rating>(
                this,
                android.R.layout.simple_expandable_list_item_1, allRatings);
        listRatings.setAdapter(adapterArticles);

    }


    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        final Rating a = (Rating) parent.getItemAtPosition(position);
        if(a.getUserWhoRated().getUsername().equals("username2")){

            LinearLayout layout = new LinearLayout(this);
            layout.setOrientation(LinearLayout.VERTICAL);

            final EditText ratingV = new EditText(ArticleDetail.this);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            ratingV.setLayoutParams(lp);
            ratingV.setInputType(InputType.TYPE_CLASS_NUMBER);
            ratingV.setText(""+a.getRatingValue());
            layout.addView(ratingV);

            final EditText ratingK = new EditText(ArticleDetail.this);
            LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            ratingK.setLayoutParams(lp2);
            ratingK.setInputType(InputType.TYPE_CLASS_TEXT);
            ratingK.setText(a.getRatingComment());
            layout.addView(ratingK);

            new AlertDialog.Builder(ArticleDetail.this)
                    .setView(layout)
                    .setTitle("Deine Bewertung")
                    .setPositiveButton("LÖSCHEN",
                            new DialogInterface.OnClickListener() {
                                @TargetApi(11)
                                public void onClick(DialogInterface dialog, int id) {
                                    try{
                                       // Toast.makeText(ArticleDetail.this,"still in development",Toast.LENGTH_LONG).show();
                                        String erg = db.deleteRating(a);
                                        Toast.makeText(ArticleDetail.this,""+erg,Toast.LENGTH_LONG).show();
                                        fillList(db.getRatingOfArticle(artNr));
                                        dialog.cancel();
                                    }catch (Exception ex){
                                        Toast.makeText(ArticleDetail.this,"update failed:" +ex,Toast.LENGTH_LONG).show();
                                    }
                                }
                            })
                    .setNeutralButton("UPDATEN",
                            new DialogInterface.OnClickListener() {
                                @TargetApi(11)
                                public void onClick(DialogInterface dialog, int id) {
                                    try{
                                        int val= Integer.parseInt(ratingV.getText().toString());
                                        if(val<1 || val > 5)
                                            throw new Exception("value has to be between 1-5");
                                        String komment = ratingK.getText().toString();
                                        if(komment.length()<10)
                                            throw new Exception("comment has to be higher than 10 characters");

                                        a.setRatingValue(val);
                                        a.setRatingComment(komment);
                                        String erg = db.updateRating(a);
                                        Toast.makeText(ArticleDetail.this,""+erg,Toast.LENGTH_LONG).show();
                                        fillList(db.getRatingOfArticle(artNr));
                                        dialog.cancel();
                                    }catch (Exception ex){
                                        Toast.makeText(ArticleDetail.this,"update failed:" +ex,Toast.LENGTH_LONG).show();
                                    }
                                }
                            })
                    .setNegativeButton("ABBRECHEN",
                            new DialogInterface.OnClickListener() {
                                @TargetApi(11)
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            }).show();
        }

        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onClick(View v) {
        try{
            if(v.getId()==R.id.button){
                Rating r = new Rating(db.getArticle(artNr),(new User("username2","lkj")), new LocalDate(11,11,1111),"new rating ccomment", 4);
                String s = db.addRating(r);
                fillList(db.getRatingOfArticle(artNr));
                Toast.makeText(this,":" +s,Toast.LENGTH_LONG).show();
            }else if(v.getId()==R.id.button2){
                String s = db.deleteArticleFromList("username2",db.getArticle(artNr));
                Toast.makeText(this,"erg: "+s,Toast.LENGTH_LONG).show();
            }
        }catch(Exception ex) {
            Toast.makeText(this,"unknown error::" +ex.getMessage(),Toast.LENGTH_LONG).show();

        }


    }
}
