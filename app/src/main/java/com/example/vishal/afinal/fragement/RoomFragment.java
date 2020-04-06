package com.example.vishal.afinal.fragement;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.vishal.afinal.R;
import com.example.vishal.afinal.adpter.RoomDetailAdapter;
import com.example.vishal.afinal.databasehandling.Dbhandler;
import com.example.vishal.afinal.util.Constant;


public class RoomFragment extends Fragment {

    GridView roomlistgrid;
    Dbhandler helper;
    Context context;

    String roomlist[]={"R-101","R-102","R-103","R-104","R-105","R-106","R-107","R-108","R-109","R-110","R-111","R-112","R-113","R-114","R-115","R-116"};
    String roomid[]={"101","102","103","104","105","106","107","108","109","110","111","112","113","114","115","116"};


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.show_room_fragment, container, false);

        helper = new Dbhandler(getActivity());
        context = getActivity();
        roomlistgrid = view.findViewById(R.id.grid_view);

        RoomDetailAdapter adapter = new RoomDetailAdapter(getActivity(), roomlist,roomid,helper);
        roomlistgrid.setAdapter(adapter);
        roomlistgrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                String count= helper.getAvailableRoomDetails(roomid[position]);
                if(Integer.parseInt(count)<4){
                    android.support.v4.app.Fragment fragment = new SaveStudentFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("roomid", ""+roomid[position]);
                    fragment.setArguments(bundle);
                    getFragmentManager().beginTransaction().replace(R.id.fragment_frame, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();
                }else{
                    Constant.showToast(getActivity(),"No accommodation available");
                }
            }
        });

        return view;

    }
}
