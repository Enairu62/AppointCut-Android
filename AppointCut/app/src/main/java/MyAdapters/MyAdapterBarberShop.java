package MyAdapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appointcut.R;

import java.util.List;

import DataModels.DataModelBarberShop;

public class MyAdapterBarberShop extends RecyclerView.Adapter<MyAdapterBarberShop.MyViewHolder>{

    private List<DataModelBarberShop> list;
    private MyAdapterBarberShop.ItemClickListener clickListener;

    public MyAdapterBarberShop(List<DataModelBarberShop> list, MyAdapterBarberShop.ItemClickListener clickListener){
        this.list = list;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public MyAdapterBarberShop.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_row_barbershop, parent, false);
        return new MyAdapterBarberShop.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapterBarberShop.MyViewHolder holder, int position) {
        holder.barbershopName.setText(list.get(position).getBarberShopName());
        holder.barbershopDesc.setText(list.get(position).getBarberShopDesc());
        holder.myImages.setImageResource(list.get(position).getBarberShopImage());
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

        TextView barbershopName, barbershopDesc;
        ImageView myImages;
        ConstraintLayout barberShopRowLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            barbershopName = itemView.findViewById(R.id.txtBarberShopName);
            barbershopDesc = itemView.findViewById(R.id.txtBarberShopDesc);
            myImages = itemView.findViewById(R.id.myImages);
            barberShopRowLayout = itemView.findViewById(R.id.barberShopRowLayout);
        }
    }
    public interface ItemClickListener{

        public void onItemClick(DataModelBarberShop dataModelBarberShop);
    }
}
