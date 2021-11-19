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

import DataModels.DataModelSelectBarber;
import DataModels.DataModelSelectServices;

public class MyAdapterSelectService extends RecyclerView.Adapter<MyAdapterSelectService.MyViewHolder> {

    private List<DataModelSelectServices> list;
    // private MyAdapterSelectBarber.ItemClickListener clickListener;

    public MyAdapterSelectService(List<DataModelSelectServices> list) {
        this.list = list;
        // this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public MyAdapterSelectService.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_row_selectservices, parent, false);
        return new MyAdapterSelectService.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapterSelectService.MyViewHolder holder, int position) {
        holder.serviceName.setText(list.get(position).getServiceName());
        holder.servicePic.setImageResource(list.get(position).getServicePic());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView serviceName;
        ImageView servicePic;
        ConstraintLayout selectServiceRowLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            serviceName = itemView.findViewById(R.id.serviceName);
            servicePic = itemView.findViewById(R.id.servicePic);
            selectServiceRowLayout = itemView.findViewById(R.id.selectServiceRowLayout);
        }
    }

    public interface ItemClickListener {

        public void onItemClick(DataModelSelectServices DataModelSelectServices);
    }
}