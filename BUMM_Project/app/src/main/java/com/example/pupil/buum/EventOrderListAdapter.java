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

import com.example.pupil.buum.pkgData.Category;
import com.example.pupil.buum.pkgData.Database;
import com.example.pupil.buum.pkgData.OrderArticle;

import java.util.ArrayList;

/**
 * Created by Melanie on 5/14/18.
 */

public class EventOrderListAdapter extends BaseAdapter {
    private ArrayList<OrderArticle> singleRow;
    private AdapterView.OnItemClickListener mListener;
    private LayoutInflater thisInflator;
    Database db = Database.newInstance();

    public EventOrderListAdapter(Context context, ArrayList<OrderArticle> aRow)
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
            convertView = thisInflator.inflate(R.layout.articleorderview, parent, false);
        }
        else{
            convertView.getTag();
        }

        EditText HeadingText = (EditText) convertView.findViewById(R.id.txtProduktName);
        TextView ArticleNr = (TextView) convertView.findViewById(R.id.txtArtikelNr);
        TextView amount = (TextView) convertView.findViewById(R.id.txtGekauft);
        TextView description = (TextView) convertView.findViewById(R.id.txtDesc);
        ImageView typeImage = (ImageView) convertView.findViewById(R.id.imgKategorie);

        OrderArticle currentArticle = (OrderArticle) getItem(position);

        HeadingText.setText(""+currentArticle.getOrderedArticle().getName());
        ArticleNr.setText("Article Nr: "+String.valueOf(currentArticle.getOrderedArticle().getArtNr()));
        amount.setText("amount: "+String.valueOf(currentArticle.getAmount()));
        description.setText(""+String.valueOf(currentArticle.getOrderedArticle().getDescription()));

        Category parentCategory = null;
        try {
            parentCategory = db.getParentCategory(currentArticle.getOrderedArticle().getArtCategory());
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
