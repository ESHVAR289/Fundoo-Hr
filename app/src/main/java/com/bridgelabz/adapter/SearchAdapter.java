package com.bridgelabz.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bridgelabz.R;

import java.util.ArrayList;

/**
 * Created by eshvar289 on 10/7/16.
 */

public class SearchAdapter extends BaseAdapter{
    private Context context;
    private ArrayList<String> mCountries;
    private LayoutInflater mLayoutInflater;
    private boolean mIsFilterList;

    public SearchAdapter(Context context, ArrayList<String> mCountries, boolean mIsFilterList) {
        this.context = context;
        this.mCountries = mCountries;
        this.mIsFilterList = mIsFilterList;
    }

    public void updateList(ArrayList<String> filterList,boolean isFilterList){
        this.mCountries = filterList;
        this.mIsFilterList = isFilterList;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return mCountries.size();
    }

    @Override
    public Object getItem(int position) {
        return mCountries.get(position);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View v=convertView;
        ViewHolder holder=null;
        if (v==null){
            holder = new ViewHolder();
            mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            v = mLayoutInflater.inflate(R.layout.list_item_search,viewGroup,false);
            holder.txtCountry = (TextView) v.findViewById(R.id.txt_country);
            v.setTag(holder);
        }else {
            holder = (ViewHolder) v.getTag();
        }

        holder.txtCountry.setText(mCountries.get(i));

        Drawable searchDrawable,recentDrawable;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            searchDrawable = context.getResources().getDrawable(R.drawable.ic_magnify_grey600_24dp, null);
            recentDrawable = context.getResources().getDrawable(R.drawable.ic_backup_restore_grey600_24dp, null);
        }else {
            searchDrawable = context.getResources().getDrawable(R.drawable.ic_magnify_grey600_24dp);
            recentDrawable = context.getResources().getDrawable(R.drawable.ic_backup_restore_grey600_24dp);
        }

        if(mIsFilterList) {
            holder.txtCountry.setCompoundDrawablesWithIntrinsicBounds(searchDrawable, null, null, null);
        }else {
            holder.txtCountry.setCompoundDrawablesWithIntrinsicBounds(recentDrawable, null, null, null);
        }
        return v;
    }
    class ViewHolder{
        TextView txtCountry;
    }
}
