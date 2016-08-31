package com.example.neild_000.happinessjar.RedundantCode.Card;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.neild_000.happinessjar.R;

/**
 * Created by neild_000 on 01/03/2015.
 */
public class CardSelectorAdapter extends BaseAdapter {

    private Context context;

    //public int[] thumbnail= {R.drawable.card_for_chooser, R.drawable.card_for_chooser, R.drawable.card_for_chooser};
    public int[] thumbnail;

    public CardSelectorAdapter( Context c, int number_of_cards ) {
        context = c;
        int[] temp = new int[number_of_cards];
        for(int i=0; i<number_of_cards; i++){
            temp[i] = R.drawable.cardwhitebg;
        }
        thumbnail = temp;
    }

    @Override
    public int getCount() {
        return thumbnail.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if(convertView == null) {
            imageView = new ImageView(context);
            imageView.setLayoutParams(new GridView.LayoutParams(250, 250));
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageView.setPadding(6, 6, 6, 6);
        }else{
                imageView = (ImageView) convertView;
        }

        imageView.setImageResource(thumbnail[position]);

        return imageView;
    }
}
