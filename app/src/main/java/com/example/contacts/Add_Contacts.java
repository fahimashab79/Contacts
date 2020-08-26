package com.example.contacts;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaFormat;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;

public class Add_Contacts extends Fragment {


   private ImageView imageView;
   private EditText name;
   private EditText phnno;
   private Button button;
   private int CODE=1;
   private Uri filepath;
   private DatabaseHelper databaseHelper;

   private Bitmap imagefile;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_add__contacts, container, false);
        imageView=(ImageView)v.findViewById(R.id.imageview);
        name=v.findViewById(R.id.nameEdittext);
        phnno=v.findViewById(R.id.phonenoEdittext);
        button=v.findViewById(R.id.save);


        databaseHelper=new DatabaseHelper(getActivity().getApplicationContext());
       imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,CODE);
            }
        });
       button.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if(name.getText().toString()!=null&&phnno.getText().toString()!=null&&imagefile!=null)
               {
                   databaseHelper.addData(new User(name.getText().toString(),phnno.getText().toString(),imagefile));
                   ((MainActivity)getActivity()).updatedata(Contacts.recyclerView);
                   imageView.setImageResource(R.drawable.addphoto);
                   phnno.setText(null);
                   name.setText(null);

               }
               else {
                   Toast.makeText(getContext(), "Please fill up all the info", Toast.LENGTH_SHORT).show();
               }

           }
       });

        return v;
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==CODE&&data!=null&&data.getData()!=null)
        {
            filepath=data.getData();
            try {
                imagefile= MediaStore.Images.Media.getBitmap(getActivity().getApplicationContext().getContentResolver(),filepath);

                imageView.setImageBitmap(null);
                imageView.setImageBitmap(imagefile);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

}