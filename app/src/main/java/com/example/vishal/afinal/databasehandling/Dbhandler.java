package com.example.vishal.afinal.databasehandling;

import android.content.ContentValues;
import android.content.Context;
import android.content.SyncStatusObserver;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.vishal.afinal.dto.FeeDetailDto;
import com.example.vishal.afinal.dto.StudentDto;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Dbhandler extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "mmithostel.db";

    public Dbhandler(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table rector_master(r_id integer primary key, name text,phone text,email text, password text)");
        db.execSQL("create table student_master(s_id integer primary key, s_name text,s_phone text,s_email text, s_address text,sp_name text,sp_relation text,sp_phonenumber text,sp_address,g_name text,g_number text,g_address text,h_id text,rm_id text)");
        db.execSQL("create table fee_details(fd_id integer primary key, s_id text ,sumited_fee text ,datetime text)");

        //static table this three table not in used
        db.execSQL("create table hostel_master(h_id integer primary key, h_type text)");
        db.execSQL("create table room_master(r_id integer primary key, room_name text ,h_id text)");
        db.execSQL("create table fee_master(fee_id integer primary key, ttlhstlfee text ,yearoffee text)");


    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
       onCreate(db);
    }

    public boolean saveRectorDetails(String name, String mobile, String email, String password) {
        boolean result=false;
        try {
                SQLiteDatabase db = this.getWritableDatabase();
                ContentValues contentValues = new ContentValues();
                contentValues.put("name", name);
                contentValues.put("phone", mobile);
                contentValues.put("email", email);
                contentValues.put("password", password);
                db.insert("rector_master", null, contentValues);
                System.out.println("Rector details Saved sucessfully...");
                result=true;
    }catch (Exception e){
        e.printStackTrace();
    }
        return result;
    }

    public boolean doLogin(String mo, String pass) {
        boolean result=false;
        try {
            String sql="select * from rector_master where phone= '"+mo+"' and password= '"+pass+"'";
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor res =  db.rawQuery( sql, null );
            res.moveToFirst();
            while(res.isAfterLast() == false){
                result=true;
                res.moveToNext();
            }
        }catch (Exception e){
            e.printStackTrace();
            result=false;
        }
        return result;
    }
    public boolean saveSudents(StudentDto save){
        boolean result=false;
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
//student info
            contentValues.put("s_name", save.getSname());
            contentValues.put("s_phone", save.getSmobile());
            contentValues.put("s_email",save.getSemailid() );
            contentValues.put("s_address", save.getScollageid());
//parrent info
            contentValues.put("sp_name", save.getSparrentname());
            contentValues.put("sp_relation",save.getSprelation() );
            contentValues.put("sp_phonenumber", save.getSpnumber());
            contentValues.put("sp_address", save.getSpaddress());
//gardian ingo
            contentValues.put("g_name", save.getSgardianname());
            contentValues.put("g_number", save.getSgardinnumber());
            contentValues.put("g_address", save.getSdardianaddress());
//hostel & room id
            contentValues.put("h_id", save.getHosteltype());
            contentValues.put("rm_id",save.getRoomid() );


            long id=db.insert("student_master", null, contentValues);
            System.out.println("student details Saved sucessfully..."+id);

            insertFeeDetails(id);
            result=true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    private void insertFeeDetails(long id) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("s_id", id);
            contentValues.put("sumited_fee", "0.0");
            contentValues.put("datetime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            db.insert("fee_details", null, contentValues);
            System.out.println("fee_details  Saved sucessfully..."+id);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public List<StudentDto> getStudentList() {
        List<StudentDto> list=new ArrayList<>();
     //   db.execSQL("create table fee_details(fd_id integer primary key, s_id text ,sumited_fee text ,datetime text)");

        String sql="Select * from student_master t1 left join fee_details t2 on t1.s_id=t2.s_id";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                StudentDto dto = new StudentDto();
                dto.setStudentid(cursor.getString(cursor.getColumnIndex("s_id")));
                dto.setSname(cursor.getString(cursor.getColumnIndex("s_name")));
                dto.setSmobile(cursor.getString(cursor.getColumnIndex("s_phone")));
                dto.setSemailid(cursor.getString(cursor.getColumnIndex("s_email")));
                dto.setScollageid(cursor.getString(cursor.getColumnIndex("s_address")));
                dto.setSparrentname(cursor.getString(cursor.getColumnIndex("sp_name")));
                dto.setSpnumber(cursor.getString(cursor.getColumnIndex("sp_phonenumber")));
                dto.setSprelation(cursor.getString(cursor.getColumnIndex("sp_relation")));
                dto.setSpaddress(cursor.getString(cursor.getColumnIndex("sp_address")));
                dto.setSgardianname(cursor.getString(cursor.getColumnIndex("g_name")));
                dto.setSgardinnumber(cursor.getString(cursor.getColumnIndex("g_number")));
                dto.setSdardianaddress(cursor.getString(cursor.getColumnIndex("g_address")));
                dto.setHosteltype(cursor.getString(cursor.getColumnIndex("h_id")));
                dto.setRoomid(cursor.getString(cursor.getColumnIndex("rm_id")));
                dto.setFeeid(cursor.getString(cursor.getColumnIndex("fd_id")));
                dto.setFee(cursor.getString(cursor.getColumnIndex("sumited_fee")));
                dto.setDatetime(cursor.getString(cursor.getColumnIndex("datetime")));

                list.add(dto);
            } while (cursor.moveToNext());
        }
        db.close();
        cursor.close();

        return list;
    }

    public String getAvailableRoomDetails(String s) {
        String count="0";
        try {
            String sql="select count(*) as c from student_master where rm_id= '"+s+"'";
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor res =  db.rawQuery( sql, null );
            res.moveToFirst();
            while(res.isAfterLast() == false){
                count=res.getString(res.getColumnIndex("c"));
                res.moveToNext();
            }
        }catch (Exception e){
            e.printStackTrace();
            count="0";
        }
        return count;
    }

    public boolean updateStudentInfo(StudentDto save) {
        boolean result=false;
        try {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("s_name", save.getSname());
        contentValues.put("s_phone", save.getSmobile());
        contentValues.put("s_email",save.getSemailid() );
        contentValues.put("s_address", save.getScollageid());
        contentValues.put("sp_name", save.getSparrentname());
        contentValues.put("sp_relation",save.getSprelation() );
        contentValues.put("sp_phonenumber", save.getSpnumber());
        contentValues.put("sp_address", save.getSpaddress());
        contentValues.put("g_name", save.getSgardianname());
        contentValues.put("g_number", save.getSgardinnumber());
        contentValues.put("g_address", save.getSdardianaddress());
        contentValues.put("h_id", save.getHosteltype());
        contentValues.put("rm_id",save.getRoomid() );
        db.update("student_master", contentValues, "s_id = '"+ save.getStudentid()+"'",null);

        result= true;
        }catch (Exception e){
            e.printStackTrace();

        }

        return  result;
    }

    public boolean updateFeeDetails(String pendingfee, String feeid) {
        boolean result=false;
        try {
            String dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            SQLiteDatabase db = this.getReadableDatabase();
            db.execSQL("UPDATE fee_details SET sumited_fee='" + pendingfee + "', datetime='" + dt + "' WHERE fd_id='" + feeid + "'");
            result=true;
            System.out.println("update sucessfully");
        }catch (Exception e){
            e.printStackTrace();
        }
        return  result;
    }

    public FeeDetailDto getStudentFeeDetails(String studentid) {
        FeeDetailDto dto=new FeeDetailDto();

        try {
            String sql="select *  from fee_details where s_id= '"+studentid+"'";
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor res =  db.rawQuery( sql, null );
            res.moveToFirst();
            while(res.isAfterLast() == false){
                dto.setFeeid(res.getString(res.getColumnIndex("fd_id")));
                dto.setStudentid(res.getString(res.getColumnIndex("s_id")));
                dto.setSubmitedfee(res.getString(res.getColumnIndex("sumited_fee")));
                dto.setSubmitedon(res.getString(res.getColumnIndex("datetime")));
                res.moveToNext();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return dto;
    }
}