package com.example.pupil.buum;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pupil.buum.Data.Article;

import java.util.ArrayList;

/**
 * Created by Melanie on 5/14/18.
 */

public class EventArticleListAdapter extends BaseAdapter {
    private ArrayList<Article> singleRow;
    private AdapterView.OnItemClickListener mListener;
    private LayoutInflater thisInflator;

    public EventArticleListAdapter(Context context, ArrayList<Article> aRow)
    {
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null)
        {
            convertView = thisInflator.inflate(R.layout.articleitemview, parent, false);

            EditText HeadingText = (EditText) convertView.findViewById(R.id.txtProduktName);
            TextView ArticleNr = (TextView) convertView.findViewById(R.id.txtArtikelNr);
            TextView onStock = (TextView) convertView.findViewById(R.id.txtAufLager);
            ImageView typeImage = (ImageView) convertView.findViewById(R.id.imgKategorie);

            Article currentArticle = (Article)getItem(position);

            HeadingText.setText(currentArticle.getName());
            ArticleNr.setText(ArticleNr.getText()+" "+String.valueOf(currentArticle.getId()));
            onStock.setText(onStock.getText()+" "+String.valueOf(currentArticle.getOnStock()));
        }
        return convertView;
    }
}
