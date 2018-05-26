package com.example.trantien.appreview.mvp.Home;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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

        ImageView image = rowView.findViewById(R.id.image);

        // 4. Set the text for textView
        title.setText(listData.get(position).getTitle());
        image.setImageResource(getMipmapResIdByName(listData.get(position).getImage()));

        // 5. retrn rowView
        return rowView;
    }

    public int getMipmapResIdByName(String resName)  {
        String pkgName = context.getPackageName();

        // Trả về 0 nếu không tìm thấy.
        int resID = context.getResources().getIdentifier(resName , "drawable", pkgName);
        Log.i("CustomListView", "Res Name: "+ resName+"==> Res ID = "+ resID);
        return resID;
    }

    private Drawable getImage(String resName){
        int resourceId = context.getResources().getIdentifier(resName, "drawable", context.getPackageName());
        return context.getResources().getDrawable(resourceId);
    }
}
