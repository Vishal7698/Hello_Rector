package com.example.vishal.afinal.fragement;




import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.vishal.afinal.R;
import com.example.vishal.afinal.databasehandling.Dbhandler;
import com.example.vishal.afinal.dto.StudentDto;
import com.example.vishal.afinal.util.Constant;

public class SaveStudentFragment extends Fragment implements View.OnClickListener{
    Spinner hosteldropdown;
    EditText edtstudentnm;
    EditText editTextmobilenumber;
    EditText editTextemailid;
    EditText editTextcollageid;
    EditText editTextparrentname;
    EditText editTextparentmobilenumber;
    EditText editTextparentaddress;
    EditText editTextparentrelation;
    EditText editTextgardianname;
    EditText editTextgardianmobile;
    EditText editTextgardianaddress;
    String hosteltype[]={"Select Hostel","Boys Hostel","Girls Hostel"};
    Dbhandler helper;
    Button savestudent;
    Context context;
    String roomid;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.save_student_fragment, container, false);
        helper=new Dbhandler(getActivity());
        context=getActivity();
        savestudent =view.findViewById(R.id.buttondavestudent);
        hosteldropdown =view.findViewById(R.id.spinnerhosteltype);

        edtstudentnm                =view.findViewById(R.id.editTextstudentname);
        editTextmobilenumber        =view.findViewById(R.id.editTextmobilenumber);
        editTextemailid             =view.findViewById(R.id.editTextemailid);
        editTextcollageid           =view.findViewById(R.id.editTextcollageid);
        editTextparrentname         =view.findViewById(R.id.editTextparrentname);
        editTextparentmobilenumber =view.findViewById(R.id.editTextparentmobilenumber);
        editTextparentaddress      =view.findViewById(R.id.editTextparentaddress);
        editTextparentrelation     =view.findViewById(R.id.editTextparentrelation);
        editTextgardianname        =view.findViewById(R.id.editTextgardianname);
        editTextgardianmobile      =view.findViewById(R.id.editTextgardianmobile);
        editTextgardianaddress     =view.findViewById(R.id.editTextgardianaddress);

        Bundle bdl = getArguments();
        roomid=bdl.getString("roomid");
        System.out.println("room id..>"+helper.getAvailableRoomDetails(roomid));

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, hosteltype);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hosteldropdown.setAdapter(dataAdapter);

        savestudent.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.buttondavestudent:
              String sname           =edtstudentnm.getText().toString().trim();
              String smobile         =editTextmobilenumber.getText().toString().trim();
              String semail          =editTextemailid.getText().toString().trim();
              String scollageid      =editTextcollageid.getText().toString().trim();
              String sparrentnm      =editTextparrentname.getText().toString().trim();
              String sparrentmo      =editTextparentmobilenumber.getText().toString().trim();
              String sparrentadrs    =editTextparentaddress.getText().toString().trim();
              String sparentrelation =editTextparentrelation.getText().toString().trim();
              String sgeriannm       =editTextgardianname.getText().toString().trim();
              String sgardinamobile  =editTextgardianmobile.getText().toString().trim();
              String sgardinaddress  =editTextgardianaddress.getText().toString().trim();
              String hosteltype      =hosteldropdown.getSelectedItem().toString().trim();


                StudentDto save=new StudentDto();
                save.setSname(sname);
                save.setSmobile(smobile);
                save.setSemailid(semail);
                save.setScollageid(scollageid);
                save.setSparrentname(sparrentnm);
                save.setSpnumber(sparrentmo);
                save.setSprelation(sparrentadrs);
                save.setSpaddress(sparentrelation);
                save.setSgardianname(sgeriannm);
                save.setSgardinnumber(sgardinamobile);
                save.setSdardianaddress(sgardinaddress);
                save.setHosteltype(hosteltype);
                save.setRoomid(roomid);

                if(validation(save)){
                    if(helper.saveSudents(save)){
                       Constant.showToast(getContext(),"Student save successfully");
                        android.support.v4.app.Fragment fragment = new HomeFragment();
                        getFragmentManager().beginTransaction().replace(R.id.fragment_frame, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();

                    }

                }
            break;
        }
    }

    private boolean validation(StudentDto save) {
        boolean result=true;

        if(save.getSname().isEmpty() || save.getSname().length()<2){
             edtstudentnm.setError("Enter correct student name");
             result = false;
        }
        if(save.getSmobile().isEmpty() || save.getSmobile().length()<10){
            editTextmobilenumber.setError("Enter correct 10 digit mobile number");
            result = false;
        }
        if(save.getSemailid().isEmpty()){
            editTextemailid.setError("Enter email id");
            result = false;
        }
        if(Constant.isEmailValid(save.getSemailid())){
            editTextemailid.setError("Enter valid email id");
            result = false;
        }

        if(save.getScollageid().isEmpty() || save.getScollageid().length()<5){
            editTextcollageid.setError("Enter correct college id");
            result = false;
        }
        if(save.getSparrentname().isEmpty() || save.getSparrentname().length()<2){
            editTextparrentname.setError("Enter correct student parent name");
            result = false;
        }
        if(save.getSpaddress().isEmpty()){
             editTextparentaddress.setError("Enter correct parent address");
            result = false;
        }
        if(save.getSpnumber().isEmpty() || save.getSpnumber().length()<10){
             editTextparentmobilenumber.setError("Enter correct student parrent 10 digit mobile number");
            result = false;
        }

        if(save.getSpnumber().isEmpty() || save.getSpnumber().length()<10){
            editTextparentrelation.setError("Enter correct parrent relation with studednt");
            result = false;
        }

        if(save.getHosteltype().equalsIgnoreCase("Select Hostel")){
          Constant.showToast(getContext(),"Plese Select Hotel");
        }
        return result;
    }

}
