package com.example.vishal.afinal.adpter;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.vishal.afinal.R;
import com.example.vishal.afinal.databasehandling.Dbhandler;
import com.example.vishal.afinal.dto.FeeDetailDto;
import com.example.vishal.afinal.dto.StudentDto;
import com.example.vishal.afinal.util.Constant;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ViewStudentAdapter extends BaseAdapter {
    Context context;
    List<StudentDto> list;
    ArrayList<StudentDto> myfilterlist;

    public ViewStudentAdapter(Context context, List<StudentDto> list) {
        this.context = context;
        this.list = list;
        this.myfilterlist = new ArrayList<StudentDto>();
        this.myfilterlist.addAll(list);
        System.out.println("in constructor" + list.size());
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View convertView, ViewGroup viewGroup) {
        StudentDto dao = list.get(i);
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.student_list_adapter, viewGroup, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        viewHolder.studentname.setText(dao.getSname());
        viewHolder.smobilenumber.setText(dao.getSmobile());
        viewHolder.setcollageid.setText(dao.getScollageid());
        viewHolder.setemailid.setText(dao.getSemailid());


        viewHolder.smobilenumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
/*
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse(list.get(i).getSmobile()));
                context.startActivity(callIntent);*/

            }
        });

       viewHolder.studentinfo.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               showStudentInfoAlert(i,list);

           }
       });

       viewHolder.feeinfo.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               showfeeInfoAlert(i,list);
           }
       });


        return convertView;
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        list.clear();
        if (charText.length() == 0) {
            list.addAll(myfilterlist);
        }
        else
        {
            for (StudentDto wp : myfilterlist)
            {
                if (wp.getSname().toLowerCase(Locale.getDefault()).contains(charText) || wp.getSmobile().toLowerCase(Locale.getDefault()).contains(charText) )
                {
                    list.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
    TextView paidfee;
    TextView submitedon;
    TextView totalfee;
    TextView idpendinffee;
    EditText pendingfeee;
    private void showfeeInfoAlert(final int position, final List<StudentDto> list) {
        final StudentDto dao=list.get(position);
        LayoutInflater factory = LayoutInflater.from(context);
        final View view = factory.inflate(R.layout.studentfeeinfo, null);
        final AlertDialog deleteDialog = new AlertDialog.Builder(context).create();
        deleteDialog.setView(view);
        paidfee=view.findViewById(R.id.submitedfee);
        pendingfeee=view.findViewById(R.id.pandingfee);
        submitedon=view.findViewById(R.id.submitedon);
        totalfee=view.findViewById(R.id.totalfee);
        idpendinffee=view.findViewById(R.id.idpendinffee);

        totalfee.setText("Total Fee (INR) :  "+"54000");
        paidfee.setText("Submited Fee (INR) :  "+dao.getFee());
        submitedon.setText("Submited On (INR) :  "+dao.getDatetime());
        idpendinffee.setText("Pending Fee (INR) :  "+(54000-Double.parseDouble(dao.getFee())));
        if(54000< Double.parseDouble(dao.getFee())){
            view.findViewById(R.id.savefee).setEnabled(false);
            pendingfeee.setEnabled(false);
        }


        view.findViewById(R.id.savefee).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pendingfee=pendingfeee.getText().toString().trim();
                double ttlfee=Double.parseDouble(pendingfee)+Double.parseDouble(dao.getFee());
            if(!pendingfee.isEmpty()){

                if(new Dbhandler(context).updateFeeDetails(""+ttlfee,dao.getFeeid())){
                    list.get(position).setFee(""+ttlfee);
                    Constant.showToast(context,"Fee update successfully");
                    deleteDialog.dismiss();
                }
            }else {
                Constant.showToast(context,"Please enter fee amount");
            }


            }
        });
        deleteDialog.show();
    }
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
    private void showStudentInfoAlert(final int position, final List<StudentDto> list) {
        final StudentDto dao=list.get(position);
        String hosteltype[]={"Select Hostel","Boys Hostel","Girls Hostel"};
        LayoutInflater factory = LayoutInflater.from(context);
        final View view = factory.inflate(R.layout.save_student_fragment, null);
        final AlertDialog deleteDialog = new AlertDialog.Builder(context).create();
        deleteDialog.setView(view);

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

       final Spinner  hosteldropdown              =view.findViewById(R.id.spinnerhosteltype);
       Button savestudent                  =view.findViewById(R.id.buttondavestudent);
        savestudent.setText("Update Student Info");
        edtstudentnm.setText(dao.getSname());
        editTextmobilenumber.setText(dao.getSmobile());
        editTextemailid.setText(dao.getSemailid());
        editTextcollageid.setText(dao.getScollageid());
        editTextparrentname.setText(dao.getSparrentname());
        editTextparentmobilenumber.setText(dao.getSpnumber());
        editTextparentaddress.setText(dao.getSpaddress());
        editTextparentrelation.setText(dao.getSprelation());
        editTextgardianname.setText(dao.getSgardianname());
        editTextgardianmobile.setText(dao.getSgardinnumber());
        editTextgardianaddress.setText(dao.getSdardianaddress());

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, hosteltype);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hosteldropdown.setAdapter(dataAdapter);
        ArrayAdapter myAdap = (ArrayAdapter) hosteldropdown.getAdapter();
        int spinnerPosition = myAdap.getPosition(dao.getHosteltype());
        hosteldropdown.setSelection(spinnerPosition);

        savestudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                save.setStudentid(list.get(position).getStudentid());
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
                save.setRoomid(dao.getRoomid());

                if(validation(save)){
                    if(new Dbhandler(context).updateStudentInfo(save)) {
                        Constant.showToast(context,"Student update sucessfully. You will get update soon...");
                        deleteDialog.dismiss();
                    }

                }
            }
        });


       deleteDialog.show();
        
    }

    private class ViewHolder {
        TextView studentname;
        TextView smobilenumber;
        TextView setcollageid;
        TextView setemailid;
        LinearLayout studentinfo;
      //  LinearLayout parrentinfo;
        LinearLayout feeinfo;
       // LinearLayout exporttinfo;

        public ViewHolder(View view) {
            studentname = view.findViewById(R.id.setstudentid);
            smobilenumber = view.findViewById(R.id.smobilenumber);
            setcollageid = view.findViewById(R.id.setcollageid);
            setemailid = view.findViewById(R.id.setemailid);
            studentinfo = view.findViewById(R.id.studentinfo);
            feeinfo = view.findViewById(R.id.feeinfo);
            // exporttinfo = view.findViewById(R.id.exporttinfo);
            //parrentinfo = view.findViewById(R.id.parrentinfo);

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
            Constant.showToast(context,"Plese Select Hotel");
        }
        return result;
    }
}
