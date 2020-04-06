package com.example.vishal.afinal;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.example.vishal.afinal.databasehandling.Dbhandler;
import com.example.vishal.afinal.util.Constant;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Dbhandler helper;
    EditText txtmobile;
    EditText txtpass;
    Button buttonlogin;
    TextView txtgotoreg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        helper=new Dbhandler(this);
        txtmobile=findViewById(R.id.editMobileNumber);
        txtpass=findViewById(R.id.editpassword);
        buttonlogin=findViewById(R.id.buttonlogin);
        txtgotoreg=findViewById(R.id.texgotosignup);
        SharedPreferences prefs = getSharedPreferences("Login", MODE_PRIVATE);
        String restoredText = prefs.getString("islogin", null);

        if (restoredText!=null) {
            if (restoredText.equalsIgnoreCase("1")) {
                startActivity(new Intent(this, Home.class));
                finish();
            }
        }
        txtgotoreg.setOnClickListener(this);
        buttonlogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.texgotosignup:
                startActivity(new Intent(MainActivity.this,Rec_Reg.class));
            break;
            case R.id.buttonlogin:
                if(validate()){

                    String mo=txtmobile.getText().toString().trim();
                    String pass=txtpass.getText().toString().trim();

                    if(helper.doLogin(mo,pass)){
                        Constant.showToast(this,Constant.LOGIN_SUCESSFULLY);
                        SharedPreferences.Editor editor = getSharedPreferences("Login", MODE_PRIVATE).edit();
                        editor.putString("islogin", "1");
                        editor.commit();
                        startActivity(new Intent(this, Home.class));
                        finish();

                    }else{
                        Constant.showToast(this,Constant.INCORRECT_MOBILE_OR_PASS);
                    }
                    
                }
                
            break;

        }

    }

    private boolean validate() {
        boolean result = false;


        if (txtmobile.getText().toString().trim().length() > 0 && txtmobile.getText().toString().trim().length()==10) {
            result = true;
        } else {
            txtmobile.setError(Constant.INVALID_RECTOR_PHONE);
            result = false;
        }


        if (txtpass.getText().toString().trim().length() > 0) {
            result = true;
        } else {
            txtpass.setError(Constant.INVALID_RECTOR_PASSWORD);
            result = false;
        }


        return result;

    }
}
