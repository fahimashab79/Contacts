package com.example.contacts;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import static android.content.ContentValues.TAG;


public class Contacts extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerviewHelper recyclerviewHelper;
    private DatabaseHelper databaseHelper;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_contacts, container, false);

        recyclerView=v.findViewById(R.id.recyclerView);

        databaseHelper=new DatabaseHelper(getActivity().getApplicationContext());
        recyclerviewHelper=new RecyclerviewHelper(databaseHelper.getData());
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerviewHelper);



        return v;
    }



}