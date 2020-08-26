package com.example.contacts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecyclerviewHelper.onItemListener {
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private DatabaseHelper databaseHelper;
    private RecyclerviewHelper recyclerviewHelper;
    private List<User> users;
    private ImageView popImageview;
    private TextView textViewname;
    private TextView textViewphn;
    private ImageView imageViewcall;
    private ImageView imageviewmessage;
    private static final int REQUEST_CALL=1;
    AlertDialog dialog;
    private static String number;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar=findViewById(R.id.toolbar);
        tabLayout=findViewById(R.id.tablayout);
        viewPager =findViewById(R.id.viewPager);


        setSupportActionBar(toolbar);
        setupAdapter(viewPager);
        tabLayout.setupWithViewPager(viewPager);

    }
    public void updatedata(RecyclerView recyclerView){
         users=new ArrayList<>();
        databaseHelper=new DatabaseHelper(getApplicationContext());
        users=databaseHelper.getData();
        recyclerviewHelper=new RecyclerviewHelper(users, (RecyclerviewHelper.onItemListener) this);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
       // Toast.makeText(this, "updatelistcalled", Toast.LENGTH_SHORT).show();
        recyclerView.setAdapter(recyclerviewHelper);
        recyclerviewHelper.notifyDataSetChanged();
    }

    public void setupAdapter(ViewPager viewPager)
    {
        ViewPagertAdapter viewPagertAdapter=new ViewPagertAdapter(getSupportFragmentManager());
        viewPagertAdapter.addFragment(new Contacts(),"Contacts");
        viewPagertAdapter.addFragment(new Add_Contacts(),"Add Contacts");
        viewPager.setAdapter(viewPagertAdapter);

    }

    @Override
    public void onItemClick(int position) {


        User user=users.get(position);

        createDialog(user,position);

    }
    public void createDialog(final User user, final int position)
    {
        final AlertDialog.Builder dialogBuilder=new AlertDialog.Builder(this);
        View popupView= getLayoutInflater().inflate(R.layout.popwindow,null);


        String name=user.getUsername();
       number=user.getUserphnno();
        Bitmap bitmap=user.getUserImg();
        popImageview=(ImageView)popupView.findViewById(R.id.circularimgpop);
        textViewname=(TextView)popupView.findViewById(R.id.popname);
        textViewphn=(TextView)popupView.findViewById(R.id.popnumber);
        imageViewcall=(ImageView)popupView.findViewById(R.id.imageviewcall);
        imageviewmessage=(ImageView)popupView.findViewById(R.id.imageviewmessage);
       imageViewcall.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               callFunction();
               dialog.dismiss();
           }
       });
       imageviewmessage.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
               dialog.dismiss();
              // View addView= getLayoutInflater().inflate(R.layout.fragment_add__contacts,null);
               final ImageView imageView=findViewById(R.id.imageview);
               final EditText editTextphn=findViewById(R.id.phonenoEdittext);
               final EditText editTextname=findViewById(R.id.nameEdittext);
               Button button=findViewById(R.id.save);
               imageView.setImageBitmap(user.getUserImg());
               editTextname.setText(user.getUsername());
               editTextphn.setText(user.getUserphnno());
               button.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {

                       if(editTextname.getText().toString()!=null&&editTextphn.getText().toString()!=null&&imageView!=null) {
                           DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.this);
                           Bitmap image = user.getUserImg();


                           User newupdatedUser = new User(editTextname.getText().toString(), editTextphn.getText().toString(), image);
                           databaseHelper.updateData(position + 1, newupdatedUser);
                           RecyclerView recyclerView = findViewById(R.id.recyclerView);
                           updatedata(recyclerView);
                           imageView.setImageResource(R.drawable.addphoto);
                           editTextname.setText(null);
                           editTextphn.setText(null);
                       }
                       else {
                           Toast.makeText(MainActivity.this, "Please fill up all the info", Toast.LENGTH_SHORT).show();
                       }
                       viewPager.setCurrentItem(viewPager.getCurrentItem()-1);

                   }
               });
           }
       });

        try {
             textViewphn.setText(number);
            textViewname.setText(name);
            popImageview.setImageBitmap(bitmap);
        }catch (Exception e)
        {
            //Log.i("exception",e.getMessage());
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }


        dialogBuilder.setView(popupView);



        dialog=dialogBuilder.create();

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.show();

    }
    public void callFunction(){
 
        if(number.trim().length()>0){
            if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){

                ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.CALL_PHONE},REQUEST_CALL);

            }
            else {
                String dial="tel:"+number;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }
        }
        else {
            Toast.makeText(this, "Invalid number", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==REQUEST_CALL)
        {
            if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                callFunction();
            }
            else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }

        }
    }
}
