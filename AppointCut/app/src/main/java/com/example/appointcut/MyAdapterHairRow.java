package com.example.appointcut;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fragments.FragmentHairCompleteInfo;
import fragments.FragmentHairTrend;

public class MyAdapterHairRow extends RecyclerView.Adapter<MyAdapterHairRow.MyViewHolder> {

    int images[];
    private List<DataModel> list;
    private ItemClickListener clickListener;

    public MyAdapterHairRow(List<DataModel> list, ItemClickListener clickListener){
        this.list = list;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public MyAdapterHairRow.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hair_row_recycler, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapterHairRow.MyViewHolder holder, int position) {
        holder.titleItems.setText(list.get(position).getTitle());
        holder.descriptionItems.setText(list.get(position).getDesc());
        holder.myImages.setImageResource(list.get(position).getImage());
       holder.hairRowLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.onItemClick(list.get(holder.getAdapterPosition()));
            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView titleItems, descriptionItems;
        ImageView myImages;
        ConstraintLayout hairRowLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            titleItems = itemView.findViewById(R.id.titleItems);
            descriptionItems = itemView.findViewById(R.id.descriptionItems);
            myImages = itemView.findViewById(R.id.myImages);
            hairRowLayout = itemView.findViewById(R.id.hairRowLayout);

            itemView.findViewById(R.id.imageBookmark).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(view.getContext(),"Bookmarksss", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public interface ItemClickListener{

        public void onItemClick(DataModel dataModel);
    }


}
