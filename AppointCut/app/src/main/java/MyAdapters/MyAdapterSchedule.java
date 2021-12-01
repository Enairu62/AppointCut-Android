package MyAdapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import online.appointcut.R;

import java.util.List;

import DataModels.DataModelSchedule;

public class MyAdapterSchedule extends RecyclerView.Adapter<MyAdapterSchedule.MyViewHolder>{

    private List<DataModelSchedule> list;

    public MyAdapterSchedule(List<DataModelSchedule> list){
        this.list = list;
    }

    @NonNull
    @Override
    public MyAdapterSchedule.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_row_schedule, parent, false);
        return new MyAdapterSchedule.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapterSchedule.MyViewHolder holder, int position) {
        holder.schedTime.setText(list.get(position).getSchedTime());
        holder.schedName.setText(list.get(position).getSchedName());
        holder.schedService.setText(list.get(position).getSchedService());
        holder.schedPrice.setText(String.valueOf(list.get(position).getSchedPrice()));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView schedName, schedService, schedPrice, schedTime;
        ConstraintLayout scheduleRowLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            schedName = itemView.findViewById(R.id.txtSchedName);
            schedService = itemView.findViewById(R.id.txtSchedServices);
            schedTime = itemView.findViewById(R.id.txtSchedRange);
            scheduleRowLayout = itemView.findViewById(R.id.scheduleRowLayout);
        }
    }
}
