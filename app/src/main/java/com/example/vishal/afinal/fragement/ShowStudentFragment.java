package com.example.vishal.afinal.fragement;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.support.v4.app.Fragment;
import com.example.vishal.afinal.R;
import com.example.vishal.afinal.adpter.RoomDetailAdapter;
import com.example.vishal.afinal.adpter.ViewStudentAdapter;
import com.example.vishal.afinal.databasehandling.Dbhandler;
import com.example.vishal.afinal.dto.StudentDto;
import com.example.vishal.afinal.util.Constant;

import java.util.List;
import java.util.Locale;

public class ShowStudentFragment extends Fragment {

    ListView studentlist;
    List<StudentDto> list;
    Dbhandler helper;
    Context context;
    TextView nodataavailabel;
    EditText searchstudent;
    ViewStudentAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.show_student_fragment, container, false);
        helper=new Dbhandler(getActivity());
        context=getActivity();
        studentlist=view.findViewById(R.id.studentlist);
        searchstudent=view.findViewById(R.id.searchstudent);

        nodataavailabel=view.findViewById(R.id.nodataavilable);
        list=helper.getStudentList();
        if (list.size()<=0)
            nodataavailabel.setVisibility(View.VISIBLE);

        adapter = new ViewStudentAdapter(context, list);
        studentlist.setAdapter(adapter);
        searchstudent.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
                String text = searchstudent.getText().toString().toLowerCase(Locale.getDefault());
                adapter.filter(text);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
                // TODO Auto-generated method stub
            }
        });

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Click action
                Constant.showToast(getActivity(),"Comming soon...");
            }
        });

        return view;
    }
}
