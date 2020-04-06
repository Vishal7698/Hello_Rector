package com.example.vishal.afinal;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

class CustomeMenuAdapter extends BaseAdapter {
    public String [] menutype = {"Add Student" ,"Collect Fee","Student Detial","Parents Contact"};
    Context mContext;
    public CustomeMenuAdapter(Context c) {
        this.mContext=c;
    }

    public int getCount() {
        return menutype.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }
    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView tv;

        if (convertView == null) {
            tv = new TextView(mContext);
            tv.setLayoutParams(new GridView.LayoutParams(100, 100));
            tv.setPadding(8, 8, 8, 8);
        }
        else
        {
            tv = (TextView) convertView;
        }
        tv.setText(menutype[position]);
        return tv;
    }

    // Keep all Images in array

}
