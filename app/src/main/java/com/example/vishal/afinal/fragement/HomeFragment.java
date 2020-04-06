package com.example.vishal.afinal.fragement;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;

import com.example.vishal.afinal.R;

public class HomeFragment extends Fragment implements View.OnClickListener{
    GridView geidhomegrid;
    Button btnsavenewstudent,btnshowstudentinfo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_home_fragment, container, false);

        btnsavenewstudent=view.findViewById(R.id.savestudent);
        btnshowstudentinfo=view.findViewById(R.id.showstudentinfo);
        btnsavenewstudent.setOnClickListener(this);
        btnshowstudentinfo.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.savestudent:
               /* android.support.v4.app.Fragment fragment = new SaveStudentFragment();
                getFragmentManager().beginTransaction().replace(R.id.fragment_frame, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();
*/
                android.support.v4.app.Fragment fragment = new RoomFragment();
                getFragmentManager().beginTransaction().replace(R.id.fragment_frame, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();

                break;

            case R.id.showstudentinfo:
                android.support.v4.app.Fragment fragment2 = new ShowStudentFragment();
                getFragmentManager().beginTransaction().replace(R.id.fragment_frame, fragment2, fragment2.getClass().getSimpleName()).addToBackStack(null).commit();



                break;

        }
    }
}
