package com.example.vishal.afinal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.vishal.afinal.databasehandling.Dbhandler;
import com.example.vishal.afinal.util.Constant;

public class Rec_Reg extends AppCompatActivity implements View.OnClickListener {

    EditText edtname;
    EditText edtmobile;
    EditText edtemail;
    EditText edtpass;
    EditText edtconfpass;
    Button savereg;
    Dbhandler helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        helper = new Dbhandler(this);

        setContentView(R.layout.activity_rec__reg);
        edtname = findViewById(R.id.editText3);
        edtmobile = findViewById(R.id.editText5);
        edtemail = findViewById(R.id.editText6);
        edtpass = findViewById(R.id.editText7);
        edtconfpass = findViewById(R.id.editText8);
        savereg = findViewById(R.id.saverector);
        savereg.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.saverector:
                if (validate()) {
                    String name= edtname.getText().toString().trim();
                    String mobile= edtmobile.getText().toString().trim();
                    String email= edtemail .getText().toString().trim();
                    String password= edtpass .getText().toString().trim();

                   if(helper.saveRectorDetails(name,mobile,email,password)){
                        Constant.showToast(this,Constant.RECTOR_SAVE_SUCESSFULLY);
                        edtname.getText().clear();
                        edtmobile.getText().clear();
                        edtemail .getText().clear();
                        edtpass .getText().clear();
                       startActivity(new Intent(this, MainActivity.class));
                       finish();
                   }
                }
                break;
        }

    }

    private boolean validate() {
        boolean result = false;
        if (edtname.getText().toString().trim().length() > 0) {
            result = true;
        } else {
            edtname.setError(Constant.INVALID_RECTOR_NAME);
            result = false;
        }

        if (edtmobile.getText().toString().trim().length() > 0) {
            result = true;
        } else {
            edtmobile.setError(Constant.INVALID_RECTOR_PHONE);
            result = false;
        }

        if (edtemail.getText().toString().trim().length() > 0) {
            result = true;
        } else {
            edtemail.setError(Constant.INVALID_RECTOR_EMAIL);
            result = false;
        }

        if (edtpass.getText().toString().trim().length() > 0 && edtpass.getText().toString().trim().equalsIgnoreCase(edtconfpass.getText().toString().trim())) {
            result = true;
        } else {
            edtpass.setError(Constant.INVALID_RECTOR_PASSWORD);
            result = false;
        }


        return result;

    }
}
