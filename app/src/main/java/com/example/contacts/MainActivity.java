package com.example.contacts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;


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

    public void setupAdapter(ViewPager viewPager)
    {
        ViewPagertAdapter viewPagertAdapter=new ViewPagertAdapter(getSupportFragmentManager());
        viewPagertAdapter.addFragment(new Contacts(),"Contacts");
        viewPagertAdapter.addFragment(new Add_Contacts(),"Add Contacts");
        viewPager.setAdapter(viewPagertAdapter);

    }
}
