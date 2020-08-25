package com.example.contacts;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    Context  context;
    private static String DATABASE_NAME="contactsdb.db";
    private static int DATABASE_VERSION=1;
    private ByteArrayOutputStream byteArrayOutputStream;
    private byte[] bytes;

    private String createTable="CREATE TABLE User(username VARCHAR,userphnno VARCHAR,userImg BLOB)";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        try {
            sqLiteDatabase.execSQL(createTable);

        }catch (Exception e)
        {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void addData(User user){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        String name=user.getUsername();
        String phnno=user.getUserphnno();
        Bitmap image=user.getUserImg();
        byteArrayOutputStream=new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG,50,byteArrayOutputStream);
        bytes=byteArrayOutputStream.toByteArray();
        ContentValues contentValues=new ContentValues();
        contentValues.put("username",name);
        contentValues.put("userphnno",phnno);
        contentValues.put("userImg",bytes);

       long flag= sqLiteDatabase.insert("User",null,contentValues);
       if(flag!=-1)
       {
           Toast.makeText(context, "Contact Created Successfully", Toast.LENGTH_SHORT).show();
       }
       else {
           Toast.makeText(context, "Failed!!!", Toast.LENGTH_SHORT).show();
       }


    }

    public List<User>getData(){
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        List<User>users=new ArrayList<>();

        Cursor c=sqLiteDatabase.rawQuery("SELECT * FROM User",null);
        Toast.makeText(context, String.valueOf(c.getCount()), Toast.LENGTH_SHORT).show();
        if (c.getCount()!=0)
        {
            //int i=0;
            while (c.moveToNext()){

               // Toast.makeText(context, "hello"+i, Toast.LENGTH_SHORT).show();
                String name=c.getString(c.getColumnIndex("username"));
                String phnno=c.getString(c.getColumnIndex("userphnno"));
                byte[]bytes=c.getBlob(c.getColumnIndex("userImg"));
                Bitmap bitmap= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                users.add(new User(name,phnno,bitmap));
               // i++;
            }
            return users;

        }
        else {
            Toast.makeText(context, "No Contact added please add one", Toast.LENGTH_SHORT).show();
        }




    return null;
    }
}
