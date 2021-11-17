package MyAdapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appointcut.R;

import java.util.ArrayList;
import java.util.List;

import DataModels.DataModelSelectBarber;
import DataModels.DataModelSelectServices;

public class MyAdapterSelectService extends RecyclerView.Adapter<MyAdapterSelectService.MyViewHolder> {

    private ArrayList<DataModelSelectServices> list;
    // private MyAdapterSelectBarber.ItemClickListener clickListener;
    private int checkPosition = -1;

    public MyAdapterSelectService(ArrayList<DataModelSelectServices> list) {
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
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView serviceName;
        ImageView servicePic;
        ConstraintLayout selectServiceRowLayout;
        CardView cardViewServices;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            serviceName = itemView.findViewById(R.id.serviceName);
            servicePic = itemView.findViewById(R.id.servicePic);
            cardViewServices = itemView.findViewById(R.id.cardViewServices);
            selectServiceRowLayout = itemView.findViewById(R.id.selectServiceRowLayout);

        }

        void bind(final DataModelSelectServices dataModelSelectServices){
            cardViewServices.setBackgroundColor(dataModelSelectServices.isChecked() ? Color.parseColor("#FFFFBB33") : Color.WHITE);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dataModelSelectServices.setChecked(!dataModelSelectServices.isChecked());
                    cardViewServices.setBackgroundColor(dataModelSelectServices.isChecked() ? Color.parseColor("#FFFFBB33") : Color.WHITE);
                }
            });
        }
    }

    public ArrayList<DataModelSelectServices> getSelected() {
        ArrayList<DataModelSelectServices> selected = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).isChecked()) {
                selected.add(list.get(i));
            }
        }
        return selected;
    }

    public ArrayList<DataModelSelectServices> getAll() {
        return list;
    }


}