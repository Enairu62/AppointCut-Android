package com.example.appointcut;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapterBarberShopRow extends RecyclerView.Adapter<MyAdapterBarberShopRow.MyViewHolder>{

    int images[];
    private List<DataModel> list;
    private MyAdapterBarberShopRow.ItemClickListener clickListener;

    public MyAdapterBarberShopRow(List<DataModel> list, MyAdapterBarberShopRow.ItemClickListener clickListener){
        this.list = list;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public MyAdapterBarberShopRow.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.barbershop_row_recycler, parent, false);
        return new MyAdapterBarberShopRow.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapterBarberShopRow.MyViewHolder holder, int position) {
        holder.titleItems.setText(list.get(position).getTitle());
        holder.descriptionItems.setText(list.get(position).getDesc());
        holder.myImages.setImageResource(list.get(position).getImage());
        holder.barberShopRowLayout.setOnClickListener(new View.OnClickListener() {
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
        ConstraintLayout barberShopRowLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            titleItems = itemView.findViewById(R.id.titleItems);
            descriptionItems = itemView.findViewById(R.id.descriptionItems);
            myImages = itemView.findViewById(R.id.myImages);
            barberShopRowLayout = itemView.findViewById(R.id.barberShopRowLayout);
        }
    }
    public interface ItemClickListener{

        public void onItemClick(DataModel dataModel);
    }
}
