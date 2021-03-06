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

import com.example.pupil.buum.pkgData.Article;
import com.example.pupil.buum.pkgData.Category;
import com.example.pupil.buum.pkgData.Database;

import java.util.ArrayList;

/**
 * Created by Melanie on 5/14/18.
 */

public class EventArticleListAdapter extends BaseAdapter {
    private ArrayList<Article> singleRow;
    private AdapterView.OnItemClickListener mListener;
    private LayoutInflater thisInflator;
    private Database db = Database.newInstance();

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
        }
        else{
            convertView.getTag();
        }

        EditText HeadingText = (EditText) convertView.findViewById(R.id.txtProduktName);
        TextView ArticleNr = (TextView) convertView.findViewById(R.id.txtArtikelNr);
        TextView onStock = (TextView) convertView.findViewById(R.id.txtAufLager);
        TextView description = (TextView) convertView.findViewById(R.id.txtDesc);
        ImageView typeImage = (ImageView) convertView.findViewById(R.id.imgKategorie);

        Article currentArticle = (Article)getItem(position);

        HeadingText.setText(""+currentArticle.getName());
        ArticleNr.setText("Article Nr: "+String.valueOf(currentArticle.getArtNr()));
        onStock.setText("on Stock: "+String.valueOf(currentArticle.getOnStock()));
        description.setText(""+String.valueOf(currentArticle.getDescription()));

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
        return convertView;
    }
}
