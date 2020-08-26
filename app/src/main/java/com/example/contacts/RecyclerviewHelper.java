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
    private onItemListener monitemListner;

    public RecyclerviewHelper(List<User> userLists, onItemListener monitemListner) {
        this.userLists = userLists;
        this.monitemListner = monitemListner;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_layout,parent,false);

        return new ViewHolder(v,monitemListner);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        User user=userLists.get(position);
        holder.textView.setText(user.getUsername());
        holder.imageView.setImageBitmap(user.getUserImg());
        holder.phnno.setText(user.getUserphnno());

    }

    @Override
    public int getItemCount() {

        return userLists == null ? 0 : userLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView imageView;
        TextView textView;
        TextView phnno;
        onItemListener onItemListener;


        public ViewHolder(@NonNull View itemView ,onItemListener onItemListener) {
            super(itemView);
            imageView=(ImageView)itemView.findViewById(R.id.circularimg);
            textView=(TextView)itemView.findViewById(R.id.contactName);
            phnno=(TextView)itemView.findViewById(R.id.contactNum);
            itemView.setOnClickListener(this);
            this.onItemListener=onItemListener;
        }

        @Override
        public void onClick(View view) {
          onItemListener.onItemClick(getAdapterPosition());
        }
    }
    public interface onItemListener{
        void onItemClick(int position);
    }
}
