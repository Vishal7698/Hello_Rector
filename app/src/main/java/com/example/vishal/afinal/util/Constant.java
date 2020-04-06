package com.example.vishal.afinal.util;

import android.content.Context;
import android.widget.Toast;

import com.example.vishal.afinal.Rec_Reg;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Constant {

    public static final String INVALID_RECTOR_NAME = "Please Enter Rector name";
    public static final String INVALID_RECTOR_PHONE = "Please Enter Rector Correct Phone Number";
    public static final String INVALID_RECTOR_EMAIL = "Please Enter Rector Correct Email ID";
    public static final String INVALID_RECTOR_PASSWORD= "Please Enter Security Password";
    public static final String INVALID_RECTOR_CONFIRM_PASS = "Please Enter Confirm Password";
    public static final String RECTOR_SAVE_SUCESSFULLY = "Rector Saved Successfully";
    public static final String INCORRECT_MOBILE_OR_PASS = "Incorrect Mobile Number OR Password";
    public static final String LOGIN_SUCESSFULLY = "Login Successfully";

    public static void showToast(Context context, String msg) {
        Toast.makeText(context,msg,Toast.LENGTH_LONG).show();
    }

    public static boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (!matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }
}
