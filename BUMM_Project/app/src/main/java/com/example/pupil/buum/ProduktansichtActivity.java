package com.example.pupil.buum;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pupil.buum.pkgData.Article;
import com.example.pupil.buum.pkgData.Category;
import com.example.pupil.buum.pkgData.Database;
import com.example.pupil.buum.pkgData.Rating;
import com.example.pupil.buum.pkgData.User;

import java.util.ArrayList;

import static android.widget.Toast.LENGTH_LONG;

public class ProduktansichtActivity extends AppCompatActivity implements View.OnClickListener,NavigationView.OnNavigationItemSelectedListener{

    private Database db;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView navigationView;
    private Article selectedArticle;
    private int pr;
    private Spinner onStock;
    private TextView txtName;
    private TextView txtPrice;
    private ImageView category;
    private TextView txtDesc;
    private TextView btnOpenRate;
    private CardView btnBuyNow, btnShoppingCart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produktansicht);

        try{
            initComponents();
            setListener();
        }catch (Exception ex){
            ex.printStackTrace();
            Toast.makeText(this,"Error: "+pr, LENGTH_LONG).show();
        }
    }

    private void initComponents() throws Exception {
        db= Database.newInstance();
        txtName = (TextView) findViewById(R.id.txtProductName);
        txtPrice = (TextView) findViewById(R.id.txtPrice);
        txtDesc = (TextView) findViewById(R.id.txtDesc);
        btnOpenRate = (TextView) findViewById(R.id.btnRateProduct);
        btnBuyNow = (CardView) findViewById(R.id.btnBuy);
        btnShoppingCart = (CardView) findViewById(R.id.btnAddToCart);
        mDrawerLayout =(DrawerLayout) findViewById(R.id.drawer);
        navigationView = (NavigationView) findViewById(R.id.navigation);
        category =(ImageView) findViewById(R.id.imgKategorie);
        onStock = (Spinner) findViewById(R.id.countProducts);
        mToggle= new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void showProduct() throws Exception{
        pr =  this.getIntent().getExtras().getInt("selectedProduct");
        selectedArticle = db.getArticle(pr);

        txtName.setText("" + selectedArticle.getName());
        txtPrice.setText("" + selectedArticle.getPrice() + "0€");
        txtDesc.setText("" + selectedArticle.getDescription());

        Category parentCategory = db.getParentCategory(selectedArticle.getArtCategory());

        switch (parentCategory.getParentCategory()) {
            case "Kleidung": {
                category.setImageResource(R.drawable.clothes);
                break;
            }
            case "Technik": {
                category.setImageResource(R.drawable.technology);
                break;
            }
            default: {
                break;
            }
        }
        fillSpinnerWithAvailableSock();
    }

    private void fillSpinnerWithAvailableSock() {
        ArrayList<String> Stock= new ArrayList<>();

        for(int i=0; i< selectedArticle.getOnStock();i++){
            int count = i+1;
            Stock.add(""+count);
        }

        ArrayAdapter<String> itemsAdapter =
                new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, Stock);

        onStock.setAdapter(itemsAdapter);
    }

    private void setListener() throws Exception {
        mDrawerLayout.addDrawerListener(mToggle);
        navigationView.setNavigationItemSelectedListener(this);
        btnOpenRate.setOnClickListener(this);
        btnBuyNow.setOnClickListener(this);
        btnShoppingCart.setOnClickListener(this);

        showProduct();
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
            int id = item.getItemId();

            switch (id){
                case (R.id.homepage):{
                    startActivity(new Intent(ProduktansichtActivity.this, HomepageActivity.class));
                    break;
                }
                case (R.id.logout):{
                    db.setCurUser(null);

                    Intent intent= new Intent(this, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    break;
                }
                case (R.id.meinKonto):{
                    startActivity(new Intent(ProduktansichtActivity.this, KontoActivity.class));
                    break;
                }
                case (R.id.warenkorb):{
                    startActivity(new Intent(ProduktansichtActivity.this, WarenkorbActivity.class));
                    break;
                }
                case (R.id.meineBestellungen):{
                    startActivity(new Intent(ProduktansichtActivity.this, BestellungenActivity.class));
                    break;
                }
            }
        }catch(Exception ex){
            Toast.makeText(this,"Error caused by Menu: " + ex.getMessage(), LENGTH_LONG).show();
        }

        return false;
    }

    @Override
    public void onClick(View view) {
        try {
            switch (view.getId()) {
                case R.id.btnRateProduct: {
                    final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

                    // ...Irrelevant code for customizing the buttons and title
                    LayoutInflater inflater = this.getLayoutInflater();
                    final View dialogView = inflater.inflate(R.layout.ratealertbox, null);
                    dialogBuilder.setView(dialogView);

                    CardView btnRate = (CardView)dialogView.findViewById(R.id.btnAddRateToProduct);
                    CardView btnBack = (CardView)dialogView.findViewById(R.id.btnBackToProduct);
                    final ListView allRatings = (ListView) dialogView.findViewById(R.id.allRatings);
                    fillListViewWithRatings(allRatings);
                    final AlertDialog ad = dialogBuilder.show();
                    btnRate.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            try {
                                String comment = ((TextView)dialogView.findViewById(R.id.txtRating)).getText().toString();
                                RatingBar b = (RatingBar) dialogView.findViewById(R.id.ratingArticle);
                                int ratingValue=Math.round(b.getRating());

                                if(comment.equals("") || b.getRating() == 0.0){
                                    throw new Exception("Type in a comment and rate product");
                                }
                                User u=db.getCurUser();
                                db.addRating(new Rating(selectedArticle,u,null,comment,ratingValue));
                                fillListViewWithRatings(allRatings);
                            } catch (Exception e) {
                                Toast.makeText(ProduktansichtActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                    final AlertDialog finalAd = ad;
                    btnBack.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                           ad.dismiss();
                        }});

                    break;
                }
                case R.id.btnAddToCart:{
                    String username = db.getCurUser().getUsername();
                    db.addArticleToList(username,selectedArticle);
                    Toast.makeText(this,selectedArticle.getName()+" added to shopping cart",Toast.LENGTH_LONG).show();                    break;
                }
            }
        }
        catch (Exception ex){
            Toast.makeText(this,ex.getMessage(), LENGTH_LONG).show();
        }
    }

    private void fillListViewWithRatings(ListView allRatings) throws Exception {
        ArrayAdapter<Rating> itemsAdapter =
                new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, db.getRatingOfArticle(selectedArticle.getArtNr()));

        allRatings.setAdapter(itemsAdapter);
    }
}
