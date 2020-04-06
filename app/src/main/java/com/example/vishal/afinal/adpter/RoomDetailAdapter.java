package com.example.vishal.afinal.adpter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.vishal.afinal.R;
import com.example.vishal.afinal.databasehandling.Dbhandler;

public class RoomDetailAdapter extends BaseAdapter {


    Context context;
    String[] roomlist;
    String[] roomid;
    Dbhandler helper;
    public RoomDetailAdapter(Context context, String[] roomlist, String[] roomid, Dbhandler helper) {
        this.context=context;
        this.roomlist=roomlist;
        this.roomid=roomid;
        this.helper=helper;
    }

    @Override
    public int getCount() {
        return roomlist.length;
    }

    @Override
    public Object getItem(int position) {
        return roomlist[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

       ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.room_detail_adapter, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.roomnameid.setText(roomlist[position]);

        viewHolder.availablestudent.setText("Total Student : "+helper.getAvailableRoomDetails(roomid[position]));
        return convertView;
    }

    private class ViewHolder {
        TextView roomnameid;
        TextView availablestudent;
        TextView setcollageid;


        public ViewHolder(View view) {
            roomnameid = view.findViewById(R.id.roomnameid);
            availablestudent = view.findViewById(R.id.availablestudent);



        }
    }
}
