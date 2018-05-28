package com.example.pupil.buum;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.pupil.buum.Data.Article;

import java.util.ArrayList;

/**
 * Created by Melanie on 5/14/18.
 */

public class EventCartListAdapter extends BaseAdapter {
    private ArrayList<Article> singleRow;
    private ArrayList<Article> checkedArticle=new ArrayList<>();
    private Article currentArticle;
    CheckBox isChecked;
    boolean[] checked=null;
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

    public ArrayList<Article> getCheckedItemList(){
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
            isChecked= (CheckBox) convertView.findViewById(R.id.isChecked);
            final Spinner countArticle = (Spinner) convertView.findViewById(R.id.countOrder);

            currentArticle = (Article)getItem(position);

            HeadingText.setText(currentArticle.getName());
            ArticleNr.setText(ArticleNr.getText()+" "+String.valueOf(currentArticle.getId()));
            onStock.setText(onStock.getText()+" "+String.valueOf(currentArticle.getOnStock()));
            final ArrayAdapter<String> count=fillSpinnerWithAvailableSock(currentArticle,convertView);
            countArticle.setAdapter(count);

            isChecked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        checkedArticle.add((Article) getItem(position));
                    }
                    else{
                        checkedArticle.remove(getItem(position));
                    }
                }
            });

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
