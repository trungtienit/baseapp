package com.example.trantien.appreview.mvp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.trantien.appreview.R;

import java.util.List;

/**
 * Created by QuocTuyen on 5/27/2018.
 */

public class NewsAdapter extends BaseAdapter {

    private List<NewsModel> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    public NewsAdapter(List<NewsModel> listData, Context context) {
        this.listData = listData;
        this.context = context;
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.home_list_view_item, null);
           // convertView = layoutInflater.inflate(R.layout.home_list_view_item, parent);
            holder = new ViewHolder();
//            holder.image = (ImageView)convertView.findViewById(R.id.image);
            holder.title = (TextView)convertView.findViewById(R.id.txtTitle);
            convertView.setTag(holder);
        }
        else
            holder = (ViewHolder)convertView.getTag();

        NewsModel news = this.listData.get(position);
        holder.title.setText(news.getTitle());

        //Add image

        return convertView;
    }

    static class ViewHolder{
        ImageView image;
        TextView title;
    }
}
