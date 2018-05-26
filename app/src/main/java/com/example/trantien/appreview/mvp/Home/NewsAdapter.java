package com.example.trantien.appreview.mvp.Home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.trantien.appreview.R;

import java.util.List;

/**
 * Created by QuocTuyen on 5/27/2018.
 */

public class NewsAdapter extends ArrayAdapter<NewsModel> {

    private List<NewsModel> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    public NewsAdapter(List<NewsModel> listData, Context context) {
        super(context, R.layout.home_list_view_item, listData);
        this.listData = listData;
        this.context = context;
    }

    @Override
    public int getCount() {
        return listData.size();
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 1. Create inflater
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // 2. Get rowView from inflater
        View rowView = inflater.inflate(R.layout.home_list_view_item, parent, false);

        // 3. Get the two text view from the rowView
        TextView title = (TextView) rowView.findViewById(R.id.txtTitle);

        // 4. Set the text for textView
        title.setText(listData.get(position).getTitle());

        // 5. retrn rowView
        return rowView;
    }

    static class ViewHolder{
        ImageView image;
        TextView title;
    }
}
