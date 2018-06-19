package com.example.pupil.buum;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.pupil.buum.pkgData.Article;
import com.example.pupil.buum.pkgData.Category;
import com.example.pupil.buum.pkgData.Database;
import com.example.pupil.buum.pkgData.OrderArticle;

import java.util.ArrayList;

/**
 * Created by Melanie on 5/14/18.
 */

public class EventCartListAdapter extends BaseAdapter {
    private ArrayList<Article> singleRow;
    private ArrayList<OrderArticle> checkedArticle=new ArrayList<>();
    private Article currentArticle;
    CheckBox Checked;
    Database db = Database.newInstance();
    private Context context;
    private LayoutInflater thisInflator;

    public EventCartListAdapter(Context context, ArrayList<Article> aRow)
    {
        this.context=context;
        this.singleRow = aRow;
        this.thisInflator = (LayoutInflater.from(context));
    }
    @Override
    public int getCount() {
        return singleRow.size();
    }

    @Override
    public Object getItem(int position) {
        return singleRow.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public ArrayList<OrderArticle> getCheckedItemList(){
        return checkedArticle;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView == null)
        {
            convertView = thisInflator.inflate(R.layout.cartitemview, parent, false);

            EditText HeadingText = (EditText) convertView.findViewById(R.id.txtProduktName);
            TextView ArticleNr = (TextView) convertView.findViewById(R.id.txtArtikelNr);
            TextView onStock = (TextView) convertView.findViewById(R.id.txtAufLager);
            ImageView typeImage = (ImageView) convertView.findViewById(R.id.imgKategorie);
            Checked= (CheckBox) convertView.findViewById(R.id.isChecked);
            final Spinner countArticle = (Spinner) convertView.findViewById(R.id.countOrder);

            currentArticle = (Article)getItem(position);

            HeadingText.setText(currentArticle.getName());
            ArticleNr.setText(ArticleNr.getText()+" "+String.valueOf(currentArticle.getArtNr()));
            onStock.setText(onStock.getText()+" "+String.valueOf(currentArticle.getOnStock()));
            final ArrayAdapter<String> count=fillSpinnerWithAvailableSock(currentArticle,convertView);
            countArticle.setAdapter(count);

            Checked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        System.out.println(""+countArticle.getSelectedItem().toString());
                        checkedArticle.remove(new OrderArticle((Article) getItem(position),Integer.parseInt(countArticle.getSelectedItem().toString())));
                        checkedArticle.add(new OrderArticle((Article) getItem(position),Integer.parseInt(countArticle.getSelectedItem().toString())));
                    }
                    else{
                        checkedArticle.remove(new OrderArticle((Article) getItem(position),Integer.parseInt(countArticle.getSelectedItem().toString())));
                    }
                }
            });

            countArticle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                   @Override
                   public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                       if(Checked.isChecked()){
                           Checked.setSelected(false);
                       }
                   }

                   @Override
                   public void onNothingSelected(AdapterView<?> adapterView) {

                   }
               }
            );

            Category parentCategory = null;
            try {
                parentCategory = db.getParentCategory(currentArticle.getArtCategory());
            } catch (Exception e) {
                e.printStackTrace();
            }

            switch (parentCategory.getParentCategory()) {
                case "Kleidung": {
                    typeImage.setImageResource(R.drawable.clothes);
                    break;
                }
                case "Technik": {
                    typeImage.setImageResource(R.drawable.technology);
                    break;
                }
                default: {
                    break;
                }
            }

        }
        return convertView;
    }


    private ArrayAdapter<String> fillSpinnerWithAvailableSock(Article curr,View convertView) {

        ArrayList<String> Stock= getDataSpinner(curr);

        ArrayAdapter<String> itemsAdapter = new ArrayAdapter<>(this.context, android.R.layout.simple_list_item_1, Stock);

        return itemsAdapter;
    }

    private ArrayList<String> getDataSpinner(Article curr) {
        ArrayList<String> Stock = new ArrayList<>();

        for (int i = 0; i < curr.getOnStock(); i++) {
            int count = i + 1;
            Stock.add("" + count);
        }
        return Stock;
    }
}
