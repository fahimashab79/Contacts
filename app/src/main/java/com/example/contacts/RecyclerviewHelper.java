package com.example.contacts;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerviewHelper extends RecyclerView.Adapter<RecyclerviewHelper.ViewHolder> {

    List<User>userLists=new ArrayList<>();

    public RecyclerviewHelper(List<User> userLists) {
        this.userLists = userLists;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_layout,parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        User user=userLists.get(position);
        holder.textView.setText(user.getUsername());
        holder.imageView.setImageBitmap(user.getUserImg());

    }

    @Override
    public int getItemCount() {
        Log.i("size",String.valueOf(userLists.size()));
        return userLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView textView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=(ImageView)itemView.findViewById(R.id.circularimg);
            textView=(TextView)itemView.findViewById(R.id.contactName);
        }
    }
}
