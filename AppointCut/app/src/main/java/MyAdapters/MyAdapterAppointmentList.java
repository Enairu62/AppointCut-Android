package MyAdapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import online.appointcut.R;

import java.util.List;

import DataModels.DataModelAppointmentList;

public class MyAdapterAppointmentList extends RecyclerView.Adapter<MyAdapterAppointmentList.MyViewHolder>{

    private List<DataModelAppointmentList> list;

    public MyAdapterAppointmentList(List<DataModelAppointmentList> list){
        this.list = list;
    }

    @NonNull
    @Override
    public MyAdapterAppointmentList.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_row_appointment_list, parent, false);
        return new MyAdapterAppointmentList.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapterAppointmentList.MyViewHolder holder, int position) {
        holder.barberShopPic.setImageResource(list.get(position).getBarbershopPic());
        holder.barberShopName.setText(list.get(position).getBarbershopName());
        holder.barberShopServices.setText(list.get(position).getBarberShopService());
        holder.barberShopSchedule.setText(list.get(position).getBarbershopSchedule());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView barberShopName, barberShopServices, barberShopSchedule;
        ImageView barberShopPic;
        ConstraintLayout appointmentListLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            barberShopPic = itemView.findViewById(R.id.barberShopPic);
            barberShopName = itemView.findViewById(R.id.barberShopName);
            barberShopServices = itemView.findViewById(R.id.barberShopService);
            barberShopSchedule = itemView.findViewById(R.id.barberShopSchedule);
            appointmentListLayout = itemView.findViewById(R.id.appointmentListLayout);
        }
    }
}
